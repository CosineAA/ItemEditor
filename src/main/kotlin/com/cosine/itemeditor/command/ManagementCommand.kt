package com.cosine.itemeditor.command

import com.cosine.itemeditor.main.ItemEditor
import com.cosine.itemeditor.util.Config
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player

class ManagementCommand(plugin: ItemEditor) : CommandExecutor {

    private val config: Config
    private val commandConfig: YamlConfiguration

    init {
        config = plugin.config()
        commandConfig = config.getConfig()
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            val player: Player = sender
            if (args.isEmpty()) {
                player.sendMessage("§c%player% = 플레이어 이름 | 슬래쉬 빼고 입력")
                player.sendMessage("§f/관리 추가 [이름] [명령어] - 이름을 입력시 실행되는 명령어를 생성합니다.")
                player.sendMessage("§f/관리 제거 [이름]")
                player.sendMessage("§f/관리 명령어목록")
                player.sendMessage("§f/관리 [이름] - 명령어 실행")
                return false
            }
            if (args[0] == "추가") {
                if (args.size == 1) {
                    player.sendMessage("§f명령어를 실행할 이름을 적어주세요.")
                    return false
                }
                if (args[1] == "추가" || args[1] == "제거" || args[1] == "명령어목록") {
                    player.sendMessage("§f해당 이름으로 추가할 수 없습니다.")
                    return false
                }
                if (commandConfig.contains("명령어.${args[1]}")) {
                    player.sendMessage("§f이미 존재하는 명령어입니다.")
                    return false
                }
                if (args.size == 2) {
                    player.sendMessage("§f실행할 명령어를 적어주세요.")
                    return false
                }
                player.sendMessage("§f명령어를 추가하였습니다.")
                commandConfig.set("명령어.${args[1]}", convertColor(args.copyOfRange(2, args.size).joinToString(" ")))
                config.saveConfig()
                return false
            }
            if (args[0] == "제거") {
                if (!commandConfig.contains("명령어.${args[1]}")) {
                    player.sendMessage("§f존재하지 않는 명령어입니다.")
                    return false
                }
                commandConfig.set(args[1], null)
                config.saveConfig()
                return false
            }
            if (args[0] == "명령어목록") {
                for (cmd: String in commandConfig.getKeys(false)) {
                    player.sendMessage("§f$cmd : ${commandConfig.getString("명령어.$cmd")}")
                }
                return false
            }
            if (args.size == 1) {
                if (!commandConfig.contains("명령어.${args[0]}")) {
                    player.sendMessage("§f존재하지 않는 명령어입니다.")
                    return false
                }
                Bukkit.dispatchCommand(player, replaceName(player, commandConfig.getString("명령어.${args[0]}")))
            }
        }
        return false
    }
    private fun replaceName(player: Player, command: String) : String {
        return command.replace("%player%", player.name)
    }
    private fun convertColor(value: String) : String {
        return value.replace("&", "§")
    }
}