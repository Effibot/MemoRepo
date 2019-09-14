package com.effirossimotoi.memome.alfa.Database;
import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.effirossimotoi.memome.alfa.Note;


@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract NoteDAO noteDAO();
}
