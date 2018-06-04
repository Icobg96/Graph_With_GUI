package Controllers;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Stack;
import java.util.stream.Collectors;

import SearchAlgorithm.EscapeByTypeAndWeight;
import SearchAlgorithm.ISearch;
import SearchAlgorithm.IntermediateNodesSearch;
import uni.Graph;
import uni.Link;
import uni.Node;

public class GraphController {
	Graph g;

	private static GraphController graphController = null;
	private ArrayList<String> intermediateNodes = new ArrayList<String>();

	private GraphController() {
		g = new Graph();
		Node a = new Node("A", 3, 0, 1);
		Node b = new Node("B", 6, 1, 1);
		Node c = new Node("C", 10, 3, 3);
		Node d = new Node("D", 4, 4, 2);
		Node e = new Node("E", 2, 5, 6);
		Node f = new Node("F", 5, 2, 4);
		Node j = new Node("J", 7, 6, 6);

		g.addNode(a);
		g.addNode(b);
		g.addNode(c);
		g.addNode(d);
		g.addNode(e);
		g.addNode(f);
		g.addNode(j);

		g.addTwoWayRoute(a, b, 8, " ");
		g.addTwoWayRoute(a, c, 2, " ");
		// g.addTwoWayRoute(a, d, 6, " ");
		g.addTwoWayRoute(b, d, 6, " ");
		g.addTwoWayRoute(b, e, 4, " ");
		g.addTwoWayRoute(c, e, 7, " ");
		g.addTwoWayRoute(c, f, 8, " ");
		g.addTwoWayRoute(d, e, 8, " ");
		g.addTwoWayRoute(e, f, 1, " ");
		// g.addTwoWayRoute(d, j, 0, " ");

	}

	public static GraphController getInstance() {
		if (graphController == null) {
			graphController = new GraphController();
		}

		return graphController;
	}

	public boolean search(String from, String to, String algorithmName, double weight, String type) {
		ISearch algorithm = null;

		try {
			algorithm = (ISearch) Class.forName("SearchAlgorithm." + algorithmName).getConstructor(Graph.class)
					.newInstance(new Object[] { g });
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (algorithm.getClass().equals(EscapeByTypeAndWeight.class)) {
			((EscapeByTypeAndWeight) algorithm).setWeight(weight);
			((EscapeByTypeAndWeight) algorithm).setType(type);
		}
		if (algorithm.getClass().equals(IntermediateNodesSearch.class)) {
			((IntermediateNodesSearch) algorithm).setIntermediateNodes(intermediateNodes);
		}

		return g.searchPath(from, to, algorithm);
	}

	public boolean search(String from, String to, String algorithmName) {

		return search(from, to, algorithmName, 0, null);
	}

	public void clearGraph() {
		g.getMap().clear();
	}

	public void addNodeInGraph(String name, int x, int y, double weight) {
		if (g.getMap().containsKey(name)) {
			Node node = g.getMap().get(name);
			node.setX(x);
			node.setY(y);
			node.setWeight(weight);
		} else {
			Node node = new Node(name, weight, x, y);
			g.addNode(node);
		}

	}

	public void removeNodeFromGraph(String name) {
		for (Node n : g.getMap().values()) {
			for (Link l : n.getLinks()) {
				if (l.getToNode().getName().equals(name)) {
					if (n.getLinks().remove(l)) {
						break;
					}
				}
			}
		}
		g.getMap().remove(name);

	}

	public void removeLinkFromGraph(String from, String to) {
		if (from.equals(null) || to.equals(null)) {
			return;
		}
		Node fromNode = g.getMap().get(from);
		Node toNode = g.getMap().get(to);
		for (Link l : fromNode.getLinks()) {
			if (l.getToNode().equals(toNode)) {
				if (fromNode.getLinks().remove(l)) {
					break;
				}
			}
		}

	}

	public boolean addRouteInGraph(String from, String to, double lenght, String type, boolean isTwoWay) {
		if (!g.getMap().containsKey(from) || !g.getMap().containsKey(to)) {
			return false;
		}
		if (isTwoWay) {
			g.addTwoWayRoute(g.getMap().get(from), g.getMap().get(to), lenght, type);
		} else {
			g.addRoute(g.getMap().get(from), g.getMap().get(to), lenght, type);
		}

		return true;
	}

	public ArrayList<String> getNodeNames() {
		ArrayList<String> names = new ArrayList<>();
		for (Node n : g.getMap().values()) {
			names.add(n.getName());
		}
		return names;
	}

	@SuppressWarnings("deprecation")
	public List<String> getAlgorithmName(ClassLoader cl, String pack) throws Exception {

		List<String> classes = new ArrayList<String>();
		URL upackage = cl.getResource(pack);

		DataInputStream dis = new DataInputStream((InputStream) upackage.getContent());
		String line = null;
		while ((line = dis.readLine()) != null) {
			if (line.endsWith(".class")) {
				if (!line.substring(0, line.lastIndexOf('.')).equals("ISearch")
						&& !line.substring(0, line.lastIndexOf('.')).equals("ASearch")) {

					classes.add(line.substring(0, line.lastIndexOf('.')));
				}
			}
		}
		return classes;
	}

	public Collection<Node> getNodes() {
		return g.getMap().values();
	}

	public ArrayList<String> getIntermediateNodesNames() {
		return intermediateNodes;
	}

	public ArrayList<Node> getPathNodes() {
		return g.getPath();
	}

	public void setIntermediateNodesNames(ArrayList<String> intermediateNodes) {
		this.intermediateNodes = intermediateNodes;
	}

	public void readingFile(File file) {
		BufferedReader br = null;
		StringBuilder sb = null;
		try {
			br = new BufferedReader(new FileReader(file));
			sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (sb.length() > 0) {
			try {
				makeGraphOfFileString(sb.toString());
			} catch (NumberFormatException e) {
				System.out.println("log");
			}

		}

	}

	private void makeGraphOfFileString(String fileString) {

		ArrayList<String[]> nodeOrLink = new ArrayList<String[]>();
		String[] lines = fileString.split(";");
		for (int i = 0; i < lines.length; i++) {
			nodeOrLink.add(lines[i].split(","));
		}

		Comparator<String[]> comperator = (e1, e2) -> {
			int difference = Integer.compare(e1.length, e2.length);
			return difference;
		};

		if (!nodeOrLink.isEmpty()) {
			clearGraph();
			for (String[] strArr : nodeOrLink) {
				for (int i = 0; i < strArr.length; i++) {
					strArr[i] = strArr[i].replaceAll("^\\s+|\\s+$", "");

				}
				if (strArr.length == 4) {
					addNodeInGraph(strArr[0].toString(), (int) Double.parseDouble(strArr[1]),
							(int) Double.parseDouble(strArr[2]), Double.parseDouble(strArr[3]));
				} else if (strArr.length == 5) {
					if (strArr[4].toLowerCase().equals("t")) {
						addRouteInGraph(strArr[0], strArr[1], Double.parseDouble(strArr[2]), strArr[3], true);
					} else if (strArr[4].toLowerCase().equals("f")) {
						addRouteInGraph(strArr[0], strArr[1], Double.parseDouble(strArr[2]), strArr[3], false);
					}
				}
			}
		}

	}

	public void saveInFile(File file) {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(file, "UTF-8");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Node n : g.getMap().values()) {
			writer.println(n.getName() + "," + n.getX() + "," + n.getY() + "," + n.getWeight() + ";");
		}
		HashMap<String, Node> copyMap = (HashMap<String, Node>) g.getMap().clone();
		boolean flag = false;
		for (Node n : copyMap.values()) {
			for (Link l : n.getLinks()) {
				flag = false;
				Node temp = l.getToNode();
				for (Link link : temp.getLinks()) {
					if (link.getToNode().equals(n)) {
						writer.println(n.getName() + "," + l.getToNode().getName() + "," + l.getLength() + ","
								+ l.getType() + "," + "T;");
						temp.getLinks().remove(link);
						flag = true;
						break;
					}
				}
				if (!flag) {
					writer.println(n.getName() + "," + l.getToNode().getName() + "," + l.getLength() + "," + l.getType()
							+ "," + "F;");
				}
			}
		}
		writer.close();
		
	}

	public void addWithOutDuplicateIntermediateNodes(String nodeName) {
		getIntermediateNodesNames().add(nodeName);
		setIntermediateNodesNames(
				(ArrayList<String>) getIntermediateNodesNames().stream().distinct().collect(Collectors.toList()));
	}

	public void removeIntermediateNodes(String nodeName) {
		getIntermediateNodesNames().remove(nodeName);
	}

	public Graph getGraph() {
		return g;
	}

}
