package com.sunny.face.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.sunny.face.R;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.tv_image)
    ImageView tvImage;
    @InjectView(R.id.fab_menu1)
    FloatingActionButton fabMenu1;
    @InjectView(R.id.fab_menu2)
    FloatingActionButton fabMenu2;
    @InjectView(R.id.fab_menu)
    FloatingActionMenu fabMenu;

    private static final int MAIN_ACTIVITY_REQUEST_CODE = 2;

    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    startActivity(new Intent(MainActivity.this, FaceTrackActivity.class));
                    break;
                case 2:
                    startActivityForResult(new Intent(MainActivity.this, FaceDetectActivity.class), MAIN_ACTIVITY_REQUEST_CODE);
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        initToolBar(false, toolbar, R.string.app_name);
        initFloatingActionBar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                startActivity(new Intent(this, AboutActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initFloatingActionBar() {
        fabMenu.setClosedOnTouchOutside(true);
        fabMenu1.setOnClickListener(this);
        fabMenu2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_menu1:
                fabMenu.close(false);
                mHandler.sendEmptyMessageDelayed(1, 50);
                break;
            case R.id.fab_menu2:
                fabMenu.close(false);
                mHandler.sendEmptyMessageDelayed(2, 50);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
