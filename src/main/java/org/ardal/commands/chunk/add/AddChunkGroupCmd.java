package org.ardal.commands.chunk.add;

import org.ardal.Ardal;
import org.ardal.api.chunks.ChunkModifierType;
import org.ardal.api.commands.ArdalCmd;
import org.ardal.managers.ChunkManager;
import org.ardal.objects.chunk.ChunkGroupObj;
import org.ardal.utils.TabCompleteUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class AddChunkGroupCmd implements ArdalCmd {
    @Override
    public boolean execute(CommandSender sender, Command command, String s, List<String> argv) {
        List<ChunkModifierType> modifierTypes = new ArrayList<>();
        for (String string : argv) {
            ChunkModifierType type = ChunkModifierType.getTypeFromString(string);
            if (type == null) {
                sender.sendMessage("Unknown chunk modifier type: " + string);
                return true;
            }
            modifierTypes.add(type);
        }

        ChunkManager chunkManager = Ardal.getInstance().getManager(ChunkManager.class);
        ChunkGroupObj chunkGroupObj = chunkManager.addNewChunkGroup(modifierTypes);

        if(chunkGroupObj != null) {
            sender.sendMessage("Created a new chunk group with id: " + chunkGroupObj.getChunkGroupId());
        } else {
            sender.sendMessage("Failed to create a new chunk group!");
        }

        return true;
    }

    @Override
    public List<String> getTabComplete(CommandSender player, Command command, String s, List<String> argv) {
        return TabCompleteUtils.getTabCompleteFromStrList(ChunkModifierType.getTypesAsList(), argv.get(argv.size() - 1));
    }

    @Override
    public String getHelp() {
        return String.format("%s%s:%s Add chunk group.",
                ChatColor.GOLD,
                getCmdName(),
                ChatColor.WHITE);
    }

    @Override
    public String getCmdName() {
        return "chunkGroup";
    }
}
