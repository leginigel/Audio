package com.study.audio.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.study.audio.MusicData;
import com.study.audio.R;

import java.util.ArrayList;
import java.util.List;

public class SongTextAdapter extends RecyclerView.Adapter<SongTextAdapter.ViewHolder>{

    private Context mContext;
    private MediaMetadataCompat media;
    private List<MusicData> musicDataList;


    public SongTextAdapter(List<MusicData> musicDataList) {
        // TODO: 2018/9/3 Constructor with Music Structure
                this.musicDataList = musicDataList;
    }

    @NonNull
    @Override
    public SongTextAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View songView = inflater.inflate(R.layout.textview_song, parent, false);

        return new SongTextAdapter.ViewHolder(songView);
    }

    @Override
    public void onBindViewHolder(@NonNull SongTextAdapter.ViewHolder holder, int position) {

        // TODO: 2018/8/20 Load the Media Data

        holder.songArtistTextView.setText(musicDataList.get(position).getArtist());

        holder.songTextView.setText(musicDataList.get(position).getTitle());
        holder.songTextView.setTextColor(Color.BLACK);
        holder.songTextView.setEllipsize(TextUtils.TruncateAt.END);
        holder.songTextView.setSingleLine(true);
    }

    @Override
    public int getItemCount() {
        return musicDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView songTextView;
        public TextView songArtistTextView;
        public TextView songTimeTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            songTextView = (TextView) itemView.findViewById(R.id.text_song_name);
            songArtistTextView = (TextView) itemView.findViewById(R.id.text_song_artist);
            songTimeTextView = (TextView) itemView.findViewById(R.id.text_song_time);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            // TODO: 2018/9/3 Pass the Music Content and Start the Activity

            Intent i = new Intent(mContext, AudioPlayerActivity.class);
            i.putParcelableArrayListExtra("musicList", (ArrayList<? extends Parcelable>) musicDataList);
            i.putExtra("currentPosition", getLayoutPosition());
            mContext.startActivity(i);
        }
    }

}
