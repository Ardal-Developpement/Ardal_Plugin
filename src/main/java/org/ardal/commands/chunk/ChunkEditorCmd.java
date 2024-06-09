package org.ardal.commands.chunk;

import org.ardal.Ardal;
import org.ardal.api.commands.ArdalCmd;
import org.ardal.inventories.chunk.ChunkEditorInventory;
import org.ardal.managers.ChunkManager;
import org.ardal.objects.chunk.ChunkGroupObj;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ChunkEditorCmd implements ArdalCmd {
    @Override
    public boolean execute(CommandSender sender, Command command, String s, List<String> argv) {
        if(argv.isEmpty()) {
            return false;
        }

        Player player = (Player) sender;
        int groupId = Integer.parseInt(argv.get(0));
        ChunkManager cM = Ardal.getInstance().getManager(ChunkManager.class);
        ChunkGroupObj chunkGroupObj = cM.getChunkGroupObj(groupId);

        if(chunkGroupObj == null) {
            player.sendMessage("Group id not found.");
            return true;
        }

        ChunkEditorInventory cEI = new ChunkEditorInventory(player, chunkGroupObj);
        cEI.showInventory();

        return true;
    }

    @Override
    public List<String> getTabComplete(CommandSender player, Command command, String s, List<String> argv) {
        return new ArrayList<>();
    }

    @Override
    public String getHelp() {
        return String.format("%s%s:%s chunk editor.",
                ChatColor.GOLD,
                getCmdName(),
                ChatColor.WHITE);
    }

    @Override
    public String getCmdName() {
        return "editor";
    }
}