package com.example.dishdelight
import androidx.fragment.app.Fragment
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dishdelight.Data.CartItem
import com.example.dishdelight.Fragments.CartFragment
import com.example.dishdelight.Fragments.HomeFragment
import com.example.dishdelight.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val cartItems = mutableListOf<CartItem>()

    private val homeFragment = HomeFragment()
    private val cartFragment = CartFragment()

    private var activeFragment : Fragment = homeFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Adding all the supported fragments to the activity
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, homeFragment, "home")
            .add(R.id.fragment_container, cartFragment, "cart").hide(cartFragment)
            .commit()

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
}


