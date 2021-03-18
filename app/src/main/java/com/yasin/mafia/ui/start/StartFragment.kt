package com.yasin.mafia.ui.start

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.transition.MaterialSharedAxis
import com.yasin.mafia.DataBase.Person
import com.yasin.mafia.DataBase.PersonDataBase
import com.yasin.mafia.R
import com.yasin.mafia.databinding.FragmentStartBinding

class StartFragment : Fragment() {

    private lateinit var startViewModel: StartViewModel
    private lateinit var binding: FragmentStartBinding
    private lateinit var adapter: StartAdapter
    private var playersNum: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialSharedAxis.create(MaterialSharedAxis.Z, false)

        reenterTransition = MaterialSharedAxis.create(MaterialSharedAxis.Z, true)


        viewModelInitialize()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_start, container, false)


        //setUp toolbar
        (activity as AppCompatActivity).setSupportActionBar(binding.tbStart)
        binding.tbStart.setupWithNavController(findNavController())

        recyclerViewInitialize()
        observers(container)
        myClickListener()

        return binding.root

    }

    private fun myClickListener() {
        val editText = binding.startEdt
        val addButton = binding.startAddBtn
        addButton.setOnClickListener { view ->
            if (!editText.text!!.isEmpty()) {
                startViewModel.insert(editText.text.toString())
                val imm =
                    requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
                editText.text.clear()
                editText.clearFocus()

            } else Toast.makeText(activity, "اول سمت چپ یه اسم وارد کن :)", Toast.LENGTH_SHORT)
                .show()
        }

        binding.nextBtn.setOnClickListener {
            if (playersNum > 2) {
                findNavController().navigate(R.id.action_nav_start_to_characterFragment)
                startViewModel.mafiaReminder = false
            } else Toast.makeText(
                activity,
                "با کمتر از سه نفر نمیتونی بازی کنی :)",
                Toast.LENGTH_SHORT
            ).show()
        }
        binding.setting.setOnClickListener {

            val newFragment = ConfigDialog(playersNum)
            newFragment.show(childFragmentManager, "")
        }
    }

    private fun observers(container: ViewGroup?) {
        //list item observer from view model
        startViewModel.persons.observe(viewLifecycleOwner, Observer { names ->
            adapter.submitList(names)
        })

        startViewModel.Names.observe(this.viewLifecycleOwner, Observer { p ->

            TransitionManager.beginDelayedTransition(
                container,
                MaterialSharedAxis.create(MaterialSharedAxis.Z, true)
            )

            playersNum = p.size

            if (playersNum > 2)
                binding.setting.visibility = View.VISIBLE
            else
                binding.setting.visibility = View.GONE


            if (startViewModel.mafiaReminder) {
                ConfigDialog.Mafia = playersNum / 3
                ConfigDialog.Citizen = playersNum - ConfigDialog.Mafia
            }


        })

    }

    private fun recyclerViewInitialize() {
        val list = binding.startList
        adapter =
            StartAdapter(StarDeletetListiner { person: Person, b: Boolean ->
                deletePerson(
                    person,
                    b
                )
            },
                StartItemListiner { person: Person, b: Boolean -> listItemClick(person, b) })
        list.adapter = adapter

    }

    private fun listItemClick(
        person: Person,
        b: Boolean
    ) {
        if (b) {
            person.isChecked = true
            startViewModel.update(person)
        } else {
            person.isChecked = false
            startViewModel.update(person)
        }

    }

    private fun deletePerson(person: Person, b: Boolean) {
        startViewModel.delete(person)
    }

    private fun viewModelInitialize() {
        val application = requireNotNull(this.activity).application
        val data = PersonDataBase.getInstance(application).personDao
        val factory = StartViewModelFactory(data, application)
        startViewModel =
            ViewModelProviders.of(this, factory).get(StartViewModel::class.java)


    }

    override fun onResume() {
        super.onResume()
        startViewModel.setAllCitizenCharactertoFalse()
        startViewModel.mafiaReminder = true
    }
}

