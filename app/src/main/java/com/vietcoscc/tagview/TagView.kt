package com.vietcoscc.tagview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

/**
 * Created by VietNQ on 26/02/2020.
 * @author VietNQ
 */
class TagView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val viewRatio = 3f
    private val outLinePath = Path()
    private val backgroundPath = Path()
    private val backgroundPaint = Paint().apply {
        color = Color.BLUE
        strokeWidth = 10f
        style = Paint.Style.FILL
        flags = Paint.ANTI_ALIAS_FLAG

    }

    private val outLinePaint = Paint().apply {
        color = Color.RED
        strokeWidth = 3f
        style = Paint.Style.STROKE
        flags = Paint.ANTI_ALIAS_FLAG
    }

    private val circlePaint = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.FILL_AND_STROKE
        flags = Paint.ANTI_ALIAS_FLAG
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(
            MeasureSpec.getSize(widthMeasureSpec),
            (MeasureSpec.getSize(widthMeasureSpec) / viewRatio).toInt()
        )
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val viewHeight = width / viewRatio
        val viewWidth = width.toFloat()
        initPath(backgroundPath, viewWidth, viewHeight, outLinePaint.strokeWidth.toInt())
        initPath(outLinePath, viewWidth, viewHeight, outLinePaint.strokeWidth.toInt())
    }

    private fun initPath(path: Path, width: Float, height: Float, padding: Int) {
        val viewWidth = width - padding
        val viewHeight = height - padding
        val leftCornerRadius = (viewHeight) / 10
        val rightCornerRadius = if (padding * 20 < viewHeight) {
            (viewHeight - padding * 20) / 10
        } else {
            0f
        }
        path.moveTo(padding + leftCornerRadius, padding + 0f)
        path.lineTo(viewWidth - viewHeight, padding + 0f)
        path.lineTo(
            -padding + viewWidth - rightCornerRadius / 2,
            (padding + viewHeight) / 2 - rightCornerRadius / 2
        )
        path.rQuadTo(rightCornerRadius / 2, rightCornerRadius / 2, 0f, rightCornerRadius)
        path.lineTo(viewWidth - viewHeight, viewHeight)
        path.lineTo(padding + 0f + leftCornerRadius, viewHeight)
        path.rQuadTo(-leftCornerRadius, 0f, -leftCornerRadius, -leftCornerRadius)
        path.lineTo(padding + 0f, padding + leftCornerRadius)
        path.rQuadTo(0f, -leftCornerRadius, leftCornerRadius, -leftCornerRadius)
    }

    override fun onDraw(canvas: Canvas) {
        val viewHeight = width / viewRatio
        val viewWidth = width.toFloat()
        canvas.drawPath(outLinePath, outLinePaint)
        canvas.drawPath(backgroundPath, backgroundPaint)
        canvas.drawCircle(viewWidth - viewHeight, viewHeight / 2, viewHeight / 15, circlePaint)
    }

    fun setTagBackgroundColor(color: Int) {
        backgroundPaint.color = color
        invalidate()
    }

    fun setTagOutLineColor(color: Int) {
        outLinePaint.color = color
        invalidate()
    }

    fun setCircleColor(color: Int) {
        circlePaint.color = color
        invalidate()
    }

    fun setStrokeWidth(width: Int) {
        outLinePaint.strokeWidth = width.toFloat()
        requestLayout()
        invalidate()
    }
}