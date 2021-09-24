package com.mycompany.tasklistapp;

public class Item {
    private String title;
    private String desc;
    private boolean done;

    public Item() {
    }

    public Item(String title, String desc, boolean done) {
        this.title = title;
        this.desc = desc;
        this.done = done;
    }
    
    @Override
    public String toString(){
        return ("{title="+title+", desc="+desc+", done="+done+"}");
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
    
}