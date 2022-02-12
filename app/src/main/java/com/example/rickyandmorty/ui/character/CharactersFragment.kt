package com.example.rickyandmorty.ui.character

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickyandmorty.common.lazyFast
import com.example.rickyandmorty.data.model.character.CharacterModel
import com.example.rickyandmorty.databinding.FragmentCharactersBinding
import com.example.rickyandmorty.databinding.ItemCharacterBinding
import com.example.rickyandmorty.ui.character.adapter.CharacterAdapter
import com.example.rickyandmorty.ui.character.adapter.LoaderStateAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalPagingApi
class CharactersFragment : Fragment(), CharacterAdapter.CharacterClickListener {

    private val mViewModel: CharactersViewModel /*by viewModels()*/
            by lazyFast { defaultViewModelProviderFactory.create(CharactersViewModel::class.java) }
    private var _binding: FragmentCharactersBinding? = null
    private val adapter: CharacterAdapter by lazyFast { CharacterAdapter(this) }
    private val loaderStateAdapter: LoaderStateAdapter by lazyFast { LoaderStateAdapter { adapter.retry() } }

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCharactersBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        fetchCharacters()
    }

    private fun fetchCharacters() {
        lifecycleScope.launch {
            try {
                mViewModel.charactersFlow.collectLatest {
                    adapter.submitData(it)
                }
            } catch (e: Exception) {
                Log.d("TAG_Character", "fetchCharacters: ${e.message}")
            }
        }
    }


    private fun setUpViews() {
        binding.charactersRv.layoutManager = //GridLayoutManager(requireContext(), 2)
            LinearLayoutManager(requireContext())
        binding.charactersRv.adapter = adapter
        adapter.withLoadStateHeaderAndFooter(
            loaderStateAdapter,
            loaderStateAdapter
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCharacterClicked(binding: ItemCharacterBinding, model: CharacterModel) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(model.url)
        startActivity(intent)


    }
}