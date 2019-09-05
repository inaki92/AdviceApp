package com.inaki.dailyAdvice.UI

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.inaki.dailyAdvice.R
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(), SearchAdviceFragment.OnFragmentInteractionListener,
    RandomAdviceFragment.OnFragmentInteractionListener, GameAdviceFragment.OnFragmentInteractionListener {

    private val searchFragment: SearchAdviceFragment by inject()
    private val randomFragment: RandomAdviceFragment by inject()
    private val gameFragment: GameAdviceFragment by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_search -> {
                    startFragment(searchFragment)
                    true
                }
                R.id.nav_random -> {
                    startFragment(randomFragment)
                    true
                }
                R.id.nav_game -> {
                    startFragment(gameFragment)
                    true
                }
                else -> false
            }
        }
    }

    private fun startFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()

    }

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
