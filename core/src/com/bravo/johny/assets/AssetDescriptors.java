package com.bravo.johny.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * Created by bittu on 21,December,2018
 */
public class AssetDescriptors {

    public static final AssetDescriptor<BitmapFont> FONT =
            new AssetDescriptor<BitmapFont>(AssetPaths.UI_FONT, BitmapFont.class);
    public static final AssetDescriptor<TextureAtlas> GAME_PLAY =
            new AssetDescriptor<TextureAtlas>(AssetPaths.GAME_PLAY, TextureAtlas.class);
    /*public static final AssetDescriptor<Texture> PLAYER =
            new AssetDescriptor<Texture>(AssetPaths.PLAYER, Texture.class);
    public static final AssetDescriptor<Texture> OBSTACLE =
            new AssetDescriptor<Texture>(AssetPaths.OBSTACLE, Texture.class);
    public static final AssetDescriptor<Texture> BACKGROUND =
            new AssetDescriptor<Texture>(AssetPaths.BACKGROUND, Texture.class);*/
    private AssetDescriptors() { }
}
