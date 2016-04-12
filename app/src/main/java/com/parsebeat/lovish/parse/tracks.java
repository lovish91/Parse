package com.parsebeat.lovish.parse;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by lovishbajaj on 26/03/16.
 */
@ParseClassName("tracks")
public class tracks extends ParseObject
{
    ParseObject user;
    public tracks()
    {

    }
    public String getfoo(){
        return getString("foo");
    }
    public void setfoo(String foo){
        put("foo",foo);
    }
    public String gettname(){
        return getString("tname");
    }
    public void settname(String tname){
        put("tname", tname);
    }
    public String getplays(){
        return getString("plays");
    }
    public void setplays(String plays){
        put("plays",plays);
    }
    public String gethearts(){
        return getString("hearts");
    }
    public void sethearts(String hearts){
        put("hearts", hearts);
    }
    public String getduration(){
        return getString("duration");
    }
    public void setduration(String duration){
        put("duration", duration);
    }
    public String getgenre()
    {
        return getString("genre");
    }
    public void setgenre(String genre){
        put("genre", genre);
    }
    public String getusername()
    {
       // ParseObject user = getParseObject("user");
       user = getParseObject("user");
        return user.getString("username");
    }
    public void setusername(String username)
    {
        put("username", username);
    }


    /////


}
