package com.example.dishdelight.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dishdelight.databinding.FragmentCartBinding
import com.example.dishdelight.databinding.PopularFoodCardBinding


class CartFragment : Fragment() {


    private var _binding: FragmentCartBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentCartBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Initialize UI here, e.g. set click listeners or start API calls

        populateCart()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun populateCart() {
        repeat(10) {
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
            binding.containerSelectedFoods.addView(popularFoodCardBinding.root)

        }
    }
}