package ru.mivlgu.pin.volleyballreferee.database.dao;

import androidx.room.*;

import java.util.List;

import ru.mivlgu.pin.volleyballreferee.database.entities.Match;

@Dao
public interface MatchDao {

    @Query ("SELECT * FROM Matches")
    List<Match> getAll ();

    @Query ("SELECT * FROM Matches WHERE id = :id")
    Match getById (int id);

    @Query("SELECT * FROM Matches WHERE id = (SELECT MAX(id) FROM Matches)")
    Match getLastMatch();

    @Insert
    void insert (Match match);

    @Update
    void update (Match match);
}