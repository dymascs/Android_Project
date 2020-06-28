package br.com.dymascs.android_project.persistence

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class OrderMessageViewModelFactory(private val orderMessage: OrderMessage) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OrderMessageViewModel::class.java)) {
            return OrderMessageViewModel(orderMessage) as T
        }
        throw IllegalArgumentException("The OrderDetailViewModel class is required")
    }
}
