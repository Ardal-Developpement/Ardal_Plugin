package org.ardal.managers;

import org.ardal.Ardal;
import org.ardal.api.commands.ArdalCmdManager;
import org.ardal.api.managers.ArdalManager;
import org.ardal.api.npc.CustomNpcType;
import org.ardal.api.npc.NpcInfo;
import org.ardal.commands.BaseCmdAlias;
import org.ardal.commands.npc.CreateAndInvokeNpc;
import org.ardal.commands.npc.give.GiveNpcManager;
import org.ardal.inventories.npc.quest.management.NpcManagementTool;
import org.ardal.models.npc.core.MNpc;
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
    private List<CustomNPCObj> invokedNpc;

    public CustomNPCManager() {
        super(BaseCmdAlias.BASE_NPC_CMD_ALIAS);

        this.registerCmd(new CreateAndInvokeNpc());
        this.registerCmd(new GiveNpcManager());

        this.invokedNpc = new ArrayList<>();

        this.npcManagementTool = new NpcManagementTool();
        Ardal.getInstance().getServer().getPluginManager().registerEvents(this, Ardal.getInstance());
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(!(commandSender instanceof Player)){
            commandSender.sendMessage("Command can be only used by player.");
            return true;
        }

        return this.onSubCmd(commandSender, command, s, StringUtils.getStrListFromStrArray(strings));
    }

    public void registerNpc(CustomNPCObj npc){
        if(!this.invokedNpc.contains(npc)){
            this.invokedNpc.add(npc);
        }
    }

    public List<CustomNPCObj> getInvokedNpc() {
        return invokedNpc;
    }

    public NpcManagementTool getNpcManagementTool() {
        return npcManagementTool;
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
    public MNpc getNpcByUuid(UUID uuid) {
        return Ardal.getInstance().getDb().gettNpc().getNpcByUuid(uuid.toString());
    }

    @Override
    public boolean setVisibleNpc(UUID id, boolean state) {
        MNpc mNpc = this.getNpcByUuid(id);
        if(mNpc == null) { return false; }

        mNpc.setIsVisible(state);
        return mNpc.updateNpc();
    }

    @Override
    public boolean setNpcName(UUID id, String newName) {
        MNpc mNpc = this.getNpcByUuid(id);
        if(mNpc == null) { return false; }

        mNpc.setName(newName);
        return mNpc.updateNpc();
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

        // TODO
        // this.npcDB.saveNPC(customNPCObj);
        return true;
    }

    @Override
    public boolean deleteNpc(UUID id) {
        return Ardal.getInstance().getDb().gettNpc().deleteNpc(id.toString());
    }

    @Override
    public List<UUID> getAllNpcIdSaved() {
        return null;
    }

    @Override
    public boolean isNpcExist(UUID id) {
        return Ardal.getInstance().getDb().gettNpc().npcExist(id.toString());
    }


    @Override
    public boolean giveManagementToolToPlayer(Player player) {
        return this.npcManagementTool.getToolToPlayer(player);
    }
    
    @EventHandler
    public void onNPCInteract(PlayerInteractEntityEvent event) {
        if (event.getRightClicked().getType() == EntityType.VILLAGER) {
            CustomNPCManager customNPCManager = Ardal.getInstance().getManager(CustomNPCManager.class);
            CustomNPCObj npc = customNPCManager.getNpcObjById(event.getRightClicked().getUniqueId());
            if(npc == null) { return; }

            event.setCancelled(true);

            if (event.getPlayer().getInventory().getItemInMainHand().isSimilar(this.npcManagementTool.getTool())) {
                npc.onNpcManageToolInteract(event);
                return;
            }

            npc.onNPCInteract(event);
        }
    }
}
