package com.innoveworkshop.gametest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.innoveworkshop.gametest.engine.Ball
import com.innoveworkshop.gametest.engine.GameSurface
import com.innoveworkshop.gametest.engine.Vector

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gameSurface = GameSurface(this)
        setContentView(gameSurface)

        // Add a Ball to the GameSurface
        val ball = Ball(Vector(200f, 200f), 50f)
        gameSurface.addGameObject(ball)
    }
}