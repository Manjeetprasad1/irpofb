package com.root.irpofb.ui.activity


import android.Manifest
import android.app.DownloadManager
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.navigation.NavigationView
import com.root.irpofb.R
import com.root.irpofb.databinding.ActivityMainBinding
import com.root.irpofb.extension.DashBoardViewModel
import com.root.irpofb.extension.showToast
import com.root.irpofb.ui.fragment.HomeFragment


class DashboardActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding : ActivityMainBinding
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private val mDashBoardViewModel by lazy {
        ViewModelProvider(this)[DashBoardViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navView.setNavigationItemSelectedListener(this)

        actionBarDrawerToggle = ActionBarDrawerToggle(this,binding.myDrawerLayout,R.string.app_name,R.string.app_name)
        binding.myDrawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        supportActionBar?.setDisplayUseLogoEnabled(true)


        if (savedInstanceState==null){
            loadFragment(HomeFragment(),false)
            binding.navView.setCheckedItem(R.id.home_nav_menu)
        }


        binding.ivOpenDrawer.setOnClickListener {
            if (!binding.myDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.myDrawerLayout.openDrawer(GravityCompat.START)
            } else {
                binding.myDrawerLayout.closeDrawer(GravityCompat.START)
            }
        }

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // check conndition for drawer item with menu item
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            true
        }else{
            super.onOptionsItemSelected(item)
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.home_nav_menu ->  loadFragment(HomeFragment(),false)
            R.id.download_nav_menu-> downloadPdf()
        }
        binding.myDrawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun downloadPdf() {
        downloadPdfFile("https://www.clickdimensions.com/links/TestPDFfile.pdf")
    }

    private fun downloadPdfFile(url : String) {
        val request = DownloadManager.Request(Uri.parse(url))
        request.setTitle("PDF Download")
        request.setDescription("Downloading PDF file")
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "pdf_file.pdf")
        val manager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        manager.enqueue(request)
    }

    private fun checkPermission(): Boolean {
        return if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
            false
        } else {
            true
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
           // downloadPdf("https://www.example.com/sample.pdf", "sample.pdf")
            downloadPdfFile("https://www.clickdimensions.com/links/TestPDFfile.pdf")
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        if (binding.myDrawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.myDrawerLayout.closeDrawer(GravityCompat.START)
        }else{
            onBackPressedDispatcher.onBackPressed()
        }
    }
}