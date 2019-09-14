package com.effirossimotoi.memome.alfa;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import com.effirossimotoi.memome.alfa.Database.AppDatabase;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

public class EditActivity extends AppCompatActivity {
    private static final String TAG = EditActivity.class.getName();
    private static final String ACTION_KEY = TAG + ".action.key";
    private static final String NOTE_KEY = TAG + ".note.key";
    private char action;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private EditText editTitle, editText;
    private AppDatabase db;
    private Note note = null;
    private MaterialButton deleteButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        deleteButton=findViewById(R.id.deleteButton);

        collapsingToolbarLayout = findViewById(R.id.toolbar_layout);
        editTitle = findViewById(R.id.editTitle);
        editText = findViewById(R.id.textNote);
        collapsingToolbarLayout.setTitle(" "); // spazio obbligatorio per annullare scritta nella toolbar

        db = Room.databaseBuilder(this, AppDatabase.class, "note_database")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        //ricevo dati dall'intent
        Intent intent = getIntent();
        action = getIntent().getCharExtra(ACTION_KEY, 'n');
        Bundle bundle = intent.getExtras();
        note = (Note) bundle.getParcelable(NOTE_KEY);

        if (action == 'e') {
            editText.setText(note.getNote());
            editTitle.setText(note.getTitle());
        }

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlert("Conferma Cancellazione","Vuoi davvero cancellare la tua nota?");
            }
        });
    }

    // get intent base
    public static Intent getEditIntent(Context context, char action) {
        Intent intent = new Intent(context, EditActivity.class);
        intent.putExtra(ACTION_KEY, action);
        return intent;
    }

    // get intent per passare anche le note
    public static Intent getEditIntent(Context context, char action, Note note) {
        Intent intent = new Intent(context, EditActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtra(ACTION_KEY, action);
        bundle.putParcelable(NOTE_KEY, note);
        intent.putExtras(bundle);
        return intent;

    }

    @Override
    public void onBackPressed() {
        String titleEdit = editTitle.getText().toString().trim();
        String textEdit = editText.getText().toString().trim();
        if (!titleEdit.isEmpty() || !textEdit.isEmpty()) {
            if (action == 'a') {
                // aggiungi al database
                db.noteDAO().insertAll(new Note(titleEdit, textEdit));
            } else if (action == 'e') {
                // modifica database
                // elimino per poi reinserire la nota

                db.noteDAO().deleteNote(note);
                db.noteDAO().insertAll(new Note(titleEdit, textEdit));
            }
            MainActivity.adapterNotifyAll();

            finish();
        } else {
            Snackbar.make(MainActivity.viewLayout, "inserisci almeno un dato", Snackbar.LENGTH_SHORT).show();

            super.onBackPressed();
        }
    }
    private void showAlert(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this);

        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                db.noteDAO().deleteNote(note);
                MainActivity.adapterNotifyAll();
                finish();

            }
        });
        builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
