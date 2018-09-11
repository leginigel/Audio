package com.study.audio.ui;

import android.R.drawable;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.study.audio.R;

public class SongActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_song);
//        toolbar.setBackgroundColor(Color.BLACK);
        toolbar.setNavigationIcon(drawable.ic_menu_revert);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), AudioPlayerActivity.class);
                startActivity(i);
            }
        });

//        AppBarLayout apl = (AppBarLayout) findViewById(R.id.app_bar);
//        ViewGroup.LayoutParams appBarLayoutParams = apl.getLayoutParams();
//        appBarLayoutParams.height = 600;
//        apl.setLayoutParams(appBarLayoutParams);

        // TODO: 2018/9/3 Paste the Album Text and Image

        ImageView collapseImg = (ImageView) findViewById(R.id.img_collapse);
        collapseImg.setBackgroundColor(Color.GRAY);
        collapseImg.setImageResource(drawable.ic_media_play);

        RecyclerView rv = (RecyclerView) findViewById(R.id.rv_song);
        //Adapter
        SongTextAdapter songTextAdapter = new SongTextAdapter();
        //LayoutManager
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        rv.setAdapter(songTextAdapter);
        rv.setLayoutManager(linearLayoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                Log.i("OptionsSelect","Return"+item.getItemId());
        }
        return super.onOptionsItemSelected(item);
    }
}
