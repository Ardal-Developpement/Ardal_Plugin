package org.ardal.callbacks.quest.edititems;

import org.ardal.api.inventories.callback.CICallBack;
import org.ardal.api.inventories.CustomInventory;
import org.ardal.objects.QuestObj;

public class EditQuestItemsRewardCallBack implements CICallBack {
    private final QuestObj questObj;

    public EditQuestItemsRewardCallBack(QuestObj questObj){
        this.questObj = questObj;
    }

    @Override
    public void executeCICallBack(CustomInventory customInventory) {
        if(this.questObj.getItemQuestReward() != customInventory.getAllItemStack()) {
            this.questObj.setItemsQuestReward(customInventory.getAllItemStack());
            customInventory.getPlayer().sendMessage("Success to save item reward for: " + this.questObj.getQuestName());
        }
    }
}
