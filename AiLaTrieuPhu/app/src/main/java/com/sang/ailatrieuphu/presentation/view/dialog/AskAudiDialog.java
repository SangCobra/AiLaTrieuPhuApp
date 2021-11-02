package com.sang.ailatrieuphu.presentation.view.dialog;

import android.content.Context;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sang.ailatrieuphu.codebase.dialogbase.BaseDialog;
import com.sang.ailatrieuphu.codebase.onclicklistener.OnButtonClickListener;
import com.sang.ailatrieuphu.databinding.AskingAudiDialogBinding;

import java.util.ArrayList;
import java.util.Random;

public class AskAudiDialog extends BaseDialog<AskingAudiDialogBinding> {
    private OnButtonClickListener onButtonClickListener;
    private int trueAnswerIndex;

    private int[] lsPrc = new int[4];

    public AskAudiDialog(Context context) {
        super(context);
    }

    @Override
    protected AskingAudiDialogBinding createView() {
        return AskingAudiDialogBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initializeViews() {
        initializeAudientsPrc();
        showAudientsOpiBar();
        setCanceledOnTouchOutside(false);
        setCancelable(false);
    }

    @Override
    protected void initializeEvents() {

        binding.btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClickListener.onButtonClicked();
            }
        });
    }

    @Override
    public void onClick(View v) {

    }




    public void setOnCloseButtonClickListener(OnButtonClickListener onButtonClickListener){
        this.onButtonClickListener = onButtonClickListener;
    }

    public void setTrueAnswerIndex(int trueAnswerIndex) {
        this.trueAnswerIndex = trueAnswerIndex;
    }

    private void showAudientsOpiBar() {

            int mulNum = getContext().getResources().getDisplayMetrics().densityDpi/ DisplayMetrics.DENSITY_DEFAULT;

            ViewGroup.LayoutParams bar1LP = binding.barAudientA.getLayoutParams();
            ViewGroup.LayoutParams bar2LP = binding.barAudientB.getLayoutParams();
            ViewGroup.LayoutParams bar3LP = binding.barAudientC.getLayoutParams();
            ViewGroup.LayoutParams bar4LP = binding.barAudientD.getLayoutParams();

        final int[] i = {0};
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (i[0] <= 61){

                    if (i[0] <= lsPrc[0]){
                        bar1LP.height = i[0]*mulNum;
                        binding.barAudientA.setLayoutParams(bar1LP);
                        binding.txtPrcA.setText(i[0] + "%");
                    }

                    if (i[0] <= lsPrc[1]){
                        bar2LP.height = i[0]*mulNum;
                        binding.barAudientB.setLayoutParams(bar2LP);
                        binding.txtPrcB.setText(i[0] + "%");
                    }

                    if (i[0] <= lsPrc[2]){
                        bar3LP.height = i[0]*mulNum;
                        binding.barAudientC.setLayoutParams(bar3LP);
                        binding.txtPrcC.setText(i[0] + "%");
                    }
                    if (i[0] <= lsPrc[3]){
                        bar4LP.height = i[0]*mulNum;
                        binding.txtPrcD.setText(i[0] + "%");
                        binding.barAudientD.setLayoutParams(bar4LP);
                    }


                    i[0]++;
                }else {
                    binding.btnClose.setVisibility(View.VISIBLE);
                    return;
                }
                handler.postDelayed(this, 50);
            }
        }, 500);

    }


    private void initializeAudientsPrc(){
        Random random = new Random();
        int trueCase = 45 + random.nextInt(16);
        int case1 = random.nextInt(30);
        int case2 = random.nextInt(10);
        int case3 = 100 - case1 - case2 - trueCase;

        int[] lsCase = {case1, case2, case3};

        int n = 0;
        for (int i = 0; i < 4; i++){
            if (i == (trueAnswerIndex - 1)){
                lsPrc[i] = trueCase;
            }else {
                lsPrc[i] = lsCase[n];
                n++;
            }
        }

    }
}
