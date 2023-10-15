package com.example.midtermproj

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.midtermproj.databinding.FragmentAttemptBinding
import com.example.midtermproj.databinding.FragmentGameBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AttemptFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AttemptFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentAttemptBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //val view = inflater.inflate(R.layout.fragment_attempt, container, false)
        binding = FragmentAttemptBinding.inflate(inflater, container, false)
        val view = binding.root

        val application = requireNotNull(this.activity).application
        val dao = HighscoreDatabaseImpl.getInstance(application).highscoreDao
        val viewModelFactory = GameViewModelFactory(dao)
        //val gameViewModel = ViewModelProvider(this)[GameViewModel::class.java]
        val gameViewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(GameViewModel::class.java)


        val attemptsTextView = binding.textAttempts

        gameViewModel.GuessedEvent += {
            val guessedEventArgs = it
            attemptsTextView.text = "Number of attempts: ${guessedEventArgs.numAttempts}"
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AttemptFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AttemptFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}