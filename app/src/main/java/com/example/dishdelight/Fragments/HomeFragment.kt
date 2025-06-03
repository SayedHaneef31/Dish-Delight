package com.example.dishdelight.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.dishdelight.API.PageCountRequest
import com.example.dishdelight.API.RetrofitClient
import com.example.dishdelight.CuisineActivity
import com.example.dishdelight.Data.CartItem
import com.example.dishdelight.Data.Dish
import com.example.dishdelight.LocaleHelper
import com.example.dishdelight.R
import com.example.dishdelight.databinding.CuisineCardBinding
import com.example.dishdelight.databinding.FragmentHomeBinding
import com.example.dishdelight.databinding.PopularFoodCardBinding
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private val cartItems = mutableListOf<CartItem>()
    private var _binding: FragmentHomeBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Initialize UI here, e.g. set click listeners or start API calls


        populateCuisineCategory()

        popluateFamousFood()



        floatingButtwonWorking()

        enableCuisineNavigation()








        binding.horizontalScrollContainer.setOnClickListener { view ->
            startActivity(Intent(requireActivity(), CuisineActivity::class.java))
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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

                            Glide.with(requireActivity())
                                .load(cuisine.cuisine_image_url)
                                .placeholder(R.drawable.broccoli)   // optional placeholder while loading
                                .error(R.drawable.broccoli)         // optional error image if load fails
                                .into(cuisineCardBinding.imageView)

                            container.addView(cuisineCardBinding.root)

                        }

                    }

                }
                else{
                    Toast.makeText(requireActivity(),"Failed: ${response.code()}", Toast.LENGTH_LONG).show()
                }
            }
            catch (e: Exception)
            {
                e.printStackTrace()
                Toast.makeText(requireActivity(),"Failed: ${e.message}", Toast.LENGTH_LONG).show()
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




    private fun popluateFamousFood() {

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.getCuisineList(PageCountRequest(3,10))

                if(response.isSuccessful)
                {
                    val cuisineResponse  = response.body()
                    cuisineResponse?.let {
                        val allDishes=it.cuisines.flatMap { cuisine -> cuisine.items }

                        val sortedDish=allDishes.sortedByDescending { dish -> dish.rating?.toDoubleOrNull()?:0.0 }

                        val topDishes=sortedDish.take(3)

                        showTopThreeDishes(topDishes)
                    }
                }
                else{
                    Toast.makeText(requireActivity(),"Failed: ${response.code()}", Toast.LENGTH_LONG).show()
                }
            }
            catch (e: Exception){
                e.printStackTrace()
                Toast.makeText(requireActivity(),"Failed: ${e.message}", Toast.LENGTH_LONG).show()

            }
        }
    }

    private fun showTopThreeDishes(topDishes: List<Dish>) {
        val container = binding.containerPopularFoods
        container.removeAllViews()
        for (dish in topDishes) {
            val popularFoodCardBinding = PopularFoodCardBinding.inflate(layoutInflater, container, false)

            popularFoodCardBinding.foodNameIddddd.text=dish.name
            popularFoodCardBinding.foodRatingIddddd.text=dish.rating+" ⭐"
            popularFoodCardBinding.foodPriceIddddd.text="₹"+dish.price

            Glide.with(requireActivity())
                .load(dish.image_url)
                .placeholder(R.drawable.salad)
                .error(R.drawable.salad)
                .into(popularFoodCardBinding.foodImageIdddd)

            popularFoodCardBinding.btnAddIddddddd.setOnClickListener {
                popularFoodCardBinding.quantitySelector.visibility = View.VISIBLE
                popularFoodCardBinding.btnAddIddddddd.visibility = View.GONE
                popularFoodCardBinding.quantityIddddd.text = "1"

                // Add dish to cart with quantity 1
                addToCart(dish, 1)
            }

            popularFoodCardBinding.btnPlusIddddd.setOnClickListener {
                var quantity = popularFoodCardBinding.quantityIddddd.text.toString().toInt()
                quantity++
                popularFoodCardBinding.quantityIddddd.text = quantity.toString()

                updateCartQuantity(dish, quantity)
            }

            popularFoodCardBinding.btnMinusIdddd.setOnClickListener {
                var quantity = popularFoodCardBinding.quantityIddddd.text.toString().toInt()
                if (quantity > 1) {
                    quantity--
                    popularFoodCardBinding.quantityIddddd.text = quantity.toString()
                    updateCartQuantity(dish, quantity)
                } else {
                    popularFoodCardBinding.quantitySelector.visibility = View.GONE
                    popularFoodCardBinding.btnAddIddddddd.visibility = View.VISIBLE

                    removeFromCart(dish)
                }
            }

            container.addView(popularFoodCardBinding.root)
        }
    }

    private fun addToCart(dish: Dish, quantity: Int) {
        var existingItem = cartItems.find { it.dish.id == dish.id }
        if(existingItem==null)
        {
            cartItems.add(CartItem(dish, quantity))
        }
        else
        {
            existingItem.quantity = quantity

        }
    }

    private fun updateCartQuantity(dish: Dish, newQuantity: Int) {
        val existingItem = cartItems.find { it.dish.id == dish.id }
        existingItem?.quantity = newQuantity
    }

    private fun removeFromCart(dish: Dish) {
        cartItems.removeIf { it.dish.id == dish.id }
    }


    private fun floatingButtwonWorking() {
        binding.floatingActionButton2.setOnClickListener { view ->
            val popupMenu = PopupMenu(requireActivity(), view)
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
        val activity = requireActivity()
        LocaleHelper.setLanguage(activity, languageCode)
        activity.recreate()
    }

    private fun showDeveloperContact() {
        AlertDialog.Builder(requireActivity())
            .setTitle("@string/dev_contacts")
            .setMessage("Email: haneefatwork01@gmail.com\nPhone: +91 9369399872\nPortfolio: www.haneef.tech")
            .setPositiveButton("OK", null)
            .show()
    }
}