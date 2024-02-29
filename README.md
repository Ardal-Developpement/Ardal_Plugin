# Ardal Plugin:

This plugin provides management for Ardal ecosystem.

## Usage:

### Quest command usage:
Base command: `/quest`.

- `add`: add a new quest (with quest book in main hand).
- `edit`:
  - `itemsRequest [quest name]`: edit items request by the quest.
  - `itemsReward [quest name]`: edit items reward by the quest.
  - `questBook`: edit the quest book of the quest. (NB: the book must have the same names of the quest!)
- `give`:
  - `itemsRequest [player] [quest name]`: give to the player the items request by the quest.
  - `itemsReward [player] [quest name]`: give to the player the items reward by the quest.
  - `questBook [player] [quest name]`: give to the player the quest book of the quest.
- `help`: print the quest command help.
- `list`: list all the quest registered.
- `npc`:
  
- `open`: open an interface with all quest book.
- `remove [quest name]`: remove the quest.
- `set`:
  - `isActive [bool] [quest name]`: set statue of a quest activity.


### Player info command usage:
Base command: `/ardal`.

- `add`
  - `activeQuest [player]`: add an active quest to the player.
  - `finishedQuest [player]`: add a finished quest to the player.
- `get`:
  - `adventureLevel [@Nullable player]`: get the adventure level of the player (or of itself).
- `list`:
  - `activeQuest [@Nullable player]`: get the list of active quest of the player.
  - `finishedQuest [@Nullable player]`: get the list of finished quest of the player.
- `remove`
  - `activeQuest [player]`: remove an active quest to the player.
  - `finishedQuest [player]`: remove a finished quest to the player.


### Npc command usage:
Base command: `/npc`.
- `create [type] [name]`: create a new npc.
- `give`
  - `managementTool [@Nullable player]`: give npc management tool.


### Shortcut commands usage:
- `/ec [@Nullable player]`: open enderchest of the player (or of itself).
- `/adventureLevel [@Nullable player]`: get the adventure level of the player (or of itself).


## Database:

You will find the quest database file in your plugin folder at: `/Ardal_Plugin/`

## License:

This project is licensed under the MIT licence. You can consult the complete text of the licence in the file [LICENSE](LICENSE).
