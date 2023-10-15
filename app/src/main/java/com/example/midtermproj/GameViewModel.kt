package com.example.midtermproj

import androidx.lifecycle.ViewModel
import kotlin.random.Random

enum class GuessResult
{
    HIGHER,
    LOWER,
    CORRECT
}

class GameViewModel : ViewModel() {
    var GuessedCorrectlyEvent = Event<Pair<String, Int>>()

    var playerName = ""
    //var prevScore = 0 // "score" and "attempts" i believe are synonymous in this assignment
    var numAttempts = 0

    var currentNumber = 0

    fun SetPlayerName(pName: String)
    {
        playerName = pName
    }
    fun Initialize()
    {
        currentNumber = Random.nextInt(1, 100 + 1)
        numAttempts = 0
        GuessedCorrectlyEvent = Event<Pair<String, Int>>()
    }

    fun AttemptGuess(guessNum: Int) : GuessResult
    {
        numAttempts += 1
        var result = GuessResult.CORRECT
        if (guessNum < currentNumber)
        {
            result = GuessResult.LOWER
        }
        else if (guessNum > currentNumber)
        {
            result = GuessResult.HIGHER
        }
        return result;
    }

    fun SignalCorrectGuess()
    {
        // Notify all subscribers that we won with this player name and this score
        GuessedCorrectlyEvent.invoke(Pair(playerName, numAttempts))
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