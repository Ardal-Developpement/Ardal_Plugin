package org.ardal.objects;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.MalformedJsonException;
import org.ardal.Ardal;
import org.ardal.managers.CustomItemManager;
import org.ardal.utils.BukkitUtils;
import org.ardal.utils.JsonUtils;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class QuestObj implements Comparable<QuestObj> {
    private boolean isActive;
    private boolean isDelete;
    private String bookId;
    private String synopsis;
    private List<String> itemsRequestId;
    private List<String>itemsRewardId;

    public QuestObj(ItemStack book, List<ItemStack> itemsRequest, List<ItemStack> itemsReward, boolean isActive) {
        this.bookId = "";
        this.isActive = isActive;
        this.isDelete = false;
        this.itemsRequestId = new ArrayList<>();
        this.itemsRewardId = new ArrayList<>();

        BukkitUtils.removeEnchant(book);

        this.setBook(book);
        this.setItemsRequest(itemsRequest);
        this.setItemsReward(itemsReward);
    }

    public QuestObj(JsonObject questObj) throws MalformedJsonException {
        JsonElement bookElem = questObj.get("bookId");
        JsonElement isActiveElem = questObj.get("isActive");
        JsonElement isDeleteElem = questObj.get("isDelete");
        JsonElement itemsRequestElem = questObj.get("itemsRequestId");
        JsonElement itemsRewardElem = questObj.get("itemsRewardId");

        JsonElement synopsisElem = questObj.get("synopsis");

        if(bookElem == null
            || isActiveElem == null
            || isDeleteElem == null
            || itemsRequestElem == null
            || itemsRewardElem == null)
        {
            throw new MalformedJsonException("Quest obj is malformed.");
        }

        this.bookId = bookElem.getAsString();
        this.isActive = isActiveElem.getAsBoolean();
        this.isDelete = isDeleteElem.getAsBoolean();
        this.itemsRequestId = JsonUtils.jsonArrayToStrList(itemsRequestElem);
        this.itemsRewardId = JsonUtils.jsonArrayToStrList(itemsRewardElem);

        if(synopsisElem != null) { this.synopsis = synopsisElem.getAsString(); }
    }

    public JsonObject toJson(){
        JsonObject questObj = new JsonObject();
        JsonArray itemRequestArray = new JsonArray();
        JsonArray itemRewardArray = new JsonArray();

        questObj.addProperty("bookId", this.bookId);
        questObj.addProperty("isActive", this.isActive);
        questObj.addProperty("isDelete", this.isDelete);

        if(this.synopsis != null) { questObj.addProperty("synopsis", this.synopsis); }

        for(String itemId : this.getItemsRequestId()){
            itemRequestArray.add(itemId);
        }
        for(String itemId : this.getItemsRewardId()){
            itemRewardArray.add(itemId);
        }

        questObj.add("itemsRequestId", itemRequestArray);
        questObj.add("itemsRewardId", itemRewardArray);

        return questObj;
    }

    public void save(){
        /*
        QuestManager questManager = Ardal.getInstance().getManager(QuestManager.class);

        if(!questManager.getQuestDB().getKeySet().contains(this.getQuestName())) {
            Ardal.writeToLogger("Adding quest: " + this.getQuestName());
        }else{
            Ardal.writeToLogger("Saving quest: " + this.getQuestName());
        }

        questManager.getQuestDB().getDb().add(this.getQuestName(), this.toJson());
        questManager.getQuestDB().saveDB();

         */
    }

    public String getQuestName(){
        if(this.getBook().getItemMeta() == null){
            return "Invalid name.";
        }

        return this.getBook().getItemMeta().getDisplayName();
    }

    public String getBookId() {
        return this.bookId;
    }

    public ItemStack getBook(){
        CustomItemManager customItemManager = Ardal.getInstance().getManager(CustomItemManager.class);
        return customItemManager.getItem(this.bookId);
    }

    public List<ItemStack> getItemsRequest() {
        CustomItemManager customItemManager = Ardal.getInstance().getManager(CustomItemManager.class);
        return customItemManager.getItems(this.itemsRequestId);
    }

    public List<ItemStack> getItemsReward() {
        CustomItemManager customItemManager = Ardal.getInstance().getManager(CustomItemManager.class);
        return customItemManager.getItems(this.itemsRewardId);
    }

    public boolean getIsActive() {
        return this.isActive;
    }

    public boolean getIsDelete() {
        return isDelete;
    }

    public String getSynopsis() {
        return this.synopsis == null ? "" : this.synopsis;
    }

    public List<String> getItemsRequestId() {
        return itemsRequestId;
    }

    public List<String> getItemsRewardId() {
        return itemsRewardId;
    }

    public boolean addItemRewardId(String itemRewardId) {
        return this.itemsRewardId.add(itemRewardId);
    }

    public boolean addItemRequestId(String itemRequestId){
        return this.itemsRequestId.add(itemRequestId);
    }

    public void setItemsRequestId(List<String> itemsRequestId){
        this.itemsRequestId = itemsRequestId;
    }

    public void setItemsRewardId(List<String> itemsRewardId) {
        this.itemsRewardId = itemsRewardId;
    }

    public boolean addItemRequest(ItemStack item){
        CustomItemManager customItemManager = Ardal.getInstance().getManager(CustomItemManager.class);
        return this.itemsRequestId.add(customItemManager.addItem(item).toString());
    }

    public boolean addItemReward(ItemStack item){
        CustomItemManager customItemManager = Ardal.getInstance().getManager(CustomItemManager.class);
        return this.itemsRewardId.add(customItemManager.addItem(item).toString());
    }

    public void setSynopsis(@Nullable String synopsis){
        this.synopsis = synopsis;
    }

    public boolean setItemsRequest(List<ItemStack> items){
        //TODO remove items from custom items
        this.itemsRequestId = new ArrayList<>();
        for(ItemStack item : items){
            if(!this.addItemRequest(item)){
                return false;
            }
        }

        return true;
    }
    public boolean setItemsReward(List<ItemStack> items){
        //TODO remove items from custom items
        this.itemsRewardId = new ArrayList<>();
        for(ItemStack item : items){
            if(!this.addItemReward(item)){
                return false;
            }
        }

        return true;
    }

    public void setBook(ItemStack item){
        CustomItemManager customItemManager = Ardal.getInstance().getManager(CustomItemManager.class);
        this.bookId = customItemManager.addItem(item).toString();
    }

    @Override
    public int compareTo(QuestObj otherQuestObj) {
        return this.getQuestName().compareTo(otherQuestObj.getQuestName());
    }
}
