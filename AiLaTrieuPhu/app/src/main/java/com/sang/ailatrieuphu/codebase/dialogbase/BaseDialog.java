package com.sang.ailatrieuphu.codebase.dialogbase;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewbinding.ViewBinding;

public abstract class BaseDialog<VB extends ViewBinding> extends Dialog implements View.OnClickListener {
    protected VB binding;

    public BaseDialog(@NonNull Context context) {
        super(context);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         binding = createView();
        setContentView(binding.getRoot());
        initializeViews();
        initializeEvents();
    }


    protected abstract VB createView();

    protected abstract void initializeViews();

    protected abstract void initializeEvents();
}
