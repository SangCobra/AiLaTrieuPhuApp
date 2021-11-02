package com.sang.ailatrieuphu.presentation.view.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;

import androidx.annotation.NonNull;

import com.sang.ailatrieuphu.R;
import com.sang.ailatrieuphu.codebase.dialogbase.BaseDialog;
import com.sang.ailatrieuphu.codebase.onclicklistener.OnButtonClickListener;
import com.sang.ailatrieuphu.databinding.AskingProDialogBinding;

import org.jetbrains.annotations.NotNull;

public class CallProsDialog extends BaseDialog<AskingProDialogBinding> {
    private OnButtonClickListener onEngiBtnClickListener;
    private OnButtonClickListener onProsBtnClickListener;
    private OnButtonClickListener onDocBtnClickListener;
    private OnButtonClickListener onRepBtnClickListener;

    private OnButtonClickListener onCloseBtnClickListener;



    public CallProsDialog(@NonNull @NotNull Context context) {
        super(context);
    }

    @Override
    protected AskingProDialogBinding createView() {
        return AskingProDialogBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initializeViews() {
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCanceledOnTouchOutside(false);
        setCancelable(false);
    }

    @Override
    protected void initializeEvents() {
        binding.btnDoc.setOnClickListener(this);
        binding.btnClose.setOnClickListener(this);
        binding.btnEngi.setOnClickListener(this);
        binding.btnReporter.setOnClickListener(this);
        binding.btnPros.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnDoc:{
                onDocBtnClickListener.onButtonClicked();
                break;
            }
            case R.id.btnEngi:{
                onEngiBtnClickListener.onButtonClicked();
                break;
            }
            case R.id.btnReporter:{
                onRepBtnClickListener.onButtonClicked();
                break;
            }
            case R.id.btnPros:{
                onProsBtnClickListener.onButtonClicked();
                break;
            }
            case R.id.btnClose:{
                onCloseBtnClickListener.onButtonClicked();
                break;
            }
        }
    }

    public void setOnEngiBtnClickListener(OnButtonClickListener onEngiBtnClickListener) {
        this.onEngiBtnClickListener = onEngiBtnClickListener;
    }

    public void setOnProsBtnClickListener(OnButtonClickListener onProsBtnClickListener) {
        this.onProsBtnClickListener = onProsBtnClickListener;
    }

    public void setOnDocBtnClickListener(OnButtonClickListener onDocBtnClickListener) {
        this.onDocBtnClickListener = onDocBtnClickListener;
    }

    public void setOnRepBtnClickListener(OnButtonClickListener onRepBtnClickListener) {
        this.onRepBtnClickListener = onRepBtnClickListener;
    }

    public void setOnCloseBtnClickListener(OnButtonClickListener onCloseBtnClickListener) {
        this.onCloseBtnClickListener = onCloseBtnClickListener;
    }

    public void showAnswer(int answerIndex, String title,int drawableId){
        setTrueAnswer(answerIndex);
        setTitleView(title, drawableId);

        binding.btnPros.setVisibility(View.GONE);
        binding.btnEngi.setVisibility(View.GONE);
        binding.btnReporter.setVisibility(View.GONE);
        binding.btnDoc.setVisibility(View.GONE);


        binding.loutShowTitle.setVisibility(View.VISIBLE);
    }

    private void setTrueAnswer(int answerIndex){
        String answer = "";

        switch (answerIndex){
            case 1:{
                answer = "A";
                break;
            }

            case 2:{
                answer = "B";
                break;
            }

            case 3:{
                answer = "C";
                break;
            }

            case 4:{
                answer = "D";
                break;
            }
        }

        binding.txtShowAnswer.setText("Theo tôi đáp án đúng là " + answer);
    }

    public void setTitleView(String title, int drawableId){
        binding.ivTitle.setImageResource(drawableId);
        binding.txtTitle.setText(title);
    }

}
