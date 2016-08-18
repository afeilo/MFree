package com.xiefei.openmusicplayer.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.RSRuntimeException;
import android.speech.tts.Voice;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.drawable.DrawableWrapper;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.ViewTarget;
import com.xiefei.openmusicplayer.BaseActivity;
import com.xiefei.openmusicplayer.MusicServiceUtils;
import com.xiefei.openmusicplayer.R;
import com.xiefei.openmusicplayer.ui.widget.glide.BlurTransformation;
import com.xiefei.openmusicplayer.ui.widget.glide.FastBlur;
import com.xiefei.openmusicplayer.ui.widget.glide.RSBlur;
import com.xiefei.openmusicplayer.ui.widget.glide.TargetRelativeLayout;
import com.xiefei.openmusicplayer.utils.OpenMusicPlayerUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xiefei on 16/7/31.
 */
public class NowPlayingActivity extends BaseActivity{
    @BindView(R.id.now_playing_content)
    RelativeLayout content;
    @BindView(R.id.song_title)
    TextView songTitle;
    @BindView(R.id.song_artist)
    TextView songArtist;
    @BindView(R.id.song_image)
    ImageView songImage;
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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.now_playing_layout);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        songTitle.setText(MusicServiceUtils.getTrackName());
        songArtist.setText(MusicServiceUtils.getArtistName());
        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.navigation_view_background);
        bitmap = RSBlur.blur(NowPlayingActivity.this, bitmap, 20);
        content.setBackground(new BitmapDrawable(getResources(),bitmap));
    }

    public void initContent(){
        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... voids) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.navigation_view_background);
//                try {
                    bitmap = RSBlur.blur(NowPlayingActivity.this, bitmap, 20);
//                } catch (RSRuntimeException e) {
//                    bitmap = FastBlur.blur(bitmap, 10, true);
//                }
                return bitmap;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                content.setBackground(new BitmapDrawable(getResources(),bitmap));
            }
        }.execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        Drawable drawable = content.getBackground();
//        drawable
        bitmap.recycle();
        bitmap = null;
    }

    @Override
    protected void freshPlay() {
        super.freshPlay();
    }

}
