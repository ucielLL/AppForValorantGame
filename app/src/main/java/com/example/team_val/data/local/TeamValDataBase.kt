package com.example.team_val.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.team_val.data.local.entity.MapEntity

@Database(entities = [MapEntity::class], version = 1)
abstract class TeamValDataBase:RoomDatabase()  {
    abstract fun getmapDao ():MapDao

}