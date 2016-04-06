package com.binh;

import java.util.ArrayList;

/**
 * Created by HuynhBinh on 3/17/16.
 */
public class DijkstraGraph2
{
    int[][] matrix;
    int vertexCount;

    ArrayList<DijkstraVertex> vertices;

    int[] unSettleVertices;
    int[] settleVertices;

    public DijkstraGraph2(int vertexCount)
    {
        this.vertexCount = vertexCount;

        this.matrix = new int[vertexCount][vertexCount];

        this.vertices = new ArrayList<>();

        this.unSettleVertices = new int[vertexCount];
        this.settleVertices = new int[vertexCount];
        initSettleNodes();
        initUnsettleNodes();
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

    private void initUnsettleNodes()
    {
        for(int i = 0 ; i < unSettleVertices.length; i ++)
        {
            unSettleVertices[i] = -1;
        }
    }

    private  void initSettleNodes()
    {
        for(int i = 0; i < settleVertices.length; i++)
        {
            settleVertices[i] = -1;
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
            if ((this.matrix[sourceVertex][i] != -1) && (!isVertexExistInSettleNode(i)) && (this.vertices.get(i).isVisited == false))
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

    private boolean isVertexExistInSettleNode(int vertexIndex)
    {
        for(int i = 0; i < settleVertices.length; i++)
        {
            if(settleVertices[i] == vertexIndex)
            {
                return true;
            }
        }

        return false;
    }


    private void addVertexToSettleNodes(int vertexIndex)
    {
        for(int i = 0; i < settleVertices.length; i++)
        {
            if(settleVertices[i] == -1)
            {
                settleVertices[i] = vertexIndex;
                return;
            }
        }

    }


    // add vertex to unsettle nodes
    // vertexIndex: is the index of vertex in the ArrayList<DijkstraVertex> vertices;
    private void addVertexToUnsettleNodes(int vertexIndex)
    {
        for (int i = 0; i < unSettleVertices.length; i++)
        {
            if (unSettleVertices[i] == vertexIndex)
            {
                return;
            }

            if(unSettleVertices[i] == -1)
            {
                unSettleVertices[i] = vertexIndex;
                return;
            }
        }

    }


    // remove vertex from un settle nodes
    // vertexIndex: is the index of vertex in the ArrayList<DijkstraVertex> vertices;
    private void removeVertexFromUnsettleNodes(int vertexIndex)
    {
        for (int i = 0; i < unSettleVertices.length; i++)
        {
            if (unSettleVertices[i] == vertexIndex)
            {
                for(int j = i ; j < unSettleVertices.length; j++)
                {
                    if(j == (unSettleVertices.length -1))
                    {

                        unSettleVertices[j] = -1;
                    }
                    else
                    {
                        unSettleVertices[j] = unSettleVertices[j + 1];
                    }
                }

                return;
            }
        }

    }

    private int findVertexWithLowestDistanceInUnSettleNodes()
    {
        int lowestDistanceIndex = this.unSettleVertices[0];

        for (int i = 1; i < this.unSettleVertices.length; i++)
        {
            int index = this.unSettleVertices[i];

            if(index != -1)
            {
                if (this.vertices.get(index).distance < this.vertices.get(lowestDistanceIndex).distance)
                {
                    lowestDistanceIndex = index;
                }

            }
            else
            {
                break;
            }
        }

        return lowestDistanceIndex;

    }

    private boolean isEmptyUnsettleNodes()
    {
        if(unSettleVertices[0] == -1)
            return true;
        else
            return false;
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


        // set distance from first node to 0
        vertices.get(sourceIndex).distance = 0;

        // predecessor of first node is null
        vertices.get(sourceIndex).predecessor = -1;

        // add source node to un settle nodes
        addVertexToUnsettleNodes(sourceIndex);
        //this.unSettleVertices.add(0);

        //print visited vertex
        printVertex(sourceIndex);


        while (!isEmptyUnsettleNodes())
        {
            // get the node with lowest distance in the stack and start find neighbour from this node
            int evaluationNodeIndex = findVertexWithLowestDistanceInUnSettleNodes();

            // remove evaluation node from un settle nodes
            removeVertexFromUnsettleNodes(evaluationNodeIndex);
            //this.unSettleVertices.remove(evaluationNodeIndex);

            // add evaluation node into settle nodes
            addVertexToSettleNodes(evaluationNodeIndex);
            //this.settleVertices.add(evaluationNodeIndex);


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
