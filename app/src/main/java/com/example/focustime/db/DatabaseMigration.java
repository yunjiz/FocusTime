package com.example.focustime.db;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class DatabaseMigration {
    public static Migration MIGRATION_1_2 = new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE `diary_table` (`focusDate` INTEGER, `content` TEXT, PRIMARY KEY(`focusDate`))");
        }
    };
}
