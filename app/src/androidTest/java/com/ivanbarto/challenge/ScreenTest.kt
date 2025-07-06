package com.ivanbarto.challenge

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.espresso.device.DeviceInteraction.Companion.setScreenOrientation
import androidx.test.espresso.device.EspressoDevice.Companion.onDevice
import androidx.test.espresso.device.action.ScreenOrientation
import androidx.test.espresso.device.rules.ScreenOrientationRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ivanbarto.challenge.presentation.cities.composables.CitiesScreen
import com.ivanbarto.challenge.tools.TestTag
import com.ivanbarto.challenge.ui.theme.ChallengeTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val screenOrientationRule = ScreenOrientationRule(ScreenOrientation.PORTRAIT)

    @Test
    fun on_screen_portrait_map_is_hidden() {
        onDevice().setScreenOrientation(ScreenOrientation.PORTRAIT)

        composeTestRule.setContent {
            ChallengeTheme {
                CitiesScreen()
            }
        }
        composeTestRule.onNodeWithTag(TestTag.CITY_LIST).onChildren().onFirst().assertExists()
        composeTestRule.onNodeWithTag(TestTag.MAP_SCREEN).assertDoesNotExist()
    }

    @Test
    fun on_screen_rotation_map_is_displayed() {

        onDevice().setScreenOrientation(ScreenOrientation.LANDSCAPE)

        composeTestRule.setContent {
            ChallengeTheme {
                CitiesScreen()
            }
        }

        composeTestRule.onNodeWithTag(TestTag.MAP_SCREEN).assertExists()

    }

    @Test
    fun on_screen_appear_cities_are_displayed() {
        onDevice().setScreenOrientation(ScreenOrientation.PORTRAIT)

        composeTestRule.setContent {
            ChallengeTheme {
                CitiesScreen()
            }
        }
        composeTestRule.onNodeWithTag(TestTag.CITY_LIST).assertExists()
    }

}