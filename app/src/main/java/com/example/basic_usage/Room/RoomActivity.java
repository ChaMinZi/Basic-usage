package com.example.basic_usage.Room;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.basic_usage.R;

public class RoomActivity extends AppCompatActivity {
    private EditText mTodoEditText;
    private TextView mResultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        mTodoEditText = findViewById(R.id.todo_edit);
        mResultTextView = findViewById(R.id.result_text);

        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class,
                "todo-db")
                .allowMainThreadQueries()
                .build();

        mResultTextView.setText(db.todoDao().getAll().toString());

        findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.todoDao().insert(new Todo(mTodoEditText.getText().toString()));
                mResultTextView.setText(db.todoDao().getAll().toString());
            }
        });
    }
}