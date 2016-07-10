package com.xiefei.openmusicplayer.ui;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.List;

/**
 * Created by xiefei on 2016/5/23.
 * Fragment管理类，主要用来解决Fragment show与hide之间的布局错乱问题。
 */
public class FragmentControl {
    private FragmentManager manager;
    private String lastTag;
    private String tag = "fragmentControlTag";
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
            transaction.show(fragment);
            if(lastTag!=null)
                transaction.hide(manager.findFragmentByTag(lastTag));
        }else {
        try {
            fragment = (Fragment) clazz.newInstance();
            transaction.add(id,fragment,clazz.getName());
            if(lastTag!=null)
                transaction.hide(manager.findFragmentByTag(lastTag));
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
        out.putString(tag,lastTag);
    }
    public void onRestoreInstanceState(Bundle saveInstance){
        lastTag = saveInstance.getString(tag);
        restore();
    }

    private void restore() {
        List<Fragment> list = manager.getFragments();
        FragmentTransaction transaction = manager.beginTransaction();
        if(list!=null){
            for (Fragment fragment:list) {
                transaction.hide(fragment);
            }
        }
        if(lastTag!=null)
            transaction.show(manager.findFragmentByTag(lastTag));
        transaction.commit();
    }

}
