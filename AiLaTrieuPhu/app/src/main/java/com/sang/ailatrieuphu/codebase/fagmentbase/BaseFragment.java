package com.sang.ailatrieuphu.codebase.fagmentbase;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import com.sang.ailatrieuphu.codebase.BaseViewModel;

public abstract class BaseFragment<
        VB extends ViewBinding,
        VM extends BaseViewModel
        > extends BaseCoreFragment {
    protected VB binding;
    protected VM viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = createViewModel();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = createViewBinding(inflater, container);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public abstract VB createViewBinding(LayoutInflater inflater, ViewGroup container);

    public abstract VM createViewModel();
}

