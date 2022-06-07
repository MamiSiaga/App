package com.mamisiaga.tools

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import androidx.exifinterface.media.ExifInterface
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import java.io.File

abstract class ImageUtil {
    companion object {
        private fun decodeExifOrientation(orientation: Int): Matrix {
            val matrix = Matrix()

            // Apply transformation corresponding to declared EXIF orientation
            when (orientation) {
                ExifInterface.ORIENTATION_NORMAL, ExifInterface.ORIENTATION_UNDEFINED -> Unit
                ExifInterface.ORIENTATION_ROTATE_90 -> matrix.postRotate(90F)
                ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(180F)
                ExifInterface.ORIENTATION_ROTATE_270 -> matrix.postRotate(270F)
                ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> matrix.postScale(-1F, 1F)
                ExifInterface.ORIENTATION_FLIP_VERTICAL -> matrix.postScale(1F, -1F)
                ExifInterface.ORIENTATION_TRANSPOSE -> {
                    matrix.postScale(-1F, 1F)
                    matrix.postRotate(270F)
                }
                ExifInterface.ORIENTATION_TRANSVERSE -> {
                    matrix.postScale(-1F, 1F)
                    matrix.postRotate(90F)
                }

                // Error out if the EXIF orientation is invalid
                else -> throw IllegalArgumentException("Invalid orientation: $orientation")
            }

            // Return the resulting matrix
            return matrix
        }

        fun setExifOrientation(filePath: String, value: String) {
            val exif = ExifInterface(filePath)
            exif.setAttribute(ExifInterface.TAG_ORIENTATION, value)
            exif.saveAttributes()
        }

        fun computeExifOrientation(rotationDegrees: Int, mirrored: Boolean) =
            when {
                rotationDegrees == 0 && !mirrored -> ExifInterface.ORIENTATION_NORMAL
                rotationDegrees == 0 && mirrored -> ExifInterface.ORIENTATION_FLIP_HORIZONTAL
                rotationDegrees == 180 && !mirrored -> ExifInterface.ORIENTATION_ROTATE_180
                rotationDegrees == 180 && mirrored -> ExifInterface.ORIENTATION_FLIP_VERTICAL
                rotationDegrees == 270 && mirrored -> ExifInterface.ORIENTATION_TRANSVERSE
                rotationDegrees == 90 && !mirrored -> ExifInterface.ORIENTATION_ROTATE_90
                rotationDegrees == 90 && mirrored -> ExifInterface.ORIENTATION_TRANSPOSE
                rotationDegrees == 270 && mirrored -> ExifInterface.ORIENTATION_ROTATE_270
                rotationDegrees == 270 && !mirrored -> ExifInterface.ORIENTATION_TRANSVERSE
                else -> ExifInterface.ORIENTATION_UNDEFINED
            }

        fun decodeBitmap(file: File): Bitmap {
            // First, decode EXIF data and retrieve transformation matrix
            val exif = ExifInterface(file.absolutePath)
            val transformation =
                decodeExifOrientation(
                    exif.getAttributeInt(
                        ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_ROTATE_90
                    )
                )

            // Read bitmap using factory methods, and transform it using EXIF data
            val options = BitmapFactory.Options()
            val bitmap = BitmapFactory.decodeFile(file.absolutePath, options)
            return Bitmap.createBitmap(
                BitmapFactory.decodeFile(file.absolutePath),
                0,
                0,
                bitmap.width,
                bitmap.height,
                transformation,
                true
            )
        }

        fun bitmapToTensorImageForRecognition(
            bitmapIn: Bitmap,
            width: Int,
            height: Int,
            mean: Float,
            std: Float
        ): TensorImage {
            val imageProcessor =
                ImageProcessor.Builder()
                    .add(ResizeOp(height, width, ResizeOp.ResizeMethod.BILINEAR))
                    .add(NormalizeOp(mean, std))
                    .build()
            var tensorImage = TensorImage(DataType.FLOAT32)

            tensorImage.load(bitmapIn)
            tensorImage = imageProcessor.process(tensorImage)

            return tensorImage
        }

        fun bitmapToTensorImageForDetection(
            bitmapIn: Bitmap,
            width: Int,
            height: Int,
            means: FloatArray,
            stds: FloatArray
        ): TensorImage {
            val imageProcessor =
                ImageProcessor.Builder()
                    .add(ResizeOp(height, width, ResizeOp.ResizeMethod.BILINEAR))
                    .add(NormalizeOp(means, stds))
                    .build()
            var tensorImage = TensorImage(DataType.FLOAT32)

            tensorImage.load(bitmapIn)
            tensorImage = imageProcessor.process(tensorImage)

            return tensorImage
        }

        fun createEmptyBitmap(
            imageWidth: Int,
            imageHeigth: Int,
            color: Int = 0,
            imageConfig: Bitmap.Config = Bitmap.Config.RGB_565
        ): Bitmap {
            val ret = Bitmap.createBitmap(imageWidth, imageHeigth, imageConfig)
            if (color != 0) {
                ret.eraseColor(color)
            }
            return ret
        }
    }
}