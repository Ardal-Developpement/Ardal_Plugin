package org.ardal.exceptions;

public class NpcNotFound extends Exception {
    public NpcNotFound() {
        super("Npc not found in the database.");
    }
}
