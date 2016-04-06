package com.binh;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by HuynhBinh on 3/17/16.
 */
public class DijkstraGraph
{
    int[][] matrix;
    int vertexCount;
    ArrayList<DijkstraVertex> vertices;

    ArrayList<Integer> unSettleVertices;
    ArrayList<Integer> settleVertices;

    public DijkstraGraph(int vertexCount)
    {
        this.matrix = new int[vertexCount][vertexCount];
        this.vertexCount = vertexCount;
        this.vertices = new ArrayList<>();
        this.unSettleVertices = new ArrayList<>();
        this.settleVertices = new ArrayList<>();

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

    public void addVertex(String name)
    {
        DijkstraVertex vertex = new DijkstraVertex();
        vertex.name = name;
        this.vertices.add(vertex);
    }

    private int getDistanceBetween2Vertices(int from, int to)
    {
        int distance = matrix[from][to];

        return distance;
    }


    private int findAdjacencyVertex(int sourceVertex)
    {
        for (int i = 0; i < this.vertexCount; i++)
        {
            if ((this.matrix[sourceVertex][i] != -1) && (!this.settleVertices.contains(i)) && (this.vertices.get(i).isVisited == false))
            {
                return i;
            }
        }

        return -1;
    }


    private void resetVertexToUnvisitedState(int sourceVertex)
    {
        for (int i = 0; i < this.vertexCount; i++)
        {
            if (this.matrix[sourceVertex][i] != -1)
            {
                this.vertices.get(i).isVisited = false;
            }
        }
    }


    // add vertex to unsettle nodes
    // vertexIndex: is the index of vertex in the ArrayList<DijkstraVertex> vertices;
    private void addVertexToUnsettleNodes(int vertexIndex)
    {

        for (int i = 0; i < this.unSettleVertices.size(); i++)
        {
            if (this.unSettleVertices.get(i) == vertexIndex)
            {
                return;
            }
        }


        this.unSettleVertices.add(vertexIndex);
    }


    // remove vertex from un settle nodes
    // vertexIndex: is the index of vertex in the ArrayList<DijkstraVertex> vertices;
    private void removeVertexFromUnsettleNodes(int vertexIndex)
    {
        for (int i = 0; i < this.unSettleVertices.size(); i++)
        {
            if (this.unSettleVertices.get(i) == vertexIndex)
            {
                this.unSettleVertices.remove(i);
                return;
            }
        }

    }

    private int findVertexWithLowestDistanceInUnSettleNodes()
    {
        int lowestDistanceIndex = this.unSettleVertices.get(0);

        for (int i = 1; i < this.unSettleVertices.size(); i++)
        {
            int index = this.unSettleVertices.get(i);

            if (this.vertices.get(index).distance < this.vertices.get(lowestDistanceIndex).distance)
            {
                lowestDistanceIndex = index;
            }
        }

        return lowestDistanceIndex;

    }

    private void printVertex(int index)
    {
        System.out.println(vertices.get(index).name);
    }




    public void dijkstra(int sourceIndex)
    {

        for (int i = 0; i < this.vertexCount; i++)
        {
            // set all max value to all nodes
            vertices.get(i).distance = Integer.MAX_VALUE;
        }

        // reset data
        this.settleVertices = new ArrayList<>();
        this.unSettleVertices = new ArrayList<>();

        // set distance from first node to 0
        vertices.get(sourceIndex).distance = 0;

        // predecessor of first node is null
        vertices.get(sourceIndex).predecessor = -1;

        // add source node to un settle nodes
        addVertexToUnsettleNodes(sourceIndex);
        //this.unSettleVertices.add(0);

        //print visited vertex
        printVertex(sourceIndex);


        while (!unSettleVertices.isEmpty())
        {
            // get the node with lowest distance in the stack and start find neighbour from this node
            int evaluationNodeIndex = findVertexWithLowestDistanceInUnSettleNodes();

            // remove evaluation node from un settle nodes
            removeVertexFromUnsettleNodes(evaluationNodeIndex);
            //this.unSettleVertices.remove(evaluationNodeIndex);

            // add evaluation node into settle nodes
            this.settleVertices.add(evaluationNodeIndex);


            // evaluate neighbour
            evaluateNeighbour(evaluationNodeIndex);
        }


        // print break line
        System.out.println();


        for (int i = 0; i < this.vertexCount; i++)
        {
            printDistance(i);
            printPath(i);
        }

    }


    private void evaluateNeighbour(int sourceIndex)
    {

        // find adjacency nodes which is not in the settle nodes

        while (findAdjacencyVertex(sourceIndex) != -1)
        {
            int destinationIndex = findAdjacencyVertex(sourceIndex);

            // mark this node as visited, we will reset it to un visited later #step2
            this.vertices.get(destinationIndex).isVisited = true;



            // calculate edge distance
            int edgeDistance = getDistanceBetween2Vertices(sourceIndex, destinationIndex);

            // calculate new distance from source to destination node
            int newDistance = vertices.get(sourceIndex).distance + edgeDistance;

            if (newDistance < vertices.get(destinationIndex).distance)
            {
                vertices.get(destinationIndex).distance = newDistance;
                this.vertices.get(destinationIndex).predecessor = sourceIndex;
                addVertexToUnsettleNodes(destinationIndex);
                //this.unSettleVertices.add(destinationIndex);
            }


            //print visited vertex
            printVertex(destinationIndex);

        }

        // #step2
        // reset vertex that has been visited to unvisited
        resetVertexToUnvisitedState(sourceIndex);

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
