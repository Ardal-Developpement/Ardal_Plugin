package org.ardal.commands.chunk.remove;

import org.ardal.Ardal;
import org.ardal.api.commands.ArdalCmd;
import org.ardal.managers.ChunkManager;
import org.ardal.models.MChunk;
import org.ardal.utils.BukkitUtils;
import org.ardal.utils.TabCompleteUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class RemoveChunkInGroupCmd implements ArdalCmd {
    @Override
    public boolean execute(CommandSender sender, Command command, String s, List<String> argv) {
        if(argv.isEmpty()) {
            return false;
        }

        Player player = (Player) sender;
        long chunkId = ChunkManager.GetChunkId(player.getLocation().getChunk());
        int groupId = Integer.parseInt(argv.get(0));
        ChunkManager chunkManager = Ardal.getInstance().getManager(ChunkManager.class);


        if(!chunkManager.chunkIsSaved(chunkId, groupId)) {
            player.sendMessage("Chunk in group not found.");
            return true;
        }

        MChunk mChunk = new MChunk(chunkId, groupId);

        if(chunkManager.removeChunkFromGroup(mChunk)) {
            player.sendMessage("Success to remove chunk in database.");
        } else {
            player.sendMessage("Failed to remove chunk in database.");
        }

        return true;
    }

    @Override
    public List<String> getTabComplete(CommandSender player, Command command, String s, List<String> argv) {
        return TabCompleteUtils.getTabCompleteFromStrList(BukkitUtils.getOfflinePlayerNames(), argv);
    }

    @Override
    public String getHelp() {
        return String.format("%s%s:%s remove chunk in group where the player are.",
                ChatColor.GOLD,
                getCmdName(),
                ChatColor.WHITE);
    }

    @Override
    public String getCmdName() {
        return "chunk";
    }
}