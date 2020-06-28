package br.com.dymascs.android_project.persistence

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import br.com.dymascs.android_project.databinding.FragmentOrderMessageBinding

private const val TAG = "OrderMessageFragment"


class OrderMessageFragment : Fragment() {
    private lateinit var binding: FragmentOrderMessageBinding
    lateinit var orderMessage : OrderMessage

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i(TAG, "Creating OrderDetailFragment")
        binding = FragmentOrderMessageBinding.inflate(inflater)
        binding.setLifecycleOwner(this)

        orderMessage = OrderMessageFragmentArgs.fromBundle(requireArguments()).orderMessage
        val productDetailViewModelFactory = orderMessage.let { OrderMessageViewModelFactory(it) }
        binding.orderMessageViewModel = ViewModelProviders.of(
            this, productDetailViewModelFactory
        ).get(OrderMessageViewModel::class.java)

        return binding.root
    }

}