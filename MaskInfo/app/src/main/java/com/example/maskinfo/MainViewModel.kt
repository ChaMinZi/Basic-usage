package com.example.maskinfo

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maskinfo.model.Store
import com.example.maskinfo.repository.MaskService
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainViewModel @ViewModelInject constructor(
    private val service: MaskService,
    private val fusedLocationProviderClient: FusedLocationProviderClient
) : ViewModel() {
    val itemLiveData = MutableLiveData<List<Store>>()
    val loadingLiveData = MutableLiveData<Boolean>()

    init {
        fetchStoreInfo()
    }

    @SuppressLint("MissingPermission")
    fun fetchStoreInfo() {
        // 로딩 시작
        loadingLiveData.value = true

        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            viewModelScope.launch {
                val storeInfo = service.fetchStoreInfo(location.latitude, location.longitude)
                itemLiveData.value = storeInfo.stores.filter { store ->
                    store.remain_stat != null
                }

                // 로딩 끝
                loadingLiveData.value = false
            }
        }.addOnFailureListener { exception ->
            loadingLiveData.value = false
        }
    }
}