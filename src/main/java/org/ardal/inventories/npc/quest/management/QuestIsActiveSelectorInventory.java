package org.ardal.inventories.npc.quest.management;

import org.ardal.Ardal;
import org.ardal.api.inventories.CICell;
import org.ardal.inventories.CICarousel;
import org.ardal.managers.QuestManager;
import org.ardal.npc.quest.QuestNpc;
import org.ardal.npc.quest.QuestNpcInfo;
import org.ardal.objects.QuestObj;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class QuestIsActiveSelectorInventory extends CICarousel {
    private final QuestNpc questNpc;
    private final List<ItemStack> questBooks;

    public QuestIsActiveSelectorInventory(QuestNpc npc, Player player){
        super(npc.getNpcName() + " properties:", 54, player);

        this.questNpc = npc;
        this.questBooks = new ArrayList<>();

        QuestManager questManager = Ardal.getInstance().getManager(QuestManager.class);
        List<String> questNames = questManager.getAllQuestNames();

        for(String questName : questNames){
            questBooks.add(this.getQuestBook(questName, questManager));
        }

        this.buildCarousel(this.questBooks, new CICell(null,
                -1,
                null,
                this,
                this,
                this
        ));
    }

    @Override
    public void onItemsClick(InventoryClickEvent event) {

        if(event.isShiftClick()){

            if(event.isLeftClick()){

                coefHandler(event, true);
            } else {
                coefHandler(event, false);
            }
        } else if (event.isLeftClick()){
            if(event.getCurrentItem().getType() == Material.WRITABLE_BOOK){
                this.changeActivityOfQuest(event.getCurrentItem());
            }
        }
    }

    @Override
    public void onCIClose(InventoryCloseEvent event){
        this.questNpc.saveNpcAdditionnalInfo();
        this.unregisterInventory();
    }

    private void changeActivityOfQuest(ItemStack book) {
        ItemMeta meta = book.getItemMeta();
        QuestNpcInfo questNpcInfo = this.questNpc.getQuestNpcByName(meta.getDisplayName());
        questNpcInfo.setIsShow(!questNpcInfo.getIsShow());

        this.refreshBookMeta(book);
    }

    private ItemStack getQuestBook(String questName, QuestManager questManager){
        QuestObj questObj = questManager.getQuestObj(questName);
        if(questObj == null || !questObj.getIsActive()) { return null; }

        ItemStack book = questObj.getBook();
        this.refreshBookMeta(book);

        return book;
    }

    private void refreshBookMeta(ItemStack book){
        ItemMeta meta = book.getItemMeta();
        QuestNpcInfo questNpcInfo = this.questNpc.getQuestNpcByName(meta.getDisplayName());

        if(questNpcInfo.getIsShow()){
            meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);

        } else {
            meta.removeEnchant(Enchantment.ARROW_INFINITE);
        }

        List<String> lore = meta.getLore();
        if (lore == null) {
            lore = new ArrayList<>();
        }

        if(!lore.isEmpty() && lore.get(0).contains(ChatColor.RED + "Coef: ")){
            lore.remove(0);
        }

        lore.add(0, ChatColor.RED + "Coef: " + questNpcInfo.getQuestCoef());
        meta.setLore(lore);


        book.setItemMeta(meta);
    }

    private void coefHandler(InventoryClickEvent event, boolean up) {
        ItemStack book = event.getCurrentItem();
        if(book == null) { return; }
        String questName = book.getItemMeta().getDisplayName();
        QuestNpcInfo questNpcInfo = this.questNpc.getQuestNpcByName(questName);

        if(up){
            questNpcInfo.setQuestCoef(questNpcInfo.getQuestCoef() + 1);
        } else if(questNpcInfo.getQuestCoef() > 1) {
            questNpcInfo.setQuestCoef(questNpcInfo.getQuestCoef() - 1);
        }

        this.refreshBookMeta(book);
    }
}
