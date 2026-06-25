package com.jsrvc.ui.rndnative

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.jsrvc.ui.rndnative.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
	
	private lateinit var binding: ActivityMainBinding
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
	
		val ctx = this@MainActivity
		if (!Python.isStarted()) {
			Python.start(AndroidPlatform(ctx))
		}
		
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)
		
		// Example of a call to a native method
		binding.sampleText.text = stringFromJNI()
	 
		//Python module call
		val python = Python.getInstance()
		val pythonFile = python.getModule("test")
		//Log.d("PYTHON", pythonFile.toString())
		val pyResult = pythonFile.callAttr("show_toast").toString()
		
		Toast.makeText(ctx, pyResult,  Toast.LENGTH_LONG).show()
	}
	
	/**
	 * A native method that is implemented by the 'rndnative' native library,
	 * which is packaged with this application.
	 */
	external fun stringFromJNI(): String
	
	companion object {
		// Used to load the 'rndnative' library on application startup.
		init {
			System.loadLibrary("rndnative")
		}
	}
}