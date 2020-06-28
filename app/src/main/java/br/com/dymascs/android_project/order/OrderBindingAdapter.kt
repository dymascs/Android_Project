package br.com.dymascs.android_project.order

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.dymascs.android_project.persistence.OrderMessage
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("orderId")
fun bindProductCode(txtOrderId: TextView, orderId: Int?) {
    orderId?.let {
        txtOrderId.text = "$orderId"
    }
}

@BindingAdapter("orderId")
fun bindOrderCode(tvOrderId: TextView, orderId: Int?) {
    orderId?.let {
        tvOrderId.text = "$orderId"
    }
}

@BindingAdapter("orderId")
fun bindOrderId(tvOrderId: TextView, orderId: Int?) {
    orderId?.let {
        tvOrderId.text = orderId.toString()
    }
}

@BindingAdapter("orderDate")
fun bindOrderDate(tvOrderDate: TextView, orderDate: Long?) {
    orderDate?.let {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd\nhh:mm", Locale("pt_BR"))
        tvOrderDate.text = dateFormat.format(Date(orderDate))
    }
}

@BindingAdapter("productPrice")
fun bindProductPrice(txtProductPrice: TextView, productPrice: Double?) {
    productPrice?.let {
        val price = "$ " + "%.2f".format(productPrice)
        txtProductPrice.text = price
    }
}

@BindingAdapter("ordersList")
fun bindProductsList(recyclerView: RecyclerView, orders: List<OrderMessage>?) {
    val adapter = recyclerView.adapter as OrderListAdapter
    adapter.submitList(orders)
}