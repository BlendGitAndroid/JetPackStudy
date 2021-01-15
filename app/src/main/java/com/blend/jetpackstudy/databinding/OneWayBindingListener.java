package com.blend.jetpackstudy.databinding;

import android.view.View;

import androidx.databinding.library.baseAdapters.BR;

public class OneWayBindingListener {

    private Book mBook;
    private ActivityDatabindingMainBinding mViewBinding;

    public OneWayBindingListener(Book book, ActivityDatabindingMainBinding viewDataBinding) {
        mBook = book;
        mViewBinding = viewDataBinding;
    }

    public void changeBook(View view) {
        mBook.author = "新的作者";
        mBook.title = "新的标题";
        mViewBinding.setVariable(BR.booka, mBook);  //单向绑定，还是得调用更新
    }
}
