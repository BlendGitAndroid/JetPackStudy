package com.blend.jetpackstudy.page3

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.blend.jetpackstudy.databinding.ItemTestDataBinding

// PagingDataAdapter是继承自RecycleView.Adapter<VH>的，所以PagingDataAdapter的功能与普通的Adapter无异，
// 只是需要指定DiffUtil.ItemCallback参数。DiffUtil.ItemCallback用于计算列表中两个非空项目之间差异的回调。
class TestPagingDataAdapter :
    PagingDataAdapter<DataX, RecyclerView.ViewHolder>(object : DiffUtil.ItemCallback<DataX>() {

        override fun areItemsTheSame(
            oldItem: DataX,
            newItem: DataX
        ): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: DataX,
            newItem: DataX
        ): Boolean {
            return oldItem == newItem
        }

    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding: ItemTestDataBinding =
            ItemTestDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TestDataViewHolder(binding)
    }

    /**
     * 将数据绑定到对应 xml 布局中
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val mHolder = holder as TestDataViewHolder
        val bean: DataX? = getItem(position)
        bean?.let {
            mHolder.dataBindingUtil.bean = it
        }
    }

    class TestDataViewHolder(var dataBindingUtil: ItemTestDataBinding) :
        RecyclerView.ViewHolder(dataBindingUtil.root) {
    }
}
