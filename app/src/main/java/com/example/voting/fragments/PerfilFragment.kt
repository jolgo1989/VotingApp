package com.example.voting.fragments

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.voting.R
import com.example.voting.data.User
import com.example.voting.data.UserViewModel
import com.example.voting.databinding.FragmentPerfilBinding
import kotlinx.android.synthetic.main.fragment_perfil.*


class PerfilFragment : Fragment() {

    private lateinit var binding: FragmentPerfilBinding
    private lateinit var mUserViewmodel: UserViewModel

    private lateinit var f: LoginFragment

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_perfil, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mUserViewmodel = ViewModelProvider(this).get(UserViewModel::class.java)
        binding = FragmentPerfilBinding.bind(view)
        binding.buttonAdd.setOnClickListener {
            insertDataToDatabase()
        }
    }

    private fun insertDataToDatabase() {

        val userName = textInputUserName.editText?.text.toString()
        val nameStudent = textInputLastNameStudent.editText?.text.toString()
        val password = textInputPassword.editText?.text.toString()

        if (inputCheck(userName, nameStudent, password)) {

            // Create User Object
            val user = User(
                    0, userName, nameStudent, password
            )

            // Add Data to Database
            mUserViewmodel.addUser(user)
            Toast.makeText(context, "Successfully added!", Toast.LENGTH_SHORT).show()
            // Navigate Back
            findNavController().navigate(R.id.action_perfilFragment_to_mainFragment)

        } else {
            Toast.makeText(context, "Please fill out all fields.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(userName: String, nameStudent: String, password: String): Boolean {

        return !(TextUtils.isEmpty(userName) && TextUtils.isEmpty(nameStudent)  && TextUtils.isEmpty(password))

    }

}