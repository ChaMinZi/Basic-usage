package com.example.basic_usage;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.basic_usage.model.Store;
import com.example.basic_usage.model.StoreInfo;
import com.example.basic_usage.repository.MaskService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class MainViewModel extends ViewModel {
    private static final String TAG = MainViewModel.class.getSimpleName();
    private MutableLiveData<List<Store>> itemLiveData = new MutableLiveData<>();

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(MaskService.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build();
    private MaskService service = retrofit.create(MaskService.class);

    private Call<StoreInfo> storeInfoCall = service.fetchStoreInfo();

    public MainViewModel() {
        fetchStoreInfo();
    }

    public void fetchStoreInfo() {
        storeInfoCall.clone().enqueue(new Callback<StoreInfo>() {
            @Override
            public void onResponse(Call<StoreInfo> call, Response<StoreInfo> response) {
                Log.e(TAG, "onRespose : refresh");
                List<Store> items = response.body().getStores()
                        .stream().filter(item -> item.getRemainStat() != null)
                        .collect(Collectors.toList());

                itemLiveData.postValue(items);
            }

            @Override
            public void onFailure(Call<StoreInfo> call, Throwable t) {
                Log.e(TAG, "onFailure : ", t);
                itemLiveData.postValue(Collections.emptyList());
            }
        });
    }

    public MutableLiveData<List<Store>> getItemLiveData() {
        return itemLiveData;
    }

    public void setItemLiveData(MutableLiveData<List<Store>> itemLiveData) {
        this.itemLiveData = itemLiveData;
    }
}
