package com.xiefei.openmusicplayer.ui.online.songmenus;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.xiefei.mvpstructure.fragment.MvpBaseFragment;
import com.xiefei.openmusicplayer.R;
import com.xiefei.openmusicplayer.entity.Artist;
import com.xiefei.openmusicplayer.entity.SongMenu;
import com.xiefei.openmusicplayer.ui.MainActivity;
import com.xiefei.openmusicplayer.ui.local.SongLibrary.BaseLayoutFragment;
import com.xiefei.openmusicplayer.ui.custom.GradDividerItemDecoration;
import com.xiefei.openmusicplayer.ui.online.songmenus.info.SongMenuInfoActivity;
import com.xiefei.openmusicplayer.ui.online.songmenus.info.SongMenuInfoListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xiefei on 16/7/10.
 */
public class SongMenuListFragment extends MvpBaseFragment<SongMenuListPresenter,SongMenuListView> implements
        SongMenuListView,SongMenuListAdapter.OnItemClickListener{
    @BindView(R.id.list_content)
    RecyclerView contentView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private SongMenuListAdapter adapter;
    private final int PAGE_SIZE = 20;


    @Override
    public int getLayout() {
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

    @Override
    protected void bindData(View v) {
        ButterKnife.bind(this,v);
        bindToolbar();
//        RecyclerView contentView = (RecyclerView) v.findViewById(R.id.content);
        adapter = new SongMenuListAdapter(getContext());
        contentView.setLayoutManager(new GridLayoutManager(getContext(),2));
        contentView.addItemDecoration(new GradDividerItemDecoration(Color.TRANSPARENT,16,2));
        contentView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        presenter.getData(PAGE_SIZE,1);
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
        adapter.addSongMenus(data);
    }


    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getContext(), SongMenuInfoActivity.class);
        SongMenu.ContentBean contentBean = adapter.getData(position);
        intent.putExtra(SongMenuInfoActivity.SONG_MENU_ID,contentBean.getListid());
        intent.putExtra(SongMenuInfoActivity.SONG_MENU_DESC,contentBean.getDesc());
        intent.putExtra(SongMenuInfoActivity.SONG_MENU_TITLE,contentBean.getTitle());
        intent.putExtra(SongMenuInfoActivity.SONG_MENU_IMAGE,contentBean.getPic_300());
        startActivity(intent);
    }
}
