package com.example.dishdelight.Data

data class PaymentResponse(
    val response_code: Int,
    val outcome_code: Int,
    val response_message: String,
    val txn_ref_no: String?
)
