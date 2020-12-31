package com.blend.jetpackstudy.workmanager;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.blend.jetpackstudy.R;

import java.util.concurrent.TimeUnit;

/**
 * 作用：
 * 绝大部分应用程序都有在后台执行任务的需求。根据需求的不同，Android为后台任务提供了多种解决方案，如JobScheduler、Loader、
 * Service等。如果这些API没有被恰当地使用，则可能会消耗大量的电量。Android在解决应用程序耗电问题上做了各种尝试，从Doze到App Standby，
 * 通过各种方式限制和管理应用程序，以保证应用程序不会在后台过分消耗设备的电量。WorkManager为应用程序中那些不需要及时完成的任务提供了一个
 * 统一的解决方案，以便在设备电量和用户体验之间达到一个比较好的平衡。
 * <p>
 * 三个重要特点：
 * 1.针对的是不需要及时完成的任务。例如，发送应用程序日志、同步应用程序数据、备份用户数据等，站在业务需求的角度，这些任务都不需要立即完成。
 * 如果我们自己来管理这些任务，逻辑可能会非常复杂，若API使用不恰当，可能会消耗大量电量。
 * 2.保证任务一定会被执行。WorkManager能保证任务一定会被执行，即使应用程序当前不在运行中，甚至在设备重启过后，任务仍然会在适当的时刻被
 * 执行。这是因为WorkManager有自己的数据库，关于任务的所有信息和数据都保存在该数据库中。因此，只要任务交给了WorkManager，哪怕应用程序
 * 彻底退出，或者设备被重新启动，WorkManager依然能够保证完成你交给它的任务。
 * 3.兼容范围广。
 * WorkManager最低能兼容API Level 14，并且不需要你的设备安装Google Play Services。因此，不用过于担心兼容性问题，因为API Level 14
 * 已经能够兼容几乎100%的设备。
 * <p>
 * 兼容方案：
 * WorkManager能依据设备的情况，选择不同的执行方案。在API Level 23以上的设备中，通过JobScheduler完成任务；在API Level 23以下的设备中，
 * 通过AlarmManager和Broadcast Receivers组合来完成任务。但无论采用哪种方案，任务最终都是交由Executor来执行的。需要注意的是，WorkManager
 * 不是一种新的工作线程，它的出现不是为了替代其他类型的工作线程。工作线程通常立即运行，并在任务执行完成后给用户反馈。而WorkManager不是即时的，
 * 它不能保证任务能立即得到执行。
 * <p>
 * 任务链：
 * 如果有一系列的任务需要按顺序执行，那么可以利用WorkManager.begin With().then().then()...enqueue()的方式构建任务链。例如，在上传数据
 * 之前，可能需要先对数据进行压缩。
 * 总结：
 * WorkManager在非原生系统的设备中不能正常使用很可能是非原生系统的问题。因此，开发者在使用WorkManager作为解决方案时，一定要慎重。
 * 周期性任务的实际执行时间，与所设定的时间差别较大。从测试的结果来看，并没有太明显的规律。并且在任务执行完成后，WorkInfo并不会收到Success的通知。
 * 查阅相关资料后发现，Android认为Success和Failure都属于“终止类”的通知。这意味着，若发出此类通知，则表明任务“彻底终止”，而周期性任务是不会彻底
 * 终止的，它会一直执行下去。因此，在使用LiveData观察周期性任务时，不会收到Success这一类的通知，这也是我们需要注意的地方。
 */
public class WorkManagerMainActivity extends AppCompatActivity {

    private static final String TAG = "WorkManagerMainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_manager_main);
        work();
    }

    public void work() {
        //1.设置任务触发条件。例如，我们可以设置当设备处于充电，网络已连接，且电池电量充足的状态下，才触发任务。
        Constraints constraints = new Constraints.Builder()
                .setRequiresCharging(true)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresBatteryNotLow(true)
                .build();

        /*9.WorkManager与Worker之间的参数传递。WorkManager通过setInputData()方法向Worker传递数据。数据的传递通过Data对象来
         完成。需要注意的是，Data只能用于传递一些小的基本类型的数据，且数据最大不能超过10KB。
         */
        Data inputData = new Data.Builder().putString("BlendData", "Hi").build();

        /*2.将任务触发条件设置到WorkRequest。WorkRequest是一个抽象类，它有两种实现方式——OneTimeWorkRequest和PeriodicWorkRequest，
         分别对应的是一次性任务和周期性任务。
         3.假设没有设置触发条件，或者所设置的触发条件此刻符合系统的执行要求，此时，系统有可能会立刻执行该任务。但如果你希望能够延迟任务的执行，
         那么可以通过setInitialDelay()方法，对任务进行延后。
         4.假如Worker线程的执行出现了异常，如服务器宕机。你可能希望过一段时间后，重试该任务。那么可以在Worker的doWork()方法中返回
         Result.retry()，系统会有默认的指数退避策略来帮你重试任务，也可以通过setBackoffCriteria()方法来自定义指数退避策略。
         5.为任务设置tag标签。设置tag标签后，你就可以通过该标签跟踪任务的状态：WorkManager.getWorkInfosByTagLiveData（String tag）；
         也可以取消任务：WorkManager.cancelAllWorkByTag（String tag）。
         */
        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(UploadLogWork.class)
                .setConstraints(constraints)
                .setInputData(inputData)
                .setInitialDelay(10, TimeUnit.SECONDS)
                .setBackoffCriteria(BackoffPolicy.LINEAR, OneTimeWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
                .addTag("BlendWork")
                .build();
        //6.将任务配置好之后，需要将其提交给系统，WorkManager.enqueue()方法用于将配置好的WorkRequest交给系统来执行。
        WorkManager.getInstance(this).enqueue(request);

        /*
        7.观察任务的状态任务在提交给系统后，可以通过WorkInfo获知任务的状态。WorkInfo包含任务的id、tag、Worker对象传递过来的outputData，以及任务
        当前的状态。有3种方式可以得到WorkInfo对象。
        ● WorkManager.getWorkInfosByTag().
        ● WorkManager.getWorkInfoById().
        ● WorkManager.getWorkInfosForUniqueWork().
        如果希望实时获知任务的状态，这3个方法还有对应的LiveData方法。
        ● WorkManager.getWorkInfosByTagLiveData().
        ● WorkManager.getWorkInfoByIdLiveData().
        ● WorkManager.getWorkInfosForUniqueWorkLiveData().
        通过LiveData，我们便可以在任务状态发生变化时收到通知。
         */
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(request.getId()).observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {
                Log.e(TAG, "onChanged: " + workInfo);
            }
        });

        //8.与观察任务类似，我们也可以根据id或tag取消某个任务，或者取消所有的任务。
        WorkManager.getInstance(this).cancelAllWork();
    }
}