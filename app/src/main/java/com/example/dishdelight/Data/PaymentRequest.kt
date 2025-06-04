package com.example.dishdelight.Data

data class PaymentRequest(
    val total_amount: String,
    val total_items: Int,
    val data: List<PaymentItem>
)
