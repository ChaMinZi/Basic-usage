package com.example.basic_usage.room_exam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.basic_usage.R;
import com.example.basic_usage.databinding.ActivityRoomBinding;

public class RoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_room); 를 야래와 같이 나타낼 수 있다.
        ActivityRoomBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_room);
        // LiveData도 관찰하면서 xml에 바로 반영되게 할 수 있음
        binding.setLifecycleOwner(this);

        MainViewModel viewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(MainViewModel.class);

        binding.setViewModel(viewModel);
    }
}