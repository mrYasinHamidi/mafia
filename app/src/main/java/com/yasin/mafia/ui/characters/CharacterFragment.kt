package com.yasin.mafia.ui.characters

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.transition.MaterialSharedAxis
import com.yasin.mafia.DataBase.PersonDataBase
import com.yasin.mafia.R
import com.yasin.mafia.databinding.FragmentCharacterBinding
import com.yasin.mafia.ui.start.ConfigDialog


class CharacterFragment : Fragment() {


    private lateinit var viewModel: CharacterViewModel
    private lateinit var binding: FragmentCharacterBinding
    private lateinit var adapter: charactersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialSharedAxis.create(MaterialSharedAxis.Z, false)

        reenterTransition = MaterialSharedAxis.create(MaterialSharedAxis.Z, true)

        val application = requireNotNull(this.activity).application
        val data = PersonDataBase.getInstance(application).personDao
        val factory = CharacterFactory(false,data, application)
        viewModel = ViewModelProviders.of(this, factory).get(CharacterViewModel::class.java)
        viewModel.setAlltoFalse()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_character, container, false)


        //setUp toolbar
        (activity as AppCompatActivity).setSupportActionBar(binding.tbCharacters)
        binding.tbCharacters.setupWithNavController(findNavController())
        binding.tbCharacters.title = "Citizen (${ConfigDialog.Citizen})"

        recyclerViewInitialize()

        myOnClickListeners()

        observer()

        return binding.root
    }

    private fun observer() {
        viewModel.Characters.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)

        })
    }

    private fun myOnClickListeners() {
        binding.characterSubmit.setOnClickListener {
            viewModel.update(viewModel.updateCharacter)
            findNavController().navigate(R.id.action_characterFragment_to_characterFragment2)
        }
    }

    private fun recyclerViewInitialize() {

        val recyclerView = binding.rvCharacters  //Initialize recycler view with data binding

        recyclerView.layoutManager =  //set a grid layout manager for recycler view
            GridLayoutManager(activity, 4, GridLayoutManager.VERTICAL, false)

        //create an adapter for recycler view
        adapter = charactersAdapter(
            viewModel,
            StartItemListiner { character, b ->
                //on click listener for each item of recycler view
                if (b)
                    viewModel.updateCharacter.apply { character.selected = true and add(character) }
                else
                    viewModel.updateCharacter.apply { character.selected = false and add(character) }
            })
        recyclerView.adapter = adapter


    }

    override fun onResume() {
        super.onResume()
        viewModel.setAlltoFalse()
    }

}

