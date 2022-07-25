package com.example.a3sccodechallenge;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;

import com.example.a3sccodechallenge.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity
{
    private ListView pokemonListView;
    private ImageCache imageCache;
    private String nextUrl;
    private String backUrl;
    private Button backButton;
    private Button nextButton;
    private Button detailsButton;

    private PokemonData[] pokemonData;
    private int selectedPokemonIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backButton = findViewById(R.id.back_button);
        nextButton = findViewById(R.id.next_button);
        detailsButton = findViewById(R.id.details_button);
        pokemonListView = findViewById(R.id.pokemon_list);
        imageCache = new ImageCache(findViewById(R.id.pokemon_sprite));

        pokemonListView.setOnItemClickListener
        (
            (adapterView, view, position, l) ->
            {
                imageCache.getImage(pokemonData[position]);
                selectedPokemonIndex = position;
            }
        );

        nextButton.setOnClickListener((view) -> searchPokemon(nextUrl));
        backButton.setOnClickListener((view) -> searchPokemon(backUrl));
        detailsButton.setOnClickListener((view) -> searchPokemonStats());

        searchPokemon("https://pokeapi.co/api/v2/pokemon/");
    }

    private void searchPokemonStats()
    {
        PokemonData data = pokemonData[selectedPokemonIndex];
        new HttpGetAsync((String str) -> onSearchPokemonStats(str)).execute(data.getUrlString());
    }

    private void onSearchPokemonStats(String response)
    {
        String[] statsAndAbilityArray = JsonHelper.parseJson(new String[] {"stats", "abilities"}, response);String value="Hello world";
        Intent i = new Intent(this, DetailsActivity.class);
        i.putExtra("stats",statsAndAbilityArray[0]);
        i.putExtra("abilities",statsAndAbilityArray[1]);
        startActivity(i);
    }

    private void searchPokemon(String url)
    {
        new HttpGetAsync((String str) -> onSearchPokemon(str)).execute(url);
    }

    private void onSearchPokemon(String response)
    {
        String[] results = JsonHelper.parseJson(new String[] {"count", "next", "previous", "results"}, response);

        int index = 0;
        String pokemon = results[3];
        this.nextUrl = JsonHelper.parseString(results[1]);
        this.backUrl = JsonHelper.parseString(results[2]);

        nextButton.setEnabled(nextUrl != null);
        backButton.setEnabled(backUrl != null);

        pokemonData = new PokemonData[21];
        for(int i=0;i<pokemonData.length;i++)
        {
            int startIndex = pokemon.indexOf('{', index);
            if(startIndex == -1)
            {
                PokemonData[] newData =  new PokemonData[i];
                for(int j = 0;j<i;j++)
                {
                    newData[j] = pokemonData[j];
                }
                pokemonData = newData;
                break;
            }
            int endIndex = pokemon.indexOf('}', startIndex);
            index = endIndex +1;
            pokemonData[i] = new PokemonData(pokemon.substring(startIndex, endIndex));
        }
        ArrayAdapter<PokemonData> adapter = new ArrayAdapter<PokemonData>(this, android.R.layout.simple_list_item_1, pokemonData);
        pokemonListView.setAdapter(adapter);
        pokemonListView.performItemClick(pokemonListView, 0, 1);
    }
}






