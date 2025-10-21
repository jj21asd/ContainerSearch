package me.jjblock21.containersearch;

import eu.midnightdust.lib.config.MidnightConfig;

public class ConfigModel extends MidnightConfig {
    @Comment(centered = true) public static Comment userInterfaceHeader;
    @Entry public static Alignment panelAlignment = Alignment.MENU;
    @Entry public static int panelOffsetX = 0;
    @Entry public static int panelOffsetY = 0;
    @Entry(min = 1) public static int searchBarWidth = 120;

    @Comment(centered = true) public static Comment behaviorHeader;
    @Entry public static boolean searchInsideShulkers = true;
    @Entry public static boolean interpretNumbersAsLevels = true;

    public enum Alignment {
        MENU,
        TOP_OF_SCREEN
    }
}
