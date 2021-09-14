package com.example.voting.list

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.voting.R
import com.example.voting.data.UserViewModel
import com.example.voting.databinding.FragmentListBinding
import com.example.voting.databinding.FragmentUpDateBinding
import kotlinx.android.synthetic.main.fragment_list.view.*


class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val mUserViewModel by viewModels<UserViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater , container , false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Recyclerview
        val adapter = ListAdapter()
        val recyclerView = binding.recyclerview
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mUserViewModel.readAllData.observe(viewLifecycleOwner, Observer { voters ->
            adapter.setData(voters)
        })

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_candidateFragment)

        }

        //Metodo para bloquear el boton a tras
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            // With blank your fragment BackPressed will be disabled.
        }
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}