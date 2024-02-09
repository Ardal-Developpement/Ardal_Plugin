package org.ardal.objects;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.MalformedJsonException;
import org.ardal.Ardal;
import org.ardal.managers.CustomItemManager;
import org.ardal.utils.JsonUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class QuestObj implements Comparable<QuestObj> {
    private final ItemStack book;
    private List<ItemStack> itemsRequest;
    private List<ItemStack>itemsReward;
    private final boolean isActive;

    public QuestObj(ItemStack book, List<ItemStack> itemsRequest, List<ItemStack> itemsReward, boolean isActive) {
        this.book = book;
        this.itemsRequest = itemsRequest;
        this.itemsReward = itemsReward;
        this.isActive = isActive;
    }

    public QuestObj(CommandSender sender, JsonObject questObj) throws MalformedJsonException {
        JsonElement bookElem = questObj.get("bookId");
        JsonElement isActiveElem = questObj.get("isActive");
        JsonElement itemsRequestElem = questObj.get("itemsRequestId");
        JsonElement itemsRewardElem = questObj.get("itemsRewardId");

        if(bookElem == null
            || isActiveElem == null
            || itemsRequestElem == null
            || itemsRewardElem == null)
        {
            throw new MalformedJsonException("Quest obj is malformed.");
        }


        CustomItemManager customItemManager = Ardal.getInstance().getManager(CustomItemManager.class);

        this.book = customItemManager.getItemByStrId(sender, bookElem.getAsString());
        this.isActive = isActiveElem.getAsBoolean();

        this.itemsRequest = customItemManager.getItemsByStrId(sender, JsonUtils.jsonArrayToStrList(itemsRequestElem));
        this.itemsReward = customItemManager.getItemsByStrId(sender, JsonUtils.jsonArrayToStrList(itemsRequestElem));
    }

    public JsonObject toJson(CommandSender sender){
        CustomItemManager customItemManager = Ardal.getInstance().getManager(CustomItemManager.class);
        JsonObject questObj = new JsonObject();
        JsonArray itemRequestArray = new JsonArray();
        JsonArray itemRewardArray = new JsonArray();

        questObj.addProperty("bookId", customItemManager.addItem(sender, this.book).toString());
        questObj.addProperty("isActive", this.isActive);
        
        for(ItemStack item : this.itemsRequest){
            itemRequestArray.add(customItemManager.addItem(sender, item).toString());
        }
        for(ItemStack item : this.itemsReward){
            itemRewardArray.add(customItemManager.addItem(sender, item).toString());
        }

        questObj.add("itemsRequestId", itemRequestArray);
        questObj.add("itemsRewardId", itemRewardArray);

        return questObj;
    }

    public String getQuestName(){
        return this.book.getItemMeta().getDisplayName();
    }

    public ItemStack getBook() {
        return book;
    }

    public List<ItemStack> getItemsRequest() {
        return itemsRequest;
    }

    public List<ItemStack> getItemsReward() {
        return itemsReward;
    }

    public void setItemsRequest(List<ItemStack> itemsRequest){
        this.itemsRequest = itemsRequest;
    }

    public void setItemsReward(List<ItemStack> itemsReward) {
        this.itemsReward = itemsReward;
    }

    @Override
    public int compareTo(QuestObj otherQuestObj) {
        return this.getQuestName().compareTo(otherQuestObj.getQuestName());
    }
}
