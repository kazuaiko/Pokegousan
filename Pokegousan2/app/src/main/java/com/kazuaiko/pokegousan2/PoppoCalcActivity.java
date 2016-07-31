package com.kazuaiko.pokegousan2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class PoppoCalcActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Handler mHandler;

    //現在のレベルのスピナー
    Spinner current_spinner;
    //目標のレベルのスピナー
    Spinner target_spinner;

    //経験値テキスト
    TextView exp_text;

    //ポッポテキスト
    TextView poppo_text;

    //現在のレベルの値
    int current_lv;
    //目標のレベルの値
    int target_lv;

    int total_exp = 0;

    ListView lv;

    //目標のレベルのスピナー
    TextView result_text;

    //目標のレベルのスピナー
    int[] exp_tabal = {1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000,
            10000, 10000, 10000, 15000, 20000, 20000, 20000, 25000, 25000, 50000,
            75000, 100000, 125000, 150000, 190000, 200000, 250000, 300000, 350000, 500000,
            500000, 750000, 1000000, 1250000, 1500000, 2000000, 2500000, 3000000, 5000000};


    String[] lst_exp = {"Lv1-2 1000", "Lv2-3 2000", "Lv3-4 3000", "Lv4-5 4000","Lv5-6 5000", "Lv6-7 6000", "Lv7-8 7000", "Lv8-9 8000", "Lv9-10 9000", "Lv10-11 10000",
            "Lv11-12 10000", "Lv12-13 10000", "Lv13-14 10000", "Lv14-15 15000", "Lv15-16 20000", "Lv16-17 20000", "Lv17-18 20000", "Lv18-19 25000", "Lv19-20 25000", "Lv20-21 50000",
            "Lv21-22 75000", "Lv22-23 100000", "Lv23-24 125000", "Lv24-25 150000", "Lv25-26 190000", "Lv26-27 200000", "Lv27-28 250000", "Lv28-29 300000", "Lv29-30 350000", "Lv30-31 500000",
            "Lv31-32 500000", "Lv32-33 750000", "Lv33-34 1000000", "Lv34-35 1250000", "Lv35-36 1500000", "Lv36-37 2000000", "Lv37-38 2500000", "Lv38-39 3000000", "Lv39-40 5000000"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poppo_calc);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //ハンドラを生成
//        mHandler = new Handler();
        mHandler = new Handler(Looper.getMainLooper());

        //現在のレベルのスピナー
        final Spinner current_spinner = (Spinner) findViewById(R.id.current_spinner);
        //目標のレベルのスピナー
        target_spinner = (Spinner) findViewById(R.id.target_spinner);

        //経験値テキスト
        exp_text = (TextView) findViewById(R.id.exp_text);
        //ポッポテキスト
        poppo_text  = (TextView) findViewById(R.id.poppo_text);

        lv = (ListView) findViewById(R.id.listView1);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1, lst_exp){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView view = (TextView)super.getView(position, convertView, parent);
                view.setTextSize( 20 );
                return view;
            }
        };
        lv.setAdapter(adapter);

        //現在のレベルのスピナーの操作設定
        current_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner spinner = (Spinner) parent;
                // 選択したアイテムを取得
                String item = (String) spinner.getSelectedItem();
                //目標のレベルを超えた場合にはスピナーに設定
                if( target_spinner.getSelectedItemPosition() < position){
                    target_spinner.setSelection(position);
                }
                current_lv =  Integer.valueOf(item).intValue();
                item = (String) target_spinner.getSelectedItem();
                target_lv =  Integer.valueOf(item).intValue();

                // ポッポ計算開始
                PoppoCalc();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {// アイテムを選択しなかったとき
            }
        });

        //目標のレベルのスピナーの操作設定
        target_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner spinner = (Spinner) parent;
                // 選択したアイテムを取得
                String item = (String) spinner.getSelectedItem();
                //目標のレベルを超えた場合にはスピナーに設定
                if( current_spinner.getSelectedItemPosition() > position){
                    current_spinner.setSelection(position);
                }
                target_lv =  Integer.valueOf(item).intValue();
                item = (String) current_spinner.getSelectedItem();
                current_lv =  Integer.valueOf(item).intValue();

                // ポッポ計算開始
                PoppoCalc();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {// アイテムを選択しなかったとき
            }
        });
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
        getMenuInflater().inflate(R.menu.poppo_calc, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        // インテントの生成
        // ポッポ計算機
        Intent intent = new Intent(PoppoCalcActivity.this, MainActivity.class);
        startActivity(intent);
        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            // URLをひらく
            //ポケストップGOを開く
            String url = "http://pokestop.link/";

            // 追加
            String packageName = CustomTabsHelper.getPackageNameToUse(this);

            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            CustomTabsIntent customTabsIntent = builder.build();

            // 追加
            customTabsIntent.intent.setPackage(packageName);

            customTabsIntent.launchUrl(this, Uri.parse(url));
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void PoppoCalc(){
        int lv;

        total_exp = 0;

        // ログで確認
        Log.v("spinner current_lv", String.valueOf(current_lv));
        Log.v("spinner target_lv", String.valueOf(target_lv) );

        // レベル差を求める
        lv = target_lv - current_lv;
        if( lv <= 0 ){
            Log.v("PoppoCalc(NG) lv",  String.valueOf(lv));
            //必要経験値をポッポ進化の経験値で割って回数算出
            exp_text.setText("必要な経験値：--");
            poppo_text.setText("必要なポッポ捕獲：--");
            return;
        }
        Log.v("PoppoCalc(OK) lv",  String.valueOf(lv));

        // 経験値を加算
        //exp = exp_tabal[target_lv];
        //Log.v("PoppoCalc exp", String.valueOf(exp) );

        for( int i = target_lv  ; i > current_lv ;i-- ){
            Log.v("PoppoCalc exp.add", String.valueOf(exp_tabal[i-2]) );
            total_exp += exp_tabal[i-2];
            Log.v("PoppoCalc exp.total", String.valueOf(total_exp) );
        }

        //必要経験値をポッポ進化の経験値で割って回数算出
        int sinkacnt = total_exp / 800;

        //３匹捕まえる(300exp)と１回進化(500exp)できることを算出
        int poppocnt = sinkacnt * 3;
        Log.v("PoppoCal　sinkacnt", String.valueOf(sinkacnt) );
        Log.v("PoppoCal　poppocnt", String.valueOf(poppocnt) );

        //必要経験値をポッポ進化の経験値で割って回数算出
        exp_text.setText("必要な経験値："+String.valueOf(total_exp));
        poppo_text.setText("必要なポッポ捕獲："+String.valueOf(poppocnt)+"匹");
    }
}
