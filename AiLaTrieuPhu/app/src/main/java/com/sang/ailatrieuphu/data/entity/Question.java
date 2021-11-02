package com.sang.ailatrieuphu.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Question")
public class Question {
    @ColumnInfo(name = "question")
    private String question = "";

    @PrimaryKey
    @ColumnInfo(name = "_id")
    private Integer id;

    @ColumnInfo(name = "level")
    private Integer level;

    @ColumnInfo(name = "casea")
    private String caseA;

    @ColumnInfo(name = "caseb")
    private String caseB;

    @ColumnInfo(name = "casec")
    private String caseC;

    @ColumnInfo(name = "cased")
    private String caseD;

    @ColumnInfo(name = "truecase")
    private Integer trueCase;

    public Question(){

    }

    @Ignore
    public Question(String question, Integer id, Integer level, String caseA, String caseB, String caseC, String caseD, Integer trueCase) {
        this.question = question;
        this.id = id;
        this.level = level;
        this.caseA = caseA;
        this.caseB = caseB;
        this.caseC = caseC;
        this.caseD = caseD;
        this.trueCase = trueCase;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getCaseA() {
        return caseA;
    }

    public void setCaseA(String caseA) {
        this.caseA = caseA;
    }

    public String getCaseB() {
        return caseB;
    }

    public void setCaseB(String caseB) {
        this.caseB = caseB;
    }

    public String getCaseC() {
        return caseC;
    }

    public void setCaseC(String caseC) {
        this.caseC = caseC;
    }

    public String getCaseD() {
        return caseD;
    }

    public void setCaseD(String caseD) {
        this.caseD = caseD;
    }

    public Integer getTrueCase() {
        return trueCase;
    }

    public void setTrueCase(Integer trueCase) {
        this.trueCase = trueCase;
    }
}
