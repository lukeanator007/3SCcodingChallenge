package com.example.a3sccodechallenge;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutionException;

import com.example.a3sccodechallenge.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity
{

    private ActivityMainBinding binding;
    private ListView pokemonListView;
    private ImageCache imageCache;
    private String nextUrl;
    private String backUrl;
    private Button backButton;
    private Button nextButton;

    private PokemonData[] pokemonData;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backButton = findViewById(R.id.back_button);
        nextButton = findViewById(R.id.next_button);
        pokemonListView = findViewById(R.id.pokemon_list);
        imageCache = new ImageCache(findViewById(R.id.pokemon_sprite));

        pokemonListView.setOnItemClickListener((adapterView, view, position, l) -> imageCache.getImage(pokemonData[position]));

        nextButton.setOnClickListener((view) -> searchPokemon(nextUrl));
        backButton.setOnClickListener((view) -> searchPokemon(backUrl));

        searchPokemon("https://pokeapi.co/api/v2/pokemon/");


    }

    private void searchPokemon(String url)
    {
        String response = null;
        try
        {
            response = new HttpGetAsync().execute(url).get();
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
        this.nextUrl = JsonHelper.parseString(results[1]);
        this.backUrl = JsonHelper.parseString(results[2]);

        nextButton.setEnabled(nextUrl != null);
        backButton.setEnabled(backUrl != null);

        pokemonData = new PokemonData[20];
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






