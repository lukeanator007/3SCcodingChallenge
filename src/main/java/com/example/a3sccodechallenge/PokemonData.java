package com.example.a3sccodechallenge;

import java.net.MalformedURLException;
import java.net.URL;

public class PokemonData
{
    private String name;
    private String url;
    public PokemonData(String json)
    {
        String[] data = JsonHelper.parseJson(new String[] {"name", "url"}, json);

        name = JsonHelper.parseString(data[0]);
        url = JsonHelper.parseString(data[1]);
    }

    public URL getUrl() throws MalformedURLException
    {
        return new URL(url);

    }

    @Override
    public String toString()
    {
        return this.name;
    }
}
