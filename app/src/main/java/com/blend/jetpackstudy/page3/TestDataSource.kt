package com.blend.jetpackstudy.page3

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import java.io.IOException


// PagingSource 有两个泛型类型：
// 1、表示页码参数的类型。2、表示每一项数据对应的实体类。
class TestDataSource : PagingSource<Int, DataX>() {
    //    创建 TestApi 对应的 service 的代码：
    private val testApi = RetrofitServiceBuilder.createService(TestApi::class.java)

    // 复写 load 方法以提供加载数据的逻辑
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataX> {
        try {
            // 通过 params.key 方法获取当前页码值，页码未定义置为 1（默认第一页）
            val currentPage = params.key ?: 1

            // 仓库层请求数据
            // params.loadSize 方法可以获取声明的每页数据量的大小（PagingConfig-pageSize 设置）
            // 在 PagingConfig 的 initialLoadSize 参数默认是 pageSize 的 3 倍，所以第一次加载数据的下标为 1～15，
            // 第二次加载的数据下标为 6～10，可以看到，这里面有部分数据是重复的。
            // Google 官方对此的回复是：仅使用 params.loadSize 请求接口导致程序出现数据重复的问题是正常且符合预期的，
            // 如果想避免这个问题，在加载数据时需要将 params.loadSize 替换为业务中的 pageSize，
            // 因为 Paging3 的设计理念就是不让开发者关心业务的具体实现，而是完全交给 Paging3 去处理。
            // 程序通过网络请求获取数据 data，当程序请求异常时，这里使用 LoadResult.Error 抛出异常，在实际项目中则需要根据对应业务来处理。
            val data = testApi?.getData(currentPage)

            Log.d("TAG", "当前页码$currentPage")

            // 题外话：这里将总页码的 pageCount（8） 认错成了 total（162），
            // 导致在刷到第 9 页时（currentPage 为 9）会持续触发此函数，导致无操作的情况下会一直加载到 total 页。
            if (currentPage == 1) Log.d("TAG", "总页码${data?.data?.pageCount}")

            // 当前页码小于总页码时，页码加 1
            val nextPage =
                if (currentPage < (data?.data?.pageCount ?: 0)) {
                    currentPage + 1
                } else {
                    null
                }
            // 上一页
            val prevKey = if (currentPage > 1) {
                currentPage - 1
            } else {
                null
            }

            when (data?.errorCode) {
                0 -> {
                    data.data.datas.let {
                        // 正常返回的数据通过 LoadResult.Page 方法返回
                        return LoadResult.Page(
                            // 需要加载的数据
                            data = it,   // 返回的列表数据
                            prevKey = prevKey,  //前一页
                            // 加载下一页的 key，如果传 null 就说明到底了
                            nextKey = nextPage  // 下一页
                        )
                    }
                }
                // 其他业务逻辑处理...
                else -> {}
            }
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }

        // 当程序请求异常时，上述示例使用的LoadResult.Error方法将抛出异常
        return LoadResult.Error(throwable = IOException())
    }

    @ExperimentalPagingApi
    override fun getRefreshKey(state: PagingState<Int, DataX>): Int? {
        return null
    }
}
