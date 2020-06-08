package com.trvl.travelinc.models;

public class Message
{
    private String message, type, from, to;
    private long timestamp;

    public Message()
    {

    }

    public Message(String message, String type, String from, String to, long timestamp)
    {
        this.message = message;
        this.type = type;
        this.from = from;
        this.to = to;
        this.timestamp = timestamp;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getFrom()
    {
        return from;
    }

    public void setFrom(String from)
    {
        this.from = from;
    }

    public String getTo()
    {
        return to;
    }

    public void setTo(String to)
    {
        this.to = to;
    }

    public long getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(long timestamp)
    {
        this.timestamp = timestamp;
    }
}
