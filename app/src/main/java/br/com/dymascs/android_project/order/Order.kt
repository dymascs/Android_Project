package br.com.dymascs.android_project.order

import com.google.firebase.firestore.IgnoreExtraProperties


data class Order(
    var username: String,
    var orderId: Int,
    var status: String,
    var productCode: String
)