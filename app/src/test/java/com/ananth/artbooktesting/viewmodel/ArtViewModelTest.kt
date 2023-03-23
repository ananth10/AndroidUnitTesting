package com.ananth.artbooktesting.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ananth.artbooktesting.MainCoroutineRule
import com.ananth.artbooktesting.getOrAwaitValueTest
import com.ananth.artbooktesting.repo.ArtRepositoryTest
import com.ananth.artbooktesting.util.Status
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ArtViewModelTest {

    private lateinit var viewModel: ArtViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    @Before
    fun setUp() {
        viewModel = ArtViewModel(ArtRepositoryTest())
    }

    @Test
   fun `insert art without year should returns error`(){
    viewModel.makeArt("mona","davin","")
    val value = viewModel.insertArtMessage.getOrAwaitValueTest()
    assertThat(value.status).isEqualTo(Status.ERROR)
   }

    @Test
    fun `insert art without name should returns error`(){
        viewModel.makeArt("","davin","1990")
        val value = viewModel.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert art without artist name should returns error`(){
        viewModel.makeArt("mona","","1990")
        val value = viewModel.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }
}