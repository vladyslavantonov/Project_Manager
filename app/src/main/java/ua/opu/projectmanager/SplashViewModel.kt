package ua.opu.projectmanager

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {

    private val _navigateToMain = MutableLiveData<Boolean>()
    val navigateToMain: LiveData<Boolean> get() = _navigateToMain

    init {
        startSplashDelay()
    }

    private fun startSplashDelay() {
        viewModelScope.launch {
            delay(3000) // 3 секунди
            _navigateToMain.value = true
        }
    }
}
