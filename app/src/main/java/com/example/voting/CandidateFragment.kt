package com.example.voting

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.voting.data.UserViewModel
import com.example.voting.data.entities.Voters
import kotlinx.android.synthetic.main.fragment_candidate.*
import kotlinx.android.synthetic.main.fragment_candidate.view.*

class CandidateFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_candidate, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        view.addCandidate_btn.setOnClickListener {
            insertDataToDatabase()
        }

        return view
    }

    private fun insertDataToDatabase() {

        val firstName = addFirstName_et.editText?.text.toString()
        val lastName = addFirstName_et.editText?.text.toString()
        val votingCard = addNumerCard_et.editText?.text.toString()

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

    private fun inputCheck(firstName: String, lastName: String, votingCrad: String): Boolean {
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && TextUtils.isEmpty(votingCrad))
    }

}