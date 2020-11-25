package FYP.Niamh.bis.SailingAppliation.Util;

/*
 * Adapted from Michael Gleesons lecture on 12/11/2020 gleeson.io
 */
public class Config {

    public static final String DATABASE_NAME = "safety-db";

    //column names of safety table
    public static final String TABLE_SAFETY = "safety";
    public static final String COLUMN_SAFETY_ID = "_id";
    public static final String COLUMN_SAFETY_NAME = "name";
    public static final String COLUMN_SAFETY_DESCRIPTION = "description";
    public static final String COLUMN_SAFETY_ISSUE = "issue";
    public static final String COLUMN_SAFETY_AVAILABLE = "available" ;

    //others for general purpose key-value pair data
    public static final String TITLE = "title";
    public static final String CREATE_SAFETY = "create_safety";
    public static final String UPDATE_SAFETY = "update_safety";

}

