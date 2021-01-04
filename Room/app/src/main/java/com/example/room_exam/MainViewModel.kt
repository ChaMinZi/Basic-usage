package com.example.room_exam

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {
    private val db = Room.databaseBuilder(
        application,
        AppDatabase::class.java, "database-todo"
    ).build()

    var todos: LiveData<List<Todo>>
    var newTodo: String? = null

    init {
        todos = getAll()
    }

    fun getAll(): LiveData<List<Todo>> {
        return db.todoDao().getAll()
    }

    // suspend 키워드가 붙으면 무조건 코루틴 스코프
    fun insert(todo: String) {
        viewModelScope.launch(Dispatchers.IO) {
            db.todoDao().insert(Todo(todo))
        }
    }
}