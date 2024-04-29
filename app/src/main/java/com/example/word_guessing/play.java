package com.example.word_guessing;

import android.annotation.SuppressLint;
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



public class play extends AppCompatActivity {

    // colors variables
    int primary;
    int lightGreen;
    int red;
    //--------------------------------

    // 2 arrays with words with their hints
    String words[] = {"Cat", "Pen", "Dog", "Sun", "Box", "Cup", "Pen", "Car", "Hat", "Egg", "Bed", "Fan", "Toy", "Ant", "Leg", "Lip", "Cow", "Bus", "Jam", "Key", "Pen", "Bus", "Sky", "Arm", "Bee", "Box", "Egg", "Fly", "Ice", "Map"};
    String hints[] = {"A furry pet", "Writing tool", "Man's best friend", "Bright in the sky", "A container", "Holds liquids", "Writes on paper", "Vehicle on the road", "Worn on the head", "Breakfast food", "Where you sleep", "Cools you down", "Plaything for kids", "Tiny insect", "Part of the body", "Surrounds the mouth", "Farm animal", "Public transportation", "Fruit spread", "Unlocks doors", "Writing instrument", "Public transport", "Where clouds float", "Part of the body", "Insect that stings", "Container", "Breakfast item", "Insect that flies", "Frozen water", "Shows directions"};

    // random number to get a random word and its hint
    int randomNumber = (int) (Math.random() * words.length );

    String word = words[randomNumber];
    String hint = hints[randomNumber];

    // get the 3 letters containers to update UI
    TextView firstLetterContainer;
    TextView secondLetterContainer;
    TextView thirdLetterContainer;

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
        setContentView(R.layout.activity_play);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        firstLetterContainer = findViewById(R.id.firstLetterContainer);
        secondLetterContainer = findViewById(R.id.secondLetterContainer);
        thirdLetterContainer = findViewById(R.id.thirdLetterContainer);

        hintContainer = findViewById(R.id.hintContainer);

        letterInput = findViewById(R.id.letterInput);

        // watcher to disable/enable the button
        letterInput.addTextChangedListener(watcher);

        btn = findViewById(R.id.button);
        btn2 = findViewById(R.id.btn2);

        attemptsContainer = findViewById(R.id.attemptContainer);
        //-----------------------------------
        // get the colors from res/values/colors
        primary =  getColor(R.color.primary);
        lightGreen = getColor(R.color.lightGreen);
        red = getColor(R.color.red);
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
        boolean isRightGuess = letterInput.getText().toString().equalsIgnoreCase(letter1) ||
                               letterInput.getText().toString().equalsIgnoreCase(letter2) ||
                               letterInput.getText().toString().equalsIgnoreCase(letter3);

        if(isRightGuess){
            // check which letter is the right letter
            String rightChar = letterInput.getText().toString().equalsIgnoreCase(letter1) ?  letter1:
                               letterInput.getText().toString().equalsIgnoreCase(letter2) ? letter2:
                               letter3;

            // get the index of the letter
            int indexOfRightChar;
            // if letter is solved we don't get it's index to avoid redundancy errors
            if(!isLetter1Solved){
                indexOfRightChar = word.indexOf(rightChar);
            } else if (!isLetter2Solved) {
                indexOfRightChar = word.indexOf(rightChar,1);
                isLetter2Solved = true;
            } else{
                indexOfRightChar = word.lastIndexOf(rightChar);
            }

            // if the letter is the first or second we change the value of the boolean so
            // we don't face redundancy errors
            if(word.indexOf(rightChar) == 0){
                isLetter1Solved = true;
            } else if (word.indexOf(rightChar) == 1) {
                isLetter2Solved = true;
            }

            if (indexOfRightChar == 0) {
                firstLetterContainer.setText(rightChar.toUpperCase());
                firstLetterContainer.setBackgroundColor(primary);
                rightGuessesCounter++;
            } else if (indexOfRightChar == 1) {
                secondLetterContainer.setText(rightChar.toUpperCase());
                secondLetterContainer.setBackgroundColor(primary);
                rightGuessesCounter++;
            } else {
                thirdLetterContainer.setText(rightChar.toUpperCase());
                thirdLetterContainer.setBackgroundColor(primary);
                rightGuessesCounter++;
            }

            if(rightGuessesCounter > 2 ){
                btn2.setText("Next Level");
                btn2.setBackgroundColor(primary);
                btn2.setVisibility(View.VISIBLE);

                btn.setVisibility(View.INVISIBLE);

                attemptsContainer.setText("Congratulations, YOU WON!!!");
                attemptsContainer.setTextColor(primary);
                letterInput.setEnabled(false);
                letterInput.setHint("Click below to go to next level!");
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
               firstLetterContainer.setText(letter1);
               firstLetterContainer.setBackgroundColor(red);

               secondLetterContainer.setText(letter2);
               secondLetterContainer.setBackgroundColor(red);

               thirdLetterContainer.setText(letter3);
               thirdLetterContainer.setBackgroundColor(red);

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
}