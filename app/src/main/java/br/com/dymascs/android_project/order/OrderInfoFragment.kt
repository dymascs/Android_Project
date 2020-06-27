package br.com.dymascs.android_project.order

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import br.com.dymascs.android_project.databinding.FragmentOrderInfoBinding
import com.google.firebase.iid.FirebaseInstanceId
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

class OrderInfoFragment : Fragment() {
    private val orderInfoViewModel: OrderInfoViewModel by lazy {
        ViewModelProviders.of(this).get(OrderInfoViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentOrderInfoBinding.inflate(inflater)
        binding.setLifecycleOwner(this)
        binding.orderInfoViewModel = orderInfoViewModel

        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    orderInfoViewModel.fcmRegistrationId.value = task.result?.token
                    Log.i("OrderInfoFragment", "FCM Token: ${task.result?.token}")
                }
            }
        if (this.arguments != null) {
            if (this.requireArguments().containsKey("orderInfo")) {
                val moshi = Moshi.Builder().build()
                val jsonAdapter: JsonAdapter<Order> =
                    moshi.adapter<Order>(Order::class.java)
                jsonAdapter.fromJson(this.requireArguments().getString("orderInfo")!!).let {
                    orderInfoViewModel.order.value = it
                }
            }
        }

        return binding.root
    }
}