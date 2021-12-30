package ru.mivlgu.pin.volleyballreferee.database.entities;

import androidx.room.*;

@Entity (tableName = "Categories")
public class Category {

    @PrimaryKey (autoGenerate = true)
    @ColumnInfo (name = "Id")
    public int id;

    @ColumnInfo (name = "Name")
    public String name;
}