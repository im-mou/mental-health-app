package com.proyectosm.mentalhealthapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;

// Clase auxiliar para mostrar fechas
public class DatesUtils {

    private String date;
    private SimpleDateFormat formattedDate;
    private Locale locale;
    private Date parsedDate;

    // Constructor por parámetros de DatesUtils (con fecha)
    public DatesUtils(String date){
        this.date = date;
        this.locale = new Locale("es", "ES");
        this.formattedDate = new SimpleDateFormat("dd-MM-yyyy", this.locale);
        try {
            this.parsedDate = this.formattedDate.parse(this.date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    // Constructor por parámetros de DatesUtils (sin fecha)
    public DatesUtils(){

        this.locale = new Locale("es", "ES");
        this.formattedDate = new SimpleDateFormat("dd-MM-yyyy", this.locale);
        try {
            this.parsedDate = this.formattedDate.parse(LocalDateTime.now().toString());
            this.date = new SimpleDateFormat("dd-MM-yyyy", this.locale).format(this.parsedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    // Funciones getters
    public String getDay(){
        return new SimpleDateFormat("dd", this.locale).format(this.parsedDate);
    }

    public String getHumanDate(){
        String dayName = new SimpleDateFormat("EEEE, dd", this.locale).format(this.parsedDate);
        String monthName = new SimpleDateFormat("LLLL, YYYY", this.locale).format(this.parsedDate);
        return dayName + " de " + monthName;
    }

    public String getMonth(){
        return new SimpleDateFormat("LLLL", this.locale).format(this.parsedDate);
    }

    public String getYear(){
        return new SimpleDateFormat("YYYY", this.locale).format(this.parsedDate);
    }

    public String getCurrentMonth(){
        return new SimpleDateFormat("MM", this.locale).format(this.parsedDate);
    }

    public String getCurrentDate(){
        return new SimpleDateFormat("dd-MM-yyyy", this.locale).format(this.parsedDate);
    }

}
