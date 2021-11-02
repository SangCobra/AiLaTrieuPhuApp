package com.sang.ailatrieuphu.codebase.fagmentbase;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.sang.ailatrieuphu.codebase.BaseViewModel;

public abstract class BaseViewModelFragment<VM extends BaseViewModel> extends BaseCoreFragment {
   protected VM viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = createViewModel();
    }

    public abstract  VM createViewModel();
}
