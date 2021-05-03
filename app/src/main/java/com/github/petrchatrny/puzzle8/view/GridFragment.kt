package com.github.petrchatrny.puzzle8.view

import android.os.Bundle
import android.util.Log
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
import com.github.petrchatrny.puzzle8.databinding.GridFragmentBinding
import com.github.petrchatrny.puzzle8.model.entities.Algorithm
import com.github.petrchatrny.puzzle8.viewModel.GridViewModel
import kotlinx.android.synthetic.main.grid_fragment.*


class GridFragment : Fragment(), GridFragmentCallback {
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

        // set variables to binding
        binding.vm = viewModel

        // default values
        setupRecyclerView()
        setupSpinner()
        setupProgressBar()
        activity?.setActionBar(toolbar)
    }

    private fun setupRecyclerView() {
        // define adapter
        viewModel.matrix.observe(viewLifecycleOwner, {
            val numberAdapter = NumberAdapter(it.toIntArray())
            numberAdapter.notifyDataSetChanged()

            // apply adapter to recyclerView
            rvGrid.apply {
                layoutManager = GridLayoutManager(context, 3)
                adapter = numberAdapter
            }

            // check solvable
            Log.println(Log.ASSERT, "SOLVEABLE", it.isSolvable().toString())
        })
    }

    private fun setupSpinner() {
        val spinnerAdapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            Algorithm.values()
        )
        dropdownAlgorithm.setAdapter(spinnerAdapter)
        dropdownAlgorithm.setOnItemClickListener { _, _, position, _ ->
            run {
                viewModel.algorithm = spinnerAdapter.getItem(position)!!
            }
        }
    }

    private fun setupProgressBar() {
        loading.apply {
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

    //region Callback

    override fun onSolving() {
        // nullify errors
        dropdownAlgorithmLayout.error = null
        iterationsLayout.error = null

        loading.visibility = View.VISIBLE
        solveButton.visibility = View.GONE
    }

    override fun onAlgorithmError() {
        dropdownAlgorithmLayout.error = " "
    }

    override fun onIterationsError() {
        iterationsLayout.error = " "
    }

    override fun onSolved() {
        loading.visibility = View.GONE
        solveButton.visibility = View.VISIBLE
    }

    //endregion

}