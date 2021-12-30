package ru.mivlgu.pin.volleyballreferee.database.dao;

import androidx.room.*;

import java.util.List;

import ru.mivlgu.pin.volleyballreferee.database.entities.Referee;

@Dao
public interface RefereeDao {

    @Query ("SELECT * FROM Referees")
    List<Referee> getAll ();

    @Query("SELECT * FROM Referees WHERE id = :id")
    Referee getById(int id);

    @Insert
    void insert (Referee referee);

    @Update
    void update (Referee referee);
}