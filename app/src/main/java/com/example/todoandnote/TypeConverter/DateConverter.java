package com.example.todoandnote.TypeConverter;

import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.sql.Timestamp;
import java.util.Date;

public class DateConverter {

    @TypeConverter
    public static Timestamp toDate( Long date )
    {
        return (date == null) ? null: new Timestamp(date);
    }

    @TypeConverter
    public static Long toTimestamp(Timestamp date)
    {
        return (date == null) ? null: date.getTime();
    }



}
