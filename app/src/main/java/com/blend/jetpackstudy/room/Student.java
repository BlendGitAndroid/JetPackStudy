package com.blend.jetpackstudy.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

// @Entity标签用于将Student类与Room中的数据表对应起来。tableName属性可以为数据表设置表名，若不设置，则表名与类名相同。
@Entity(tableName = "student")
public class Student {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
    public int id;

    @ColumnInfo(name = "name", typeAffinity = ColumnInfo.TEXT)
    public String name;

    @ColumnInfo(name = "age", typeAffinity = ColumnInfo.TEXT)
    public String age;

    public Student(int id, String name, String age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    /**
     * 由于Room只能识别和使用一个构造器，如果希望定义多个构造器，可以使用Ignore标签，让Room忽略这个构造器
     * 不仅如此，@Ignore标签还能用于字段，Room不会持久化@Ignore标签标记过的字段数据
     */
    @Ignore
    public Student(String name, String age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
