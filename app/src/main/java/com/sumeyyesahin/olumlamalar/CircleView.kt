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
    private val paint = Paint().apply {
        color = ContextCompat.getColor(context, R.color.purple)
        style = Paint.Style.STROKE
        strokeWidth = 20f
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
    }

    fun setProgress(progress: Float) {
        this.progress = progress
        invalidate()
    }

    fun setTimerText(text: String) {
        tvTimer.text = text
    }
}
