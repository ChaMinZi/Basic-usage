package com.example.basic_usage;

import android.location.Location;
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

    public MutableLiveData<Boolean> getLoadingLiveData() {
        return loadingLiveData;
    }

    public void setLoadingLiveData(MutableLiveData<Boolean> loadingLiveData) {
        this.loadingLiveData = loadingLiveData;
    }

    private MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>();

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    private Location location;

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(MaskService.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build();
    private MaskService service = retrofit.create(MaskService.class);

    public void fetchStoreInfo() {
        // 로딩 시작
        loadingLiveData.setValue(true);

        service.fetchStoreInfo(location.getLongitude(), location.getLongitude()).clone()
                .enqueue(new Callback<StoreInfo>() {
            @Override
            public void onResponse(Call<StoreInfo> call, Response<StoreInfo> response) {
                Log.e(TAG, "onRespose : refresh");
                List<Store> items = response.body().getStores()
                        .stream()
                        .filter(item -> item.getRemainStat() != null)
                        .filter(item -> !item.getRemainStat().equals("empty"))
                        .collect(Collectors.toList());

                for (Store store: items) {
                    double distance = LocationDistance.distance(location.getLatitude(),
                            location.getLongitude(), store.getLat(), store.getLat(), "k");
                    store.setDistance(distance);
                }

                Collections.sort(items);        // 오름차순 정렬
                itemLiveData.postValue(items);

                // 로딩 끝
                loadingLiveData.postValue(false);
            }

            @Override
            public void onFailure(Call<StoreInfo> call, Throwable t) {
                Log.e(TAG, "onFailure : ", t);
                itemLiveData.postValue(Collections.emptyList());

                // 로딩 끝
                loadingLiveData.postValue(false);
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
