package uni;

import java.util.ArrayList;
import java.util.HashMap;

import SearchAlgorithm.ISearch;

public class Graph {

	private HashMap<String, Node> graph = new HashMap<String, Node>();
	private String pathInforamtion = "";
	private ArrayList<Node> path=new ArrayList<Node>();
	private String fullInformation="";
	private static int a;
    static  {
      a=12;
    }
    public static void as(){
    	
    }
	public void resetGraph() {
		as();
		for (Node value : graph.values()) {
			value.resetNode();
		}
		pathInforamtion = "";
		path.clear();
		setFullInformation("");
	}

	public void addNode(Node n) {
		graph.put(n.getName(), n);
	}

	public boolean searchPath(String from, String to, ISearch algorithm) {

		return algorithm.search(from, to);
	}

	public boolean addRoute(Node from, Node to, double lenght, String type) {

		Link link = new Link(to, lenght, type);
		from.getLinks().add(link);
		return true;
	}

	public void addTwoWayRoute(Node from, Node to, double lenght, String type) {
		addRoute(from, to, lenght, type);
		addRoute(to, from, lenght, type);
	}

	public HashMap<String, Node> getMap() {
		return graph;
	}

	public String getPathInforamtion() {
		return pathInforamtion;
	}

	public void setPathInforamtion(String pathInforamtion) {
		this.pathInforamtion = pathInforamtion;
	}

	public ArrayList<Node> getPath() {
		return path;
	}

	public void setPath(ArrayList<Node> path) {
		this.path = path;
	}

	public String getFullInformation() {
		return fullInformation;
	}

	public void setFullInformation(String fullInformation) {
		this.fullInformation = fullInformation;
	}

}
