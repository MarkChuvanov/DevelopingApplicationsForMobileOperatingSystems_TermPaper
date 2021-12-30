package ru.mivlgu.pin.volleyballreferee.database.dao;

import androidx.room.*;

import java.util.List;

import ru.mivlgu.pin.volleyballreferee.database.entities.Team;

@Dao
public interface TeamDao {

    @Query ("SELECT * FROM Teams")
    List<Team> getAll ();

    @Query("SELECT * FROM Teams WHERE id = :id")
    Team getById(int id);

    @Insert
    void insert (Team team);

    @Update
    void update(Team team);

    @Delete
    void delete(Team team);
}