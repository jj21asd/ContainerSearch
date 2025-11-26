package me.jjblock21.containersearch;

import eu.midnightdust.lib.config.MidnightConfig;

public class CSConfig extends MidnightConfig {
    @Comment(centered = true) public static Comment behaviorHeader;
    @Entry public static boolean searchShulkers = true;
    @Entry public static boolean searchBundles = true;
    @Entry public static boolean interpretNumbersAsLevels = true;
    @Entry(min = 0) public static int minSlotCount = 27;

    @Comment(centered = true) public static Comment guiHeader;
    @Entry public static Alignment panelAlignment = Alignment.MENU;
    @Entry public static int panelOffsetX = 0;
    @Entry public static int panelOffsetY = 0;
    @Entry(min = 1) public static int searchBarWidth = 120;

    public enum Alignment {
        MENU,
        TOP_OF_SCREEN
    }
}
