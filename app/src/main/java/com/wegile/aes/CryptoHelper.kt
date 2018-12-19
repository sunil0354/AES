package com.wegile.aes

import android.org.apache.commons.codec.binary.Base64

import java.security.NoSuchAlgorithmException

import javax.crypto.Cipher
import javax.crypto.NoSuchPaddingException
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class CryptoHelper private constructor() {
    private val ivspec: IvParameterSpec
    private val keyspec: SecretKeySpec
    private var cipher: Cipher? = null

    init {
        ivspec = IvParameterSpec(SecretKey.toByteArray())
        keyspec = SecretKeySpec(SecretKey.toByteArray(), "AES")
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        } catch (e: NoSuchAlgorithmException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        } catch (e: NoSuchPaddingException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

    }

    @Throws(Exception::class)
    private fun encryptInternal(text: String?): ByteArray? {
        if (text == null || text.length == 0) {
            throw Exception("Empty string")
        }
        var encrypted: ByteArray? = null
        try {
            cipher!!.init(Cipher.ENCRYPT_MODE, keyspec, ivspec)
            encrypted = cipher!!.doFinal(text.toByteArray())
        } catch (e: Exception) {
            throw Exception("[encrypt] " + e.message)
        }

        return encrypted
    }

    @Throws(Exception::class)
    private fun decryptInternal(code: String?): ByteArray? {
        if (code == null || code.length == 0) {
            throw Exception("Empty string")
        }
        var decrypted: ByteArray? = null
        try {
            cipher!!.init(Cipher.DECRYPT_MODE, keyspec, ivspec)
            //decrypted = cipher.doFinal(Base64.decode(code,Base64.DEFAULT));
            decrypted = cipher!!.doFinal(Base64.decodeBase64(code))
        } catch (e: Exception) {
            throw Exception("[decrypt] " + e.message)
        }

        return decrypted
    }

    companion object {
        private const val SecretKey = "ABCDEFGHIJKLMNOP"//16 char secret key

        @Throws(Exception::class)
        fun encrypt(valueToEncrypt: String): String {
            val enc = CryptoHelper()
            return Base64.encodeBase64String(enc.encryptInternal(valueToEncrypt))
            //return Base64.encodeToString(enc.encryptInternal(valueToEncrypt), Base64.DEFAULT);
        }

        @Throws(Exception::class)
        fun decrypt(valueToDecrypt: String): String {
            val enc = CryptoHelper()
            return String(enc.decryptInternal(valueToDecrypt)!!)
        }
    }
}