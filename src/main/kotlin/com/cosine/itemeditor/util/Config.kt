package com.cosine.itemeditor.util

import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

class Config(plugin: JavaPlugin, fileName: String) {

    private var fileName: String
    private var plugin: JavaPlugin
    private var file: File
    private var config: YamlConfiguration? = null

    init {
        this.plugin = plugin
        this.fileName = fileName
        val dataFolder: File = plugin.dataFolder ?: throw IllegalStateException()
        this.file = File(dataFolder.toString() + File.separatorChar + this.fileName)
    }

    fun reloadConfig() {
        try {
            this.config = YamlConfiguration.loadConfiguration(InputStreamReader(FileInputStream(this.file), StandardCharsets.UTF_8))
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        val defConfigStream: InputStream? = this.plugin.getResource(this.fileName)
        if (defConfigStream != null) {
            val defConfig: YamlConfiguration = YamlConfiguration.loadConfiguration(InputStreamReader(defConfigStream))
            this.config?.defaults = defConfig
        }
    }
    fun getConfig(): YamlConfiguration? {
        if (config == null) {
            reloadConfig()
        }
        return this.config
    }
    fun saveConfig() {
        try {
            getConfig()?.save(this.file)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    fun saveDefaultConfig() {
        if (!this.file.exists()) {
            this.plugin.saveResource(this.fileName, false)
        }
    }
}