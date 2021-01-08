package com.example.maskinfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val storeAdapter = StoreAdapter()

        var progressBar: ProgressBar = findViewById(R.id.progressBar)
        findViewById<RecyclerView>(R.id.recycler_view)
            .apply {
                layoutManager = LinearLayoutManager(
                    this@MainActivity,
                    RecyclerView.VERTICAL,
                    false
                )
                adapter = storeAdapter
            }

        viewModel.apply {
            itemLiveData.observe(this@MainActivity, Observer { stores ->
                storeAdapter.updateItems(stores)
            })

            loadingLiveData.observe(this@MainActivity, {
                progressBar.visibility = if (it) View.VISIBLE else View.GONE
            })
        }
    }
}