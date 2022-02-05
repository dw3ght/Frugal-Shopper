package edu.uga.cs.frugalshopper;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOError;
import java.math.RoundingMode;
import android.os.Bundle;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private static final String DEBUG_TAG = "FrugalShopper";

    // initializing prices of products to 0.0
    private double getPriceofA = 0.0;
    private double getPriceofB = 0.0;
    private double getPriceofC = 0.0;

    // initializing pounds of products to 0.0
    private double getPoundsofA = 0;
    private double getPoundsofB = 0;
    private double getPoundsofC = 0;

    // initializing ounces of products to 0.0
    private double getOuncesofA = 0;
    private double getOuncesofB = 0;
    private double getOuncesofC = 0;

    //initializing the units prices of the proudcts to 0.0
    private double unitPriceofA = 0.0;
    private double unitPriceofB = 0.0;
    private double unitPriceofC = 0.0;

    //initializing textviews for display
    private TextView textViewPriceofA;
    private TextView textViewPriceofB;
    private TextView textViewPriceofC;

    private TextView textViewPoundsofA;
    private TextView textViewPoundsofB;
    private TextView textViewPoundsofC;

    private TextView textViewOuncesofA;
    private TextView textViewOuncesofB;
    private TextView textViewOuncesofC;

    //response returned by method
    private TextView frugalBuyResponse;

    //initializing button
    private Button frugalBuyCalculation;
    private Button resetValues;

    //formater
    private DecimalFormat formatNumber = new DecimalFormat("$#0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(DEBUG_TAG, "MainActivity.onCreate()");

        //views for input
        textViewPriceofA = (TextView) findViewById(R.id.priceofA);
        textViewPriceofB = (TextView) findViewById(R.id.priceofB);
        textViewPriceofC = (TextView) findViewById(R.id.priceofC);

        textViewPoundsofA = (TextView) findViewById(R.id.PoundsofA);
        textViewPoundsofB = (TextView) findViewById(R.id.PoundsofB);
        textViewPoundsofC = (TextView) findViewById(R.id.PoundsofC);

        textViewOuncesofA = (TextView) findViewById(R.id.OuncesofA);
        textViewOuncesofB = (TextView) findViewById(R.id.OuncesofB);
        textViewOuncesofC = (TextView) findViewById(R.id.OuncesofC);

        //views for ouput
        frugalBuyResponse = (TextView) findViewById(R.id.frugalBuy);
        frugalBuyCalculation = (Button) findViewById(R.id.calculateFrugalBuyButton);
        frugalBuyCalculation.setOnClickListener(new ButtonClickListener());

        resetValues = (Button) findViewById(R.id.resetValuesButton);
        resetValues.setOnClickListener(new ResetClickListener());


    }

    //converting Strings(the input) into doubles
    /**
     * Converting Strings(the input) into doubles. This method returns a double value from the string parameter(input)
     * @param input     input String which will be converted
     * @return  Double      output message to be displayed to user
     * @exception NumberFormatException catch the number format exception
     * @exception Exception catch all other exceptions
     */
    double ParseDouble(String input) {
        if (input != null && input.length() > 0) {
            try {
                return Double.parseDouble(input);
            }catch (NumberFormatException ne){
                return -1;
            }
            catch(Exception e) {
                return -1;   // or some value to mark this field is wrong. or make a function valid-
                // ates field at intital point
            }
        }
        else return 0;
    }
    /**
     * Converting Strings(the input) into integers. This method returns an Integer value from the string parameter(input)
     * @param input     input String which will be converted
     * @return  Integer     output message to be displayed to user
     * @exception NumberFormatException catch the number format exception
     * @exception Exception catch all other exceptions
     */
    //converting Strings(the input) into integers
    double ParseInt(String input) {
        if (input != null && input.length() > 0) {
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException ne){
                return -1;
            }
            catch(Exception e) {
                return -1;   // or some value to mark this field is wrong. or make a function valid-
                // ates field at initial point
            }
        }
        else return 0;
    }

    //get frugal buy with three products parameters
    /**
     * Takes in up to three parameters and returns a string that can shown to the user.
     * This string will allow the user to know which product is the better purchase.
     *
     * @param priceofA    double value of product A that will be compared
     * @param priceofB    double value of product B that will be compared
     * @param priceofC    double value of product C that will be compared
     * @return String   returns which product to purchase to the user
     * @see String
     */
    String getFrugalBuy(double priceofA, double priceofB, double priceofC){


        DecimalFormat df = new DecimalFormat("#.##"); // formater
        df.setRoundingMode(RoundingMode.CEILING); //rounding each price to the nearest tenth to keep a consistency
        String response = ""; // blank response as of right now

        // initializing array of prices
        Double[] prices = {(ParseDouble(df.format(priceofA))), (ParseDouble(df.format(priceofB))), (ParseDouble(df.format(priceofC)))};
        double temp = ParseDouble(df.format(priceofA)); //creating a temp variable as a holder

        //for loop to find the minimum
        for(int i = 0;i < prices.length; i++){
           // prices[i] = ParseDouble(df.format(prices[i]));
            if(temp >= prices[i]) {
                temp = prices[i];
            }
            else {
                temp = temp;
            }
        }

        //use the minimum to find the which product is a frugal buy
        if (temp >= ParseDouble(df.format(priceofA))){
            response = "Buy product A";
        }
        else if(temp >= ParseDouble(df.format(priceofB))){
            response = "Buy product B";
        }
        else if(temp >= ParseDouble(df.format(priceofC))){
            response = "Buy product C";
        }


        return response;
    }

    private class ButtonClickListener implements View.OnClickListener {
        /**
         *  Action to perform when user clicks the button.
         *  Gets values from input, and computes the unite price using mathematical arithmetic.
         * @param   v   View
         * @see     #getFrugalBuy(double, double, double)
         */
        @Override
        public void onClick(View v) {

            //setting response to empty as of right now
            frugalBuyResponse.setText(" ");

            //if input of product A is null or empty
                if((!textViewPriceofA.getText().equals(null)) && (!textViewPoundsofA.getText().equals(null))
                        && (!textViewOuncesofA.getText().equals(null)) ){
                    //getting content values
                    getPriceofA = ParseDouble(textViewPriceofA.getText().toString());
                    getPoundsofA = ParseDouble(textViewPoundsofA.getText().toString());
                    getOuncesofA = ParseDouble(textViewOuncesofA.getText().toString());
                    //unit price equation
                    unitPriceofA = getPriceofA / ((getPoundsofA * 16) + getOuncesofA);
            }
            //if input of product A is null or empty
                if((!textViewPriceofB.getText().equals(null)) && (!textViewPoundsofB.getText().equals(null))
                        && (!textViewOuncesofB.getText().equals(null))) {
                    //getting content values
                    getPriceofB = ParseDouble(textViewPriceofB.getText().toString());
                    getPoundsofB = ParseDouble(textViewPoundsofB.getText().toString());
                    getOuncesofB = ParseDouble(textViewOuncesofB.getText().toString());
                    //unit price equation
                    unitPriceofB = getPriceofB / ((getPoundsofB * 16) + getOuncesofB);
                }

                //if input of product A is null or empty
                if ((!textViewPriceofC.getText().equals(null)) && (!textViewPoundsofC.getText().equals(null))
                        && (!textViewOuncesofC.getText().equals(null))) {
                    //getting content values
                    getPriceofC = ParseDouble(textViewPriceofC.getText().toString());
                    getPoundsofC = ParseDouble(textViewPoundsofC.getText().toString());
                    getOuncesofC = ParseDouble(textViewOuncesofC.getText().toString());
                    //unit price equation
                    unitPriceofC = getPriceofC / ((getPoundsofC * 16) + getOuncesofC);
                }

                //set text to the response returned by getfrugalbuy method
                frugalBuyResponse.setText(getFrugalBuy(unitPriceofA,unitPriceofB,unitPriceofC));



        }
    }


    private class ResetClickListener implements View.OnClickListener {
        /**
         *  Action to perform when user clicks the button.
         *  Resets input values to null
         * @param   v   View
         */
        @Override
        public void onClick(View v) {
            textViewPriceofA.setText(null);
            textViewPriceofB.setText(null);
            textViewPriceofC.setText(null);

            textViewOuncesofA.setText(null);
            textViewOuncesofB.setText(null);
            textViewOuncesofC.setText(null);

            textViewPoundsofA.setText(null);
            textViewPoundsofB.setText(null);
            textViewPoundsofC.setText(null);

        }
    }

}

