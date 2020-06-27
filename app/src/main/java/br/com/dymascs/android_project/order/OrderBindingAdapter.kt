package br.com.dymascs.android_project.order

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("orderId")
fun bindProductCode(txtOrderId: TextView, orderId: Int?) {
    orderId?.let {
        txtOrderId.text = "$orderId"
    }
}