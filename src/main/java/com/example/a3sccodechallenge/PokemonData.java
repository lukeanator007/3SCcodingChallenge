package com.example.a3sccodechallenge;

public class PokemonData
{private String name;
    private String url;
    public PokemonData(String json)
    {
        String[] data = JsonHelper.parseJson(new String[] {"name", "url"}, json);

        name = JsonHelper.parseString(data[0]);
        url = JsonHelper.parseString(data[1]);
    }

    @Override
    public String toString()
    {
        return this.name;
    }
}
