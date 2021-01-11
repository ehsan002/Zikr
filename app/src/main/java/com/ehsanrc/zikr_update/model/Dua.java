package com.ehsanrc.zikr_update.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "dua_table")
public class Dua {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "dua_title")
    public String duaTitle;

    @ColumnInfo(name = "dua_arabic")
    public String duaArabic;

    @ColumnInfo(name = "dua_pronunciation")
    public String duaPronunciation;

    @ColumnInfo(name = "dua_translation")
    public String duaTranslation;

    @ColumnInfo(name = "favorite")
    public boolean isFavorite;

    public void setId(int id) {
        this.id = id;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public Dua(String duaTitle, String duaArabic, String duaPronunciation, String duaTranslation) {

        this.duaTitle = duaTitle;
        this.duaArabic = duaArabic;
        this.duaPronunciation = duaPronunciation;
        this.duaTranslation = duaTranslation;
    }

}
