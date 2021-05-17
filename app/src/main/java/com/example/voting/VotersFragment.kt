package com.example.voting

import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.voting.databinding.FragmentVotersBinding

class VotersFragment : Fragment() {

    private lateinit var binding: FragmentVotersBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_voters, container, false)

        binding = FragmentVotersBinding.bind(view)

        binding.imageView2.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    openGallery()
                } else {
                    val permissionRequest = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permissionRequest, REQUES_PERMISSION_CODE)
                }

            } else {
                openGallery()
            }

        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_GALLERY) {
            if (resultCode == RESULT_OK && data != null) {
                val photo = data.data
                binding.imageView2.setImageURI(photo)
            }else{
                Toast.makeText(context, "You didn't any photo", Toast.LENGTH_SHORT).show()
            }
        }


        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUES_PERMISSION_CODE -> {
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


    private fun openGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_IMAGE_GALLERY)
    }

    companion object {
        private const val REQUEST_IMAGE_GALLERY = 100
        private const val REQUES_PERMISSION_CODE = 101
    }
}