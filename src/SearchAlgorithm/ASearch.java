package SearchAlgorithm;

import java.util.ArrayList;
import java.util.HashMap;

import uni.Graph;
import uni.Link;
import uni.Node;

public abstract class ASearch implements ISearch {
	protected Graph myGraph = new Graph();
	protected HashMap<String, Node> myMap = new HashMap<String, Node>();
	private ArrayList<Node> pathList = new ArrayList<Node>();

	public ASearch(Graph graph) {
		this.myGraph = graph;
		this.myMap = myGraph.getMap();
	}

	protected void setDepth(Node n) {
		for (Link l : n.getLinks()) {
			if (l.getToNode().getDepth() == -1)
				l.getToNode().setDepth(n.getDepth() + 1);
		}
	}

	private void path(Node node, String[] pathInfo) {
		// System.out.println(node.getName());
		if (node.getDepth() == 0) {
			pathList.add(node);
			return; 
		}
		for (Link l : node.getLinks()) {
			if (l.getToNode().getDepth() == (node.getDepth() - 1)) {
				for (int i = 0; i < pathInfo.length; i++) {
					if (l.getToNode().getName().equals(pathInfo[i])) {
						pathList.add(node);
						path(l.getToNode(), pathInfo);
						return;
					}
				}

			}

		}

	}

	protected ArrayList<Node> printPath(Node node) {
		String[] pathInfo = myGraph.getPathInforamtion().split(",");
		for (int i = 0; i < pathInfo.length; i++) {
			pathInfo[i] = pathInfo[i].replaceAll("^\\s+|\\s+$", "");

		}
		path(node, pathInfo);
		return pathList;
	}

	protected ArrayList<Node> parentPrintPath(Node from, Node to) {

		if (from.getParent().equals(to)) {
			pathList.add(from);
			pathList.add(to);
			return pathList;
		}
		pathList.add(from);
		parentPrintPath(from.getParent(), to);
		return pathList;
	}

}
