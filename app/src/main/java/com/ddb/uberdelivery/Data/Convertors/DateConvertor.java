package com.ddb.uberdelivery.Data.Convertors;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public  class DateConvertor {
    public static String fromDateToString(Date date){

        return date==null?null: new SimpleDateFormat("dd/MM/yyyy").format(date) ;
    }
    public static Date fromStringToDate(String dateStr) throws ParseException {
        return  new SimpleDateFormat("dd/MM/yyyy").parse(dateStr) ;
    }
}
