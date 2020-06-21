package com.withplum.yourheroes.util

import android.graphics.Bitmap
import android.graphics.Bitmap.Config
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff.Mode
import android.graphics.PorterDuffXfermode
import android.graphics.Rect

//Reference to: https://stackoverflow.com/questions/11932805/how-to-crop-circular-area-from-bitmap-in-android

fun Bitmap.getCroppedBitmap(): Bitmap {
    val output = Bitmap.createBitmap(this.width, this.height, Config.ARGB_8888)
    val canvas = Canvas(output)
    val color = -0xbdbdbe
    val paint = Paint()
    val rect = Rect(0, 0, this.width, this.height)
    paint.isAntiAlias = true
    canvas.drawARGB(0, 0, 0, 0)
    paint.color = color
    canvas.drawCircle((this.width / 2).toFloat(),
            (this.height / 2).toFloat(),
            (this.width / 2).toFloat(),
            paint)
    paint.xfermode = PorterDuffXfermode(Mode.SRC_IN)
    canvas.drawBitmap(this, rect, rect, paint)
    if (output != this) {
        this.recycle()
    }
    return output
}

