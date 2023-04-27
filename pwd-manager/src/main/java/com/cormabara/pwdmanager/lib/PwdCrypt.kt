package com.cormabara.pwdmanager.lib

import java.io.File
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class PwdCrypt {

    companion object {
        private fun PwdConvert(password_: String): IvParameterSpec {
            val iv = ByteArray(16)
            for (i in iv.indices)
                iv[i] = 0x20

            val charArray = password_.toCharArray()

            for (i in charArray.indices) {
                iv[i] = charArray[i].toByte()
            }
            val ivParameterSpec = IvParameterSpec(iv)
            return ivParameterSpec
        }

        fun FileEncrypt(password: String, source_: String, filedest_: File) {
            var pwd: String = password
            while (pwd.length < 16) {
                pwd += " "
            }

            val ivParameterSpec = PwdConvert(pwd)
            val secretKeySpec = SecretKeySpec(pwd.toByteArray(), "AES")

            val cipher = Cipher.getInstance("AES/GCM/NoPadding")
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec)
            val encryptedValue = cipher.doFinal(source_.toByteArray())

            filedest_.writeBytes(encryptedValue)
        }

        fun FileDecrypt(password: String, filesrc_: File): String {
            var pwd: String = password
            while (pwd.length < 16) {
                pwd += " "
            }
            val secretKeySpec = SecretKeySpec(pwd.toByteArray(), "AES")
            val ivParameterSpec = PwdConvert(pwd)
            val cipher = Cipher.getInstance("AES/GCM/NoPadding")

            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec)

            val src = filesrc_.readBytes()
            val decryptedByteValue = cipher.doFinal(src)
            return String(decryptedByteValue)
        }
    }
}