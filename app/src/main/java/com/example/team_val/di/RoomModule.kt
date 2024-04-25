package com.example.team_val.di

import android.content.Context
import androidx.room.Room
import com.example.team_val.data.local.MapDao
import com.example.team_val.data.local.TeamValDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    private const val MAP_TABLE_NAME ="map_table"

    @Singleton
    @Provides
   // @Named("name") colocar naem
    fun provideRoom(@ApplicationContext context: Context): MapDao{
        return Room.databaseBuilder(context,TeamValDataBase::class.java, MAP_TABLE_NAME).build().getmapDao()
    }
}