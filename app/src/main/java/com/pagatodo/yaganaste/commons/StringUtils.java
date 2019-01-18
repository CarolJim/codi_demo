package com.pagatodo.yaganaste.commons;

import android.text.SpannableStringBuilder;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author Juan Guerra on 27/03/2017.
 */

public class StringUtils {

    private static final int SHORT_MONTH_NAME_L = 3;

    public static String capitalize(String s) {
        if (s.length() == 0) {
            return s;
        }
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }

    public static String getMonthShortName(@Nullable String monthLongName) {
        if (monthLongName == null) {
            return "";
        } else if (monthLongName.length() <= SHORT_MONTH_NAME_L) {
            return monthLongName;
        }
        return monthLongName.substring(0, SHORT_MONTH_NAME_L);
    }

    public static String cleanMount(String value) {
        return getCurrencyValue(value).replace("$", "");
    }

    public static String getCurrencyValue(double value) {
        return getCurrencyValueInFormat(value, 2, 1);
    }

    public static String getCurrencyValue(String value) {
        return getCurrencyValue(getDoubleValue(value));
    }

    private static String getCurrencyValueInFormat(double value, int minFractionDigits, int minIntDigits) {
        Locale mx = Locale.getDefault();
        Locale.setDefault(Locale.US);
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getCurrencyInstance();
        formatter.setNegativePrefix("-$");
        formatter.setNegativeSuffix("");
        formatter.setPositivePrefix("$");
        formatter.setPositiveSuffix("");
        formatter.setMinimumFractionDigits(minFractionDigits);
        formatter.setMaximumFractionDigits(minFractionDigits);
        formatter.setMinimumIntegerDigits(minIntDigits);
        formatter.setRoundingMode(RoundingMode.HALF_UP);
        Locale.setDefault(mx);
        return formatter.format(value);
    }

    public static String getCurrencyNoFraction(String value) {
        return getCurrencyNoFraction(getDoubleValue(value));
    }

    public static String getCurrencyNoFraction(double value) {
        return getCurrencyValueInFormat(value, 0, 1);
    }

    public static Double getDoubleValue(String value) {
        try {
            value = value.replace("$", "").replace(",", "").replace("%", "")
                    .replace("&nbsp;", "");
            if (value.isEmpty()) {
                return 0.0;
            }
            return Double.valueOf(value);
        } catch (Exception e) {
            return 0.0;
        }
    }

    public static Double getDoubleValue(TextView textView) {
        return getDoubleValue(textView.getText().toString());
    }

    public static int getIntValue(String value) {
        return getDoubleValue(value).intValue();
    }

    public static SpannableStringBuilder getSpannable(String formatted) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append(formatted);
        return spannableStringBuilder;
    }

    public static String genericFormat(String text, String separator) {
        int groups = (text.length() - 1) / 5;
        int[] formatPattern = new int[groups];
        Arrays.fill(formatPattern, 4);
        StringBuilder format = new StringBuilder();
        int size = 0;
        for (int current : formatPattern) {
            size += current;
        }

        if (text.length() > size) {
            return text;
        }

        int currentGroup = formatPattern.length - 1;
        int currentSize = formatPattern[currentGroup];
        for (int n = text.length() - 1; n >= 0; n--) {
            format.insert(0, text.charAt(n));
            currentSize--;
            if (currentSize == 0) {
                currentGroup--;
                if (n != 0) {
                    currentSize = formatPattern[currentGroup];
                    format.insert(0, separator);
                }
            }
        }

        return format.toString();
    }

    public static String getCreditCardFormat(String card) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < card.length(); i++) {
            if (i % 4 == 0 && i != 0) {
                result.append(" ");
            }
            result.append(card.charAt(i));
        }

        return result.toString();
    }

    public static String createTicket() {
        return String.format("%1$tY%1$tM%1$tS%2$02d", Calendar.getInstance(), (int) (Math.random() * 90));
    }

    public static String ocultarCardNumber(String cardNumber) {
        String lastFourNumbers = cardNumber.substring(cardNumber.length() - 4);
        String comodines = "****";
        return comodines + comodines + comodines + lastFourNumbers;
    }

    public static String ocultarCardNumberFormat(String cardNumber) {
        String comodines = "****";
        String separador = " ";
        if (cardNumber.length() > 0) {
            String lastFourNumbers = cardNumber.substring(cardNumber.length() - 4);
            return comodines + separador + comodines + separador + comodines + separador + lastFourNumbers;
        } else {
            //return comodines + separador + comodines + separador + comodines + separador + "1234";
            return "";
        }
    }

    /**
     * @param mFormatoPago
     * @return
     * @deprecated Use {@link StringUtils#format(String, String, int...)} instead
     * <p>
     * Se encarga de dar fomato a los 10 digitos dr telefono, 16 de TDC y 18 de clave, segun las
     * reglas de espaciado seleccionado
     * </p>
     */
    @Deprecated
    public static String formatoPagoMedios(String mFormatoPago) {
        String formatoPago = "";
        String comodin = " ";
        if (mFormatoPago.length() == 10) {
            try {
                String parteTel1 = mFormatoPago.substring(0, 2);
                String parteTel2 = mFormatoPago.substring(2, 6);
                String parteTel3 = mFormatoPago.substring(6);
                formatoPago = parteTel1 + comodin + parteTel2 + comodin + parteTel3;
            } catch (Exception e) {
                formatoPago = mFormatoPago;
            }
        } else if (mFormatoPago.length() == 16) {
            try {
                /**
                 * Cambiamos el codigo que separa, por el metodo ocultarCardNumberFormat que
                 * separa y ofusca

                 String formatoTDC1 = mFormatoPago.substring(0, 4);
                 String formatoTDC2 = mFormatoPago.substring(4, 8);
                 String formatoTDC3 = mFormatoPago.substring(8, 12);
                 String formatoTDC4 = mFormatoPago.substring(12, 16);
                 formatoPago = formatoTDC1 + comodin + formatoTDC2 + comodin + formatoTDC3 + comodin + formatoTDC4;
                 */
                formatoPago = ocultarCardNumberFormat(mFormatoPago);
            } catch (Exception e) {
                formatoPago = ocultarCardNumberFormat(mFormatoPago);
            }
        } else if (mFormatoPago.length() == 18) {
            try {
                String formatoClabe1 = mFormatoPago.substring(0, 3);
                String formatoClabe2 = mFormatoPago.substring(3, 6);
                String formatoClabe3 = mFormatoPago.substring(6, 10);
                String formatoClabe4 = mFormatoPago.substring(10, 14);
                String formatoClabe5 = mFormatoPago.substring(14, 18);
                formatoPago = formatoClabe1 + comodin + formatoClabe2 + comodin + formatoClabe3
                        + comodin + formatoClabe4 + comodin + formatoClabe5;
            } catch (Exception e) {
                formatoPago = mFormatoPago;
            }
        }

        return formatoPago;
    }

    @Deprecated
    public static String formatoPagoMediostag(String mFormatoPago) {
        String formatoPago = "";
        String comodin = " ";
        if (mFormatoPago.length() == 13) {
            try {
                String parteTel1 = mFormatoPago.substring(0, 1);
                String parteTel2 = mFormatoPago.substring(1, 5);
                String parteTel3 = mFormatoPago.substring(5, 9);
                String parteTel4 = mFormatoPago.substring(9, 13);

                formatoPago = parteTel1 + comodin + parteTel2 + comodin + parteTel3 + comodin + parteTel4;
            } catch (Exception e) {
                formatoPago = mFormatoPago;
            }
        }

        return formatoPago;
    }

    /**
     * added by Juan Guerra
     *
     * @param text          text to format
     * @param separator     string that must be between groups
     * @param formatPattern int array for example 2,4,4 for phone number
     * @return String formatted or empty if text is null
     */
    public static String format(@Nullable String text, @NonNull String separator, @NonNull int... formatPattern) {
        StringBuilder format = new StringBuilder();
        int size = 0;
        for (int current : formatPattern) {
            size += current;
        }
        if (text == null || text.length() > size) {
            return text;
        }

        int currentGroup = 0;
        int currentSize = formatPattern[currentGroup];
        for (int n = 0; n < text.length(); n++) {
            format.append(text.charAt(n));
            currentSize--;
            if (currentSize == 0) {
                currentGroup++;
                if (n != text.length() - 1) {
                    currentSize = formatPattern[currentGroup];
                    format.append(separator);
                }
            }
        }
        return format.toString();
    }


    public static String maskReference(String original, char markerChar, int leftVisible) {
        int toMask = original.replace(" ", "").length() - leftVisible;
        StringBuilder newFormat = new StringBuilder();
        for (char current : original.toCharArray()) {
            if (Character.isDigit(current) && toMask-- > 0) {
                newFormat.append(markerChar);
            } else {
                newFormat.append(current);
            }
        }
        return newFormat.toString();
    }

    public static String formatWithSpace(@Nullable String text, @NonNull int... formatPattern) {
        return format(text, " ", formatPattern);
    }


    public static String getFirstName(String name) {
        return name.contains(" ") ? name.substring(0, name.indexOf(" ")) : name;
    }

    /**
     * Procesa la URL de la imagen y le agrega lo necesario para tomar el tamaño correcto
     */
    public static String procesarURLString(String mUserImage) {
        String[] urlSplit = mUserImage.split("_");
        String urlEdit;
        String split = urlSplit[0];
        if (split.isEmpty()) {
            urlEdit = "_M.png";

        } else {
            if (urlSplit[1].contains("jpg")) {
                urlEdit = urlSplit[0] + "_M.jpg";
            } else if (urlSplit[1].contains("png")) {
                urlEdit = urlSplit[0] + "_M.png";
            } else {
                urlEdit = "_M.png";
            }

        }
        return urlEdit;
    }

    /**
     * Se encarga de cifrar el correo, muestra 3 caracteres, asteriscos y la direccion de servidor
     *
     * @param username
     * @return
     */
    public static String cifrarPass(String username) {
        String[] mStringPart = username.split("@");
        String mStringLetters = mStringPart[0].substring(0, 3);

        return mStringLetters + "******@" + mStringPart[1];
    }

    public static String formatStatus(String estatus) {
        String mTitleStatus = "";
        switch (estatus) {
            case "DOCTO. RECHAZADO":
                mTitleStatus = "Documento Rechazado";
                break;
            default:
                mTitleStatus = "Documento Rechazado";
                break;
        }
        return mTitleStatus;
    }

    public static String formatAutorization(String numAut) {
        String fullFormat = "";
        fullFormat = numAut.substring(0, 3) + " " + numAut.substring(3);

        return fullFormat;
    }

    public static String formatSingleName(String mFullName) {
        String[] fullName = mFullName.split(" ");
        return fullName[0];
    }

    public static String formatCardToService(String mFullName) {
        return mFullName.replace(" ", "");
    }

    /**
     * Obtiene las iniciales a mostrar si no tenemos foto: Ejemplo
     * Frank Manzo Nava= FM
     * Francisco = Fr
     *
     * @param fullName
     * @return
     */
    public static String getIniciales(String fullName) {
        if (fullName.trim().length() == 1) {
            return fullName.substring(0, 1).toUpperCase();
        }

        String[] spliName = fullName.trim().split(" ");
        String sIniciales = "";
        if (spliName.length > 1) {
            sIniciales = spliName[0].substring(0, 1) + spliName[1].substring(0, 1).toUpperCase();
            return sIniciales;
        }

        if (fullName.trim().length() > 1) {
            return fullName.substring(0, 2).toUpperCase();
        }
        return "";
    }
}
