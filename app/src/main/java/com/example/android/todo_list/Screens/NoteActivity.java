package com.example.android.todo_list.Screens;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.todo_list.R;
import com.example.android.todo_list.Screens.Adapters.NoteAdapter;
import com.example.android.todo_list.Screens.Entity.Note;
import com.example.android.todo_list.Screens.ViewModel.NoteViewModel;

import java.util.List;

public class NoteActivity extends AppCompatActivity {

    NoteViewModel noteViewModel;
    RecyclerView recyclerView;
    NoteAdapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        recyclerView = findViewById(R.id.noteActivity_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        noteAdapter = new NoteAdapter();
        recyclerView.setAdapter(noteAdapter);

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                // update Recycler View
                noteAdapter.setNotes(notes);
            }
        });
    }
}
