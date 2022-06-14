package io.github.inggameteam.plugin.example

import org.bukkit.plugin.java.JavaPlugin

class Plugin : JavaPlugin() {

    override fun onEnable() {
        super.onEnable()
        logger.info("Hello World!")
    }

}