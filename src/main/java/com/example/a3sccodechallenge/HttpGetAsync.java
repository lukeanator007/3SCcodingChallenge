package com.example.a3sccodechallenge;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;

public class HttpGetAsync extends AsyncTask<String, Void, String>
{
    private Consumer<String> onPostExecute;
    public HttpGetAsync(Consumer<String> onPostExecute)
    {
        this.onPostExecute = onPostExecute;
    }

    @Override
    protected String doInBackground(String... strings)
    {
        String result = null;
        HttpURLConnection urlConnection = null;
        URL url = null;
        try
        {
            url = new URL(strings[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream response = new BufferedInputStream(urlConnection.getInputStream());
            Scanner scanner = new Scanner(response);
            result = scanner.useDelimiter("\\A").next();
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
            System.out.println(9);
        }

        return result;
    }

    @Override
    protected void onPostExecute(String result)
    {
        this.onPostExecute.accept(result);
    }
}
