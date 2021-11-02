package com.sang.ailatrieuphu.codebase.activitybase;

import androidx.lifecycle.ViewModel;
import androidx.viewbinding.ViewBinding;

public abstract class BaseViewModelActivity<
        VB extends ViewBinding,
        VM extends ViewModel>
        extends BaseActivity<VB> {
    protected VM viewModel;

    @Override
    protected void onPostOnCreate() {
        viewModel = createViewModel();
    }

    public abstract VM createViewModel();

}