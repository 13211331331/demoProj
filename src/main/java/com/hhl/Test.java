package com.hhl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author hanlin.huang
 * @create 2020-07-10 16:27
 * @desc
 **/
public class Test {
    public static void main(String[] args) throws ParseException {
        String activeTime = "2020-07-10";
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        String nowDateStr = dateFormat1.format(new Date());
        if(activeTime.length()>10){
            activeTime = activeTime.substring(0,10);
        }

        Date date = dateFormat1.parse(activeTime);
        Date dateNow = dateFormat1.parse(nowDateStr);

        System.out.println(dateNow.getTime()  - date.getTime());

    }
}
