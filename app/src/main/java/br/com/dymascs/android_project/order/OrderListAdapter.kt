package br.com.dymascs.android_project.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.dymascs.android_project.databinding.ItemOrderBinding
import br.com.dymascs.android_project.persistence.OrderMessage
import com.google.firebase.analytics.FirebaseAnalytics


class OrderListAdapter(val onOrderClickListener: OrderClickListener) :
    ListAdapter<OrderMessage, OrderListAdapter.OrderStatusViewHolder>(OrderDiff) {
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderStatusViewHolder {
        firebaseAnalytics = FirebaseAnalytics.getInstance(parent.context)

        return OrderStatusViewHolder(ItemOrderBinding.inflate(LayoutInflater.from(parent.context)))
    }
    override fun onBindViewHolder(holder: OrderStatusViewHolder, position: Int) {
        val order = getItem(position)

        holder.bind(order)

        holder.itemView.setOnClickListener {
            val bundle = Bundle()

            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, order.id)
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM, bundle)

            onOrderClickListener.onClick(order)
        }
    }
    companion object OrderDiff : DiffUtil.ItemCallback<OrderMessage>() {
        override fun areItemsTheSame(oldItem: OrderMessage, newItem: OrderMessage): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: OrderMessage, newItem: OrderMessage): Boolean {
            return ((oldItem.id == newItem.id)
                    && (oldItem.date == newItem.date)
                    && (oldItem.orderId == newItem.orderId)
                    && (oldItem.productCode == newItem.productCode)
                    && (oldItem.status == newItem.status)
                    && (oldItem.userId == newItem.userId))
        }

    }

    class OrderStatusViewHolder(private var binding: ItemOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(order: OrderMessage) {
            binding.order = order
            binding.root.setOnClickListener {

            }
            binding.executePendingBindings()
        }
    }

    class OrderClickListener(val clickListener: (order: OrderMessage) -> Unit) {
        fun onClick(order: OrderMessage) = clickListener(order)
    }
}