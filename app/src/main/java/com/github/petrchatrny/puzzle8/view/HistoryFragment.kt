package com.github.petrchatrny.puzzle8.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.petrchatrny.puzzle8.R
import com.github.petrchatrny.puzzle8.collections.adapters.AttemptResultAdapter
import com.github.petrchatrny.puzzle8.collections.onClickListeners.OnAttemptClickListener
import com.github.petrchatrny.puzzle8.databinding.HistoryFragmentBinding
import com.github.petrchatrny.puzzle8.model.entities.AttemptResult
import com.github.petrchatrny.puzzle8.viewModel.HistoryViewModel

class HistoryFragment : Fragment(), OnAttemptClickListener {
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
        activity?.setActionBar(binding.toolbar)

        // remove notifications
        val mainActivity = activity as MainActivity
        mainActivity.binding.mainBottomNav.removeBadge(R.id.historyFragment)
    }

    private fun setupRecyclerView() {
        viewModel.allAttemptResults.observe(viewLifecycleOwner, {
            val attemptAdapter = AttemptResultAdapter(it, this)
            attemptAdapter.notifyDataSetChanged()

            binding.historyRecyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = attemptAdapter
                setHasFixedSize(true)
            }
        })
    }

    override fun onAttemptClick(attempt: AttemptResult) {
        val action = HistoryFragmentDirections.actionHistoryFragmentToAttemptResultFragment(attempt)
        Navigation.findNavController(requireView()).navigate(action)
    }

}