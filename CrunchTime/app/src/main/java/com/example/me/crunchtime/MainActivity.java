package com.example.me.crunchtime;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.EditText;
import android.widget.RadioGroup;

public class MainActivity extends Activity implements AdapterView.OnItemSelectedListener {

    private static ImageView switchImage;
    private static Button buttonUpdate;
    private RadioButton reps;
    private RadioButton mins;
    private RadioButton goalBurn;
    private RadioButton workoutBurned;
    private EditText editText; //this is input reps/mins, attempting to change name messes everything up
    private EditText inputWeight;
    private EditText convCals;
    private RadioGroup type;
    private RadioGroup preOrPost;
    private EditText resultText;
    //private EditText displayText;
    private EditText repsMins;
    private Button resetButton;



    private int currentImageIndex;
    private int imageIndex = 0;
    int[] images = {R.drawable.push_up, R.drawable.sit_up, R.drawable.squats, R.drawable.leg_lift,
                    R.drawable.plank, R.drawable.jumping_jack, R.drawable.pull_up, R.drawable.cycling,
                    R.drawable.walking, R.drawable.jogging, R.drawable.swimming, R.drawable.stair_climbing};
    Spinner exercise;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        exercise = (Spinner) findViewById(R.id.exercise);
        switchImage = (ImageView) findViewById(R.id.image);
        type = (RadioGroup) findViewById(R.id.type);
        preOrPost = (RadioGroup) findViewById(R.id.preOrPost);
        repsMins = (EditText) findViewById(R.id.repsMins);
        editText = (EditText) findViewById(R.id.editText);
        inputWeight = (EditText) findViewById(R.id.weight);
        convCals = (EditText) findViewById(R.id.calBurn);
        resultText = (EditText) findViewById(R.id.result);

        //displayText = (EditText) findViewById(R.id.display);


        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.exercise, android.R.layout.simple_spinner_item);
        exercise.setAdapter(adapter);
        exercise.setOnItemSelectedListener(this);
        //spinnerPick();
        updateButtonClick();
        resetButtonClick();
    }

    public void resetButtonClick() {
        resetButton = (Button) findViewById(R.id.resetButton);
        resetButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //clear the radioGroups
                        type.clearCheck();
                        preOrPost.clearCheck();
                        //clear each text field
                        repsMins.setText("");
                        inputWeight.setText("");
                        convCals.setText("");
                        resultText.setText("");
                        Toast.makeText(MainActivity.this, "Reset all fields", Toast.LENGTH_SHORT).show();
                    }
                }
        );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TextView myText = (TextView) view;
        //Toast.makeText(this, "You selected"+Integer.toString(position), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "You selected "+myText.getText(), Toast.LENGTH_SHORT).show();
        imageIndex = position;
        switchImage.setImageResource(images[position]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void updateButtonClick() {


        //int preOrPostId = preOrPost.getCheckedRadioButtonId();
        /*if (type.getCheckedRadioButtonId() != -1) {
            typeID = type.getCheckedRadioButtonId()
        }*/
        //switchImage = (ImageView) findViewById(R.id.image);
        buttonUpdate = (Button) findViewById(R.id.updateButton);

        buttonUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String exerciseString = exercise.getSelectedItem().toString();
                        double multiplier = 1;
                        double constant = 25 / 350D;
                        double factor = 0;
                        //determine the multiplier
                        if (!inputWeight.getText().toString().equals("")) {
                            if (MainActivity.this.isValid(inputWeight.getText().toString())) {
                                int weight = (int) Double.parseDouble(inputWeight.getText().toString());
                                //get the weight
                                weight = weight / 10 * 10; //this is for rough estimate of weight, in tens
                                factor = (weight - 150) / 10; //get the factor to add to multiplier
                                multiplier = multiplier + (factor * constant); //get the multiplier

                                Toast.makeText(MainActivity.this, "weight is " + Double.toString(weight) + " and factor is " + Double.toString(factor) + " and constant is " + Double.toString(constant), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "Weight must be a number greater than 0", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        //gives which radio button has been selected
                        int typeID = type.getCheckedRadioButtonId();
                        int preOrPostID = preOrPost.getCheckedRadioButtonId();
                        String displayString = "";
                        //takes the radio button that is selected
                        RadioButton typeButton = (RadioButton) findViewById(typeID);
                        RadioButton preOrPostButton = (RadioButton) findViewById(preOrPostID);
                        if (typeID == -1 || preOrPostID == -1) {
                            Toast.makeText(MainActivity.this, "Both buttons must be selected to update", Toast.LENGTH_SHORT).show();
                        } else {
                            /*if (preOrPostButton.equals("Pre Workout")) {
                                try {
                                    double dub = (double) Integer.parseInt(convCals.getText().toString());
                                    if (dub < 0) {
                                        Toast.makeText(MainActivity.this, "cannot convert to negative calories", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                } catch(NumberFormatException e) {
                                    Toast.makeText(MainActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }*/
                            //default Reps exercise is Situp
                            //default Mins exercise is Walking
                            int withoutWeight = 0;
                            double reps;
                            double mins;
                            double toUpdate = 0;
                            double defaultBurnedReps;
                            double defaultBurnedMins;
                            double defaultToBurnReps;
                            double defaultToBurnMins;
                            if (preOrPostButton.getText().equals("Pre Workout")) {
                                if (convCals.getText().toString().equals("")) {
                                    Toast.makeText(MainActivity.this, "Must have input for calories burned goal", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } else if (preOrPostButton.getText().equals("Post Workout")) {
                                if (repsMins.getText().toString().equals("")) {
                                    Toast.makeText(MainActivity.this, "Must have input for reps/mins done", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }

                            //now check to see that inputs are valid
                            if (preOrPostButton.getText().equals("Pre Workout")) {
                                if (!MainActivity.isValid(convCals.getText().toString())) {
                                    Toast.makeText(MainActivity.this, "The input must be a number greater than 0", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } else if (preOrPostButton.getText().equals("Post Workout")) {
                                if (!MainActivity.isValid(repsMins.getText().toString())) {
                                    Toast.makeText(MainActivity.this, "The input must be a number greater than 0", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }


                            if (typeButton.getText().equals("Reps") && preOrPostButton.getText().equals("Pre Workout")) {
                                //take in exercise and convCals (calories), change value of editText (reps/mins)
                                defaultToBurnReps = (double) Integer.parseInt(convCals.getText().toString()) / .5;
                                displayString = "Reps";
                                if (exerciseString.equals("Pushup")) {
                                    toUpdate = defaultToBurnReps * 1.75;
                                } else if (exerciseString.equals("Situp")) {
                                    toUpdate = defaultToBurnReps;
                                } else if (exerciseString.equals("Squats")) {
                                    toUpdate = defaultToBurnReps * 1.125;
                                } else if (exerciseString.equals("Pullup")) {
                                    toUpdate = defaultToBurnReps * .5;
                                } else {
                                    toUpdate = defaultToBurnReps / 10;
                                    displayString = "Mins";
                                    if (exerciseString.equals("Leg Lift")) {
                                        toUpdate = toUpdate * 1.25;
                                    } else if (exerciseString.equals("Plank")) {
                                        toUpdate = toUpdate * 1.25;
                                    } else if (exerciseString.equals("Jumping Jacks")) {
                                        toUpdate = toUpdate * .5;
                                    } else if (exerciseString.equals("Cycling")) {
                                        toUpdate = toUpdate * .6;
                                    } else if (exerciseString.equals("Walking")) {
                                        toUpdate = toUpdate;
                                    } else if (exerciseString.equals("Jogging")) {
                                        toUpdate = toUpdate * .6;
                                    } else if (exerciseString.equals("Swimming")) {
                                        toUpdate = toUpdate * .65;
                                    } else if (exerciseString.equals("Stair Climbing")) {
                                        toUpdate = toUpdate * .75;
                                    }
                                }

                            } else if (typeButton.getText().equals("Reps") && preOrPostButton.getText().equals("Post Workout")) {
                                //take in exercise and editText (reps/mins), change value of withoutWeight (calories)
                                defaultBurnedReps = Integer.parseInt(repsMins.getText().toString()) * .5;
                                if (exerciseString.equals("Pushup")) {
                                    toUpdate = defaultBurnedReps / 1.75;
                                } else if (exerciseString.equals("Situp")) {
                                    toUpdate = defaultBurnedReps;
                                } else if (exerciseString.equals("Squats")) {
                                    toUpdate = defaultBurnedReps / 1.125;
                                } else if (exerciseString.equals("Pullup")) {
                                    toUpdate = defaultBurnedReps / .5;
                                } else {
                                    Toast.makeText(MainActivity.this, "cannot do reps of this exercise", Toast.LENGTH_SHORT).show();
                                    /*toUpdate = defaultBurnedReps / 10;
                                    if (exerciseString.equals("Leg Lift")) {
                                        toUpdate = toUpdate * 1.25;
                                    } else if (exerciseString.equals("Plank")) {
                                        toUpdate = toUpdate * 1.25;
                                    } else if (exerciseString.equals("Jumping Jacks")) {
                                        toUpdate = toUpdate * .5;
                                    } else if (exerciseString.equals("Cycling")) {
                                        toUpdate = toUpdate * .6;
                                    } else if (exerciseString.equals("Walking")) {
                                        toUpdate = toUpdate;
                                    } else if (exerciseString.equals("Jogging")) {
                                        toUpdate = toUpdate * .6;
                                    } else if (exerciseString.equals("Swimming")) {
                                        toUpdate = toUpdate * .65;
                                    } else if (exerciseString.equals("Stair Climbing")) {
                                        toUpdate = toUpdate * .75;
                                    }*/
                                }

                            } else if (typeButton.getText().equals("Minutes") && preOrPostButton.getText().equals("Pre Workout")) {
                                //take in exercise and convCals (calories), change value of editText (reps/mins)
                                defaultToBurnMins = (double) Integer.parseInt(convCals.getText().toString()) / 5;
                                displayString = "Mins";
                                if (exerciseString.equals("Leg Lift")) {
                                    toUpdate = defaultToBurnMins * 1.25;
                                } else if (exerciseString.equals("Plank")) {
                                    toUpdate = defaultToBurnMins * 1.25;
                                } else if (exerciseString.equals("Jumping Jacks")) {
                                    toUpdate = defaultToBurnMins * .5;
                                } else if (exerciseString.equals("Cycling")) {
                                    toUpdate = defaultToBurnMins * .6;
                                } else if (exerciseString.equals("Walking")) {
                                    toUpdate = defaultToBurnMins;
                                } else if (exerciseString.equals("Jogging")) {
                                    toUpdate = defaultToBurnMins * .6;
                                } else if (exerciseString.equals("Swimming")) {
                                    toUpdate = defaultToBurnMins * .65;
                                } else if (exerciseString.equals("Stair Climbing")) {
                                    toUpdate = defaultToBurnMins * .75;
                                } else {
                                    toUpdate = defaultToBurnMins * 10;
                                    displayString = "Reps";
                                    if (exerciseString.equals("Pushup")) {
                                        toUpdate = toUpdate * 1.75;
                                    } else if (exerciseString.equals("Situp")) {
                                        toUpdate = toUpdate;
                                    } else if (exerciseString.equals("Squats")) {
                                        toUpdate = toUpdate * 1.125;
                                    } else if (exerciseString.equals("Pullup")) {
                                        toUpdate = toUpdate * .5;
                                    }
                                }

                            } else if (typeButton.getText().equals("Minutes") && preOrPostButton.getText().equals("Post Workout")) {
                                //take in exercise and editText (reps/mins), change value of withoutWeight
                                defaultBurnedMins = (double) Integer.parseInt(repsMins.getText().toString()) * 5;
                                if (exerciseString.equals("Leg Lift")) {
                                    toUpdate = defaultBurnedMins / 1.25;
                                } else if (exerciseString.equals("Plank")) {
                                    toUpdate = defaultBurnedMins / 1.25;
                                } else if (exerciseString.equals("Jumping Jacks")) {
                                    toUpdate = defaultBurnedMins / .5;
                                } else if (exerciseString.equals("Cycling")) {
                                    toUpdate = defaultBurnedMins / .6;
                                } else if (exerciseString.equals("Walking")) {
                                    toUpdate = defaultBurnedMins;
                                } else if (exerciseString.equals("Jogging")) {
                                    toUpdate = defaultBurnedMins / .6;
                                } else if (exerciseString.equals("Swimming")) {
                                    toUpdate = defaultBurnedMins / .65;
                                } else if (exerciseString.equals("Stair Climbing")) {
                                    toUpdate = defaultBurnedMins / .75;
                                } else {
                                    Toast.makeText(MainActivity.this, "Cannot feasibly measure this exercise in minutes", Toast.LENGTH_SHORT).show();
                                }
                            }
                            if (preOrPostButton.getText().equals("Pre Workout")) {
                                //update Reps/Mins needed
                                toUpdate = toUpdate / multiplier;
                                resultText.setText(String.valueOf((int) toUpdate) + " " + displayString);
                                //resultText.setText(String.valueOf(toUpdate));
                                //reset displayText
                                //displayText.setText(displayString);
                            } else {
                                //update convCals
                                //working correctly
                                toUpdate = toUpdate * multiplier;
                                convCals.setText(String.valueOf((int) toUpdate));
                                //displayText.setText("");
                                resultText.setText("");
                            }
                            Toast.makeText(MainActivity.this, "multiplier is " + (Double.toString(multiplier)), Toast.LENGTH_SHORT).show();
                            //displayText.setText(displayString);
                        }





                    }
                }
        );
    }

    public static boolean isValid(String s) {
        //first check to see that it's numberic
        for (char c: s.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        if (Double.parseDouble(s) < 0) {
            return false;
        }
        return true;
    }

    /*public void spinnerPick() {
        //need to get the index of the correct image
        imageIndex = 2;
        switchImage.setImageResource(images[imageIndex]);
    }*/
}
