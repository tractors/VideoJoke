package com.mooc.libnetwork.cache

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mooc.libcommon.AppGlobals

/**
 * 用于缓存
 */
@Database(entities = [Cache::class],version = 1,exportSchema = true)
abstract class CacheDatabase : RoomDatabase() {
    companion object {
        //创建一个内存数据库，这种数据库只存在于内存中，进程被杀后，数据随之丢失
        //Room.inMemoryDatabaseBuilder()
        private val database = AppGlobals.getApplication()?.let {
            Room.databaseBuilder(it, CacheDatabase::class.java, "video_joke_cache")
                //是否允许在主线程进行查询
                .allowMainThreadQueries()
            //打开和创建的回调
            //.addCallback()
            //设置查询时的线程池
            //.setQueryExecutor()
            //设置数据库工厂
            //.openHelperFactory()
            //room 的日志模式
            //.setJournalMode()
            //数据库升级异常后的回滚
            //.fallbackToDestructiveMigration()
            //数据库升级异常后根据指定版本进行回滚
            //.fallbackToDestructiveMigrationFrom()
            //向数据库添加迁移，每次迁移都要有开始和最后版本，Room 将将迁移到最新版本
            // 如果没有迁移对象，则数据库会重新创建
            //.addMigrations(CacheDatabase.sMigration)
                .build()
        }
        /**
         * 数据库迁移对象，可以对数据库进行必要的更改
         */

        private val sMigration  = object : Migration(1,3){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("alter table teacher rename to student")
                database.execSQL("alter table teacher add column teacher_age INTEGER NOT NULL default 0")
            }

        }

        fun get():CacheDatabase{
            return database!!
        }
    }

    abstract fun getCache() : CacheDao

    init {

    }
}