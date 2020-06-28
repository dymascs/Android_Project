package br.com.dymascs.android_project.messaging

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import br.com.dymascs.android_project.MainActivity
import br.com.dymascs.android_project.R
import br.com.dymascs.android_project.order.Order
import br.com.dymascs.android_project.persistence.OrderMessage
import br.com.dymascs.android_project.persistence.OrderRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import java.util.*

private const val TAG = "FCMService"

class FCMService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.d(TAG, "FCM token: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        remoteMessage.data.isNotEmpty().let {
            Log.d(TAG, "Payload: " + remoteMessage.data)
            if (remoteMessage.data.containsKey("orderDetail")) {
                sendOrderNotification(remoteMessage.data.get("orderDetail")!!)
            }
        }
    }

    private fun sendOrderNotification(orderInfo: String) {
        Gson().fromJson(
            orderInfo,
            Order::class.java
        ).let {
            val currentUser = FirebaseAuth.getInstance().currentUser

            if (it.username == currentUser?.email) {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("order", orderInfo)
                sendNotification(intent)

                OrderRepository.saveOrder(
                    OrderMessage(currentUser.uid, it.orderId, it.status, it.productCode, Date().time)
                )
            }
        }
    }

    private fun sendNotification(intent: Intent) {
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val channelId = "1"
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_cloud_queue_black_24dp)
            .setContentTitle("Ta quase chegando!!")
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Sales provider",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(0, notificationBuilder.build())
    }


}
