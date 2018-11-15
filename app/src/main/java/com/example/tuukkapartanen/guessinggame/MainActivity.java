package com.example.tuukkapartanen.guessinggame;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText textGuess;
    private Button btnGuess;
    private TextView labelOutput;
    private int theNumber;
    private int range = 100;
    private TextView labelRange;

    public void checkGuess() {
        String guessText = textGuess.getText().toString();
        String message = "";
        try {
            int guess = Integer.parseInt(guessText);
            if (guess < theNumber) {
                message = guess + " is too low. Try again.";
            }
            else if (guess > theNumber) {
                message = guess + " is too high. Try again.";
            }
            else {
                message = guess + " is correct. You win! Let's play again!";
                newGame(); }
        } catch (Exception e) {
            message = "Enter a whole number between 1 and " + range + ".";
        } finally { labelOutput.setText(message);
            textGuess.requestFocus();
            textGuess.selectAll();
        }
    }

    public void newGame() {
        theNumber = (int)(Math.random() * range + 1);
        labelRange.setText("Enter a number between 1 and " + range + ".");
        textGuess.setText("" + range/2);
        textGuess.requestFocus();
        textGuess.selectAll();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textGuess = (EditText) findViewById(R.id.textGuess);
        btnGuess = (Button) findViewById(R.id.btnGuess);
        labelOutput = (TextView) findViewById(R.id.labelOutput);
        labelRange = (TextView) findViewById(R.id.textView2);
        newGame();
        btnGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkGuess();
            }
        });
        textGuess.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                checkGuess();
                return true; }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                final CharSequence[] items = {"1 to 10", "1 to 100", "1 to 1000"};
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Select the Range:");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch(item) {
                            case 0:
                                range = 10;
                                newGame();
                                break;
                            case 1:
                                range = 100;
                                newGame();
                                break;
                            case 2:
                                range = 1000;
                                newGame();
                                break; }
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                return true;
            case R.id.action_newgame: newGame();
                return true;
            case R.id.action_gamestats:
                return true;
            case R.id.action_about:
                AlertDialog aboutDialog = new AlertDialog.Builder(MainActivity.this).create();
                aboutDialog.setTitle("About Guessing Game");
                aboutDialog.setMessage("(c)2018 Tuukka Partanen.");
                aboutDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                }
                            }
                        );
                aboutDialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
