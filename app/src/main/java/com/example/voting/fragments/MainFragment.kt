package com.example.voting.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.voting.R
import com.example.voting.databinding.FragmentListBinding
import com.example.voting.databinding.FragmentMainBinding

/**
 * https://developer.android.com/topic/libraries/view-binding
 * https://developer.android.com/guide/fragments
 */
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        binding = FragmentMainBinding.bind(view)
        with(binding) {

            cvAdmin.setOnClickListener {
                findNavController().navigate(R.id.action_mainFragment_to_logInFragment)
            }

            cvVoters.setOnClickListener {
                findNavController().navigate(R.id.action_mainFragment_to_votersFragment)
            }

        }

        return view

        // binding = FragmentLoginBinding.bind(view)
    }


}