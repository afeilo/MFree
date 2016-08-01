package com.xiefei.openmusicplayer;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.content.ContextCompat;

import com.example.xiefei.openmusicplayer.IMediaPlaybackService;
import com.xiefei.openmusicplayer.entity.SongInfo;
import com.xiefei.openmusicplayer.service.MusicService;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

/**
 * Created by xiefei on 16/7/23.
 */
public class MusicServiceUtils {
    private static WeakHashMap<ContextWrapper,ServiceBinder> serviceWeakHashMap;
    private static IMediaPlaybackService mService;
    static{
        serviceWeakHashMap = new WeakHashMap<>();
    }
    public static ServiceToken bindService(Context context){
        return bindService(context,null);
    }
    public static ServiceToken bindService(Context context, ServiceConnection conn){
        Activity real = ((Activity) context).getParent();
        if(real == null)
            real = (Activity) context;
        ContextWrapper contextWrapper = new ContextWrapper(real);
        if(mService == null)
            contextWrapper.startService(new Intent().setClass(contextWrapper,MusicService.class));
        ServiceBinder binder = new ServiceBinder(contextWrapper,conn);
        contextWrapper.bindService(new Intent().setClass(contextWrapper,MusicService.class),binder,Service.BIND_AUTO_CREATE);
        serviceWeakHashMap.put(contextWrapper,binder);
        return new ServiceToken(contextWrapper);
    }

    public static void unBindService(ServiceToken token){
        if(token!=null){
            ContextWrapper contextWrapper = token.getContextWrapper();
            ServiceConnection serviceConnection = serviceWeakHashMap.get(contextWrapper);
            if(serviceConnection!=null){
                contextWrapper.unbindService(serviceConnection);
            }
        }

    }

    //api方法
    public static void play(){
        try {
            if(mService!=null)
                mService.play();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void next() {
        try {
            if(mService!=null)
                mService.next();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    public static void pause(){
        try {
            if(mService!=null)
                mService.pause();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    public static void stop(){
        try {
            if(mService!=null)
                mService.stop();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    public static String getTrackName() {
        try {
            if(mService!=null)
                return mService.getTrackName();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String getArtistName() {
        try {
            if(mService!=null)
                return mService.getArtistName();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static long duration() {
        try {
            if(mService!=null)
                return mService.duration();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static long seek(long seek) {
        try {
            if(mService!=null)
                return mService.seek(seek);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return 0;
    }



    public static void setPlayList(List<SongInfo> songInfos, int position){
        if(mService != null){
            long[] playList = new long[songInfos.size()];
            for (int i = 0; i < songInfos.size(); i++) {
                playList[i] = songInfos.get(i).getId();
            }
            try {
                mService.open(playList,position);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public static void openFile(String path) {
        if(mService!=null){
            try {
                mService.openFile(path);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }


    //内部类,用来管理bindService
    public static class ServiceToken{
        private ContextWrapper contextWrapper;

        public ServiceToken(ContextWrapper contextWrapper) {
            this.contextWrapper = contextWrapper;
        }

        public ContextWrapper getContextWrapper() {
            return contextWrapper;
        }
    }
    public static class ServiceBinder implements ServiceConnection{
        private Context context;
        private ServiceConnection callback;
        public ServiceBinder(Context context,ServiceConnection callback) {
            this.context = context;
            this.callback = callback;
        }
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            if(callback!=null)
                callback.onServiceConnected(name, service);
            if(mService==null)
                mService = IMediaPlaybackService.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            if(callback!=null)
                callback.onServiceDisconnected(name);
            mService = null;
        }
    }
}
