package com.pagatodo.richardsproject.watchers;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.pagatodo.richardsproject.commons.StringUtils;

public class CardTextWatcher implements TextWatcher {

    private EditText cardNumber;
    private int maxLength;
    private String finalText = "";
    private String auxText = "";

    public CardTextWatcher(EditText cardNumber, int maxLength) {
        this.cardNumber = cardNumber;
        this.maxLength = maxLength;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        finalText = "";
        auxText = "";
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        String newString = "";
        auxText = charSequence.toString().replaceAll(" ", "");

        // Procesamos si el texto se sale de los rangos permitidos de maxlenght
        if (auxText.length() > maxLength - 3) {
            // Procesamos el recorte
            int baseNum = maxLength - 3;
            newString = charSequence.toString().substring(0, baseNum);
            auxText = StringUtils.genericFormat(newString.toString().replaceAll(" ", ""), " ");
            cardNumber.removeTextChangedListener(this);
            cardNumber.setText(auxText);
            cardNumber.addTextChangedListener(this);
            finalText = auxText;
        }
    }

    /**
     * Procesa el formato de poner espacios cuando se registran los numetros
     * 4, 9 y 14. Y cuando se hace un borrado, se registra con esos mismos nuemros, borando el
     * espacio mas el numero que logicamente corresponde
     *
     * @param s
     */
    @Override
    public void afterTextChanged(Editable s) {
        cardNumber.removeTextChangedListener(this);
        String newS;

        if (finalText.length() > 0) {
            newS = StringUtils.format(finalText.toString().replace(" ", ""), " ", 4, 4, 4, 4);
        } else {
            newS = StringUtils.format(s.toString().replace(" ", ""), " ", 4, 4, 4, 4);
        }
        cardNumber.setText(newS);
        cardNumber.setSelection(newS.length());
        cardNumber.addTextChangedListener(this);

        /*if (listener != null) {
            listener.onTextChanged();
            if (cardNumber.getText().toString().length() == maxLength) {
                listener.onTextComplete();
            }
        }*/
    }
}
