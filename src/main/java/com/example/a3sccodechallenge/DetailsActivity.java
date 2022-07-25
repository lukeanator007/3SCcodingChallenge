package com.example.a3sccodechallenge;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailsActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        TextView statTextView= findViewById(R.id.details_text);
        TextView abilitiesTextView= findViewById(R.id.abilities_text);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            StringBuilder statsStringBuilder = new StringBuilder();
            StringBuilder abilitiesStringBuilder = new StringBuilder();

            String stats = extras.getString("stats");
            String abilities = extras.getString("abilities");

            String[] statsArray = stats.split("\\},\\{");

            for(int i = 0;i<statsArray.length;i++)
            {
                String[] statData = JsonHelper.parseJson(new String[] {"base_stat", "stat"}, statsArray[i]);
                String statName = JsonHelper.parseJson(new String[] {"name"}, statData[1])[0];

                statName = JsonHelper.parseString(statName);
                statName = statName.replace('-', ' ');
                int statValue = Integer.parseInt(statData[0]);

                statsStringBuilder.append(statName);
                statsStringBuilder.append(": ");
                statsStringBuilder.append(statValue);
                statsStringBuilder.append("\n");
            }
            statTextView.setText(statsStringBuilder.toString());

            String[] abilitiesArray =  abilities.split("\\},\\{");

            for(int i = 0;i<abilitiesArray.length;i++)
            {
                String[] abilityData = JsonHelper.parseJson(new String[] {"ability", "is_hidden"}, abilitiesArray[i]);

                String abilityName = JsonHelper.parseJson(new String[] {"name"}, abilityData[0])[0];
                abilityName = JsonHelper.parseString(abilityName);
                abilitiesStringBuilder.append(abilityName);
                boolean isHidden = Boolean.parseBoolean(abilityData[1]);

                if(isHidden) abilitiesStringBuilder.append(" (hidden)");
                abilitiesStringBuilder.append("\n");
            }
            abilitiesTextView.setText(abilitiesStringBuilder.toString());
        }
    }
}
