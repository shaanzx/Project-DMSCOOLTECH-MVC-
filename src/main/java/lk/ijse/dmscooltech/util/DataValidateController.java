package lk.ijse.dmscooltech.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataValidateController {
    //Customer class
    public  static  boolean validateCustomerName(String name){
        String nameRegex = "^[A-z|\\s]{3,}$";
        Pattern pattern = Pattern.compile(nameRegex);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }
    public static boolean validateCustomerAddress(String address){
        String addressRegex = "^([A-z0-9]|[-/,.@+]|\\s){4,}$";
        Pattern pattern = Pattern.compile(addressRegex);
        Matcher matcher = pattern.matcher(address);
        return matcher.matches();
    }
    public static boolean validateCustomerTel(String tel){
        String telRegex = "^([+]94{1,3}|[0])([1-9]{2})([0-9]){7}$";
        Pattern pattern = Pattern.compile(telRegex);
        Matcher matcher = pattern.matcher(tel);
        return matcher.matches();
    }
    public static boolean validateCustomerEmail(String email){
        String emailRegex = "^([A-z])([A-z0-9.]){1,}[@]([A-z0-9]){1,10}[.]([A-z]){2,5}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    //Employee class
    public static boolean validateEmployeeName(String name){
        String nameRegex = "^[A-z|\\s]{3,}$";
        Pattern pattern = Pattern.compile(nameRegex);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }
    public static boolean validateEmployeeAddress(String address){
        String addressRegex = "^([A-z0-9]|[-/,.@+]|\\s){4,}$";
        Pattern pattern = Pattern.compile(addressRegex);
        Matcher matcher = pattern.matcher(address);
        return matcher.matches();
    }
    public static boolean validateEmployeeTel(String tel){
        String telRegex = "^([+]94{1,3}|[0])([1-9]{2})([0-9]){7}$";
        Pattern pattern = Pattern.compile(telRegex);
        Matcher matcher = pattern.matcher(tel);
        return matcher.matches();
    }
    public static boolean validateEmployeeJobRole(String job){
        String jobroleRegex = "^[A-z|\\s]{3,}$";
        Pattern pattern = Pattern.compile(jobroleRegex);
        Matcher matcher = pattern.matcher(job);
        return matcher.matches();
    }
}
