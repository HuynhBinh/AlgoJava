package com.binh;

/**
 * Created by HuynhBinh on 3/17/16.
 */
public class Vertex
{
    public String name;
    public boolean isVisited;
    public int predecessor;
    public int distance;

    public  Vertex(){};

    @Override
    public String toString()
    {
        return name + " " + predecessor + " " + distance + " "  + isVisited;
    }
}
