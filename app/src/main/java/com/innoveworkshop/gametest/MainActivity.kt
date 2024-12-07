package com.innoveworkshop.gametest

import android.os.Bundle
import android.os.Handler
import android.view.ViewTreeObserver
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
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        gameSurface = GameSurface(this)
        setContentView(gameSurface)

        // Ensure layout is ready before spawning balls
        val observer = gameSurface.viewTreeObserver
        observer.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                gameSurface.viewTreeObserver.removeOnGlobalLayoutListener(this)
                initializeGame()
            }
        })
    }

    private fun initializeGame() {
        // Add multiple balls to the GameSurface
        for (i in 1..10) {
            spawnBall()
        }

        // Start game timer
        handler.postDelayed({
            endGame()
        }, gameDuration)
    }

    private fun spawnBall() {
        val randomX = Random.nextFloat() * gameSurface.width
        val randomY = Random.nextFloat() * gameSurface.height
        val ball = Ball(Vector(randomX, randomY), 50f)

        // Add a click listener to detect ball clicks
        ball.onClick = {
            gameSurface.removeGameObject(ball)
            score++
            handler.postDelayed({
                spawnBall() // Respawn the ball
            }, 500) // Reappear after 500ms
        }

        // Increase speed by setting higher random velocities
        val speedMultiplier = 15f // Adjust this for desired speed
        val vx = (Random.nextFloat() * 2 - 1) * speedMultiplier
        val vy = (Random.nextFloat() * 2 - 1) * speedMultiplier
        ball.setVelocity(vx, vy)

        gameSurface.addGameObject(ball)
    }


    private fun endGame() {
        gameSurface.stopGame()
        Toast.makeText(this, "Game Over! You destroyed $score balls!", Toast.LENGTH_LONG).show()
    }
}
