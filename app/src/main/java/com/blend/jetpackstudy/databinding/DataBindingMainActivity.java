package com.blend.jetpackstudy.databinding;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.blend.jetpackstudy.BR;
import com.blend.jetpackstudy.R;

/**
 * DataBinding的优势：
 * ● 项目更简洁，可读性更高。部分与UI控件相关的代码可以在布局文件中完成。
 * ● 不再需要findViewById()方法。
 * ● 布局文件可以包含简单的业务逻辑。UI控件能够直接与数据模型中的字段绑定，甚至能响应用户的交互。
 * <p>
 * 1.在Activity中实例化的Book对象只能被Activity持有，所有对Book对象的操作及业务逻辑都必须在Activity中完成。布局文件与
 * Book对象之间没有任何关联。现在，我们希望减轻Activity的工作量，让布局文件也承担部分工作，因此，将Book对象传递给布局文
 * 件是要做的第一步工作。
 * 2.便可以将UI控件与Book对象的属性进行绑定，布局表达式以＠{}的格式作为属性的值存在，绑定后，我们就不再需要在Activity中
 * 通过setText()方法对UI控件进行赋值了
 * 3.有时需要在布局文件中引用一些Java工具类，帮助我们处理一些简单的逻辑，可以在布局文件中通过import标签导入静态工具类。
 * 接着在UI控件中调用静态方法。
 * 4.DataBinding相应事件。
 * 5.二级页面的绑定。在编写布局文件的过程中，遇到布局层次结构复杂，或者部分布局在其他布局文件中能够复用的情况时，通常我们会将这部分布局独立
 * 成一个单独的布局文件，然后通过<include>标签进行引用。在布局文件中设置好布局变量book之后，便可以接收来自页面的数据，进而将数据与UI控件
 * 进行绑定了。不仅如此，布局变量book同时也是命名空间xmlns:app的一个属性。
 * 6.DataBinding的adapter库为我们生成了很多常用的类的Adapter，我们在使用的时候直接使用，要是不够用，就能自己自定义。
 * DataBinding库以静态方法的形式为UI控件的各个属性绑定了相应的代码。若工程师在UI控件的属性中使用了布局表达式，那么当布局文件
 * 被渲染时，属性所绑定的静态方法会被自动调用。
 * 7.自定义Adapter。在静态方法前需要加入＠BindingAdapter（""）标签，并为该方法起一个别名，此处为image，布局文件正是通过别
 * 名来调用该方法的。
 * 8.双向绑定。TextView的android:text属性与book对象的title字段之间的绑定，就是一种单向绑定。绑定后，当title字段发生变化
 * 时，TextView会自动更新相应的内容。@Bindable就是告诉编译期，这里要实现双向绑定。
 * 完成双向绑定非常简单，布局表达式由单向绑定的＠{}变为＠={}即可。
 */
public class DataBindingMainActivity extends AppCompatActivity {


    private ActivityDatabindingMainBinding viewDataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_databinding_main);
        Book book = new Book();
        book.title = "加油";
        book.author = "Blend";
        book.rating = 2;
        //这个只是相当于给variable赋值，和其他的赋值都是一样的
        viewDataBinding.setVariable(BR.booka, book);
        viewDataBinding.setBooka(book);

        // 通过DataBindingUtil将Activity与布局文件绑定，接着实例化EventHandleListener类，并将其传入布局文件，
        // 通过布局表达式，调用EventHandleListener中的方法。
        // viewDataBinding.setEventHandlerListener(new EventHandleListener(this));
        viewDataBinding.setVariable(BR.EventHandlerListener, new EventHandleListener(this));

        viewDataBinding.setOneWayBindingListener(new OneWayBindingListener(book, viewDataBinding));

        viewDataBinding.setMyPadding(5);

        viewDataBinding.setNetworkImage("https://cn.bing.com/sa/simg/hpb/LaDigue_EN-CA1115245085_1920x1080.jpg");
        viewDataBinding.setLocalImage(R.drawable.ic_launcher_background);
    }

}