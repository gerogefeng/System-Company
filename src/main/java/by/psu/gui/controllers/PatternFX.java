package by.psu.gui.controllers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternFX {

    public boolean checkTextField(String string, int beginSymbols,int endSymbols){
        Pattern p = Pattern.compile(String.format("^([A-z]){%s,%s}+|([А-я]){%s,%s}+",
                String.valueOf(beginSymbols), String.valueOf(endSymbols),
                String.valueOf(beginSymbols), String.valueOf(endSymbols)));
        Matcher m = p.matcher(string);
        return m.matches();
    }

    //+375 (29) 947-96-30
    //375 (29) 947 96 30
    //(\+)?(\d{3})(\s)?(\()?(\d{2})?(\))?(\s)?(\d{3})(\-|\s)?(\d{2})(\-|\s)?(\d){2}
    //^(+375 (29|44|33)|(29|44|33))((\s)?(\d{3})(\-|\s)?(\d{2})(\-|\s)?(\d){2})$
    public boolean checkNumberPhone(String string){
        String strCode = "(29|44|33)";
        String strSpace = "(\\-|\\s)?";
        String strNumber = String.format("(\\d{3})%s(\\d{2})%s(\\d){2}$", strSpace, strSpace, strSpace);

        String pattern = String.format("^((\\+)(375)%s(\\s)?%s)|(%s%s)$", strCode,strNumber, strCode, strNumber);
        Pattern p = Pattern.compile(pattern);
        //Pattern p = Pattern.compile( "((\\+)?(\\d{3})?(\\s)?(\\()?(\\d{2})?(\\))?(\\s)?(\\d{3})(\\-|\\s)?(\\d{2})(\\-|\\s)?(\\d){2})");
        Matcher m = p.matcher(string);
        return m.matches();
    }

    // paveltalaiko@gmail.com
    // paveltalaiko@yahoo.com
    public boolean checkEmail(String string){
        Pattern p = Pattern.compile("^(\\D+)(\\.)?(\\d+)?(\\@)(\\D)+(\\.)(\\D){2,4}");
        Matcher m = p.matcher(string);
        return m.matches();
    }

    public boolean checkNumberTextField(String string, int beginSymbols,int endSymbols){
        Pattern p = Pattern.compile(String.format("^([0-9]){%s,%s}+", beginSymbols, endSymbols));
        Matcher m = p.matcher(string);
        return m.matches();
    }

}
