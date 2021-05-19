package com.example.voting.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.voting.R
import com.example.voting.VotersFragment
import com.example.voting.data.UserViewModel
import com.example.voting.data.entities.Voters
import com.example.voting.databinding.FragmentCandidateBinding
import com.example.voting.databinding.FragmentLoginBinding
import kotlinx.android.synthetic.main.fragment_candidate.*
import kotlinx.android.synthetic.main.fragment_candidate.view.*
import java.util.jar.Manifest

class CandidateFragment : Fragment() {

    private lateinit var binding: FragmentCandidateBinding

    private val mUserViewModel by viewModels<UserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_candidate, container, false)

        view.btAddCandidate.setOnClickListener {
            insertDataToDatabase()
        }

        binding = FragmentCandidateBinding.bind(view)
        binding.imageViewSelect.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    openGallery()
                } else {
                    val permissionRequest = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permissionRequest, REQUEST_PERMISSION_CODE)
                }

            } else {
                openGallery()
            }

        }


        return view
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_IMAGE_GALLERY)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGallery()
                } else {
                    Toast.makeText(
                        context,
                        "Unable to update location without permission",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            else -> {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_GALLERY) {
            if (resultCode == RESULT_OK && data != null) {
                val photo = data.data
                binding.imageViewSelect.setImageURI(photo)
            }else{
                Toast.makeText(context, "You didn't any photo", Toast.LENGTH_SHORT).show()
            }
        }


        super.onActivityResult(requestCode, resultCode, data)
    }


    private fun insertDataToDatabase() {

        binding = view?.let { FragmentCandidateBinding.bind(it) }!!
        with(binding) {
            val firstName = etAddFirstName.editText?.text.toString()
            val lastName = etAddFirstName.editText?.text.toString()
            val votingCard = etAddNumerCard.editText?.text.toString()

            if (inputCheck(firstName, lastName, votingCard)) {
                // Create User Object
                val voters = Voters(
                    0,
                    firstName,
                    lastName,
                    votingCard
                )
                // Add Data to Database
                mUserViewModel.addVoters(voters)
                Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
                // Navigate Back
                findNavController().navigate(R.id.action_candidateFragment_to_listFragment)
            } else {
                Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_LONG)
                    .show()
            }
        }

    }

    private fun inputCheck(firstName: String, lastName: String, votingCrad: String): Boolean {
        return !(TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) || TextUtils.isEmpty(votingCrad))
    }

    companion object {
        private const val REQUEST_IMAGE_GALLERY = 100
        private const val REQUEST_PERMISSION_CODE = 101
    }


}