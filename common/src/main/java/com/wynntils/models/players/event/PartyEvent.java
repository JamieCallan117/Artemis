/*
 * Copyright © Wynntils 2023.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynntils.models.players.event;

import net.minecraftforge.eventbus.api.Event;

/**
 * These events correspond to data from PartyModel
 */
public abstract class PartyEvent extends Event {

    /**
     * Fired upon obtaining a new party list.
     * Get the party list from the party model manually if required.
     */
    public static class Listed extends PartyEvent {}

    /**
     * Fired upon someone else joining the user's party
     * @field playerName the name of the player who joined
     */
    public static class OtherJoined extends PartyEvent {
        private final String playerName;

        public OtherJoined(String playerName) {
            this.playerName = playerName;
        }

        public String getPlayerName() {
            return playerName;
        }
    }

    /**
     * Fired upon someone else leaving the user's party
     * @field playerName the name of the player who left
     */
    public static class OtherLeft extends PartyEvent {
        private final String playerName;

        public OtherLeft(String playerName) {
            this.playerName = playerName;
        }

        public String getPlayerName() {
            return playerName;
        }
    }

    /**
     * Fired upon a party member disconnecting
     * @field playerName the name of the player who disconnected
     */
    public static class OtherDisconnected extends PartyEvent {
        private final String playerName;

        public OtherDisconnected(String playerName) {
            this.playerName = playerName;
        }

        public String getPlayerName() {
            return playerName;
        }
    }

    /**
     * Fired upon a party member reconnecting
     */
    public static class OtherReconnected extends PartyEvent {
        private final String playerName;

        public OtherReconnected(String playerName) {
            this.playerName = playerName;
        }

        public String getPlayerName() {
            return playerName;
        }
    }
}