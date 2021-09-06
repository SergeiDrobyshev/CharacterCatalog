package by.sergei.charactercatalog

import by.sergei.charactercatalog.ui.details.FragmentCharacterDetails
import by.sergei.charactercatalog.ui.list.FragmentCharactersList
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    fun charactersList(): FragmentScreen = FragmentScreen { FragmentCharactersList.newInstance() }
    fun charactersDetails(id: Long): FragmentScreen = FragmentScreen { FragmentCharacterDetails.newInstance(id) }
}