package org.ardal.commands.chunk.add;


import org.ardal.Ardal;
import org.ardal.api.commands.ArdalCmd;
import org.ardal.managers.ChunkManager;
import org.ardal.models.MChunk;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AddChunkCmd implements ArdalCmd {
    @Override
    public boolean execute(CommandSender sender, Command command, String s, List<String> argv) {
        if(argv.isEmpty()) {
            return false;
        }

        Player player = (Player) sender;
        ChunkManager chunkManager = Ardal.getInstance().getManager(ChunkManager.class);
        long chunkId = ChunkManager.GetChunkId(player.getLocation().getChunk());
        int groupId = Integer.parseInt(argv.get(0));

        if(!chunkManager.chunkGroupExist(groupId)) {
            player.sendMessage("Chunk group does not exist.");
            return true;
        }

        if(chunkManager.chunkIsSaved(chunkId)) {
            player.sendMessage("Chunk already saved.");
            return true;
        }

        if(chunkManager.addChunkInGroup(new MChunk(chunkId, groupId))) {
            player.sendMessage("Success to save in database.");
        } else {
            player.sendMessage("Failed to save in database.");
        }

        return true;
    }

    @Override
    public List<String> getTabComplete(CommandSender player, Command command, String s, List<String> argv) {
        return new ArrayList<>();
    }

    @Override
    public String getHelp() {
        return String.format("%s%s:%s save chunk where the player is.",
                ChatColor.GOLD,
                getCmdName(),
                ChatColor.WHITE);
    }

    @Override
    public String getCmdName() {
        return "chunk";
    }
}
