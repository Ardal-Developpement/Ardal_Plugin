package org.ardal.api.entities.npc.type;

public interface QuestNpcInfo {
    /**
     * Return the number of quests that the quest npc can show at most
     *
     * @return number of quest to show at most
     */
    int getNbQuestShow();

    /**
     * Set the number of quests that the quest npc can show at most
     *
     * @param nbQuestShow number of quest to show at most
     * @return true on success
     */
    boolean setNbQuestShow(int nbQuestShow);
}
