package com.practice.project.android_bootcamp.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.practice.project.android_bootcamp.model.Venue;

import java.util.List;

@Dao
public interface VenueDao {

    @Query("SELECT * FROM venue")
    List<Venue> getAll();

    @Query("SELECT * FROM venue WHERE id LIKE :id LIMIT 1")
    Venue findById(String id);

    @Insert
    void insertAll(List<Venue> venues);

    @Insert
    long insert(Venue venue);

    @Update
    void update(List<Venue> venues);

    @Delete
    void delete(List<Venue> venues);
}
