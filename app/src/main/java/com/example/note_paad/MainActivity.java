package com.example.note_paad;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.View;



import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    NoteDataAccess access = new NoteDataAccess(this);
    private RecyclerView recyclerView;
   // private AppBarConfiguration appBarConfiguration;
    //private ActivityMainBinding binding;
    FloatingActionButton add_btn;
    public static ArrayList<note_modle> notes;
    adapter ada ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_main);
        add_btn= findViewById(R.id.fab);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),CreatNoteActivity.class);
                startActivity(intent);
                finish();
            }
        });
        recyclerView = findViewById(R.id.notesRecyclerView);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));///////
        notes= new ArrayList<>();
        //setSupportActionBar(binding.toolbar);

//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        access.openDB();
        //access.addNewNote(new note_modle("2","text",new Date().getTime()+"","title","sub",null));
        notes = access.getall();
         ada = new adapter(notes);
         recyclerView.setAdapter(ada);
        access.closeDB();
//        binding.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Log.i("TAG", "onOptionsItemSelected: ");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}