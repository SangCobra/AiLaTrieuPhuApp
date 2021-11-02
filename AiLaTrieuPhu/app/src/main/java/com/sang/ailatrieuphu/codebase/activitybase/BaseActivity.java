package com.sang.ailatrieuphu.codebase.activitybase;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

public abstract class BaseActivity<VB extends ViewBinding>
        extends AppCompatActivity
        implements BaseView, View.OnClickListener {
    protected VB binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(getClass().getName(), "onCreate()...");

        onPostOnCreate();

        binding = getActivityBinding();
        setContentView(binding.getRoot());

        initializeData();
        initializeView();
        initializeComponent();
        initializeEvents();

    }

    @Override
    protected void onDestroy() {
        Log.d(getClass().getName(), "onDestroy()...");
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
    }

    protected void onPostOnCreate() {
        // DO_NO_THING
    }

    protected abstract VB getActivityBinding();

    protected void setOnClickListener(View view) {
        view.setOnClickListener(this);
    }

}