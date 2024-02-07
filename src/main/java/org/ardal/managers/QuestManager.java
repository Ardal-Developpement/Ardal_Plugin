package org.ardal.managers;

import org.ardal.Ardal;
import org.ardal.api.commands.ArdalCmdManager;
import org.ardal.commands.BaseCmdAlias;
import org.ardal.commands.quests.*;
import org.ardal.db.QuestDB;

public class QuestManager extends ArdalCmdManager {
    private final QuestDB questDB;

    public QuestManager(Ardal plugin){
        super(plugin, BaseCmdAlias.BASE_QUEST_CMD_ALIAS);

        this.registerCmd(new AddQuest());
        this.registerCmd(new GetQuestBook());
        this.registerCmd(new ListQuest());
        this.registerCmd(new OpenQuestGUI());
        this.registerCmd(new RemoveQuest());
        this.registerCmd(new SetStateQuest());

        this.questDB = new QuestDB(plugin.getDataFolder().toPath().toAbsolutePath());
    }

    public QuestDB getQuestDB() {
        return questDB;
    }
}
