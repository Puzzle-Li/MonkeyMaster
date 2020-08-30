package com.cebsit.monkeymaster.database;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.cebsit.monkeymaster.database.Monkey;
import com.cebsit.monkeymaster.database.Record;

import java.util.List;

public class MonkeyWithRecords {
    @Embedded
    public Monkey monkey;
    @Relation(
            parentColumn = "monkey_id",
            entityColumn = "monkey_id"
    )
    public List<Record> record_list;
}
