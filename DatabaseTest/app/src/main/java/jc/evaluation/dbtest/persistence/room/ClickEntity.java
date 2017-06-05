package jc.evaluation.dbtest.persistence.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "clicks",
        foreignKeys = @ForeignKey(entity = UserEntity.class, parentColumns = "uid", childColumns = "parent_id", onDelete = CASCADE))
public class ClickEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public Date timestamp;

    @ColumnInfo(name = "parent_id")
    public int parentId;

}
