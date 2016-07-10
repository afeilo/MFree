package com.xiefei.openmusicplayer.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.xiefei.openmusicplayer.R;
import com.xiefei.openmusicplayer.loader.SongLoader;
import com.xiefei.openmusicplayer.ui.SongLibrary.SongLibFragment;
import com.xiefei.openmusicplayer.ui.SongLibrary.songs.SongListFragment;

import java.security.Permission;

import butterknife.BindView;
import butterknife.ButterKnife;
public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 0;
    private FragmentControl fragmentControl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentControl = new FragmentControl(getSupportFragmentManager());
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.M){
            checkPermission();
        }else {
            load();
        }

    }

    private void load() {
        fragmentControl.add(R.id.content, SongLibFragment.class);
    }

    private void checkPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE);
        }else {
            load();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == PERMISSION_REQUEST_CODE){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                load();
            }else {
                Toast.makeText(this,"权限不允许",Toast.LENGTH_SHORT).show();
            }
        }
    }


}
