package com.example.a3sccodechallenge;

import android.widget.ImageView;

public class ImageCache
{
    private final ImageView imageView;

    public ImageCache(ImageView imageView)
    {
        this.imageView = imageView;
    }

    public void getImage(PokemonData data)
    {
        HttpImageGetAsync imageGetter = new HttpImageGetAsync(imageView);
        imageGetter.execute(data);
    }
}
