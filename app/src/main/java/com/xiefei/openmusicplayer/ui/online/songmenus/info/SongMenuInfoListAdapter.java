package com.xiefei.openmusicplayer.ui.online.songmenus.info;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xiefei.library.XRecyclerAdapter;
import com.xiefei.library.XViewHolderHelper;
import com.xiefei.openmusicplayer.R;
import com.xiefei.openmusicplayer.entity.SongMenu;
import com.xiefei.openmusicplayer.entity.SongMenuInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xiefei on 16/7/10.
 */
public class SongMenuInfoListAdapter extends XRecyclerAdapter<SongMenuInfo.ContentBean>{
    private OnItemClickListener onItemClickListener;

    public SongMenuInfoListAdapter(Context context, @LayoutRes int layoutId) {
        super(context, layoutId);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void bindItemView(final XViewHolderHelper holderHelper, SongMenuInfo.ContentBean data, final int position) {
        ((TextView)holderHelper.getViewById(R.id.primary_title)).setText(data.getTitle());
        ((TextView)holderHelper.getViewById(R.id.secondary_title)).setText(data.getAuthor());
        ((ImageView)holderHelper.getViewById(R.id.image)).setImageResource(R.drawable.songmenu_item);
        if(onItemClickListener!=null)
        holderHelper.getHoldView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(holderHelper.getHoldView(),position);
            }
        });
    }
    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }
}
