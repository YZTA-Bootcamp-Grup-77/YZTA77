package com.swanky.sympai.ui.healthtips

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.swanky.sympai.R
import com.swanky.sympai.data.model.HealthTip
import com.swanky.sympai.data.model.HealthTipCategory
import com.swanky.sympai.data.repository.HealthTipsRepository
import com.swanky.sympai.databinding.FragmentHealthTipsBinding
import com.swanky.sympai.ui.healthtips.adapter.HealthTipsAdapter

class HealthTipsFragment : Fragment() {

    private var _binding: FragmentHealthTipsBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var viewModel: HealthTipsViewModel
    private lateinit var adapter: HealthTipsAdapter
    private var allHealthTips: List<HealthTip> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHealthTipsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val repository = HealthTipsRepository()
        viewModel = ViewModelProvider(
            this,
            HealthTipsViewModel.HealthTipsViewModelFactory(repository)
        )[HealthTipsViewModel::class.java]
        
        setupRecyclerView()
        setupObservers()
        setupListeners()
    }
    
    private fun setupRecyclerView() {
        adapter = HealthTipsAdapter()
        binding.healthTipsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@HealthTipsFragment.adapter
        }
    }
    
    private fun setupObservers() {
        viewModel.healthTips.observe(viewLifecycleOwner) { tips ->
            allHealthTips = tips
            adapter.submitList(tips)
        }
        
        viewModel.selectedCategory.observe(viewLifecycleOwner) { category ->
            updateCategorySelection(category)
        }
    }
    
    private fun setupListeners() {
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
        
        binding.categoryChipGroup.setOnCheckedStateChangeListener { _, checkedIds ->
            if (checkedIds.isNotEmpty()) {
                val selectedCategory = when (checkedIds[0]) {
                    R.id.chipDailyTips -> HealthTipCategory.DAILY_TIPS
                    R.id.chipCommonDiseases -> HealthTipCategory.COMMON_DISEASES
                    R.id.chipFirstAid -> HealthTipCategory.FIRST_AID
                    R.id.chipHealthyLiving -> HealthTipCategory.HEALTHY_LIVING
                    else -> HealthTipCategory.DAILY_TIPS
                }
                viewModel.selectCategory(selectedCategory)
            }
        }
        
        // Arama özelliği
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            
            override fun afterTextChanged(s: Editable?) {
                filterHealthTips(s.toString())
            }
        })
    }
    
    private fun filterHealthTips(query: String) {
        if (query.isEmpty()) {
            adapter.submitList(allHealthTips)
        } else {
            val filteredList = allHealthTips.filter { tip ->
                tip.title.contains(query, ignoreCase = true) ||
                tip.description.contains(query, ignoreCase = true)
            }
            adapter.submitList(filteredList)
        }
    }
    
    private fun updateCategorySelection(category: HealthTipCategory) {
        val chipId = when (category) {
            HealthTipCategory.DAILY_TIPS -> R.id.chipDailyTips
            HealthTipCategory.COMMON_DISEASES -> R.id.chipCommonDiseases
            HealthTipCategory.FIRST_AID -> R.id.chipFirstAid
            HealthTipCategory.HEALTHY_LIVING -> R.id.chipHealthyLiving
        }
        
        binding.categoryChipGroup.check(chipId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}