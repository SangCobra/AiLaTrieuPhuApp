package com.sang.ailatrieuphu.data.entity;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Entity(tableName = "HighScore")
public class HighScore {
    @ColumnInfo(name = "Name")
    @NotNull
    private String name;

    @ColumnInfo(name = "Score")
    private Integer score;

    @PrimaryKey(autoGenerate = true)
    @NotNull
    @ColumnInfo(name = "ID")
    private int id;

    public HighScore(){

    }

    @Ignore
    public HighScore(@NotNull String name, int score) {
        this.name = name;
        this.score = score;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
   }

    public int getId() {
        return id;
    }

    public void setId(int id) {
       this.id = id;
    }
}
