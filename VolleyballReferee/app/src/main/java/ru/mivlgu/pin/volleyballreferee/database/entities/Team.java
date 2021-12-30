package ru.mivlgu.pin.volleyballreferee.database.entities;

import androidx.room.*;

@Entity (tableName = "Teams")
public class Team {

    @PrimaryKey (autoGenerate = true)
    @ColumnInfo (name = "Id")
    public int id;

    @ColumnInfo (name = "Name")
    public String name;

    @ColumnInfo (name = "City")
    public String city;

    @ColumnInfo (name = "Home_Arena")
    public String homeArena;
}