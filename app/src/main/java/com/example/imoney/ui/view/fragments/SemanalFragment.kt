package com.example.imoney.ui.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.imoney.databinding.FragmentSemanalBinding


class SemanalFragment : Fragment() {

    private var _binding: FragmentSemanalBinding? =null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?
    ): View? {
       // activity?.application?.setTheme(R.style.Theme_IMoney_ingresos)

                _binding = FragmentSemanalBinding.inflate(inflater,container,false)

        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)




        // Specify layout for recycler view
      /*  binding.recyclerviewTransacciones?.setHasFixedSize(true)
        val gridLayoutManager = GridLayoutManager(
            this.context,1)


        binding.recyclerviewTransacciones?.layoutManager = gridLayoutManager*/
    }
}