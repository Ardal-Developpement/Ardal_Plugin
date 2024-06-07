package org.ardal.commands.chunk;

import org.ardal.Ardal;
import org.ardal.api.commands.ArdalCmd;
import org.ardal.managers.ChunkManager;
import org.ardal.models.MChunk;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ShowChunkMapCmd implements ArdalCmd {
    @Override
    public boolean execute(CommandSender sender, Command command, String s, List<String> argv) {
        Player player = (Player) sender;
        ChunkManager chunkManager = Ardal.getInstance().getManager(ChunkManager.class);
        Location epicenter = player.getLocation();
        final int range = 5;

        StringBuilder sb = new StringBuilder();

        for(int z = (int)epicenter.getZ() - range * 16; z < (int)epicenter.getZ() + (range+1) * 16; z+=16) {
            for(int x = (int)epicenter.getX() - range * 16; x < (int)epicenter.getX() + (range+1) * 16; x+=16) {
                Location location = new Location(player.getWorld(), x, 0, z);
                Long chunkId = ChunkManager.GetChunkId(location.getChunk());

                MChunk mChunk = chunkManager.getChunkById(chunkId);

                if(x == (int)epicenter.getX() && z == (int)epicenter.getZ()) {
                    sb.append('◯');
                } else {
                    if(mChunk != null) {
                        sb.append('X');
                    } else {
                        sb.append('-');
                    }
                }
            }

            sb.append('\n');
        }

        player.sendMessage(sb.toString());

        return true;
    }

    @Override
    public List<String> getTabComplete(CommandSender player, Command command, String s, List<String> argv) {
        return new ArrayList<>();
    }

    @Override
    public String getHelp() {
        return String.format("%s%s:%s show chunk map.",
                ChatColor.GOLD,
                getCmdName(),
                ChatColor.WHITE);
    }

    @Override
    public String getCmdName() {
        return "map";
    }
}