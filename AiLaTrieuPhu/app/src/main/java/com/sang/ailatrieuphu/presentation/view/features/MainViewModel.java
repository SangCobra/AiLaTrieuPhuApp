package com.sang.ailatrieuphu.presentation.view.features;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;

import androidx.lifecycle.ViewModel;

import com.sang.ailatrieuphu.R;
import com.sang.ailatrieuphu.data.QuestionsDBMgr;
import com.sang.ailatrieuphu.data.entity.Question;

import java.util.ArrayList;
import java.util.Random;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleOnSubscribe;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends ViewModel {
    private QuestionsDBMgr dbMgr;

    private MediaPlayer bgMusicPlayer = new MediaPlayer();
    private MediaPlayer musicPlayer = new MediaPlayer();

    private boolean isBgMusicRunnable = false;

    private Random random = new Random();
    private int musicId = 0;
    private int[] lsMp3QuesId = new int[15];

    private boolean isDataFetched = false;

    private boolean isRunnable;
    private ArrayList<Question>[] lsLevelQuestion = new ArrayList[15];

    public void initializeData(Context context){
             dbMgr = QuestionsDBMgr.getInstance();
             dbMgr.initialize(context);
    }

    public Single<Boolean> fetChingDataFromDB(){

        return Single.create((SingleOnSubscribe<Boolean>) emitter -> {
            for (int i = 0; i < 15; i++){
                lsLevelQuestion[i] = (ArrayList<Question>) QuestionsDBMgr.getInstance().getQuestionDatabase().questionDao().getLevelQuestion(i + 1);
            }
            emitter.onSuccess(true);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    public ArrayList<Question>[] getData(){
        return lsLevelQuestion;
    }

    public void dataFetched(boolean value){
        isDataFetched = value;
    }

    public boolean isDataFetched(){
        return isDataFetched;
    }

    public void startBackgroundMusic(Context context){
        if (bgMusicPlayer != null){
            bgMusicPlayer.stop();
        }
        System.out.println("-------------------------isBgMusicRunnable: " + isBgMusicRunnable);
            if (isBgMusicRunnable){
                bgMusicPlayer = MediaPlayer.create(context, R.raw.bgmusic);
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        bgMusicPlayer.start();
                        if (isBgMusicRunnable){
                            handler.post(this);
                        }

                    }
                });
            }
    }

    public void stopBgMusic(){
        isBgMusicRunnable = false;
        bgMusicPlayer.stop();
    }

    public void stopBgMusicTemp(){
        bgMusicPlayer.stop();
    }

    public void  bgMusicRunnable(boolean value){
        isBgMusicRunnable = value;
    }


    public void startReadymp3(Context context){
       stopMusicMp3();

        switch (random.nextInt(3)){
            case 0:{
                musicId = R.raw.ready;
                break;
            }
            case 1:{
                musicId = R.raw.ready_b;
                break;
            }
            case 2:{
                musicId = R.raw.ready_c;
                break;
            }
        }

        startMp3(context, musicId);
    }

    public void startQuesMp3(Context context, int currentLevel){
        bgMusicPlayer.stop();
        stopMusicMp3();

        lsMp3QuesId[0] = arrange2Mp3Id( R.raw.ques1, R.raw.ques1_b);
        lsMp3QuesId[1] = arrange2Mp3Id(R.raw.ques2, R.raw.ques2_b);
        lsMp3QuesId[2] = arrange2Mp3Id(R.raw.ques3, R.raw.ques3_b);
        lsMp3QuesId[3] = arrange2Mp3Id(R.raw.ques4, R.raw.ques4_b);
        lsMp3QuesId[4] = arrange2Mp3Id(R.raw.ques5, R.raw.ques5_b);
        lsMp3QuesId[5] = R.raw.ques6;
        lsMp3QuesId[6] = arrange2Mp3Id(R.raw.ques7, R.raw.ques7_b);
        lsMp3QuesId[7] = arrange2Mp3Id(R.raw.ques8, R.raw.ques8_b);
        lsMp3QuesId[8] = arrange2Mp3Id(R.raw.ques9, R.raw.ques9_b);
        lsMp3QuesId[9] = R.raw.ques10;
        lsMp3QuesId[10] = R.raw.ques11;
        lsMp3QuesId[11] = R.raw.ques12;
        lsMp3QuesId[12] = R.raw.ques13;
        lsMp3QuesId[13] = R.raw.ques14;
        lsMp3QuesId[14] = R.raw.ques15;
        System.out.println("---------------------Runnable: " + isRunnable);
        startMp3(context, lsMp3QuesId[currentLevel - 1]);
    }

    public void startLoseGameMp3(Context context){
        stopMusicMp3();
        musicId = arrange2Mp3Id(R.raw.lose, R.raw.lose2);
        startMp3(context, musicId);
    }

    public void startLoseAnswerMp3(Context context, int answerIndex){
        stopMusicMp3();
        switch (answerIndex){
            case 1:{
                musicId = arrange2Mp3Id(R.raw.lose_a, R.raw.lose_a2);
                break;
            }

            case 2:{
                musicId = arrange2Mp3Id(R.raw.lose_b, R.raw.lose_b2);
                break;
            }

            case 3:{
                musicId = arrange2Mp3Id(R.raw.lose_c, R.raw.lose_c2);
                break;
            }

            case 4:{
                musicId = arrange2Mp3Id(R.raw.lose_d, R.raw.lose_d2);
                break;
            }
        }

        startMp3(context, musicId);
    }

    public void startRulesMp3(Context context){
        stopMusicMp3();
        startMp3(context, arrange2Mp3Id(R.raw.luatchoi_b, R.raw.luatchoi_c));
    }

    public void startAnswerNowMp3(Context context){
        stopMusicMp3();
        startMp3(context, arrange3Mp3Id(R.raw.ans_now1, R.raw.ans_now2, R.raw.ans_now3));
    }

    public void startAnswerMp3(Context context, int answerIndex){
        stopMusicMp3();
        switch (answerIndex){
            case 1:{
                startMp3(context, arrange2Mp3Id(R.raw.ans_a, R.raw.ans_a2));
                break;
            }

            case 2:{
                startMp3(context, arrange2Mp3Id(R.raw.ans_b, R.raw.ans_b2));
                break;
            }

            case 3:{
                startMp3(context, arrange2Mp3Id(R.raw.ans_c, R.raw.ans_c2));
                break;
            }

            case 4:{
                startMp3(context, arrange2Mp3Id(R.raw.ans_d, R.raw.ans_d2));
                break;
            }
        }

    }

    public void startTrueAnswerMp3(Context context, int answerIndex){
        stopMusicMp3();
        switch (answerIndex){
            case 1:{
                startMp3(context, arrange3Mp3Id(R.raw.true_a, R.raw.true_a2, R.raw.true_a3));
                break;
            }

            case 2:{
                startMp3(context, arrange3Mp3Id(R.raw.true_b, R.raw.true_b2, R.raw.true_b3));
                break;
            }

            case 3:{
                startMp3(context, arrange3Mp3Id(R.raw.true_c, R.raw.true_c2, R.raw.true_c3));
                break;
            }

            case 4:{
                startMp3(context, arrange2Mp3Id(R.raw.true_d2, R.raw.true_d3));
                break;
            }
        }
    }

    public void start5050Mp3(Context context){
        stopMusicMp3();
        musicId = arrange2Mp3Id(R.raw.sound5050, R.raw.sound5050_2);
        musicPlayer = MediaPlayer.create(context, musicId);
        musicPlayer.start();
    }

    public void startAskAudientsMp3(Context context){
        stopMusicMp3();
        musicPlayer = MediaPlayer.create(context, R.raw.khan_gia);
        musicPlayer.start();
    }

    public void startCallProsMp3(Context context){
        stopMusicMp3();
        musicPlayer = MediaPlayer.create(context, R.raw.hoi_y_kien_chuyen_gia_01b);
        musicPlayer.start();
    }

    public void startHelpCallMp3(Context context){
        stopMusicMp3();
        startMp3(context, arrange2Mp3Id(R.raw.help_call, R.raw.help_callb));
    }

    public void startBeginMp3(Context context){
        stopMusicMp3();
        startMp3(context, arrange2Mp3Id(R.raw.gofind, R.raw.gofind_b));
    }

    public void startReachLimetonesMp3(Context context, int level){
        stopMusicMp3();
        if (level < 10){
            startMp3(context, arrange2Mp3Id(R.raw.chuc_mung_vuot_moc_01_1, R.raw.chuc_mung_vuot_moc_01_0));
        }else {
            startMp3(context, R.raw.chuc_mung_vuot_moc_02_0);
        }
    }

    public void startBackgroundMusicMp3(Context context){
        stopMusicMp3();
        startMp3(context, arrange3Mp3Id(R.raw.background_music, R.raw.background_music_b, R.raw.background_music_c));
    }


    public void startBestPlayerMp3(Context context){
        stopMusicMp3();
        startMp3(context, R.raw.best_player);
    }

    public void startImportantMp3(Context context){
        stopMusicMp3();
        startMp3(context, R.raw.important);
    }

    public void startOutOfTimeMp3(Context context){
        stopMusicMp3();
        startMp3(context, R.raw.out_of_time);
    }

    public void startTouchMp3(Context context){
        stopMusicMp3();
        startMp3(context, R.raw.touch_sound);
    }

    public void startCallMp3(Context context){
        stopMusicMp3();
        startMp3(context, R.raw.call);
    }

    public int arrange2Mp3Id(int id1, int id2){

        if (random.nextBoolean()){
            return id1;
        }else {
             return id2;
        }
    }

    public int arrange3Mp3Id(int id1, int id2, int id3){
        switch (random.nextInt(3)){
            case 0:{
                return id1;
            }
            case 1:{
                return id2;

            }

            case 2:{
                return id3;
            }

            default:{
                return 0;
            }
        }
    }

    public void stopMusicMp3(){
        if (musicPlayer != null){
            musicPlayer.stop();
        }
    }

    private void startMp3(Context context, int id){
        if (!isRunnable){
            return;
        }
        musicPlayer = MediaPlayer.create(context, id);
        musicPlayer.start();
    }

    public void isRunnable(boolean value){
        isRunnable = value;
    }
}
