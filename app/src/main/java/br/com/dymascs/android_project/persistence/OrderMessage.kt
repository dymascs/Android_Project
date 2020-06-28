package br.com.dymascs.android_project.persistence

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties
import java.io.Serializable

@IgnoreExtraProperties
data class OrderMessage(
    var userId: String? = null,
    var orderId: Int? = null,
    var status: String? = null,
    var productCode: String? = null,
    var date: Long? = null
) : Serializable
{
    @Exclude
    var id: String? = null
}