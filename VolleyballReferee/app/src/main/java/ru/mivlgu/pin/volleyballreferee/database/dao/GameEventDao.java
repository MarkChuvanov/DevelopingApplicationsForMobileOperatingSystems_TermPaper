package ru.mivlgu.pin.volleyballreferee.database.dao;

import androidx.room.*;

import java.util.List;

import ru.mivlgu.pin.volleyballreferee.database.entities.GameEvent;

@Dao
public interface GameEventDao {

    @Query ("SELECT * FROM Game_Events")
    List<GameEvent> getAll ();

    @Query ("SELECT * FROM Game_Events WHERE Id = :id")
    GameEvent getById (int id);

    @Insert
    void insert (GameEvent event);
}