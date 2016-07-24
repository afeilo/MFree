package com.xiefei.library;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiefei on 16/7/11.
 */
public abstract class XRecyclerAdapter<M> extends RecyclerView.Adapter<XRecyclerViewHolder>{
    protected Context context;
    protected @LayoutRes int layoutId;
    protected List<M> datas;
    protected OnItemClickListener onItemClickListener;
    public XRecyclerAdapter(Context context, @LayoutRes int layoutId){
        this.context = context;
        this.layoutId = layoutId;
        datas = new ArrayList<>();
    }
    @Override
    public XRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new XRecyclerViewHolder(LayoutInflater.from(context).inflate(layoutId,parent,false));
    }
    public void addDatas(List<M> datas){
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }
    public void setDatas(List<M> datas){
        this.datas = datas;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void refreshDatas(List<M> datas){
        this.datas.addAll(0,datas);
        notifyDataSetChanged();
    }

    public void removeData(M data){
        datas.remove(data);
        notifyDataSetChanged();
//        notifyItemRemoved();
    }
    public M getData(int position){
        return datas.get(position);
    }

    public List<M> getDatas(){
        return datas;
    }

    @Override
    public void onBindViewHolder(final XRecyclerViewHolder holder, final int position) {
        bindItemView(holder.getHolderHelper(),datas.get(position),position);
        if(onItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onClick(holder.itemView,position,datas.get(position));
                }
            });
        }
    }
    public abstract void bindItemView(XViewHolderHelper holderHelper,M data,int position);

    @Override
    public int getItemCount() {
        return datas == null? 0 : datas.size();
    }
    public interface OnItemClickListener<M>{
        void onClick(View view,int position,M data);
    }
}
