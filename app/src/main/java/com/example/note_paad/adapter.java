package com.example.note_paad;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class adapter extends RecyclerView.Adapter<adapter.holder> {
    private List<note_modle> notes;

    public adapter(List<note_modle> notes) {
        this.notes = notes;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.items,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        holder.set_note(notes.get(position));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class holder extends RecyclerView.ViewHolder{
        TextView title ,subtitle ,time;
        holder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textTitle);
            subtitle = itemView.findViewById(R.id.textSubtitle);
            time = itemView.findViewById(R.id.textDateTime);
        }
        void set_note(note_modle note){
            title.setText(note.getTitle());
            if (note.getSubtitle().trim().isEmpty()){
                subtitle.setVisibility(View.GONE);
            }else {
                subtitle.setText(note.getSubtitle());
            }
            time.setText(note.getTime());
        }
    }
}
