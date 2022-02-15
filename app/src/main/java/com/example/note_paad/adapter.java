package com.example.note_paad;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class adapter extends RecyclerView.Adapter<adapter.holder> {
    private ArrayList<note_modle> notes;


    public adapter(ArrayList<note_modle> notes) {
        this.notes = notes;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.items,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        SharedPreferences sharedPref = ((Activity)holder.itemView.getContext()).getPreferences(Context.MODE_PRIVATE);
        if (sharedPref.getInt("theme",-1)==1){
            //linearLayout.setBackgroundColor(dark.getItem());
            holder.layoutNote.setBackgroundResource(R.drawable.search_background);
            holder.subtitle.setTextColor(MainActivity.light.text_color);
            holder.time.setTextColor(MainActivity.light.text_color);
            holder.title.setTextColor(MainActivity.light.text_color);

        }else if (sharedPref.getInt("theme",-1)==2){//go to light
            holder.layoutNote.setBackgroundResource(R.drawable.search_background_dark);
            holder.subtitle.setTextColor(MainActivity.dark.text_color);
            holder.time.setTextColor(MainActivity.dark.text_color);
            holder.title.setTextColor(MainActivity.dark.text_color);

        }
        holder.set_note(notes.get(position));
        holder.layoutNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),show_note.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("index",position);
                Activity activity = (Activity) view.getContext();
                activity.finish();
                view.getContext().startActivity(intent);
            }
        });
        holder.layoutNote.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(view.getContext(), "delete", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        Log.i("fdfdfd",position+"");
    }

    @Override
    public int getItemCount() {
        Log.i("fdfdfd","getItemCount");

        return notes.size();
    }

    @Override
    public int getItemViewType(int position) {
        Log.i("fdfdfd","getItemViewType");

        return position;

    }
    public void n_() {
        notes = new ArrayList<note_modle>();

        //this.messages.add(new Message("salam"));
        notifyDataSetChanged();
        // to render the list we need to notify
    }

    static class holder extends RecyclerView.ViewHolder{
        TextView title ,subtitle ,time;
        ImageView imageView;
        LinearLayout layoutNote;

        holder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textTitle);
            subtitle = itemView.findViewById(R.id.textSubtitle);
            time = itemView.findViewById(R.id.textDateTime);
            imageView = itemView.findViewById(R.id.image);
            layoutNote = itemView.findViewById(R.id.layoutNote);

        }
        void set_note(note_modle note){
            title.setText(note.getTitle());
            if (note.getSubtitle().trim().isEmpty()){
                subtitle.setVisibility(View.GONE);
            }else {
                subtitle.setText(note.getSubtitle());
            }
            time.setText(note.getTime());
            if (note.getImage()!=null){
                Bitmap bitmap = BitmapFactory.decodeByteArray(note.getImage(), 0, note.getImage().length);
                imageView.setImageBitmap(bitmap);
            }else if (note.getDraw()!=null){
                Bitmap bitmap = BitmapFactory.decodeByteArray(note.getDraw(), 0, note.getDraw().length);
                imageView.setImageBitmap(bitmap);
            }

        }
    }
}
