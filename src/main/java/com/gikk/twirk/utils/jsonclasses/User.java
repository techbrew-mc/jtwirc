package com.gikk.twirk.utils.jsonclasses;

import org.json.simple.JSONStreamAware;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.io.Writer;
import java.util.LinkedHashMap;

public class User implements JSONStreamAware
{

    private String name;
    private long amount;

    public User(String name, long amount)
    {
        this.name = name;
        this.amount = amount;
    }

    @Override
    public void writeJSONString(Writer writer) throws IOException
    {
        LinkedHashMap<String, java.io.Serializable> obj = new LinkedHashMap<>();
        obj.put("user", name);
        obj.put("amount", amount);
        JSONValue.writeJSONString(obj, writer);
    }
}