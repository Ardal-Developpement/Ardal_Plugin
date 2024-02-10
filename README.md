# Ardal Plugin:

This plugin provides management for Ardal ecosystem.

## Usage:

### Quest command usage:
Base plugin command: `/quest`.

- `add`: add a new quest (with quest book in main hand).
- `edit`:
  - `itemsRequest [quest name]`: edit the items request by the quest.
  - `itemsReward [quest name]`: edit the items reward by the quest.
  - `questBook`: edit the quest book of the quest. (NB: the book must have the same names of the quest!)
- `give`:
  - `itemsRequest [player] [quest name]`: give to the player the items request by the quest.
  - `itemsReward [player] [quest name]`: give to the player the items reward by the quest.
  - `questBook [player] [quest name]`: give to the player the quest book of the quest.
- `help`: print the quest command help.
- `list`: list all the quest registered.
- `open`: open an interface with all quest book.
- `remove [quest name]`: remove the quest.
- `set`:
  - `isActive [bool] [quest name]`: set statue of a quest activity.

### Player info command usage:
Base plugin command: `/ardal`.

- `get`:
  - `adventureLevel [@Nullable player]`: get the adventure level of the player (or of itself) .


## Database:

You will find the quest database file in your plugin folder at: `/Ardal_Plugin/`

## License:

This project is licensed under the MIT licence. You can consult the complete text of the licence in the file [LICENSE](LICENSE).
