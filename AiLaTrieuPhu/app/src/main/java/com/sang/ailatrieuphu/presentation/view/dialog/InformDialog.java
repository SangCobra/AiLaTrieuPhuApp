package com.sang.ailatrieuphu.presentation.view.dialog;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.sang.ailatrieuphu.R;
import com.sang.ailatrieuphu.codebase.dialogbase.BaseDialog;
import com.sang.ailatrieuphu.codebase.onclicklistener.OnButtonClickListener;
import com.sang.ailatrieuphu.databinding.InformDialogBinding;

import org.jetbrains.annotations.NotNull;

public class InformDialog extends BaseDialog<InformDialogBinding> {
    private String content;
    private String btnYes;
    private String btnNo;

    private OnButtonClickListener onButtonYesClickListener;
    private OnButtonClickListener onButtonNoClickListener;

    public InformDialog( Context context, String content, String btnYes, String btnNo) {
        super(context);
        this.content = content;
        this.btnYes = btnYes;
        this.btnNo = btnNo;
    }

    @Override
    protected InformDialogBinding createView() {
        return InformDialogBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initializeViews() {
        binding.txtInformContent.setText(content);
        binding.btnInform1.setText(btnYes);
        binding.btnInform2.setText(btnNo);
    }

    @Override
    protected void initializeEvents() {
     binding.btnInform2.setOnClickListener(this);
     binding.btnInform1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnInform1:{
                onButtonYesClickListener.onButtonClicked();
                dismiss();
                break;
            }

            case R.id.btnInform2:{
                onButtonNoClickListener.onButtonClicked();
                dismiss();
                break;
            }
        }
    }

    public void setOnButtonYesClickListener(OnButtonClickListener listener){
        onButtonYesClickListener = listener;
    }

    public void setOnButtonNoClickListener(OnButtonClickListener listener){
        onButtonNoClickListener = listener;
    }
}
