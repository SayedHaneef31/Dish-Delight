package com.example.dishdelight
import android.content.Context
import androidx.fragment.app.Fragment
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dishdelight.Data.CartItem
import com.example.dishdelight.Fragments.CartFragment
import com.example.dishdelight.Fragments.HomeFragment
import com.example.dishdelight.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val cartItems = mutableListOf<CartItem>()

    private val homeFragment = HomeFragment()
    private val cartFragment = CartFragment()

    private var activeFragment : Fragment = homeFragment

    private var selectedNavItemId = R.id.nav_home

    override fun attachBaseContext(newBase: Context) {
        val language = LocaleHelper.getLanguage(newBase)
        val context = LocaleHelper.updateLocale(newBase, language)
        super.attachBaseContext(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            // Only add fragments the first time
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, homeFragment, "home")
                .add(R.id.fragment_container, cartFragment, "cart").hide(cartFragment)
                .commit()
        }

        if (savedInstanceState != null) {
            selectedNavItemId = savedInstanceState.getInt("SELECTED_NAV_ITEM", R.id.nav_home)
        }
        binding.navigationnnnn.selectedItemId = selectedNavItemId


        //Setting up the bottom navigation
        binding.navigationnnnn.setOnItemSelectedListener { item ->
            when(item.itemId)
            {
                R.id.nav_home -> {
                    switchFragment(homeFragment)
                    true
                }
                R.id.cart -> {
                    switchFragment(cartFragment)
                    true
                }
                else -> false
            }
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (activeFragment != homeFragment) {
                    switchFragment(homeFragment)
                    binding.navigationnnnn.selectedItemId = R.id.nav_home
                } else {
                    // Exit app on back from home
                    finish()
                }
            }
        })
    }

    fun switchFragment(targetFragment: Fragment) {
        if(targetFragment == activeFragment) return

        // If the target fragment is not already added, add it
        if (!targetFragment.isAdded) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, targetFragment)
                .hide(activeFragment)
                .show(targetFragment)
                .commit()
        } else {
            supportFragmentManager.beginTransaction()
                .hide(activeFragment)
                .show(targetFragment)
                .commit()
        }
        activeFragment = targetFragment
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("SELECTED_NAV_ITEM", binding.navigationnnnn.selectedItemId)
        super.onSaveInstanceState(outState)
    }


}


