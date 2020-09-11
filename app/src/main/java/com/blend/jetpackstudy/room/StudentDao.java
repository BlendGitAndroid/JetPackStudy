package com.blend.jetpackstudy.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface StudentDao {

    @Insert
    void insertStudent(Student student);

    @Delete
    void deleteStudent(Student student);

    @Update
    void updateStudent(Student student);

    @Query("SELECT * FROM student")
    List<Student> getStudentList();

    @Query("SELECT * FROM student")
    LiveData<List<Student>> getStudentListLiveData();

    @Query("SELECT * FROM student WHERE id = :id")
    Student getStudentById(int id);
}
