package com.example.midtermproj

import android.R.attr.data
import android.media.MediaPlayer
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.midtermproj.databinding.FragmentGuessBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GuessFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GuessFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentGuessBinding

    var currentGuessText = ""

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
        //val view =  inflater.inflate(R.layout.fragment_guess, container, false)
        binding = FragmentGuessBinding.inflate(inflater, container, false)
        val view = binding.root

        val viewModelFactory = GameViewModelFactory()
        val gameViewModel = ViewModelProvider(this, viewModelFactory)[GameViewModel::class.java]

        binding.editTextPlayerName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(mEdit: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int)
            {
                gameViewModel.SetPlayerName(s.toString())
            }
        })

        binding.editTextGuess.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(mEdit: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int)
            {
                currentGuessText = s.toString()
            }
        })

        binding.bPlus.setOnClickListener {
            var tryParseNumFromText = currentGuessText.toIntOrNull()
            if (tryParseNumFromText != null)
            {
                currentGuessText = (tryParseNumFromText+1).toString()
            }
        }
        binding.bMinus.setOnClickListener {
            var tryParseNumFromText = currentGuessText.toIntOrNull()
            if (tryParseNumFromText != null)
            {
                currentGuessText = (tryParseNumFromText-1).toString()
            }
        }

        val correctSound = MediaPlayer.create(context, R.raw.right)
        val wrongSound = MediaPlayer.create(context, R.raw.wrong)

        binding.bOk.setOnClickListener {
            var tryParseNumFromText = currentGuessText.toIntOrNull()
            if (tryParseNumFromText != null)
            {
                var guessedNum = tryParseNumFromText
                var guessResult = gameViewModel.AttemptGuess(guessedNum)
                if (guessResult == GuessResult.HIGHER)
                {
                    Toast.makeText(activity, "Higher!", Toast.LENGTH_LONG).show()
                    wrongSound.start()
                }
                else if (guessResult == GuessResult.LOWER)
                {
                    Toast.makeText(activity, "Lower!", Toast.LENGTH_LONG).show()
                    wrongSound.start()
                }
                else
                {
                    Toast.makeText(activity, "Correct!", Toast.LENGTH_LONG).show()
                    correctSound.start()
                    val finalNumAttempts = gameViewModel.numAttempts
                    // TODO: transition back to main screen passing it the number of attempts
                    // it explicitly specifies in the rubric we need to pass the data back to main screen, can't just access it

                }
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
         * @return A new instance of fragment GuessFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GuessFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}