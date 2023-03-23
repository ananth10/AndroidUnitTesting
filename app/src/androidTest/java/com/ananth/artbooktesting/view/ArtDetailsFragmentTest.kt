package com.ananth.artbooktesting.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.ananth.artbooktesting.R
import com.ananth.artbooktesting.getOrAwaitValueTest
import com.ananth.artbooktesting.launchFragmentInHiltContainer
import com.ananth.artbooktesting.repo.ArtRepositoryTest
import com.ananth.artbooktesting.roomdb.Art
import com.ananth.artbooktesting.viewmodel.ArtViewModel
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ArtDetailsFragmentTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var fragmentFactory: ArtFragmentFactory

    @Before
    fun setUp(){
        hiltRule.inject()
    }

    @Test
    fun testNavigationFromArtDetailsToImageApi(){
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<ArtDetailFragment>(factory = fragmentFactory){
            Navigation.setViewNavController(requireView(),navController)
        }

        Espresso.onView(ViewMatchers.withId(R.id.artImageView)).perform(ViewActions.click())

        Mockito.verify(navController).navigate(ArtDetailFragmentDirections.actionArtDetailFragmentToImageApiFragment())
    }

    @Test
    fun testOnBackPressed(){
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<ArtDetailFragment>(factory = fragmentFactory){
            Navigation.setViewNavController(requireView(),navController)
        }

        Espresso.pressBack()

        Mockito.verify(navController).popBackStack()
    }

    @Test
    fun testSave(){
        val artViewModel = ArtViewModel(ArtRepositoryTest())
        launchFragmentInHiltContainer<ArtDetailFragment>(
            factory = fragmentFactory
        ){
            viewModel = artViewModel
        }

        Espresso.onView(withId(R.id.enterName)).perform(replaceText("Mona lisa"))
        Espresso.onView(withId(R.id.enterArtist)).perform(replaceText("Davin ci"))
        Espresso.onView(withId(R.id.enterYear)).perform(replaceText("1900"))
        Espresso.onView(withId(R.id.saveButton)).perform(ViewActions.click())

        assertThat(artViewModel.artList.getOrAwaitValueTest()).contains(Art("Mona lisa","Davin ci",1900,"",null))

    }

}