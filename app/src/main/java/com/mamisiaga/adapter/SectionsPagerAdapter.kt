package com.mamisiaga.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mamisiaga.ui.BelumImunisasiFragment
import com.mamisiaga.ui.SudahImunisasiFragment

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null

        when (position) {
            0 -> fragment = BelumImunisasiFragment()
            1 -> fragment = SudahImunisasiFragment()
        }

        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}