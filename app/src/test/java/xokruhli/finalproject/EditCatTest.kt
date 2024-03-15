package xokruhli.finalproject

import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import xokruhli.finalproject.model.profile.Cat
import xokruhli.finalproject.ui.screens.addCat.AddCatViewModel

class EditCatTest {
    private lateinit var viewModel: AddCatViewModel

    @Before
    fun setUp() {
        viewModel = Mockito.mock(AddCatViewModel::class.java)
    }

    @Test
    fun editCat() = runBlocking {
        val cat1 = Cat(
            id = 1200,
            name = "kocicka",
            breed = "chlupata",
            weight = null,
            height = null,
            vaccination = null,
            medical_report = null,
            photo = ""
        )

        viewModel.insertCat(cat1)
        val cat2 = Cat(
            id = 1200,
            name = "kockicka",
            breed = "stejneChlupata",
            weight = null,
            height = null,
            vaccination = null,
            medical_report = null,
            photo = ""
        )
        viewModel.updateCat(cat2)

        Mockito.verify(viewModel).updateCat(cat2)
    }
}