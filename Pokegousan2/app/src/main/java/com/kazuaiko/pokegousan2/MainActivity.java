package com.kazuaiko.pokegousan2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<FeedItem>>, NavigationView.OnNavigationItemSelectedListener {
    private ListView mListView;
    private ArrayAdapter<FeedItem> mListAdapter;

    private List<FeedItem> mItemList;

    private static final int FEED_LOADER_ID =1;
    public final static String EXTRA_LINK = "com.kazuaiko.pokegousan.EXTRA_LINK";
    public final static String EXTRA_TITLE = "com.kazuaiko.pokegousan.EXTRA_TITLE";

    static boolean flgDat = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //リスト対応
        mListView = (ListView)findViewById(R.id.listView);
        mItemList = new ArrayList<>();
        mListAdapter = new MyAdapter(this, mItemList);
        mListView.setAdapter(mListAdapter);

        // リストアイテムの間の区切り線を非表示にする
        mListView.setDivider(null);

        //読み込み
        getSupportLoaderManager().initLoader(FEED_LOADER_ID, null, this);

/*
        //フローティングボタン設定
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView list = (ListView) parent;
                FeedItem item = (FeedItem) list.getItemAtPosition(position);
                Log.d("setOnItemClickListener", item.getlink());
                // インテントの生成
                Intent intent = new Intent(MainActivity.this, BrowserRss.class);
                intent.putExtra(EXTRA_LINK, item.getlink());
                intent.putExtra(EXTRA_TITLE, item.getTitle());
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //読み込み
            Log.d("MainActivity", "onOptionsItemSelected action_settings");
            getSupportLoaderManager().initLoader(FEED_LOADER_ID, null, this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public Loader<List<FeedItem>> onCreateLoader(int id, Bundle args) {
        Log.d("MainActivity", "onCreateLoader");
        Toast.makeText(MainActivity.this, "onCreateLoader", Toast.LENGTH_SHORT).show();
        FeedAsyncTaskLoader loader = new FeedAsyncTaskLoader(this);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<List<FeedItem>> loader, List<FeedItem> data) {
        Log.d("MainActivity", "onLoadFinished");
        mListAdapter = new MyAdapter(this, data);
        //mListAdapter.addAll(data);
        Toast.makeText(MainActivity.this, "onLoadFinished", Toast.LENGTH_SHORT).show();
        mListView.setAdapter(mListAdapter);
        flgDat = true;
    }

    @Override
    public void onLoaderReset(Loader<List<FeedItem>> loader) {
        Log.d("MainActivity", "onLoaderReset");
        Toast.makeText(MainActivity.this, "onLoaderReset", Toast.LENGTH_SHORT).show();
    }
}
