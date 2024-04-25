package com.example.team_val.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import com.example.team_val.data.local.entity.MapEntity

@Dao
interface MapDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun  insert(Map:MapEntity): Long

    @Query("SELECT * FROM map_table")
    suspend fun getAallMaps():List<MapEntity>
}