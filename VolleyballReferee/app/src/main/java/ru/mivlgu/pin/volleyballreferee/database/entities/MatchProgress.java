package ru.mivlgu.pin.volleyballreferee.database.entities;

import androidx.room.*;

@Entity (tableName = "Matches_Progress",
        foreignKeys = {
                @ForeignKey (entity = Match.class, parentColumns = "Id", childColumns = "Match_Id"),
                @ForeignKey (entity = GameEvent.class, parentColumns = "Id", childColumns = "Game_Event_Id")
        })
public class MatchProgress {

    @PrimaryKey (autoGenerate = true)
    @ColumnInfo (name = "Id")
    public int id;

    @ColumnInfo (name = "Match_Id")
    public int matchId;

    @ColumnInfo (name = "Game_Event_Id")
    public int gameEventId;
}