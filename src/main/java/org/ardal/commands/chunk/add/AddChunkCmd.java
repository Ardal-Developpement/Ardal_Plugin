package org.ardal.commands.chunk.add;


import org.ardal.Ardal;
import org.ardal.api.chunks.ChunkModifierType;
import org.ardal.api.commands.ArdalCmd;
import org.ardal.managers.ChunkManager;
import org.ardal.models.MChunk;
import org.ardal.utils.TabCompleteUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AddChunkCmd implements ArdalCmd {
    @Override
    public boolean execute(CommandSender sender, Command command, String s, List<String> argv) {
        if(argv.size() < 2) {
            return false;
        }

        Player player = (Player) sender;
        ChunkManager chunkManager = Ardal.getInstance().getManager(ChunkManager.class);
        long chunkId = ChunkManager.GetChunkId(player.getLocation().getChunk());
        int groupId = Integer.parseInt(argv.get(0));

        List<ChunkModifierType> modifiers = new ArrayList<>();

        for(int i = 1; i < argv.size(); i++) {
            ChunkModifierType chunkModifierType = ChunkModifierType.getTypeFromString(argv.get(i));
            if(chunkModifierType == null) {
                player.sendMessage("Invalid group type.");
                return true;
            }

            modifiers.add(chunkModifierType);
        }

        if(chunkManager.chunkIsSaved(chunkId)) {
            player.sendMessage("Chunk already saved.");
            return true;
        }

        if(chunkManager.addChunkInGroup(new MChunk(chunkId, groupId, modifiers))) {
            player.sendMessage("Success to save in database.");
        } else {
            player.sendMessage("Failed to save in database.");
        }

        return true;
    }

    @Override
    public List<String> getTabComplete(CommandSender player, Command command, String s, List<String> argv) {
        if(argv.size() == 2) {
            return TabCompleteUtils.getTabCompleteFromStrList(
                        ChunkModifierType.getTypesAsList(),
                        argv.get(1)
                    );
        } else {
            return new ArrayList<>();
        }
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
