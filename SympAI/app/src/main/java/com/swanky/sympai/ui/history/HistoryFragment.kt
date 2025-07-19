package com.swanky.sympai.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.swanky.sympai.R
import com.swanky.sympai.SympAIApplication
import com.swanky.sympai.databinding.FragmentHistoryBinding
import com.swanky.sympai.ui.history.adapter.HistoryAdapter

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var viewModel: HistoryViewModel
    private lateinit var historyAdapter: HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val repository = (requireActivity().application as SympAIApplication).repository
        viewModel = ViewModelProvider(
            this,
            HistoryViewModel.HistoryViewModelFactory(repository)
        )[HistoryViewModel::class.java]
        
        setupRecyclerView()
        setupObservers()
        setupListeners()
    }
    
    private fun setupRecyclerView() {
        historyAdapter = HistoryAdapter(
            onItemClick = { analysis ->
                val action = HistoryFragmentDirections.actionHistoryFragmentToResultFragment(analysis)
                findNavController().navigate(action)
            },
            onDeleteClick = { analysis ->
                showDeleteConfirmationDialog(analysis.id)
            }
        )
        
        binding.historyRecyclerView.apply {
            adapter = historyAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
    
    private fun setupObservers() {
        viewModel.allAnalyses.observe(viewLifecycleOwner) { analyses ->
            historyAdapter.submitList(analyses)
            
            // Show empty state if no analyses
            if (analyses.isEmpty()) {
                binding.emptyStateGroup.visibility = View.VISIBLE
                binding.historyRecyclerView.visibility = View.GONE
            } else {
                binding.emptyStateGroup.visibility = View.GONE
                binding.historyRecyclerView.visibility = View.VISIBLE
            }
        }
    }
    
    private fun setupListeners() {
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }
    
    private fun showDeleteConfirmationDialog(analysisId: Long) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.delete_analysis_title)
            .setMessage(R.string.delete_analysis_message)
            .setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(R.string.delete) { _, _ ->
                viewModel.deleteAnalysis(analysisId)
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}