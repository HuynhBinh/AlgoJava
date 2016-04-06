package com.binh;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by HuynhBinh on 3/17/16.
 */
public class Graph
{
    int[][] matrix;
    int vertexCount;
    ArrayList<Vertex> vertices;
    Queue<Integer> queue;

    public Graph(int vertexCount)
    {
        this.matrix = new int[vertexCount][vertexCount];
        this.vertexCount = vertexCount;
        this.queue = new LinkedList<>();
        this.vertices = new ArrayList<>();

        initMatrix();

    }

    private void initMatrix()
    {
        for (int i = 0; i < this.vertexCount; i++)
        {
            for (int j = 0; j < vertexCount; j++)
            {
                matrix[i][j] = -1;
            }
        }
    }

    public void addEdge(int from, int to, int distance)
    {
        this.matrix[from][to] = distance;
        this.matrix[to][from] = distance;
    }

    public void addEdge(int from, int to)
    {
        this.matrix[from][to] = 0;
        this.matrix[to][from] = 0;
    }

    public void addVertex(String name)
    {
        Vertex vertex = new Vertex();
        vertex.name = name;
        vertex.isVisited = false;
        this.vertices.add(vertex);
    }

    private int getDistanceBetween2Vertices(int from, int to)
    {
        int distance = matrix[from][to];

        return distance;
    }

    private int findAdjacencyUnVisitedVertex(int sourceVertex)
    {
        for (int i = 0; i < this.vertexCount; i++)
        {
            if (this.matrix[sourceVertex][i] != -1 && this.vertices.get(i).isVisited == false)
            {
                return i;
            }
        }

        return -1;
    }


    private void printVertex(int index)
    {
        System.out.println(vertices.get(index).name);
    }

    public void findPath()
    {
        // set the first vertex = true = visited
        vertices.get(0).isVisited = true;

        // set predecessor of 0 is -1
        vertices.get(0).predecessor = -1;

        // add the first vertex into queue
        queue.add(0);

        //print visited vertex
        printVertex(0);


        while (!queue.isEmpty())
        {
            int currentVertexIndex = queue.poll();

            while (findAdjacencyUnVisitedVertex(currentVertexIndex) != -1)
            {
                int adjacencyVertexIndex = findAdjacencyUnVisitedVertex((currentVertexIndex));

                vertices.get(adjacencyVertexIndex).isVisited = true;
                vertices.get(adjacencyVertexIndex).predecessor = currentVertexIndex;
                vertices.get(adjacencyVertexIndex).distance = vertices.get(currentVertexIndex).distance + getDistanceBetween2Vertices(currentVertexIndex, adjacencyVertexIndex);

                queue.add(adjacencyVertexIndex);

                //print visited vertex
                printVertex(adjacencyVertexIndex);
            }
        }

        // print break line
        System.out.println();


        for(int i = 0; i < this.vertexCount ; i++)
        {
            printDistance(i);
            printPath(i);
        }

    }

    public void printDistance(int index)
    {
        System.out.println(vertices.get(index).distance);
    }

    public void printPath(int index)
    {

        if (vertices.get(index).predecessor == -1)
        {
            System.out.println(vertices.get(index).name + " : End");
            return;
        }

        System.out.print(vertices.get(index).name + "->");
        printPath(vertices.get(index).predecessor);

    }


}
