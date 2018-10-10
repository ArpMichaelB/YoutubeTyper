package com.glacier.util;

public class Utilities {
	public static final int CYCLE_0_TO_TITLE = 14;
	public static final int CYCLE_1_TO_TITLE = 23;
	public static final int TO_TITLE = 24;
    public static final int TO_DESCRIPTION = 1;
    public static final int TO_TAGS = 1;//amount of tabs to move from the current item to the item listed
    public static final int MENU_SIZE = 550;
    public static final int MENU_SIZE_TWO = 550;//size of the initial menu
    public static final int ERROR_SIZE = 375;
    public static final int ERROR_SIZE_TWO = 60;
    public static final int INITIAL_WAIT = 10000;
    /**
     * A method which, given three strings of items delimited by pluses, returns an array of the items being split by the pluses
     * @param titles a string of titles, split by pluses
     * @param descriptions a string of descriptions, split by pluses
     * @param tags a string of tags, split by pluses
     * @return a two dimensional array. The second dimension is the items delimited by the pluses, and the first dimension is 3 spaces to hold each of these arrays
     */
    public static String[][] parseItems(String titles, String descriptions, String tags)
    {
        String[][] ret = new String[3][30];
        String[] temp = titles.split("[+]");//make an array, each item being the given box's thing
        ret[0] = temp;//then add that array to the array of things we're returning
        temp = descriptions.split("[+]");
        ret[1] = temp;
        temp = tags.split("[+]");
        ret[2] = temp;//such that the titles are in there first, then the descriptions, then the tags
        //originally this was using system.arraycopy but I think that limited the number of titles tags and descriptions to 30 per run of the program
        //this should remove that limit and seems to work properly
        return ret;
    }
}
