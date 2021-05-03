package com.github.petrchatrny.puzzle8.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.petrchatrny.puzzle8.R
import com.github.petrchatrny.puzzle8.collections.adapters.AttemptResultAdapter
import com.github.petrchatrny.puzzle8.databinding.HistoryFragmentBinding
import com.github.petrchatrny.puzzle8.model.entities.AttemptResult
import com.github.petrchatrny.puzzle8.model.enums.Algorithm
import com.github.petrchatrny.puzzle8.viewModel.HistoryViewModel
import kotlinx.android.synthetic.main.grid_fragment.toolbar
import kotlinx.android.synthetic.main.history_fragment.*
import java.util.*
import kotlin.random.Random

class HistoryFragment : Fragment() {
    private lateinit var viewModel: HistoryViewModel
    private lateinit var binding: HistoryFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.history_fragment, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)

        // set variables to binding
        binding.vm = viewModel

        // default values
        setupRecyclerView()
        activity?.setActionBar(toolbar)
    }

    private fun setupRecyclerView() {
        viewModel.allAttemptResults.observe(viewLifecycleOwner, {
            val attemptAdapter = AttemptResultAdapter(it)
            attemptAdapter.notifyDataSetChanged()

            historyRecyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = attemptAdapter
            }
        })
    }

    private fun prepopulateDB() {
        for (i in 0..20) {
            viewModel.insertAttemptResult(
                AttemptResult(
                    id = Random.nextInt(),
                    status = Random.nextBoolean(),
                    totalSteps = Random.nextInt(),
                    algorithm = Algorithm.values().toList().shuffled().first(),
                    timestamp = Date(),
                    steps = listOf()
                )
            )
        }
    }

}