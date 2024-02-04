package com.blend.jetpackstudy.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

// @Dao标签用于标识这是一个Dao类。Dao类是一个接口或者抽象类，Room会在编译时自动生成它的实现类。在Dao类中，我们可以定义增/删/改/查的方法。
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
