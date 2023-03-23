package com.ananth.artbooktesting.repo

import androidx.lifecycle.LiveData
import com.ananth.artbooktesting.api.RetrofitApi
import com.ananth.artbooktesting.model.ImageResponse
import com.ananth.artbooktesting.roomdb.Art
import com.ananth.artbooktesting.roomdb.ArtDao
import com.ananth.artbooktesting.util.Resource
import javax.inject.Inject

class ArtRepository @Inject constructor(
    private val artDao: ArtDao,
    private val retrofitApi: RetrofitApi
) : IArtRepository {
    override suspend fun insertArt(art: Art) {
        artDao.insertArt(art)
    }

    override suspend fun deleteArt(art: Art) {
        artDao.deleteArt(art)
    }

    override fun getArt(): LiveData<List<Art>> {
        return artDao.observeArts()
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return try {
            val response = retrofitApi.imageSearch(imageString)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("error", null)
            } else {
                Resource.error("error", null)
            }
        } catch (e: Exception) {
            Resource.error("no data", null)
        }
    }
}