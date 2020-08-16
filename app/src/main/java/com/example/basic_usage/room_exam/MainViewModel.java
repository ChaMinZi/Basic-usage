package com.example.basic_usage.room_exam;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private AppDatabase db;
    public LiveData<List<Todo>> todos;
    public String newTodo;

    public MainViewModel(@NonNull Application application) {
        super(application);
        db = Room.databaseBuilder(application, AppDatabase.class,
                "todo-db")
                .allowMainThreadQueries()
                .build();
        todos = getAll();
    }

    public LiveData<List<Todo>> getAll() {
        return db.todoDao().getAll();
    }

    public void insert(String todo) {
        db.todoDao().insert(new Todo(todo));
    }
}
