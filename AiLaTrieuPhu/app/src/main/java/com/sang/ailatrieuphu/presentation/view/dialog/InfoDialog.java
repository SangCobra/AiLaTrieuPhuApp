package com.sang.ailatrieuphu.presentation.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.location.GnssAntennaInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.sang.ailatrieuphu.codebase.dialogbase.BaseDialog;
import com.sang.ailatrieuphu.databinding.InfoDialogBinding;
import com.sang.ailatrieuphu.databinding.InformDialogBinding;

import org.jetbrains.annotations.NotNull;

public class InfoDialog extends BaseDialog<InfoDialogBinding> {


    public InfoDialog(@NonNull @NotNull Context context) {
        super(context);
    }

    @Override
    protected InfoDialogBinding createView() {
        return InfoDialogBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initializeViews() {

    }

    @Override
    protected void initializeEvents() {

    }

    @Override
    public void onClick(View v) {

    }
}
