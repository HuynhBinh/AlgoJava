package com.binh;

public class Main
{

    public static void main(String[] args)
    {

        /*DijkstraGraph2 graph = new DijkstraGraph2(6);

        graph.addVertex("A"); //0
        graph.addVertex("B"); //1
        graph.addVertex("C"); //2
        graph.addVertex("D"); //3
        graph.addVertex("E"); //4
        graph.addVertex("Z"); //5

        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 2, 2);

        graph.addEdge(1, 2, 1);
        graph.addEdge(1, 3, 5);

        graph.addEdge(2, 3, 8);
        graph.addEdge(2, 4, 10);

        graph.addEdge(3, 4, 2);
        graph.addEdge(3, 5, 6);

        graph.addEdge(4, 5, 3);


        graph.dijkstra(0);*/


        int data = 2;
        System.out.println("Before " + data);
        Test test = new Test();
        test.changeValue(data);
        System.out.println("After " + data);


        Vertex vertex = new Vertex();
        vertex.name = "abc";
        vertex.predecessor = 2;
        vertex.isVisited = false;
        vertex.distance = 1;

        System.out.println("Before " + vertex);


        test.changeObject(vertex);

        System.out.println("After " + vertex);


        Integer input = 1;
        System.out.println("Before " + input);
        test.changeInteger(input);
        System.out.println("After " + input);


        Dog dog = new Dog();
        dog.name = "lulu";
        dog.age = 11;

        System.out.println("Before " + dog);
        test.changeDog(dog);
        System.out.println("After " + dog);


    }


}
