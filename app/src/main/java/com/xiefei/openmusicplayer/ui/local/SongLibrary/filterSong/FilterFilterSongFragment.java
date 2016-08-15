package com.xiefei.openmusicplayer.ui.local.SongLibrary.filterSong;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xiefei.library.XRecyclerAdapter;
import com.xiefei.mvpstructure.fragment.MvpBaseFragment;
import com.xiefei.openmusicplayer.MusicServiceUtils;
import com.xiefei.openmusicplayer.R;
import com.xiefei.openmusicplayer.entity.SongInfo;
import com.xiefei.openmusicplayer.entity.SongMenuInfo;
import com.xiefei.openmusicplayer.ui.MainActivity;
import com.xiefei.openmusicplayer.ui.custom.DividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xiefei on 16/7/13.
 */
public class FilterFilterSongFragment extends MvpBaseFragment<FilterSongListPresenter,FilterSongListView>
        implements FilterSongListView,XRecyclerAdapter.OnItemClickListener{
    private static final String Tag = "FilterFilterSongFragment";
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
    private FilterSongListAdapter adapter;
    private MainActivity activity;
    private boolean isFirst = true;
    private List<SongInfo> data;

    public static final String TITLE="title";
    public static final String SELECTION="selection";
    public static final String IMAGE_URI = "image_uri";
    private String title;
    private String seletion;
    private String imageUri;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        title = bundle.getString(TITLE);
        seletion = bundle.getString(SELECTION);
        imageUri = bundle.getString(IMAGE_URI);
    }

    @Override
    public FilterSongListPresenter createPresent() {
        return new FilterSongListPresenter(getContext(),getLoaderManager());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }

    @Override
    protected boolean isRetainInstance() {
        return false;
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
        adapter.setDatas(data);
    }

    @Override
    public void onClick(View view, int position) {
        MusicServiceUtils.setPlayList(adapter.getDatas(),position);
//        MusicServiceUtils.play();

    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        if(isFirst){
            collapsingToolbarLayout.setTitle(title);
            adapter = new FilterSongListAdapter(getContext(),R.layout.song_list_item);
            listContent.setAdapter(adapter);
            adapter.setOnItemClickListener(this);
            if(data == null)
                presenter.getData(seletion);
            else
                adapter.setDatas(data);
            Glide.with(this).load(imageUri).into(imageView);
            isFirst = false;
        }

    }

    @Override
    protected void initView(View contentView) {
        ButterKnife.bind(this,contentView);
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listContent.setLayoutManager(new LinearLayoutManager(getContext()));
        listContent.addItemDecoration(new DividerItemDecoration(Color.BLACK,1));
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.song_menu_info_layout;
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(isRetainInstance()&&adapter!=null){
            data = adapter.getDatas();
        }
        isFirst = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
