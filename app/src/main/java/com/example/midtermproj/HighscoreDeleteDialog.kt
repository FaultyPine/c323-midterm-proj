package com.example.midtermproj

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider


class HighscoreDeleteDialog(highscoreId: Int) : DialogFragment() {
    val highscoreId = highscoreId
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setMessage("Are you sure you want to delete?")
            .setPositiveButton("Delete") { _,_ ->
                val application = requireNotNull(this.activity).application
                val dao = HighscoreDatabaseImpl.getInstance(application).highscoreDao
                val viewModelFactory = GameViewModelFactory(dao)
                //val gameViewModel = ViewModelProvider(this)[GameViewModel::class.java]
                val gameViewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(GameViewModel::class.java)
                gameViewModel.DeleteHighscore(highscoreId, dao)
            }
            .setNegativeButton("Cancel"){ _,_ -> }
            .create()




    companion object {
        const val TAG = "NoteDeleteDialogFragment"
    }
}