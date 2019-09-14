package com.effirossimotoi.memome.alfa;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Entity
public class Note implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "title_column")
    private String title;
    @ColumnInfo(name = "note_column")
    private String note;
    @ColumnInfo(name = "date_column")
    private String date;    // inizializzata in automatico da data del telefono

    public Note(String title, String note) {
        int id;
        this.title = title;
        this.note = note;
        date = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(Calendar.getInstance().getTime());
        // modo per mostrare la data in una stringa
        /*tramite il pattern scegliamo il modo in cui rappresentiamo la data
        * Format è statico di Calendar e permette di ottenere data e ora dal sistema*/
    }
    public Note(Parcel parcel){
        this.id = parcel.readInt();
        this.title = parcel.readString();
        this.note = parcel.readString();
        this.date = parcel.readString();
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(note);
        dest.writeString(date);
    }
    public final static Creator CREATOR = new Creator() {
        @Override
        public Note createFromParcel(Parcel source) {
            return new Note(source);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

}
