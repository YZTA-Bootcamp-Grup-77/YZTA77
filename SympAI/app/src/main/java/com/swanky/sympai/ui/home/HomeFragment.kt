package com.swanky.sympai.ui.home

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.swanky.sympai.R
import com.swanky.sympai.SympAIApplication
import com.swanky.sympai.databinding.FragmentHomeBinding
import com.swanky.sympai.utils.Constants
import java.util.Locale

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var viewModel: HomeViewModel
    
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            startSpeechRecognition()
        } else {
            Snackbar.make(
                binding.root,
                "Microphone permission is required for voice input",
                Snackbar.LENGTH_LONG
            ).show()
        }
    }
    
    private val speechRecognitionLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == android.app.Activity.RESULT_OK) {
            val data = result.data
            val results = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val spokenText = results?.get(0) ?: ""
            
            if (spokenText.isNotEmpty()) {
                binding.inputText.setText(spokenText)
                analyzeSymptoms(spokenText)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val repository = (requireActivity().application as SympAIApplication).repository
        viewModel = ViewModelProvider(
            this,
            HomeViewModel.HomeViewModelFactory(repository)
        )[HomeViewModel::class.java]
        
        setupObservers()
        setupListeners()
    }
    
    private fun setupObservers() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.loadingContainer.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.micButtonCard.isEnabled = !isLoading
            binding.analyzeButton.isEnabled = !isLoading
            binding.inputMethodsCard.isEnabled = !isLoading
        }
        
        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
            }
        }
        
        viewModel.analysisResult.observe(viewLifecycleOwner) { result ->
            result?.let {
                // Navigate to result fragment
                val action = HomeFragmentDirections.actionHomeFragmentToResultFragment(it)
                findNavController().navigate(action)
                viewModel.clearResult()
            }
        }
    }
    
    private fun setupListeners() {
        binding.micButtonCard.setOnClickListener {
            checkMicrophonePermission()
        }
        
        binding.analyzeButton.setOnClickListener {
            val text = binding.inputText.text.toString().trim()
            if (text.isNotEmpty()) {
                analyzeSymptoms(text)
            } else {
                Snackbar.make(
                    binding.root,
                    "Lütfen önce semptomlarınızı açıklayın",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
        
        binding.historyButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_historyFragment)
        }
        
        binding.healthTipsButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_healthTipsFragment)
        }
        
        // Mikrofon ikonuna da tıklama işlevi ekleyelim
        binding.micButton.setOnClickListener {
            checkMicrophonePermission()
        }
        
        // Ana animasyonu tekrar oynatma
        binding.lottieAnimationView.setOnClickListener {
            if (!binding.lottieAnimationView.isAnimating) {
                binding.lottieAnimationView.playAnimation()
            }
        }
    }
    
    private fun checkMicrophonePermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED -> {
                startSpeechRecognition()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.RECORD_AUDIO) -> {
                Snackbar.make(
                    binding.root,
                    "Microphone access is required for voice input",
                    Snackbar.LENGTH_INDEFINITE
                ).setAction("Grant") {
                    requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                }.show()
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
            }
        }
    }
    
    private fun startSpeechRecognition() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            putExtra(RecognizerIntent.EXTRA_PROMPT, "Describe your symptoms...")
        }
        
        try {
            speechRecognitionLauncher.launch(intent)
        } catch (e: Exception) {
            Toast.makeText(
                requireContext(),
                "Speech recognition not supported on this device",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    
    private fun analyzeSymptoms(text: String) {
        binding.inputText.setText(text)
        viewModel.analyzeSymptoms(text)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}