package com.innoveworkshop.gametest

import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.innoveworkshop.gametest.engine.Ball
import com.innoveworkshop.gametest.engine.GameSurface
import com.innoveworkshop.gametest.engine.Vector
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var gameSurface: GameSurface
    private var score: Int = 0
    private val gameDuration: Long = 30000 // 30 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        gameSurface = GameSurface(this)
        setContentView(gameSurface)

        // Add multiple balls to the GameSurface
        for (i in 1..10) {
            val randomX = Random.nextFloat() * 800f // Replace 800f with gameSurface.width if needed
            val randomY = Random.nextFloat() * 800f
            val ball = Ball(Vector(randomX, randomY), 50f)

            // Add a click listener to detect ball clicks
            ball.onClick = {
                gameSurface.removeGameObject(ball)
                score++
            }

            gameSurface.addGameObject(ball)
        }

        // Start game timer
        Handler(mainLooper).postDelayed({
            endGame()
        }, gameDuration)
    }

    private fun endGame() {
        gameSurface.stopGame()
        Toast.makeText(this, "Game Over! You destroyed $score balls!", Toast.LENGTH_LONG).show()
    }
}
