package br.com.dymascs.android_project.order

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import br.com.dymascs.android_project.databinding.FragmentOrderListBinding

private const val TAG = "OrderListFragment"

class OrderListFragment : Fragment() {
    private val orderListViewModel: OrderListViewModel by lazy {
        ViewModelProviders.of(this).get(OrderListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i(TAG, "Creating OrdersListFragment")
        val binding = FragmentOrderListBinding.inflate(inflater)
        binding.setLifecycleOwner(this)
        binding.orderListViewModel = orderListViewModel
        val itemDecor = DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL)
        binding.listOrders.addItemDecoration(itemDecor);
        binding.listOrders.adapter = OrderListAdapter(OrderListAdapter.OrderClickListener {
//                Log.i(TAG, "Order selected: ${it.orderId}")
//                this.findNavController()
//                    .navigate(actionShowOrderDetail(it))
        })

        return binding.root
    }

}