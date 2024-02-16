package org.ardal.managers;

import com.google.gson.JsonObject;
import org.ardal.Ardal;
import org.ardal.api.commands.ArdalCmdManager;
import org.ardal.api.managers.ArdalManager;
import org.ardal.api.npc.CustomNpcType;
import org.ardal.api.npc.NpcInfo;
import org.ardal.commands.BaseCmdAlias;
import org.ardal.commands.npc.CreateAndInvokeNpc;
import org.ardal.commands.npc.give.GiveNpcManager;
import org.ardal.db.NpcDB;
import org.ardal.npc.NpcManagementTool;
import org.ardal.npc.quest.QuestNpc;
import org.ardal.objects.CustomNPCObj;
import org.ardal.utils.StringUtils;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CustomNPCManager extends ArdalCmdManager implements NpcInfo, ArdalManager, Listener {
    private final NpcManagementTool npcManagementTool;
    private final NpcDB npcDB;
    private List<CustomNPCObj> invokedNpc;

    public CustomNPCManager() {
        super(BaseCmdAlias.BASE_NPC_CMD_ALIAS);

        this.registerCmd(new CreateAndInvokeNpc());
        this.registerCmd(new GiveNpcManager());

        this.invokedNpc = new ArrayList<>();
        this.npcDB = new NpcDB(Ardal.getInstance().getDataFolder().toPath().toAbsolutePath());

        this.npcManagementTool = new NpcManagementTool();
        Ardal.getInstance().getServer().getPluginManager().registerEvents(this, Ardal.getInstance());
    }

    public void registerNpc(CustomNPCObj npc){
        if(!this.invokedNpc.contains(npc)){
            this.invokedNpc.add(npc);
        }
    }

    public NpcDB getNpcDB() {
        return npcDB;
    }

    public List<CustomNPCObj> getInvokedNpc() {
        return invokedNpc;
    }

    @Nullable
    public CustomNPCObj getNpcObjById(UUID id){
        for(CustomNPCObj customNPCObj : this.invokedNpc){
            if(customNPCObj.getId().equals(id)){
                return customNPCObj;
            }
        }

        return null;
    }

    @Override
    public boolean destroyNpc(UUID id) {
        for(CustomNPCObj npc : this.invokedNpc){
            if(npc.getId().equals(id)){
                npc.destroy();
                this.invokedNpc.remove(npc);
                return true;
            }
        }

        return false;
    }

    @Nullable
    public CustomNPCObj invokeNpcFromJsonObj(@NotNull JsonObject npcObj, @NotNull UUID id){
        String npcTypeName = npcObj.get("type").getAsString();
        CustomNpcType npcType = CustomNpcType.getNpcTypeByName(npcTypeName);
        if(npcType == null) { return null; }

        CustomNPCObj customNPCObj;
        switch (npcType){
            case QUEST_NPC:
                customNPCObj = new QuestNpc(npcObj, id);
                break;
            default:
                return null;
        }

        this.registerNpc(customNPCObj);
        return customNPCObj;
    }

    @Override
    public void onEnable() {
        this.npcDB.loadNPCs();
    }

    @Override
    public void onDisable() {
        this.npcDB.saveDB();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(!(commandSender instanceof Player)){
            commandSender.sendMessage("Command can be only used by player.");
            return true;
        }

        return this.onSubCmd(commandSender, command, s, StringUtils.getStrListFromStrArray(strings));
    }

    @Override
    public boolean setVisibleNpc(UUID id, boolean state) {
        for(CustomNPCObj npc : this.invokedNpc){
            if(npc.getId().equals(id)) {
                npc.setVisible(state);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean setNpcName(UUID id, String newName) {
        JsonObject npcObj = this.npcDB.getDb().getAsJsonObject(id.toString());
        if(npcObj == null) { return false; }

        npcObj.addProperty("npcName", newName);
        this.npcDB.saveDB();

        return true;
    }

    @Override
    public boolean createNewNpc(String name, CustomNpcType type, Location location) {
        CustomNPCObj customNPCObj;
        switch (type){
            case QUEST_NPC:
                customNPCObj = new QuestNpc(name, location);
                break;
            default:
                return false;
        }

        this.npcDB.saveNPC(customNPCObj);
        return true;
    }

    @Override
    public boolean deleteNpc(UUID id) {
        if(!this.isNpcExist(id)) { return false; }
        this.destroyNpc(id);
        this.npcDB.removeNpc(id);

        return true;
    }

    @Override
    public List<UUID> getAllNpcIdSaved() {
        List<UUID> ids = new ArrayList<>();
        for(String strId : this.npcDB.getKeySet()){
            ids.add(UUID.fromString(strId));
        }

        return ids;
    }

    @Override
    public boolean isNpcExist(UUID id) {
        return this.npcDB.getDb().getAsJsonObject(id.toString()) != null;
    }

    @Override
    public boolean giveManagementToolToPlayer(Player player) {
        return this.npcManagementTool.getToolToPlayer(player);
    }

    @Override
    public List<UUID> getAllNpcIdByTypeSaved(CustomNpcType type) {
        List<UUID> allIdList = getAllNpcIdSaved();
        List<UUID> idList = new ArrayList<>();
        for(UUID id : allIdList){
            JsonObject npcObj = this.npcDB.getDb().getAsJsonObject(id.toString());
            if(type.toString().equals(npcObj.getAsJsonObject("type").toString())){
                idList.add(id);
            }
        }

        return idList;
    }

    @EventHandler
    public void onNPCInteract(PlayerInteractEntityEvent event) {
        if (event.getRightClicked().getType() == EntityType.VILLAGER) {
            CustomNPCManager customNPCManager = Ardal.getInstance().getManager(CustomNPCManager.class);
            CustomNPCObj npc = customNPCManager.getNpcObjById(event.getRightClicked().getUniqueId());
            if(npc == null) { return; }

            event.setCancelled(true);

            if (event.getPlayer().getInventory().getItemInMainHand().equals(this.npcManagementTool)) {
                npc.onNpcManageToolInteract(event);
                return;
            }

            npc.onNPCInteract(event);
        }
    }
}
