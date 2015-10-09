package com.example.shawn.decide;

/**
 * Created by Fonseca on 10/2/15.
 */
public class ListItem {
    public String text; //item name
    public String id; //list it belongs to
    public boolean completed; //if user completes task

    public ListItem(String text, String id, boolean completed){
        this.text = text;
        this.id = id;
        this.completed = completed;
    }

    public String toString(){
        return "TodoItem: text = " + text + ", id = " + id + ", completed = " + completed;
    }
}
