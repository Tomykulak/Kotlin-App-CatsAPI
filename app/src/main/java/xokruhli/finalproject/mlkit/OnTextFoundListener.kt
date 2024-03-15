package xokruhli.finalproject.mlkit

import com.google.mlkit.vision.text.Text

interface OnTextFoundListener {
    fun onTextFound(text: Text)
    fun onFailure(exception: Exception)
}