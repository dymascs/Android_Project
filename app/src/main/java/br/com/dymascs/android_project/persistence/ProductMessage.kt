package br.com.dymascs.android_project.persistence

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
class ProductMessage(
    @Exclude var id: String? = null,
    var userId: String? = null,
    var name: String? = null,
    var description: String? = null,
    var code: String? = null,
    var price: Double? = null
)