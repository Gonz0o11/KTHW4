package com.example.kthw4_2

import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.os.IBinder

class MyService : Service() {
    companion object {
        var flag: Boolean = false
    }

    private var h = 0
    private var m = 0
    private var s = 0

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        flag = intent?.getBooleanExtra("flag", false) ?: false

        Thread {
            while (flag) {
                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

                s++
                if (s >= 60) {
                    s = 0
                    m++
                    if (m >= 60) {
                        m = 0
                        h++
                    }
                }

                val broadcastIntent = Intent("MyMessage")
                val bundle = Bundle().apply {
                    putInt("H", h)
                    putInt("M", m)
                    putInt("S", s)
                }
                broadcastIntent.putExtras(bundle)
                sendBroadcast(broadcastIntent)
            }
        }.start()

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        // TODO: Return the communication channel to the service.
        throw UnsupportedOperationException("Not yet implemented")
    }
}
