package com.sunny.face.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.sunny.face.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by SunFusheng on 2015/5/29.
 */
public class AboutActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.inject(this);

        initToolBar(true, toolbar, R.string.action_about);
    }

}
