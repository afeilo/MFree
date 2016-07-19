package com.xiefei.openmusicplayer.ui.local.SongLibrary.artists;

import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.xiefei.openmusicplayer.entity.Artist;
import com.xiefei.openmusicplayer.ui.local.SongLibrary.BaseLayoutFragment;
import com.xiefei.openmusicplayer.ui.custom.GradDividerItemDecoration;

import java.util.List;

/**
 * Created by xiefei on 16/7/10.
 */
public class ArtistListFragment extends BaseLayoutFragment<ArtistListPresenter,ArtistListView> implements ArtistListView {
    private ArtistListAdapter adapter;

//

    @Override
    public ArtistListPresenter createPresent() {
        return new ArtistListPresenter(getContext());
    }

    @Override
    protected boolean isRetainInstance() {
        return true;
    }

    @Override
    protected void bindData(View v) {
        super.bindData(v);
//        RecyclerView contentView = (RecyclerView) v.findViewById(R.id.content);
        adapter = new ArtistListAdapter(getContext());
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
    public void setData(List<Artist> data) {
        adapter.addSongs(data);
    }

}
