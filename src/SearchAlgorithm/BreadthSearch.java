package SearchAlgorithm;

import java.util.ArrayList;

import uni.Graph;
import uni.Link;
import uni.Node;

public class BreadthSearch extends ASearch {

	public BreadthSearch(Graph graph) {
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
			myGraph.setFullInformation(myGraph.getFullInformation()+temp.getName() + " : "+"| Parant : "+
					                   (temp.getParent()!= null ? temp.getParent().getName() : "Null")+  
					                   " | Expens : "+temp.getExpense()+
					                   " | Distance to end : "+temp.getDistanceToEnd()+
					                   " | Depth : "+temp.getDepth()+" | \n");
			//System.out.println(temp.getName());
			if (temp.getName().equals(to)) {
				myGraph.setPath(printPath(temp));
				return true;
			}
			temp.setTested(true);
			for (Link l : temp.getLinks()) {
				if (!l.getToNode().isTested() && !queue.contains(l.getToNode())) {
					queue.add(l.getToNode());
				}
			}
			temp.setExpanded(true);
		}

		return false;
	}

}
