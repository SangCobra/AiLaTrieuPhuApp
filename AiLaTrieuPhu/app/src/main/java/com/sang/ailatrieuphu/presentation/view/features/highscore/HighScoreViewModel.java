package com.sang.ailatrieuphu.presentation.view.features.highscore;

import com.sang.ailatrieuphu.codebase.BaseViewModel;
import com.sang.ailatrieuphu.data.QuestionsDBMgr;
import com.sang.ailatrieuphu.data.entity.HighScore;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HighScoreViewModel extends BaseViewModel {
    private final ArrayList<HighScore> lsHighScore = new ArrayList<>();
    public Single<Object> initializeData(){
        return Single.create(
                emitter -> {
                    lsHighScore.clear();
                    lsHighScore.addAll( QuestionsDBMgr.getInstance().getQuestionDatabase().questionDao().getLsHighScore());
                    emitter.onSuccess(true);
                }
        ).observeOn(AndroidSchedulers.mainThread())
         .subscribeOn(Schedulers.io());
    }

    public ArrayList<HighScore> getData(){
        return lsHighScore;
    }
}
