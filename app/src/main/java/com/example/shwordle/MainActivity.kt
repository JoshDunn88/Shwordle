package com.example.shwordle

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.shwordle.FourLetterWordList.getRandomFourLetterWord
import android.widget.Toast


class MainActivity : AppCompatActivity() {
    //on start set wordtoguess
    var wordToGuess=getRandomFourLetterWord()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentGuess = findViewById<TextView>(R.id.guessInput)
        val guess2 = findViewById<TextView>(R.id.guess2)
        val guess1 = findViewById<TextView>(R.id.guess1)
        val guess3 = findViewById<TextView>(R.id.guess3)
        val score = findViewById<TextView>(R.id.message)
        val streak = findViewById<TextView>(R.id.streakCount)
        val mainButton = findViewById<Button>(R.id.submitButt)

        var guessCount = 0
        var streakCount = 0
        var validGuess= false

        mainButton.text="GUESS"
        streak.setTextColor(Color.BLUE)
        streak.text="Streak\n 0"



        mainButton.setOnClickListener{
            Log.v("cheat", wordToGuess)
            //check input
            if(currentGuess.text.length==4&&currentGuess.text.matches("[a-zA-Z]+".toRegex())){
                validGuess=true
            }
            else if(guessCount>-1){
                Toast.makeText(this, "Must be 4 letters A-Z", Toast.LENGTH_SHORT).show()
            }
            //reset
            if (guessCount==-1){
                score.setTextColor(Color.BLACK)
                guess1.text=""
                guess2.text=""
                guess3.text=""
                score.text="3 Guesses Left!"
                mainButton.text="GUESS"
                wordToGuess=getRandomFourLetterWord()
            }
            if (guessCount==0&&validGuess){
                guess1.text= "Guess 1: "+ currentGuess.text + "  " + checkGuess((currentGuess.text.toString()).uppercase())
                score.text="2 Guesses Left!"
            }
            if (guessCount==1&&validGuess){
                guess2.text= "Guess 2: "+ currentGuess.text + "  " + checkGuess((currentGuess.text.toString()).uppercase())
                score.text="1 Guess Left!"
            }
            //last guess
            if (guessCount==2&&validGuess){
                guess3.text= "Guess 3: "+ currentGuess.text + "  " + checkGuess((currentGuess.text.toString()).uppercase())

                if (checkGuess((currentGuess.text.toString()).uppercase())!="OOOO"){
                    score.setTextColor(Color.RED)
                    score.text="FAILURE: " + wordToGuess
                    mainButton.text="RESET"
                    guessCount=-2
                    streakCount=0
                    streak.setTextColor(Color.BLUE)
                    streak.text="Streak\n" + streakCount.toString()
                }
            }
            //on success
            if (guessCount!=-1 && validGuess && checkGuess((currentGuess.text.toString()).uppercase())=="OOOO"){
                score.setTextColor(Color.GREEN)
                score.text="SUCCESS!"
                mainButton.text="RESET"
                guessCount=-2
                streakCount++
                streak.setTextColor(Color.MAGENTA)
                streak.text="Streak\n" + streakCount.toString()
            }
            //valid guess or on reset
            if(validGuess||guessCount==-1) {
                currentGuess.text = ""
                guessCount++
            }
            validGuess=false
        }
    }

    //unchanged
    private fun checkGuess(guess: String) : String {
        var result = ""
        for (i in 0..3) {
            if (guess[i] == wordToGuess[i]) {
                result += "O"
            }
            else if (guess[i] in wordToGuess) {
                result += "+"
            }
            else {
                result += "X"
            }
        }
        return result
    }
}