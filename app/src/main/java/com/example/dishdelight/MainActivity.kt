package com.example.dishdelight
import androidx.fragment.app.Fragment
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dishdelight.Fragments.CartFragment
import com.example.dishdelight.Fragments.CuisineFragment
import com.example.dishdelight.Fragments.HomeFragment
import com.example.dishdelight.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val homeFragment = HomeFragment()
    private val cartFragment = CartFragment()
    private val cuisineFragment = CuisineFragment()


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
            .add(R.id.fragment_container, cuisineFragment, "cuisine").hide(cuisineFragment)
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
    private fun switchFragment(targetFragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .hide(activeFragment)
            .show(targetFragment)
            .commit()
        activeFragment = targetFragment
    }
}


