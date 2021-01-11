package com.example.maskinfo

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    private val requestPermission = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()){ map ->
        
        // null이 들어오지 않는다고 강제
        if (map[Manifest.permission.ACCESS_FINE_LOCATION]!! ||
                map[Manifest.permission.ACCESS_COARSE_LOCATION]!!) {
            viewModel.fetchStoreInfo()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // permission check
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // 권한 요청하는 부분
            requestPermission.launch(arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ))
            return
        }

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

            loadingLiveData.observe(this@MainActivity) {
                progressBar.visibility = if (it) View.VISIBLE else View.GONE
            }
        }
    }
}