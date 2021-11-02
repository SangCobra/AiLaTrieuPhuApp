package com.sang.ailatrieuphu.data;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

public class QuestionsDBMgr {
    private static QuestionsDBMgr instance;
    private QuestionDatabase questionDatabase;

    private QuestionsDBMgr(){

    }

    public static QuestionsDBMgr getInstance(){
        if (instance == null){
            instance = new QuestionsDBMgr();
        }

        return instance;
    }

    public void initialize(Context context){
       questionDatabase = Room.databaseBuilder(context, QuestionDatabase.class, "Question.db")
               .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
                .createFromAsset("Question.db")
                .build();
    }

    public QuestionDatabase getQuestionDatabase(){
         return questionDatabase;
    }
}
