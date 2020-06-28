package br.com.dymascs.android_project.persistence

import  android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject

private const val TAG = "OrderRepository"
private const val COLLECTION = "orders"
private const val FIELD_USER_ID = "userId"
private const val FIELD_DATE = "date"
private const val FIELD_ORDER_ID = "orderId"
private const val FIELD_STATUS = "status"
private const val FIELD_PRODUCT_CODE = "productCode"

object OrderRepository {
    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    private val firebaseFirestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }
    fun saveOrder(order: OrderMessage): String {
        val document = if (order.id != null) {
            firebaseFirestore.collection(COLLECTION).document(order.id!!)
        } else {
            order.userId = firebaseAuth.getUid()!!
            firebaseFirestore.collection(COLLECTION).document()
        }
        document.set(order)
        return document.id
    }
    fun deleteOrder(orderId: String) {
        val document = firebaseFirestore.collection(COLLECTION).document(orderId)
        document.delete()
    }
    fun getOrders(): MutableLiveData<List<OrderMessage>> {
        val liveOrder = MutableLiveData<List<OrderMessage>>()
        firebaseFirestore.collection(COLLECTION)
            .whereEqualTo(FIELD_USER_ID, firebaseAuth.uid)
            .orderBy(FIELD_DATE, Query.Direction.ASCENDING)
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException != null) {
                    Log.w(TAG, "Listen failed.", firebaseFirestoreException)
                    return@addSnapshotListener
                }
                if (querySnapshot != null && !querySnapshot.isEmpty) {
                    val orders = ArrayList<OrderMessage>()
                    querySnapshot.forEach {
                        val order = it.toObject<OrderMessage>()
                        order.userId = it.id
                        orders.add(order)
                    }
                    liveOrder.postValue(orders)
                } else {
                    Log.d(TAG, "No Order has been found")
                }
            }
        return liveOrder
    }

}