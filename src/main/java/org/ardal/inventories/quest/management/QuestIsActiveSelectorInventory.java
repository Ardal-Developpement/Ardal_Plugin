package org.ardal.inventories.quest.management;

import org.ardal.Ardal;
import org.ardal.api.inventories.CICell;
import org.ardal.api.inventories.CustomInventory;
import org.ardal.api.inventories.callback.CellCallBack;
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

public class QuestIsActiveSelectorInventory extends CustomInventory implements CellCallBack {
    private static final int SHOWED_QUEST_RANGE = 45;
    private final QuestNpc questNpc;
    private final List<String> quests;
    private int currentStartIndex;

    public QuestIsActiveSelectorInventory(QuestNpc npc, Player player) {
        /*
            Big inventory
            last line is just for buttons (page +, page -)
            All the rest is filled by quest book.

         */

        super(npc.getNpcName() + " properties:", 54, player);

        QuestManager questManager = Ardal.getInstance().getManager(QuestManager.class);
        this.questNpc = npc;
        this.quests = questManager.getAllQuestNames();

        this.currentStartIndex = 0;

        this.setPreviousPageItem();
        this.setNextPageItem();
        this.showQuest(true);
    }

    private void showQuest(boolean nextPage) {
        int newIndex;
        if (nextPage) {
            newIndex = this.currentStartIndex + SHOWED_QUEST_RANGE;
            if (newIndex >= this.quests.size()) {
                newIndex = 0;
            }
        } else {
            newIndex = this.currentStartIndex - SHOWED_QUEST_RANGE;
            if (newIndex < 0) {
                newIndex = this.quests.size() - 1;
            }
        }

        int slot = 0;
        for (int i = this.currentStartIndex; i < this.quests.size() && slot < SHOWED_QUEST_RANGE; i++) {
            ItemStack book = this.getQuestBook(this.quests.get(i));
            if (book == null) {
                continue;
            }
            this.setCell(new CICell(book,
                slot,
                null,
                this,
                this,
                this
            ));
            slot++;
        }
        this.currentStartIndex = newIndex;

        //clean inventory
        for(; slot < SHOWED_QUEST_RANGE; slot++){
            this.clearCell(slot);
        }
    }


    private ItemStack getQuestBook(String questName){
        QuestManager questManager = Ardal.getInstance().getManager(QuestManager.class);
        QuestObj questObj = questManager.getQuestObj(questName);
        if(questObj == null || !questObj.getIsActive()) { return null; }

        ItemStack book = questObj.getBook();
        this.refreshBookMeta(book);

        return book;
    }

    private void setNextPageItem(){
        ItemStack item = new ItemStack(Material.CAMPFIRE);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("Next page");
        item.setItemMeta(meta);

        this.setCell(new CICell(
                item,
                5, 5,
                null,
                null,
                this,
                null
        ));
    }


    private void setPreviousPageItem(){
        ItemStack item = new ItemStack(Material.SOUL_CAMPFIRE);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("Previous page");

        item.setItemMeta(meta);
        this.setCell(new CICell(
                item,
                3, 5,
                null,
                null,
                this,
                null
        ));
    }

    private void changeActivityOfQuest(ItemStack book) {
        ItemMeta meta = book.getItemMeta();
        QuestNpcInfo questNpcInfo = this.questNpc.getQuestNpcByName(meta.getDisplayName());
        questNpcInfo.setIsShow(!questNpcInfo.getIsShow());

        this.refreshBookMeta(book);
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



    @Override
    public void onCIClick(InventoryClickEvent event){
        event.setCancelled(true);
        CICell cell = this.getCell(event.getSlot());
        if(cell != null){
            cell.onCellClick(event);
        }
    }

    public List<String> getQuests() {
        return quests;
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        if(event.isShiftClick()){

            if(event.isLeftClick()){

                coefHandler(event, true);
            } else {
                coefHandler(event, false);
            }
        } else if (event.isLeftClick()){
            isActiveHandler(event);
        }
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

    private void isActiveHandler(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if(item == null) { return; }

        switch (item.getType()) {
            case CAMPFIRE:
                this.showQuest(true);
                break;
            case SOUL_CAMPFIRE:
                this.showQuest(false);
                break;
            case WRITABLE_BOOK:
                this.changeActivityOfQuest(item);
                break;
            default:
                break;
        }
    }

    public void onCIClose(InventoryCloseEvent event){
        this.questNpc.saveNpcAdditionnalInfo();
        this.unregisterInventory();
    }
}