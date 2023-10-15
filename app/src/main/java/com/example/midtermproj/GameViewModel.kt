package com.example.midtermproj

import androidx.lifecycle.ViewModel
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

class GameViewModel : ViewModel() {
    var GuessedEvent = Event<GuessedEventArgs>()

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