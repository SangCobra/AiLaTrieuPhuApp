package com.sang.ailatrieuphu.presentation.view.features.highscore;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.sang.ailatrieuphu.codebase.fagmentbase.BaseFragment;
import com.sang.ailatrieuphu.databinding.HighScoreFragmentBinding;

public class FragmentHighScore extends BaseFragment<HighScoreFragmentBinding, HighScoreViewModel> {
    private HighScoreAdapter adapter;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.initializeData().subscribe(success->{

        }, t -> {
            t.printStackTrace();
        });
    }

    @Override
    public HighScoreFragmentBinding createViewBinding(LayoutInflater inflater, ViewGroup container) {
        return HighScoreFragmentBinding.inflate(inflater, container, false);
    }

    @Override
    public HighScoreViewModel createViewModel() {
        return new ViewModelProvider(this).get(HighScoreViewModel.class);
    }

    @Override
    public void initializeData() {

    }

    @Override
    public void initializeViews() {
        adapter = new HighScoreAdapter(viewModel.getData());

        binding.lsScores.setAdapter(adapter);
        binding.lsScores.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

    }

    @Override
    public void initializeEvents() {

    }

    @Override
    public void initializeComponents() {

    }
}
