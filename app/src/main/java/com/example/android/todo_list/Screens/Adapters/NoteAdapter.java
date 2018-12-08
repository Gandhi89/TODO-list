package com.example.android.todo_list.Screens.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.todo_list.R;
import com.example.android.todo_list.Screens.Entity.Note;

import java.util.ArrayList;
import java.util.List;



public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    List<Note> notes = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.note_item,viewGroup,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(i);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<Note> notes){
        this.notes = notes;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView description;
        TextView priority;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.note_item_title);
            description = itemView.findViewById(R.id.note_item_description);
            priority = itemView.findViewById(R.id.note_item_priority);
        }
        public void bind(int position){
            title.setText(notes.get(position).getTitle());
            description.setText(notes.get(position).getDescription());
            priority.setText(String.valueOf(notes.get(position).getPriority()));
        }
    }

}
