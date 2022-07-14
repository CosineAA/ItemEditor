package com.cosine.itemeditor.command

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class NoticeCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            val player: Player = sender
            if (args.isEmpty()) {
                player.sendMessage("§f/확성기 [메시지]")
                return false
            }
            val message = convertColor(args.copyOfRange(0, args.size).joinToString(" "))
            sendMessage(player, message)
        }
        return false
    }
    private fun sendMessage(player: Player, message: String) {
        Bukkit.broadcastMessage("§6§l>>> [ §f§l${player.name} §f님의 확성기 §6§l] §7§l:: §b§l[ §f$message §b§l]")
    }
    private fun convertColor(value: String) : String {
        return value.replace("&", "§")
    }
}