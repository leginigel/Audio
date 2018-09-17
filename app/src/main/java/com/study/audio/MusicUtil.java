package com.study.audio;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MusicUtil {
    private static final String TAG = MusicUtil.class.getName();

    public static List<MusicData> getMusicData(Context context){

        Log.i(TAG,"getMusicData");
        List<MusicData> musicData = new ArrayList<>();
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = null;
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        String [] projections = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DISPLAY_NAME
        };
        String order = MediaStore.Audio.Media.ARTIST;

        try {
            cursor = resolver.query(uri, projections, null,
                    null, null);
            if (cursor != null) {

                Log.i(TAG, "Fetch Data...");

                while (cursor.moveToNext()) {
                    MusicData data = new MusicData(
                            cursor.getLong(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            "",
                            cursor.getLong(5),
                            cursor.getString(6),
                            cursor.getString(7)
                    );
                    Log.v("title: ", cursor.getString(1));
                    Log.v("artist: ", cursor.getString(2));
                    Log.v("album: ", cursor.getString(3));
                    Log.v("path: ", cursor.getString(6));
                    Log.v("dpname: ", cursor.getString(7));

                    musicData.add(data);
                }
            } else
                Log.i(TAG, "Fetch NO Data");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            cursor.close();
        }

        Uri albumUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        String secOrder = MediaStore.Audio.AlbumColumns.ARTIST;
        String[] secProjections = {
                MediaStore.Audio.AlbumColumns.ALBUM_ART,
                MediaStore.Audio.AlbumColumns.ALBUM
        };
        Cursor secCursor = null;
        try {
            secCursor = context.getContentResolver().query(albumUri, secProjections,
                    null, null, null);

            ArrayList<String> tempAlbumArtList = new ArrayList<>();
            ArrayList<String> tempAlbumList = new ArrayList<>();
            if (secCursor != null) {
                int i = 0;
                while (secCursor.moveToNext()) {
                    tempAlbumArtList.add(secCursor.getString(0));
                    tempAlbumList.add(secCursor.getString(1));
                    i++;
                }
            }

            List<MusicData> mediaPathList = new ArrayList<>(musicData);

            for (MusicData mediaData : mediaPathList) {
                int j = tempAlbumList.indexOf(mediaData.getAlbum());
                if(j != -1)
                    mediaData.setAlbumId(tempAlbumArtList.get(j));
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            secCursor.close();
        }


        return musicData;
    }


}
