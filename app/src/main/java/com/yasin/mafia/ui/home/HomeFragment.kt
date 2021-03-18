package com.yasin.mafia.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.google.android.material.transition.MaterialFadeThrough
import com.google.android.material.transition.MaterialSharedAxis
import com.yasin.mafia.DataBase.PersonDataBase
import com.yasin.mafia.R
import com.yasin.mafia.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialSharedAxis.create(MaterialSharedAxis.Z, false)
        reenterTransition = MaterialSharedAxis.create(MaterialSharedAxis.Z, true)


        //this is for clean plater list
        val application = requireNotNull(this.activity).application
        val data = PersonDataBase.getInstance(application).personDao
        val factory = HomeFactory(data, application)
        homeViewModel =
            ViewModelProvider(this, factory).get(HomeViewModel::class.java)
        homeViewModel.initializeDataBase()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        //ToolBar
        val toolbar: Toolbar = binding.tbHome
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        //DrawerLayout
//        val drawerLayout: DrawerLayout = binding.drawerLayout
//        val navView: NavigationView = binding.navView
        val navController = findNavController()
//        val appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        toolbar.setupWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
        }







        binding.sartButton.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_nav_home_to_nav_start)
        }
        binding.homeBtnAboutUs.setOnClickListener { view ->
            Toast.makeText(activity,"Conect Us",Toast.LENGTH_SHORT).show()
        }
        binding.homeBtnRateUs.setOnClickListener { view ->
            Toast.makeText(activity,"Rate Us",Toast.LENGTH_SHORT).show()
        }
        binding.homeBtnRoles.setOnClickListener { view ->
            Toast.makeText(activity,"Roles",Toast.LENGTH_SHORT).show()
        }
        binding.homeBtnSetting.setOnClickListener { view ->
            Toast.makeText(activity,"Setting",Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }


    override fun onResume() {
        super.onResume()
        homeViewModel.setAllPersonToFalse()
    }
}
