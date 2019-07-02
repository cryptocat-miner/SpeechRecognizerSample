package com.cryptocat.speechrecognizersample

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity() {
    private val TAG = "RecognitionListener"
    private val RECORD_REQUEST_CODE = 101

    private var mSpeechRecognizer : SpeechRecognizer? = null
    private var speechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

    private var listItems = mutableListOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault().getLanguage())
        speechRecognizerIntent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.packageName)
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, true)
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)
        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(applicationContext)
        mSpeechRecognizer?.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {
                Log.d(TAG, "onReadyForSpeech")
            }

            override fun onRmsChanged(rmsdB: Float) {
                Log.d(TAG, "onRmsChanged")
            }

            override fun onBufferReceived(buffer: ByteArray?) {
                Log.d(TAG, "onBufferReceived")
            }

            override fun onBeginningOfSpeech() {
                Log.d(TAG, "onBeginningOfSpeech")
            }

            override fun onEndOfSpeech() {
                Log.d(TAG, "onEndOfSpeech")
            }

            override fun onError(error: Int) {
                var errorCode = ""
                when (error) {
                    SpeechRecognizer.ERROR_AUDIO -> errorCode = "Audio recording error"
                    SpeechRecognizer.ERROR_CLIENT -> errorCode = "Other client side errors"
                    SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> errorCode = "Insufficient permissions"
                    SpeechRecognizer.ERROR_NETWORK -> errorCode = "Network related errors"
                    SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> errorCode = "Network operation timed out"
                    SpeechRecognizer.ERROR_NO_MATCH -> errorCode = "No recognition result matched"
                    SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> errorCode = "RecognitionService busy"
                    SpeechRecognizer.ERROR_SERVER -> errorCode = "Server sends error status"
                    SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> errorCode = "No speech input"
                }
                Log.d("RecognitionListener", "onError:" + errorCode)
                try {
                    GlobalScope.launch {
                        runOnUiThread {
                            mSpeechRecognizer?.cancel()
                        }
                        delay(1000)
                        runOnUiThread {
                            mSpeechRecognizer?.startListening(speechRecognizerIntent)
                        }
                    }
                } catch (ex: Exception) {

                }
            }

            override fun onEvent(eventType: Int, params: Bundle?) {
                Log.d(TAG, "onEvent")
            }

            override fun onPartialResults(partialResults: Bundle) {
                Log.d(TAG, "onPartialResults")
                val result = partialResults.getStringArrayList(android.speech.SpeechRecognizer.RESULTS_RECOGNITION)
                Log.i(TAG, "onPartialResults" + result.toString())
            }

            override fun onResults(results: Bundle) {
                Log.d(TAG, "onResults")

                val result = results.getStringArrayList(android.speech.SpeechRecognizer.RESULTS_RECOGNITION)

                Log.i(TAG, "onResults" + result.toString())
                listItems.add(result.toString())
                if (listItems.count() >= 10){
                    listItems.removeAt(0)
                }
                var text = ""
                listItems.forEach {
                    text += it + "\n"
                }
                try {
                    GlobalScope.launch {
                        runOnUiThread {
                            textViewSpeech.text = text
                        }
                        runOnUiThread {
                            mSpeechRecognizer?.startListening(speechRecognizerIntent)
                        }
                    }
                } catch (ex: Exception) {

                }
            }
        })

        val permission = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), RECORD_REQUEST_CODE)
        }
        else {
            Log.i(TAG, "Permission is granted")
            mSpeechRecognizer?.startListening(speechRecognizerIntent)
            Log.i(TAG, "Start listening")
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == RECORD_REQUEST_CODE) {
            if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED){
                Log.i(TAG, "Permission has been denied by user")
            }
            else{
                Log.i(TAG, "Permission has been granted by user")
                mSpeechRecognizer?.startListening(speechRecognizerIntent)
                Log.i(TAG, "Start listening")
            }
        }
    }
}
