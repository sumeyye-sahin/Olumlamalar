package com.sumeyyesahin.olumlamalar.viewmodel

import android.animation.ValueAnimator
import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sumeyyesahin.olumlamalar.R

class BreathViewModel(application: Application) : AndroidViewModel(application) {

    private val _instructionText = MutableLiveData<String>()
    val instructionText: LiveData<String> get() = _instructionText

    private val _roundCounterText = MutableLiveData<String>()
    val roundCounterText: LiveData<String> get() = _roundCounterText

    private val _animationProgress = MutableLiveData<Float>()
    val animationProgress: LiveData<Float> get() = _animationProgress

    private val _animationMode = MutableLiveData<Int>()
    val animationMode: LiveData<Int> get() = _animationMode

    private val _isExerciseComplete = MutableLiveData<Boolean>()
    val isExerciseComplete: LiveData<Boolean> get() = _isExerciseComplete

    private var currentRound = 0
    private val handler = Handler(Looper.getMainLooper())

    fun startBreathingExercise(repeatCount: Int) {
        currentRound = 0
        _isExerciseComplete.value = false
        startNextCycle(repeatCount)
    }

    private fun startNextCycle(repeatCount: Int) {
        if (currentRound >= repeatCount) {
            _isExerciseComplete.value = true
            return
        }

        val resources = getApplication<Application>().resources
        val breatheInDuration = 4000L
        val holdDuration = 7000L
        val breatheOutDuration = 8000L
        val totalCycleDuration = breatheInDuration + holdDuration + breatheOutDuration

        handler.postDelayed({
            _instructionText.value = resources.getString(R.string.nefesAl)
            _animationMode.value = 0
            _animationProgress.value = 0f
            startProgressAnimation(breatheInDuration, 1f)
        }, 0)

        handler.postDelayed({
            _instructionText.value = resources.getString(R.string.nefesTut)
            _animationMode.value = 1
            startProgressAnimation(holdDuration, 1f)
        }, breatheInDuration)

        handler.postDelayed({
            _instructionText.value = resources.getString(R.string.nefesVer)
            _animationMode.value = 2
            startProgressAnimation(breatheOutDuration, 0f)
        }, breatheInDuration + holdDuration)

    }
    private fun startProgressAnimation(duration: Long, targetProgress: Float) {
        val animator = ValueAnimator.ofFloat(_animationProgress.value ?: 0f, targetProgress).apply {
            this.duration = duration
            addUpdateListener { animation ->
                _animationProgress.value = animation.animatedValue as Float
            }
            start()
        }
    }

    override fun onCleared() {
        super.onCleared()
        handler.removeCallbacksAndMessages(null)
    }
}
