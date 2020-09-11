package com.blend.jetpackstudy.workmanager;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

/**
 * 假设要实现将日志上传到服务器的需求。该类继承自Worker类，并且覆盖doWork()方法，所有需要在任务中执行的代码都在该方法中进
 * 行编写。
 * <p>
 * doWork()方法有3种类型的返回值。
 * ● 若执行成功，则返回Result.success()。
 * ● 若执行失败，则返回Result.failure()。
 * ● 若需要重新执行，则返回Result.retry()。
 */
public class UploadLogWork extends Worker {

    public UploadLogWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    /**
     * Worker通过getInputData()方法接收数据，并在任务完成后，向WorkManager返回数据。
     */
    @NonNull
    @Override
    public Result doWork() {
        return Result.success();
    }

}
