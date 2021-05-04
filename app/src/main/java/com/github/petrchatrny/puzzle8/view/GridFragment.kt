package com.github.petrchatrny.puzzle8.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.github.petrchatrny.puzzle8.R
import com.github.petrchatrny.puzzle8.collections.adapters.NumberAdapter
import com.github.petrchatrny.puzzle8.collections.onClickListeners.OnNumberClickListener
import com.github.petrchatrny.puzzle8.databinding.GridFragmentBinding
import com.github.petrchatrny.puzzle8.model.enums.Algorithm
import com.github.petrchatrny.puzzle8.viewModel.GridViewModel


class GridFragment : Fragment(), GridFragmentCallback, OnNumberClickListener {
    private lateinit var viewModel: GridViewModel
    private lateinit var binding: GridFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.grid_fragment, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GridViewModel::class.java)
        viewModel.callback = this
        viewModel.randomPuzzle()

        // set variables to binding
        binding.vm = viewModel

        // default values
        setupRecyclerView()
        setupSpinner()
        setupProgressBar()
        activity?.setActionBar(binding.toolbar)
    }

    //region Setups

    private fun setupRecyclerView() {
        // define adapter
        viewModel.matrix.observe(viewLifecycleOwner, {
            val numberAdapter = NumberAdapter(it.toIntArray(), this, 65)
            numberAdapter.notifyDataSetChanged()

            // apply adapter to recyclerView
            binding.pathRecyclerView.apply {
                layoutManager = GridLayoutManager(context, 3)
                adapter = numberAdapter
            }
        })
    }

    private fun setupSpinner() {
        val spinnerAdapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            Algorithm.values()
        )
        binding.dropdownAlgorithm.setAdapter(spinnerAdapter)
        binding.dropdownAlgorithm.setOnItemClickListener { _, _, position, _ ->
            run {
                viewModel.algorithm = spinnerAdapter.getItem(position)!!
            }
        }
    }

    private fun setupProgressBar() {
        binding.loading.apply {
            viewModel.iterations.observe(viewLifecycleOwner, {
                max = if (it == null || it == "") {
                    0
                } else {
                    it.toInt()
                }
            })
            viewModel.counter.observe(viewLifecycleOwner, {
                progress = it
            })
        }
    }

    //endregion

    //region Callback

    override fun onSolving() {
        // nullify errors
        binding.dropdownAlgorithmLayout.error = null
        binding.iterationsLayout.error = null

        binding.loading.visibility = View.VISIBLE
        binding.solveButton.visibility = View.GONE
    }

    override fun onAlgorithmError() {
        binding.dropdownAlgorithmLayout.error = " "
    }

    override fun onIterationsError() {
        binding.iterationsLayout.error = " "
    }

    override fun onSolved() {
        binding.loading.visibility = View.GONE
        binding.solveButton.visibility = View.VISIBLE

        // increase notification
        val mainActivity = activity as MainActivity
        mainActivity.binding.mainBottomNav.getOrCreateBadge(R.id.historyFragment).number += 1
    }

    override fun onNumberClick(number: Int) {
        viewModel.matrix.value?.apply {
            val coords = this.getCoordsByNumber(number)
            this.swap(coords)
            setupRecyclerView()
        }
    }

    //endregion

}