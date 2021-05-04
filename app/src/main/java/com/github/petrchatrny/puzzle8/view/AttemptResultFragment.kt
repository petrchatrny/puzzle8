package com.github.petrchatrny.puzzle8.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.github.petrchatrny.puzzle8.R
import com.github.petrchatrny.puzzle8.databinding.AttemptResultFragmentBinding
import kotlinx.android.synthetic.main.grid_fragment.*

class AttemptResultFragment : Fragment() {
    private val args: AttemptResultFragmentArgs by navArgs()
    private lateinit var binding: AttemptResultFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.attempt_result_fragment, container, false)
        binding.lifecycleOwner = this
        binding.attempt = args.attemptResult
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.setActionBar(toolbar)
    }

}