package com.example.diceroller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import java.util.*

/**
 * DiceRoller demonstrates simple interactivity in an Android app.
 * It contains one button that updates a text view with a random
 * value between 1 and 6.
 */
class MainActivity : AppCompatActivity() {

    lateinit var diceImage_1: ImageView
    lateinit var diceImage_2: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        diceImage_1 = findViewById(R.id.dice_image_1)
        diceImage_2 = findViewById(R.id.dice_image_2)
        // Get the Button view from the layout and assign a click
        // listener to it.
        val rollButton: Button = findViewById(R.id.roll_button)
        rollButton.setOnClickListener { rollDice() }

        val resetButton: Button = findViewById(R.id.reset_button)
        resetButton.setOnClickListener { reset() }
    }

    /**
     * Click listener for the Roll button.
     */
    private fun rollDice() {
        // Toast.makeText(this, "button clicked",
        //  Toast.LENGTH_SHORT).show()
        val imageSource_1 = getRandomRoll()
        val imageSource_2 = getRandomRoll()
        diceImage_1.setImageResource(imageSource_1)
        diceImage_2.setImageResource(imageSource_2)
    }

    private fun getRandomRoll(): Int {
        val randomInt = (1..6).random()

        val imageSource = when (randomInt) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
        return imageSource
    }

    private fun reset() {
        diceImage_1.setImageResource(R.drawable.empty_dice)
        diceImage_2.setImageResource(R.drawable.empty_dice)
    }

}
