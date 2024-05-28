package org.ardal.commands.individual;


import org.ardal.Ardal;
import org.ardal.api.commands.ArdalCmd;
import org.ardal.db.tables.TChunk;
import org.ardal.managers.ChunkManager;
import org.ardal.models.MChunk;
import org.ardal.utils.BukkitUtils;
import org.ardal.utils.TabCompleteUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class AddSpecialChunk implements ArdalCmd {
    @Override
    public boolean execute(CommandSender sender, Command command, String s, List<String> argv) {
        Player player = (Player) sender;
        long chunkId = ChunkManager.GetChunkId(player.getLocation().getChunk());

        TChunk tChunk = Ardal.getInstance().getDb().gettChunk();
        if(tChunk.getChunkById(chunkId) != null) {
            player.sendMessage("Chunk already saved");
            return true;
        }

        if(tChunk.createChunk(new MChunk(chunkId, 1))) {
            player.sendMessage("Success to save in database.");
        } else {
            player.sendMessage("Failed to save in database.");
        }

        return true;
    }

    @Override
    public List<String> getTabComplete(CommandSender player, Command command, String s, List<String> argv) {
        return TabCompleteUtils.getTabCompleteFromStrList(BukkitUtils.getOfflinePlayerNames(), argv);
    }

    @Override
    public String getHelp() {
        return String.format("%s%s:%s save chunk where the player are.",
                ChatColor.GOLD,
                getCmdName(),
                ChatColor.WHITE);
    }

    @Override
    public String getCmdName() {
        return "special_chunk";
    }
}
