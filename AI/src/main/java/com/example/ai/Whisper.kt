package com.example.ai

import android.content.Context
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform

open class AI (script: String)  {
    val python = Python.getInstance()
    // Assuming your script is named script.py
    val module = python.getModule(script)

}
class Whisper: AI("whisper") {
    fun speechToText(audio: String, context: Context) {
        if (! Python.isStarted()) {
            Python.start(AndroidPlatform(context))
            val result = module.callAttr("speechToText", audio) // Call a function in your script", arg1, arg2) // Call a function in your script
            result.call()
        }
    }

}
