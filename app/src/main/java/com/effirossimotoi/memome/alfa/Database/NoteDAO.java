package com.effirossimotoi.memome.alfa.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;


import com.effirossimotoi.memome.alfa.Note;

import java.util.List;

@Dao
public interface NoteDAO {
    @Query("SELECT * FROM Note")
    List<Note> getAll();

    @Insert
    public void insertAll(Note... notes);
    @Delete
    public void deleteNote(Note note);
    @Query("DELETE FROM Note")
    void deleteAll();
}
