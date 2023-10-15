package com.example.midtermproj

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.random.Random

enum class GuessResult
{
    HIGHER,
    LOWER,
    CORRECT
}

class GuessedEventArgs(pname: String, numAttempts: Int, result :GuessResult) {
    var pName = pname
    var numAttempts = numAttempts
    var guessResult = result
}

class GameViewModel(dao: HighscoreDao) : ViewModel() {
    var GuessedEvent = Event<GuessedEventArgs>()

    var playerName = ""
    //var prevScore = 0 // "score" and "attempts" i believe are synonymous in this assignment
    var numAttempts = 0

    var currentNumber = 0

    var allHighscores = dao.getAll()

    fun SetPlayerName(pName: String)
    {
        playerName = pName
    }
    fun Initialize()
    {
        currentNumber = Random.nextInt(1, 100 + 1)
        numAttempts = 0
        GuessedEvent = Event<GuessedEventArgs>()
    }

    fun AttemptGuess(guessNum: Int) : GuessResult
    {
        numAttempts += 1
        var result = GuessResult.CORRECT
        if (guessNum < currentNumber)
        {
            result = GuessResult.HIGHER
        }
        else if (guessNum > currentNumber)
        {
            result = GuessResult.LOWER
        }
        // Notify all subscribers about this guess
        GuessedEvent.invoke(GuessedEventArgs(playerName, numAttempts, result))
        return result;
    }

    fun RecordHighscoreInDatabase(guessedEventArgs: GuessedEventArgs, dao: HighscoreDao)
    {
        var highscore = Highscore()
        highscore.numAttempts = guessedEventArgs.numAttempts
        highscore.playerName = guessedEventArgs.pName
        viewModelScope.launch {
            dao.insert(highscore)
        }
    }

    //fun GetHighscores(dao: HighscoreDao) : List<Highscore>
    //{
        //val highscores = dao.getAll()
        //return highscores
        //var highscores = listOf<Highscore>()
        //var highscores_ld: LiveData<List<Highscore>> = MutableLiveData()
        //val job = viewModelScope.launch {
        //    highscores_ld = dao.getAll()
        //}
        //while (!highscores_ld.isInitialized) {} // spinlock
        //return highscores_ld.value!!
    //}

    fun DeleteHighscore(highscoreId: Int, dao: HighscoreDao)
    {
        viewModelScope.launch {
            val highscore = dao.get(highscoreId).await()
            dao.delete(highscore)
        }
    }

}




public suspend fun <T> LiveData<T>.await(): T {
    return withContext(Dispatchers.Main.immediate) {
        suspendCancellableCoroutine { continuation ->
            val observer = object : Observer<T> {
                override fun onChanged(value: T) {
                    removeObserver(this)
                    continuation.resume(value)
                }
            }
            observeForever(observer)
            continuation.invokeOnCancellation {
                removeObserver(observer)
            }
        }
    }
}


class Event<T> {
    private val observers = mutableSetOf<(T) -> Unit>()
    operator fun plusAssign(observer: (T) -> Unit) {
        observers.add(observer)
    }
    operator fun minusAssign(observer: (T) -> Unit) {
        observers.remove(observer)
    }
    operator fun invoke(value: T) {
        for (observer in observers)
            observer(value)
    }
}