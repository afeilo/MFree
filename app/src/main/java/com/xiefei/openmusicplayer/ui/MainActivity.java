package com.xiefei.openmusicplayer.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.xiefei.library.XRecyclerAdapter;
import com.xiefei.openmusicplayer.BaseActivity;
import com.xiefei.openmusicplayer.MusicServiceUtils;
import com.xiefei.openmusicplayer.R;
import com.xiefei.openmusicplayer.entity.SongInfo;
import com.xiefei.openmusicplayer.ui.local.SongLibrary.SongLibFragment;
import com.xiefei.openmusicplayer.ui.local.SongLibrary.filterSong.FilterFilterSongFragment;
import com.xiefei.openmusicplayer.ui.online.songmenus.SongMenuListFragment;
import com.xiefei.openmusicplayer.ui.online.songmenus.info.SongMenuInfoFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dalvik.system.PathClassLoader;

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
    @BindView(R.id.play_bar)
    LinearLayout playBar;
    private static final int PERMISSION_REQUEST_CODE = 0;
    private FragmentControl fragmentControl;
    private FragmentManager fragmentManager;
    private FilterFilterSongFragment filterFilterSongFragment;
    private SongMenuInfoFragment songMenuInfoFragment;
    private boolean isPause = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ButterKnife.bind(this);
        fragmentControl = new FragmentControl(getSupportFragmentManager());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission();
        } else {
            load();
        }
        navigationView.setNavigationItemSelectedListener(this);
        fragmentManager = getSupportFragmentManager();
    }

    private void load() {
        fragmentControl.add(R.id.content, SongLibFragment.class);
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        } else {
            load();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                load();
            } else {
                Toast.makeText(this, "权限不允许", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.local:
                fragmentControl.add(R.id.content, SongLibFragment.class);
                break;
            case R.id.remote:
                fragmentControl.add(R.id.content, SongMenuListFragment.class);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                popBackStack();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void popBackStack() {
        if (filterFilterSongFragment != null & !filterFilterSongFragment.isHidden())
            filterFilterSongFragment.getFragmentManager().popBackStack();
        if (songMenuInfoFragment != null && !songMenuInfoFragment.isHidden())
            songMenuInfoFragment.getFragmentManager().popBackStack();
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
    void pressNext(View v) {
        MusicServiceUtils.next();
    }

    @OnClick(R.id.main_play)
    void pressPlay(View v) {
        if(isPause){
            MusicServiceUtils.play();
        }else {
            MusicServiceUtils.pause();
        }
    }

//    @OnClick(R.id.play_bar)
//    void pressPlayBar(View v) {
//        startActivity(new Intent(this,NowPlayingActivity.class));
//    }

    public void openFilterFilterSongFragment(String title, String image, String selection) {
        if (filterFilterSongFragment == null)
            filterFilterSongFragment = new FilterFilterSongFragment();
        Bundle bundle = new Bundle();
        bundle.putString(FilterFilterSongFragment.IMAGE_URI, image);
        bundle.putString(FilterFilterSongFragment.TITLE, title);
        bundle.putString(FilterFilterSongFragment.SELECTION, selection);
        filterFilterSongFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.content, filterFilterSongFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void openSongMenuInfoFragment(String id, String desc, String title, String image) {
        if (songMenuInfoFragment == null)
            songMenuInfoFragment = new SongMenuInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putString(SongMenuInfoFragment.SONG_MENU_ID, id);
        bundle.putString(SongMenuInfoFragment.SONG_MENU_DESC, desc);
        bundle.putString(SongMenuInfoFragment.SONG_MENU_TITLE, title);
        bundle.putString(SongMenuInfoFragment.SONG_MENU_IMAGE, image);
        songMenuInfoFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.content, songMenuInfoFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    protected void musicPause() {
        isPause = true;
        super.musicPause();
        playButton.setImageResource(R.drawable.icon_music_play);
    }

    @Override
    protected void musicPlay() {
        isPause = false;
        super.musicPlay();
        playButton.setImageResource(R.drawable.icon_music_pause);
    }

    @Override
    protected void freshPlay() {
        super.freshPlay();
        songTitle.setText(MusicServiceUtils.getTrackName());
        songArtist.setText(MusicServiceUtils.getArtistName());
    }

    @Override
    protected void musicStop() {
        super.musicStop();
    }
}