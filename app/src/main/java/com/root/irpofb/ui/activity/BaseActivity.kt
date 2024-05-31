package com.root.irpofb.ui.activity

import android.os.Build
import android.os.PowerManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.root.irpofb.R

abstract class BaseActivity : AppCompatActivity() {


    fun loadFragment(fragment: Fragment, addToBackStack: Boolean) {
        val transition = supportFragmentManager.beginTransaction()
        transition.replace(R.id.container, fragment)
        // turn of animations if power saver is on
        val powerManager = getSystemService(POWER_SERVICE) as PowerManager

        if (addToBackStack) {
            transition.addToBackStack(null)
            transition.commit()
        } else {

            supportFragmentManager.popBackStack()
            transition.commit()
        }

    }
}