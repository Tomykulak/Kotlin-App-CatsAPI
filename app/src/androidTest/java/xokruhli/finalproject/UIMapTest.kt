package xokruhli.finalproject

import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import xokruhli.finalproject.ui.activities.MainActivity
import xokruhli.finalproject.ui.screens.NavGraphs
import xokruhli.finalproject.ui.screens.map.MapExists
import xokruhli.finalproject.ui.screens.map.MapScreenTag
import xokruhli.finalproject.ui.screens.map.MarkerTag

@ExperimentalCoroutinesApi
@HiltAndroidTest
class UIMapTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.activity.setContent {
            MaterialTheme {
                val navController = rememberNavController()
                DestinationsNavHost(navGraph = NavGraphs.root, navController = navController)
            }
        }
    }

    @Test
    fun MapShows() {
        with(composeRule) {
            onNodeWithTag(MapScreenTag, useUnmergedTree = true).performClick()
            waitForIdle()
            Thread.sleep(1000)
            onNodeWithTag(MapExists, useUnmergedTree = true).assertIsDisplayed()
        }
    }

    @Test
    fun MarkerShows() {
        with(composeRule) {
            onNodeWithTag(MapScreenTag, useUnmergedTree = true).performClick()
            waitForIdle()
            Thread.sleep(1000)
            onNodeWithTag(MapExists, useUnmergedTree = true).assertIsDisplayed()
            waitForIdle()
            Thread.sleep(1000)

            val markerNodes = onAllNodes(hasTestTag(MarkerTag))

            assertTrue("marker exist", markerNodes != null)

            waitForIdle()
            Thread.sleep(1000)
        }
    }
}