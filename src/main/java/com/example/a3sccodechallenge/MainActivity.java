package com.example.a3sccodechallenge;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutionException;

import com.example.a3sccodechallenge.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity
{

    private ActivityMainBinding binding;
    private ListView pokemonListView;

    private PokemonData[] pokemonData;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchPokemon();
        pokemonListView = findViewById(R.id.pokemon_list);
        if(pokemonListView == null) System.out.println("thing");

        ArrayAdapter<PokemonData> adapter = new ArrayAdapter<PokemonData>(this, android.R.layout.simple_list_item_1, pokemonData);
        pokemonListView.setAdapter(adapter);
    }

    private void searchPokemon()
    {

        String response = null;
        try
        {
            response = new HttpGetAsync().execute("https://pokeapi.co/api/v2/pokemon/").get();
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        String[] results = JsonHelper.parseJson(new String[] {"count", "next", "previous", "results"}, response);

        int index = 0;
        String pokemon = results[3];

        pokemonData = new PokemonData[20];
        for(int i=0;i<pokemonData.length;i++)
        {
            int startIndex = pokemon.indexOf('{', index);
            int endIndex = pokemon.indexOf('}', startIndex);
            index = endIndex +1;
            pokemonData[i] = new PokemonData(pokemon.substring(startIndex, endIndex));
        }
    }




}