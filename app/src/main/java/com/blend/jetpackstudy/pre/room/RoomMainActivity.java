package com.blend.jetpackstudy.pre.room;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.blend.jetpackstudy.R;

import java.util.List;

/**
 * Android采用Sqlite作为数据库存储。由于Sqlite代码写起来烦琐且容易出错，因此，开源社区逐渐出现了各种ORM（Object Relational Mapping）库。
 * 这些开源ORM库都是为了方便Sqlite的使用而出现的，包括数据库的创建、升级、增/删/改/查等操作。常见的ORM库有ORMLite、GreenDAO等。Google也意
 * 识到了推出自家ORM库的必要性，于是有了Room。Room与其他ORM库一样，也是在Sqlite上提供了一层封装。
 * <p>
 * 相关概念：
 * Entity：一个Entity对应于数据库中的一张表。Entity类是Sqlite表结构对Java类的映射，在Java中可以被看作一个Model类。
 * Dao：即Data Access Objects，数据访问对象。顾名思义，我们可以通过它来访问数据。
 * 一个Entity代表一张表，而每张表都需要一个Dao对象，用于对表进行增/删/改/查。Room数据库在被实例化之后，我们就可以通过数据库实例得到Dao对
 * 象（Get Dao），进而通过Dao对象对表中的数据进行操作。
 * 标签：
 * ●＠Entity标签用于将Student类与Room中的数据表对应起来。tableName属性可以为数据表设置表名，若不设置，则表名与类名相同。
 * ●＠PrimaryKey标签用于指定该字段作为表的主键。
 * ●＠ColumnInfo标签可用于设置该字段存储在数据库表中的名字，并指定字段的类型。
 * ●＠Ignore标签用来告诉Room忽略该字段或方法。
 * ●＠Database标签用于告诉系统这是Room数据库对象。entities属性用于指定该数据库有哪些表，若需要建立多张表，则表名以逗号相隔开。version属性
 * 用于指定数据库版本号，后面数据库的升级正是依据版本号进行判断的。
 * <p>
 * LiveData结合Room、ViewModel使用：
 * 当每次数据库中的数据发生变化时，我们都需要开启一个工作线程去重新获取数据库中的数据。这很不方便，我们希望数据在发生变化时，能自动收到通知，这
 * 就是LiveData的作用。LiveData通常与ViewModel一起使用，ViewModel是用于存放数据的，因此我们可以将数据库放在ViewModel中进行实例化。但数
 * 据库的实例化需要用到Context，而ViewModel中最好不要传入Context，因此，我们不宜直接使用ViewModel，而应该用它的子类AndroidViewModel。
 * AndroidViewModel构造器含有Application参数，Application作为Context的子类，刚好可以用于数据库的实例化。
 * <p>
 * 数据库升级：
 * 随着业务的变化，数据库可能也需要做一些调整。例如，数据表可能需要增加一个新字段。Android提供了一个名为Migration的类，来对Room进行升级。
 * Migration有两个参数，startVersion和endVersion。startVersion表示当前数据库版本（设备上安装的版本），endVersion表示将要升级到的
 * 版本。若设备中的应用程序数据库版本为1，那么以下Migration会将你的数据库版本从1升级到2。若用户设备上的应用程序数据库版本为1，而当前要安
 * 装的应用程序数据库版本为3，那么该怎么办呢？在这种情况下，Room会先判断当前有没有直接从1到3的升级方案，如果有，就直接执行从1到3的升级方案；
 * 如果没有，那么Room会按照顺序先后执行Migration（1，2）、Migration（2，3）以完成升级。
 * <p>
 * 升级异常：若Room在升级过程中没有匹配到相应的Migration。为了防止出现升级失败导致应用程序崩溃的情况，我们可以在创建数据库时加入
 * fallbackToDestructiveMigration()方法。该方法能够在出现升级异常时，重新创建数据表。需要注意的是，虽然应用程序不会崩溃，但由于数据表被
 * 重新创建，所有的数据也将会丢失。
 * <p>
 * Schema文件：类似于针对数据库的Git仓库
 * Room提供了一项功能，在每次数据库的升级过程中，它都会为你导出一个Schema文件，这是一个json格式的文件，其中包含了数据库的
 * 所有基本信息。有了该文件，开发者便能清楚地知道数据库的历次变更情况。Schema文件默认是导出的，你只需指定它的导出位置即可。
 * <p>
 * 销毁重建：
 * 在Sqlite中修改表结构比较麻烦。例如，想将表中的字段类型从INTEGER改为TEXT。
 * 面对此类需求，最好的方式是采用销毁与重建策略，该策略大致分为以下几个步骤。
 * 1.创建一张符合表结构要求的临时表temp_Student。
 * 2.将数据从旧表Student复制至临时表temp_Student。
 * 3.删除旧表Student。
 * 4.将临时表temp_Student重命名为Student。
 * <p>
 * 预填充数据库：createFromAsset() API和createFromFiIe() API：
 * Room加入了两个新的API，用于在给定已填充数据库文件的基础上创建Room数据库。基于这两个API，开发者可以基于特定的预打包好的数据库文件
 * 来创建Room数据库。
 */
public class RoomMainActivity extends AppCompatActivity {

    private static final String TAG = "RoomMainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_main);
        final MyDatabase myDatabase = MyDatabase.getInstance(this);
        //开启一个线程插入
        findViewById(R.id.insert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        myDatabase.studentDao().insertStudent(new Student(11, "Blend", "15"));
                    }
                }).start();
            }
        });

        //开启一个线程查询
        findViewById(R.id.query).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (Student student : myDatabase.studentDao().getStudentList()) {
                            Log.e(TAG, "Student: " + student.toString());
                        }
                    }
                }).start();
            }
        });

        /*
        结合ViewModel使用
        运行应用程序，当对数据库进行增加、删除或修改操作时，onChange（List<Student>students）方法会被自动调用。只需要在该方法内做相应的数据
        处理，其他什么都不用做。
        这里会自动查询，真的神奇！！！
         */
        StudentViewModel studentViewModel = new ViewModelProvider(this).get(StudentViewModel.class);
        studentViewModel.getListLiveData().observe(this, new Observer<List<Student>>() {
            @Override
            public void onChanged(List<Student> students) {
                for (Student student : students) {
                    Log.e(TAG, "LiveData Student: " + student.toString());
                }
            }
        });
    }
}