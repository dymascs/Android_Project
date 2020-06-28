package br.com.dymascs.android_project.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.dymascs.android_project.persistence.OrderMessage
import br.com.dymascs.android_project.persistence.OrderRepository

class OrderListViewModel : ViewModel() {

    var _orderMessage = MutableLiveData<List<OrderMessage>>()
    val listOrders: LiveData<List<OrderMessage>>
        get() = _orderMessage

    init {
        getOrders()
    }

    fun getOrders() {
        _orderMessage = OrderRepository.getOrders()
    }
}