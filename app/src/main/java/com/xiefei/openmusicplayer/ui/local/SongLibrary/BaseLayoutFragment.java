package com.xiefei.openmusicplayer.ui.local.SongLibrary;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xiefei.mvpstructure.fragment.MvpBaseFragment;
import com.xiefei.mvpstructure.presenter.MvpPresenter;
import com.xiefei.mvpstructure.view.MvpView;
import com.xiefei.openmusicplayer.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xiefei on 16/7/10.
 */
public abstract class BaseLayoutFragment<P extends MvpPresenter,V extends MvpView>  extends MvpBaseFragment<P,V>{
    @BindView(R.id.list_content)
    protected RecyclerView contentView;
    @Override
    public int getLayout() {
        return R.layout.song_library_list_layout;
    }

    @Override
    protected void bindData(View v) {
        ButterKnife.bind(this,v);
    }
}
