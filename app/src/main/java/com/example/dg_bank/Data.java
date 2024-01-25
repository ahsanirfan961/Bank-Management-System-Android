package com.example.dg_bank;

import android.content.res.Resources;

import java.util.regex.Pattern;

public class Data {
    static public SQLServerConnect sqlManager = new SQLServerConnect();
    static public String CurrentUserID;
    static public String CurrentUserName;
    static public String CurrentBalance;
    static public String CurrentGender;
    static public boolean accountExist;
    static public String Receiver_ID;
    static public String FirstName;
    static public String LastName;
    static public String FatherName;
    static public String MotherName;
    static public String CNIC;
    static public String Designation;
    static public String Phone;
    static public String DOB;
    static public String CNIC_Issue;
    static public String CNIC_Expiry;
    static public String Gender;
    static public String Marital_Status;
    static public String Education;
    static public String BirthPlace;
    static public String Nationality;
    static public String Residence;
    static public String Profession;
    static public String Mobile;
    static public String Permenant_Address;
    static public String Current_Address;
    static public String City;
    static public String Country;
    static public String Postal_Code;
    static public String Email;
    static public String Account_Type;
    static public String Currency;
    static public String Account_Title;
    static public String Zakat_Exemption_Type;
    static public String Additional_Info;
    static public boolean US_National;
    static public boolean US_BirthPlace;
    static public boolean US_Address;
    static public boolean US_Links;
    static public boolean Zakaat;

    public static boolean containsOnlyAlphabets(String input) {
        // Regular expression pattern to match alphabetic characters
        String regex = "^[a-zA-Z\\s]+$";

        // Create a Pattern object from the regex
        Pattern pattern = Pattern.compile(regex);

        // Use Matcher to check if the input matches the pattern
        return pattern.matcher(input).matches();
    }

    public static boolean isValidEmail(String email) {
        // Regular expression pattern for a valid email address
        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        // Create a Pattern object from the regex
        Pattern pattern = Pattern.compile(regex);

        // Use Matcher to check if the email matches the pattern
        return pattern.matcher(email).matches();
    }

    public static String getStringIndexInArray(String targetString, int stringArrayResId, Resources resources) {
        String[] stringArray = resources.getStringArray(stringArrayResId);
        for (int i = 0; i < stringArray.length; i++) {
            if (stringArray[i].equals(targetString)) {
                return String.valueOf(i);
            }
        }
        return "0";
    }

    public static String convertBooltoString(Boolean bool)
    {
        if(bool)
        {
            return "1";
        }
        else
        {
            return "0";
        }
    }

}
