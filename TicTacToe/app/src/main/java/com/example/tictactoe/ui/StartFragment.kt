package com.example.tictactoe.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.tictactoe.databinding.FragmentStartBinding

class StartFragment : Fragment() {


    lateinit var binding: FragmentStartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding=FragmentStartBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       binding.offline.setOnClickListener{navToOffline()}
        binding.online.setOnClickListener{navToOnline()}
    }
    fun navToOffline(){
        requireView().findNavController().navigate(StartFragmentDirections.actionStartFragmentToGameFragment())
    }
    fun navToOnline(){
        requireView().findNavController().navigate(StartFragmentDirections.actionStartFragmentToOnlineGameFragment3())
    }
}



