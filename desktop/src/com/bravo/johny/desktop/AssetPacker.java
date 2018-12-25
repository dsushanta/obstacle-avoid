package com.bravo.johny.desktop;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

/**
 * Created by bittu on 22,December,2018
 */
public class AssetPacker {

    private static final boolean DRAW_DEBUG_OUTLINE = false;
    private static final String RAW_ASSET_PATH = "desktop/assets-raw";
    private static final String ASSETS_PATH = "android/assets";

    public static void main(String[] args) {
        TexturePacker.Settings settings = new TexturePacker.Settings();
        settings.debug = DRAW_DEBUG_OUTLINE;

        TexturePacker.process(settings,
                RAW_ASSET_PATH + "/gameplay",
                ASSETS_PATH + "/gameplay",
                "gameplay");

        TexturePacker.process(settings,
                RAW_ASSET_PATH + "/ui",
                ASSETS_PATH + "/ui",
                "ui");

        TexturePacker.process(settings,
                RAW_ASSET_PATH + "/skin",
                ASSETS_PATH + "/ui",
                "uiskin");
    }
}
