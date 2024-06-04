package org.ardal.commands.chunk.add;


import org.ardal.Ardal;
import org.ardal.api.commands.ArdalCmd;
import org.ardal.db.tables.chunk.TChunk;
import org.ardal.managers.ChunkManager;
import org.ardal.models.MChunk;
import org.ardal.utils.BukkitUtils;
import org.ardal.utils.TabCompleteUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class AddChunkCmd implements ArdalCmd {
    @Override
    public boolean execute(CommandSender sender, Command command, String s, List<String> argv) {
        if(argv.isEmpty()) {
            return false;
        }

        Player player = (Player) sender;
        long chunkId = ChunkManager.GetChunkId(player.getLocation().getChunk());

        int groupId = Integer.parseInt(argv.get(0));

        TChunk tChunk = Ardal.getInstance().getDb().gettChunk();
        if(tChunk.getChunkById(chunkId) != null) {
            player.sendMessage("Chunk already saved");
            return true;
        }

        MChunk mChunk = new MChunk(chunkId, groupId);

        if(Ardal.getInstance().getManager(ChunkManager.class).addChunkInGroup(mChunk)) {
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
        return "chunk";
    }
}
