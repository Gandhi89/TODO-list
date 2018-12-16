package com.example.android.todo_list.Screens;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.example.android.todo_list.R;

public class AddEditNoteActivity extends AppCompatActivity {

    EditText title;
    EditText description;
    NumberPicker numberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        title = findViewById(R.id.addNoteActivity_title);
        description = findViewById(R.id.addNoteActivity_description);
        numberPicker = findViewById(R.id.addNote_numberPacker);
        numberPicker.setMaxValue(10);
        numberPicker.setMinValue(0);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow);

        Intent intent = getIntent();
        if (intent.hasExtra("id")) {
            setTitle("Edit Note");
            title.setText(intent.getStringExtra("title"));
            description.setText(intent.getStringExtra("description"));
            numberPicker.setValue(intent.getIntExtra("priority", 1));
        } else {
            setTitle("Add Note");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.save_note) {
            saveNote();
        }
        return true;
    }

    private void saveNote() {
        String note_title = title.getText().toString();
        String note_description = description.getText().toString();
        int note_priority = numberPicker.getValue();

        Intent intent = new Intent();
        intent.putExtra("note_title", note_title);
        intent.putExtra("note_description", note_description);
        intent.putExtra("note_priority", note_priority);
        int id = getIntent().getIntExtra("id",-1);
        if (id != -1){
            intent.putExtra("id",id);
        }
        setResult(RESULT_OK, intent);
        finish();
    }
}
