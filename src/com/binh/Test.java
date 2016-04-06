package com.binh;

/**
 * Created by HuynhBinh on 3/30/16.
 */
public class Test
{
    public Test()
    {

    }

    public void changeValue(int input)
    {
        input = input + 5;
        System.out.println("Inside: " + input);
    }

    public void changeObject(Vertex vertex)
    {
        vertex.name = "new name";
        vertex.predecessor = 111;
        vertex.distance = 222;
        vertex.isVisited = true;

        vertex = new Vertex();

        System.out.println("Inside " +  vertex);
    }


    public void changeInteger(Integer input)
    {
        input = 12;

        System.out.println("Inside " + input);
    }


    public void changeDog(Dog dog)
    {
        dog.name = "new name";
        dog.age = 1234;
        dog.age = 333;

        dog = new Dog();
        dog.name = "qwe";
        dog.age = 44;

        System.out.println("Inside " + dog);
    }
}
