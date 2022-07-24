package com.example.a3sccodechallenge;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.util.HashMap;

public class ImageCache
{
    private static final int maxCacheSize = 100;
    private HashMap<PokemonData, Bitmap> map = new HashMap<>();
    private final ImageView imageView;

    public ImageCache(ImageView imageView)
    {
        this.imageView = imageView;
    }

    public void getImage(PokemonData data)
    {
        if(map.size() >= maxCacheSize) map.clear();
        HttpImageGetAsync imageGetter = new HttpImageGetAsync(imageView, map);
        imageGetter.execute(data);
    }
}
