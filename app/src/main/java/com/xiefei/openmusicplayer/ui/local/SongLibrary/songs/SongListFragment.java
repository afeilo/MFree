package com.xiefei.openmusicplayer.ui.local.SongLibrary.songs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.xiefei.library.XRecyclerAdapter;
import com.xiefei.openmusicplayer.MusicServiceUtils;
import com.xiefei.openmusicplayer.R;
import com.xiefei.openmusicplayer.entity.SongInfo;
import com.xiefei.openmusicplayer.loader.SongLoader;
import com.xiefei.openmusicplayer.service.MusicService;
import com.xiefei.openmusicplayer.ui.MainActivity;
import com.xiefei.openmusicplayer.ui.local.SongLibrary.BaseLayoutFragment;
import com.xiefei.openmusicplayer.ui.custom.DividerItemDecoration;

import java.util.List;

/**
 * Created by xiefei on 16/7/10.
 */
public class SongListFragment extends BaseLayoutFragment<SongListPresenter,SongListView> implements SongListView
        ,XRecyclerAdapter.OnItemClickListener{
    private SongListAdapter adapter;
    private boolean isFirst = true;
    private List<SongInfo> songInfos = null;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("songList",this.toString());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public SongListPresenter createPresent() {
        return new SongListPresenter(getContext());
    }

    @Override
    protected boolean isRetainInstance() {
        return true;
    }

    @Override
    public void showLoading(boolean isPullToRefresh) {

    }

    @Override
    public void showContent() {

    }

    @Override
    public void showError(Throwable throwable, boolean isPullToRefresh) {

    }

    @Override
    public void setData(List<SongInfo> data) {
        adapter.setDatas(data);
    }


    @Override
    protected void initView(View contentView) {
        super.initView(contentView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(Color.DKGRAY,1));
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        if(isFirst){
            adapter = new SongListAdapter(getContext(), R.layout.song_list_item);
            adapter.setOnItemClickListener(this);
            recyclerView.setAdapter(adapter);
            if(songInfos == null)
                presenter.getData();
            else
                adapter.setDatas(songInfos);
        }
        isFirst = false;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(isRetainInstance()&&adapter!=null){
            songInfos = adapter.getDatas();
        }
    }

    @Override
    public void onClick(View view, int position) {
        MusicServiceUtils.setPlayList(adapter.getDatas(),position);
        MusicServiceUtils.play();
    }
}
