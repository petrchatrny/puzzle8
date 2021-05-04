package com.github.petrchatrny.puzzle8.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.github.petrchatrny.puzzle8.model.repositories.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingsViewModel(app: Application) : AndroidViewModel(app) {
    private val repository = SettingsRepository(app)
    val settings = repository.getSettings().asLiveData()

    fun save() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveSettings(settings.value!!)
        }
    }
}