package com.example.hangman

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                    Hangman(word = SelectWord(),
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Hangman(word: String, modifier: Modifier = Modifier) {
    var lives by remember { mutableIntStateOf(3) }
    var letterColor = remember { mutableStateMapOf<Char, Color>() }

    Column(

        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 10.dp)
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.lives),
            fontSize = 40.sp
        )

        Text(text = word)
        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = lives.toString(),
            fontSize = 45.sp
        )
        AlphabetGrid(
            letterColor = letterColor,
            onLetterClicked = { letter ->
            val (color, indexes) = checkLetter(word, letter)
                letterColor[letter] = color
            if (indexes.isEmpty()) {
                lives -= 1
            }
        })
    }
}

@Composable
fun AlphabetButton(letter: Char, color: Color, onLetterClicked: (Char) -> Unit) {
    Button(
        onClick = {
            onLetterClicked(letter)
        },
        contentPadding = PaddingValues(horizontal = 1.dp, vertical = 1.dp),
        colors = ButtonDefaults.outlinedButtonColors(Color(color.value)),
        modifier = Modifier
            .height(40.dp)
            .width(40.dp)
            .padding(1.dp)
    ) {
        Text(text = letter.toString(), fontSize = 20.sp)
    }
}

@Composable
fun AlphabetGrid(letterColor: Map<Char, Color>, onLetterClicked: (Char) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
        val alphabet = ('A'..'Z').toList()

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            for (i in 0 until 8) {
                var letter = alphabet[i]
                AlphabetButton(letter = alphabet[i], color = letterColor[letter] ?: Color.White, onLetterClicked = onLetterClicked)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            for (i in 8 until 16) {
                var letter = alphabet[i]
                AlphabetButton(letter = alphabet[i], color = letterColor[letter] ?: Color.White, onLetterClicked = onLetterClicked)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            for (i in 16 until 24) {
                var letter = alphabet[i]
                AlphabetButton(letter = alphabet[i], color = letterColor[letter] ?: Color.White, onLetterClicked = onLetterClicked)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            for (i in 24 until 26) {
                var letter = alphabet[i]
                AlphabetButton(letter = alphabet[i], color = letterColor[letter] ?: Color.White, onLetterClicked = onLetterClicked)
            }
        }
    }
}

private fun checkLetter(word: String, letter: Char): Pair<Color, MutableList<Int>> {
    var indexes: MutableList<Int> = mutableListOf()
    var color = Color.Red
    for (i in word.indices) {
        if (word[i].lowercaseChar() == letter.lowercaseChar()) {
            indexes.add(i)
        }
    }
    if (indexes.size > 0) {
        color = Color.Green
    }
    return Pair(color, indexes)
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun GreetingPreview() {
    HangmanTheme {
        Hangman(SelectWord())
    }
}

@Composable
fun SelectWord(): String {
    val words = stringResource(R.string.words).split(" ").toTypedArray()
    val randomIndex = Random.nextInt(words.size)
    val word = words[randomIndex]

    return word
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