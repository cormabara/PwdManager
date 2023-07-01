package com.cormabara.pwdmanager

import android.Manifest
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.cormabara.pwdmanager.databinding.ActivityMainBinding
import com.cormabara.pwdmanager.gui.dialogs.ChooseDialog
import com.cormabara.pwdmanager.gui.dialogs.backupDialog
import com.cormabara.pwdmanager.gui.fragments.MainFragment
import com.cormabara.pwdmanager.lib.MyLog
import com.cormabara.pwdmanager.managers.ManAppConfig
import com.cormabara.pwdmanager.managers.ManPwdData
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.regex.Pattern


class MainActivity : AppCompatActivity()
{
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    lateinit var manAppConfig: ManAppConfig

    lateinit var manPwdData: ManPwdData
    var mainPassword: String = ""
    lateinit var backupPath: String

    val IMPORT_CODE = 111
    val EXPORT_CODE = 222

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        val requiredPermissions1 = arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE)
        ActivityCompat.requestPermissions(this, requiredPermissions1, 0);
        val requiredPermissions2 = arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE)
        ActivityCompat.requestPermissions(this, requiredPermissions2, 0);
        super.onCreate(savedInstanceState)
        backupPath = this.filesDir.absoluteFile.toString() + "/backup"
        MyLog.logOpen(this)
        MyLog.logInfo("Program is started")
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

    @RequiresApi(Build.VERSION_CODES.O)
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
            R.id.action_create_backup -> {
                backupDialog(this,true)
                true
            }
            R.id.action_restore_backup -> {
                backupDialog(this,false)
                true
            }
            R.id.action_change_password -> {
                findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.action_to_changePasswordFragment)
                manPwdData.save(mainPassword)
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
            R.id.action_import_data -> {
                val intent = Intent()
                    .setType("*/*")
                    .setAction(Intent.ACTION_GET_CONTENT)
                    .addCategory(Intent.CATEGORY_OPENABLE)
                    .putExtra(Intent.EXTRA_LOCAL_ONLY, true)
                startActivityForResult(Intent.createChooser(intent, "Select a file"),
                    IMPORT_CODE)
                return true
            }
            R.id.action_export_data -> {
                val intent = Intent()
                try {
                    intent.action = Intent.ACTION_CREATE_DOCUMENT
                    intent.addCategory(Intent.CATEGORY_OPENABLE)
                    intent.type = "*/*" //not needed, but maybe usefull
                    intent.putExtra(Intent.EXTRA_TITLE,"PwdManager-backup")
                    MyLog.logInfo("export intent ready, start activity")
                    startActivityForResult(Intent.createChooser(intent, "Select a file"),
                        EXPORT_CODE)
                    MyLog.logInfo("export activity done")
                }
                catch (e: Exception) {
                    MyLog.logError("Error exporting files");
                    Toast.makeText(this, "Error exporting file", Toast.LENGTH_SHORT).show()
                }
                true
            }
            R.id.action_save_all -> {
                return manPwdData.save(mainPassword)
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
        if (resultCode == RESULT_OK) {
            val fileUri: Uri? = data_?.data   // The URI with the location of the file
            MyLog.logInfo("File Uri: $fileUri")
            if (requestCode == IMPORT_CODE ) {
                // Data import
                if (fileUri != null) {
                    manPwdData.importDataFromUri(this,fileUri,mainPassword)
                }
                val navHostFragment: NavHostFragment =
                    supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main)
                    as NavHostFragment
                (navHostFragment.childFragmentManager.fragments[0] as MainFragment?)?.reloadData()
            }
            else if (requestCode == EXPORT_CODE) {
                // Data export
                if (fileUri != null) {
                    manPwdData.exportDataToUri(this, fileUri,mainPassword)
                    Toast.makeText(this, "Data exported to: $fileUri", Toast.LENGTH_SHORT).show()
                }
                val navHostFragment: NavHostFragment =
                    supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment

                (navHostFragment.childFragmentManager.fragments[0] as MainFragment?)?.reloadData()
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
        MyLog.logInfo("Application init")
        manAppConfig = ManAppConfig(filesDir)
        manPwdData = ManPwdData(filesDir)
        setAppLocale(manAppConfig.language.toString())
        MyLog.logInfo("Application init is done")
    }

    private fun appClose()
    {
        MyLog.logInfo("Application closing")
        manPwdData.save(mainPassword)
        manAppConfig.saveData()
        MyLog.logInfo("Application closed corectly")
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

    fun getDateTime(format_: String): String {
        var datetime: String = if (android.os.Build.VERSION.SDK_INT > 25) {
            // Do something for lollipop and above versions
            val formatter = DateTimeFormatter.ofPattern(format_)
            LocalDateTime.now().format(formatter)
        } else {
            val sdf = SimpleDateFormat(format_)
            sdf.format(Date())
        }
        return datetime
    }
}
