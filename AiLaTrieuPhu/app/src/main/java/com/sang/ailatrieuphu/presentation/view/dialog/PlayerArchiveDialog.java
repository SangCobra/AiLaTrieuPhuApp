package com.sang.ailatrieuphu.presentation.view.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.sang.ailatrieuphu.codebase.dialogbase.BaseDialog;
import com.sang.ailatrieuphu.codebase.onclicklistener.OnButtonClickListener;
import com.sang.ailatrieuphu.databinding.PlayerArchiveDialogBinding;

import org.jetbrains.annotations.NotNull;

public class PlayerArchiveDialog extends BaseDialog<PlayerArchiveDialogBinding> {
    private OnButtonClickListener listener;
    private String money;

    public PlayerArchiveDialog(@NonNull @NotNull Context context) {
        super(context);
    }

    @Override
    protected PlayerArchiveDialogBinding createView() {
        return PlayerArchiveDialogBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initializeViews() {
        setCanceledOnTouchOutside(false);
        binding.txtMoney.setText(money);
    }

    @Override
    protected void initializeEvents() {
        binding.btnAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.edtName.getText().toString().length() != 0){
                    listener.onButtonClicked();
                    dismiss();
                }

            }
        });
    }

    @Override
    public void onClick(View v) {

    }


    public void setOnClickAgreeBtn(OnButtonClickListener buttonClickListener){
        listener = buttonClickListener;
    }

    public void setPlayerMoney(String money){
        this.money = money;
    }

    public String getPlayerName(){
        String playerName;
        playerName = String.valueOf(binding.edtName.getText());
        return playerName;
    }
}