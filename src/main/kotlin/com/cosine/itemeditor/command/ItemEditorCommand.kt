package com.cosine.itemeditor.command

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

class ItemEditorCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            val player: Player = sender
            if (args.isEmpty()) {
                help(player)
                return false
            }
            if (player.inventory.itemInMainHand == null) {
                player.sendMessage("§f손에 아이템을 들고 명령어를 입력해주세요.")
                return false
            }
            when (args[0]) {
                "이름" -> {
                    if (args.size == 1) {
                        player.sendMessage("§f이름을 적어주세요.")
                        return false
                    }
                    val display = convertColor(args.copyOfRange(1, args.size).joinToString(" "))
                    setDisplay(player, display)
                }
                "로어추가" -> {
                    if (args.size == 1) {
                        player.sendMessage("§f추가할 로어를 적어주세요.")
                        return false
                    }
                    val lore = convertColor(args.copyOfRange(1, args.size).joinToString(" "))
                    addLore(player, lore)
                }
                "로어제거" -> {
                    if (args.size == 1) {
                        player.sendMessage("§f제거할 라인을 적어주세요.")
                        return false
                    }
                    if (!isInt(args[1])) {
                        player.sendMessage("§f숫자만 입력이 가능합니다.")
                        return false
                    }
                    val row = args[1].toInt()
                    removeLore(player, row)
                }
                "로어설정" -> {
                    if (args.size == 1) {
                        player.sendMessage("§f설정할 라인을 적어주세요.")
                        return false
                    }
                    if (!isInt(args[1])) {
                        player.sendMessage("§f숫자만 입력이 가능합니다.")
                        return false
                    }
                    if (args.size == 2) {
                        player.sendMessage("§f설정할 로어를 적어주세요.")
                        return false
                    }
                    val row = args[1].toInt()
                    val lore = convertColor(args.copyOfRange(2, args.size).joinToString(" "))
                    setLore(player, row, lore)
                }
                "내구도무한" -> {
                    setUnbreakable(player)
                }
            }
        }
        return false
    }
    private fun setDisplay(player: Player, display: String) {
        val item: ItemStack = player.inventory.itemInMainHand
        val meta: ItemMeta = item.itemMeta
        meta.displayName = display
        item.itemMeta = meta
    }
    private fun addLore(player: Player, lore: String) {
        val item: ItemStack = player.inventory.itemInMainHand
        val meta: ItemMeta = item.itemMeta
        val itemLore = meta.lore
        itemLore.add(lore)
        item.itemMeta = meta
    }
    private fun removeLore(player: Player, row: Int) {
        val item: ItemStack = player.inventory.itemInMainHand
        val meta: ItemMeta = item.itemMeta
        val itemLore = meta.lore
        itemLore.removeAt(row + 1)
        item.itemMeta = meta
    }
    private fun setLore(player: Player, row: Int, lore: String) {
        val item: ItemStack = player.inventory.itemInMainHand
        val meta: ItemMeta = item.itemMeta
        val itemLore = meta.lore
        itemLore[row + 1] = lore
        item.itemMeta = meta
    }
    private fun setUnbreakable(player: Player) {
        val item: ItemStack = player.inventory.itemInMainHand
        val meta: ItemMeta = item.itemMeta
        meta.isUnbreakable = true
        item.itemMeta = meta
    }
    private fun convertColor(value: String) : String {
        return value.replace("&", "§")
    }
    private fun isInt(str: String): Boolean {
        return try {
            str.toInt()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }
    private fun help(player: Player) {
        player.sendMessage("§f/아이템 이름 [이름]")
        player.sendMessage("§f/아이템 로어추가 [로어]")
        player.sendMessage("§f/아이템 로어제거 [라인]")
        player.sendMessage("§f/아이템 로어설정 [라인] [로어]")
        player.sendMessage("§f/아이템 내구도무한")
    }
}