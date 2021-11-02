package com.sang.ailatrieuphu.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.sang.ailatrieuphu.data.entity.HighScore;
import com.sang.ailatrieuphu.data.entity.Question;

import java.util.List;

@Dao
public interface QuestionDao {
    @Query("Select * from question")
    List<Question> getAll();

    @Query("Select * from HighScore")
    List<HighScore> getLsHighScore();

    @Query("Select * From Question Where level = :questionLevel")
    List<Question> getLevelQuestion(int questionLevel);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addHighScore(HighScore highScore);

}
