package ru.mivlgu.pin.volleyballreferee.database.dao;

import androidx.room.*;

import java.util.List;

import ru.mivlgu.pin.volleyballreferee.database.entities.Player;

@Dao
public interface PlayerDao {

    @Query ("SELECT * FROM Players")
    List<Player> getAll ();

    @Query("SELECT * FROM Players WHERE id = :id")
    Player getById(int id);

    @Insert
    void insert (Player player);

    @Update
    void update (Player player);
}