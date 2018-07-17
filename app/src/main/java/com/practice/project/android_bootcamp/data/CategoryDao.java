package com.practice.project.android_bootcamp.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.practice.project.android_bootcamp.model.Category;

import java.util.List;

@Dao
public interface CategoryDao {

    @Query("SELECT * FROM category")
    List<Category> getAll();

    @Query("SELECT * FROM category WHERE category_id LIKE :id LIMIT 1")
    Category findById(String id);

    @Insert
    void insertAll(List<Category> categories);

    @Insert
    long insert(Category category);

    @Update
    void update(List<Category> categories);

    @Delete
    void delete(List<Category> categories);
}
