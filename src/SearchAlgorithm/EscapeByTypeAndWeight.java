package SearchAlgorithm;

import java.util.ArrayList;

import uni.Graph;
import uni.Link;
import uni.Node;

public class EscapeByTypeAndWeight extends ASearch {
	private String type = "";
	private double weight = Double.MAX_VALUE;

	public void setType(String type) {
		this.type = type;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public EscapeByTypeAndWeight(Graph graph) {
		super(graph);

	}

	@Override
	public boolean search(String from, String to) {
		myGraph.resetGraph();
		if (!myMap.containsKey(from) || !myMap.containsKey(to)) {
			return false;
		}
		Node fromNode = myMap.get(from);
		fromNode.setDepth(0);
		ArrayList<Node> queue = new ArrayList<Node>();
		queue.add(fromNode);
		while (!queue.isEmpty()) {
			Node temp = queue.get(0);
			queue.remove(0);
			setDepth(temp);
			myGraph.setPathInforamtion(myGraph.getPathInforamtion() + temp.getName() + ",");
			myGraph.setFullInformation(myGraph.getFullInformation() + temp.getName() + " : " + "| Parant : "
					+ (temp.getParent() != null ? temp.getParent().getName() : "Null") + " | Expens : "
					+ temp.getExpense() + " | Depth : " + temp.getDepth() + " | \n");
			// System.out.println(temp.getName() + " |Depth: " +
			// temp.getDepth());
			if (temp.getName().equals(to)) {
				// myGraph.setPath(printPath(temp));
				myGraph.setPath(parentPrintPath(myMap.get(to), myMap.get(from)));
				return true;
			}
			temp.setTested(true);
			for (Link l : temp.getLinks()) {
				if (!l.getToNode().isTested() && !queue.contains(l.getToNode()) && !l.getType().equals(type)
						&& l.getToNode().getWeight() <= weight) {
					queue.add(l.getToNode());
					l.getToNode().setParent(temp);
				}
			}
			temp.setExpanded(true);
		}

		return false;
	}
}
