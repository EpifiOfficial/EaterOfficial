package com.epifistudios.eater.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.epifistudios.eater.Menu.ViewPagerAdapter
import com.epifistudios.eater.Menu.fragments.Fragment_Dessert
import com.epifistudios.eater.Menu.fragments.Fragment_Drinks
import com.epifistudios.eater.Menu.fragments.Fragment_MainDishes
import com.epifistudios.eater.R
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Fragment_MainDishes(), "Main dishes")
        adapter.addFragment(Fragment_Dessert(), "Desserts")
        adapter.addFragment(Fragment_Drinks(), "Drinks")
        ViewPager.adapter = adapter
        tabs.setupWithViewPager(ViewPager)






        BtnBack.setOnClickListener {
            finish()
        }

    }


}