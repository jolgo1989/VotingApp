package com.example.voting.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.voting.R
import com.example.voting.data.UserViewModel
import com.example.voting.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    //Data binding fragment
    private lateinit var binding: FragmentLoginBinding
    private val mUserViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        binding = FragmentLoginBinding.bind(view)
        with(binding) {

            textViewRegistrase.setOnClickListener {
                findNavController().navigate(R.id.action_mainFragment_to_perfilFragment)
            }
            buttonIngresar.setOnClickListener {

                val name = editTExUser.editText?.text.toString()
                val passWord = editTextPassword.editText?.text.toString()


                context?.let { it1 ->
                    mUserViewModel.getLoginDetails(it1, name, passWord)
                        ?.observe(viewLifecycleOwner, Observer {

                            if ( it == null) {
                                Toast.makeText(context, "User or PassWord no found ", Toast.LENGTH_SHORT).show()
                            } else {
                                //  Toast.makeText(context, "Found", Toast.LENGTH_SHORT).show()
                                findNavController().navigate(R.id.action_logInFragment_to_listFragment)

                                //Metodo para borrar datos del editText
                                editTExUser.editText?.setText("")
                                editTextPassword.editText?.setText("")

                            }

                        })
                }
            }


            //Metodo para conservar los datos en el editTExt
            editTExUser.editText?.setText(mUserViewModel.username_.value)
            editTextPassword.editText?.setText(mUserViewModel.passWord_.value)

            editTExUser.editText?.doOnTextChanged { charSequence: CharSequence?, i: Int, i1: Int, i2: Int ->
                mUserViewModel.username_.value = editTExUser.editText?.text.toString()
            }
            editTextPassword.editText?.doOnTextChanged { charSequence: CharSequence?, i: Int, _: Int, i2: Int ->
                mUserViewModel.passWord_.value = binding.editTextPassword.editText?.text.toString()
            }


        }



        return view

    }

}