package com.xiefei.openmusicplayer.ui.SongLibrary.songs;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.xiefei.openmusicplayer.entity.SongInfo;
import com.xiefei.openmusicplayer.ui.SongLibrary.BaseLayoutFragment;
import com.xiefei.openmusicplayer.ui.custom.DividerItemDecoration;

import java.util.List;

/**
 * Created by xiefei on 16/7/10.
 */
public class SongListFragment extends BaseLayoutFragment<SongListPresenter,SongListView> implements SongListView{
    private SongListAdapter adapter;

//

    @Override
    public SongListPresenter createPresent() {
        return new SongListPresenter(getContext());
    }

    @Override
    protected boolean isRetainInstance() {
        return false;
    }

    @Override
    protected void bindData(View v) {
        super.bindData(v);
//        RecyclerView contentView = (RecyclerView) v.findViewById(R.id.content);
        adapter = new SongListAdapter(getContext());
        contentView.setLayoutManager(new LinearLayoutManager(getContext()));
        contentView.addItemDecoration(new DividerItemDecoration(Color.DKGRAY,1));
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
    public void setData(List<SongInfo> data) {
        adapter.addSongs(data);
    }
}
