package com.example.android.todo_list.Screens;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.android.todo_list.R;
import com.example.android.todo_list.Screens.Adapters.NoteAdapter;
import com.example.android.todo_list.Screens.Entity.Note;
import com.example.android.todo_list.Screens.ViewModel.NoteViewModel;

import java.util.List;

public class NoteActivity extends AppCompatActivity implements View.OnClickListener, NoteAdapter.onItemClickListner {

    NoteViewModel noteViewModel;
    RecyclerView recyclerView;
    NoteAdapter noteAdapter;
    FloatingActionButton floatingActionButton;

    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        floatingActionButton = findViewById(R.id.noteActivity_floatingButton);
        recyclerView = findViewById(R.id.noteActivity_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        noteAdapter = new NoteAdapter();
        recyclerView.setAdapter(noteAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                noteViewModel.delete(noteAdapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(NoteActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                // update Recycler View
                noteAdapter.setNotes(notes);
            }
        });

        floatingActionButton.setOnClickListener(this);
        noteAdapter.setOnItemClickListner(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_delete_all,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.notes_delete){
            noteViewModel.deleteAll();
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.noteActivity_floatingButton:
                Intent intent = new Intent(NoteActivity.this,AddEditNoteActivity.class);
                startActivityForResult(intent,ADD_NOTE_REQUEST);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK){
            String note_title = data.getStringExtra("note_title");
            String note_description = data.getStringExtra("note_description");
            int note_priority = data.getIntExtra("note_priority",0);

            noteViewModel.insert(new Note(note_title,note_description,note_priority));

            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
        }
        else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK){
            String note_title = data.getStringExtra("note_title");
            String note_description = data.getStringExtra("note_description");
            int note_priority = data.getIntExtra("note_priority",0);
            int note_id = data.getIntExtra("id",0);

            Note note = new Note(note_title,note_description,note_priority);
            note.setId(note_id);
            noteViewModel.update(note);

            Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(Note note) {
        Intent intent = new Intent(NoteActivity.this,AddEditNoteActivity.class);
        intent.putExtra("id",note.getId());
        intent.putExtra("title",note.getTitle());
        intent.putExtra("description",note.getDescription());
        intent.putExtra("priority",note.getPriority());
        startActivityForResult(intent,EDIT_NOTE_REQUEST);

    }
}
