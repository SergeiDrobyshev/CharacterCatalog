package by.sergei.charactercatalog

import by.sergei.charactercatalog.retrofit.RetrofitServices
import by.sergei.charactercatalog.retrofit.Retrofit
import by.sergei.charactercatalog.ui.details.CharacterDetailsViewModel
import by.sergei.charactercatalog.ui.list.CharactersListViewModel
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { Cicerone.create(Router()) }
    factory<NavigatorHolder> { get<Cicerone<Router>>().getNavigatorHolder() }
    factory<Router> { get<Cicerone<Router>>().router }

    factory<RetrofitServices> {
        Retrofit.build().create(RetrofitServices::class.java)
    }

    single<CharacterRepository> { JsonCharacterRepository(retrofitService = get()) }

    viewModel { CharactersListViewModel(characterRepository = get()) }
    viewModel { parameters ->
        CharacterDetailsViewModel(
            characterRepository = get(),
            id = parameters.get()
        )
    }
}