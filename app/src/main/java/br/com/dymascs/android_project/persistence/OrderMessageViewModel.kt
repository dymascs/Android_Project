package br.com.dymascs.android_project.persistence

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.dymascs.android_project.network.SalesApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

private const val TAG = "OrderMessageViewModel"

class OrderMessageViewModel (val order: OrderMessage) : ViewModel() {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val _product = MutableLiveData<ProductMessage>()
    val product: LiveData<ProductMessage>
        get() = _product

    init {
        getProduct()
    }

    private fun getProduct() {
        coroutineScope.launch {
            var getProductDeferred = SalesApi.retrofitService.getProductByCode(order.productCode!!)
            try {
                var productByCode = getProductDeferred.await()
                _product.value = productByCode
            } catch (e: Exception) {
                Log.i(TAG, "Error: ${e.message}")
            }
        }
    }
}