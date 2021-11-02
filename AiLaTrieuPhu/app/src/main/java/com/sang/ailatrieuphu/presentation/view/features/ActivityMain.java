package com.sang.ailatrieuphu.presentation.view.features;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.ViewModelProvider;

import com.sang.ailatrieuphu.codebase.activitybase.BaseViewModelActivity;
import com.sang.ailatrieuphu.databinding.MainActivityBinding;
import com.sang.ailatrieuphu.presentation.view.key.Key;

import io.reactivex.rxjava3.functions.Consumer;

public class ActivityMain extends BaseViewModelActivity<MainActivityBinding, MainViewModel> {

    @Override
    public MainViewModel createViewModel() {
        return new ViewModelProvider(this).get(MainViewModel.class);
    }

    @Override
    protected MainActivityBinding getActivityBinding() {
        return MainActivityBinding.inflate(getLayoutInflater());
    }

    @Override
    public void initializeView() {

    }

    @Override
    public void initializeComponent() {
        if (getBgMusicState() != 0){
            viewModel.bgMusicRunnable(true);
        }

        if (getMusicState() != 0){
            viewModel.isRunnable(true);
        }
    }

    @Override
    public void initializeEvents() {

    }

    @Override
    public void initializeData() {
        viewModel.initializeData(getApplicationContext());
        viewModel.fetChingDataFromDB().subscribe(aBoolean -> {
            viewModel.dataFetched(true);
        }, t->{
            t.printStackTrace();
        });
    }

    public MainViewModel getViewModel(){
        return viewModel;
    }

    public int getBgMusicState(){
        SharedPreferences sharedPreferences = this.getSharedPreferences("app", Context.MODE_PRIVATE);
        return sharedPreferences.getInt(Key.BG_MUSIC_STATE, -1);
    }

    public int getMusicState(){
        SharedPreferences sharedPreferences = getSharedPreferences("app", Context.MODE_PRIVATE);
        return sharedPreferences.getInt(Key.MUSIC_STATE, -1);
    }
}
