package com.sang.ailatrieuphu.presentation.view.dialog;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.sang.ailatrieuphu.codebase.dialogbase.BaseDialog;
import com.sang.ailatrieuphu.codebase.onclicklistener.OnButtonClickListener;
import com.sang.ailatrieuphu.databinding.TimeOutDialogBinding;

import org.jetbrains.annotations.NotNull;

public class TimeOutDialog extends BaseDialog<TimeOutDialogBinding> {
    private OnButtonClickListener listener;

    public TimeOutDialog(@NonNull @NotNull Context context) {
        super(context);
    }

    @Override
    protected TimeOutDialogBinding createView() {
        return TimeOutDialogBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initializeViews() {

    }

    @Override
    protected void initializeEvents() {
        binding.btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onButtonClicked();
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    public void setOnBtnCloseListener(OnButtonClickListener onBtnCloseListener){
        listener = onBtnCloseListener;
    }
}
