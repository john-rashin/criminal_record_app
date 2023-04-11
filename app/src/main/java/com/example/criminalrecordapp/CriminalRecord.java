package com.example.criminalrecordapp;


public class CriminalRecord {
    private int id;
    private String name;
    private int age;
    private String date;
    private int offenseId;
    private int locationId;
    private int policeDepartmentId;
    private String getOffenseType;
    private String getOffenseDescription;
    private String getMunicipality;
    private String getProvince;
    private String getStationName;
    private String getStationAddress;

    public CriminalRecord() {
        this.id = id;
        this.name = name;
        this.age = age;
        this.date = date;
        this.offenseId = offenseId;
        this.locationId = locationId;
        this.policeDepartmentId = policeDepartmentId;
    }

    public String getOffenseType() {
        return getOffenseType;
    }

    public String getOffenseDescription() {
        return getOffenseDescription;
    }

    public String getMunicipality() {
        return getMunicipality;
    }

    public String getProvince() {
        return getProvince;
    }

    public String getStationName() {
        return getStationName;
    }

    public String getStationAddress() {
        return getStationAddress;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getOffenseId() {
        return offenseId;
    }

    public void setOffenseId(int offenseId) {
        this.offenseId = offenseId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getPoliceDepartmentId() {
        return policeDepartmentId;
    }

    public void setPoliceDepartmentId(int policeDepartmentId) {
        this.policeDepartmentId = policeDepartmentId;
    }


}
