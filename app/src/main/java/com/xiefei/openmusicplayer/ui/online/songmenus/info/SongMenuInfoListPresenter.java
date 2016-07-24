package com.xiefei.openmusicplayer.ui.online.songmenus.info;

import android.content.Context;

import com.xiefei.mvpstructure.presenter.MvpBasePresenter;
import com.xiefei.openmusicplayer.entity.SongMenu;
import com.xiefei.openmusicplayer.entity.SongMenuInfo;
import com.xiefei.openmusicplayer.loader.SongMenuLoader;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by xiefei on 16/7/10.
 */
public class SongMenuInfoListPresenter extends MvpBasePresenter<SongMenuInfoListView> {
    private Context context;
    private SongMenuLoader songMenuLoader;
    public SongMenuInfoListPresenter(){
        songMenuLoader = SongMenuLoader.getInstance();
    }
    void getData(String listId){
        getView().showLoading(false);
        Call<SongMenuInfo> songMenuCall = songMenuLoader.getSongMenuInfos(listId);
        songMenuCall.enqueue(new Callback<SongMenuInfo>() {
            @Override
            public void onResponse(Call<SongMenuInfo> call, Response<SongMenuInfo> response) {
                if(response!=null)
                    getView().setData(response.body().getContent());
                else
                    getView().showError(new NullPointerException(""),false);
            }

            @Override
            public void onFailure(Call<SongMenuInfo> call, Throwable t) {
                getView().showError(t,false);
            }
        });
    }

    @Override
    public void cancel() {

    }
}
