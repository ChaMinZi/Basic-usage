package com.example.maskinfo

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maskinfo.model.Store
import com.example.maskinfo.repository.MaskService
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainViewModel @ViewModelInject constructor(
    private val service: MaskService
) : ViewModel() {
    val itemLiveData = MutableLiveData<List<Store>>()
    val loadingLiveData = MutableLiveData<Boolean>()

    init {
        fetchStoreInfo()
    }

    fun fetchStoreInfo() {
        // 로딩 시작
        loadingLiveData.value = true

        viewModelScope.launch {
            val storeInfo = service.fetchStoreInfo(37.188078, 127.043002)
            itemLiveData.value = storeInfo.stores.filter { store ->
                store.remain_stat != null
            }

            // 로딩 끝
            loadingLiveData.value = false
        }
    }
}