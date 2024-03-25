package org.ardal.managers;

import org.ardal.Ardal;
import org.ardal.api.commands.ArdalCmdManager;
import org.ardal.api.managers.ArdalManager;
import org.ardal.api.npc.NpcManagerInfo;
import org.ardal.api.npc.NpcType;
import org.ardal.commands.BaseCmdAlias;
import org.ardal.commands.npc.give.GiveNpcManager;
import org.ardal.db.tables.npc.TNpc;
import org.ardal.inventories.npc.quest.management.NpcManagementTool;
import org.ardal.models.npc.MNpc;
import org.ardal.objects.NpcObj;
import org.ardal.utils.StringUtils;
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

public class NPCManager extends ArdalCmdManager implements NpcManagerInfo, ArdalManager, Listener {
    private final NpcManagementTool npcManagementTool;
    private final List<NpcObj> npcs;
    public NPCManager() {
        super(BaseCmdAlias.BASE_NPC_CMD_ALIAS);

        //.registerCmd(new CreateAndInvokeNpc());
        //this.registerCmd(new GiveNpcManager());

        this.npcs = new ArrayList<>();

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
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Command can be only used by player.");
            return true;
        }

        return this.onSubCmd(commandSender, command, s, StringUtils.getStrListFromStrArray(strings));
    }

    public void registerNpc(NpcObj npcObj) {
        if(!this.npcs.contains(npcObj)) {
            this.npcs.add(npcObj);
        }
    }

    public void unregisterNpc(NpcObj npcObj) {
        this.npcs.remove(npcObj);
    }

    private void loadNpcs() {
        List<String> npcUuids = this.getAllNpcUuids();
        TNpc tNpc = Ardal.getInstance().getDb().gettNpc();
        for(String npcUuid : npcUuids) {
            MNpc mNpc = tNpc.getNpcByUuid(npcUuid);
            NpcType npcType = mNpc.getType();
            if(npcType == null) {
                Ardal.writeToLogger("Unknown Npc type found: " + mNpc.getType());
                continue;
            }

            switch (npcType){
                case QUEST_NPC:

                    break;
            }
        }
    }

    @Override
    @Nullable
    public NpcObj getRegisteredNpcByUuid(String npcUuid) {
        for(NpcObj npc : this.npcs) {
            if(npc.getUuid().equals(npcUuid)) {
                return npc;
            }
        }
        return null;
    }

    @Override
    public List<String> getAllNpcUuids() {
        return null;
    }

    @Override
    public boolean getIsNpcExist(String id) {
        return Ardal.getInstance().getDb().gettNpc().npcExist(id);
    }

    @Override
    public boolean giveManagementToolToPlayer(Player player) {
        return this.npcManagementTool.getToolToPlayer(player);
    }

    @EventHandler
    public void onNPCInteract(PlayerInteractEntityEvent event) {
        if (event.getRightClicked().getType() == EntityType.VILLAGER) {
            NpcObj npc = this.getRegisteredNpcByUuid(event.getRightClicked().getUniqueId().toString());
            if(npc == null) { return; }

            event.setCancelled(true);

            if (event.getPlayer().getInventory().getItemInMainHand().isSimilar(this.npcManagementTool.getTool())) {
                npc.onNpcManageToolInteract(event);
                return;
            }

            npc.onNPCInteract(event);
        }
    }

    public NpcManagementTool getNpcManagementTool() {
        return npcManagementTool;
    }
}
