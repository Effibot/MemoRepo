package com.effirossimotoi.memome.alfa;

import android.content.Context;
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
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.effirossimotoi.memome.alfa.Database.AppDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity  {
    private static AppDatabase db;
    private static List<Note> notes;
    private static RecyclerView recyclerView;
    private static RecyclerViewAdapter adapter;
    private static Context baseContext;
    public static View viewLayout;
    public static NavigationView navigationView;
    private static ItemTouchHelper itemTouchHelper;
    private static Note mRecentlyDeletedItem;

    private static int lastSort;
    private MenuItem checkBox;


    public static void adapterNotifyAll() {
        notes = db.noteDAO().getAll();
        recyclerView = viewLayout.findViewById(R.id.recyclerView);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(baseContext);
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RecyclerViewAdapter(notes, baseContext);
        recyclerView.setAdapter(adapter);

        itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback());
        itemTouchHelper.attachToRecyclerView(recyclerView);
        sortNotes(lastSort);
        adapter.notifyDataSetChanged();

    }

    /**
     * Sort notes
     * i=0 : alphabetically descending
     * i=1 : alphabetically ascending
     * i=2 : by modified date descending (Default at start)
     * i=3 : by modified date ascending
     * i=4 : by creation date descending
     * i=5 : by creation date ascending
     * i=6 : by color descending
     * i=7 : by color ascending
     * @param i
     */

    private static void sortNotes(int i) {
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
                            date1 = sdf.parse(n1.getModifed_date());
                            date2 = sdf.parse(n2.getModifed_date());
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
                            date1 = sdf.parse(n1.getModifed_date());
                            date2 = sdf.parse(n2.getModifed_date());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if (date1.after(date2)) return 1;
                        else if (date1.before(date2)) return -1;
                        else return 0;
                    }
                });
                break;
            case 4:
                Collections.sort(notes, new Comparator<Note>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public int compare(Note n1, Note n2) {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                        Date date1 = new Date();
                        Date date2 = new Date();
                        try {
                            date1 = sdf.parse(n1.getCreation_date());
                            date2 = sdf.parse(n2.getCreation_date());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if (date1.before(date2)) return 1;
                        else if (date1.after(date2)) return -1;
                        else return 0;
                    }
                });
                break;
            case 5:
                Collections.sort(notes, new Comparator<Note>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public int compare(Note n1, Note n2) {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                        Date date1 = new Date();
                        Date date2 = new Date();
                        try {
                            date1 = sdf.parse(n1.getCreation_date());
                            date2 = sdf.parse(n2.getCreation_date());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if (date1.after(date2)) return 1;
                        else if (date1.before(date2)) return -1;
                        else return 0;
                    }
                });
                break;
            case 6:
                Collections.sort(notes, new Comparator<Note>() {
                    @Override
                    public int compare(Note n1, Note n2) {
                        if (n1.getColorId()<n2.getColorId())
                            return 1;
                        else if (n1.getColorId()>n2.getColorId())
                            return -1;
                        else return 0;
                    }
                });
                break;
            case 7:
                Collections.sort(notes, new Comparator<Note>() {
                    @Override
                    public int compare(Note n1, Note n2) {
                        if (n1.getColorId()<n2.getColorId())
                            return -1;
                        else if (n1.getColorId()>n2.getColorId())
                            return 1;
                        else return 0;                    }
                });
                break;
        }
        adapter.notifyDataSetChanged();
    }

    public static void sendObject(Context context, int pos) {
        context.startActivity(EditActivity.getEditIntent(context, 'e', notes.get(pos)));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewLayout = findViewById(R.id.view_layout);
        baseContext = MainActivity.this;

        // controllo che entro per la prima volta nella onCreate
        if (savedInstanceState == null) {
            db = Room.databaseBuilder(MainActivity.this, AppDatabase.class, "note_database")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
            MainActivity.adapterNotifyAll();
            sortNotes(2); // Sort by modified date at start by default
        }

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RecyclerViewAdapter(notes, MainActivity.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        // Swipe to delete
        itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback());
        itemTouchHelper.attachToRecyclerView(recyclerView);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // uso tag 'a' per add
                startActivity(EditActivity.getEditIntent(MainActivity.this, 'a'));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setInputType(InputType.TYPE_TEXT_VARIATION_FILTER);

        checkBox = menu.findItem(R.id.descending);
        checkBox.setChecked(true);

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
        switch (id) {
            case R.id.sortAz:
                item.setChecked(true);
                if (checkBox.isChecked()) {
                    lastSort = 0;
                    sortNotes(0);
                } else {
                    lastSort = 1;
                    sortNotes(1);
                }
                break;
            case R.id.sortMod:
                item.setChecked(true);
                if (checkBox.isChecked()) {
                    lastSort = 2;
                    sortNotes(2);
                } else {
                    lastSort = 3;
                    sortNotes(3);
                }
                break;
            case R.id.sortCre:
                item.setChecked(true);
                if (checkBox.isChecked()) {
                    lastSort = 4;
                    sortNotes(4);
                } else {
                    lastSort = 5;
                    sortNotes(5);
                }
                break;
            case R.id.sortColor:
                item.setChecked(true);
                if (checkBox.isChecked()) {
                    lastSort = 6;
                    sortNotes(6);
                } else {
                    lastSort = 7;
                    sortNotes(7);
                }
                break;
            case R.id.descending:
                if (checkBox.isChecked()) {
                    checkBox.setChecked(false);
                    lastSort++;
                    sortNotes(lastSort);
                } else {
                    checkBox.setChecked(true);
                    lastSort--;
                    sortNotes(lastSort);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public static void deleteItem(int position) {
        mRecentlyDeletedItem = notes.get(position);
        db.noteDAO().deleteNote(mRecentlyDeletedItem);
        adapterNotifyAll();
        showUndoSnackbar();
    }

    private static void showUndoSnackbar(){
        Snackbar snackbar = Snackbar.make(viewLayout, R.string.snack_bar_text,
                Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.snack_bar_undo, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                undoDelete();
            }
        });
        snackbar.show();
    }

    private static void undoDelete() {
        db.noteDAO().insertAll(mRecentlyDeletedItem);
        adapterNotifyAll();
    }
}