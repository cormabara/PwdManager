package com.cormabara.pwdmanager

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.cormabara.pwdmanager.data.PwdDataFile
import com.cormabara.pwdmanager.databinding.ActivityMainBinding
import java.io.File


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private val cnfAppFn = "app_config.json"
    private lateinit var appCnfFile: AppCnfFile

    lateinit var pwdDataFile: PwdDataFile
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
                         pwdDataFile.newData()
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
        MyLog.LInfo("Application init")
        val appFile = File(filesDir , cnfAppFn)
        appCnfFile = AppCnfFile(appFile)
        pwdDataFile = PwdDataFile(filesDir)
        MyLog.LInfo("Application init is done")
    }

    private fun appClose()
    {
        MyLog.LInfo("Application closing")
        pwdDataFile.saveData(mainPassword)
        MyLog.LInfo("Application closed corectly")
    }
}
