package org.ardal.commands.quests.set;

import org.ardal.Ardal;
import org.ardal.api.commands.ArdalCmd;
import org.ardal.managers.QuestManager;
import org.ardal.objects.QuestObj;
import org.ardal.utils.StringUtils;
import org.ardal.utils.TabCompleteUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;
import java.util.List;

public class SetQuestSynopsis implements ArdalCmd {
    @Override
    public boolean execute(CommandSender sender, Command command, String s, List<String> argv) {
        Player player = (Player) sender;
        ItemStack book = player.getInventory().getItemInMainHand().clone();

        if(!(book.getType() == Material.WRITABLE_BOOK || book.getType() == Material.WRITTEN_BOOK)){
            player.sendMessage("Please take a written book in your main hand.");
            return true;
        }


        if (!(book.hasItemMeta() && book.getItemMeta() instanceof BookMeta)) {
            player.sendMessage("Book don't have book meta.");
            return true;
        }

        BookMeta meta = (BookMeta) book.getItemMeta();
        List<String> pages = meta.getPages();
        String synopsis = "";

        if(!pages.isEmpty()) {
            StringBuilder sb = new StringBuilder(pages.get(0));
            for(int i = 1; i < pages.size(); i++){
                sb.append('\n').append(pages.get(i));
            }

            synopsis = sb.toString();
        }

        String questName = meta.getDisplayName();
        QuestObj questObj = new QuestObj(questName);
        if(!questObj.isQuestExist()) {
            player.sendMessage("Invalid quest name.");
            return true;
        }

        questObj.setQuestSynopsis(synopsis);
        sender.sendMessage("Synopsis of '" + questName + "': " + synopsis);

        return true;
    }

    @Override
    public List<String> getTabComplete(CommandSender sender, Command command, String s, List<String> argv) {
        if(argv.size() < 2) {
            QuestManager questManager = Ardal.getInstance().getManager(QuestManager.class);
            return TabCompleteUtils.getTabCompleteFromStrList(questManager.getAllQuestNames(false), StringUtils.getStringFromConcatStringList(argv));
        }

        return new ArrayList<>();
    }

    public String getHelp() {
        return String.format("%s%s:%s set synopsis.",
                ChatColor.GOLD,
                getCmdName(),
                ChatColor.WHITE);
    }

    @Override
    public String getCmdName() {
        return "synopsis";
    }
}
