package org.ardal.callbacks.quest.edititems;

import org.ardal.api.inventories.callback.CICallBack;
import org.ardal.api.inventories.CustomInventory;
import org.ardal.objects.QuestObj;
import org.ardal.utils.ListUtils;

public class EditQuestItemsRequestCallBack implements CICallBack {
    private final QuestObj questObj;

    public EditQuestItemsRequestCallBack(QuestObj questObj){
        this.questObj = questObj;
    }

    @Override
    public void executeCICallBack(CustomInventory customInventory) {
        if(!ListUtils.isItemListEqual(this.questObj.getItemsQuestRequest(), customInventory.getAllItemStack())){
            System.out.println("test1");
            this.questObj.setItemsQuestRequest(customInventory.getAllItemStack());
            customInventory.getPlayer().sendMessage("Success to save item request for: " + this.questObj.getQuestName());
        }
    }
}
