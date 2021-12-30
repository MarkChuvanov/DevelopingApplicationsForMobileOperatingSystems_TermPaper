package ru.mivlgu.pin.volleyballreferee.database.entities;

import androidx.room.*;

@Entity (tableName = "Players",
        foreignKeys = {
            @ForeignKey (entity = Role.class, parentColumns = "Id", childColumns = "Role_Id"),
            @ForeignKey (entity = Team.class, parentColumns = "Id", childColumns = "Team_Id")
        })
public class Player {

    @PrimaryKey (autoGenerate = true)
    @ColumnInfo (name = "Id")
    public int id;

    @ColumnInfo (name = "Full_Name")
    public String fullName;

    @ColumnInfo (name = "Photo")
    public String photo;

    @ColumnInfo (name = "Role_Id")
    public int roleId;

    @ColumnInfo (name = "Team_Id")
    public int teamId;
}