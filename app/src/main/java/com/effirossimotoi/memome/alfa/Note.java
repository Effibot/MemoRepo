package com.effirossimotoi.memome.alfa;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;
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
    @ColumnInfo(name = "creation_column")
    private String creation_date;    // inizializzata in automatico da data del telefono
    @ColumnInfo(name = "modified_column")
    private String modifed_date;
    @ColumnInfo(name = "color_column")
    private int colorId;
    @ColumnInfo(name = "icon_column")
    private int iconId;

    public Note(String title, String note, @Nullable int colorId, int iconId) {
        int id;
        this.title = title;
        this.note = note;
        this.colorId = colorId;
        this.iconId = iconId;
        creation_date = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(Calendar.getInstance().getTime());
        modifed_date = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(Calendar.getInstance().getTime());
        // modo per mostrare la data in una stringa
        /*tramite il pattern scegliamo il modo in cui rappresentiamo la data
        * Format è statico di Calendar e permette di ottenere data e ora dal sistema*/
    }
    public Note(Parcel parcel){
        this.id = parcel.readInt();
        this.title = parcel.readString();
        this.note = parcel.readString();
        this.creation_date = parcel.readString();
        this.modifed_date = parcel.readString();
        this.colorId = parcel.readInt();
        this.iconId = parcel.readInt();
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

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public String getModifed_date() {
        return modifed_date;
    }

    public void setModifed_date(String modifed_date) {
        this.modifed_date = modifed_date;
    }

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
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
        dest.writeString(creation_date);
        dest.writeString(modifed_date);
        dest.writeInt(colorId);
        dest.writeInt(iconId);
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
