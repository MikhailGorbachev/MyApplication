package ru.mg.myapplication.push

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import ru.mg.myapplication.App
import ru.mg.myapplication.R
import ru.mg.myapplication.ui.main.MainActivity
import timber.log.Timber
import javax.inject.Inject

class FirebaseMessagingServiceCustom : FirebaseMessagingService() {

    @Inject
    lateinit var tokenRepository: TokenRepository

    @Inject
    lateinit var fcmTokenRepository: FCMTokenRepository

    override fun onCreate() {
        super.onCreate()

        App.get(applicationContext)
            .getPushComponent()
            .inject(this)
    }


    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Timber.tag(TAG).d(
            "From: ${remoteMessage.from}\nMessage data payload: ${remoteMessage.data}"
        )
        tokenRepository.accessToken?.let { sendNotification(remoteMessage) }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        fcmTokenRepository.updateFCMToken()

        Timber.tag(TAG).d("New token $token")
    }

    private fun sendNotification(remoteMessage: RemoteMessage) {
        val notificationData = remoteMessage.data
        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

            notificationData.forEach { entry ->
                putExtra(entry.key, entry.value)
            }
        }

        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val channelId = getString(R.string.push_order_channel_id)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_logo)
            .setColor(ContextCompat.getColor(applicationContext, R.color.blazeOrange))
            .setContentTitle(remoteMessage.notification?.title ?: notificationData[TITLE_KEY])
            .setContentText(remoteMessage.notification?.body ?: notificationData[BODY_KEY])
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                getString(R.string.push_order_channel_title),
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notificationBuilder.build())
    }

    companion object {

        private const val TITLE_KEY = "title"
        private const val BODY_KEY = "body"
        private const val TAG = "FirebaseMessaging"
    }
}