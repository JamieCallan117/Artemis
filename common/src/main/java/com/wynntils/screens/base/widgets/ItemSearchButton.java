/*
 * Copyright © Wynntils 2024.
 * This file is released under LGPLv3. See LICENSE for full license details.
 */
package com.wynntils.screens.base.widgets;

import com.wynntils.screens.itemfilter.ItemFilterScreen;
import com.wynntils.utils.mc.McUtils;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ItemSearchButton extends WynntilsButton {
    private final SearchWidget searchWidget;
    private final Screen previousScreen;
    private final boolean supportsSorting;

    public ItemSearchButton(int x, int y, SearchWidget searchWidget, Screen previousScreen, boolean supportsSorting) {
        super(x, y, 20, 20, Component.literal("✎"));
        this.searchWidget = searchWidget;
        this.previousScreen = previousScreen;
        this.supportsSorting = supportsSorting;
        this.setTooltip(Tooltip.create(Component.translatable("screens.wynntils.itemSearchButton.tooltip")));
    }

    @Override
    public void onPress() {
        McUtils.mc().setScreen(ItemFilterScreen.create(searchWidget, previousScreen, supportsSorting));
    }
}