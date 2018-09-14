package com.study.audio.ui;

import android.R.drawable;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
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


public class SongListAdapter extends RecyclerView.Adapter<SongListAdapter.ViewHolder>{

    private Context mContext;
    private MediaMetadataCompat media;
    private List<MusicData> musicDataList;

    public SongListAdapter(List<MusicData> musicDataList) {
        // TODO: 2018/9/3 Constructor with Music Structure

        this.musicDataList = musicDataList;
    }

    @NonNull
    @Override
    public SongListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View songView = inflater.inflate(R.layout.cardview_song, parent, false);

        return new SongListAdapter.ViewHolder(songView);
    }

    @Override
    public void onBindViewHolder(@NonNull SongListAdapter.ViewHolder holder, int position) {

        // TODO: 2018/8/20 Load the Media Data

        RequestOptions requestOptions =
                new RequestOptions().centerCrop()
                        .placeholder(R.drawable.ic_album_black_24dp);

        Glide.with(mContext)
                .load(musicDataList.get(position).getAlbumId())
                .apply(requestOptions)
                .into(holder.songImageView);

//        holder.songImageView.setImageResource(drawable.ic_media_play);
//        holder.songImageView.setBackgroundColor(Color.GRAY);
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

        public CardView songCardView;
        public TextView songTextView;
        public TextView songArtistTextView;
        public TextView songTimeTextView;
        public ImageView songImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            songCardView = (CardView) itemView.findViewById(R.id.card_view);
            songTextView = (TextView) itemView.findViewById(R.id.card_song_name);
            songImageView = (ImageView) itemView.findViewById(R.id.card_song_img);
            songArtistTextView = (TextView) itemView.findViewById(R.id.card_song_artist);
            songTimeTextView = (TextView) itemView.findViewById(R.id.card_song_time);

            songCardView.setOnClickListener(this);
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