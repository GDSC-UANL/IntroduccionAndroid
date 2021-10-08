package com.fmaldonado.tallerandroid

import android.content.DialogInterface
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import coil.load
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    val charactersList = mutableListOf<CharacterModel>()

    lateinit var selectedCharacter: CharacterModel
    lateinit var imageView: ImageView
    lateinit var scoreTextView: TextView

    var score = 0

    val buttons = listOf(
        R.id.button1,
        R.id.button2,
        R.id.button3,
        R.id.button4
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageView = findViewById(R.id.imageView)
        scoreTextView = findViewById(R.id.counter)
        loadJson()
        getRandomCharacter()
    }

    private fun loadJson() {
        val charactersJson = resources.openRawResource(R.raw.characters).reader().readText()
        val characters = Gson().fromJson(charactersJson, Array<CharacterModel>::class.java)
        charactersList.addAll(characters)
    }

    private fun getRandomCharacter() {
        val selectedCharacters = charactersList.shuffled().take(4).toList()
        selectedCharacter = selectedCharacters.random()
        charactersList.remove(selectedCharacter)

        imageView.load(selectedCharacter.imageUrl)

        for (i in selectedCharacters.indices) {
            val button: Button = findViewById(buttons[i])
            val currentCharacter = selectedCharacters[i]
            button.text = currentCharacter.name
            button.setOnClickListener {
                checkSelectedCharacter(currentCharacter.id)
            }
        }

    }

    private fun checkSelectedCharacter(id: Int) {

        if (id == selectedCharacter.id) {
            score++
            scoreTextView.text = score.toString()
            getRandomCharacter()
            return
        }

        AlertDialog.Builder(this).apply {
            setTitle("Juego terminado")
            setMessage("Respuesta correcta: ${selectedCharacter.name}")
            setPositiveButton("Reiniciar"){ dialogInterface: DialogInterface, i: Int ->
                restart()
            }
            show()
        }

    }

    private fun restart(){
        score = 0
        scoreTextView.text = score.toString()
        loadJson()
        getRandomCharacter()
    }

}