package com.mooc.libnetwork.cache

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
@Entity(tableName = "cache")
class Cache : Serializable {

    @NonNull
    @PrimaryKey
    var key : String?=""

    @ColumnInfo
    var data : ByteArray? = null
}