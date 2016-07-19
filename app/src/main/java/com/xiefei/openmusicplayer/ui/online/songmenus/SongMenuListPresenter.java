package com.xiefei.openmusicplayer.ui.online.songmenus;

import android.content.Context;

import com.xiefei.mvpstructure.presenter.MvpBasePresenter;
import com.xiefei.openmusicplayer.entity.Artist;
import com.xiefei.openmusicplayer.entity.SongMenu;
import com.xiefei.openmusicplayer.loader.ArtistLoader;
import com.xiefei.openmusicplayer.loader.SongMenuLoader;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by xiefei on 16/7/10.
 */
public class SongMenuListPresenter extends MvpBasePresenter<SongMenuListView> {
    private Context context;
    private SongMenuLoader songMenuLoader;
    public SongMenuListPresenter(){
        songMenuLoader = SongMenuLoader.getInstance();
    }
    void getData(int pageSize, final int pageNo){
        if(pageNo == 1){
            getView().showLoading(true);
        }else {
            getView().showLoading(false);
        }
        Call<SongMenu> songMenuCall = songMenuLoader.getSongMenus(pageSize,pageNo);
        songMenuCall.enqueue(new Callback<SongMenu>() {
            @Override
            public void onResponse(Call<SongMenu> call, Response<SongMenu> response) {
                getView().setData(response.body().getContent());
            }

            @Override
            public void onFailure(Call<SongMenu> call, Throwable t) {
                getView().showError(t,pageNo==1);
            }
        });
    }
}
