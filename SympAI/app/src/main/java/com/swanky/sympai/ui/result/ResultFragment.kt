package com.swanky.sympai.ui.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.swanky.sympai.SympAIApplication
import com.swanky.sympai.databinding.FragmentResultBinding
import com.swanky.sympai.ui.result.adapter.ConditionAdapter
import java.text.SimpleDateFormat
import java.util.Locale

class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var viewModel: ResultViewModel
    private val args: ResultFragmentArgs by navArgs()
    private lateinit var conditionAdapter: ConditionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val repository = (requireActivity().application as SympAIApplication).repository
        viewModel = ViewModelProvider(
            this,
            ResultViewModel.ResultViewModelFactory(repository)
        )[ResultViewModel::class.java]
        
        setupRecyclerView()
        setupObservers()
        setupListeners()
        
        // Set the analysis from navigation args
        args.analysisResult?.let {
            viewModel.setAnalysis(it)
        }
    }
    
    private fun setupRecyclerView() {
        conditionAdapter = ConditionAdapter()
        binding.conditionsRecyclerView.apply {
            adapter = conditionAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
    
    private fun setupObservers() {
        viewModel.analysis.observe(viewLifecycleOwner) { analysis ->
            analysis?.let {
                // Format date
                val dateFormat = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
                val formattedDate = dateFormat.format(it.timestamp)
                
                // Update UI
                binding.userInputText.text = it.userInput
                binding.dateText.text = formattedDate
                binding.specialistText.text = it.specialistRecommendation
                binding.adviceText.text = it.generalAdvice
                
                // Update adapter
                conditionAdapter.submitList(it.possibleConditions)
            }
        }
    }
    
    private fun setupListeners() {
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
        
        binding.homeButton.setOnClickListener {
            findNavController().popBackStack(com.swanky.sympai.R.id.homeFragment, false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}