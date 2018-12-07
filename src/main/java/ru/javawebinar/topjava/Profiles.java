package ru.javawebinar.topjava;

public class Profiles {
    public static final String POSTGRES_DB = "postgres";
    public static final String HSQL_DB = "hsqldb";

    public static final String JDBC = "jdbc";
    public static final String JPA = "jpa";
    public static final String DATAJPA = "datajpa";

    public static final String REPOSITORY_IMPLEMENTATION = DATAJPA;

    public static String getActiveDbProfile(){
        try{
            Class.forName("org.postgresql.Driver");
            return POSTGRES_DB;
        }catch (ClassNotFoundException e){
            try{
                Class.forName("org.hsqldb.jdbcDriver");
                return Profiles.HSQL_DB;
            }catch (ClassNotFoundException ex){
                throw new IllegalStateException("Could not resolve DB profile");
            }
        }
    }
}
