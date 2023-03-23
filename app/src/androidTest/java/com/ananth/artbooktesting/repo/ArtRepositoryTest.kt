package com.ananth.artbooktesting.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ananth.artbooktesting.model.ImageResponse
import com.ananth.artbooktesting.roomdb.Art
import com.ananth.artbooktesting.util.Resource
import org.junit.Before

class ArtRepositoryTest : IArtRepository {

    private val arts = mutableListOf<Art>()
    private val artsLiveData = MutableLiveData<List<Art>>(arts)

    @Before
    fun setUp() {
    }

    override suspend fun insertArt(art: Art) {
        arts.add(art)
        refreshData()
    }

    override suspend fun deleteArt(art: Art) {
        arts.remove(art)
        refreshData()
    }

    override fun getArt(): LiveData<List<Art>> {
        return artsLiveData
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return Resource.success(ImageResponse(listOf(), 0, 0))
    }

    private fun refreshData(){
        artsLiveData.postValue(arts)
    }
}