package com.lib.commui.nova.util.download;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;

import com.lib.commui.nova.util.Utils;

import java.io.File;

/**
 * 下载帮助类
 * 参考文档：https://www.jianshu.com/p/861b61cb5b34
 */
public class DownloadManagerHelper {


    /**
     * 启动下载
     * @param url 下载地址
     * @param saveFilePath 保存文件路径
     * @param downloadTitle 下载时通知栏显示的标题
     * @param downloadDesc 下载时通知栏显示的描述
     */
    public static void startDownload(String url, String saveFilePath, String downloadTitle, String downloadDesc){
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        //设置漫游条件下是否可以下载
        request.setAllowedOverRoaming(false);
        //在通知栏中显示，默认就是显示的
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        //设置通知标题
        request.setTitle(downloadTitle);
        //设置通知标题message
        request.setDescription(downloadDesc);
        //设置是否在系统的下载UI中显示此下载 默认True
        request.setVisibleInDownloadsUi(true);

        //设置文件存放路径
        File file = new File(saveFilePath);
        request.setDestinationUri(Uri.fromFile(file));

        //获取下载服务
        DownloadManager downloadManager = (DownloadManager) Utils.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
        downloadManager.enqueue(request);
    }

}
