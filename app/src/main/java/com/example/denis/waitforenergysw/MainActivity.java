package com.example.denis.waitforenergysw;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    public static int DEFAULT_VALUE_NUMBERPICKER= 71 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NumberPicker nbP = (NumberPicker) findViewById(R.id.nombreEnergieTotalePicker);
        nbP.setMinValue(40);
        nbP.setMaxValue(100);
        nbP.setValue(DEFAULT_VALUE_NUMBERPICKER);
        nbP.setWrapSelectorWheel(false);
    }

    public void calculTempsRechargement(View view){

        // Cacher clavier
        View viewForKeyboard = this.getCurrentFocus();
        if (viewForKeyboard != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(viewForKeyboard.getWindowToken(), 0 );
        }

        EditText editTextNbEnergieActuelle= (EditText) findViewById(R.id.energieRestante);
        int nbEnergieActuelle = 0 ;
        if(!editTextNbEnergieActuelle.getText().toString().equals("")) {
            nbEnergieActuelle = Integer.parseInt(editTextNbEnergieActuelle.getText().toString());
        } else {
            Toast.makeText(MainActivity.this, "Vous n'avez pas rentré de valeur. N'avez-vous pas d'énergie ?", Toast.LENGTH_SHORT).show();
        }

        NumberPicker numberPickerMaxEnergie= (NumberPicker) findViewById(R.id.nombreEnergieTotalePicker);
        int nbEnergieMax = numberPickerMaxEnergie.getValue();

        int diffEnergie = nbEnergieMax-nbEnergieActuelle;
        int tpsRestant = diffEnergie * 5;


        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.MINUTE,tpsRestant);
        String heure = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
        if(heure.length()==1){
            heure = "0" + heure;
        }

        String minute = String.valueOf(cal.get(Calendar.MINUTE));
        if(minute.length()==1){
            minute = "0" + minute;
        }

        String resultatCalcul = "Il vous faut patienter encore " + tpsRestant + " minutes... Il sera " + heure + "h"+ minute;

        if(tpsRestant == 0) {
            resultatCalcul = "Vous pouvez jouer !!";
        } else if (tpsRestant < 0 ){
            resultatCalcul = "Vérifiez votre saisie d'énergie actuelle.";
        }

        TextView textViewResultat= (TextView) findViewById(R.id.resultatEnergie);
        textViewResultat.setText(resultatCalcul);
    }
}
