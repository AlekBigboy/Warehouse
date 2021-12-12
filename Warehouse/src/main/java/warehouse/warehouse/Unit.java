package warehouse.warehouse;

import java.util.Date;

public class Unit {
    Integer id;
    String name;
    String user;
    Date date;
    String note;

    public Unit(){

    }


    public Unit(Integer id,String name, String user, Date date, String note) {
        this.id = id;
        this.name = name;
        this.user = user;
        this.date = date;
        this.note = note;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
