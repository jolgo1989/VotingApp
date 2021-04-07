package com.example.voting.fragments

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.voting.R
import com.example.voting.data.UserViewModel
import com.example.voting.data.entities.Voters
import com.example.voting.databinding.FragmentCandidateBinding
import com.example.voting.databinding.FragmentLoginBinding
import kotlinx.android.synthetic.main.fragment_candidate.*
import kotlinx.android.synthetic.main.fragment_candidate.view.*

class CandidateFragment : Fragment() {

    //private lateinit var binding: FragmentLoginBinding

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

        return view
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

}