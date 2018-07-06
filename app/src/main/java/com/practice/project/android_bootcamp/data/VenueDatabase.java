package com.practice.project.android_bootcamp.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.practice.project.android_bootcamp.model.Category;
import com.practice.project.android_bootcamp.model.Venue;

@Database(entities = {Category.class, Venue.class}, version = 1)
public abstract class VenueDatabase extends RoomDatabase {
    public abstract CategoryDao categoryDao();
    public abstract VenueDao venueDao();
}