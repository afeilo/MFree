package com.xiefei.openmusicplayer.ui.online.songmenus.info;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xiefei.library.XRecyclerAdapter;
import com.xiefei.mvpstructure.fragment.MvpBaseActivity;
import com.xiefei.mvpstructure.fragment.MvpBaseFragment;
import com.xiefei.openmusicplayer.MusicServiceUtils;
import com.xiefei.openmusicplayer.R;
import com.xiefei.openmusicplayer.entity.SongMenuInfo;
import com.xiefei.openmusicplayer.service.helper.MusicHelper;
import com.xiefei.openmusicplayer.ui.MainActivity;
import com.xiefei.openmusicplayer.ui.custom.DividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xiefei on 16/7/13.
 */
public class SongMenuInfoFragment extends MvpBaseFragment<SongMenuInfoListPresenter,SongMenuInfoListView>
        implements SongMenuInfoListView,XRecyclerAdapter.OnItemClickListener{
    private static final String Tag = "SongMenuInfoFragment";
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
    public static final String SONG_MENU_ID="menu_list_id";
    public static final String SONG_MENU_TITLE="menu_list_title";
    public static final String SONG_MENU_IMAGE="menu_list_image";
    public static final String SONG_MENU_DESC="menu_list_desc";
    private MainActivity mainActivity;
    private String id;
    private String title;
    private String desc;
    private String image;
    private boolean isFirst = true;
    private List<SongMenuInfo.ContentBean> datas;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        id = getArguments().getString(SONG_MENU_ID);
        title = getArguments().getString(SONG_MENU_TITLE);
        desc = getArguments().getString(SONG_MENU_DESC);
        image = getArguments().getString(SONG_MENU_IMAGE);

    }

    @Override
    public SongMenuInfoListPresenter createPresent() {
        return new SongMenuInfoListPresenter();
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
    public void setData(List<SongMenuInfo.ContentBean> data) {
        adapter.setDatas(data);
    }

    @Override
    public void onClick(View view, int position) {
//        String url = adapter.getData(position).getSongUrl();
//        if(url==null)
//            Snackbar.make(view,"歌曲未获取到链接!",Snackbar.LENGTH_SHORT).setAction("ok",null);
//        else {
//            MusicServiceUtils.openFile(adapter.getData(position).getSong_id(),);
//            MusicServiceUtils.play();
//        }
        MusicServiceUtils.setOnlinePlayList(adapter.getDatas(),position, MusicHelper.baidu_online);

    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        if(isFirst){
            adapter = new SongMenuInfoListAdapter(getContext(),R.layout.song_list_item);
            listContent.setAdapter(adapter);
            adapter.setOnItemClickListener(this);
            Glide.with(getContext()).load(image).into(imageView);
            isFirst = false;
            if(datas!=null)
                adapter.setDatas(datas);
            else
                presenter.getData(id);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(isRetainInstance()&&adapter!=null)
            datas = adapter.getDatas();
        isFirst = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        datas = null;
    }

    @Override
    protected void initView(View contentView) {
        ButterKnife.bind(this,contentView);
        mainActivity.setSupportActionBar(toolbar);
        listContent.setLayoutManager(new LinearLayoutManager(getContext()));
        listContent.addItemDecoration(new DividerItemDecoration(Color.BLACK,1));
        collapsingToolbarLayout.setTitle(title);
        mainActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.song_menu_info_layout;
    }
}
