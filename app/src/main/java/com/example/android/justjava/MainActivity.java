/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    /**
     * public void submitOrder(View view) {
     * displayPrice(quantity * 5);
     * }
     */

    public void submitOrder(View view) {
        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();
        //  Log.v("mainactivity", "hasWhippedcream: " + hasWhippedCream);
        CheckBox ChocolateCheckbox = (CheckBox) findViewById(R.id.chokolate_checkbox);
        boolean hasChocolate = ChocolateCheckbox.isChecked();
        //   Log.v("mainactivity", "hasChocolate: " + hasChocolate);
        EditText customerName = (EditText) findViewById(R.id.name_field);
        String name = customerName.getText().toString();
        // Log.v("mainactivity", "customer name: " + name);
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, name);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        //intent.putExtra(Intent.EXTRA_EMAIL,"zufazufa1984@gmail.com")
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_for, name) );
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        startActivity(intent);
    }



// mailto:bob@example.org?cc=alice@example.com&subject=sender%40mail.ru&body=bodyText
//        {
//            EditText customerName = (EditText) findViewById(R.id.name_field);
//            String name = customerName.getText().toString();
//
//            CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.cream_checkbox);
//            boolean hasWhippedCream = whippedCreamCheckbox.isChecked();
//            //  Log.v("mainactivity", "hasWhippedcream: " + hasWhippedCream);
//            CheckBox ChocolateCheckbox = (CheckBox) findViewById(R.id.chokolate_checkbox);
//            boolean hasChocolate = ChocolateCheckbox.isChecked();
//            //   Log.v("mainactivity", "hasChocolate: " + hasChocolate);
//            int price = calculatePrice(hasWhippedCream, hasChocolate);
//
// //String bodyText = createOrderSummary(price, hasWhippedCream, hasChocolate, name);
//
//            String mailto = "mailto:zufazufa1984@gmail.com" + "?cc=" +
//                    "&subject=" + Uri.encode("JustJava order for " + name ) +
//                    "&body=" + Uri.encode("Name: " + name + "\nAdd whipped cream?" + hasWhippedCream + "\nAdd chocolate?" + hasChocolate +
//                    "\nQuantity: " + quantity + "\nTotal: $" + price + "\nThank you!");
//
//
//            //Log.v("mainactivity", "customer name: " + mailto);
//
//            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
//            emailIntent.setData(Uri.parse(mailto));
//            startActivity(emailIntent);
//
//        }



    /**
     * Calculates the price of the order.
     *
     * @return total price of coffee
     */
    private int calculatePrice(boolean addWhipperCream, boolean addChocolate) {
        int basePrice = 5;

        if (addWhipperCream) {
            basePrice += 1;
        }
        if (addChocolate) {
            basePrice += 2;
        }
        return quantity * basePrice;
    }

    /**
     * crate summary of the order.
     *
     * @param cream    whether whipped cream.
     * @param choco    whether chocolate.
     * @param customer name of customer og coffee.
     * @param price    price of the order.
     * @return total price of coffee and other params.
     */

    private String createOrderSummary(int price, boolean cream, boolean choco, String customer) {
        String priceMessage = getString(R.string.order_summary_name,customer) ;
        String creamTopping ;
        String chocoTopping ;

        if (cream) {
            creamTopping = getString(R.string.yesCream);
        } else creamTopping = getString(R.string.noCream);

        if (choco) {
            chocoTopping = getString(R.string.yesChoco);
        } else chocoTopping = getString(R.string.noChoco);

        priceMessage += "\n" + getString(R.string.order_summary_whipped_cream, creamTopping) ;
        priceMessage += "\n" + getString(R.string.order_summary_chocolate, chocoTopping);
        priceMessage += "\n" + getString(R.string.quantity_coffee,quantity);
        //NumberFormat.getCurrencyInstance().format(price) мазкур метод билан умумий нарх валютаси махаллий валюта қийматида кўрсатилади
        priceMessage += "\n" + getString(R.string.total_price, NumberFormat.getCurrencyInstance().format(price));
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }

    /**
     * This method is called when the increment button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            // Show an error message as a toast
            Toast toast = Toast.makeText(this, getString(R.string.can_not_have_more100), Toast.LENGTH_LONG);
            toast.show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the decrement button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            // Show an error message as a toast
            Toast toast = Toast.makeText(this, getString(R.string.can_not_have_less1), Toast.LENGTH_LONG);
            toast.show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     *
     private void displayPrice(String message) {
     TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
     priceTextView.setText(message);
     }
     */

    /**
     * This method displays the given text on the screen.
     */
//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }
}