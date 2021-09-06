package by.sergei.charactercatalog.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import by.sergei.charactercatalog.R
import by.sergei.charactercatalog.databinding.FragmentCharacterDetailBinding
import by.sergei.charactercatalog.model.CharacterDetails
import by.sergei.charactercatalog.model.ValueResult
import by.sergei.charactercatalog.model.ViewState
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.core.parameter.parametersOf

class FragmentCharacterDetails : Fragment(R.layout.fragment_character_detail) {

    private val viewBinding: FragmentCharacterDetailBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getLong(ID_CHARACTER)
        val viewModel: CharacterDetailsViewModel by viewModel{ parametersOf(id)}
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state
                .collect { state -> handleState(state) }
        }
    }

    private fun handleState(state: ViewState<CharacterDetails, Throwable>) =
        when (state) {
            is ViewState.Loading -> {
                viewBinding.progressBarDetail.isVisible = true
            }
            is ViewState.Result -> {
                viewBinding.progressBarDetail.isVisible = false
                handleResultState(state.result)
            }
        }

    private fun handleResultState(result: ValueResult<CharacterDetails, Throwable>) =
        when (result) {
            is ValueResult.Success -> {
                setData(result.value)
            }
            is ValueResult.Error -> {
                Toast.makeText(context, result.value.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }

    private fun setData(characterDetails: CharacterDetails) {
        viewBinding.name.text = characterDetails.name
        viewBinding.status.text = characterDetails.status
        viewBinding.species.text = characterDetails.species
        viewBinding.type.text = characterDetails.type
        viewBinding.gender.text  = characterDetails.gender
        Glide
            .with(viewBinding.image.context)
            .load(characterDetails.image)
            .into(viewBinding.image)
        viewBinding.created.text = characterDetails.created
    }

    companion object {
        private const val ID_CHARACTER: String = "idCharacter"
        fun newInstance(id: Long): FragmentCharacterDetails {
            val args: Bundle = Bundle(1)
            args.putLong(ID_CHARACTER, id)
            val fragment: FragmentCharacterDetails = FragmentCharacterDetails()
            fragment.arguments = args
            return fragment
        }
    }
}