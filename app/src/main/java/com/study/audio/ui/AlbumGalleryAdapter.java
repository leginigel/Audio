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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.study.audio.MusicData;
import com.study.audio.MusicUtil;
import com.study.audio.R;

import java.util.ArrayList;
import java.util.List;


class AlbumGalleryAdapter extends RecyclerView.Adapter<AlbumGalleryAdapter.ViewHolder>{

    private Context mContext;
    private MediaMetadataCompat media;
    private List<String> mAlbumList, mAlbumArtList, mArtistList;
    private List<MusicData> mAlbumMusicList;

    public AlbumGalleryAdapter(List<MusicData> musicDataList) {
        // TODO: 2018/9/3 Constructor with Music Structure
        this.mAlbumArtList = MusicUtil.mAlbumArtList;
        this.mAlbumList = MusicUtil.mAlbumList;
        mArtistList = new ArrayList<>();
        mAlbumMusicList = musicDataList;
        for (String album : mAlbumList) {
//            Log.d("mAlbumlist",album);
            String artist = null;
            String temp = null;
            int i = 0;
            for (MusicData mediaData : musicDataList) {
                temp = artist;
                if (mediaData.getAlbum().equals(album)) {
                    artist = mediaData.getArtist();
//                    Log.d("debug",temp+" "+artist+i);
                    if(temp!=null) {
                        if (!temp.equals(artist)) {
                            i++;
                        }
                    }
                }
            }

            if(i>0) artist = "Multiple Players";

            if(artist!=null)
                mArtistList.add(artist);
            else
                mArtistList.add("unknown");
        }
    }

    @NonNull
    @Override
    public AlbumGalleryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View albumView = inflater.inflate(R.layout.cardview_album, parent, false);

        return new AlbumGalleryAdapter.ViewHolder(albumView);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumGalleryAdapter.ViewHolder holder, int position) {

        // TODO: 2018/8/20 Load the Media Data

        String albumArt = mAlbumArtList.get(position);
        String album = mAlbumList.get(position);
        String artist = mArtistList.get(position);

        RequestOptions requestOptions =
                new RequestOptions().centerCrop()
                        .placeholder(R.drawable.ic_album_black_24dp);

        Glide.with(mContext)
                .load(albumArt)
                .apply(requestOptions)
                .into(holder.albumImageView);

        holder.albumImageView.setBackgroundColor(Color.GRAY);
        holder.albumArtistTextView.setText(artist);
        holder.albumTextView.setText(album);
        holder.albumTextView.setTextColor(Color.BLACK);
        holder.albumTextView.setSingleLine(true);
        holder.albumTextView.setEllipsize(TextUtils.TruncateAt.END);
    }

    @Override
    public int getItemCount() {
        return mAlbumArtList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public CardView albumCardView;
        public TextView albumTextView;
        public TextView albumArtistTextView;
        public ImageView albumImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            albumCardView = (CardView) itemView.findViewById(R.id.card_view);
            albumTextView = (TextView) itemView.findViewById(R.id.card_album_text);
            albumArtistTextView = (TextView) itemView.findViewById(R.id.card_album_artist);
            albumImageView = (ImageView) itemView.findViewById(R.id.card_album_img);

            albumCardView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            // TODO: 2018/9/3 Pass the Music Content and Start the Activity

            String album = mAlbumList.get(getLayoutPosition());
            List<MusicData> tempList = new ArrayList<>();
            for (MusicData tempMusic : mAlbumMusicList){
                if (tempMusic.getAlbum().equals(album)) {
                    tempList.add(tempMusic);
                }
            }

            Intent i = new Intent(mContext, SongActivity.class);
            i.putParcelableArrayListExtra("tempList", (ArrayList<? extends Parcelable>) tempList);
            i.putExtra("currentPosition", getLayoutPosition());
            mContext.startActivity(i);
        }
    }

}
