package org.ardal.inventories.npc.quest.management;


import org.ardal.Ardal;
import org.ardal.api.inventories.CICell;
import org.ardal.inventories.CICarousel;
import org.ardal.managers.QuestManager;
import org.ardal.models.npc.type.MQuestNpc;
import org.ardal.npc.quest.QuestNpc;
import org.ardal.objects.QuestObj;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class QuestIsShowSelectorInventory extends CICarousel {
    private final QuestNpc questNpc;
    private final List<ItemStack> questBooks;

    public QuestIsShowSelectorInventory(QuestNpc npc, Player player){
        super(npc.getName() + " properties:", 54, player);

        this.questNpc = npc;
        this.questBooks = new ArrayList<>();

        QuestManager questManager = Ardal.getInstance().getManager(QuestManager.class);
        List<String> questNames = questManager.getAllQuestNames(false);

        for(String questName : questNames){
            questBooks.add(this.getFormattedQuestBook(questName));
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
                this.changeShowStateOfQuest(event.getCurrentItem());
            }
        }
    }

    private void changeShowStateOfQuest(ItemStack book) {
        MQuestNpc mQuestNpc =  this.questNpc.getQuestNpcByName(book.getItemMeta().getDisplayName());
        mQuestNpc.setIsShow(!mQuestNpc.getIsShow());
        mQuestNpc.updateQuestNpc();
        this.refreshBookMeta(book, mQuestNpc);
    }

    private ItemStack getFormattedQuestBook(String questName) {
        QuestObj questObj = new QuestObj(questName);

        if(questObj.isQuestExist()) {
            ItemStack book = questObj.getQuestBook();
            MQuestNpc mQuestNpc =  this.questNpc.getQuestNpcByName(questName);
            this.refreshBookMeta(book, mQuestNpc);
            return book;
        }

        return new ItemStack(Material.BARRIER);
    }

    private void refreshBookMeta(ItemStack book, MQuestNpc mQuestNpc){
        ItemMeta meta = book.getItemMeta();
        if(mQuestNpc == null) { return; }

        if(mQuestNpc.getIsShow()) {
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

        lore.add(0, ChatColor.RED + "Coef: " + mQuestNpc.getQuestCoef());
        meta.setLore(lore);

        book.setItemMeta(meta);
    }

    private void coefHandler(InventoryClickEvent event, boolean up) {
        ItemStack book = event.getCurrentItem();
        if(book == null) { return; }
        MQuestNpc mQuestNpc =  this.questNpc.getQuestNpcByName(book.getItemMeta().getDisplayName());

        if(up){
            mQuestNpc.setQuestCoef(mQuestNpc.getQuestCoef() + 1);
        } else if(mQuestNpc.getQuestCoef() > 1) {
            mQuestNpc.setQuestCoef(mQuestNpc.getQuestCoef() - 1);
        }

        mQuestNpc.updateQuestNpc();
        this.refreshBookMeta(book, mQuestNpc);
    }
}

