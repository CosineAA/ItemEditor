package com.cosine.itemeditor.main

import com.cosine.itemeditor.command.ItemEditorCommand
import com.cosine.itemeditor.command.ManagementCommand
import com.cosine.itemeditor.command.NoticeCommand
import com.cosine.itemeditor.util.Config
import org.bukkit.plugin.java.JavaPlugin

class ItemEditor : JavaPlugin() {

    private lateinit var config: Config

    override fun onEnable() {
        logger.info("아이템 수정 플러그인 활성화")

        config = Config(this, "command.yml")

        getCommand("아이템").executor = ItemEditorCommand()
        getCommand("확성기").executor = NoticeCommand()
        getCommand("관리").executor = ManagementCommand(this)
    }

    override fun onDisable() {
        logger.info("아이템 수정 플러그인 비활성화")
    }
    fun config(): Config {
        return this.config
    }
}