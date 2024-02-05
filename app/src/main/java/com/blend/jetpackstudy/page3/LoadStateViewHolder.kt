package com.blend.jetpackstudy.page3

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.blend.jetpackstudy.R
import com.blend.jetpackstudy.databinding.ItemTestFootBinding


// ViewHolder 与底部布局绑定，通过 bindStatue 方法处理加载状态中的逻辑。
class LoadStateViewHolder(parent: ViewGroup, var retry: () -> Unit) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_test_foot, parent, false)
) {
    private val itemLoadStateBindingUtil: ItemTestFootBinding = ItemTestFootBinding.bind(itemView)

    fun bindStatue(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            Log.d("TAG", "----------LoadState.Error----")
            itemLoadStateBindingUtil.btnRetry.visibility = View.VISIBLE
            itemLoadStateBindingUtil.btnRetry.setOnClickListener {
                retry()
            }
        } else if (loadState is LoadState.Loading) {
            Log.d("TAG", "--------LoadState.Loading------")
            itemLoadStateBindingUtil.llLoading.visibility = View.VISIBLE
        } else if (loadState is LoadState.NotLoading) {
            Log.d("TAG", "--------LoadState.NotLoading------")
        }
    }
}
