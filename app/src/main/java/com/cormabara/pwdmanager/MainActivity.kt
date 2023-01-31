package com.cormabara.pwdmanager

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
            val chooseDiag = ChooseDialog(this)
            chooseDiag.show("Delete all data!","If YES all data will be deleted, are you sure?",{
                if (it == ChooseDialog.ResponseType.YES) {
                    Toast.makeText(this, "Clear all data", Toast.LENGTH_SHORT).show()
                    removePwdData()
                    navController.navigate(R.id.action_StartFragment_to_firstStartFragment)

                }
            })
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
        Log.i("PwdCnfFile.kt",myStr)
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
