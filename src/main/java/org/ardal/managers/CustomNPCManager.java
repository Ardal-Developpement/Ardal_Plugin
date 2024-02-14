package org.ardal.managers;

import com.google.gson.JsonObject;
import org.ardal.Ardal;
import org.ardal.api.commands.ArdalCmdManager;
import org.ardal.api.managers.ArdalManager;
import org.ardal.api.npc.CustomNpcType;
import org.ardal.api.npc.NpcInfo;
import org.ardal.commands.BaseCmdAlias;
import org.ardal.commands.npc.CreateNpc;
import org.ardal.commands.npc.DeleteNpc;
import org.ardal.commands.npc.InvokeNpc;
import org.ardal.db.NpcDB;
import org.ardal.npc.quest.QuestNpc;
import org.ardal.objects.CustomNPCObj;
import org.ardal.utils.JsonUtils;
import org.ardal.utils.StringUtils;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CustomNPCManager extends ArdalCmdManager implements NpcInfo, ArdalManager, Listener {
    private final NpcDB npcDB;
    private final List<CustomNPCObj> registeredNpc;

    public CustomNPCManager() {
        super(BaseCmdAlias.BASE_NPC_CMD_ALIAS);

        this.registerCmd(new CreateNpc());
        this.registerCmd(new DeleteNpc());
        this.registerCmd(new InvokeNpc());

        this.npcDB = new NpcDB(Ardal.getInstance().getDataFolder().toPath().toAbsolutePath());
        this.registeredNpc = this.npcDB.loadNPCs();
    }

    public void registerNpc(CustomNPCObj npc){
        if(!this.registeredNpc.contains(npc)){
            this.npcDB.saveNPC(npc);
            this.registeredNpc.add(npc);
        }
    }

    public void unregisterNpc(CustomNPCObj npc){
        npc.destroy();
        this.npcDB.removeNpc(npc.getNpcName());
        this.registeredNpc.remove(npc);
    }

    @Override
    public void onEnable() {
        this.registeredNpc.forEach(CustomNPCObj::invoke);
    }

    @Override
    public void onDisable() {
        this.registeredNpc.forEach(CustomNPCObj::destroy);
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

    @EventHandler
    public void onNPCInteract(PlayerInteractEntityEvent event) {
        if (event.getRightClicked().getType() == EntityType.VILLAGER) {
            Villager npc = (Villager) event.getRightClicked();

            for(CustomNPCObj customNpc : this.registeredNpc){
                if(npc == customNpc.getNpcEntity()){
                    customNpc.onNPCInteract(event);
                    return;
                }
            }
        }
    }

    @Override
    public boolean invokeNpc(String name) {
        for(CustomNPCObj npc : this.registeredNpc){
            if(npc.getNpcName().equals(name)){
                npc.invoke();
                return true;
            }
        }

        return false;
    }


    @Override
    public boolean destroyNpc(String name) {
        for(CustomNPCObj npc : this.registeredNpc){
            if(npc.getNpcName().equals(name)){
                npc.destroy();
                return true;
            }
        }

        return false;
    }

    @Override
    public Location getNpcLocation(String name) {
        for(CustomNPCObj npc : this.registeredNpc){
            if(npc.getNpcName().equals(name)){
                return npc.getLocation();
            }
        }

        return null;
    }

    @Override
    public boolean setVisibleNpc(String name, boolean state) {
        for(CustomNPCObj npc : this.registeredNpc){
            if(npc.getNpcName().equals(name)){
                npc.setVisible(state);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean createNewNpc(String name, CustomNpcType type) {
        for(CustomNPCObj npc : this.registeredNpc){
            if(npc.getNpcName().equals(name)) {
                return false;
            }
        }

        CustomNPCObj customNPCObj;
        switch (type){
            case QUEST_NPC:
                customNPCObj = new QuestNpc(name, null);
                break;
            default:
                return false;
        }

        this.registerNpc(customNPCObj);
        return true;
    }

    @Override
    public boolean deleteNpc(String name) {
        for(CustomNPCObj npc : this.registeredNpc){
            if(npc.getNpcName().equals(name)){
                this.unregisterNpc(npc);
                return true;
            }
        }

        return false;
    }

    @Override
    public List<String> getAllNpcNamesByType(CustomNpcType type) {
        List<String> npcNames = getAllNpcNames();
        List<String> npcNamesByType = new ArrayList<>();
        for(String name : npcNames){
            JsonObject npcObj = this.npcDB.getDb().getAsJsonObject(name);
            if(type.toString().equals(npcObj.getAsJsonObject("type").toString())){
                npcNamesByType.add(name);
            }
        }

        return npcNamesByType;
    }

    @Override
    public List<String> getAllNpcNames() {
        return JsonUtils.getKeySet(this.npcDB.getDb());
    }
}
