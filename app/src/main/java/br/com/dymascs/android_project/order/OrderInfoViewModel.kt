package br.com.dymascs.android_project.order

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OrderInfoViewModel : ViewModel() {
    val fcmRegistrationId = MutableLiveData<String>()
    val order = MutableLiveData<Order>()
}