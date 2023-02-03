package com.cormabara.pwdmanager

import android.content.ClipData
import android.content.ClipData.Item
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.cormabara.pwdmanager.data.PwdCnfFile
import com.cormabara.pwdmanager.databinding.ActivityMainBinding
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private val cnfAppFn = "app_config.json"
    private lateinit var appCnfFile: AppCnfFile

    private val cnfPwdFn = "pwd_data.json"
    private val cnfPwdFnCrypt = "pwd_data_crypt.json"
    lateinit var pwdCnfFile: PwdCnfFile

    var mainPassword: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyLog.LInfo("Program is started")
        appInit()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.activityToolbar)
        binding.mainTitle.text = "Pwd Manager"
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
             R.id.action_delete_data -> {
                 val chooseDiag = ChooseDialog(this)
                 chooseDiag.show("Delete all data!", "If YES all data will be deleted, are you sure?") {
                     if (it == ChooseDialog.ResponseType.YES) {
                         Toast.makeText(this, "Clear all data", Toast.LENGTH_SHORT).show()
                         removePwdData()
                         findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.action_StartFragment_to_firstStartFragment)

                     }
                 }
                 true
             }
             R.id.action_close_app -> {
                finish()
                 true
             }
             else -> {
                 super.onOptionsItemSelected(item)
            }
        }
    }


    override fun onStop() {
        super.onStop()
        appClose()
    }


    private fun appInit() {
        val appFile = File(filesDir , cnfAppFn)
        Log.i("MainActivity", "Loading configuration files")
        appCnfFile = AppCnfFile(appFile)

        Log.i("MainActivity", "Application init is done")
    }

    fun createPwdData()
    {
        val pwdFileCrypt = File(filesDir, cnfPwdFnCrypt)
        if (pwdFileCrypt.exists() ) {
            pwdFileCrypt.delete()
        }
        pwdCnfFile = PwdCnfFile()
    }

    fun loadPwdData() : Boolean
    {
        val mapper = jacksonObjectMapper()
        val pwdFileCrypt = File(filesDir, cnfPwdFnCrypt)
        if (pwdFileCrypt.exists() ) {
            try {
                val str2 = PwdCrypt.FileDecrypt(mainPassword,pwdFileCrypt)
                Log.i("LogPwdDataLoad",str2)
                pwdCnfFile = mapper.readValue(str2)
            } catch (e: Exception) {
                return false
            }
        }
        else {
            return false
        }
        return true
    }

    fun savePwdData()
    {
        val pwdFileCrypt = File(filesDir,cnfPwdFnCrypt)
        val mapper = jacksonObjectMapper()
        val myStr = mapper.writeValueAsString(pwdCnfFile)
        Log.i("LogPwdDataSave",myStr)
        PwdCrypt.FileEncrypt(mainPassword,myStr,pwdFileCrypt)
    }

    fun CheckPwdData() :Boolean
    {
        return File(filesDir,cnfPwdFnCrypt).exists()
    }

    fun removePwdData() : Boolean
    {
        File(filesDir,cnfPwdFnCrypt).delete()
        File(filesDir,cnfPwdFn).delete()
        return true
    }

    private fun appClose()
    {
        savePwdData()
    }
}
