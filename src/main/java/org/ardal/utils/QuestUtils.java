package org.ardal.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.ardal.Ardal;
import org.ardal.db.QuestDB;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class QuestUtils {
    public static ItemStack getQuestBook(QuestDB questDB, Player player, String questName){
        JsonObject questObj = questDB.getQuest(questName);
        if(questObj == null){
            player.sendMessage("Unknown quest.");
            return null;
        }

        JsonObject pagesObj = questObj.getAsJsonObject("pages");

        if(pagesObj == null){
            player.sendMessage("Invalid database format (in database)");
            return null;
        }

        ItemStack book = new ItemStack(Material.WRITABLE_BOOK);
        BookMeta bookMeta = (BookMeta) book.getItemMeta();
        Objects.requireNonNull(bookMeta).setDisplayName(questName);

        for(String pageNum : JsonUtils.getKeySet(pagesObj)) {
            JsonElement pageElement = pagesObj.get(pageNum);
            if(pageElement.isJsonPrimitive()) {
                String pageContent = pageElement.getAsString();
                bookMeta.addPage(pageContent);
            }
        }

        book.setItemMeta(bookMeta);
        return book;
    }

    public static List<ItemStack> getAllQuestBook(QuestDB questDB, Player player){
        List<ItemStack> questBooks = new ArrayList<>();

        for(String questName : questDB.getAllQuestName()){
            questBooks.add(QuestUtils.getQuestBook(questDB, player, questName));
        }

        return questBooks;
    }

    public static void addQuestBook(QuestDB questDB, Player player, BookMeta bookMeta){
        String title = bookMeta.getDisplayName().trim();
        if(title.isEmpty()) {
            player.sendMessage("Invalid quest name.");
            return;
        }

        List<String> pages = bookMeta.getPages();

        JsonObject newQuestBookObj = new JsonObject();
        JsonObject pagesObj = new JsonObject();
        for (int indexPage = 0; indexPage < pages.size(); indexPage++){
            pagesObj.addProperty("page_" + (indexPage + 1), pages.get(indexPage));
        }

        newQuestBookObj.add("pages", pagesObj);
        newQuestBookObj.addProperty("isActive", false);

        if(questDB.getQuest(title) != null){
            Ardal.writeToLogger("Overwriting the quest: " + title + " .");
        }

        questDB.getDb().add(title, newQuestBookObj);
        questDB.saveDB();

        player.sendMessage("Success to add quest book");
    }
}
