package org.ardal.commands.mobs;

import org.ardal.api.commands.ArdalCmd;
import org.ardal.api.entities.mobs.MobType;
import org.ardal.entities.mobs.CustomMob;
import org.ardal.utils.TabCompleteUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class InvokeMobManager implements ArdalCmd {
    @Override
    public boolean execute(CommandSender sender, Command command, String s, List<String> argv) {
        if (!(argv.size() == 1 || argv.size() == 4)) {
            return false;
        }

        Player player = (Player) sender;
        MobType mobType = MobType.getMobTypeByName(argv.get(0));
        if(mobType == null) {
            player.sendMessage("Invalid mob type");
            return true;
        }

        Location spawnLocation = player.getLocation();

        try {
            CustomMob.invokeCustomMob(mobType, spawnLocation).spawn();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public List<String> getTabComplete(CommandSender sender, Command command, String s, List<String> argv) {
        if(argv.size() < 2) {
            List<String> mobTypes = new ArrayList<>();
            for(MobType type : MobType.values()){
                mobTypes.add(type.toString());
            }

            return TabCompleteUtils.getTabCompleteFromStrList(mobTypes, argv.get(0));        }

        return new ArrayList<>();
    }

    @Override
    public String getHelp() {
        return String.format("%s%s:%s invoke a custom mob.",
                ChatColor.GOLD,
                getCmdName(),
                ChatColor.WHITE);
    }

    @Override
    public String getCmdName() {
        return "invoke";
    }
}
