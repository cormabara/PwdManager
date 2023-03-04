package com.cormabara.pwdmanager

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.cormabara.pwdmanager.databinding.ActivityMainBinding
import com.cormabara.pwdmanager.gui.dialogs.ChooseDialog
import com.cormabara.pwdmanager.managers.ManAppConfig
import com.cormabara.pwdmanager.managers.ManPwdData


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    lateinit var manAppConfig: ManAppConfig

    lateinit var manPwdData: ManPwdData
    var mainPassword: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyLog.LInfo("Program is started")
        appInit()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.mainTitle.text = "Pwd Manager"

        val toolbar = binding.activityToolbar
        setSupportActionBar(toolbar)
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
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
                         manPwdData.newData()
                         findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.action_StartFragment_to_firstStartFragment)

                     }
                 }
                 true
             }
             R.id.action_close_app -> {
                finish()
                 true
             }
             R.id.action_settings -> {
                 findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.action_MainFragment_to_settingFragment)
                 true
             }
             else -> {
                 super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun onStop() {
        super.onStop()
        appClose()
    }


    private fun appInit() {
        MyLog.LInfo("Application init")
        manAppConfig = ManAppConfig(filesDir)
        manPwdData = ManPwdData(filesDir)
        MyLog.LInfo("Application init is done")
    }

    private fun appClose()
    {
        MyLog.LInfo("Application closing")
        manPwdData.saveData(mainPassword)
        manAppConfig.saveData()
        MyLog.LInfo("Application closed corectly")
    }

    fun showUpButton() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    fun hideUpButton() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
    }
}
