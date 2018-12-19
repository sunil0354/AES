package com.wegile.aes

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val encrypt = CryptoHelper.encrypt("hello")
        textview.text = "Value hello"+"\nEncrypted Value " + encrypt + "\nDecrypted Value " + CryptoHelper.decrypt(encrypt)
    }
}
