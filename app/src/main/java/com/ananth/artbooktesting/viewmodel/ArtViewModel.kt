package com.ananth.artbooktesting.viewmodel

import android.support.v4.os.IResultReceiver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ananth.artbooktesting.model.ImageResponse
import com.ananth.artbooktesting.repo.ArtRepository
import com.ananth.artbooktesting.repo.IArtRepository
import com.ananth.artbooktesting.roomdb.Art
import com.ananth.artbooktesting.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtViewModel @Inject constructor(
    private val repository: IArtRepository,

    ) : ViewModel() {

    //Art Fragment
    val artList = repository.getArt()

    //Image API Fragment
    private val images = MutableLiveData<Resource<ImageResponse>>()
    val imageList: LiveData<Resource<ImageResponse>>
        get() = images

    private val selectedImage = MutableLiveData<String>()
    val selectedImageUrl: LiveData<String>
        get() = selectedImage

    //Art Details fragment
    private var insertArtMsg = MutableLiveData<Resource<Art>>()
    val insertArtMessage: LiveData<Resource<Art>>
        get() = insertArtMsg

    fun reserInsertArtMsg() {
        insertArtMsg = MutableLiveData<Resource<Art>>()
    }

    fun setSelectedImage(url: String) {
        selectedImage.postValue(url)
    }

    fun deleteArt(art: Art) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteArt(art)
        }

    fun insertArt(art: Art) = viewModelScope.launch {
        repository.insertArt(art)
    }

    fun searchForImage(searchString: String) {
        if (searchString.isEmpty())
            return

        images.value = Resource.loading(null)
        viewModelScope.launch {
            val response = repository.searchImage(searchString)
            images.value = response
        }
    }

    fun makeArt(name: String, artist: String, year: String) {
        if (name.isEmpty() || artist.isEmpty() || year.isEmpty())
        {
            insertArtMsg.postValue(Resource.error("Enter name, artist name, year!!",null))
            return
        }
        val yearInt = try {
            year.toInt()
        }catch (e:Exception){
            insertArtMsg.postValue(Resource.error("Year should be number!",null))
        }

        val art = Art(name, artist, yearInt as Int,selectedImage.value?:"",)
        insertArt(art)
        setSelectedImage("")
        insertArtMsg.postValue(Resource.success(art))
    }
}
