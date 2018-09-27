package com.glacier.util;

public class Utilities {
	public static final int TO_TITLE = 19;
    public static final int TO_DESCRIPTION = 1;
    public static final int TO_TAGS = 1;//amount of tabs to move from the current item to the item listed
    public static String getNextEndSignal = "1234567890CompletedWork0987654321";
    public static int menuSize = 550;
    public static int menuSizeTwo = 550;//size of the initial menu
    public static int errorSize = 375;
    public static int errorSizeTwo = 60;
    
    public static String[][] parseItems(String titles, String descriptions, String tags)
    {
        String[][] ret = new String[3][30];
        String[] temp = titles.split("[+]");//make an array, each item being the given box's thing
        System.arraycopy(temp, 0, ret[0], 0, temp.length); //fill the first array with Titles
        temp = descriptions.split("[+]");
        System.arraycopy(temp, 0, ret[1], 0, temp.length); //fill the second array with Descriptions
        temp = tags.split("[+]");
        System.arraycopy(temp, 0, ret[2], 0, temp.length); //fill the third array with tags
        return ret;
    }
}
