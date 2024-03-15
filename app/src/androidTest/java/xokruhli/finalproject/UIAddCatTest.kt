package xokruhli.finalproject

import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import xokruhli.finalproject.ui.activities.MainActivity
import xokruhli.finalproject.ui.screens.NavGraphs
import xokruhli.finalproject.ui.screens.addCat.ButtonTestTag
import xokruhli.finalproject.ui.screens.addCat.NameInputTestTag

@HiltAndroidTest
class UIAddCatTest {

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
    fun testAddCatScreenContent() {
        with(composeRule) {
            onNodeWithTag(NameInputTestTag, useUnmergedTree = true).performTextInput("Cat Name")

            waitForIdle()
            Thread.sleep(1000)
            onNodeWithTag(ButtonTestTag, useUnmergedTree = true)
                .performClick()
            waitForIdle()
            Thread.sleep(1000)
        }
    }
}