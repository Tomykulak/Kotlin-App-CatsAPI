package xokruhli.finalproject

import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import xokruhli.finalproject.database.ICatsRepository
import xokruhli.finalproject.model.profile.Cat
import xokruhli.finalproject.ui.screens.addCat.AddCatViewModel

class AddCatTest {
    private lateinit var viewModel: AddCatViewModel
    private val repository = Mockito.mock(ICatsRepository::class.java)

    @Before
    fun setUp() {
        viewModel = Mockito.mock(AddCatViewModel::class.java)
    }

    @Test
    fun addCat() {
        val cat = Cat(
            id = null,
            name = "Chuaha",
            breed = "mrnava",
            weight = 10.2,
            height = 10.3,
            vaccination = null,
            medical_report = null,
            photo = ""
        )
        Mockito.`when`(viewModel.insertCat(cat)).then {
        }
        viewModel.insertCat(cat)
        Mockito.verify(viewModel).insertCat(cat)
    }

    @Test
    fun addCatNotValid() = runBlocking {
        val cat = Cat(
            id = null,
            name = "Chuaha",
            breed = null,
            weight = null,
            height = null,
            vaccination = null,
            medical_report = null,
            photo = ""
        )

        viewModel.insertCat(cat)

        Mockito.verify(repository, Mockito.never()).insert(cat)
    }
}