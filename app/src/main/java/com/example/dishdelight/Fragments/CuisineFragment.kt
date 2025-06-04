package com.example.dishdelight.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.dishdelight.API.RetrofitClient
import com.example.dishdelight.Data.CuisineRequest
import com.example.dishdelight.Data.Dish
import com.example.dishdelight.Model.CartViewModel
import com.example.dishdelight.R
import com.example.dishdelight.databinding.DishesBinding
import com.example.dishdelight.databinding.FragmentCuisineBinding
import kotlinx.coroutines.launch


class CuisineFragment : Fragment() {

    private val cartViewModel: CartViewModel by activityViewModels()

    private var _binding: FragmentCuisineBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentCuisineBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Initialize UI here, e.g. set click listeners or start API calls


        val cuisineName=arguments?.getString("cuisine_name")
        val cuisineId=arguments?.getString("cuisine_id")

        println("Cuisine Name: $cuisineName")

        lifecycleScope.launch {
            try {
                cuisineName?.let { name ->
                    val request = CuisineRequest(listOf(name))
                    val response= RetrofitClient.api.getItemsByFilter(request)

                    if (response.isSuccessful) {
                        val dishes = response.body()?.cuisines?.flatMap { it.items } ?: emptyList()
                        populateDishes(dishes)
                    } else {
                        Toast.makeText(requireContext(), "Failed: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun populateDishes(dishes: List<Dish>) {
        binding.containerSelectedCuisines.removeAllViews()
        val inflater=layoutInflater

        for (dish in dishes) {
            val dishBinding = DishesBinding.inflate(inflater, binding.containerSelectedCuisines, false)

            dishBinding.foodNameIddddd.text = dish.name
            dishBinding.foodRatingIddddd.text = dish.rating?.plus(" ⭐") ?: "N/A"
            dishBinding.foodPriceIddddd.text = "₹ ${dish.price ?: "N/A"}"

            Glide.with(this)
                .load(dish.image_url)
                .placeholder(R.drawable.salad) // Use your placeholder image
                .error(R.drawable.salad)
                .into(dishBinding.foodImageIdddd)

            dishBinding.btnAddIddddddd.setOnClickListener {
                dishBinding.quantitySelector.visibility = View.VISIBLE
                dishBinding.btnAddIddddddd.visibility = View.GONE
                dishBinding.quantityIddddd.text = "1"
                cartViewModel.addItem(dish, 1)

            }

            dishBinding.btnPlusIddddd.setOnClickListener {
                var quantity = dishBinding.quantityIddddd.text.toString().toInt()
                quantity++
                dishBinding.quantityIddddd.text = quantity.toString()

                cartViewModel.updateQuantity(dish, quantity)
            }

            dishBinding.btnMinusIdddd.setOnClickListener {
                var quantity = dishBinding.quantityIddddd.text.toString().toInt()
                if (quantity > 1) {
                    quantity--
                    dishBinding.quantityIddddd.text = quantity.toString()
                    cartViewModel.updateQuantity(dish, quantity)
                } else {
                    dishBinding.quantitySelector.visibility = View.GONE
                    dishBinding.btnAddIddddddd.visibility = View.VISIBLE
                    cartViewModel.removeItem(dish)
                }
            }

            binding.containerSelectedCuisines.addView(dishBinding.root)
        }
    }
//    private fun addToCart(dish: Dish, quantity: Int) {
//        var existingItem = cartItems.find { it.dish.id == dish.id }
//        if(existingItem==null)
//        {
//            cartItems.add(CartItem(dish, quantity))
//        }
//        else
//        {
//            existingItem.quantity = quantity
//
//        }
//    }
//
//    private fun updateCartQuantity(dish: Dish, newQuantity: Int) {
//        val existingItem = cartItems.find { it.dish.id == dish.id }
//        existingItem?.quantity = newQuantity
//    }
//
//    private fun removeFromCart(dish: Dish) {
//        cartItems.removeIf { it.dish.id == dish.id }
//    }


    private fun poplateSelectedCuisine() {
//            repeat(3) {
//            val cuisineListBinding = DishesBinding.inflate(layoutInflater)
//
//            cuisineListBinding.foodNameIddddd.text="Green Salad"
//            cuisineListBinding.foodRatingIddddd.text="4.1 ⭐"
//            cuisineListBinding.foodPriceIddddd.text="₹ 349"
//
//            cuisineListBinding.btnAddIddddddd.setOnClickListener {
//                cuisineListBinding.quantitySelector.visibility = View.VISIBLE
//                cuisineListBinding.btnAddIddddddd.visibility = View.GONE
//                cuisineListBinding.quantityIddddd.text="1"
//            }
//
//            cuisineListBinding.btnPlusIddddd.setOnClickListener {
//                var quantity = cuisineListBinding.quantityIddddd.text.toString().toInt()
//                quantity++
//                cuisineListBinding.quantityIddddd.text = quantity.toString()
//            }
//
//            cuisineListBinding.btnMinusIdddd.setOnClickListener {
//                var quantity = cuisineListBinding.quantityIddddd.text.toString().toInt()
//                if(quantity>1){
//                    quantity--
//                    cuisineListBinding.quantityIddddd.text = quantity.toString()
//                }
//                else
//                {
//                    cuisineListBinding.quantitySelector.visibility = View.GONE
//                    cuisineListBinding.btnAddIddddddd.visibility = View.VISIBLE
//
//                }
//            }
//
//            //Adding card view to container
//            binding.containerSelectedCuisines.addView(cuisineListBinding.root)
//
//        }
    }

}