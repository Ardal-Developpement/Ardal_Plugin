package org.ardal.callbacks.quest;

import org.ardal.inventories.CICallBack;
import org.ardal.inventories.CustomInventory;
import org.ardal.objects.QuestObj;

public class EditQuestItemsRequestCallBack implements CICallBack {
    private final QuestObj questObj;

    public EditQuestItemsRequestCallBack(QuestObj questObj){
        this.questObj = questObj;
    }

    @Override
    public void executeCICallBack(CustomInventory customInventory) {
        if(this.questObj.getItemsReward() != customInventory.getAllItemStack()){
            this.questObj.setItemsRequest(customInventory.getAllItemStack());
            this.questObj.save();
            customInventory.getPlayer().sendMessage("Success to save item request for: " + this.questObj.getQuestName());
        }
    }
}
