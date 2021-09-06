package by.sergei.charactercatalog.ui.list

import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.sergei.charactercatalog.databinding.FragmentCharactersListBinding
import by.sergei.charactercatalog.*
import by.sergei.charactercatalog.model.Character
import by.sergei.charactercatalog.model.ValueResult
import by.sergei.charactercatalog.model.ViewState
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class FragmentCharactersList : Fragment(R.layout.fragment_characters_list) {

    private val viewBinding: FragmentCharactersListBinding by viewBinding()
    private val router: Router by inject()
    private val adapter = CharactersListAdapter { id: Long ->
        router?.navigateTo(Screens.charactersDetails(id))

    }

    private val viewModel: CharactersListViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.recyclerViewCharacters.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        viewBinding.recyclerViewCharacters.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state
                .collect { state -> handleState(state) }
        }
    }

    private fun handleState(state: ViewState<List<Character>, Throwable>) =
        when (state) {
            is ViewState.Loading -> {
                viewBinding.progressBar.isVisible = true
            }
            is ViewState.Result -> {
                viewBinding.progressBar.isVisible = false
                handleResultState(state.result)
            }
        }

    private fun handleResultState(result: ValueResult<List<Character>, Throwable>) {
        when (result) {
            is ValueResult.Success -> {
                adapter.submitList(result.value)
            }
            is ValueResult.Error -> {
                Toast.makeText(context, result.value.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        fun newInstance(): FragmentCharactersList = FragmentCharactersList()
    }
}