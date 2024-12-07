package com.innoveworkshop.gametest.engine

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.util.concurrent.CopyOnWriteArrayList

class GameSurface @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : SurfaceView(context, attrs, defStyleAttr), SurfaceHolder.Callback {

    private val gameObjects = CopyOnWriteArrayList<GameObject>()
    private var gameThread: GameThread? = null
    private var root: GameObject? = null

    init {
        holder.addCallback(this)
    }

    fun setRootGameObject(root: GameObject?) {
        this.root = root
        root?.onStart(this)
    }

    fun addGameObject(gameObject: GameObject) {
        gameObjects.add(gameObject)
        gameObject.onStart(this)
    }

    fun removeGameObject(gameObject: GameObject): Boolean {
        return gameObjects.remove(gameObject)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        gameThread = GameThread(holder, this).apply {
            running = true
            start()
        }
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        // Handle surface changes if needed
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        gameThread?.running = false
        gameThread?.join()
    }

    fun update() {
        root?.onFixedUpdate()
        for (gameObject in gameObjects) {
            gameObject.onFixedUpdate()
        }
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        root?.onDraw(canvas)
        for (gameObject in gameObjects) {
            gameObject.onDraw(canvas)
        }
    }

    private class GameThread(
        private val surfaceHolder: SurfaceHolder,
        private val gameSurface: GameSurface
    ) : Thread() {
        var running: Boolean = false

        override fun run() {
            while (running) {
                val canvas: Canvas? = surfaceHolder.lockCanvas()
                if (canvas != null) {
                    synchronized(surfaceHolder) {
                        gameSurface.update()
                        gameSurface.draw(canvas)
                    }
                    surfaceHolder.unlockCanvasAndPost(canvas)
                }
            }
        }
    }
}
