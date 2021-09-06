package by.sergei.charactercatalog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import by.sergei.charactercatalog.model.Character
import by.sergei.charactercatalog.databinding.ItemCharacterBinding
import com.bumptech.glide.Glide

class CharactersListAdapter(private val onClick: (Long) -> Unit) :
    ListAdapter<Character, CharacterDataViewHolder>(PersonItemDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterDataViewHolder {
        return CharacterDataViewHolder(
            ItemCharacterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onClick
        )
    }

    override fun onBindViewHolder(holder: CharacterDataViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class CharacterDataViewHolder(
    private val itemBinding: ItemCharacterBinding,
    private val onClick: (Long) -> Unit
) :
    RecyclerView.ViewHolder(itemBinding.root) {

    init {
        itemBinding.idCharacter.setOnClickListener {
            onClick(id!!)
        }
    }

    private var id: Long? = null

    fun bind(character: Character) {
        Glide
            .with(itemBinding.characterAvatar.context)
            .load(character.imageUrl)
            .into(itemBinding.characterAvatar)
        itemBinding.characterName.text = character.name
        id = character.id
    }
}

private class PersonItemDiffCallBack : DiffUtil.ItemCallback<Character>() {
    override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean =
        (oldItem.id == newItem.id)

    override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean =
        (oldItem == newItem)
}