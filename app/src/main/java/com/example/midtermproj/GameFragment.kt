package com.example.midtermproj

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.midtermproj.databinding.FragmentGameBinding
import com.example.midtermproj.databinding.FragmentMainBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GameFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GameFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentGameBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    fun GameFragmentToMainScreen(playerName: String, prevAttempts: Int)
    {
        val action = GameFragmentDirections.actionGameFragmentToMainFragment(playerName, prevAttempts)
        findNavController().navigate(action)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //val view = inflater.inflate(R.layout.fragment_game, container, false)
        binding = FragmentGameBinding.inflate(inflater, container, false)
        val view = binding.root

        val viewModelFactory = GameViewModelFactory()
        //val gameViewModel = ViewModelProvider(this)[GameViewModel::class.java]
        val gameViewModel = ViewModelProvider(requireActivity()).get(GameViewModel::class.java)

        val guessFragment = binding.guessFragment
        val attemptFragment = binding.attemptsFragment

        var fm = childFragmentManager.beginTransaction()
        fm.replace(R.id.guessFragment, GuessFragment()).commit()

        fm = childFragmentManager.beginTransaction()
        fm.replace(R.id.attemptsFragment, AttemptFragment()).commit()

        gameViewModel.Initialize()

        gameViewModel.GuessedEvent += {
            val guessedEventArgs: GuessedEventArgs = it
            val playerName = guessedEventArgs.pName
            val numAttempts = guessedEventArgs.numAttempts
            val guessResult = guessedEventArgs.guessResult
            if (guessResult == GuessResult.CORRECT)
            {
                GameFragmentToMainScreen(playerName, numAttempts)
            }
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
         * @return A new instance of fragment GameFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GameFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}