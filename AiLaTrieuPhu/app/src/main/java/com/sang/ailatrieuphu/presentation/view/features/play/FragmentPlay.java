package com.sang.ailatrieuphu.presentation.view.features.play;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import com.sang.ailatrieuphu.R;
import com.sang.ailatrieuphu.codebase.fagmentbase.BaseFragment;
import com.sang.ailatrieuphu.codebase.onclicklistener.OnButtonClickListener;
import com.sang.ailatrieuphu.data.entity.HighScore;
import com.sang.ailatrieuphu.data.entity.Question;
import com.sang.ailatrieuphu.databinding.MainActivityBinding;
import com.sang.ailatrieuphu.databinding.PlayFragmentBinding;
import com.sang.ailatrieuphu.presentation.view.dialog.AskAudiDialog;
import com.sang.ailatrieuphu.presentation.view.dialog.CallProsDialog;
import com.sang.ailatrieuphu.presentation.view.dialog.InformDialog;
import com.sang.ailatrieuphu.presentation.view.dialog.PlayerArchiveDialog;
import com.sang.ailatrieuphu.presentation.view.dialog.TimeOutDialog;
import com.sang.ailatrieuphu.presentation.view.features.ActivityMain;
import com.sang.ailatrieuphu.presentation.view.features.MainViewModel;

import org.jetbrains.annotations.NotNull;

import io.reactivex.rxjava3.functions.Consumer;

public class FragmentPlay extends BaseFragment<PlayFragmentBinding, PlayViewModel> {
    private InformDialog stopDialog;
    private InformDialog changeQuestionDialog;
    private AskAudiDialog askAudiDialog;
    private CallProsDialog callProsDialog;
    private PlayerArchiveDialog playerArchiveDialog;
    private InformDialog startDialog;
    private TimeOutDialog timeOutDialog;

    private CountDownTimer countDownTimer;
    private long timePoint;

    private MainViewModel mainViewModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel.playingState(true);
        mainViewModel = ((ActivityMain)getActivity()).getViewModel();
    }

    @Override
    public PlayFragmentBinding createViewBinding(LayoutInflater inflater, ViewGroup container) {
        return PlayFragmentBinding.inflate(inflater, container, false);
    }

    @Override
    public PlayViewModel createViewModel() {
        return new ViewModelProvider(this).get(PlayViewModel.class);
    }

    @Override
    public void initializeData() {
        fetchDataFromDB();
    }

    @Override
    public void initializeViews() {
        mainViewModel.stopBgMusicTemp();
        setFullScreenForSublayout();
        binding.getRoot().openDrawer(GravityCompat.START);
        mainViewModel.startRulesMp3(getContext());


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mainViewModel.startReadymp3(getContext());
            }
        }, 6200);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
               startDialog.show();
            }
        }, 6500);

    }

    @Override
    public void initializeEvents() {
        binding.btnPlayer.setOnClickListener(this);
        binding.btnStop.setOnClickListener(this);
        binding.btnChangeQuestion.setOnClickListener(this);
        binding.btn5050.setOnClickListener(this);
        binding.btnAskAudients.setOnClickListener(this);
        binding.btnCall.setOnClickListener(this);

        binding.btnAnswerA.setOnClickListener(this);
        binding.btnAnswerB.setOnClickListener(this);
        binding.btnAnswerC.setOnClickListener(this);
        binding.btnAnswerD.setOnClickListener(this);

        setOnClickForSublayout();

        setOnClickForStopDialog();
        setOnClickForChangeQuestionDialog();

        setOnAskAudientDialogCancel();

        setOnClickForAskAudientDialog();

        setOnClickForCallProsDialogBtn();

        setOnClickForPlayerArchive();

        setOnClickForStartDialog();

        setOnClickForTimeOutDialog();

        setOnDrawerLoutDraged();

    }

    @Override
    public void initializeComponents() {
        initializeDialog();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnPlayer:{
                binding.getRoot().openDrawer(GravityCompat.START);
                break;
            }

            case R.id.btnStop:{
                stopDialog.show();
                binding.btnStop.setImageResource(R.drawable.player_button_image_help_stop_active);
                break;
            }

            case R.id.btnChangeQuestion:{
                if (viewModel.isTimeOut()){
                    return;
                }
                if (viewModel.isQuestionChanged()){
                   break;
                }
                if (viewModel.getCurrentQuestion() == null){
                    return;
                }

                binding.btnChangeQuestion.setImageResource(R.drawable.player_button_image_help_change_question_active);
                changeQuestionDialog.show();
                break;
            }

            case R.id.btn5050:{
                if (viewModel.isTimeOut()){
                    return;
                }
                if (viewModel.isWrongAnswersHidden()){
                    break;
                }
                if (viewModel.getCurrentQuestion() == null){
                    return;
                }
                mainViewModel.start5050Mp3(getContext());
                countDownTimer.cancel();


                binding.btn5050.setImageResource(R.drawable.player_button_image_help_5050_x);
                int[] wrongAnswerIndexs = viewModel.wrongAnswerIndex();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setCountDownView(timePoint);
                        hideTheWrongAnswer(wrongAnswerIndexs[0]);
                        hideTheWrongAnswer(wrongAnswerIndexs[1]);
                    }
                }, 2000);
                break;
            }

            case R.id.btnAskAudients:{
                if (viewModel.isTimeOut()){
                    return;
                }
                if (viewModel.isAsked()){
                    break;
                }
                if (viewModel.getCurrentQuestion() == null){
                    return;
                }
                mainViewModel.startAskAudientsMp3(getContext());
                countDownTimer.cancel();
                binding.btnAskAudients.setImageResource(R.drawable.player_button_image_help_audience_active);
                viewModel.askingAudients(true);
                askAudiDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                askAudiDialog.setTrueAnswerIndex(viewModel.getTrueCaseIndex());
                askAudiDialog.show();
                break;
            }

            case R.id.btnCall:{
                if (viewModel.isTimeOut()){
                    return;
                }
                if (viewModel.isCalled()){
                    break;
                }
                if (viewModel.getCurrentQuestion() == null){
                    return;
                }
                mainViewModel.startHelpCallMp3(getContext());
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mainViewModel.startCallProsMp3(getContext());
                    }
                }, 1200);

                countDownTimer.cancel();
                viewModel.callPros(true);
                binding.btnCall.setImageResource(R.drawable.player_button_image_help_call_active);
                callProsDialog.show();
                break;
            }

            case R.id.btnAnswerA:{
                if (viewModel.isTimeOut()){
                    return;
                }
                if (viewModel.getCurrentQuestion() == null){
                    return;
                }
                if (viewModel.isChoosen()){
                    return;
                }
                onBtnAnswerAClicked();
                break;
            }
            case R.id.btnAnswerB:{
                if (viewModel.isTimeOut()){
                    return;
                }
                if (viewModel.getCurrentQuestion() == null){
                    return;
                }
                if (viewModel.isChoosen()){
                    return;
                }
                onBtnAnswerBClicked();
                break;
            }
            case R.id.btnAnswerC:{
                if (viewModel.isTimeOut()){
                    return;
                }
                if (viewModel.getCurrentQuestion() == null){
                    return;
                }
                if (viewModel.isChoosen()){
                    return;
                }
                onBtnAnswerCClicked();
                break;
            }
            case R.id.btnAnswerD:{
                if (viewModel.isTimeOut()){
                    return;
                }
                if (viewModel.getCurrentQuestion() == null){
                    return;
                }
                if (viewModel.isChoosen()){
                    return;
                }
                onBtnAnswerDClicked();
                break;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel.playingState(false);
    }

    private void onBtnAnswerAClicked(){
        doClickAnswer(binding.ivAnswerA, 1);
        if (viewModel.getTrueCaseIndex() == 1){
            doClickTrueAnswer(binding.ivAnswerA);
        }else {
            doClickWrongAnswer(binding.ivAnswerA);
        }
    }

    private void onBtnAnswerBClicked(){
        doClickAnswer(binding.ivAnswerB, 2);
        if (viewModel.getTrueCaseIndex() == 2){
            doClickTrueAnswer(binding.ivAnswerB);
        }else {
            doClickWrongAnswer(binding.ivAnswerB);
        }
    }

    private void onBtnAnswerCClicked(){
        doClickAnswer(binding.ivAnswerC, 3);
        if (viewModel.getTrueCaseIndex() == 3){
            doClickTrueAnswer(binding.ivAnswerC);
        }else {
            doClickWrongAnswer(binding.ivAnswerC);
        }
    }

    private void onBtnAnswerDClicked(){
        doClickAnswer(binding.ivAnswerD, 4);
        if (viewModel.getTrueCaseIndex() == 4){
            doClickTrueAnswer(binding.ivAnswerD);
        }else {
            doClickWrongAnswer(binding.ivAnswerD);
        }
    }

    private void doClickAnswer(ImageView imageView, int answerIndex){
        mainViewModel.startAnswerMp3(getContext(), answerIndex);
        imageView.setImageResource(R.drawable.player_answer_background_selected);
        viewModel.choosen(true);
        countDownTimer.cancel();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mainViewModel.startAnswerNowMp3(getContext());
            }
        }, 4000);
    }

    private void doClickTrueAnswer(ImageView imageView){
        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                viewModel.updateLevel();
                viewModel.updateMoney();

                int delayTime = 100;
                if (viewModel.getCurrentLevel() == 6 || viewModel.getCurrentLevel() == 11){
                    delayTime = 10500;
                }

                updateSubLayout();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        updateView();
                        mainViewModel.startQuesMp3(getContext(), viewModel.getCurrentLevel());                    }
                }, delayTime);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (viewModel.getCurrentLevel() == 5 || viewModel.getCurrentLevel() == 10 || viewModel.getCurrentLevel() == 15){
                            mainViewModel.startImportantMp3(getContext());
                        }
                    }
                }, 1500);
            }
        }, 12000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mainViewModel.startTrueAnswerMp3(getContext(), viewModel.getTrueCaseIndex());
                blinkTrueAnswer();
            }
        }, 8000);

    }

    private void doClickWrongAnswer(ImageView imageView){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                        mainViewModel.startLoseAnswerMp3(getContext(), viewModel.getTrueCaseIndex());
                        blinkTrueAnswer();
                        imageView.setImageResource(R.drawable.player_answer_background_wrong);
                        }
        }, 8000);


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (viewModel.getCurrentScore() == 0){
                    findNavController().popBackStack();
                }else {
                    playerArchiveDialog.setPlayerMoney(viewModel.getCurrenMoney());
                    playerArchiveDialog.show();
                    viewModel.timeOut(true);
                }
            }
        }, 12000);


    }

    private void setOnDrawerLoutDraged(){
       binding.getRoot().addDrawerListener(new DrawerLayout.DrawerListener() {
           @Override
           public void onDrawerSlide(@NonNull @NotNull View drawerView, float slideOffset) {

           }

           @Override
           public void onDrawerOpened(@NonNull @NotNull View drawerView) {

           }

           @Override
           public void onDrawerClosed(@NonNull @NotNull View drawerView) {

           }

           @Override
           public void onDrawerStateChanged(int newState) {
                        
           }
       });
    }

    private void blinkTrueAnswer(){
        ImageView imageView = new ImageView(getContext());
        switch (viewModel.getTrueCaseIndex()){
            case 1:{
                imageView = binding.ivAnswerA;
                break;
            }
            case 2:{
                imageView = binding.ivAnswerB;
                break;
            }
            case 3:{
                imageView = binding.ivAnswerC;
                break;
            }
            case 4:{
                imageView = binding.ivAnswerD;
                break;
            }
        }
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.blink_button);

        Handler handler = new Handler();
        ImageView finalImageView = imageView;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finalImageView.setImageResource(R.drawable.player_answer_background_true);
                finalImageView.startAnimation(animation);
            }
        }, 2000);

    }

    private void updateSubLayout(){
        Handler handler = new Handler();
        binding.getRoot().openDrawer(GravityCompat.START);
        ImageView[] lsIv = {
                binding.ltMoneyBoard.iv2tr, binding.ltMoneyBoard.iv4tr, binding.ltMoneyBoard.iv6tr,
                binding.ltMoneyBoard.iv1, binding.ltMoneyBoard.iv2, binding.ltMoneyBoard.iv3,
                binding.ltMoneyBoard.iv6, binding.ltMoneyBoard.iv10, binding.ltMoneyBoard.iv14,
                binding.ltMoneyBoard.iv22, binding.ltMoneyBoard.iv30, binding.ltMoneyBoard.iv40,
                binding.ltMoneyBoard.iv60, binding.ltMoneyBoard.iv85, binding.ltMoneyBoard.iv150
        };

        int currentLevel = viewModel.getCurrentLevel();
        if (currentLevel > 1){
            lsIv[currentLevel - 2].setVisibility(View.INVISIBLE);
        }
        lsIv[currentLevel - 1].setImageResource(R.drawable.player_image_money_curent);

        if (viewModel.getCurrentLevel() == 6 || viewModel.getCurrentLevel() == 11){
            mainViewModel.startReachLimetonesMp3(getContext(), viewModel.getCurrentLevel());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    binding.getRoot().closeDrawer(GravityCompat.START);
                }
            }, 10000);
        }else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    binding.getRoot().closeDrawer(GravityCompat.START);
                }
            }, 1000);
        }

    }

    private void setOnClickForPlayerArchive(){
        playerArchiveDialog.setOnClickAgreeBtn(new OnButtonClickListener() {
            @Override
            public void onButtonClicked() {
                if (playerArchiveDialog.getPlayerName() != null){
                    saveHighScorePlayerInDB();
                    mainViewModel.startLoseGameMp3(getContext());
                    if (viewModel.isTimeOut()){
                        timeOutDialog.dismiss();
                    }
                }
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        findNavController().popBackStack();
                    }
                }, 8000);
            }
        });
    }

    private void saveHighScorePlayerInDB(){
        HighScore highScore = new HighScore(playerArchiveDialog.getPlayerName(), viewModel.getCurrentScore());

        viewModel.insertHighScore(highScore).subscribe(aBoolean -> {

        }, t ->{
            t.printStackTrace();
        });
    }

    private void setOnClickForStartDialog(){
        startDialog.setOnButtonYesClickListener(new OnButtonClickListener() {
            @Override
            public void onButtonClicked() {
                mainViewModel.startBeginMp3(getContext());
                startGame();
            }
        });

        startDialog.setOnButtonNoClickListener(new OnButtonClickListener() {
            @Override
            public void onButtonClicked() {
                findNavController().popBackStack();
            }
        });
    }

    private void playVoice(){
        switch (viewModel.getTrueCaseIndex()){
            case 1:{


                break;
            }
            case 2:{
                break;
            }
            case 3:{
                break;
            }
            case 4:{
                break;
            }

        }
    }

    private void fetchDataFromDB(){
        if (mainViewModel.isDataFetched()){
            viewModel.initializeData(mainViewModel.getData());
        }

        Question question = viewModel.getRamdomQuestion();
        if (question != null){
            binding.txtQuestion.setText(question.getQuestion());
            binding.txtAnswerA.setText(question.getCaseA());
            binding.txtAnswerB.setText(question.getCaseB());
            binding.txtAnswerC.setText(question.getCaseC());
            binding.txtAnswerD.setText(question.getCaseD());
            binding.txtQuestionNumber.setText("Câu 1");
        }

    }

    private void updateView(){

        binding.txtMoney.setText(viewModel.getCurrenMoney() + "");
        Question question = viewModel.getRamdomQuestion();
        binding.txtQuestionNumber.setText("Câu: " + viewModel.getCurrentLevel());
        binding.txtQuestion.setText(question.getQuestion());
        binding.txtAnswerA.setText(question.getCaseA());
        binding.txtAnswerB.setText(question.getCaseB());
        binding.txtAnswerC.setText(question.getCaseC());
        binding.txtAnswerD.setText(question.getCaseD());

        binding.ivAnswerA.setImageResource(R.drawable.player_answer_background_normal);
        binding.ivAnswerB.setImageResource(R.drawable.player_answer_background_normal);
        binding.ivAnswerC.setImageResource(R.drawable.player_answer_background_normal);
        binding.ivAnswerD.setImageResource(R.drawable.player_answer_background_normal);

        binding.btnAnswerA.setVisibility(View.VISIBLE);
        binding.btnAnswerB.setVisibility(View.VISIBLE);
        binding.btnAnswerC.setVisibility(View.VISIBLE);
        binding.btnAnswerD.setVisibility(View.VISIBLE);

        setCountDownView(31000);

    }


    private void setCountDownView(long millisInFuture){
        if (countDownTimer != null){
            countDownTimer.cancel();
        }
        viewModel.timeOut(false);

        countDownTimer = new CountDownTimer(millisInFuture, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (viewModel.isPlaying()){
                    timePoint = millisUntilFinished;
                    int time = (int)millisUntilFinished /1000;
                    binding.txtTime.setText(time + "");
                }
            }

            @Override
            public void onFinish() {
                viewModel.timeOut(true);
                mainViewModel.startOutOfTimeMp3(getContext());
                timeOutDialog.show();
            }
        };

        countDownTimer.start();

    }


    private void startGame(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mainViewModel.startQuesMp3(getContext(), viewModel.getCurrentLevel());
                binding.loutStart.setVisibility(View.INVISIBLE);
                updateSubLayout();
                setCountDownView(31000);
            }
        }, 5200);

    }

    private void initializeDialog(){
        stopDialog = new InformDialog(getContext(), "Bạn chắc chắn muốn dừng cuộc chơi tại đây ?", "Chắc chắn", "Tiếp tục chơi");
        changeQuestionDialog = new InformDialog(getContext(), "Bạn chắc chắn muốn đổi câu hỏi chứ ?", "Chắc chắn", "Không");
        startDialog = new InformDialog(getContext(), "Bạn đã sẵn sàng chơi với chúng tôi?", "Sẵn sàng", "Bỏ qua");
        askAudiDialog = new AskAudiDialog(getContext());
        callProsDialog = new CallProsDialog(getContext());
        playerArchiveDialog = new PlayerArchiveDialog(getContext());
        timeOutDialog = new TimeOutDialog(getContext());
    }

    private void setFullScreenForSublayout(){
        DrawerLayout.LayoutParams layoutParams = (DrawerLayout.LayoutParams) binding.ltMoneyBoard.getRoot().getLayoutParams();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        layoutParams.width = displayMetrics.widthPixels;
        layoutParams.height = displayMetrics.heightPixels;

        binding.ltMoneyBoard.getRoot().setLayoutParams(layoutParams);

    }

    private void setOnClickForSublayout(){
        binding.ltMoneyBoard.btnHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.getRoot().closeDrawer(GravityCompat.START);
            }
        });
    }

    private void setOnClickForStopDialog(){
        stopDialog.setOnButtonYesClickListener(new OnButtonClickListener() {
            @Override
            public void onButtonClicked() {
                findNavController().popBackStack();
                stopDialog.dismiss();
            }
        });

        stopDialog.setOnButtonNoClickListener(new OnButtonClickListener() {
            @Override
            public void onButtonClicked() {
                binding.btnStop.setImageResource(R.drawable.player_button_image_help_stop);
                stopDialog.dismiss();
            }
        });

        setOnStopDialogChange();
    }

    private void setOnClickForChangeQuestionDialog(){
        changeQuestionDialog.setOnButtonYesClickListener(new OnButtonClickListener() {
            @Override
            public void onButtonClicked() {
                    updateView();
                    binding.btnChangeQuestion.setImageResource(R.drawable.player_button_image_help_change_question_x);
                changeQuestionDialog.dismiss();
            }
        });

        changeQuestionDialog.setOnButtonNoClickListener(new OnButtonClickListener() {
            @Override
            public void onButtonClicked() {
                changeQuestionDialog.dismiss();
                binding.btnChangeQuestion.setImageResource(R.drawable.player_button_image_help_change_question);
            }
        });

        changeQuestionDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (viewModel.isQuestionChanged()){
                    binding.btnChangeQuestion.setImageResource(R.drawable.player_button_image_help_change_question_x);
                }else {
                    binding.btnChangeQuestion.setImageResource(R.drawable.player_button_image_help_change_question);
                }
            }
        });
    }

    private void setOnClickForTimeOutDialog(){
        timeOutDialog.setOnBtnCloseListener(new OnButtonClickListener() {
            @Override
            public void onButtonClicked() {
                playerArchiveDialog.setPlayerMoney(viewModel.getCurrenMoney());
                playerArchiveDialog.show();
            }
        });
    }

    private void hideTheWrongAnswer(int wrongAnswerIndex){
        switch (wrongAnswerIndex){
            case 1:{
                binding.btnAnswerA.setVisibility(View.INVISIBLE);
                break;
            }
            case 2:{
                binding.btnAnswerB.setVisibility(View.INVISIBLE);
                break;
            }
            case 3:{
                binding.btnAnswerC.setVisibility(View.INVISIBLE);
                break;
            }

            case 4:{
                binding.btnAnswerD.setVisibility(View.INVISIBLE);
                break;
            }

        }
    }

    private void setOnStopDialogChange(){
        stopDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (viewModel.isPlaying()){
                    binding.btnStop.setImageResource(R.drawable.player_button_image_help_stop);
                }

            }
        });
    }

    private void setOnAskAudientDialogCancel(){
        askAudiDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (viewModel.isAsked()){
                    binding.btnAskAudients.setImageResource(R.drawable.player_button_image_help_audience_x);
                    setCountDownView(timePoint);
                }
            }
        });

    }

    private void setOnClickForAskAudientDialog(){
        askAudiDialog.setOnCloseButtonClickListener(new OnButtonClickListener() {
            @Override
            public void onButtonClicked() {
                askAudiDialog.dismiss();
                setCountDownView(timePoint);
            }
        });
    }


    private void setOnClickForCallProsDialogBtn(){
        Handler handler = new Handler();

        callProsDialog.setOnDocBtnClickListener(new OnButtonClickListener() {
            @Override
            public void onButtonClicked(){
                mainViewModel.startCallMp3(getContext());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        callProsDialog.showAnswer(viewModel.getTrueCaseIndex(), "Bác sĩ", R.drawable.player_layout_help_call_01);
                    }
                }, 1500);
            }
        });

        callProsDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                binding.btnCall.setImageResource(R.drawable.player_button_image_help_call_x);
            }
        });

        callProsDialog.setOnEngiBtnClickListener(new OnButtonClickListener() {
            @Override
            public void onButtonClicked() {
                mainViewModel.startCallMp3(getContext());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        callProsDialog.showAnswer(viewModel.getTrueCaseIndex(), "Kỹ sư", R.drawable.player_layout_help_call_03);
                    }
                }, 1500);
            }
        });

        callProsDialog.setOnProsBtnClickListener(new OnButtonClickListener() {
            @Override
            public void onButtonClicked() {
                mainViewModel.startCallMp3(getContext());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        callProsDialog.showAnswer(viewModel.getTrueCaseIndex(), "Giáo sư", R.drawable.player_layout_help_call_02);
                    }
                }, 1500);

            }
        });

        callProsDialog.setOnRepBtnClickListener(new OnButtonClickListener() {
            @Override
            public void onButtonClicked() {
                mainViewModel.startCallMp3(getContext());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        callProsDialog.showAnswer(viewModel.getTrueCaseIndex(), "Phóng viên", R.drawable.player_layout_help_call_04);
                    }
                }, 1500);

            }
        });

        callProsDialog.setOnCloseBtnClickListener(new OnButtonClickListener() {
            @Override
            public void onButtonClicked() {
                setCountDownView(timePoint);
                callProsDialog.dismiss();
                binding.btnCall.setImageResource(R.drawable.player_button_image_help_call_x);
            }
        });
    }
}
