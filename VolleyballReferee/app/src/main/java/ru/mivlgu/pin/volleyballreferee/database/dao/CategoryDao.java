package ru.mivlgu.pin.volleyballreferee.database.dao;

import androidx.room.*;

import java.util.List;

import ru.mivlgu.pin.volleyballreferee.database.entities.Category;

@Dao
public interface CategoryDao {

    @Query ("SELECT Name FROM Categories WHERE Id = :id")
    String getNameById (int id);

    @Insert
    void insert (Category category);

    @Query("SELECT * FROM Categories")
    List<Category> getAll ();
}