package com.yasin.mafia.ui.characters
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.transition.MaterialSharedAxis
import com.yasin.mafia.DataBase.PersonDataBase
import com.yasin.mafia.R
import com.yasin.mafia.databinding.CharacterFragment2Binding
import com.yasin.mafia.ui.start.ConfigDialog

class CharacterFragment2 : Fragment() {

    private lateinit var viewModel: CharacterViewModel
    private lateinit var binding: CharacterFragment2Binding
    private lateinit var adapter: charactersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialSharedAxis.create(MaterialSharedAxis.Z, false)

        reenterTransition = MaterialSharedAxis.create(MaterialSharedAxis.Z, true)

        val application = requireNotNull(this.activity).application
        val data = PersonDataBase.getInstance(application).personDao
        val factory = CharacterFactory(true, data, application)
        viewModel = ViewModelProviders.of(this, factory).get(CharacterViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //initialize data binding
        binding = DataBindingUtil.inflate(inflater, R.layout.character_fragment2, container, false)

        //setUp toolbar
        (activity as AppCompatActivity).setSupportActionBar(binding.tbCharacters2)
        binding.tbCharacters2.setupWithNavController(findNavController())
        binding.tbCharacters2.title = "Mafia (${ConfigDialog.Mafia})"

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

        binding.character2NextBtn.setOnClickListener {
            viewModel.update(viewModel.updateCharacter)
            findNavController().navigate(R.id.action_characterFragment2_to_showRoleFragment)
        }
    }

    private fun recyclerViewInitialize() {

        val recyclerView = binding.character2List  //Initialize recycler view with data binding

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
                    viewModel.updateCharacter.apply { character.selected = false and add(character) } })
        recyclerView.adapter = adapter


    }
}