package com.blend.jetpackstudy.databinding;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.blend.jetpackstudy.R;

/**
 * DataBinding的优势：
 * ● 项目更简洁，可读性更高。部分与UI控件相关的代码可以在布局文件中完成。
 * ● 不再需要findViewById()方法。
 * ● 布局文件可以包含简单的业务逻辑。UI控件能够直接与数据模型中的字段绑定，甚至能响应用户的交互。
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
        // viewDataBinding.setVariable(BR.book, book);
        viewDataBinding.setBook(book);
        viewDataBinding.setEventHandler(new EventHandleListener(this));

        viewDataBinding.setNetworkImage("https://cn.bing.com/sa/simg/hpb/LaDigue_EN-CA1115245085_1920x1080.jpg");
        viewDataBinding.setLocalImage(R.drawable.ic_launcher_background);
    }

}