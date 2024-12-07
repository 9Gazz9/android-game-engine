/*
package com.innoveworkshop.gametest.engine


import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class Target(position: Vector, private val radius: Float) : GameObject(position) {

    private val paint: Paint = Paint().apply {
        color = Color.GREEN
        style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawCircle(position.x, position.y, radius, paint)
    }

    fun isHit(ball: Ball): Boolean {
        val dx = position.x - ball.position.x
        val dy = position.y - ball.position.y
        val distance = Math.sqrt((dx * dx + dy * dy).toDouble()).toFloat()
        return distance < radius + ball.radius
    }
}
*/
