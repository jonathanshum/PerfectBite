package cs160group36.perfectbite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "FoodData.db";

    //table for food data - add fields for ratios for easier recommendations?
    public static final String TABLE_NAME = "foodtable";
    public static final String KEY_ROWID = "_id";
    public static final String KEY_FOODNAME = "name";
    public static final String KEY_SERVING = "servingsize";
    public static final String KEY_CAL = "Calories";
    public static final String KEY_FAT = "Fat";
    public static final String KEY_CARB = "Carbohydrates";
    public static final String KEY_SODIUM = "Sodium";
    public static final String KEY_PROTEIN = "Protein";
    public static final int DATABASE_VERSION = 1;

    //table for data about food consumed; shares some column names with the above table
    public static final String TABLE2_NAME = "log";
    public static final String KEY_DATE = "date";
    public static final String KEY_TIME = "time";

    //table for goal information
    public static final String TABLE3_NAME = "usergoals";
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_GOAL = "isgoal";
    public static final String KEY_VALUE = "value";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_PROGRESS = "progressmessage";

    private static final String DATABASE_CREATE =
            " create table  " +  TABLE_NAME  + " ("
                    + KEY_ROWID + " integer primary key autoincrement,  "
                    + KEY_FOODNAME + " text collate nocase, "
                    + KEY_SERVING + " text, "
                    + KEY_CAL + " integer, "
                    + KEY_FAT + " integer, "
                    + KEY_CARB + " integer, "
                    + KEY_SODIUM + " integer, "
                    + KEY_PROTEIN + " integer); ";

    private static final String DATABASE2_CREATE =
            " create table  " +  TABLE2_NAME  + " ("
                    + KEY_ROWID + " integer primary key autoincrement,  "
                    + KEY_FOODNAME + " text collate nocase, "
                    + KEY_DATE + " text, "
                    + KEY_TIME + " text, "
                    + KEY_CAL + " integer, "
                    + KEY_FAT + " integer, "
                    + KEY_CARB + " integer, "
                    + KEY_SODIUM + " integer, "
                    + KEY_PROTEIN + " integer); ";

    private static final String DATABASE3_CREATE =
            " create table  " +  TABLE3_NAME  + " ("
                    + KEY_ROWID + " integer primary key autoincrement,  "
                    + KEY_CATEGORY + " text, "
                    + KEY_GOAL + " integer, "
                    + KEY_VALUE + " integer, "
                    + KEY_DESCRIPTION + " text, "
                    + KEY_PROGRESS + " text); ";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("oncreate", "reached");
        db.execSQL(DATABASE_CREATE);
        db.execSQL(DATABASE2_CREATE);
        db.execSQL(DATABASE3_CREATE);

        //Insert default data into database. Currently inserts example values to demonstrate functionality.
        this.insertDefaultData(db);
        /*this.insertData(db, "example food 1", "1 cup", 100, 50, 20, 15, 50);
        this.insertData(db, "example food 2", "1 cup", 200, 100, 40, 30, 100);
        this.insertData(db, "example food 3", "3 cookies", 200, 60, 100, 10, 0);
        this.insertData(db, "example food 4", "1/4 cup", 50, 0, 20, 0, 0);
        this.insertData(db, "example food 5", "1 tsp", 80, 0, 35, 0, 5);*/

        //Insert default goals into database.
        //cal 2000, protein 50, fat 45?
        this.insertDefaultGoals(db);

        //Insert default log data into database. Currently empty.
        this.insertDefaultLogData(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertDefaultData(SQLiteDatabase db) {
        this.insertData(db, "Lemon Greek Yogurt","1 container",100,0,14,80,10);
        this.insertData(db, "Strawberry Lowfat Yogurt","1 cup",240,2,45,130,10);
        this.insertData(db, "Shredded Mozzarella Cheese","1 oz",80,6,0,190,7);
        this.insertData(db, "Bean & Cheese Burrito","1 burrito",440,14,68,990,17);
        this.insertData(db, "Red Wine Vinegar","1 tbsp",0,0,0,0,0);
        this.insertData(db, "Strawberry Smoothie","1 packet",100,1,7,170,15);
        this.insertData(db, "Fat Free Skim Milk","1 cup",90,0,13,130,9);
        this.insertData(db, "Balsamic Vinaigrette","2 tbsp",100,10,2,370,0);
        this.insertData(db, "European Baguette","3.5 inch slice",180,0,38,450,6);
        this.insertData(db, "Butter Cookies","2 cookies",140,7,18,100,1);
        this.insertData(db, "White Enriched Bread","33 g",90,1,17,190,3);
        this.insertData(db, "Chocolate Ice Cream","0.5 cup",260,16,27,70,5);
        this.insertData(db, "Shredded Cheddar Cheese","0.25 cup",110,9,2,180,7);
        this.insertData(db, "Tomatoes","0.5 cup",25,0,5,220,1);
        this.insertData(db, "Bologna","1 slice",150,13,5,470,4);
        this.insertData(db, "Cheddar Cheese Baked Potatoes","1 piece",190,7,27,300,4);
        this.insertData(db, "100% Apple Juice","8 fl oz",110,0,28,20,0);
        this.insertData(db, "Fat Free Milk","1 cup",90,0,13,135,9);
        this.insertData(db, "Wild Alaska Sockeye Salmon","0.25 cup",110,7,0,270,13);
        this.insertData(db, "Extra Virgin Olive Oil","1 tbsp",120,14,0,0,0);
        this.insertData(db, "Garbanzo Beans","0.5 cup",120,2,21,135,7);
        this.insertData(db, "Whole Grain Waffles","2 waffles",140,2,27,390,5);
        this.insertData(db, "Mini Bagels","1 bagel",120,1,24,210,4);
        this.insertData(db, "Soda","1 bottle",140,0,39,45,0);
        this.insertData(db, "Tropical Fruit Salad","1 cup",70,0,17,10,0);
        this.insertData(db, "Fruit Snacks","1 pouch",80,0,19,30,1);
        this.insertData(db, "Raspberry Jam","1 tbsp",50,0,13,0,0);
        this.insertData(db, "Pinto Beans","36 g",60,0,22,15,7);
        this.insertData(db, "Orange Juice","8 fl oz",110,0,27,0,1);
        this.insertData(db, "Energy Drink","12 oz",10,0,0,60,0);
        this.insertData(db, "Whey Protein","1 rounded scoop",120,1,3,110,24);
        this.insertData(db, "Potato Chips","1 oz",150,9,16,115,2);
        this.insertData(db, "Cottage Cheese","0.5 cup",85,1,5,55,13);
        this.insertData(db, "Corn","0.5 cup",60,1,9,200,2);
        this.insertData(db, "Pistachios","0.25 cup",180,16,8,230,4);
        this.insertData(db, "Cheese Ravioli","9 pieces",250,4,40,180,13);
        this.insertData(db, "Ham & Cheese Sandwich","1 piece",270,8,40,490,12);
        this.insertData(db, "Ground Beef Patty","1 patty",290,23,0,75,19);
        this.insertData(db, "Whole Wheat Bread","1 slice",80,1,14,160,4);
        this.insertData(db, "Long Grain Brown Rice","0.25 cup uncooked",150,1,32,0,3);
        this.insertData(db, "Grade A Jumbo Eggs","1 egg",90,5,0,90,8);
        this.insertData(db, "Salted Peanuts","25 pieces",170,12,8,72,7);
        this.insertData(db, "Spinach","1 cup",30,0,3,120,2);
        this.insertData(db, "Chicken Fried Rice","1 cup",240,2,44,1190,12);
        this.insertData(db, "Honey","1 tbsp",60,0,17,0,0);
        this.insertData(db, "Sugar","1 tsp",15,0,4,0,0);
        this.insertData(db, "Watermelon","2.7 oz",25,0,6,1,0);
        this.insertData(db, "Clover Honey","1 tbsp",60,0,17,0,0);
        this.insertData(db, "Salad Dressing","2 tbsp",210,22,3,200,0);
        this.insertData(db, "Grilled Chicken Sandwich","1 sandwich",280,9,0,490,15);
        this.insertData(db, "Sourdough Bread","2 slices",190,3,35,270,7);
        this.insertData(db, "Apple Pie","0.1 pie",330,18,42,160,2);
        this.insertData(db, "Soymilk","1 cup",70,4,4,120,7);
        this.insertData(db, "Sausage","1 link",280,23,4,810,12);
        this.insertData(db, "Mandarin Oranges","0.5 cup",45,0,13,10,0);
        this.insertData(db, "Steak","1 steak",856,62,0,253,72);
        this.insertData(db, "Kentucky Fried Chicken","1 skinless breast",280,15,7,748,28);
        this.insertData(db, "Brussels Sprouts","1 sprout",7,0,1,4,0);
        this.insertData(db, "Beef Tenderloin Steak","3 oz",185,9,0,47,23);
        this.insertData(db, "Butter","1 tbsp",101,11,0,1,0);
        this.insertData(db, "Breakfast Biscuit","1 item",436,25,35,1183,17);
        this.insertData(db, "Chicken","1 back (rotisserie)",456,34,0,926,36);
        this.insertData(db, "Turkey","1 lb",1843,176,0,280,58);
        this.insertData(db, "Grilled Salmon","1 serving",405,25,0,385,45);
        this.insertData(db, "Peanut Butter","99 g",460,27,48,390,11);
        this.insertData(db, "Tea","16 fl oz",0,0,0,15,0);
        this.insertData(db, "Chocolate Chip Cookie","1 cookie",500,27,62,330,6);
        this.insertData(db, "Cappuccino","12 fl oz",90,4,9,95,6);
        this.insertData(db, "Macaroni Salad","5 oz",270,14,33,600,4);
        this.insertData(db, "Red Bull","1 serving",110,0,28,100,0);
        this.insertData(db, "Pepperoni Pizza","1 serving",240,11,24,630,11);
        this.insertData(db, "Mayonnaise","0.42 oz",80,9,0,65,0);
        this.insertData(db, "Hash Browns","1 serving",130,8,14,720,1);
        this.insertData(db, "Apple","1 apple",65,0,17,1,0);
        this.insertData(db, "Hamburger","1 burger",250,8,32,490,12);
    }

    /*  Inserts a data entry for a new food. If a data entry already exists with the same name, the insertion fails.
        Returns true if the insertion is successful. Otherwise, returns false.
     */
    public boolean insertData(SQLiteDatabase db, String name, String serving, int cal, int fat, int carb, int sodium, int protein) {
        Cursor c = this.fetchDataFromName(db, name);
        if (c.moveToFirst()) {
            return false;
        }
        c.close();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_FOODNAME, name);
        contentValues.put(KEY_SERVING, serving);
        contentValues.put(KEY_CAL, cal);
        contentValues.put(KEY_FAT, fat);
        contentValues.put(KEY_CARB, carb);
        contentValues.put(KEY_SODIUM, sodium);
        contentValues.put(KEY_PROTEIN, protein);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    /*  Fetches the data entry for a specific food name (string). Returns the data as a Cursor.
        If there is no data entry for the specified food name, the returned Cursor will be empty
        and have a count of 0. This can be checked by calling the Cursor's getCount() method.
     */
    public Cursor fetchDataFromName(SQLiteDatabase db, String name) {
        String whereClause = KEY_FOODNAME + " = ?";
        String[] whereArgs = new String[] {name};
        Cursor c = db.query(TABLE_NAME, null, whereClause, whereArgs, null, null, null);
        return c;
    }

    /*  Inserts default log data for what the user has consumed. Currently empty (no default log data).
     */
    public void insertDefaultLogData(SQLiteDatabase db) {

    }

    /*  Inserts data into the log given the food name, the date, the time, and the number of servings consumed.
        Returns false if the insertion fails. Otherwise, returns true.
     */
    public boolean insertLogData(SQLiteDatabase db, String name, String date, String time, double servings) {
        Cursor data = this.fetchDataFromName(db, name);
        if (data.getCount() == 0) {
            return false;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_FOODNAME, name);
        contentValues.put(KEY_DATE, date);
        contentValues.put(KEY_TIME, time);

        Map<String, Integer> categories = new HashMap<String, Integer>();
        categories.put(KEY_CAL, 0);
        categories.put(KEY_FAT, 0);
        categories.put(KEY_CARB, 0);
        categories.put(KEY_SODIUM, 0);
        categories.put(KEY_PROTEIN, 0);
        Cursor goals = this.fetchGoalCategories(db);
        for (data.moveToFirst(); !data.isAfterLast(); data.moveToNext()) {
            for (goals.moveToFirst(); !goals.isAfterLast(); goals.moveToNext()) {
                String category = goals.getString(goals.getColumnIndex(KEY_CATEGORY));
                int value = data.getInt(data.getColumnIndex(category));
                value = (int)(servings * value); //multiply by number of servings
                categories.put(category, value);
            }
        }
        data.close();
        goals.close();

        contentValues.put(KEY_CAL, categories.get(KEY_CAL));
        contentValues.put(KEY_FAT, categories.get(KEY_FAT));
        contentValues.put(KEY_CARB, categories.get(KEY_CARB));
        contentValues.put(KEY_SODIUM, categories.get(KEY_SODIUM));
        contentValues.put(KEY_PROTEIN, categories.get(KEY_PROTEIN));

        long result = db.insert(TABLE2_NAME, null, contentValues);
        if (result == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    /*  Deletes the instance of food consumed on the given date and time with the given name from the log.
     */
    public void deleteLogData(SQLiteDatabase db, String name, String date, String time) {
        String whereClause = KEY_FOODNAME + " = ? and " + KEY_DATE + " = ? and " + KEY_TIME + " = ?";
        String[] whereArgs = new String[] {name, date, time};
        db.delete(TABLE2_NAME, whereClause, whereArgs);
    }

    /*  Fetches all log data stored in the database. Returns a Cursor with the data.
     */
    public Cursor fetchAllLogData(SQLiteDatabase db) {
        Cursor c = db.query(TABLE2_NAME, null, null, null, null, null, null);
        return c;
    }

    /*  Fetches log data for a specific date. Returns a Cursor with the data.
     */
    public Cursor fetchLogDataFromDate(SQLiteDatabase db, String date) {
        String whereClause = KEY_DATE + " = ?";
        String[] whereArgs = new String[] {date};
        Cursor c = db.query(TABLE2_NAME, null, whereClause, whereArgs, null, null, null);
        return c;
    }

    /*  Calculates the progress towards current goals for the log data for the given date.
        Returns a mapping from category name (string) to fractional progress (double).
        The fractional progress is equal to the total amount consumed for the category for the given date
        divided by the goal amount. If this progress is greater than 1, the goal has been surpassed.
     */
    public Map<String, Double> getProgressTowardGoals(SQLiteDatabase db, String date) {
        Cursor logData = this.fetchLogDataFromDate(db, date);
        Cursor goals = this.fetchGoals(db);
        Map<String, Double> progress = new HashMap<String, Double>();
        for (goals.moveToFirst(); !goals.isAfterLast(); goals.moveToNext()) {
            String category = goals.getString(goals.getColumnIndex(KEY_CATEGORY));
            progress.put(category, 0.0);
        }
        for (logData.moveToFirst(); !logData.isAfterLast(); logData.moveToNext()) {
            for (goals.moveToFirst(); !goals.isAfterLast(); goals.moveToNext()) {
                String category = goals.getString(goals.getColumnIndex(KEY_CATEGORY));
                int value = logData.getInt(logData.getColumnIndex(category));
                int goalValue = goals.getInt(goals.getColumnIndex(KEY_VALUE));
                double fractionOfGoal = value / (double)goalValue; //cast to double for floating point calculation
                progress.put(category, progress.get(category) + fractionOfGoal);
            }
        }
        logData.close();
        goals.close();
        return progress;
    }

    /*  Finds the lowest fractional progress towards the current goals for the given date.
        Returns the name of the category with the lowest progress. If all fractional progresses are greater
        than or equal to 1 (all goals have been passed), returns "all goals passed" instead.
     */
    public String getLowestProgress(SQLiteDatabase db, String date) {
        Map<String, Double> progress = this.getProgressTowardGoals(db, date);
        double min = 1.0;
        String lowest = "all goals passed";
        for (Map.Entry<String, Double> entry : progress.entrySet()) {
            if (entry.getValue() < min) {
                min = entry.getValue();
                lowest = entry.getKey();
            }
        }
        return lowest;
    }

    /*  Inserts default goals. This method should be called upon creation of the database.
        For editing goals (adding, deleting, editing information), call the methods below this method instead.
     */
    public void insertDefaultGoals(SQLiteDatabase db) {
        ContentValues calValues = new ContentValues();
        calValues.put(KEY_CATEGORY, KEY_CAL);
        calValues.put(KEY_GOAL, 1);
        calValues.put(KEY_VALUE, 2000);
        calValues.put(KEY_DESCRIPTION, "Consume about 2000 calories per day");
        calValues.put(KEY_PROGRESS, "You're doing great! You have eaten an average of 1800 calories per day over the last 2 weeks! Proper calorie intake is essential in maintaining a healthy weight");

        db.insert(TABLE3_NAME, null, calValues);

        ContentValues fatValues = new ContentValues();
        fatValues.put(KEY_CATEGORY, KEY_FAT);
        fatValues.put(KEY_GOAL, 0);
        fatValues.put(KEY_VALUE, 70);
        fatValues.put(KEY_DESCRIPTION, "Eat around 70g of Fat per day");
        fatValues.put(KEY_PROGRESS, "This is a new goal of yours so we don't have much to grade your performance off of.  Keep working hard and eating lean foods!");

        db.insert(TABLE3_NAME, null, fatValues);

        ContentValues carbValues = new ContentValues();
        carbValues.put(KEY_CATEGORY, KEY_CARB);
        carbValues.put(KEY_GOAL, 0);
        carbValues.put(KEY_VALUE, 1);
        carbValues.put(KEY_DESCRIPTION, "not initialized");
        carbValues.put(KEY_PROGRESS, "not initialized");

        db.insert(TABLE3_NAME, null, carbValues);

        ContentValues sodiumValues = new ContentValues();
        sodiumValues.put(KEY_CATEGORY, KEY_SODIUM);
        sodiumValues.put(KEY_GOAL, 0);
        sodiumValues.put(KEY_VALUE, 1);
        sodiumValues.put(KEY_DESCRIPTION, "not initialized");
        sodiumValues.put(KEY_PROGRESS, "not initialized");

        db.insert(TABLE3_NAME, null, sodiumValues);

        ContentValues proteinValues = new ContentValues();
        proteinValues.put(KEY_CATEGORY, KEY_PROTEIN);
        proteinValues.put(KEY_GOAL, 1);
        proteinValues.put(KEY_VALUE, 25);
        proteinValues.put(KEY_DESCRIPTION, "Eat about 25g of Protein per day");
        proteinValues.put(KEY_PROGRESS, "Historically you meet your goal on 70% of days.  Deficiencies in protein can lead to exhaustion and headaches.");

        db.insert(TABLE3_NAME, null, proteinValues);
    }

    /*  Modifies all of the goal information for a specific category. Returns true if successful and false otherwise.
        The category string should be one of the following: "calories", "totalfat", "carbohydrates", "sodium", or
        "proteins".
        The goal integer should be 1 if the goal exists (created) and 0 if it does not (deleted).
        The value integer should be equal to the value entered for the goal (the units are whatever units are used
        in the database for the categories).
        The description string should be a text description of the goal.
        The progress string should be a text description of the user's progress towards the goal.
     */
    public boolean modifyEntireGoal(SQLiteDatabase db, String category, int goal, int value, String description, String progress) {
        String whereClause = KEY_CATEGORY + " = ?";
        String[] whereArgs = new String[] {category};
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_GOAL, goal);
        contentValues.put(KEY_VALUE, value);
        contentValues.put(KEY_DESCRIPTION, description);
        contentValues.put(KEY_PROGRESS, progress);

        int result = db.update(TABLE3_NAME, contentValues, whereClause, whereArgs);
        if (result == 1) {
            return true;
        }
        else {
            return false;
        }
    }

    /*  Modifies whether or not the goal exists for a specific category. Returns true if successful and false otherwise.
        The category string should be one of the following: "calories", "totalfat", "carbohydrates", "sodium", or
        "proteins".
        A goal value of 0 means the goal does not exist (deleted).
        A goal value of 1 means the goal does exist (created).
        This method should be called when deleting or creating a goal for a category.
        Editing an existing goal should not require calling this method.
     */
    public boolean modifyIsGoal(SQLiteDatabase db, String category, int goal) {
        String whereClause = KEY_CATEGORY + " = ?";
        String[] whereArgs = new String[] {category};
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_GOAL, goal);

        int result = db.update(TABLE3_NAME, contentValues, whereClause, whereArgs);
        if (result == 1) {
            return true;
        }
        else {
            return false;
        }
    }

    /*  Modifies the goal value (integer) for a specific category. Returns true if successful and false otherwise.
        The category string should be one of the following: "calories", "totalfat", "carbohydrates", "sodium", or
        "proteins".
        The value integer should be equal to the value entered for the goal (the units are whatever units are used
        in the database for the categories).
     */
    public boolean modifyGoalValue(SQLiteDatabase db, String category, int value) {
        String whereClause = KEY_CATEGORY + " = ?";
        String[] whereArgs = new String[] {category};
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_VALUE, value);

        int result = db.update(TABLE3_NAME, contentValues, whereClause, whereArgs);
        if (result == 1) {
            return true;
        }
        else {
            return false;
        }
    }

    /*  Modifies the goal description (string) for a specific category. Returns true if successful and false otherwise.
        The category string should be one of the following: "calories", "totalfat", "carbohydrates", "sodium", or
        "proteins".
        The description string should be a text description of the goal.
     */
    public boolean modifyGoalDescription(SQLiteDatabase db, String category, String description) {
        String whereClause = KEY_CATEGORY + " = ?";
        String[] whereArgs = new String[] {category};
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_DESCRIPTION, description);

        int result = db.update(TABLE3_NAME, contentValues, whereClause, whereArgs);
        if (result == 1) {
            return true;
        }
        else {
            return false;
        }
    }

    /*  Modifies the goal progress (string) for a specific category. Returns true if successful and false otherwise.
        The category string should be one of the following: "calories", "totalfat", "carbohydrates", "sodium", or
        "proteins".
        The progress string should be a text description of the user's progress towards the goal.
     */
    public boolean modifyGoalProgress(SQLiteDatabase db, String category, String progress) {
        String whereClause = KEY_CATEGORY + " = ?";
        String[] whereArgs = new String[] {category};
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_PROGRESS, progress);

        int result = db.update(TABLE3_NAME, contentValues, whereClause, whereArgs);
        if (result == 1) {
            return true;
        }
        else {
            return false;
        }
    }

    /*  Fetches the goal information for all current goals.
     */
    public Cursor fetchGoals(SQLiteDatabase db) {
        String whereClause = KEY_GOAL + " = ?";
        String[] whereArgs = new String[] {Integer.toString(1)};
        Cursor c = db.query(TABLE3_NAME, null, whereClause, whereArgs, null, null, null);
        return c;
    }

    /*  Fetches just the categories for goals that currently exist.
     */
    public Cursor fetchGoalCategories(SQLiteDatabase db) {
        String whereClause = KEY_GOAL + " = ?";
        String[] whereArgs = new String[] {Integer.toString(1)};
        String[] columns = new String[] {KEY_CATEGORY};
        Cursor c = db.query(TABLE3_NAME, columns, whereClause, whereArgs, null, null, null);
        return c;
    }
}