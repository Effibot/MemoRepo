package com.effirossimotoi.memome.alfa;

import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.effirossimotoi.memome.alfa.Database.AppDatabase;
import com.effirossimotoi.memome.alfa.Note;
import com.effirossimotoi.memome.alfa.RecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static List<String> lista;
    private static AppDatabase db;
    private static List<Note> notes;
    private static RecyclerView recyclerView;
    private static RecyclerViewAdapter adapter;
    private static Context baseContext;
    public static View viewLayout;

    //TODO DEBUG
    private FloatingActionButton deleteAllFab;
    /////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        viewLayout = findViewById(R.id.view_layout);
        baseContext = MainActivity.this;    // baro perché mi servirà statico per aggiornare adapter

        // controllo che entro per la prima votla nella onCreate
        if(savedInstanceState == null){
            db = Room.databaseBuilder(MainActivity.this, AppDatabase.class,"note_database")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
            MainActivity.adapterNotifyAll();
            sortNotes(2);
        }

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RecyclerViewAdapter(notes, MainActivity.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // uso tag 'a' per add
               startActivity(EditActivity.getEditIntent(MainActivity.this,'a'));
             }
        });

        deleteAllFab = findViewById(R.id.deleteAllFab);
        deleteAllFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.noteDAO().deleteAll();
                MainActivity.adapterNotifyAll();
            }
        });

    }

    public static void adapterNotifyAll() {
        notes = db.noteDAO().getAll();
        recyclerView = viewLayout.findViewById(R.id.recyclerView);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(baseContext);
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RecyclerViewAdapter(notes, baseContext);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    public static void sendObject(Context context, int pos){
        context.startActivity(EditActivity.getEditIntent(context,'e',notes.get(pos)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setInputType(InputType.TYPE_TEXT_VARIATION_FILTER);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.sortAz:
                sortNotes(0);
                break;
            case R.id.sortZa:
                sortNotes(1);
                break;
            case R.id.sortMod:
                sortNotes(2);
                break;
            case R.id.sortModInv:
                sortNotes(3);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void sortNotes(int i){
        switch (i){
            case 0:
                Collections.sort(notes, new Comparator<Note>() {
                    @Override
                    public int compare(Note n1, Note n2) {
                        return n1.getTitle().compareTo(n2.getTitle());
                    }
                });
                break;
            case 1:
                Collections.sort(notes, new Comparator<Note>() {
                    @Override
                    public int compare(Note n1, Note n2) {
                        return -1*n1.getTitle().compareTo(n2.getTitle());
                    }
                });
                break;
            case 2:
                Collections.sort(notes, new Comparator<Note>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public int compare(Note n1, Note n2) {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                        Date date1 = new Date();
                        Date date2 = new Date();
                        try {
                            date1 = sdf.parse(n1.getDate());
                            date2 = sdf.parse(n2.getDate());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if (date1.before(date2)) return 1;
                        else if (date1.after(date2)) return -1;
                        else return 0;
                    }
                });
                break;
            case 3:
                Collections.sort(notes, new Comparator<Note>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public int compare(Note n1, Note n2) {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                        Date date1 = new Date();
                        Date date2 = new Date();
                        try {
                            date1 = sdf.parse(n1.getDate());
                            date2 = sdf.parse(n2.getDate());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if (date1.after(date2)) return 1;
                        else if (date1.before(date2)) return -1;
                        else return 0;
                    }
                });
                break;
        }
        adapter.notifyDataSetChanged();
    }
}