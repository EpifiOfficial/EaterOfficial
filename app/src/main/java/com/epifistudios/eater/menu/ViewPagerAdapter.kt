package com.epifistudios.eater.Menu

import android.icu.text.CaseMap
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.epifistudios.eater.Menu.fragments.Fragment_Dessert
import com.epifistudios.eater.Menu.fragments.Fragment_Drinks
import com.epifistudios.eater.Menu.fragments.Fragment_MainDishes

class ViewPagerAdapter(fm:FragmentManager):FragmentPagerAdapter(fm) {

    private val list:MutableList<Fragment> = ArrayList()
    private val titles:MutableList<String> = ArrayList()

    override fun getItem(position: Int): Fragment {
        return list[position]
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getPageTitle(position: Int): CharSequence? {

        return titles[position]

    }
    fun addFragment(fragment: Fragment,title: String){
        list.add(fragment)
        titles.add(title)


    }
}