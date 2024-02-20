package org.ardal.inventories.quest.management;

import org.ardal.Ardal;
import org.ardal.api.inventories.CICell;
import org.ardal.api.inventories.CustomInventory;
import org.ardal.api.inventories.callback.CellCallBack;
import org.ardal.managers.QuestManager;
import org.ardal.objects.QuestObj;
import org.ardal.utils.MathUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class QuestIsActiveSelectorInventory extends CustomInventory implements CellCallBack {
    private static final int SHOWED_QUEST_RANGE = 45;
    private final List<String> quests;
    private int currentStartIndex;

    public QuestIsActiveSelectorInventory(String npcName, Player player, List<String> quests) {
        /*
            Big inventory
            last line is just for buttons (page +, page -)
            All the rest is filled by quest book.

         */

        super(npcName + " properties:", 54, player);
        this.quests = quests;
        this.currentStartIndex = 0;

        this.setPreviousPageItem();
        this.setNextPageItem();
    }

    private void showNextQuest(){
        if(this.currentStartIndex >= this.quests.size()) { this.currentStartIndex = 0; }

        int lenght = MathUtils.clamp(this.currentStartIndex, 0, this.quests.size());
        
        for (int i = this.currentStartIndex; i < lenght; i++){
            this.setCell(new CICell(this.getQuestBook(this.quests.get(i)),
                    this.currentStartIndex - i,
                    null,
                    null,
                    this,
                    null
            ));
        }
    }

    private void showPreviousPage(){
        if(this.currentStartIndex == this.quests.size()) { this.currentStartIndex = this.quests.size() - 1; }

        int lenght = MathUtils.clamp(this.currentStartIndex, 0, this.quests.size());

        for (int i = this.currentStartIndex; i >= 0; i--){
            this.setCell(new CICell(this.getQuestBook(this.quests.get(i)),
                    this.currentStartIndex - i,
                    null,
                    null,
                    this,
                    null
            ));
        }
    }



    private ItemStack getQuestBook(String questName){
        QuestManager questManager = Ardal.getInstance().getManager(QuestManager.class);
        QuestObj questObj = questManager.getQuestObj(questName);
        if(questObj == null) { return null; }

        ItemStack item = questObj.getBook();
        ItemMeta meta = item.getItemMeta();

        if(questObj.getIsActive()){
            meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        }

        item.setItemMeta(meta);
        return item;
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
        QuestManager questManager = Ardal.getInstance().getManager(QuestManager.class);
        Boolean currentQuestActivity = questManager.getQuestActivity(meta.getDisplayName());

        if(currentQuestActivity == null) {
            Ardal.getInstance().getLogger().severe("Error of quest book name.");
            return;
        }

        questManager.setQuestActivity(meta.getDisplayName(), !currentQuestActivity);

        if(currentQuestActivity){
            meta.removeEnchant(Enchantment.ARROW_INFINITE);
        } else {
            meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        }

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

    @Override
    public void onCIClose(InventoryCloseEvent event) {

    }

    public List<String> getQuests() {
        return quests;
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if(item == null) { return; }

        switch (item.getType()){
            case CAMPFIRE:
                this.setNextPageItem();
                break;
            case SOUL_CAMPFIRE:
                this.setPreviousPageItem();
                break;
            case WRITABLE_BOOK:
                this.changeActivityOfQuest(item);
                break;
            default:
                break;
        }
    }
}
