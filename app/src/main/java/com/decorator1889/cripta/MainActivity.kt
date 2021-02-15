package com.decorator1889.cripta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.decorator1889.cripta.Fragments.DeFiFragment
import com.decorator1889.cripta.Fragments.MarketFragment
import com.decorator1889.cripta.Fragments.NewsFragment
import com.decorator1889.cripta.Fragments.PortfolioFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    val fragment1: Fragment = MarketFragment()
    val fragment2: Fragment = NewsFragment()
    //val fragment3: Fragment = DeFiFragment()
    val fragment4: Fragment = PortfolioFragment()
    //val fragment5: Fragment = SignalsFragment()
    val fm: FragmentManager = supportFragmentManager
    var active: Fragment = fragment1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navigation =
            findViewById<View>(R.id.navigation) as BottomNavigationView
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        fm.beginTransaction().replace(R.id.main_container, fragment1, "3").commit()
    }

    private val mOnNavigationItemSelectedListener: BottomNavigationView.OnNavigationItemSelectedListener =
        object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(@NonNull item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.navigation_call -> {
                        fm.beginTransaction().replace(R.id.main_container, fragment1).commit()
                        active = fragment1
                        return true
                    }
                    R.id.navigation_chat -> {
                        fm.beginTransaction().replace(R.id.main_container, fragment2).commit()
                        active = fragment2
                        return true
                    }
//                    R.id.navigation_fisting -> {
//                        fm.beginTransaction().replace(R.id.main_container, fragment3).commit()
//                        active = fragment3
//                        return true
//                    }
                    R.id.navigation_portfolio -> {
                        fm.beginTransaction().replace(R.id.main_container, fragment4).commit()
                        active = fragment4
                        return true
                    }
                    R.id.navigation_helpful -> {
                        fm.beginTransaction().replace(R.id.main_container, fragment2).commit()
                        active = fragment2
                        return true
                    }
                }
                return false
            }
        }

    fun showUpButton() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun hideUpButton() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
    }

    override fun onBackPressed() {

        val backStackCount = supportFragmentManager.backStackEntryCount

        if (backStackCount >= 1) {
            supportFragmentManager.popBackStack()
            if (backStackCount == 1) {
                hideUpButton()
            }
        } else {
            super.onBackPressed()
        }
    }
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home ->{
            onBackPressed()
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }
}