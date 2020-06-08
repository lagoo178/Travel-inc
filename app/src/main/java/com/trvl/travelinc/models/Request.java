package com.trvl.travelinc.models;

public class Request
{
    private String type;

    public Request()
    {

    }

    public Request(String type)
    {
        this.type = type;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }
}
