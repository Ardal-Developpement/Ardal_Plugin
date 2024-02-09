package org.ardal.callbacks.quest;

import org.ardal.Ardal;
import org.ardal.inventories.CICallBack;
import org.ardal.inventories.CustomInventory;
import org.ardal.managers.QuestManager;
import org.ardal.objects.QuestObj;

public class EditQuestItemsRequestCallBack implements CICallBack {
    private final QuestObj questObj;

    public EditQuestItemsRequestCallBack(QuestObj questObj){
        this.questObj = questObj;
    }
    @Override
    public void executeCICallBack(CustomInventory customInventory) {
        QuestManager questManager = Ardal.getInstance().getManager(QuestManager.class);
        this.questObj.setItemsRequest(customInventory.getAllItemStack());
        questManager.getQuestDB().saveDB();
        customInventory.getPlayer().sendMessage("Success to save item request for: " + this.questObj.getQuestName());
    }
}
