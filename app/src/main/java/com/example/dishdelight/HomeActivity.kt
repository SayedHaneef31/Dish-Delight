package com.example.dishdelight

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.dishdelight.API.PageCountRequest
import com.example.dishdelight.API.RetrofitClient
import com.example.dishdelight.databinding.ActivityHomeBinding
import com.example.dishdelight.databinding.CuisineCardBinding
import com.example.dishdelight.databinding.PopularFoodCardBinding
import kotlinx.coroutines.launch


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding


    override fun attachBaseContext(newBase: Context) {
        val language = LocaleHelper.getLanguage(newBase)
        val context = LocaleHelper.updateLocale(newBase, language)
        super.attachBaseContext(context)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableBottomNavigation()

        populateCuisineCategory()

        popluateFamousFood()



        floatingButtwonWorking()

        enableCuisineNavigation()

        binding.horizontalScrollContainer.setOnClickListener { view ->
            startActivity(Intent(this, CuisineActivity::class.java))
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun populateCuisineCategory() {

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.getCuisineList(PageCountRequest(3,10))

                if (response.isSuccessful)
                {
                    val cuisineResponse  = response.body()
                    cuisineResponse?.let {

                            val container= binding.horizontalScrollContainer
                            container.removeAllViews()   //saare puraane views hta do

                            for (cuisine in it.cuisines)
                            {
                               val cuisineCardBinding = CuisineCardBinding.inflate(layoutInflater,container,false)

                               cuisineCardBinding.cuisineNameIdddd.text=cuisine.cuisine_name
                                Log.d("Cuisines", cuisine.cuisine_name)

                                Glide.with(this@HomeActivity)
                                    .load(cuisine.cuisine_image_url)
                                    .placeholder(R.drawable.broccoli)   // optional placeholder while loading
                                    .error(R.drawable.broccoli)         // optional error image if load fails
                                    .into(cuisineCardBinding.imageView)

                                container.addView(cuisineCardBinding.root)

                            }

                    }

                }
                else{
                    Toast.makeText(this@HomeActivity,"Failed: ${response.code()}", Toast.LENGTH_LONG).show()
                }
            }
            catch (e: Exception)
            {
                e.printStackTrace()
                Toast.makeText(this@HomeActivity,"Failed: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }

//        repeat(3) {
//            val cuisineCardBinding = CuisineCardBinding.inflate(layoutInflater)
//
//            cuisineCardBinding.textView6.text="North Indian"
//
//
//            //Adding card view to container
//            binding.horizontalScrollContainer.addView(cuisineCardBinding.root)
//
//        }
    }

    private fun enableCuisineNavigation() {
//        binding.
    }

    private fun enableBottomNavigation() {
        binding.navigationnnnn.selectedItemId = R.id.nav_home
        binding.navigationnnnn.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    true
                }
                R.id.cart -> {
                    startActivity(Intent(this, CartActivity::class.java))
                    finish()
                    true
                }
                else -> false
            }
        }
    }


    private fun popluateFamousFood() {
        repeat(3) {
            val popularFoodCardBinding = PopularFoodCardBinding.inflate(layoutInflater)

            popularFoodCardBinding.foodNameIddddd.text="Green Salad"
            popularFoodCardBinding.foodRatingIddddd.text="4.1 ⭐"
            popularFoodCardBinding.foodPriceIddddd.text="₹ 349"

            popularFoodCardBinding.btnAddIddddddd.setOnClickListener {
                popularFoodCardBinding.quantitySelector.visibility = View.VISIBLE
                popularFoodCardBinding.btnAddIddddddd.visibility = View.GONE
                popularFoodCardBinding.quantityIddddd.text="1"
            }

            popularFoodCardBinding.btnPlusIddddd.setOnClickListener {
                var quantity = popularFoodCardBinding.quantityIddddd.text.toString().toInt()
                quantity++
                popularFoodCardBinding.quantityIddddd.text = quantity.toString()
            }

            popularFoodCardBinding.btnMinusIdddd.setOnClickListener {
                var quantity = popularFoodCardBinding.quantityIddddd.text.toString().toInt()
                if(quantity>1){
                    quantity--
                    popularFoodCardBinding.quantityIddddd.text = quantity.toString()
                }
                else
                {
                    popularFoodCardBinding.quantitySelector.visibility = View.GONE
                    popularFoodCardBinding.btnAddIddddddd.visibility = View.VISIBLE

                }
            }

            //Adding card view to container
            binding.containerPopularFoods.addView(popularFoodCardBinding.root)

        }
    }

    private fun floatingButtwonWorking() {
        binding.floatingActionButton2.setOnClickListener { view ->
            val popupMenu = PopupMenu(this, view)
            popupMenu.menuInflater.inflate(R.menu.menu_options, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.englishhhh -> {
                        switchLanguage("en")
                        true
                    }

                    R.id.hindiiii -> {
                        switchLanguage("hi")
                        true
                    }

                    R.id.devContactsss -> {
                        showDeveloperContact()
                        true
                    }

                    else -> false

                }
            }
            popupMenu.show()
        }
    }



    private fun switchLanguage(languageCode: String) {

        LocaleHelper.setLanguage(this, languageCode)
        recreate()
    }

    private fun showDeveloperContact() {
        AlertDialog.Builder(this)
            .setTitle("@string/dev_contacts")
            .setMessage("Email: haneefatwork01@gmail.com\nPhone: +91 9369399872\nPortfolio: www.haneef.tech")
            .setPositiveButton("OK", null)
            .show()
    }
}





