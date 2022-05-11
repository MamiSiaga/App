package com.mamisiaga.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mamisiaga.ui.ViewPager1Fragment
import com.mamisiaga.ui.ViewPager2Fragment

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null

        when (position) {
            0 -> fragment = ViewPager1Fragment()
            1 -> fragment = ViewPager2Fragment()
        }

        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}