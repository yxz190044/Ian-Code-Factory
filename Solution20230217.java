import java.io.*;
import java.util.*;

class GraphNode {
    List<Integer> neighbors = new ArrayList<>();
}

public class Solution20230217 {
    
    int[][] data;
    int[] xCoordinate;
    int[] yCoordinate;
    int m;
    GraphNode[] graph;
    
    public static void funcDrop(int[] xCoordinate, int[] yCoordinate) {
        
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        m = xCoordinate.length;
        buildGraph();
        
        
    }
    
    private static void buildGraph() {
        graph = new GraphNode[m];
        for (int i = 0; i < m; i++) {
            graph[i] = new GraphNode();
        }
        data = new int[m][3];
        for (int i = 0; i < m; i++) {
            data[i][0] = [i];
            data[i][1] = xCoordinate[i];
            data[i][2] = yCoordinate[i];
        }
        
        sortByX();
        for (int i = 0; i < m; i++) {
            if (i > 0 && data[i - 1][1] == data[i][1]) {
                graph[data[i][0]].neighbors.add(data[i - 1][0]);
            }
            
            if (i < m - 1 && data[i + 1][1] == data[i][1]) {
                graph[data[i][0]].neighbors.add(data[i + 1][0]);
            }
        }
        
        sortByY();
        for (int i = 0; i < m; i++) {
            if (i > 0 && data[i - 1][2] == data[i][2]) {
                graph[data[i][0]].neighbors.add(data[i - 1][0]);
            }
            
            if (i < m - 1 && data[i + 1][2] == data[i][2]) {
                graph[data[i][0]].neighbors.add(data[i + 1][0]);
            }
        }
    }
    
    private static void sortByX() {
        
        Arrays.sort(data, new Comparator<>() {
            @Override
            public int compare(int[] arr1, int[] arr2) {
                if (arr1[1] != arr2[1]) {
                    return arr1[1] < arr2[1] ? -1 : 1;
                }
                return arr1[2] < arr2[2] ? -1 : 1;
            }
        });
        
    }
	
    private static void sortByY() {
        
        Arrays.sort(data, new Comparator<>() {
            @Override
            public int compare(int[] arr1, int[] arr2) {
                if (arr1[2] != arr2[2]) {
                    return arr1[2] < arr2[2] ? -1 : 1;
                }
                return arr1[1] < arr2[1] ? -1 : 1;
            }
        });
        
    }
}

