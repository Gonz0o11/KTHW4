package com.example.kthw4_2

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var tvClock: TextView
    private lateinit var btnStart: Button

    private var flag = false

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val bundle = intent?.extras
            bundle?.let {
                tvClock.text = String.format(
                    Locale.getDefault(),
                    "%02d:%02d:%02d",
                    it.getInt("H"),
                    it.getInt("M"),
                    it.getInt("S")
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvClock = findViewById(R.id.tv_clock)
        btnStart = findViewById(R.id.btn_start)

        registerReceiver(receiver, IntentFilter("MyMessage"), Context.RECEIVER_EXPORTED)

        flag = MyService.flag

        btnStart.text = if (flag) "暫停" else "開始"

        btnStart.setOnClickListener {
            flag = !flag

            if (flag) {
                btnStart.text = "暫停"
                Toast.makeText(this, "計時開始", Toast.LENGTH_SHORT).show()
            } else {
                btnStart.text = "開始"
                Toast.makeText(this, "計時暫停", Toast.LENGTH_SHORT).show()
            }

            startService(Intent(this, MyService::class.java).apply {
                putExtra("flag", flag)
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
}
