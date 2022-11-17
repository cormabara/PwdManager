package com.cormabara.simpleapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File

import com.cormabara.simpleapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private val cnfAppFn = "app_config.json"
    private lateinit var appCnfFile: AppCnfFile

    private val cnfPwdFn = "pwd_data.json"
    lateinit var pwdCnfFile: PwdCnfFile

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

        val cnfPwdFile = File(filesDir,cnfPwdFn)
        pwdCnfFile = PwdCnfFileInit(cnfPwdFile)

        val fileTest = File(filesDir,"pippo.txt")
        val fileTestCrypt = File(filesDir,"pippo_crypt.txt")
        val text = "pippo fa la pizza e la fa bene devo allungare la frase per verificare esception"

        fileTest.writeText(text)
        PwdCrypt.FileEncrypt("turbopino",text,fileTestCrypt)
        val str = fileTest.readText()
        val str_crypted = fileTestCrypt.readText()
        var str_decrypted = PwdCrypt.FileDecrypt("turbopino",fileTestCrypt)
        Log.i("MainActivity-str", str)
        Log.i("MainActivity-strcrypt", str)
        val str_cypt_2 = fileTest.readText()

        val mapper = jacksonObjectMapper()
        val pwdFile = File(filesDir, cnfPwdFn)
        if (pwdFile.exists()) {
            try {
                val string = pwdFile.readText()
                Log.i("PwdCnfFile", string)

                pwdCnfFile = mapper.readValue(string)
                Log.i("PwdCnfFile", pwdFile.readText())

            } catch (e: Exception) {
                pwdFile.delete()
                pwdCnfFile = PwdCnfFile()
                pwdCnfFile.AddItem("item1", "username1", "password1")
                pwdCnfFile.AddItem("item2", "username2", "password2")
            }
        } else {
            pwdCnfFile = PwdCnfFile()
            pwdCnfFile.AddItem("item1", "username1", "password1")
            pwdCnfFile.AddItem("item2", "username2", "password2")
        }
        Log.i("MainActivity", "Application init is done")
    }

    private fun appClose()
    {
        val pwdFile = File(filesDir, cnfPwdFn)
        val mapper = jacksonObjectMapper()
        val myStr = mapper.writeValueAsString(pwdCnfFile)
        Log.i("PwdCnfFile",myStr)
        pwdFile.writeText(myStr)
        // mapper.writeValue(file, pwdCnfData)
    }

}
