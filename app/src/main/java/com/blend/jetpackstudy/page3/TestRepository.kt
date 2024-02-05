package com.blend.jetpackstudy.page3

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow


class TestRepository {
    // Paging3 返回的数据类型是 Flow<PagingData<T>>，
    // 其中，T 是业务中的数据类型，在这里对应的是 StudentReqData.ListBean。
    fun loadStudentMessage(): Flow<PagingData<DataX>> {
        return Pager(
            // PagingConfig 指定了关于分页的参数配置，主要有如下属性：
            // pageSize：每次加载数据量的大小（指定每页数据量大小）。
            // initialLoadSize：处理加载数据量的大小，默认为 pageSize 的三倍。
            // enablePlaceholders：是否启动展示位，启动后数据未加载出来之前将显示空白的展示位。
            // prefetchDistance：预取距离，当数据超过这个数值时自动触发加载下一页。
            config = PagingConfig(pageSize = 8),
            // pagingSourceFactory 参数指定了加载分页的数据源是 TestDataSource
            pagingSourceFactory = { TestDataSource() }).flow
    }
}
