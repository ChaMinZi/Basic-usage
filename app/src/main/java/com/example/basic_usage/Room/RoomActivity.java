package com.example.basic_usage.Room;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.basic_usage.R;

import java.util.List;

public class RoomActivity extends AppCompatActivity {
    private EditText mTodoEditText;
    private TextView mResultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        mTodoEditText = findViewById(R.id.todo_edit);
        mResultTextView = findViewById(R.id.result_text);

        /**
         * main Thread에서 db 작업은 할 수 없다.
         * allowMainThreadQueries 옵션은 임시방편이다.
         * 만약 db 작업량이 많은 경우 main Thread를 사용하면 화면 draw 과정이 느려질 수 있다.
         **/
        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class,
                "todo-db")
                .allowMainThreadQueries()
                .build();

        /**
         * getAll()을 했을 때 변경된 인자가 todos로 들어온다.
         * Lambda 사용
         * project struct -> modules에서 target version을 1.8로 하면 람다식 사용 가능
         *
         * Live data 사용시
         * 변경 사항이 있는지 관찰하다가 변경되면 알아서 UI 갱신
         **/
        db.todoDao().getAll().observe(this, todos -> {
            mResultTextView.setText(todos.toString());
        });

        // button 클릭 시 DB에 insert
        findViewById(R.id.add_button).setOnClickListener(view -> {
            db.todoDao().insert(new Todo(mTodoEditText.getText().toString()));
        });
    }
}