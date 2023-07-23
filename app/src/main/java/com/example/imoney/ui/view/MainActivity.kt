package com.example.imoney.ui.view

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.imoney.R
import com.example.imoney.databinding.ActivityMainBinding
import com.example.imoney.ui.view.fragments.DialogRegistroGasto
import com.example.imoney.ui.view.fragments.DialogRegistroIngreso
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu.MenuStateChangeListener
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton


class MainActivity : AppCompatActivity() {
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //
        val bottomNavigation = binding.bottomNavigation
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNavigation.setupWithNavController(navController)

        binding.bottomNavigation.background = null
        binding.bottomNavigation.menu.getItem(1).isEnabled = false

        //
        var icon = ImageView(this)
        icon.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_suma))
        var actionButton= FloatingActionButton.Builder(this)
            .setContentView(icon)
            .setPosition(5)

            .setBackgroundDrawable(resources.getDrawable(R.drawable.background_fb))
            .build()

        var itemBuilder = SubActionButton.Builder(this)
        itemBuilder.setTheme(2)


        //1 DOWN
        var itemIcon = ImageView(this)
        itemIcon.setImageDrawable(ContextCompat.getDrawable(applicationContext,
            R.drawable.icon_flecha_down
        ))
        itemIcon.setColorFilter(ContextCompat.getColor(this, R.color.red_normal), android.graphics.PorterDuff.Mode.SRC_IN)

        var button1 = itemBuilder.setContentView(itemIcon).build()
        button1.layoutParams = ViewGroup.LayoutParams(160,160)



        //2 UP
         itemIcon = ImageView(this)
        itemIcon.setImageDrawable(ContextCompat.getDrawable(applicationContext,
            R.drawable.icon_flecha_up
        ))
        itemIcon.setColorFilter(ContextCompat.getColor(this, R.color.green_normal), android.graphics.PorterDuff.Mode.SRC_IN)

        var button2 = itemBuilder.setContentView(itemIcon).build()
        button2.layoutParams = ViewGroup.LayoutParams(160,160)






        var actionMenu = FloatingActionMenu.Builder(this)
            .addSubActionView(button1)
            .addSubActionView(button2)
            .setStartAngle(-30)
            .setRadius(300)
            .attachTo(actionButton)
            .build()

        button1.setOnClickListener {
            val dialog = DialogRegistroGasto()
            val ft = supportFragmentManager.beginTransaction()
            dialog.show(ft, DialogRegistroGasto().TAG)
            actionMenu.close(true)
        }
        button2.setOnClickListener{

            val dialog = DialogRegistroIngreso()
            val ft = supportFragmentManager.beginTransaction()
            dialog.show(ft, DialogRegistroIngreso().TAG)
            actionMenu.close(true)
            //supportFragmentManager.beginTransaction().add(R.id.nav_host_fragment,DialogRegistroIngreso()).commit()
        }


        actionMenu.setStateChangeListener(object : MenuStateChangeListener {
            override fun onMenuOpened(menu: FloatingActionMenu) {
                binding.shadowView.visibility = View.VISIBLE

            }

            override fun onMenuClosed(menu: FloatingActionMenu) {
                binding.shadowView.visibility = View.GONE
            }
        })
        binding.shadowView.setOnClickListener {
            binding.shadowView.visibility = View.GONE
            actionMenu.close(true)
        }
    }
}