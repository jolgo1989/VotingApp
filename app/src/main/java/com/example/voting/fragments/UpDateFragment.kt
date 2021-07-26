package com.example.voting.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.voting.R
import com.example.voting.data.UserViewModel
import com.example.voting.data.entities.Voters
import kotlinx.android.synthetic.main.fragment_up_date.*
import kotlinx.android.synthetic.main.fragment_up_date.view.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class UpDateFragment : Fragment() {


    private val arg by navArgs<UpDateFragmentArgs>()
    private val mUserViewModel by viewModels<UserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_up_date, container, false)

        view.etUpDateFirstName.editText?.setText(arg.currentUser.firstName)
        view.etUpDateLastName.editText?.setText(arg.currentUser.lastName)
        view.etUpdateNumerCard.editText?.setText(arg.currentUser.votingCard)
        view.imageViewUpDate.setImageURI(Uri.parse(arg.currentUser.img))

        view.buttonUpDate.setOnClickListener {
            updateItem()
        }

        return view

    }

    private fun updateItem() {
        val firstName = etUpDateFirstName.editText?.text.toString()
        val lastName = etUpDateLastName.editText?.text.toString()
        val votingCard = etUpdateNumerCard.editText?.text.toString()
        //val myFile = File(stringPath).toString()

        if (inputCheck(firstName,lastName,votingCard)){

            //Create User Object
            val updateUser = Voters(arg.currentUser.votersId, firstName, lastName, votingCard, "")
            // Update Current User
            mUserViewModel.updateVoter(updateUser)
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show()
            // Navigate Back
            findNavController().navigate(R.id.action_upDateFragment_to_listFragment)
        }else {
            Toast.makeText(context, "Please fill out all fields.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(firsName: String, lastName: String, numberCard: String): Boolean {
        return !(TextUtils.isEmpty(firsName) && TextUtils.isEmpty(lastName) && TextUtils.isEmpty(
            numberCard
        ))
    }


}