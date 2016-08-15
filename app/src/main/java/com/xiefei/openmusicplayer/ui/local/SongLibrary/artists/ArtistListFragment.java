package com.xiefei.openmusicplayer.ui.local.SongLibrary.artists;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.xiefei.library.XRecyclerAdapter;
import com.xiefei.openmusicplayer.R;
import com.xiefei.openmusicplayer.entity.Artist;
import com.xiefei.openmusicplayer.ui.MainActivity;
import com.xiefei.openmusicplayer.ui.local.SongLibrary.BaseLayoutFragment;
import com.xiefei.openmusicplayer.ui.custom.GradDividerItemDecoration;
import java.util.List;

/**
 * Created by xiefei on 16/7/10.
 */
public class ArtistListFragment extends BaseLayoutFragment<ArtistListPresenter,ArtistListView> implements
        ArtistListView,XRecyclerAdapter.OnItemClickListener {
    private ArtistListAdapter adapter;
    private boolean isFirst = true;
    private List<Artist> artists;

    @Override
    public ArtistListPresenter createPresent() {
        return new ArtistListPresenter(getContext());
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
    public void setData(List<Artist> data) {
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
            adapter = new ArtistListAdapter(getContext(), R.layout.artist_list_item);
            adapter.setOnItemClickListener(this);
            recyclerView.setAdapter(adapter);
            if(artists == null)
                presenter.getData();
            else
                adapter.setDatas(artists);
        }
        isFirst = false;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(isRetainInstance()&&adapter!=null){
            artists = adapter.getDatas();
            isFirst = true;
        }

    }

    @Override
    public void onClick(View view, int position) {
        ((MainActivity)getActivity()).openFilterFilterSongFragment(adapter.getData(position).getAstist(),
                "android.resource://"+getActivity().getPackageName()+"/"+R.mipmap.logo_icon,
                "is_music = 1 AND artist_id = "+adapter.getData(position).getId());
    }
}
