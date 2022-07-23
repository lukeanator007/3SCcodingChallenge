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
import java.util.Scanner;

public class HttpImageGetAsync extends AsyncTask<PokemonData, Void, Bitmap>
{
        ImageView bmImage;

    public HttpImageGetAsync(ImageView bmImage)
    {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(PokemonData... pokemon)
    {
        URL imageURL = null;
        HttpURLConnection urlConnection = null;
        try
        {
            URL url = pokemon[0].getUrl();
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream response = new BufferedInputStream(urlConnection.getInputStream());
            Scanner scanner = new Scanner(response);
            String pokemonJson = scanner.useDelimiter("\\A").next();

            String[] pokemonSpritesJson = JsonHelper.parseJson(new String[] {"sprites"}, pokemonJson);
            String[] pokemonImageJson = JsonHelper.parseJson(new String[] {"front_default"}, pokemonSpritesJson[0]);
            imageURL = new URL(JsonHelper.parseString(pokemonImageJson[0]));
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
        return image;
    }

    protected void onPostExecute(Bitmap result)
    {
        bmImage.setImageBitmap(result);
    }
}
