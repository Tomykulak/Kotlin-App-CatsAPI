package xokruhli.finalproject.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint
import xokruhli.finalproject.ui.screens.NavGraphs
import xokruhli.finalproject.ui.theme.FinalProjectTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FinalProjectTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}