package com.example.viewmodelcomposecodelab.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.viewmodelcomposecodelab.data.allWords
import com.example.viewmodelcomposecodelab.utils.MAX_NO_OF_WORDS
import com.example.viewmodelcomposecodelab.utils.SCORE_INCREASE
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GameViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()
    private lateinit var currentWord: String
    private val usedWords: MutableSet<String> = mutableSetOf()
    var userGuess by mutableStateOf("")
        private set


    init {
        resetGame()
    }

    private fun pickRandomWordsAndShuffle(): String {
        currentWord = allWords.random()
        return if (usedWords.contains(currentWord)) {
            pickRandomWordsAndShuffle()
        } else {
            usedWords.add(currentWord)
            shuffleCurrentWord(currentWord)
        }
    }

    private fun shuffleCurrentWord(cWord: String): String {
        val tempWord = cWord.toCharArray()
        Log.e("tag_current_word", cWord )
        tempWord.shuffle()
        while (String(tempWord) == cWord) {
            tempWord.shuffle()
        }
        return String(tempWord)
    }

    fun updateUserGuess(guessedWord: String) {
        userGuess = guessedWord
    }

    fun checkUserGuess() {
        if (userGuess.equals(currentWord, ignoreCase = true)) {
            val updatedScore = _uiState.value.score.plus(SCORE_INCREASE)
            updateGameState(updatedScore)
        } else {
            _uiState.update { currentState ->
                currentState.copy(isGuessedWordWrong = true)
            }
        }

        updateUserGuess("")
    }

    private fun updateGameState(updatedScore: Int) {
        if(usedWords.size == MAX_NO_OF_WORDS) {
            _uiState.update { currentState ->
                currentState.copy(
                    isGuessedWordWrong = false,
                    score = updatedScore,
                    isGameOver = true
                )
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(
                    isGuessedWordWrong = false,
                    currentScrambledWord = pickRandomWordsAndShuffle(),
                    score = updatedScore,
                    currentWordCount = currentState.currentWordCount.plus(1)
                )
            }
        }
    }


    fun skipWord() {
        updateGameState(_uiState.value.score)
        updateUserGuess("")
    }


    fun resetGame() {
        usedWords.clear()
        _uiState.value = GameUiState(currentScrambledWord = pickRandomWordsAndShuffle())
    }
}