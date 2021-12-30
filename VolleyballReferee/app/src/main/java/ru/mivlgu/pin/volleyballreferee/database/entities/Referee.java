package ru.mivlgu.pin.volleyballreferee.database.entities;

import androidx.room.*;

@Entity (tableName = "Referees",
        foreignKeys = @ForeignKey (entity = Category.class, parentColumns = "Id", childColumns = "Category_Id"))
public class Referee {

    @PrimaryKey (autoGenerate = true)
    @ColumnInfo (name = "Id")
    public int id;

    @ColumnInfo (name = "Full_Name")
    public String fullName;

    @ColumnInfo (name = "City")
    public String city;

    @ColumnInfo (name = "Photo")
    public String photo;

    @ColumnInfo (name = "Category_Id")
    public int categoryId;
}