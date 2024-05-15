package com.sumeyyesahin.olumlamalar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat

class CircleView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {
    private var progress: Float = 0f
    private var mode: Int = 0 // 0: Breathe In, 1: Hold, 2: Breathe Out

    private val paint = Paint().apply {
        color = ContextCompat.getColor(context, R.color.yellow)
        style = Paint.Style.STROKE
        strokeWidth = 20f
        isAntiAlias = true
    }

    private val fillPaint = Paint().apply {
        color = ContextCompat.getColor(context, R.color.purple_white)
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private lateinit var tvTimer: TextView

    init {
        View.inflate(context, R.layout.view_circle, this)
        tvTimer = findViewById(R.id.tvTimer)
    }

    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
        canvas?.let {
            val radius = (width.coerceAtMost(height) / 2 * 0.8).toFloat()
            val cx = width / 2f
            val cy = height / 2f

            when (mode) {
                0 -> { // Breathe In
                    val currentRadius = radius * progress
                    it.drawCircle(cx, cy, currentRadius, fillPaint)
                }
                1 -> { // Hold
                    it.drawCircle(cx, cy, radius, fillPaint)
                    it.drawArc(
                        cx - radius,
                        cy - radius,
                        cx + radius,
                        cy + radius,
                        -90f,
                        360 * progress,
                        false,
                        paint
                    )
                }
                2 -> { // Breathe Out
                    val currentRadius = radius * (1 - progress)
                    it.drawCircle(cx, cy, currentRadius, fillPaint)
                }
            }
        }
    }

    fun setProgress(progress: Float, mode: Int) {
        this.progress = progress
        this.mode = mode
        invalidate()
    }

    fun setTimerText(text: String) {
        tvTimer.text = text
    }
}
