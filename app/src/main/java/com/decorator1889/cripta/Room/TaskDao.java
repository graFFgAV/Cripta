package com.decorator1889.cripta.Room;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.decorator1889.cripta.Models.Task;
import java.util.List;


@Dao
public interface TaskDao {
    @Query("SELECT * FROM task")
    List<Task> getAll();
    @Insert
    void insert(Task task);
    @Delete
    void delete(Task task);
    @Update
    void update(Task task);
}
