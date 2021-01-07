package com.example.datatransfer

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.os.bundleOf

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        var button = findViewById<Button>(R.id.button3)
        button.setOnClickListener {
            val intent = Intent()
            intent.putExtra("data","hello")
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}