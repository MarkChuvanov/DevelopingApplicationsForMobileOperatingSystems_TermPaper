package ru.mivlgu.pin.volleyballreferee.database.dao;

import androidx.room.*;

import java.util.List;

import ru.mivlgu.pin.volleyballreferee.database.entities.Role;

@Dao
public interface RoleDao {

    @Query("SELECT * FROM Roles")
    List<Role> getAll();

    @Query ("SELECT Name FROM Roles WHERE Id = :id")
    String getNameById (int id);

    @Insert
    void insert (Role role);
}