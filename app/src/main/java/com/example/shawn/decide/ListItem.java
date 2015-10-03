package com.example.shawn.decide;

/**
 * Created by Fonseca on 10/2/15.
 */
public class ListItem {
    public String text;
    public String id;
    public boolean completed;

    public ListItem(String text, String id){
        this.text = text;
        this.id = id;
        this.completed = false;
    }

    public String toString(){
        return "TodoItem: text = " + text + ", id = " + id + ", completed = " + completed;
    }
}
