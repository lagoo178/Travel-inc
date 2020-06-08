package com.trvl.travelinc.models;

public class Chat
{
    private String message;
    private int typing;
    private long timestamp, seen;

    public Chat()
    {

    }

    public Chat(String message, int typing, long timestamp, long seen)
    {
        this.message = message;
        this.typing = typing;
        this.timestamp = timestamp;
        this.seen = seen;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public int getTyping()
    {
        return typing;
    }

    public void setTyping(int typing)
    {
        this.typing = typing;
    }

    public long getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(long timestamp)
    {
        this.timestamp = timestamp;
    }

    public long getSeen()
    {
        return seen;
    }

    public void setSeen(long seen)
    {
        this.seen = seen;
    }
}
