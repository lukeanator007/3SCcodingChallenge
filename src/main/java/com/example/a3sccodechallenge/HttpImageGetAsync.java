package com.example.a3sccodechallenge;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

public class HttpImageGetAsync extends AsyncTask<PokemonData, Void, Bitmap>
{
    private ImageView bmImage;
    private HashMap<PokemonData, Bitmap> map;

    public HttpImageGetAsync(ImageView bmImage, HashMap<PokemonData, Bitmap> map)
    {
        this.bmImage = bmImage;
        this.map = map;
    }

    protected Bitmap doInBackground(PokemonData... pokemon)
    {
        Bitmap cached = map.get(pokemon[0]);
        if(cached!=null) return cached;

        URL imageURL = null;
        HttpURLConnection urlConnection = null;
        try
        {
            URL url = pokemon[0].getUrl();
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream response = new BufferedInputStream(urlConnection.getInputStream());
            Scanner scanner = new Scanner(response);
            String pokemonJson = scanner.useDelimiter("\\A").next();

            String pokemonSpritesJson = JsonHelper.parseJson(new String[] {"sprites"}, pokemonJson)[0];
            String pokemonImageJson = null;

            if(pokemonImageJson == null)
            {
                String pokemonSpritesHomeJson = JsonHelper.parseJson(new String[] {"home"}, pokemonSpritesJson)[0];
                pokemonImageJson = JsonHelper.parseJson(new String[] {"front_default"}, pokemonSpritesHomeJson)[0];
                pokemonImageJson = JsonHelper.parseString(pokemonImageJson);
            }

            imageURL = new URL(pokemonImageJson);
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(urlConnection != null) urlConnection.disconnect();
        }

        Bitmap image = null;
        try
        {
            InputStream in = imageURL.openStream();
            image = BitmapFactory.decodeStream(in);
            in.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        map.put(pokemon[0], image);
        return image;
    }

    protected void onPostExecute(Bitmap result)
    {
        bmImage.setImageBitmap(result);
    }
}
