package com.xiefei.openmusicplayer;

import android.app.Application;

import com.liulishuo.filedownloader.FileDownloader;

/**
 * Created by xiefei on 16/9/9.
 */
public class OpenMusicApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        initDownloaderManager();
    }
    private void initDownloaderManager() {
        FileDownloader.init(getApplicationContext());
        //下载文件所保存的目录
//        File storeFile = StorageUtils.getCacheDirectory(this, false, "FileDownloader");
//        if (!storeFile.exists()) {
//            storeFile.mkdirs();
//        }
//
//        final DownloaderManagerConfiguration.Builder dmBulder = new DownloaderManagerConfiguration.Builder(this)
//                .setMaxDownloadingCount(3) //配置最大并行下载任务数，配置范围[1-100]
////                .setDbExtField(...) //配置数据库扩展字段
//        .setDbVersion(1)//配置数据库版本
////        .setDbUpgradeListener(...) //配置数据库更新回调
//        .setDownloadStorePath(storeFile.getAbsolutePath()); //配置下载文件存储目录
//
//        //初始化下载管理最好放到线程中去执行防止卡顿情况
//        new Thread() {
//            @Override
//            public void run() {
//                super.run();
//                DownloaderManager.getInstance().init(dmBulder.build());//必要语句
//                DownloaderManager.getInstance().getAllTask().get(0).;
//
//            }
//        }.start();
    }
}
