package com.sang.ailatrieuphu.data.converter;

import android.icu.lang.UCharacter;

import androidx.room.TypeConverter;

public class IntergertoIntConverter {
    @TypeConverter
    public Integer convertToInteger(int value){
        return new Integer(value);
    }

    @TypeConverter
    public int convertToInt(Integer value){

        return value.intValue();
    }
}
