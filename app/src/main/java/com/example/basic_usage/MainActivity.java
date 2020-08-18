package com.example.basic_usage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Switch;

import com.example.basic_usage.model.Store;
import com.example.basic_usage.model.StoreInfo;
import com.example.basic_usage.repository.MaskService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.store_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        final StoreAdapter adapter = new StoreAdapter();
        recyclerView.setAdapter(adapter);

        // UI 변경 감지 업데이트
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.getItemLiveData().observe(this, stores -> {
            adapter.updateItems(stores);
            getSupportActionBar().setTitle("마스크 재고 있는 곳 : "+stores.size());
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_refresh:
                // 리프레시
                viewModel.fetchStoreInfo();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}