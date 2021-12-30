package ru.mivlgu.pin.volleyballreferee.database.entities;

import androidx.room.*;

@Entity (tableName = "Roles")
public class Role {

    @PrimaryKey (autoGenerate = true)
    @ColumnInfo (name = "Id")
    public int id;

    @ColumnInfo (name = "Name")
    public String name;
}