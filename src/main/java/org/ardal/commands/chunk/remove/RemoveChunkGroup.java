package org.ardal.commands.chunk.remove;

import org.ardal.Ardal;
import org.ardal.api.commands.ArdalCmd;
import org.ardal.managers.ChunkManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class RemoveChunkGroup implements ArdalCmd {
    @Override
    public boolean execute(CommandSender sender, Command command, String s, List<String> argv) {
        if(argv.isEmpty()) {
            return false;
        }

        Player player = (Player) sender;
        int groupId = Integer.parseInt(argv.get(0));
        ChunkManager chunkManager = Ardal.getInstance().getManager(ChunkManager.class);

        if(!chunkManager.chunkGroupExist(groupId)) {
            player.sendMessage("Chunk group not found.");
            return true;
        }

        if(chunkManager.deleteChunkGroup(groupId)) {
            player.sendMessage("Success to remove chunk in database.");
        } else {
            player.sendMessage("Failed to remove chunk in database.");
        }

        return true;
    }

    @Override
    public List<String> getTabComplete(CommandSender player, Command command, String s, List<String> argv) {
        return new ArrayList<>();
    }

    @Override
    public String getHelp() {
        return String.format("%s%s:%s remove chunk group.",
                ChatColor.GOLD,
                getCmdName(),
                ChatColor.WHITE);
    }

    @Override
    public String getCmdName() {
        return "group";
    }
}