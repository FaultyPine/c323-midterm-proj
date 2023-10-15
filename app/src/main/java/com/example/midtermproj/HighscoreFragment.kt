package com.example.midtermproj

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.midtermproj.databinding.FragmentGameBinding
import com.example.midtermproj.databinding.FragmentHighscoreBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HighscoreFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HighscoreFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentHighscoreBinding
    var hasHighscores = false

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
        //val view = inflater.inflate(R.layout.fragment_highscore, container, false)
        binding = FragmentHighscoreBinding.inflate(inflater, container, false)
        val view = binding.root

        val application = requireNotNull(this.activity).application
        val dao = HighscoreDatabaseImpl.getInstance(application).highscoreDao
        val viewModelFactory = GameViewModelFactory(dao)
        //val gameViewModel = ViewModelProvider(this)[GameViewModel::class.java]
        val gameViewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(GameViewModel::class.java)

        //val highscores = gameViewModel.GetHighscores(dao)

        val adapter = HighscoreAdapter(
            { id: Int -> run {}}, // on highscore clicked
            { id: Int -> run {
                val dialog = HighscoreDeleteDialog(id)
                dialog.show(childFragmentManager, HighscoreDeleteDialog.TAG)
            }}
        )
        binding.highscoreRecyclerView.adapter = adapter

        gameViewModel.allHighscores.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
                binding.textNoScoresText.visibility = INVISIBLE
                binding.highscoreRecyclerView.visibility = VISIBLE
            }
        })

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HighscoreFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HighscoreFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}