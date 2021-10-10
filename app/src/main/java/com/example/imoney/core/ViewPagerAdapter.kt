package com.example.imoney.core

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.imoney.ui.view.fragments.MensualFragment
import com.example.imoney.ui.view.fragments.SemanalFragment
import com.example.imoney.ui.view.fragments.DiarioFragment

private const val NUM_TABS = 2
class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 ->return DiarioFragment()


        }
        return MensualFragment()
    }

}