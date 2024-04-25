package com.example.team_val.doman

import android.content.ContentValues
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.util.Log
import com.bumptech.glide.Glide
import com.example.team_val.ResponseData
import com.example.team_val.data.local.MapDao
import com.example.team_val.data.local.entity.MapEntity
import com.example.team_val.data.network.firebase.FirestoreManager

import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class DownloadDataUseCase  @Inject constructor(
    val firestore: FirestoreManager,
    private val mapDao: MapDao
) {

    suspend operator fun invoke(context: Context) :ResponseData<Unit> {
       return when (val maps = firestore.getConsts()) {
            is ResponseData.Success -> {
                var fail : Int = 0
                if( maps.data?.maps !=  null ){
                    maps.data.maps!!.forEach{
                        if(!saveImg(context, it.key,it.value)){ fail++}
                    }
                    if (fail==0) ResponseData.Success(Unit) else ResponseData.Error("datsos no descargados correctamete ")
                }
                else
                    ResponseData.Error("Error")
            }
            is ResponseData.Error -> {
                ResponseData.Error(maps.messageError)
            }
        }

    }
    suspend fun saveImg(context: Context, name :String, imagenUrl:String):Boolean{
        val cw  = ContextWrapper(context)
        val file = cw.getDir("ImagesMaps", Context.MODE_PRIVATE)
        val Path = File(file,name+ "JPEG")
        var fos: FileOutputStream? = null
       return try {
            fos = FileOutputStream(Path)
            val image =  Glide.with(context).asBitmap().load(imagenUrl).submit().get()
            image.compress(Bitmap.CompressFormat.JPEG, 10, fos)
            fos!!.flush()
           //guardad en room los datros de la ruta
           val map = MapEntity(name, Path.absolutePath)
           Log.w(ContentValues.TAG, "PATH" + Path.absolutePath)
            mapDao.insert(map)

            true
        } catch (ex: Exception) {

            ex.printStackTrace()
           false
        }
    }
}