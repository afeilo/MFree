package com.xiefei.openmusicplayer.ui;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiefei on 2016/5/23.
 * Fragment管理类，主要用来解决Fragment show与hide之间的布局错乱问题。
 */
public class FragmentControl {
    private FragmentManager manager;
    private String lastTag;
    private String tag = "fragmentControlTag";
    private String FRAGMENT_SHOWING="fragmentShowing";
    private String FRAGMENT_TAGS = "fragmentControlId";
    private ArrayList<String> tags = new ArrayList();
    public FragmentControl(FragmentManager manager){
        this.manager = manager;
    }
    public boolean add(@IdRes int id, Class clazz){
        if(lastTag!=null)
            if(clazz.getName()==lastTag)
                return true;
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment = manager.findFragmentByTag(clazz.getName());
        if(fragment!=null){
            fragment.setUserVisibleHint(true);
            transaction.show(fragment);
            if(lastTag!=null){
                fragment = manager.findFragmentByTag(lastTag);
                fragment.setUserVisibleHint(false);
                transaction.hide(fragment);
            }
        }else {
        try {
            fragment = (Fragment) clazz.newInstance();
//            fragment.setRetainInstance(true);
            transaction.add(id,fragment,clazz.getName());
            tags.add(clazz.getName());
            fragment.setUserVisibleHint(true);
            if(lastTag!=null){
                fragment = manager.findFragmentByTag(lastTag);
                fragment.setUserVisibleHint(false);
                transaction.hide(fragment);
            }

        } catch (InstantiationException e) {
            e.printStackTrace();
            return false;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
    }
        transaction.commit();
        lastTag = clazz.getName();
        return true;
    }
    public void onSaveInstanceState(Bundle out){
        out.putStringArrayList(FRAGMENT_TAGS,tags);
        out.putString(FRAGMENT_SHOWING,lastTag);
    }
    public void onRestoreInstanceState(Bundle saveInstance){
        lastTag = saveInstance.getString(FRAGMENT_SHOWING);
        tags = saveInstance.getStringArrayList(FRAGMENT_TAGS);
        restore();
    }

    private void restore() {
        List<Fragment> list = manager.getFragments();
        FragmentTransaction transaction = manager.beginTransaction();
        if(list!=null){
            for (Fragment fragment:list) {
                if(fragment==null)
                    continue;
                if(tags.contains(fragment.getClass().getName())){
                    fragment.setUserVisibleHint(false);
                    transaction.hide(fragment);
                }

            }
        }
        if(lastTag!=null){
            Fragment fragment = manager.findFragmentByTag(lastTag);
            fragment.setUserVisibleHint(true);
            transaction.show(fragment);
        }


        transaction.commit();
    }

}
