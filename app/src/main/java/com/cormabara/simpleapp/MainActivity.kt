package com.cormabara.simpleapp

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.cormabara.simpleapp.databinding.ActivityMainBinding
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.google.android.material.snackbar.Snackbar
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
        Log.i("MainActivity", "onCreate Called")
        MyLog.LInfo("Program is started")
        appInit()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        // get reference to button
        val btnExit = findViewById<ImageButton>(R.id.btn_exit)
        btnExit.setOnClickListener {
            Toast.makeText(this@MainActivity, "You clicked me.", Toast.LENGTH_SHORT).show()
            finish()
        }


        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        appClose()
    }



    private fun appInit() {
        val appFile = File(filesDir , cnfAppFn)
        Log.i("MainActivity", "Loading configuration files")
        appCnfFile = AppCnfFile(appFile)

        Log.i("MainActivity", "Application init is done")
    }

    fun loadPwdData()
    {
        val cnfPwdFile = File(filesDir,cnfPwdFn)
        // pwdCnfFile = PwdCnfFileInit(cnfPwdFile)

        val mapper = jacksonObjectMapper()
        val pwdFile = File(filesDir, cnfPwdFn)
        val pwdFileCrypt = File(filesDir, cnfPwdFnCrypt)
        if (pwdFile.exists() && pwdFileCrypt.exists()) {
            try {
                val str1 = pwdFile.readText()
                Log.i("PwdCnfFile.kt", str1)
                pwdCnfFile = mapper.readValue(str1)

                val str2 = PwdCrypt.FileDecrypt(mainPassword,pwdFileCrypt)
                Log.i("pwd data 1", str1)
                Log.i("pwd data 2", str2)
            } catch (e: Exception) {
                pwdFile.delete()
                pwdCnfFile = PwdCnfFile()
            }
        } else {
            pwdCnfFile = PwdCnfFile()
        }
    }

    fun savePwdData()
    {
        val pwdFile = File(filesDir, cnfPwdFn)
        val pwdFileCrypt = File(filesDir,cnfPwdFnCrypt)
        val mapper = jacksonObjectMapper()
        val myStr = mapper.writeValueAsString(pwdCnfFile)
        Log.i("PwdCnfFile.kt",myStr)
        pwdFile.writeText(myStr)
        PwdCrypt.FileEncrypt(mainPassword,myStr,pwdFileCrypt)
    }

    fun CheckPwdData() :Boolean
    {
        return File(filesDir,cnfPwdFnCrypt).exists()
    }

    private fun appClose()
    {
        savePwdData()
    }
}
