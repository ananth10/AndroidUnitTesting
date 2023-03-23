package com.ananth.artbooktesting.repo

import androidx.lifecycle.LiveData
import com.ananth.artbooktesting.model.ImageResponse
import com.ananth.artbooktesting.roomdb.Art
import com.ananth.artbooktesting.util.Resource

interface IArtRepository {

    suspend fun insertArt(art:Art)

    suspend fun deleteArt(art: Art)

    fun getArt():LiveData<List<Art>>

    suspend fun searchImage(imageString: String): Resource<ImageResponse>
}