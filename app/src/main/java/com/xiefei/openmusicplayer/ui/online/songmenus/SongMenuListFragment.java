package com.xiefei.openmusicplayer.ui.online.songmenus;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
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
import com.xiefei.openmusicplayer.ui.local.SongLibrary.BaseLayoutFragment;
import com.xiefei.openmusicplayer.ui.custom.GradDividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xiefei on 16/7/10.
 */
public class SongMenuListFragment extends MvpBaseFragment<SongMenuListPresenter,SongMenuListView> implements SongMenuListView {
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
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle(getResources().getString(R.string.online_song_menu));
//        RecyclerView contentView = (RecyclerView) v.findViewById(R.id.content);
        adapter = new SongMenuListAdapter(getContext());
        contentView.setLayoutManager(new GridLayoutManager(getContext(),2));
        contentView.addItemDecoration(new GradDividerItemDecoration(Color.TRANSPARENT,16,2));
        contentView.setAdapter(adapter);
        presenter.getData(PAGE_SIZE,1);
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


}
