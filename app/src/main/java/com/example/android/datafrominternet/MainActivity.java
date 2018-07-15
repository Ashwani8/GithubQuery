/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.datafrominternet;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.datafrominternet.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    //  Create an EditText variable called mSearchBoxEditText
    private EditText mSearchBoxEditText ;

    //  Create a TextView variable called mUrlDisplayTextView
    private TextView mUrlDisplayTextView;
    //  Create a TextView variable called mSearchResultsTextView
    private TextView mSearchResultsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //  Use findViewById to get a reference to mSearchBoxEditText
        mSearchBoxEditText = (EditText) findViewById(R.id.et_search_box);

        //  Use findViewById to get a reference to mUrlDisplayTextView
        mSearchResultsTextView = (TextView) findViewById(R.id.tv_url_display);
        // Use findViewById to get a reference to mSearchResultsTextView
        mSearchResultsTextView = (TextView) findViewById(R.id.tv_github_search_result_json);
    }

    // Do 2 - 7 in menu.xml ///////////////////////////////////////////////////////////////////////
    // completed  (2) Create a menu xml called 'main.xml' in the res->menu folder
    // completed  (3) Add one menu item to your menu
    // completed  (4) Give the menu item an id of @+id/action_search
    // completed  (5) Set the orderInCategory to 1
    // completed  (6) Show this item if there is room (use app:showAsAction, not android:showAsAction)
    // completed  (7) Set the title to the search string ("Search") from strings.xml
    // Do 2 - 7 in menu.xml ///////////////////////////////////////////////////////////////////////

    // completed  (8) Override onCreateOptionsMenu
    // completed(9) Within onCreateOptionsMenu, use getMenuInflater().inflate to inflate the menu
    // completed(10) Return true to display your menu

    // complete 2.3.2 and .3  Create a method called makeGithubSearchQuery. Complete 2.3.3 with in
    // this method,build URL from the EditText and set the build URL to the TextView

    /**
     * this method retrieves the search text from the EditText, constructs the url (using
     * {@link NetworkUtils} ) for the github repository you would like to find, displays that URL in
     * a TextView.
     */
    private void makeGithubSearchQuery(){
                String githubQuery = mSearchBoxEditText.toString();
        URL githubSearchUrl = NetworkUtils.buildUrl(githubQuery);
        mUrlDisplayTextView.setText(githubSearchUrl.toString());

        // complete 2.4. (2) Call getResponseFromHttpUrl and display the results in mSearchResultsTextView
        // complete 2.4.(3) Surround the call to getResponseFromHttpUrl with a try / catch
        // block to catch an IOException
       /* No need for this sync call, use AsyncTask instead
        String githubSearchResults = null;
        try{
            githubSearchResults = NetworkUtils.getResponseFromHttpUrl(githubSearchUrl);
            mSearchResultsTextView.setText(githubSearchResults);
        } catch (IOException e){
            e.printStackTrace();
        }
        */
        // completed 2.5.(4) Create a new GithubQueryTask and call its execute method, passing in the url to query
        new GithubQueryTask().execute(githubSearchUrl);
    }
    //completed 2.5.(1) Create a class called GithubQueryTask that extends AsyncTask<URL, Void, String>
    public class GithubQueryTask extends AsyncTask<URL, Void, String>{
        // completed 2.5.(2) Override the doInBackground method to perform the query. Return the results.
        // (Hint: You've already written the code to perform the query)
        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String githubSearchResults = null;
            try{
                githubSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);

            }catch (IOException e){
                e.printStackTrace();

            }
            return githubSearchResults;
        }
        // completed 2.5.(3) Override onPostExecute to display the results in the TextView
        @Override
        protected void onPostExecute(String githubSearchResults) {
            if(githubSearchResults != null && !githubSearchResults.equals("")){
                mSearchResultsTextView.setText(githubSearchResults);
            }
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    // completed (11) Override onOptionsItemSelected
    // completed (12) Within onOptionsItemSelected, get the ID of the item that was selected
    // completed (13) If the item's ID is R.id.action_search, show a Toast and return true to tell Android that you've handled this menu click
    // completed(14) Don't forgot to call .show() on your Toast
    // completed (15) If you do NOT handle the menu click, return super.onOptionsItemSelected to let Android handle the menu click

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemThatWasSelected = item.getItemId();
        if (menuItemThatWasSelected == R.id.action_search){
            //Completed 2.3.4 remove Toast message when the search menue was clicked
            // completed 2.3.5 call makeGithubSerachQuery when the search menu item is clicked

            makeGithubSearchQuery();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
