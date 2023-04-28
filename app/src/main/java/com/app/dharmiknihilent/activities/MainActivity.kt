package com.app.dharmiknihilent.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.app.dharmiknihilent.R
import com.app.dharmiknihilent.adapter.BottomMenuSliderAdapter
import com.app.dharmiknihilent.databinding.ActivityMainBinding
import com.app.dharmiknihilent.fragments.FavoriteFragment
import com.app.dharmiknihilent.fragments.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    private var homeFragment:HomeFragment?=null
    private var favoriteFragment:FavoriteFragment?=null
    var pagerAdapter: BottomMenuSliderAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)
        setUpTab()
    }

    private fun setUpTab(){

        homeFragment = HomeFragment()
        favoriteFragment = FavoriteFragment()

        val fragList: MutableList<Fragment> = mutableListOf()
        fragList.add(homeFragment!!)
        fragList.add(favoriteFragment!!)

        pagerAdapter = BottomMenuSliderAdapter(fragList, this@MainActivity)
        mainBinding.vpMain.offscreenPageLimit = fragList.size

        mainBinding.vpMain.isUserInputEnabled = false
        mainBinding.vpMain.adapter = pagerAdapter

        mainBinding.bottomMenu.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menuHome -> {
                    mainBinding.vpMain.currentItem = 0
                }
                R.id.menuLike -> {
                    mainBinding.vpMain.currentItem = 1
                }
            }
            true
        }
    }
}