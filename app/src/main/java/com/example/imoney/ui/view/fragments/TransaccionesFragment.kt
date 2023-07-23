package com.example.imoney.ui.view.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.imoney.R
import com.example.imoney.core.ViewPagerAdapter
import com.example.imoney.data.clases.Tab
import com.example.imoney.databinding.FragmentTransaccionesBinding
import com.example.imoney.ui.viewmodel.FrTransaccionViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator


class TransaccionesFragment : Fragment() {


    private val viewModelTransacciones: FrTransaccionViewModel by viewModels()


    val tabsArray = arrayListOf<Tab>(
        Tab("MES", R.drawable.ic_flechas_invertidas),
       // Tab("Semanal", R.drawable.icon_finanzas_up),
        Tab("AÑO", R.drawable.icon_finanzas_down)

    )
    private var _binding: FragmentTransaccionesBinding? =null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTransaccionesBinding.inflate(inflater,container,false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val viewPager = binding.viewPager

        val tabLayout = binding.tabLayout

        val adapter = ViewPagerAdapter(childFragmentManager, lifecycle)
        viewPager.adapter = adapter


        //CHANGE COLOR TABLAYOUT
        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {

                if (tab.position.toString() == "0"){



                }
                if (tab.position.toString() == "1"){


                }


            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabsArray[position].nombre

            //tab.setIcon(tabsArray[position].icon)

        }.attach()




        //CHIP CHANGE COLOR SELECT
        //CHIP CHANGE COLOR SELECT

       /* binding.chipTodoas.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked) {
                tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#3498DB"));
                tabLayout.setTabTextColors(Color.parseColor("#A1A3A4"), Color.parseColor("#3498DB"));
                binding.containerSelectMonth.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.celeste_normal))

            }
        }

        binding.chipIngresos.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked){
                tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#2ECC71"));
                tabLayout.setTabTextColors(Color.parseColor("#A1A3A4"), Color.parseColor("#2ECC71"));
                binding.containerSelectMonth.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.green_normal))
            }


        }
        binding.chipGastos.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked) {
                tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#E74C3C"));
                tabLayout.setTabTextColors(Color.parseColor("#A1A3A4"), Color.parseColor("#E74C3C"))
                binding.containerSelectMonth.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.red_normal))

            }
        }*/
    }
}