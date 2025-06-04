package com.example.dishdelight.Model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dishdelight.Data.CartItem
import com.example.dishdelight.Data.Dish

class CartViewModel: ViewModel()
{
    private val _cartItems = MutableLiveData<MutableList<CartItem>>(mutableListOf())
    val cartItems: LiveData<MutableList<CartItem>> get() = _cartItems


    fun addItem(dish: Dish, quantity: Int) {
        val currentList = _cartItems.value ?: mutableListOf()
        val existingItem = currentList.find { it.dish.id == dish.id }
        if (existingItem != null) {
            existingItem.quantity += quantity
        } else {
            currentList.add(CartItem(dish, quantity))
        }
        _cartItems.value = currentList
    }

    fun updateQuantity(dish: Dish, quantity: Int) {
        val currentList = _cartItems.value ?: mutableListOf()
        val item = currentList.find { it.dish.id == dish.id }
        if (item != null) {
            if (quantity <= 0) {
                currentList.remove(item)
            } else {
                item.quantity = quantity
            }
            _cartItems.value = currentList
        }
    }

    fun removeItem(dish: Dish) {
        val currentList = _cartItems.value ?: mutableListOf()
        currentList.removeAll { it.dish.id == dish.id }
        _cartItems.value = currentList
    }

    fun clearCart() {
        _cartItems.value = mutableListOf()
    }



}