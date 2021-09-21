package com.example.voting.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.voting.R
import com.example.voting.data.UserViewModel
import com.example.voting.data.entities.User
import com.example.voting.data.entities.Voters
import com.example.voting.databinding.FragmentUpDateBinding
import kotlinx.android.synthetic.main.fragment_up_date.*
import kotlinx.android.synthetic.main.fragment_up_date.view.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
/**
 * https://developer.android.com/topic/libraries/view-binding
 * https://developer.android.com/guide/fragments
 */

class UpDateFragment : Fragment() {

    private var _binding: FragmentUpDateBinding? = null
    private val binding get() = _binding!!

    private val arg by navArgs<UpDateFragmentArgs>()
    private val mUserViewModel by viewModels<UserViewModel>()
    var stringPath: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpDateBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etUpDateFirstName.editText?.setText(arg.currentUser.firstName)
        binding.etUpDateLastName.editText?.setText(arg.currentUser.lastName)
        binding.etUpdateNumerCard.editText?.setText(arg.currentUser.votingCard)
        binding.imageViewUpDate.setImageURI(Uri.parse(arg.currentUser.img))


        binding.buttonUpDate.setOnClickListener {
            updateItem()
        }

        binding.imageViewUpDate.setOnClickListener {
            openGallery()
        }

        //add Menu
        setHasOptionsMenu(true)

    }

    //Open gallery
    fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK && requestCode == 1) {
                //File path
                val selectedImageUri: Uri? = data?.data

                //Convert Uri to a Bitmap
                val source = selectedImageUri?.let {
                    context?.let { it1 ->
                        ImageDecoder.createSource(
                            it1.contentResolver, it
                        )
                    }
                }

                val bitmap = source?.let { ImageDecoder.decodeBitmap(it) }
                binding.imageViewUpDate.setImageBitmap(bitmap)

                //Creation date
                //File creation
                val fileCreation = System.currentTimeMillis()
                val imageName = ("$fileCreation${"image.jpg"}")

                //If it does not exist create the temporary
                val file = File(context?.getExternalFilesDir("PhotoTemp/"), imageName)
                if (file.exists()) {
                    file.delete()
                }
                //Save the temporary
                try {
                    val out = FileOutputStream(file)
                    bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, out)
                    out.flush()
                    out.close()

                } catch (e: IOException) {

                }
                stringPath = file.toString()

            } else {
                Toast.makeText(context, "You didn't any photo", Toast.LENGTH_SHORT).show()
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun updateItem() {
        val firsName = binding.etUpDateFirstName.editText?.text.toString()
        val lastName = binding.etUpDateLastName.editText?.text.toString()
        val votingCard = binding.etUpdateNumerCard.editText?.text.toString()
        var myFile = File(stringPath).toString()
        //Validation of the image in case user data is updated and the image is not modified
        if (myFile == "") {
            myFile = Uri.parse(arg.currentUser.img).toString()
        }

        if (inputCheck(firsName, lastName, votingCard)) {

            //Create User Object
            val updateUser =
                Voters(arg.currentUser.votersId, firsName, lastName, votingCard, myFile)
            // Update Current User
            mUserViewModel.updateVoter(updateUser)
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show()
            // Navigate Back
            findNavController().navigate(R.id.action_upDateFragment_to_listFragment)
        } else {
            Toast.makeText(context, "Please fill out all fields.", Toast.LENGTH_SHORT).show()
        }
    }


    private fun inputCheck(firsName: String, lastName: String, numberCard: String): Boolean {
        return !(TextUtils.isEmpty(firsName) && TextUtils.isEmpty(lastName) && TextUtils.isEmpty(
            numberCard
        ))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        if (item.itemId == R.id.delete_menu) {
            deleteCandidate()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun deleteCandidate() {
        val builder = AlertDialog.Builder(context)
        builder.setPositiveButton("Yes") { _, _ ->
            mUserViewModel.deleteUser(arg.currentUser)
            Toast.makeText(context, "Successfully removed", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_upDateFragment_to_listFragment)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete${arg.currentUser.firstName}?")
        builder.setMessage("Are you sere you want to delete ${arg.currentUser.firstName}")
        builder.create().show()

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}