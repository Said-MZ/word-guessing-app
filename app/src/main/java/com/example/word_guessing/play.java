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

    // get colors (not taken in class)
    int primary;
    int lightGreen;
    //--------------------------------

    // 2 arrays with words and thier hints
    String words[] = {"Cat" ,"Pen" , "Dog","Sun","Box","Cup","Pen","Car","Hat","Egg","Bed","Fan","Toy","Ant","Leg","Lip","Cow","Bus","Jam","Key","Pen","Bus","Sky","Arm","Bee","Box","Egg","Fly","Ice","Map"};
    String hints[] = {"A furry pet","writing tool","Man's best friend", "Bright in the sky", "A container", "Holds liquids", "Writes on paper", "Vehicle on the road", "Worn on the head", "Breakfast food", "Where you sleep", "Cools you down", "Plaything for kids", "Tiny insect", "Part of the body", "Surrounds the mouth", "Farm animal", "Public transportation", "Fruit spread", "Unlocks doors", "Writing instrument", "Public transport", "Where clouds float", "Part of the body", "Insect that stings", "Container", "Breakfast item", "Insect that flies", "Frozen water", "Shows directions"};

    // random number to get a random word and its hint
    int randomNumber = (int) (Math.random() * 31);

    String word = words[randomNumber];
    String hint = hints[randomNumber];

    // get the 3 letters containers
    TextView firstLetterContainer;
    TextView secondLetterContainer;
    TextView thirdLetterContainer;

    TextView hintContainer;


    // the + "" to avoid error, the error occurs because the charAt returns a char not a string
    String letter1 = word.charAt(0) + "";
    String letter2 = word.charAt(1) + "";
    String letter3 = word.charAt(2) + "";

    // get the input
    EditText letterInput;

    // get the button to disable and enable it
    Button btn;

    //attempt container and counter
    TextView attemptsContainer;
    int attemptsCounter = 10;


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
        letterInput.addTextChangedListener(watcher);

        btn = findViewById(R.id.button);

        attemptsContainer = findViewById(R.id.attemptContainer);
        //-----------------------------------
        primary =  getColor(R.color.primary);
        lightGreen = getColor(R.color.lightGreen);
        //----------------------------------
        attemptsContainer.setText(word);
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
        boolean isRightGuess = letterInput.getText().toString().equalsIgnoreCase(letter1) ||
                               letterInput.getText().toString().equalsIgnoreCase(letter2) ||
                               letterInput.getText().toString().equalsIgnoreCase(letter3);

        String rightChar =  letterInput.getText().toString().equalsIgnoreCase(letter1) ? letter1:
                            letterInput.getText().toString().equalsIgnoreCase(letter2) ? letter2 :  letter3;
        int indexOfRightChar = word.indexOf(rightChar);
        if(isRightGuess){
            if (indexOfRightChar == 0) {
                firstLetterContainer.setText(rightChar.toUpperCase());
                firstLetterContainer.setBackgroundColor(primary);
            } else if (indexOfRightChar == 1) {
                    secondLetterContainer.setText(rightChar.toUpperCase());
                    secondLetterContainer.setBackgroundColor(primary);
            } else {
                    thirdLetterContainer.setText(rightChar.toUpperCase());
                    thirdLetterContainer.setBackgroundColor(primary);
            }
        } else{
            attemptsCounter--;
            if(attemptsCounter < 6){
                hintContainer.setText("hint: " + hint);
            }
            if(attemptsCounter == 0){
                // lost
            }
            attemptsContainer.setText(word + " " + attemptsCounter);
        }



    }
}