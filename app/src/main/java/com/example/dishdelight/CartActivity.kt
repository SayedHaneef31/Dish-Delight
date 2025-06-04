package com.example.dishdelight

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dishdelight.databinding.ActivityCartBinding
import com.example.dishdelight.databinding.PopularFoodCardBinding

class CartActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityCartBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        populateCart()

        enableBottomNavigation()




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun enableBottomNavigation() {
        binding.navigationnnnn.selectedItemId = R.id.cart
        binding.navigationnnnn.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                    true
                }
                R.id.cart -> {
                    true
                }
                else -> false
            }
        }
    }

    private fun populateCart() {
//        repeat(10) {
//            val popularFoodCardBinding = PopularFoodCardBinding.inflate(layoutInflater)
//
//            popularFoodCardBinding.foodNameIddddd.text="Green Salad"
//            popularFoodCardBinding.foodRatingIddddd.text="4.1 ⭐"
//            popularFoodCardBinding.foodPriceIddddd.text="₹ 349"
//
//            popularFoodCardBinding.btnAddIddddddd.setOnClickListener {
//                popularFoodCardBinding.quantitySelector.visibility = View.VISIBLE
//                popularFoodCardBinding.btnAddIddddddd.visibility = View.GONE
//                popularFoodCardBinding.quantityIddddd.text="1"
//            }
//
//            popularFoodCardBinding.btnPlusIddddd.setOnClickListener {
//                var quantity = popularFoodCardBinding.quantityIddddd.text.toString().toInt()
//                quantity++
//                popularFoodCardBinding.quantityIddddd.text = quantity.toString()
//            }
//
//            popularFoodCardBinding.btnMinusIdddd.setOnClickListener {
//                var quantity = popularFoodCardBinding.quantityIddddd.text.toString().toInt()
//                if(quantity>1){
//                    quantity--
//                    popularFoodCardBinding.quantityIddddd.text = quantity.toString()
//                }
//                else
//                {
//                    popularFoodCardBinding.quantitySelector.visibility = View.GONE
//                    popularFoodCardBinding.btnAddIddddddd.visibility = View.VISIBLE
//
//                }
//            }
//
//            //Adding card view to container
//            binding.containerCartFoods.addView(popularFoodCardBinding.root)
//
//        }
    }
}