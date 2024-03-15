package xokruhli.finalproject

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import xokruhli.finalproject.model.profile.Cat
import xokruhli.finalproject.ui.screens.addCat.AddCatViewModel
import xokruhli.finalproject.ui.screens.profileCat.ProfileCatViewModel

class DeleteCatTest {
    private lateinit var viewModel: AddCatViewModel
    private lateinit var profileViewModel: ProfileCatViewModel

    @Before
    fun setUp() {
        viewModel = Mockito.mock(AddCatViewModel::class.java)
        profileViewModel = Mockito.mock(ProfileCatViewModel::class.java)
    }

    @Test
    fun deleteCat() {
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

        viewModel.insertCat(cat)

        profileViewModel.deleteCat(cat)

        Mockito.verify(profileViewModel).deleteCat(cat)
    }
}