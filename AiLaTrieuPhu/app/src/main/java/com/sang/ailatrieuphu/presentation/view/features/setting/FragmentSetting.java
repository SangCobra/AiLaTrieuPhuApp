package com.sang.ailatrieuphu.presentation.view.features.setting;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.sang.ailatrieuphu.R;
import com.sang.ailatrieuphu.codebase.fagmentbase.BaseViewBindingFragment;
import com.sang.ailatrieuphu.databinding.SettingFragmentBinding;
import com.sang.ailatrieuphu.presentation.view.features.ActivityMain;
import com.sang.ailatrieuphu.presentation.view.features.MainViewModel;
import com.sang.ailatrieuphu.presentation.view.key.Key;

public class FragmentSetting extends BaseViewBindingFragment<SettingFragmentBinding> {
    private MainViewModel mainViewModel;
    
    private boolean isBgMusicPlaying = false;
    private boolean isMusicPlaying = false;

    @Override
    public SettingFragmentBinding createViewBinding(LayoutInflater inflater, ViewGroup container) {
        return SettingFragmentBinding.inflate(inflater);
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = ((ActivityMain)getActivity()).getViewModel();
    }

    @Override
    public void initializeData() {

    }

    @Override
    public void initializeViews() {
        if (getBgMusicState() == 0){
            binding.btnBgMusic.setImageResource(R.drawable.toggle_button_off);
            isBgMusicPlaying = false;
        }else {
            binding.btnBgMusic.setImageResource(R.drawable.toggle_button_on);
            isBgMusicPlaying = true;
        }

        if (getMusicState() == 0){
            binding.btnMusic.setImageResource(R.drawable.toggle_button_off);
            mainViewModel.isRunnable(false);
            isMusicPlaying = false;
        }else {
            mainViewModel.isRunnable(true);
            binding.btnMusic.setImageResource(R.drawable.toggle_button_on);
            isMusicPlaying = true;
        }
    }

    @Override
    public void initializeEvents() {
        binding.btnBgMusic.setOnClickListener(this);
        binding.btnMusic.setOnClickListener(this);
    }

    @Override
    public void initializeComponents() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.btnBgMusic :{
                if (isBgMusicPlaying){
                    mainViewModel.stopBgMusic();
                    binding.btnBgMusic.setImageResource(R.drawable.toggle_button_off);
                    saveBgMusicStateToStorage(0);
                    isBgMusicPlaying = false;
                }else {
                    mainViewModel.bgMusicRunnable(true);
                    mainViewModel.startBackgroundMusic(getContext());
                    binding.btnBgMusic.setImageResource(R.drawable.toggle_button_on);
                    saveBgMusicStateToStorage(1);
                    isBgMusicPlaying = true;
                }

                break;
            }

            case R.id.btnMusic :{
                if (isMusicPlaying){
                    binding.btnMusic.setImageResource(R.drawable.toggle_button_off);
                    saveMusicStateToStorage(0);
                    mainViewModel.isRunnable(false);
                    isMusicPlaying = false;
                }else {
                    binding.btnMusic.setImageResource(R.drawable.toggle_button_on);
                    saveMusicStateToStorage(1);
                    mainViewModel.isRunnable(true);
                    isMusicPlaying = true;
                }
                break;
            }
        }
    }

    public void saveBgMusicStateToStorage(int state){
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("app", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(Key.BG_MUSIC_STATE, state);
        editor.apply();
    }

    public int getBgMusicState(){
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("app", Context.MODE_PRIVATE);
        return sharedPreferences.getInt(Key.BG_MUSIC_STATE, -1);
    }

    public void saveMusicStateToStorage(int state){
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("app", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(Key.MUSIC_STATE, state);
        editor.apply();
    }

    public int getMusicState(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("app", Context.MODE_PRIVATE);
        return sharedPreferences.getInt(Key.MUSIC_STATE, -1);
    }
}
