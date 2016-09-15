package com.xiefei.openmusicplayer.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.RSRuntimeException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.xiefei.openmusicplayer.BaseActivity;
import com.xiefei.openmusicplayer.MusicServiceUtils;
import com.xiefei.openmusicplayer.R;
import com.xiefei.openmusicplayer.ui.widget.glide.FastBlur;
import com.xiefei.openmusicplayer.ui.widget.glide.RSBlur;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xiefei on 16/7/31.
 */
public class NowPlayingActivity extends BaseActivity implements SeekBar.OnSeekBarChangeListener{
    private static final String Tag = "NowPlayingActivity";
    @BindView(R.id.song_title)
    TextView songTitle;
    @BindView(R.id.song_artist)
    TextView songArtist;
    @BindView(R.id.song_image)
    ImageView songImage;
    @BindView(R.id.blur_background)
    ImageView blurBackground;
    @BindView(R.id.song_seekbar)
    SeekBar songSeekbar;
    @BindView(R.id.shuffle)
    ImageButton shuffleButton;
    @BindView(R.id.play_prev)
    ImageButton playPrev;
    @BindView(R.id.play)
    ImageButton playButton;
    @BindView(R.id.play_next)
    ImageButton playNext;
    @BindView(R.id.repeat)
    ImageButton repeatButton;

    Bitmap bitmap;
    private boolean isPause;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.now_playing_layout);
        ButterKnife.bind(this);
        initView();
        progressBar = songSeekbar;
        progressBar.setMax((int) MusicServiceUtils.duration());
        progressBar.postDelayed(mUpdateCircularProgress, 10);
        songSeekbar.setOnSeekBarChangeListener(this);
    }
    private void initView() {
        songTitle.setText(MusicServiceUtils.getTrackName());
        songArtist.setText(MusicServiceUtils.getArtistName());
        loadBackground();
        isPause = !MusicServiceUtils.isPlaying();
        setPlayButton();
//        Glide.with(getApplicationContext()).load(MusicServiceUtils.getDescPic()).placeholder(R.drawable.navigation_view_background).bitmapTransform(new BlurTransformation(getApplicationContext(),25)).into(blurBackground);
    }

    private void loadBackground() {
        Glide.with(getApplicationContext()).load(MusicServiceUtils.getDescPic()).asBitmap().into(new SimpleTarget<Bitmap>(500,500) {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                setBackBitmap(resource, Bitmap.Config.ARGB_8888, bitmap);
            }
            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                Log.d(Tag,"onLoadFailed");
                Glide.with(getApplicationContext()).load(R.drawable.navigation_view_background).asBitmap().into(new SimpleTarget<Bitmap>(500,500) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        setBackBitmap(resource, Bitmap.Config.ARGB_8888,bitmap);
                    }
                });
            }
        });
    }

    private void setBackBitmap(Bitmap resource, Bitmap.Config rgb565, Bitmap bitmap) {
        bitmap = Bitmap.createBitmap(resource.getWidth(), resource.getHeight(), rgb565);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(resource, 0, 0, paint);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            try {
                bitmap = RSBlur.blur(getApplicationContext(), bitmap, 20);
            } catch (RSRuntimeException e) {
                bitmap = FastBlur.blur(bitmap, 20, true);
            }
        } else {
            bitmap = FastBlur.blur(bitmap, 20, true);
        }
        songImage.setImageBitmap(resource);
        blurBackground.setImageBitmap(bitmap);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        Drawable drawable = content.getBackground();
//        drawable
        if(bitmap!=null){
            bitmap.recycle();
            bitmap = null;
        }
        progressBar=null;
    }
    @OnClick(R.id.play_next)
    void pressNext(View v) {
        MusicServiceUtils.next();
    }

    @OnClick(R.id.play)
    void pressPlay(View v) {
        if(isPause){
            MusicServiceUtils.play();
        }else {
            MusicServiceUtils.pause();
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        progressBar = null;
    }


    @Override
    protected void musicPause() {
        isPause = true;
        super.musicPause();
        setPlayButton();
    }
    private void setPlayButton(){
        if(isPause){
            playButton.setImageResource(R.drawable.ic_play);
        }else {
            playButton.setImageResource(R.drawable.ic_pause);
        }
    }

    @Override
    protected void musicPlay() {
        isPause = false;
        super.musicPlay();
        setPlayButton();
    }

    @Override
    protected void freshPlay() {
        super.freshPlay();
        freshPlayBar();
    }
    private void freshPlayBar() {
        songTitle.setText(MusicServiceUtils.getTrackName());
        songArtist.setText(MusicServiceUtils.getArtistName());
        loadBackground();
        progressBar.setMax((int) MusicServiceUtils.duration());
        progressBar.postDelayed(mUpdateCircularProgress, 10);
        Glide.with(getApplicationContext()).load(MusicServiceUtils.getDescPic()).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                setBackBitmap(resource, Bitmap.Config.ARGB_8888, bitmap);
            }
        });
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        MusicServiceUtils.seek(seekBar.getProgress());
    }
}
