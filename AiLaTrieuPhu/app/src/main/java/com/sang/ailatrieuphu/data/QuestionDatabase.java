package com.sang.ailatrieuphu.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.sang.ailatrieuphu.data.converter.IntergertoIntConverter;
import com.sang.ailatrieuphu.data.dao.QuestionDao;
import com.sang.ailatrieuphu.data.entity.HighScore;
import com.sang.ailatrieuphu.data.entity.Question;

@Database(entities = {
        HighScore.class, Question.class
}, version = 1)
@TypeConverters({
        IntergertoIntConverter.class
})
public abstract class QuestionDatabase extends RoomDatabase {
  public abstract QuestionDao questionDao();
}
