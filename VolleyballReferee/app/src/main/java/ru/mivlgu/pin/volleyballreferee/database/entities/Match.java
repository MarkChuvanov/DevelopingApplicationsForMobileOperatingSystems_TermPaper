package ru.mivlgu.pin.volleyballreferee.database.entities;

import androidx.room.*;

@Entity(tableName = "Matches",
        foreignKeys = {
            @ForeignKey(entity = Team.class, parentColumns = "Id", childColumns = "First_Team_Id"),
            @ForeignKey(entity = Team.class, parentColumns = "Id", childColumns = "Second_Team_Id"),
            @ForeignKey(entity = Team.class,parentColumns = "Id",childColumns = "Winning_Team_Id"),
            @ForeignKey(entity = Referee.class,parentColumns = "Id", childColumns = "Referee_Id")
        })
public class Match {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id")
    public int id;

    @ColumnInfo(name = "First_Team_Id")
    public int firstTeamId;

    @ColumnInfo(name = "Second_Team_Id")
    public int secondTeamId;

    @ColumnInfo(name = "Location")
    public String location;

    @ColumnInfo(name = "Date")
    public String date;

    @ColumnInfo(name = "Referee_Id")
    public int refereeId;

    @ColumnInfo(name = "Winning_Team_Id")
    public int winningTeamId;

    @ColumnInfo(name = "Result")
    public String result;

    @ColumnInfo(name = "Start_Time")
    public String startTime;

    @ColumnInfo(name = "End_Time")
    public String endTime;
}