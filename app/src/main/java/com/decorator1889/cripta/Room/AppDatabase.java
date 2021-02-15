package com.decorator1889.cripta.Room;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.decorator1889.cripta.Models.Task;


@Database(entities = {Task.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
}
