package com.xiefei.openmusicplayer.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.xiefei.library.XRecyclerAdapter;
import com.xiefei.openmusicplayer.BaseActivity;
import com.xiefei.openmusicplayer.MusicServiceUtils;
import com.xiefei.openmusicplayer.R;
import com.xiefei.openmusicplayer.entity.SongInfo;
import com.xiefei.openmusicplayer.ui.local.SongLibrary.SongLibFragment;
import com.xiefei.openmusicplayer.ui.online.songmenus.SongMenuListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.navigation_view)
    public NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    public DrawerLayout drawerLayout;
    @BindView(R.id.main_progressbar)
    ProgressBar progressBar;
    @BindView(R.id.main_song_title)
    TextView songTitle;
    @BindView(R.id.main_song_article)
    TextView songArtist;
    @BindView(R.id.main_play)
    ImageView playButton;
    @BindView(R.id.main_next)
    ImageView nextButton;
    private static final int PERMISSION_REQUEST_CODE = 0;
    private FragmentControl fragmentControl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ButterKnife.bind(this);
        fragmentControl = new FragmentControl(getSupportFragmentManager());
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            checkPermission();
        }else {
            load();
        }
        navigationView.setNavigationItemSelectedListener(this);
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

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.local:
                fragmentControl.add(R.id.content,SongLibFragment.class);
                break;
            case R.id.remote:
                fragmentControl.add(R.id.content, SongMenuListFragment.class);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        fragmentControl.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        fragmentControl.onRestoreInstanceState(savedInstanceState);
    }
    @OnClick(R.id.main_next)
    void pressNext(View v){
        MusicServiceUtils.next();
    }
    @OnClick(R.id.main_play)
    void pressPlay(View v){

    }


}
