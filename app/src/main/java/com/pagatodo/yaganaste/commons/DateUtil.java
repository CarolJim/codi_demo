package com.pagatodo.richardsproject.commons;

/**
 * Created by flima on 23/02/2017.
 */

import com.pagatodo.richardsproject.App;
import com.pagatodo.richardsproject.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Fausto on 6/29/16.
 */
public class DateUtil {

    public static String simpleDateFormatCustom = "dd MMM yyyy";
    public static String completeDateFormat = "dd MMM yyyy hh:mm:ss";
    public static String simpleDateFormatFirstYear = "yyyy-MM-dd";
    public static String simpleDateDayMonthYear = "ddMMyy";


    public static String renameDateMonth(String fullDateName) {
        char[] chars = fullDateName.toCharArray();
        for (int x = 0; x < chars.length; x++) {
            if (Character.isLetter(chars[x])) {
                chars[x] = Character.toUpperCase(chars[x]);
                return new String(chars).replace(".", "");
            }
        }
        return fullDateName.replace(".", "");
    }

    public static String getBirthDateCustomString(Calendar date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(simpleDateFormatCustom, new Locale("es", "MX"));
        return renameDateMonth(dateFormat.format(date.getTime()));

    }

    /**
     * Entrega un formato de tipo ddMMyy, por ejemplo 29 de Noviembre de 2017 = 291117
     *
     * @return
     */
    public static String getDayMonthYear() {
        SimpleDateFormat sdf = new SimpleDateFormat(simpleDateDayMonthYear);
        String currentDateandTime = sdf.format(new Date());

        // SimpleDateFormat dateFormat = new SimpleDateFormat(simpleDateDayMonthYear, new Locale("es", "mx"));
        return currentDateandTime;

    }

    /**
     * Encargado de hacer un formato especial de nombres a 3 letras, para evitar cambios de idiomas o
     * configuraciones adicionales
     *
     * @param year
     * @param moth
     * @param day
     * @return
     */
    public static String getBirthDateSpecialCustom(int year, int moth, int day) {
        String myMonth = "";

        switch (moth) {
            case 0:
                myMonth = App.getContext().getResources().getString(R.string.lista_meses_enero);
                break;
            case 1:
                myMonth = App.getContext().getResources().getString(R.string.lista_meses_febrero);
                break;
            case 2:
                myMonth = App.getContext().getResources().getString(R.string.lista_meses_marzo);
                break;
            case 3:
                myMonth = App.getContext().getResources().getString(R.string.lista_meses_abril);
                break;
            case 4:
                myMonth = App.getContext().getResources().getString(R.string.lista_meses_mayo);
                break;
            case 5:
                myMonth = App.getContext().getResources().getString(R.string.lista_meses_junio);
                break;
            case 6:
                myMonth = App.getContext().getResources().getString(R.string.lista_meses_julio);
                break;
            case 7:
                myMonth = App.getContext().getResources().getString(R.string.lista_meses_agosto);
                break;
            case 8:
                myMonth = App.getContext().getResources().getString(R.string.lista_meses_septiembre);
                break;
            case 9:
                myMonth = App.getContext().getResources().getString(R.string.lista_meses_octubre);
                break;
            case 10:
                myMonth = App.getContext().getResources().getString(R.string.lista_meses_noviembre);
                break;
            case 11:
                myMonth = App.getContext().getResources().getString(R.string.lista_meses_diciembre);
                break;
        }
        return day + " " + myMonth + " " + year;
    }

    /**
     * Se encarga de entregar el formato especifico de tipo 25 / Ago / 2017. Se usa usualmente en
     * PaymentSuccessFragment
     *
     * @param date Funciona con una instancia del calendario actual,
     * @return
     */
    public static String getPaymentDateSpecialCustom(Calendar date) {
        // Codigo para hacer pruebas de fecha
//       Calendar date2 = Calendar.getInstance();
//        date2.set(2017, 7, 1);

        String dateYear = String.valueOf(date.get(Calendar.YEAR));
        int dateMonth = date.get(Calendar.MONTH);
        String dateDay = String.valueOf(date.get(Calendar.DAY_OF_MONTH));

        String stringMonth = "";
        switch (dateMonth) {
            case 0:
                stringMonth = App.getContext().getResources().getString(R.string.lista_meses_enero);
                break;
            case 1:
                stringMonth = App.getContext().getResources().getString(R.string.lista_meses_febrero);
                break;
            case 2:
                stringMonth = App.getContext().getResources().getString(R.string.lista_meses_marzo);
                break;
            case 3:
                stringMonth = App.getContext().getResources().getString(R.string.lista_meses_abril);
                break;
            case 4:
                stringMonth = App.getContext().getResources().getString(R.string.lista_meses_mayo);
                break;
            case 5:
                stringMonth = App.getContext().getResources().getString(R.string.lista_meses_junio);
                break;
            case 6:
                stringMonth = App.getContext().getResources().getString(R.string.lista_meses_julio);
                break;
            case 7:
                stringMonth = App.getContext().getResources().getString(R.string.lista_meses_agosto);
                break;
            case 8:
                stringMonth = App.getContext().getResources().getString(R.string.lista_meses_septiembre);
                break;
            case 9:
                stringMonth = App.getContext().getResources().getString(R.string.lista_meses_octubre);
                break;
            case 10:
                stringMonth = App.getContext().getResources().getString(R.string.lista_meses_noviembre);
                break;
            case 11:
                stringMonth = App.getContext().getResources().getString(R.string.lista_meses_diciembre);
                break;
        }

        switch (dateDay) {
            case "1":
                dateDay = "01";
                break;
            case "2":
                dateDay = "02";
                break;
            case "3":
                dateDay = "03";
                break;
            case "4":
                dateDay = "04";
                break;
            case "5":
                dateDay = "05";
                break;
            case "6":
                dateDay = "06";
                break;
            case "7":
                dateDay = "07";
                break;
            case "8":
                dateDay = "08";
                break;
            case "9":
                dateDay = "09";
                break;

        }
        return dateDay + " " + stringMonth + " " + dateYear;
    }

    public static String getTodayCompleteDateFormat() {
        return getCompleteDateFormat(Calendar.getInstance());
    }


    public static String getCompleteDateFormat(Calendar date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(completeDateFormat, new Locale("es"));
        String result = dateFormat.format(date.getTime());
        return renameDateMonth(result);
    }


    public static String getDateStringFirstYear(Calendar date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(simpleDateFormatFirstYear, Locale.US);
        String result = dateFormat.format(date.getTime());
        return result;
    }

    public static String getMonthShortName(Calendar calendar) {
        return StringUtils.capitalize(StringUtils.getMonthShortName(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, new Locale("es", "MX"))));
    }

    public static Date getAdquirenteMovementDate(String movDate) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss", new Locale("es", "mx"));
        Date date = null;
        try {
            date = dateFormat.parse(movDate);
        } catch (ParseException e) {
            dateFormat = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss", new Locale("es"));
            try {
                date = dateFormat.parse(movDate);
            } catch (ParseException e1) {
                dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss", new Locale("es", "mx"));
                try {
                    date = dateFormat.parse(movDate);
                } catch (ParseException e2) {
                    dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss", new Locale("es"));
                    try {
                        date = dateFormat.parse(movDate);
                    } catch (ParseException e3) {
                        e3.printStackTrace();
                        dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault());
                        try {
                            date = dateFormat.parse(movDate);
                        } catch (ParseException e4) {
                            e4.printStackTrace();
                            dateFormat = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss", new Locale("en", "us"));
                            try {
                                date = dateFormat.parse(movDate);
                            } catch (ParseException e5) {
                                date = Calendar.getInstance().getTime();
                            }
                        }
                    }
                }
            }
        }
        return date;
    }
}