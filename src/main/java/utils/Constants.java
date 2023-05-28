package utils;

import lombok.Getter;

@Getter
public class Constants {

    private static Constants instance;

    private Constants(){ }

    public Constants getInstance(){
        if (instance == null)
            instance = new Constants();
        return instance;
    }

    public static String MYSQL_IP = "134.209.239.154";
    public static String MYSQL_DB = "bp_tim58";
    public static String MYSQL_USERNAME = "writer";
    public static String MYSQL_PASSWORD = "KVmBBnYAK7HZjs4E";

}
