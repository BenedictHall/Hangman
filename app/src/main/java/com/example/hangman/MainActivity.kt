package com.example.hangman

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.example.hangman.ui.theme.HangmanTheme
import java.io.File
import java.io.BufferedReader
import java.io.FileWriter
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HangmanTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Hangman(
                        modifier = Modifier.padding(innerPadding)
                    )
                    SelectWord()
                }
            }
        }
    }
}

@Composable
fun Hangman(modifier: Modifier = Modifier) {
    Column {
        Text(text = "Lives")
        //TextField(text = currentLives,
            //onValueChange = { newValue -> currentLives = newValue})
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HangmanTheme {
        Hangman()
    }
}

@Preview(showBackground = true)
@Composable
fun SelectWord() {
    val words = stringResource(R.string.words).split(" ").toTypedArray()
    val randomIndex = Random.nextInt(words.size);
    val word = words[randomIndex]
    Text(text=word)
}


//generates the word to be shown according to the letters guessed so far
@Preview
@Composable
fun GenerateUnderscores(word:String="canal",guessedLetters:String="al"){
    val builder = StringBuilder()
    for (char in word){
        if (char in guessedLetters) {
            builder.append(char)
        } else {
            builder.append("_")
        }
    }
    Text(text=builder.toString())
    //return builder.toString()
}

@Preview()
@Composable
fun HangmanLetter(letter:String="e") {
    //var currentLetter by remember {
    //    mutableStateOf("_")
    //}
    //TextField(value = currentLetter, onValueChange = {newLetter -> currentLetter = newLetter})
}