package com.innoveworkshop.gametest.engine

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent

class Ball(position: Vector, private val radius: Float) : GameObject(position) {

    private val velocity: Vector = Vector(5f, 7f)
    private val paint: Paint = Paint().apply {
        color = Color.RED
        style = Paint.Style.FILL
    }

    var onClick: (() -> Unit)? = null

    override fun onFixedUpdate() {
        super.onFixedUpdate()

        // Update position with velocity
        position.x += velocity.x
        position.y += velocity.y

        // Handle collision with screen edges
        gameSurface?.let {
            val width = it.width.toFloat()
            val height = it.height.toFloat()

            if (position.x - radius < 0 || position.x + radius > width) {
                velocity.x = -velocity.x
            }
            if (position.y - radius < 0 || position.y + radius > height) {
                velocity.y = -velocity.y
            }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawCircle(position.x, position.y, radius, paint)
    }

    fun handleTouch(event: MotionEvent): Boolean {
        val dx = event.x - position.x
        val dy = event.y - position.y
        val distanceSquared = dx * dx + dy * dy

        if (distanceSquared <= radius * radius) {
            onClick?.invoke()
            return true
        }
        return false
    }

    fun setVelocity(vx: Float, vy: Float) {
        velocity.x = vx
        velocity.y = vy
    }
}