package com.xiefei.openmusicplayer.ui.online.songmenus.info;

import android.content.Context;

import com.xiefei.mvpstructure.presenter.MvpBasePresenter;
import com.xiefei.openmusicplayer.API.BaiduUtils;
import com.xiefei.openmusicplayer.entity.SongMenuInfo;
import com.xiefei.openmusicplayer.loader.SongMenuLoader;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by xiefei on 16/7/10.
 */
public class SongMenuInfoListPresenter extends MvpBasePresenter<SongMenuInfoListView> {
    private Context context;
    private SongMenuLoader songMenuLoader;
    private Observable<List<SongMenuInfo.ContentBean>> contentBeanObservable;
    public SongMenuInfoListPresenter(){
        songMenuLoader = SongMenuLoader.getInstance();
    }
    void getData(String listId){
        getView().showLoading(false);
        Observable<SongMenuInfo> songMenuCall = songMenuLoader.getSongMenuInfos(listId);
        contentBeanObservable = songMenuCall.map(new Func1<SongMenuInfo, List<SongMenuInfo.ContentBean>>() {
            @Override
            public List<SongMenuInfo.ContentBean> call(SongMenuInfo songMenuInfo) {
                List<SongMenuInfo.ContentBean> contentBeans = songMenuInfo.getContent();
                for (int i = 0; i <contentBeans.size(); i++) {
                    contentBeans.get(i).setSongUrl(BaiduUtils.getUrlById(contentBeans.get(i).getSong_id()));
                }
                return contentBeans;
            }
        });
        contentBeanObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<List<SongMenuInfo.ContentBean>>() {
            @Override
            public void call(List<SongMenuInfo.ContentBean> contentBeen) {
                if(getView()!=null)
                    getView().setData(contentBeen);
            }
        });
    }

    @Override
    public void cancel() {
        if(contentBeanObservable != null){
            contentBeanObservable.unsubscribeOn(AndroidSchedulers.mainThread());
            contentBeanObservable.unsubscribeOn(Schedulers.io());
        }


    }
}
