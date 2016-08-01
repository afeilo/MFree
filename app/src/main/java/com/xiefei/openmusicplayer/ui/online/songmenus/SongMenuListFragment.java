package com.xiefei.openmusicplayer.ui.online.songmenus;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.xiefei.mvpstructure.fragment.MvpBaseFragment;
import com.xiefei.openmusicplayer.R;
import com.xiefei.openmusicplayer.entity.SongMenu;
import com.xiefei.openmusicplayer.ui.MainActivity;
import com.xiefei.openmusicplayer.ui.custom.GradDividerItemDecoration;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xiefei on 16/7/10.
 */
public class SongMenuListFragment extends MvpBaseFragment<SongMenuListPresenter,SongMenuListView> implements
        SongMenuListView,SongMenuListAdapter.OnItemClickListener{
    @BindView(R.id.list_content)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private boolean isFirst = true;
    private SongMenuListAdapter adapter;
    private final int PAGE_SIZE = 20;
    private List<SongMenu.ContentBean> songMenuInfos;
    private MainActivity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.song_menu_layout;
    }

    @Override
    public SongMenuListPresenter createPresent() {
        return new SongMenuListPresenter();
    }

    @Override
    protected boolean isRetainInstance() {
        return false;
    }



    private void bindToolbar() {
        MainActivity activity =  ((MainActivity)getActivity());
        activity.setSupportActionBar(toolbar);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(activity,activity.drawerLayout
                ,toolbar, R.string.drawer_open,R.string.drawer_close);
        drawerToggle.syncState();
        activity.drawerLayout.addDrawerListener(drawerToggle);
        activity.getSupportActionBar().setTitle(getResources().getString(R.string.online_song_menu));
    }
    @Override
    protected void initView(View contentView) {
        ButterKnife.bind(this,contentView);
        bindToolbar();
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.addItemDecoration(new GradDividerItemDecoration(Color.TRANSPARENT,16,2));
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        if(isFirst){
            adapter = new SongMenuListAdapter(getContext(), R.layout.songmenu_list_item);
            recyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener(this);
            if(songMenuInfos == null)
                presenter.getData(PAGE_SIZE,1);
            else
                adapter.setDatas(songMenuInfos);
        }
        isFirst = false;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(isRetainInstance()&&adapter!=null)
            songMenuInfos = adapter.getDatas();
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
    public void setData(List<SongMenu.ContentBean> data) {
        Log.d("song","size->"+data.size());
        adapter.setDatas(data);
    }

    @Override
    public void onClick(View view, int position) {
        SongMenu.ContentBean contentBean = adapter.getData(position);
        activity.openSongMenuInfoFragment(contentBean.getListid(),contentBean.getDesc(),contentBean.getTitle(),contentBean.getPic_300());
    }
}
