package com.example.team_val.doman

import android.R.attr.bitmap
import android.graphics.BitmapFactory
import com.example.team_val.data.local.MapDao
import com.example.team_val.data.network.firebase.FirestoreManager
import com.example.team_val.doman.model.MapModel
import com.example.team_val.doman.model.toMapModel
import java.io.FileInputStream
import java.io.IOException
import javax.inject.Inject


class GetMapaUseCase @Inject constructor(
    private val mapDao: MapDao
) {
    suspend operator fun invoke(): List<MapModel> {
      return  try {
            var fileInputStream: FileInputStream
           val maps = mapDao.getAallMaps()
            maps.map { it.toMapModel()}.toList()
        } catch (ex: Exception) {
          return emptyList()
        }
    }
//imageView.setImageBitmap(bitmap);

}

/*
 return  try {
            var fileInputStream: FileInputStream
           val maps = mapDao.getAallMaps()
            maps.map {
               fileInputStream= FileInputStream(it.path)
                val bitmap = BitmapFactory.decodeStream(fileInputStream)
                fileInputStream.reset()
                MapModel(it.name, bitmap)
            }.toList()
        } catch (io: IOException) {
            io.printStackTrace()
          return emptyList()
        }
 */