package com.example.alarmmanager

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity() {
    private var notificationMgr : NotificationManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notificationMgr = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationMgr!!.createNotificationChannel(NotificationChannel("default",
                "기본 채널", NotificationManager.IMPORTANCE_DEFAULT))
        }
    }

    fun createNotification(view: View) {
        show()
    }

    private fun show() {
        var builder = NotificationCompat.Builder(this, "default")

        builder.setSmallIcon(R.mipmap.ic_launcher)
        builder.setContentTitle("알림 제목")
        builder.setContentText("알림 세부 테스트")

        intent = Intent(this, MainActivity::class.java)
        val pendingIntent =
            PendingIntent.getService(this, 0, intent,
                PendingIntent.FLAG_NO_CREATE)

        builder.setContentIntent(pendingIntent)

        val largeIcon = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
        builder.setLargeIcon(largeIcon)
        builder.color = Color.RED

        // 소리 설정
        val ringtonUri = RingtoneManager.getActualDefaultRingtoneUri(this,
            RingtoneManager.TYPE_NOTIFICATION)
        builder.setSound(ringtonUri)

        var vibrate = longArrayOf(0,100,200,300)
        builder.setVibrate(vibrate)
        builder.setAutoCancel(true)

        notificationMgr!!.notify(1, builder.build())
    }

    fun removeNotification(view: View) {
        hide()
    }

    private fun hide() {
        NotificationManagerCompat.from(this).cancel(1)
    }
}