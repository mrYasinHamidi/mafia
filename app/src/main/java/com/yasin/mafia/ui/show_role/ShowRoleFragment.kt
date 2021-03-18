package com.yasin.mafia.ui.show_role

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.transition.MaterialArcMotion
import com.google.android.material.transition.MaterialContainerTransform
import com.yasin.mafia.DataBase.PersonDataBase
import com.yasin.mafia.R
import com.yasin.mafia.databinding.ShowDialogBinding
import com.yasin.mafia.databinding.ShowRoleFragmentBinding

class ShowRoleFragment : Fragment() {

    private lateinit var viewModel: ShowRoleViewModel
    private lateinit var binding: ShowRoleFragmentBinding
    private lateinit var adapter: ShowRoleAdapter
    private lateinit var root: FrameLayout
    private lateinit var endCard: MaterialCardView
    private lateinit var recyclerView: RecyclerView
    private lateinit var backDialog: FrameLayout
    private var selectedImage: ImageView? = null
    private var startCard: View? = null
    private val enterDuration = 500L
    private val returnDuration = 475L


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.show_role_fragment, container, false)

        //toolBar
        (activity as AppCompatActivity).setSupportActionBar(binding.showToolbar)
        binding.showToolbar.setupWithNavController(findNavController())

        root = binding.showViewRoot
        endCard = binding.showEndView
        backDialog = binding.dialogBack

        //initialize view model and list of item for recycler view
        init()
        initializeRecyclerView()

        myClickLisibers()

        viewModeObservers()

        return binding.root
    }

    private fun init() {

        val application = requireNotNull(this.activity).application
        val data = PersonDataBase.getInstance(application).personDao
        val factory = ShowRoleFactory(data, application)
        viewModel = ViewModelProviders.of(this, factory).get(ShowRoleViewModel::class.java)


    }

    private fun initializeRecyclerView() {
        recyclerView = binding.showRv
        val rvManager = GridLayoutManager(activity, 4, GridLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = rvManager
        adapter = buildAdapter()
        recyclerView.adapter = adapter


    }

    private fun buildAdapter(): ShowRoleAdapter {
        return ShowRoleAdapter(ShowItemListiner { card, startView, img ->
            selectedImage = img
            startCard = startView
            if (card.isShown) {
                val showDialog = ShowDialog(ShowDialog.ShowDialogListiner {
                    if (it)
                        showEndView(card)
                })
                showDialog.show(childFragmentManager, "")
            } else showEndView(card)

        })
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun myClickLisibers() {
        endCard.setOnClickListener {
            showStartView()

        }
        binding.showRefresh.setOnClickListener {
            initializeRecyclerView()
            viewModel.initList(requireActivity().application)
        }
        backDialog.setOnClickListener {
            showStartView()
        }
    }

    private fun showEndView(card: Card) {

        // Construct a container transform transition between two views.
        val transition = buildContainerTransform(true)
        transition.startView = startCard
        transition.endView = endCard


        // Trigger the container transform transition.
        TransitionManager.beginDelayedTransition(root, transition)

        //setRole
        binding.showEndCardName.text =
            "نام : " + card.character.role + "\n" + "گروه " + if (card.character.side == 1) "مافیا" else "شهروند"


        //setDescription
        binding.shoeEndCardDesc.text = card.character.discription

        //setImage
        var photoName: String = card.character.image
        if (photoName.contains(".")) {
            photoName = photoName.substring(0, photoName.lastIndexOf('.'))
        }
        val imageResId: Int = getResources().getIdentifier(
            photoName, "drawable", context?.getApplicationContext()?.getPackageName()
        )
        binding.showEndCardImg.setImageResource(imageResId)
        card.isShown = true

        startCard?.visibility = View.INVISIBLE
        endCard.visibility = View.VISIBLE
        backDialog.visibility = View.VISIBLE
    }

    private fun showStartView() {

        // Construct a container transform transition between two views.
        val transition = buildContainerTransform(false)
        transition.startView = endCard
        transition.endView = startCard


        // Trigger the container transform transition.
        TransitionManager.beginDelayedTransition(root, transition)

        selectedImage?.setImageResource(R.drawable.police)

        startCard?.visibility = View.VISIBLE
        endCard.visibility = View.INVISIBLE
        backDialog.visibility = View.GONE
    }

    private fun buildContainerTransform(entering: Boolean): MaterialContainerTransform {
        val transform = MaterialContainerTransform()
        transform.scrimColor = Color.TRANSPARENT
        transform.setDuration(if (entering) enterDuration else returnDuration)
        transform.setInterpolator(FastOutSlowInInterpolator())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            transform.pathMotion = MaterialArcMotion()
        }

        return transform
    }

    private fun viewModeObservers() {
        viewModel.Cards.observe(viewLifecycleOwner, Observer { a ->
            adapter.cards = a
        })

    }
}

public class ShowDialog(val showDialogListiner: ShowDialogListiner) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater;

            val view = inflater.inflate(R.layout.show_dialog, null)
            val binding = DataBindingUtil.bind<ShowDialogBinding>(view)
            builder.setView(view)
            binding?.showDialogButtonOk?.setOnClickListener {
                showDialogListiner.onItemClick(true)
                dismiss()
            }
            binding?.showDialogButtonCancel?.setOnClickListener {
                dismiss()
            }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    class ShowDialogListiner(val listiner: (ok: Boolean) -> Unit) {
        fun onItemClick(ok: Boolean) = listiner(ok)
    }
}
