package com.example.word_guessing;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Level2 extends AppCompatActivity {

    // colors variables
    int primary;
    int lightGreen;
    int red;
    int white;
    //--------------------------------

    // 2 arrays with words with their hints
    String words[] = {"Bear", "Cake", "Duck", "Moon", "Tree", "Rain", "Book", "Fish", "Hand", "Star", "Flag", "Desk", "Moon", "Lion", "Rain", "Ring", "Gate", "Ball", "Song", "Wind", "Kite", "Moon", "Bird", "Fish", "Tree", "Lake", "Shoe", "Moon", "Road"};
    String hints[] = {"Furry mammal", "Sweet dessert", "Quacking bird", "Shines at night", "Tall plant", "Water from the sky", "Reads words", "Swims in water", "Part of the arm", "Bright in the sky", "Waving symbol", "Study furniture", "Shines at night", "Wild cat", "Water from the sky", "Circular jewelry", "Entrance barrier", "Round object", "Melodic tune", "Moving air", "Flying toy", "Shines at night", "Flying animal", "Swims in water", "Tall plant", "Large water body", "Footwear", "Shines at night", "Pathway"};

    // random number to get a random word and its hint
    int randomNumber = (int) (Math.random() * words.length );

    String word = words[randomNumber];
    String hint = hints[randomNumber];

    // get the 3 letters containers to update UI
    TextView firstLetterContainer;
    TextView secondLetterContainer;
    TextView thirdLetterContainer;
    TextView fourthLetterContainer;

    // hint container, it will be hidden at start
    TextView hintContainer;

    // get second button
    Button btn2;

    // get each letter, with a boolean if it's solved for two reasons (1. to avoid error when some letters occur twice or more)
    //                                                                 2. to check if all letters is solved)
    // the + "" to avoid an error, the error occurs because the charAt returns a char not a string
    String letter1 = word.charAt(0) + "";
    boolean isLetter1Solved = false;
    String letter2 = word.charAt(1) + "";
    boolean isLetter2Solved = false;

    String letter3 = word.charAt(2) + "";
    boolean isLetter3Solved = false;

    String letter4 = word.charAt(3) + "";



    // get the user input
    EditText letterInput;

    // get the button to disable and enable it
    Button btn;

    //attempt container to update UI and counter to track attempts
    TextView attemptsContainer;
    int attemptsCounter = 10;
    int rightGuessesCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_level2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        firstLetterContainer = findViewById(R.id.firstLetterContainer);
        secondLetterContainer = findViewById(R.id.secondLetterContainer);
        thirdLetterContainer = findViewById(R.id.thirdLetterContainer);
        fourthLetterContainer = findViewById(R.id.fourthLetterContainer);

        hintContainer = findViewById(R.id.hintContainer);

        letterInput = findViewById(R.id.letterInput);

        // watcher to disable/enable the button
        letterInput.addTextChangedListener(watcher);

        btn = findViewById(R.id.htpButton);
        btn2 = findViewById(R.id.btn2);

        attemptsContainer = findViewById(R.id.attemptContainer);
        //-----------------------------------
        // get the colors from res/values/colors
        primary =  getColor(R.color.primary);
        lightGreen = getColor(R.color.lightGreen);
        red = getColor(R.color.red);
        white = getColor(R.color.white);
        //----------------------------------

        btn.setEnabled(false);
        btn.setBackgroundColor(lightGreen);
    }

    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(!letterInput.getText().toString().isEmpty()){
                btn.setEnabled(true);
                btn.setBackgroundColor(primary);
            } else{
                btn.setEnabled(false);
                btn.setBackgroundColor(lightGreen);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    @SuppressLint("ResourceAsColor")
    public void guess(View view) {
        // check if the user guess is one of the three letters and store the value in a boolean
        boolean isRightGuess = word.toLowerCase().contains(letterInput.getText().toString().toLowerCase());

        if(isRightGuess && !letterInput.getText().toString().equals("~")){
            // get right char from input
            String rightChar = letterInput.getText().toString();
            // get right char index
            int indexOfRightChar = word.toLowerCase().indexOf(rightChar.toLowerCase());

            if(indexOfRightChar == 0){
                firstLetterContainer.setText(rightChar.toUpperCase());
                firstLetterContainer.setBackgroundColor(primary);
                rightGuessesCounter++;
                word = "~" + word.substring(1,4);  // book => ~ook
            } else if (indexOfRightChar == 1) {
                secondLetterContainer.setText(rightChar.toUpperCase());
                secondLetterContainer.setBackgroundColor(primary);
                rightGuessesCounter++;
                word = word.substring(0,1) + "~" + word.substring(2);  // book => b~ok

            } else if (indexOfRightChar == 2){
                thirdLetterContainer.setText(rightChar.toUpperCase());
                thirdLetterContainer.setBackgroundColor(primary);
                rightGuessesCounter++;
                word = word.substring(0,2) + "~" + word.substring(3);  // book => bo~k
            } else{
                fourthLetterContainer.setText(rightChar.toUpperCase());
                fourthLetterContainer.setBackgroundColor(primary);
                rightGuessesCounter++;
                word = word.substring(0,3) + "~";  // boo => boo~
            }



            if(rightGuessesCounter > 3 ){
                btn2.setText("Home");
                btn2.setBackgroundColor(primary);
                btn2.setVisibility(View.VISIBLE);

                btn.setVisibility(View.INVISIBLE);

                attemptsContainer.setText("Congratulations, YOU WON!!!");
                attemptsContainer.setTextColor(primary);
                letterInput.setEnabled(false);
                letterInput.setHint("");
                hintContainer.setText("");
            }
        } else{

            // if user guessed wrong we subtract 1 from the counter
            attemptsCounter--;
            // if the user attempts remaining reached 5 we show a hint
            if(attemptsCounter < 6){
                hintContainer.setText("hint: " + hint);
            }
            if(attemptsCounter < 1){
                attemptsContainer.setText("You Lost!!!");

                firstLetterContainer.setText(letter1.toUpperCase());
                firstLetterContainer.setBackgroundColor(red);

                secondLetterContainer.setText(letter2.toUpperCase());
                secondLetterContainer.setBackgroundColor(red);

                thirdLetterContainer.setText(letter3.toUpperCase());
                thirdLetterContainer.setBackgroundColor(red);

                fourthLetterContainer.setText(letter4.toUpperCase());
                fourthLetterContainer.setBackgroundColor(red);

                btn2.setVisibility(View.VISIBLE);
                btn2.setText("Restart");
                btn2.setBackgroundColor(red);

                btn.setVisibility(View.INVISIBLE);
                attemptsContainer.setTextColor(red);
                letterInput.setEnabled(false);
                letterInput.setHint("Click below to restart!");
                letterInput.setHintTextColor(red);
                hintContainer.setText("");
            } else{
                attemptsContainer.setText( "You have: " + attemptsCounter + " attempts left!");
            }

        }
        //empty input after guessing for better UX
        letterInput.setText("");
    }

    public void nextEvent(View view) {
        if(btn2.getText().toString().equalsIgnoreCase("restart")){
            rightGuessesCounter = 0;
            attemptsCounter = 10;

            hintContainer.setText("");
            attemptsContainer.setText(attemptsCounter + " Attempts Left");
            firstLetterContainer.setText("");
            secondLetterContainer.setText("");
            thirdLetterContainer.setText("");
            fourthLetterContainer.setText("");

            btn2.setVisibility(View.INVISIBLE);
            btn.setVisibility(View.VISIBLE);

            letterInput.setEnabled(true);
            letterInput.setHintTextColor(lightGreen);

            firstLetterContainer.setBackgroundColor(lightGreen);
            secondLetterContainer.setBackgroundColor(lightGreen);
            thirdLetterContainer.setBackgroundColor(lightGreen);
            fourthLetterContainer.setBackgroundColor(lightGreen);

            attemptsContainer.setTextColor(white);

            randomNumber = (int) (Math.random() * words.length );

            word = words[randomNumber];
            hint = hints[randomNumber];

            letter1 = word.charAt(0) + "";
            isLetter1Solved = false;
            letter2 = word.charAt(1) + "";
            isLetter2Solved = false;
            letter3 = word.charAt(2) + "";
            isLetter3Solved = false;
            letter4 = word.charAt(3) + "";

        } else{
            Intent goHome = new Intent(Level2.this, MainActivity.class);
            startActivity(goHome);
        }
    }
}