package com.practice.project.android_bootcamp.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.practice.project.android_bootcamp.model.Location;

import java.util.List;

@Dao
public interface LocationDao {

    @Query("SELECT * FROM location")
    List<Location> getAll();

    @Query("SELECT * FROM location WHERE location_id LIKE :id LIMIT 1")
    Location findById(String id);

    @Insert
    void insertAll(List<Location> locations);

    @Insert
    long insert(Location location);

    @Update
    void update(List<Location> locations);

    @Delete
    void delete(List<Location> locations);
}
