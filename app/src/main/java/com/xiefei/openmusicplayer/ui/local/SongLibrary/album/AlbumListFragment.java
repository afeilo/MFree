package com.xiefei.openmusicplayer.ui.local.SongLibrary.album;

import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.xiefei.openmusicplayer.entity.Album;
import com.xiefei.openmusicplayer.ui.local.SongLibrary.BaseLayoutFragment;
import com.xiefei.openmusicplayer.ui.custom.GradDividerItemDecoration;

import java.util.List;

/**
 * Created by xiefei on 16/7/10.
 */
public class AlbumListFragment extends BaseLayoutFragment<AlbumListPresenter,AlbumListView> implements AlbumListView {
    private AlbumListAdapter adapter;

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
    protected void bindData(View v) {
        super.bindData(v);
//        RecyclerView contentView = (RecyclerView) v.findViewById(R.id.content);
        adapter = new AlbumListAdapter(getContext());
        contentView.setLayoutManager(new GridLayoutManager(getContext(),2));
        contentView.addItemDecoration(new GradDividerItemDecoration(Color.TRANSPARENT,16,2));
        contentView.setAdapter(adapter);
        presenter.getData();
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
        adapter.addSongs(data);
    }

}
