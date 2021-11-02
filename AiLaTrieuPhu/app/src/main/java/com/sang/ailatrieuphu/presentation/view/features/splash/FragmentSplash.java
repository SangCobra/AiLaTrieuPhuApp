package com.sang.ailatrieuphu.presentation.view.features.splash;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sang.ailatrieuphu.R;
import com.sang.ailatrieuphu.codebase.fagmentbase.BaseViewBindingFragment;
import com.sang.ailatrieuphu.databinding.SplashFragmentBinding;

import org.jetbrains.annotations.NotNull;

public class FragmentSplash extends BaseViewBindingFragment<SplashFragmentBinding> {
    @Override
    public SplashFragmentBinding createViewBinding(LayoutInflater inflater, ViewGroup container) {
        return SplashFragmentBinding.inflate(inflater, container, false);
    }

    @Override
    public void initializeData() {

    }

    @Override
    public void initializeViews() {

    }

    @Override
    public void initializeEvents() {

    }

    @Override
    public void initializeComponents() {
        Animation animation = AnimationUtils.loadAnimation(this.getContext(), R.anim.prg_loading_rotate);
        binding.prgLoading.startAnimation(animation);

    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                findNavController().navigate(R.id.actSplashHome);
            }
        }, 3000);
    }
}
