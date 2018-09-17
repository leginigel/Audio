package com.study.audio.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.study.audio.MusicData;
import com.study.audio.MyMediaPlayer;
import com.study.audio.R;

import java.io.IOException;
import java.util.List;

import static android.media.AudioManager.STREAM_MUSIC;

public class AudioPlayerActivity extends AppCompatActivity {

    private List<MusicData> musicDataList;
    private TextView titleTextView;
    private TextView artistTextView;
    private int currentPosition;
    private ImageView albumImg;
    private ImageView previousImg;
    private ImageView playImg;
    private ImageView nextImg;
    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;
    private int volume = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_player);

        musicDataList = getIntent().getParcelableArrayListExtra("musicList");
        currentPosition = getIntent().getIntExtra("currentPosition", 0);

        albumImg = findViewById(R.id.album_art);
        titleTextView = findViewById(R.id.song_title);
        artistTextView = findViewById(R.id.song_artist);
        previousImg = findViewById(R.id.button_previous);
        playImg = findViewById(R.id.button_play);
        nextImg = findViewById(R.id.button_next);

        final ClickListener clickListener = new ClickListener();
        previousImg.setOnClickListener(clickListener);
        playImg.setOnClickListener(clickListener);
        nextImg.setOnClickListener(clickListener);

        Button b = (Button) findViewById(R.id.button_vol);
        b.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                PopupWindow popup = new PopupWindow(AudioPlayerActivity.this);
                View layout = getLayoutInflater().inflate(R.layout.volbar, null);
                popup.setContentView(layout);

                // Set content width and height
                popup.setHeight(300);
                popup.setWidth(300);
                popup.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                // Closes the popup window when touch outside of it - when looses focus
                popup.setOutsideTouchable(true);
                popup.setFocusable(true);

                // Show anchored to button
                //popup.showAsDropDown(v);
                popup.showAtLocation(v, Gravity.LEFT, 0, 200);

                SeekBar volbar1 = (SeekBar) layout.findViewById(R.id.seekBar_vol);
                volbar1.setBackgroundColor(Color.RED);
                //volbar1.setProgress(currentProgr);
                volbar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress,
                                                  boolean fromUser) {
                        //currentProgr=progress;
                    }
                });

            }
        });

        audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        if (audioManager != null) {
            volume = audioManager.getStreamVolume(STREAM_MUSIC);
        }
        mediaPlayer = MyMediaPlayer.getMediaPlayer();
        mediaPlayer.setVolume(volume, volume);
        playMusic();
    }

    void setMusicDisplay(){
        RequestOptions requestOptions =
                new RequestOptions().centerCrop()
                        .placeholder(R.drawable.ic_album_black_24dp);
        Glide.with(this)
                .load(musicDataList.get(currentPosition).getAlbumId())
                .apply(requestOptions)
                .into(albumImg);

        titleTextView.setText(musicDataList.get(currentPosition).getTitle());
        artistTextView.setText(musicDataList.get(currentPosition).getArtist());
    }

    void playMusic() {
            setMusicDisplay();
            mediaPlayer.reset();
            try {
                mediaPlayer.setDataSource(musicDataList.get(currentPosition).getPath());
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
//            playImg.setSelected(false);
    }

    private class ClickListener implements View.OnClickListener {

        // TODO: 2018/9/3 Implement the Functionality of Music Player

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_previous:
                    if (currentPosition > 0) {
                        currentPosition--;
                        playMusic();
                    } else {
                        Toast.makeText(AudioPlayerActivity.this, "No previous song~~~", Toast.LENGTH_SHORT).show();
                        mediaPlayer.stop();
                    }
                    break;
                case R.id.button_play:
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
//                        playImg.setSelected(true);
                    } else {
                        mediaPlayer.start();
//                        playImg.setSelected(false);
                    }
                    break;
                case R.id.button_next:
                    if (currentPosition < musicDataList.size()-1) {
                        currentPosition++;
                        playMusic();
                    } else {
                        Toast.makeText(AudioPlayerActivity.this, "No Next song~~~", Toast.LENGTH_SHORT).show();
                        mediaPlayer.stop();
                    }
                    break;
            }
        }
    }

    // TODO: 2018/9/3 Create the Class to Load the Content of Music
}
