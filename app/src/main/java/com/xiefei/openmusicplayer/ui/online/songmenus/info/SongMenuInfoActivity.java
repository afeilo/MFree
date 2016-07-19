package com.xiefei.openmusicplayer.ui.online.songmenus.info;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xiefei.mvpstructure.fragment.MvpBaseActivity;
import com.xiefei.openmusicplayer.R;
import com.xiefei.openmusicplayer.entity.SongMenuInfo;
import com.xiefei.openmusicplayer.ui.custom.DividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xiefei on 16/7/13.
 */
public class SongMenuInfoActivity extends MvpBaseActivity<SongMenuInfoListPresenter,SongMenuInfoListView>
        implements SongMenuInfoListView{
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.list_content)
    RecyclerView listContent;
    @BindView(R.id.song_menu_info_image)
    ImageView imageView;
    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    private SongMenuInfoListAdapter adapter;
    public static String SONG_MENU_ID="menu_list_id";
    public static String SONG_MENU_TITLE="menu_list_title";
    public static String SONG_MENU_IMAGE="menu_list_image";
    public static String SONG_MENU_DESC="menu_list_desc";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_menu_info_layout);
        ButterKnife.bind(this);
        initView();
        String id = getIntent().getExtras().getString(SONG_MENU_ID);
        String title = getIntent().getExtras().getString(SONG_MENU_TITLE);
        String desc = getIntent().getExtras().getString(SONG_MENU_DESC);
        String image = getIntent().getExtras().getString(SONG_MENU_IMAGE);
        collapsingToolbarLayout.setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Glide.with(this).load(image).into(imageView);
        presenter.getData(id);
    }

    private void initView() {
        setSupportActionBar(toolbar);
        listContent.setLayoutManager(new LinearLayoutManager(this));
        listContent.addItemDecoration(new DividerItemDecoration(Color.BLACK,1));
        adapter = new SongMenuInfoListAdapter(this,R.layout.song_list_item);
        listContent.setAdapter(adapter);
    }

    @Override
    protected SongMenuInfoListPresenter createPresent() {
        return new SongMenuInfoListPresenter();
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
    public void setData(List<SongMenuInfo.ContentBean> data) {
        adapter.setDatas(data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
