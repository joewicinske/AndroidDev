package com.example.homework3;

public class GlucoseData {
    String entryDate; //
    int fasting;

    public GlucoseData(int fasting, int breakfast, int lunch, int dinner) {
        this.fasting = fasting;
        this.breakfast = breakfast;
        this.dinner = dinner;
        this.lunch = lunch;
    }

    int breakfast;
    int dinner;
    int lunch;
    int average;
    int id;
    boolean isNormal;

    public GlucoseData(){

    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public int getFasting() {
        return fasting;
    }

    public void setFasting(int fasting) {
        this.fasting = fasting;
    }

    public int getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(int breakfast) {
        this.breakfast = breakfast;
    }

    public int getDinner() {
        return dinner;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }
    public void setDinner(int dinner) {
        this.dinner = dinner;
    }

    public int getLunch() {
        return lunch;
    }

    public void setLunch(int lunch) {
        this.lunch = lunch;
    }

    public int getAverage() {
        return (getBreakfast() + getDinner() + getLunch() + getFasting() )/4;
    }

    public void setAverage(int average) {
        this.average = average;
    }

    public boolean isNormal() {
        return !(getAverage() > 99 || getAverage() < 70);
    }

    public void setNormal(boolean normal) {
        isNormal = normal;
    }
}
