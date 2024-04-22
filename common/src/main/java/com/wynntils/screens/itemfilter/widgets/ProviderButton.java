/*
 * Copyright © Wynntils 2024.
 * This file is released under LGPLv3. See LICENSE for full license details.
 */
package com.wynntils.screens.itemfilter.widgets;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import com.wynntils.core.text.StyledText;
import com.wynntils.screens.base.widgets.WynntilsButton;
import com.wynntils.screens.itemfilter.ItemFilterScreen;
import com.wynntils.services.itemfilter.filters.AnyStatFilters;
import com.wynntils.services.itemfilter.type.ItemStatProvider;
import com.wynntils.services.itemfilter.type.SortDirection;
import com.wynntils.services.itemfilter.type.SortInfo;
import com.wynntils.services.itemfilter.type.StatProviderAndFilterPair;
import com.wynntils.utils.colors.CommonColors;
import com.wynntils.utils.colors.CustomColor;
import com.wynntils.utils.mc.McUtils;
import com.wynntils.utils.render.FontRenderer;
import com.wynntils.utils.render.RenderUtils;
import com.wynntils.utils.render.type.HorizontalAlignment;
import com.wynntils.utils.render.type.TextShadow;
import com.wynntils.utils.render.type.VerticalAlignment;
import java.util.List;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

public class ProviderButton extends WynntilsButton {
    private static final CustomColor DISABLED_COLOR = new CustomColor(120, 0, 0, 255);
    private static final CustomColor DISABLED_COLOR_BORDER = new CustomColor(255, 0, 0, 255);
    private static final CustomColor ENABLED_COLOR = new CustomColor(0, 116, 0, 255);
    private static final CustomColor ENABLED_COLOR_BORDER = new CustomColor(0, 220, 0, 255);

    private final ItemFilterScreen filterScreen;
    private final float translationX;
    private final float translationY;
    private final ItemStatProvider<?> provider;
    private final List<Component> tooltip;

    public ProviderButton(
            int x,
            int y,
            int width,
            int height,
            ItemFilterScreen filterScreen,
            ItemStatProvider<?> provider,
            float translationX,
            float translationY) {
        super(x, y, width, height, Component.literal(provider.getTranslatedName()));
        this.filterScreen = filterScreen;
        this.translationX = translationX;
        this.translationY = translationY;
        this.provider = provider;

        // Boolean is currently the only stat type to not support "any" so don't
        // add the tooltip mentioning it
        if (provider.getType().equals(Boolean.class)) {
            this.tooltip = List.of(
                    Component.literal(provider.getDescription()),
                    Component.translatable("screens.wynntils.itemFilter.providerHelp2"));
        } else {
            this.tooltip = List.of(
                    Component.literal(provider.getDescription()),
                    Component.translatable("screens.wynntils.itemFilter.providerHelp1"),
                    Component.translatable("screens.wynntils.itemFilter.providerHelp2"));
        }
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        PoseStack poseStack = guiGraphics.pose();

        RenderUtils.drawRect(poseStack, getRectColor().withAlpha(100), getX(), getY(), 0, width, height);

        RenderUtils.drawRectBorders(poseStack, getBorderColor(), getX(), getY(), getX() + width, getY() + height, 1, 2);

        FontRenderer.getInstance()
                .renderScrollingString(
                        poseStack,
                        StyledText.fromString(provider.getDisplayName()),
                        getX() + 2,
                        getY() + (height / 2f),
                        width - 4,
                        translationX,
                        translationY,
                        CommonColors.WHITE,
                        HorizontalAlignment.LEFT,
                        VerticalAlignment.MIDDLE,
                        TextShadow.NORMAL,
                        1.0f);

        if (this.isHovered) {
            McUtils.mc().screen.setTooltipForNextRenderPass(Lists.transform(tooltip, Component::getVisualOrderText));
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (McUtils.mc().screen instanceof ItemFilterScreen itemFilterScreen) {
            if (itemFilterScreen.inSortMode()) {
                if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
                    itemFilterScreen.addSort(new SortInfo(SortDirection.ASCENDING, provider));
                } else if (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
                    itemFilterScreen.removeSort(provider);
                }
            }

            // Boolean does not support the any filter
            if (button == GLFW.GLFW_MOUSE_BUTTON_MIDDLE && !provider.getType().equals(Boolean.class)) {
                // Technically this should use the any stat filter for the correct type but this works
                filterScreen.setFiltersForProvider(
                        provider,
                        List.of(new StatProviderAndFilterPair(provider, new AnyStatFilters.AnyStringStatFilter())));
            }
        }

        return true;
    }

    @Override
    public void onPress() {}

    private CustomColor getRectColor() {
        return filterScreen.isProviderInUse(provider) ? ENABLED_COLOR_BORDER : DISABLED_COLOR_BORDER;
    }

    private CustomColor getBorderColor() {
        return filterScreen.isProviderInUse(provider) ? ENABLED_COLOR : DISABLED_COLOR;
    }
}