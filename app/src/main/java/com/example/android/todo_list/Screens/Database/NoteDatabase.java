package com.example.android.todo_list.Screens.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.android.todo_list.Screens.Dao.NoteDao;
import com.example.android.todo_list.Screens.Entity.Note;

@Database(entities = Note.class,version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    public abstract NoteDao noteDao();

    private static NoteDatabase instance;

    public static NoteDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context,NoteDatabase.class,"note_database")
                    .build();
        }
        return instance;
    }
}
