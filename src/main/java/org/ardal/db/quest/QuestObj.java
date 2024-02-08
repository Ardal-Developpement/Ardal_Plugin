package org.ardal.db.quest;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.MalformedJsonException;
import org.ardal.utils.ItemStackUtils;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class QuestObj {
    private final ItemStack book;
    private final List<ItemStack> itemsRequest;
    private final List<ItemStack>itemsReward;
    private final boolean isActive;

    public QuestObj(ItemStack book, List<ItemStack> itemsRequest, List<ItemStack> itemsReward, boolean isActive) {
        this.book = book;
        this.itemsRequest = itemsRequest;
        this.itemsReward = itemsReward;
        this.isActive = isActive;
    }

    public QuestObj(JsonObject questObj) throws MalformedJsonException {
        JsonElement bookElem = questObj.get("book");
        JsonElement isActiveElem = questObj.get("isActive");
        JsonElement itemsRequestElem = questObj.get("itemsRequest");
        JsonElement itemsRewardElem = questObj.get("itemsReward");

        if(bookElem == null
            || isActiveElem == null
            || itemsRequestElem == null
            || itemsRewardElem == null)
        {
            throw new MalformedJsonException("Quest obj is malformed.");
        }

        this.book = ItemStackUtils.getItemStackFromJson(bookElem);
        this.isActive = isActiveElem.getAsBoolean();
        this.itemsRequest = new ArrayList<>();
        this.itemsReward = new ArrayList<>();

        for(JsonElement itemRequestElem : itemsRequestElem.getAsJsonArray()){
            this.itemsRequest.add(ItemStackUtils.getItemStackFromJson(itemRequestElem));
        }
        for (JsonElement itemRewardElem : itemsRewardElem.getAsJsonArray()){
            this.itemsReward.add(ItemStackUtils.getItemStackFromJson(itemRewardElem));
        }
    }

    public JsonObject toJson(){
        JsonObject questObj = new JsonObject();
        JsonElement bookObj = ItemStackUtils.ItemStackToJson(this.book);

        JsonArray itemsRequestArray = new JsonArray();
        for(ItemStack itemRequest : this.itemsRequest){
            itemsRequestArray.add(ItemStackUtils.ItemStackToJson(itemRequest));
        }

        JsonArray itemRewardArray = new JsonArray();
        for(ItemStack itemReward : this.itemsReward){
            itemRewardArray.add(ItemStackUtils.ItemStackToJson(itemReward));
        }

        questObj.addProperty("isActive", this.isActive);
        questObj.add("book", bookObj);
        questObj.add("itemsRequest", itemsRequestArray);
        questObj.add("itemsReward", itemRewardArray);

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
}
