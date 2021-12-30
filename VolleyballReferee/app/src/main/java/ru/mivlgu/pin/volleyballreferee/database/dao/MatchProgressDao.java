package ru.mivlgu.pin.volleyballreferee.database.dao;

import androidx.room.*;

import java.util.List;

import ru.mivlgu.pin.volleyballreferee.database.entities.MatchProgress;

@Dao
public interface MatchProgressDao {

    @Query ("SELECT * FROM Matches_Progress WHERE Match_Id = :matchId")
    List<MatchProgress> getAllByMatchId (int matchId);

    @Insert
    void insert (MatchProgress progress);
}