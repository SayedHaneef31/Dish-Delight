package com.example.dishdelight.Fragments

import android.R.id.message
import com.example.dishdelight.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.dishdelight.API.RetrofitClient
import com.example.dishdelight.Data.CartItem
import com.example.dishdelight.Data.PaymentItem
import com.example.dishdelight.Data.PaymentRequest
import com.example.dishdelight.Model.CartViewModel
import com.example.dishdelight.databinding.FragmentCartBinding
import com.example.dishdelight.databinding.PopularFoodCardBinding
import kotlinx.coroutines.launch
import kotlin.getValue


class CartFragment : Fragment() {

    private val cartViewModel: CartViewModel by activityViewModels()

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

        cartViewModel.cartItems.observe(viewLifecycleOwner) { cartList ->
            populateCart(cartList)
            updatePaymentSummary(cartList)
        }

        binding.btnPlaceOrder.setOnClickListener {
            val cartList = cartViewModel.cartItems.value ?: emptyList()
            val paymentRequest = preparePaymentRequest(cartList)

            lifecycleScope.launch {
                try {
                    val response = RetrofitClient.api.makePayments(paymentRequest)
                    if (response.isSuccessful) {
                        AlertDialog.Builder(requireContext())
                            .setTitle(HtmlCompat.fromHtml("<b>Order Confirmed ✅</b>", HtmlCompat.FROM_HTML_MODE_LEGACY))
                            .setMessage("🎉 Payment successful!\n\nTransaction Ref:\n${response.body()?.txn_ref_no ?: "N/A"}")
                            .setIcon(R.drawable.mobile_payment) // add a suitable drawable icon in your drawable folder
                            .setPositiveButton("OK") { dialog, _ ->
                                dialog.dismiss()
                            }
                            .setCancelable(false)
                            .show()
                        cartViewModel.clearCart()
                    } else {
                        Toast.makeText(requireContext(), "Payment failed: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun updatePaymentSummary(cartItems: List<CartItem>) {
        val (netTotal, cgst, sgst) = calculateTotals(cartItems)
        val grandTotal = netTotal + cgst + sgst

        binding.tvNetTotal.text = "₹${String.format("%.2f", netTotal)}"
        binding.tvCGST.text = "₹${String.format("%.2f", cgst)}"
        binding.tvSGST.text = "₹${String.format("%.2f", sgst)}"
        binding.tvGrandTotal.text = "₹${String.format("%.2f", grandTotal)}"
    }
    private fun calculateTotals(cartItems: List<CartItem>): Triple<Double, Double, Double> {
        val netTotal = cartItems.sumOf { (it.dish.price?.toDoubleOrNull() ?: 0.0) * it.quantity }
        val cgst = netTotal * 0.025
        val sgst = netTotal * 0.025
        return Triple(netTotal, cgst, sgst)
    }


    private fun preparePaymentRequest(cartItems: List<CartItem>): PaymentRequest {
        val (netTotal, cgst, sgst) = calculateTotals(cartItems)
        val grandTotal = netTotal + cgst + sgst
        val totalItems = cartItems.sumOf { it.quantity }

        val items = cartItems.map {
            PaymentItem(
                cuisine_id = it.dish.id,
                item_id = it.dish.id,
                item_price = it.dish.price ?: "0",
                item_quantity = it.quantity
            )
        }

        return PaymentRequest(
            total_amount = String.format("%.2f", grandTotal),
            total_items = totalItems,
            data = items
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun populateCart(cartItems: List<CartItem>)
    {
        val container = binding.containerCartttFoods
        container.removeAllViews()

        val inflater=LayoutInflater.from(requireContext())

        for (cartItem in cartItems) {
            val dish = cartItem.dish
            val quantity = cartItem.quantity

            val itemBinding = PopularFoodCardBinding.inflate(inflater, container, false)

            // Set dish data
            itemBinding.foodNameIddddd.text = dish.name
            itemBinding.foodRatingIddddd.text = dish.rating?.plus(" ⭐") ?: "N/A"
            itemBinding.foodPriceIddddd.text = "₹${dish.price ?: "N/A"}"

            Glide.with(this)
                .load(dish.image_url)
                .placeholder(R.drawable.salad)
                .error(R.drawable.salad)
                .into(itemBinding.foodImageIdddd)


            itemBinding.quantitySelector.visibility = View.VISIBLE
            itemBinding.btnAddIddddddd.visibility = View.GONE
            itemBinding.quantityIddddd.text = quantity.toString()

            itemBinding.btnPlusIddddd.setOnClickListener {
                val newQty = itemBinding.quantityIddddd.text.toString().toInt() + 1
                itemBinding.quantityIddddd.text = newQty.toString()
                cartViewModel.updateQuantity(dish, newQty)
            }

            itemBinding.btnMinusIdddd.setOnClickListener {
                var currentQty = itemBinding.quantityIddddd.text.toString().toInt()
                if (currentQty > 1) {
                    currentQty--
                    itemBinding.quantityIddddd.text = currentQty.toString()
                    cartViewModel.updateQuantity(dish, currentQty)
                } else {
                    // Remove item from cart and update UI
                    cartViewModel.removeItem(dish)
                }
            }
            itemBinding.btnAddIddddddd.visibility = View.GONE

            container.addView(itemBinding.root)

        }

//    private fun populateCartLocally() {
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
//            binding.containerSelectedFoods.addView(popularFoodCardBinding.root)
//
//        }
    }
}