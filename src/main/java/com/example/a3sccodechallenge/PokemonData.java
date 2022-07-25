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

    public String getUrlString()
    {
        return this.url;
    }

    @Override
    public String toString()
    {
        return this.name;
    }

    @Override
    public int hashCode()
    {
        return this.name.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj.getClass().equals(this.getClass()))
        {
            PokemonData other = (PokemonData) obj;
            return other.name.equals(this.name);
        }
        return false;
    }
}
