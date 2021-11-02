package com.sang.ailatrieuphu.codebase.fagmentbase;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.sang.ailatrieuphu.codebase.onclicklistener.OnButtonClickListener;

public abstract class BaseCoreFragment extends Fragment implements BaseView, View.OnClickListener {
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeViews();
        initializeComponents();
        initializeEvents();
        initializeData();
    }

    public NavController findNavController(){
        return Navigation.findNavController(getView());
    }

    public void setOnClickListener(View view){
        view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }

    public void showMessage(String message, String pBtnText, String nBtnText){
        showMessage(message, new OnButtonClickListener() {
            @Override
            public void onButtonClicked() {

            }
        }, new OnButtonClickListener() {
            @Override
            public void onButtonClicked() {

            }
        }, pBtnText, nBtnText);
    }


    public void showMessage(String message, OnButtonClickListener positiveBtn, OnButtonClickListener negativeBtn, String positiveBtnText, String negativeBtnText){
        new AlertDialog.Builder(getContext())
                .setMessage(message)
                .setPositiveButton(positiveBtnText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        positiveBtn.onButtonClicked();
                    }
                })
                .setNegativeButton(negativeBtnText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        negativeBtn.onButtonClicked();
                    }
                })
                .create()
                .show();
    }
}
