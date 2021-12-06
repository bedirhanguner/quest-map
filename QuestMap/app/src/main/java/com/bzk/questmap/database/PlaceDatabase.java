package com.bzk.questmap.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.bzk.questmap.model.Place;

@Database(entities = {Place.class}, version = 1)
public abstract class PlaceDatabase extends RoomDatabase {
    public abstract PlaceDao placeDao();
}