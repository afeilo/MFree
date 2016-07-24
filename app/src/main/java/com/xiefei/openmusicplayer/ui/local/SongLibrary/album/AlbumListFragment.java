package com.xiefei.openmusicplayer.ui.local.SongLibrary.album;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.xiefei.openmusicplayer.R;
import com.xiefei.openmusicplayer.entity.Album;
import com.xiefei.openmusicplayer.ui.local.SongLibrary.BaseLayoutFragment;
import com.xiefei.openmusicplayer.ui.custom.GradDividerItemDecoration;

import java.util.List;

/**
 * Created by xiefei on 16/7/10.
 */
public class AlbumListFragment extends BaseLayoutFragment<AlbumListPresenter,AlbumListView> implements AlbumListView {
    private AlbumListAdapter adapter;
    private boolean isFirst = true;
    private List<Album> albums;
//

    @Override
    public AlbumListPresenter createPresent() {
        return new AlbumListPresenter(getContext(),getLoaderManager());
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
    public void setData(List<Album> data) {
        adapter.setDatas(data);
    }
    @Override
    protected void initView(View contentView) {
        super.initView(contentView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.addItemDecoration(new GradDividerItemDecoration(Color.TRANSPARENT,16,2));
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        if(isFirst){
            adapter = new AlbumListAdapter(getContext(), R.layout.album_list_item);
            recyclerView.setAdapter(adapter);
            if(albums == null)
                presenter.getData();
            else
                adapter.setDatas(albums);
        }
        isFirst = false;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(isRetainInstance() && adapter !=null)
            albums = adapter.getDatas();
    }
}
