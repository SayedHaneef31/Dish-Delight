package com.example.dishdelight

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dishdelight.databinding.ActivityCuisineBinding
import com.example.dishdelight.databinding.DishesBinding

class CuisineActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCuisineBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCuisineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        poplateSelectedCuisine()

        enableBottomNavigation()


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }


    private fun enableBottomNavigation() {
        binding.navigationnnnn.selectedItemId = R.id.nav_home
        binding.navigationnnnn.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                    true
                }
                R.id.cart -> {
                    startActivity(Intent(this, CartActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    private fun poplateSelectedCuisine() {
        repeat(3) {
            val cuisineListBinding = DishesBinding.inflate(layoutInflater)

            cuisineListBinding.foodNameIddddd.text="Green Salad"
            cuisineListBinding.foodRatingIddddd.text="4.1 ⭐"
            cuisineListBinding.foodPriceIddddd.text="₹ 349"

            cuisineListBinding.btnAddIddddddd.setOnClickListener {
                cuisineListBinding.quantitySelector.visibility = View.VISIBLE
                cuisineListBinding.btnAddIddddddd.visibility = View.GONE
                cuisineListBinding.quantityIddddd.text="1"
            }

            cuisineListBinding.btnPlusIddddd.setOnClickListener {
                var quantity = cuisineListBinding.quantityIddddd.text.toString().toInt()
                quantity++
                cuisineListBinding.quantityIddddd.text = quantity.toString()
            }

            cuisineListBinding.btnMinusIdddd.setOnClickListener {
                var quantity = cuisineListBinding.quantityIddddd.text.toString().toInt()
                if(quantity>1){
                    quantity--
                    cuisineListBinding.quantityIddddd.text = quantity.toString()
                }
                else
                {
                    cuisineListBinding.quantitySelector.visibility = View.GONE
                    cuisineListBinding.btnAddIddddddd.visibility = View.VISIBLE

                }
            }

            //Adding card view to container
            binding.containerSelectedCuisines.addView(cuisineListBinding.root)

        }
    }


}