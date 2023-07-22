/*
 * Copyright © Wynntils 2023.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynntils.models.containers.type;

import com.wynntils.utils.mc.type.Location;

public record MythicFind(
        String itemName, int chestCount, int dryCount, int dryBoxes, long timestamp, Location chestCoordinate) {}