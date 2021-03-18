package com.yasin.mafia.ui.start

import android.app.Dialog
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.yasin.mafia.R
import com.yasin.mafia.databinding.ConfigDialogBinding

class ConfigDialog(val playersNum: Int) : DialogFragment() {
    companion object {
        var Mafia: Int = 0
        var Citizen: Int = 0
    }

    private var oldMafia: Int = Mafia
    private var oldCitizen: Int = Citizen


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater;

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            val view = inflater.inflate(R.layout.config_dialog, null)
            val binding = DataBindingUtil.bind<ConfigDialogBinding>(view)
            val txtMafia = binding?.vSTxtMafiaNum
            val txtCitizen = binding?.vSTxtCitizenNum

            val buzzer = activity?.getSystemService<Vibrator>()


            setUpTextViews(txtCitizen, txtMafia)

            binding?.vSImgMafiaPlus?.setOnClickListener {
                if (Citizen > 0) {
                    it.startAnimation(
                        AnimationUtils.loadAnimation(
                            view.context,
                            R.anim.rotate_a
                        )
                    )
                    plusMafia()
                    setUpTextViews(txtCitizen, txtMafia)
                } else {
                    buzzer?.let {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            buzzer.vibrate(VibrationEffect.createWaveform(longArrayOf(0, 100), -1))
                        } else {
                            //deprecated in API 26
                            buzzer.vibrate(longArrayOf(0, 100), -1)
                        }
                    }
                }

            }
            binding?.vSImgMafiaMinus?.setOnClickListener {
                if (Mafia > 0) {
                    it.startAnimation(
                        AnimationUtils.loadAnimation(
                            view.context,
                            R.anim.rotate
                        )
                    )
                    removeMafia()
                    setUpTextViews(txtCitizen, txtMafia)
                } else {
                    buzzer?.let {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            buzzer.vibrate(VibrationEffect.createWaveform(longArrayOf(0, 100), -1))
                        } else {
                            //deprecated in API 26
                            buzzer.vibrate(longArrayOf(0, 100), -1)
                        }
                    }
                }
            }
            binding?.vSImgCitizenPlus?.setOnClickListener {
                if (Mafia > 0) {
                    it.startAnimation(
                        AnimationUtils.loadAnimation(
                            view.context,
                            R.anim.rotate_a
                        )
                    )
                    removeMafia()
                    setUpTextViews(txtCitizen, txtMafia)
                } else {
                    buzzer?.let {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            buzzer.vibrate(VibrationEffect.createWaveform(longArrayOf(0, 100), -1))
                        } else {
                            //deprecated in API 26
                            buzzer.vibrate(longArrayOf(0, 100), -1)
                        }
                    }
                }

            }
            binding?.vSImgCitizenMinus?.setOnClickListener {
                if (Citizen > 0) {
                    it.startAnimation(
                        AnimationUtils.loadAnimation(
                            view.context,
                            R.anim.rotate
                        )
                    )
                    plusMafia()
                    setUpTextViews(txtCitizen, txtMafia)

                } else {
                    buzzer?.let {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            buzzer.vibrate(VibrationEffect.createWaveform(longArrayOf(0, 100), -1))
                        } else {
                            //deprecated in API 26
                            buzzer.vibrate(longArrayOf(0, 100), -1)
                        }
                    }
                }
            }

            binding?.vSBtnSubmit?.setOnClickListener {

                dismiss()
            }
            binding?.vSBtnCancel?.setOnClickListener {
                dialog?.cancel()
            }
            binding?.vSBtnReset?.setOnClickListener {
                Mafia = playersNum / 3
                Citizen = playersNum - Mafia
                setUpTextViews(txtCitizen, txtMafia)
            }
            builder.setView(view)


            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun setUpTextViews(
        txtCitizen: TextView?,
        txtMafia: TextView?
    ) {
        txtCitizen?.text = Citizen.toString()
        txtMafia?.text = Mafia.toString()
    }

    private fun plusMafia() {
        Mafia++
        Citizen--
    }

    private fun removeMafia() {
        Mafia--
        Citizen++
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        Mafia = oldMafia
        Citizen = oldCitizen
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
    }
}

