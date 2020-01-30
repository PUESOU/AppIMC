package com.example.appimc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button envoyer = null;
    Button reset = null;
    EditText taille = null;
    EditText poids = null;
    CheckBox commentaire = null;
    RadioGroup group = null;
    TextView result = null;

    // private final String texteInit = "Cliquez sur le bouton « Calculer l'IMC » pour obtenir un résultat.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        envoyer = (Button)findViewById(R.id.calcul);
        reset = (Button)findViewById(R.id.reset);
        taille = (EditText)findViewById(R.id.taille);
        poids = (EditText)findViewById(R.id.poids);
        commentaire = (CheckBox)findViewById(R.id.commentaire);
        group = (RadioGroup)findViewById(R.id.group);
        result = (TextView)findViewById(R.id.result);

        envoyer.setOnClickListener(envoyerListener);
        reset.setOnClickListener(resetListener);
        commentaire.setOnClickListener(checkedListener);
        // taille.addTextChangedListener(textWatcher);
        // poids.addTextChangedListener(textWatcher);
        taille.setOnKeyListener(modifListener);
        poids.setOnKeyListener(modifListener);
    }

    private View.OnClickListener envoyerListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                String t = taille.getText().toString();
                String p = poids.getText().toString();
                float tValue = Float.valueOf(t);

                if (tValue <= 0) {
                    Toast.makeText(MainActivity.this, getString(R.string.notice_positiveHeight), Toast.LENGTH_SHORT).show();
                } else {
                    float pValue = Float.valueOf(p);

                    if (pValue <= 0) {
                        Toast.makeText(MainActivity.this, getString(R.string.notice_positiveWeight), Toast.LENGTH_SHORT).show();
                    } else {
                        if (group.getCheckedRadioButtonId() == R.id.radio_centimetre) {
                            tValue = tValue / 100;
                        }

                        float imc = pValue / (tValue * tValue);
                        String resultat = String.format(getString(R.string.bmi_message), imc);

                        if(commentaire.isChecked()) {
                            resultat += " (" + interpreteIMC(imc) + ").";
                        }

                        result.setText(resultat);
                    }
                }
            } catch(NumberFormatException e) {
                Toast.makeText(MainActivity.this, getString(R.string.notice_emptyFields), Toast.LENGTH_SHORT).show();
            }
        }
    };

    private View.OnClickListener resetListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            poids.getText().clear();
            taille.getText().clear();
            // result.setText(texteInit);
            result.setText(getString(R.string.result_default));
        }
    };

    private View.OnClickListener checkedListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(((CheckBox)v).isChecked()) {
                // result.setText(texteInit);
                result.setText(getString(R.string.result_default));
            }
        }
    };

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // result.setText(texteInit);
            result.setText(getString(R.string.result_default));
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private String interpreteIMC(float imc) {
        if(imc < 16.5) {
            return getString(R.string.bmi_category0);
        }

        if(imc < 18.5) {
            return getString(R.string.bmi_category1);
        }

        if(imc < 25) {
            return getString(R.string.bmi_category2);
        }

        if(imc < 30) {
            return getString(R.string.bmi_category3);
        }

        if(imc < 35) {
            return getString(R.string.bmi_category4);
        }

        if(imc < 40) {
            return getString(R.string.bmi_category5);
        }

        return getString(R.string.bmi_category6);
    }

    private View.OnKeyListener modifListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            // result.setText(texteInit);
            result.setText(getString(R.string.result_default));

            char character = (char) event.getUnicodeChar();

            if(character == '.' && v == taille) {
                group.check(R.id.radio_metre);
            }

            return false;
        }
    };
}
