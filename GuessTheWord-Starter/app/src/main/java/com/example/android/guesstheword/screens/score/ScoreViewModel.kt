package com.example.android.guesstheword.screens.score

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScoreViewModel(finalScore: Int, time: String): ViewModel() {
    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score
    private val _timeComplete = MutableLiveData<String>()
    val timeComplete: LiveData<String>
        get() = _timeComplete
    init {
        _score.value = finalScore
        _timeComplete.value = time
        Log.i("ScoreViewModel", "final score is ${finalScore}")
    }
}