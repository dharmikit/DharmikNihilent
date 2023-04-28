package com.app.dharmiknihilent.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


class BottomMenuSliderAdapter(
    private val fragmentList: MutableList<Fragment>,
    fm: FragmentActivity
) : FragmentStateAdapter(fm) {
    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getItemId(position: Int): Long {
        return fragmentList[position].hashCode().toLong()
    }

    override fun containsItem(itemId: Long): Boolean {
        return fragmentList.find { it.hashCode().toLong() == itemId } != null
    }
}