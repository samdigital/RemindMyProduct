package it.uniud.remindmyproduct;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ReportActivity extends AppCompatActivity {

    private int num_of_product;
    private double value_of_product;
    TextView num_of_product_text;
    TextView value_of_product_text;
    TextView suggestion_text;
    TextView emoticon_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        num_of_product=15;
        value_of_product=89.56;

        num_of_product_text=(TextView) findViewById(R.id.reportNumeroProdotti);
        value_of_product_text=(TextView) findViewById(R.id.reportValoreEuro);
        suggestion_text=(TextView) findViewById(R.id.reportSuggest);
        emoticon_text=(TextView) findViewById(R.id.reportEmoticon);

        num_of_product_text.setText(String.valueOf(num_of_product));
        value_of_product_text.setText(String.format("%.2f", value_of_product)+" €");

        if(num_of_product>0) {
            suggestion_text.setText(getString(R.string.report_suggest_bad));
            emoticon_text.setText(getEmojiByUnicode(0x1F620));
        } else {
            suggestion_text.setText(getString(R.string.report_suggest_good));
            emoticon_text.setText(getEmojiByUnicode(0x1F60A));
        }
    }

    public String getEmojiByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }
}