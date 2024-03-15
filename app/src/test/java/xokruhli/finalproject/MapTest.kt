package xokruhli.finalproject

import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import xokruhli.finalproject.ui.screens.map.MapViewModel

class MapTest {
    private lateinit var viewModel: MapViewModel

    @Before
    fun setUp() {
        viewModel = Mockito.mock(MapViewModel::class.java)
    }
    @Test
    fun mapMarkersExists() = runBlocking {
        viewModel.loadData()
        val markers = viewModel.loadData()
        Assert.assertNotEquals(markers, null)

    }
}