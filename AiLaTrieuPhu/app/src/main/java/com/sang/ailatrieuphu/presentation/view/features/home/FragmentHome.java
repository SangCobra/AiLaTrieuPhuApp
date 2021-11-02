package com.sang.ailatrieuphu.presentation.view.features.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.sang.ailatrieuphu.R;
import com.sang.ailatrieuphu.codebase.fagmentbase.BaseFragment;
import com.sang.ailatrieuphu.databinding.HomeFragmentBinding;
import com.sang.ailatrieuphu.presentation.view.dialog.InfoDialog;
import com.sang.ailatrieuphu.presentation.view.features.ActivityMain;
import com.sang.ailatrieuphu.presentation.view.features.MainViewModel;

public class FragmentHome extends BaseFragment<HomeFragmentBinding, HomeViewModel> {
    private MainViewModel mainViewModel;

    @Override
    public HomeFragmentBinding createViewBinding(LayoutInflater inflater, ViewGroup container) {
        return HomeFragmentBinding.inflate(inflater, container, false);
    }

    @Override
    public HomeViewModel createViewModel() {
        return new ViewModelProvider(this).get(HomeViewModel.class);
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
        startBgRotateAnimation();
    }

    @Override
    public void initializeEvents() {
        binding.btnSetting.setOnClickListener(this);
        binding.btnInfor.setOnClickListener(this);
        binding.btnRank.setOnClickListener(this);
        binding.btnPlay.setOnClickListener(this);
    }

    @Override
    public void initializeComponents() {
        mainViewModel.startBackgroundMusic(getContext());
    }

    private void startBgRotateAnimation(){
        Animation animation = AnimationUtils.loadAnimation(this.getContext(), R.anim.bg_home_rotate);
        binding.imgHomeRotateBg.startAnimation(animation);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.btnInfor:{
                InfoDialog informDialog = new InfoDialog(getContext());

                informDialog.show();
                break;
            }
            case R.id.btnRank:{
                findNavController().navigate(R.id.actHomeHighScore);
                break;
            }
            case R.id.btnSetting:{
                findNavController().navigate(R.id.actHomeSetting);
                break;
            }
            case R.id.btnPlay:{
                findNavController().navigate(R.id.actHomePlay);
                break;
            }
        }
    }
}
