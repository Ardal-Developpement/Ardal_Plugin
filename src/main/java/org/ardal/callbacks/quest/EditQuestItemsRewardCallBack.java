package org.ardal.callbacks.quest;

import org.ardal.inventories.CICallBack;
import org.ardal.inventories.CustomInventory;
import org.ardal.objects.QuestObj;

public class EditQuestItemsRewardCallBack implements CICallBack {
    private final QuestObj questObj;

    public EditQuestItemsRewardCallBack(QuestObj questObj){
        this.questObj = questObj;
    }

    @Override
    public void executeCICallBack(CustomInventory customInventory) {
        if(this.questObj.getItemsReward() != customInventory.getAllItemStack()) {
            this.questObj.setItemsReward(customInventory.getAllItemStack());
            this.questObj.save();
            customInventory.getPlayer().sendMessage("Success to save item reward for: " + this.questObj.getQuestName());
        }
    }
}
