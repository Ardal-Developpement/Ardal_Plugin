package org.ardal.callbacks.quest;

import org.ardal.Ardal;
import org.ardal.inventories.CICallBack;
import org.ardal.inventories.CustomInventory;
import org.ardal.managers.QuestManager;
import org.ardal.objects.QuestObj;

public class EditQuestItemsRewardCallBack implements CICallBack {
    private final QuestObj questObj;

    public EditQuestItemsRewardCallBack(QuestObj questObj){
        this.questObj = questObj;
    }
    @Override
    public void executeCICallBack(CustomInventory customInventory) {
        this.questObj.setItemsReward(customInventory.getAllItemStack());
        Ardal.getInstance().getManager(QuestManager.class).getQuestDB().saveDB();
        customInventory.getPlayer().sendMessage("Success to save item reward for: " + this.questObj.getQuestName());
    }
}
