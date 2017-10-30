import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

/**
 * 
 */

/**
 * @author kritika
 *
 */


public class DisjointSets {

	/**
	 * @param args
	 */	
	static int min = 1;
	static int max;
	static HashSet<Integer> set = new HashSet<>();
	static HashMap<Integer, Integer> mapRank = new HashMap<>();
	static HashMap<Integer, Integer> mapParent = new HashMap<>();
	static HashMap<Integer, HashSet<Integer>> mapEdges = new HashMap<>();
	static ArrayList<String> edgeList = new ArrayList<>();

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		System.out.println("enter n");
		Scanner scanner = new Scanner(System.in);
		max = scanner.nextInt();
		scanner.close();
		
		for(int i=1;i<=max;i++){
			mapRank.put(i, 0);
			mapParent.put(i, i);
		}
		
		int firstVertex, secondVertex;
		while (set.size() != max) {
			firstVertex = (int) ((Math.random() * (max + 1 - min)) + min);
			secondVertex = (int) ((Math.random() * (max + 1 - min)) + min);
			while(firstVertex == secondVertex){
				secondVertex = (int) ((Math.random() * (max + 1 - min)) + min);
			}
			
			System.out.println("Selected vertices: (" + firstVertex + ", " + secondVertex + ")");
			
			if (mapEdges.containsKey(firstVertex) && mapEdges.containsKey(secondVertex)) {
				mapEdges.get(firstVertex).add(secondVertex);
				mapEdges.get(secondVertex).add(firstVertex);
			} else if (mapEdges.containsKey(firstVertex) && !mapEdges.containsKey(secondVertex)) {
				mapEdges.get(firstVertex).add(secondVertex);
				HashSet<Integer> val = new HashSet<>();
				val.add(firstVertex);
				mapEdges.put(secondVertex, val);
			} else if (!mapEdges.containsKey(firstVertex) && mapEdges.containsKey(secondVertex)) {
				mapEdges.get(secondVertex).add(firstVertex);
				HashSet<Integer> val = new HashSet<>();
				val.add(secondVertex);
				mapEdges.put(firstVertex, val);
			} else {
				HashSet<Integer> val1 = new HashSet<>();
				HashSet<Integer> val2 = new HashSet<>();
				val1.add(firstVertex);
				mapEdges.put(secondVertex, val1);
				val2.add(secondVertex);
				mapEdges.put(firstVertex, val2);
			}
			
			Union(firstVertex, secondVertex);
			System.out.println("Connected set: \n" + set + "\n");
		}
		
		System.out.println("The edges are: \n");
		for (int i = 0; i < edgeList.size(); i++) {
			System.out.println(edgeList.get(i));
		}
		
		System.out.println("The minimum number edges required to generate a connected graph of "+max+" vertices: "+edgeList.size());
		
		long duration = System.currentTimeMillis() - startTime;
		System.out.println("The running time of Disjoint-set algorithm is: "+ duration + " milliseconds.\n");
	}

	private static int FindV(int vertex) {
		
		int parent = mapParent.get(vertex);
		if(parent == vertex)
		{
			return vertex;
		}
		else
		{
			return FindV(parent);
		}
	}
	
	public static void Union(int x, int y){
		int parent1 = FindV(x);
		int parent2 = FindV(y);
		
		int rank1 = getRank(parent1);
		int rank2 = getRank(parent2);
		
		if(parent1 != parent2){  
			if(rank1 == rank2){
				putValueIntoMap(mapParent,parent2, parent1);
				putValueIntoMap(mapRank, parent1, ++rank1);
			}else if(rank1 > rank2){
				putValueIntoMap(mapParent, parent2, parent1);
			}else{
				putValueIntoMap(mapParent, parent1, parent2);
			}
		}
		
		if(!set.isEmpty()){
			if(set.contains(x) && set.contains(y)){
				if(!existingEdge(edgeList, x, y))
					edgeList.add(x + "----" + y);
				return;
			}
			else if(set.contains(x) || set.contains(y))
				addVal(x,y);
			else
				edgeList.add(x + "----" + y);
		}else{
			addVal(x,y);
		}
	}
	
	public static int getRank(int num){
		return mapRank.get(num);
	}
	
	public static void putValueIntoMap(HashMap<Integer,Integer> m, int key, int val){
		m.put(key, val);
	}

	static void addVal(int a, int b) {
		if(mapEdges.containsKey(a)){
			hasVertex(a);
		}
		if(mapEdges.containsKey(b)){
			hasVertex(b);
		}
		set.add(a);
		set.add(b);
		if(!existingEdge(edgeList, a, b))
			edgeList.add(a + "----" + b);
	}
	
	static void hasVertex(int x){
		HashSet<Integer> s = mapEdges.get(x);
		Iterator<Integer> i = s.iterator();
		int temp;
		while(i.hasNext()){
			temp = i.next();
			if(mapEdges.containsKey(temp) && !set.contains(temp)){
				set.add(temp);
				hasVertex(temp);
			}
		}
	}
	
	static boolean existingEdge(ArrayList<String> list, int v1, int v2){
		for(int i=0;i<list.size();i++){
			if(list.contains(v1+"----"+v2) || list.contains(v2+"----"+v1)){
				return true;
			}
		}
		return false;
	}
	
}
