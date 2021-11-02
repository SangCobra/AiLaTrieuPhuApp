package com.sang.ailatrieuphu.presentation.view.features.play;

import com.sang.ailatrieuphu.codebase.BaseViewModel;
import com.sang.ailatrieuphu.data.QuestionsDBMgr;
import com.sang.ailatrieuphu.data.entity.HighScore;
import com.sang.ailatrieuphu.data.entity.Question;

import java.util.ArrayList;
import java.util.Random;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.core.SingleOnSubscribe;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PlayViewModel extends BaseViewModel {
    private boolean isPlaying = false;
    private boolean isQuestionChanged = false;
    private boolean isWrongAnswersHidden = false;
    private boolean isAsked = false;
    private boolean isCalled = false;
    private boolean isChoosen = false;
    private boolean timeIsOut = false;

    private Question currentQuestion;
    private int currentLevel = 1;

    private String money = "0";

    private ArrayList<Question>[] lsLevelQuestion;


    public void initializeData(ArrayList<Question>[] lsQuestion){
        lsLevelQuestion = lsQuestion;
    }

    public void updateLevel(){
        currentLevel++;
    }

    public int getCurrentLevel(){
        return currentLevel;
    }

    public void playingState(boolean value){
        isPlaying = value;
    }

    public boolean isPlaying(){
        return isPlaying;
    }

    public Question getRamdomQuestion(){
        isChoosen = false;
        Random random = new Random();
        int questionIndex = random.nextInt(lsLevelQuestion[currentLevel - 1].size());
        currentQuestion = lsLevelQuestion[currentLevel - 1].get(questionIndex);
        return currentQuestion;
    }

    public Question changeQuestion(){
        isChoosen = false;
        isQuestionChanged = true;
        return getRamdomQuestion();
    }

    public boolean isQuestionChanged(){
        return isQuestionChanged;
    }

    public int[] wrongAnswerIndex(){
        int[] wrongAnswerIndexs = new int[2];
        int n = 0;
        for(int i = 1; i <= 4; i++){
            if (i != currentQuestion.getTrueCase() && n < 2){
                wrongAnswerIndexs[n] = i;
                n++;
            }
        }

        isWrongAnswersHidden = true;
        return wrongAnswerIndexs;
    }

    public Single<Boolean> insertHighScore(HighScore highScore){
        return Single.create((SingleOnSubscribe<Boolean>) emitter -> {
            QuestionsDBMgr.getInstance().getQuestionDatabase().questionDao().addHighScore(highScore);
            emitter.onSuccess(true);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public int getCurrentScore(){
        if (money == "0"){
            return 0;
        }
        String s = money.substring(0, money.length() - 4);
        int m = Integer.parseInt(s) * 1000;
        return m;
    }

    public String getCurrenMoney(){
        return money;
    }

    public void updateMoney(){
        switch (currentLevel - 1){
            case 1:{
                money = "200,000";
                break;
            }
            case 2:{
                money = "400,000";
                break;
            }
            case 3:{
                money = "600,000";
                break;
            }
            case 4:{
                money = "1000,000";
                break;
            }
            case 5:{
                money = "2000,000";
                break;
            }
            case 6:{
                money = "3000,000";
                break;
            }
            case 7:{
                money = "6000,000";
                break;
            }
            case 8:{
                money = "10,000,000";
                break;
            }
            case 9:{
                money = "14,000,000";
                break;
            }
            case 10:{
                money = "22,000,000";
                break;
            }
            case 11:{
                money = "30,000,000";
                break;
            }
            case 12:{
                money = "40,000,000";
                break;
            }
            case 13:{
                money = "60,000,000";
                break;
            }
            case 14:{
                money = "85,000,000";
                break;
            }
            case 15:{
                money = "150,000,000";
                break;
            }
        }
    }

    public void timeOut(boolean value){
        timeIsOut = value;
    }

    public boolean isTimeOut(){
        return timeIsOut;
    }

    public void choosen(boolean value){
        isChoosen = true;
    }

    public boolean isChoosen(){
        return isChoosen;
    }

    public Question getCurrentQuestion(){
        return currentQuestion;
    }

    public boolean isWrongAnswersHidden(){
        return isWrongAnswersHidden;
    }

    public void askingAudients(boolean value){
        isAsked = value;
    }

    public void callPros(boolean value){
        isCalled = value;
    }

    public boolean isAsked(){
        return isAsked;
    }

    public boolean isCalled(){
        return isCalled;
    }

    public int getTrueCaseIndex(){
        return currentQuestion.getTrueCase();
    }
}
