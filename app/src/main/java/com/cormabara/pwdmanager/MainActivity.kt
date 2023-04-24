package com.cormabara.pwdmanager

import android.Manifest
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.cormabara.pwdmanager.databinding.ActivityMainBinding
import com.cormabara.pwdmanager.gui.dialogs.ChooseDialog
import com.cormabara.pwdmanager.gui.fragments.MainFragment
import com.cormabara.pwdmanager.lib.MyFileUtils
import com.cormabara.pwdmanager.lib.MyLog
import com.cormabara.pwdmanager.managers.ManAppConfig
import com.cormabara.pwdmanager.managers.ManPwdData
import java.io.File
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    lateinit var manAppConfig: ManAppConfig

    lateinit var manPwdData: ManPwdData
    var mainPassword: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        val requiredPermissions1 = arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE)
        ActivityCompat.requestPermissions(this, requiredPermissions1, 0);
        val requiredPermissions2 = arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE)
        ActivityCompat.requestPermissions(this, requiredPermissions2, 0);
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
                        findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.action_to_newPasswordFragment)

                    }
                }
                true
            }
            R.id.action_restore_backup -> {
                // Restore the backup data from the backup file
                manPwdData.restoreBackupData(this,mainPassword)
                val navHostFragment: NavHostFragment =
                    supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment

                (navHostFragment.childFragmentManager.fragments[0] as MainFragment?)?.reloadData()
                true
            }
            R.id.action_change_password -> {
                findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.action_to_newPasswordFragment)
                manPwdData.save(mainPassword,true)
                true
            }
            R.id.action_close_app -> {
                finish()
                true
            }
            R.id.action_settings -> {
                findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.action_to_settingFragment)
                true
            }
            R.id.action_export_data -> {
                manPwdData.exportData(this,manAppConfig.userMail)
                true
            }
            R.id.action_import_data -> {
                val intent = Intent()
                    .setType("*/*")
                    .setAction(Intent.ACTION_GET_CONTENT)
                    .addCategory(Intent.CATEGORY_OPENABLE)
                    .putExtra(Intent.EXTRA_LOCAL_ONLY, true)
                startActivityForResult(Intent.createChooser(intent, "Select a file"), 111)
                return true
            }
            R.id.action_save_all -> {
                return manPwdData.save(mainPassword,true)
            }
            R.id.action_about -> {
                findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.action_to_aboutFragment)
                return true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data_: Intent?) {
        super.onActivityResult(requestCode, resultCode, data_)
        if (requestCode == 111 && resultCode == RESULT_OK) {
            val fileUri: Uri? = data_?.data   // The URI with the location of the file
            val file = File(fileUri?.let { MyFileUtils.getPath(this, it) })
            manPwdData.importData(this, file, mainPassword)
            val navHostFragment: NavHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment

            (navHostFragment.childFragmentManager.fragments[0] as MainFragment?)?.reloadData()
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
        setAppLocale(manAppConfig.language.toString())
        MyLog.LInfo("Application init is done")
    }

    private fun appClose()
    {
        MyLog.LInfo("Application closing")
        manPwdData.save(mainPassword,true)
        manAppConfig.saveData()
        MyLog.LInfo("Application closed corectly")
    }

    fun showUpButton() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    fun hideUpButton() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
    }
    fun setAppLocale(localeCode: String) {
        val resources: Resources = resources
        val displayMetrics: DisplayMetrics = resources.displayMetrics
        val configuration: Configuration = resources.configuration
        configuration.setLocale(Locale(localeCode.lowercase(Locale.getDefault())))
        resources.updateConfiguration(configuration, displayMetrics)
        configuration.locale = Locale(localeCode.lowercase(Locale.getDefault()))
        resources.updateConfiguration(configuration, displayMetrics)
    }
}
