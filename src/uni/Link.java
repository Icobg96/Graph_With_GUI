package uni;

public class Link {
	private double length;
	private Node toNode;
	private String type;

	public Link(Node to) {
		this.toNode = to;
	}

	public Link(Node to, double lenght) {
		this(to);
		this.length = lenght;
	}

	public Link(Node to, double lenght, String type) {
		this(to, lenght);
		this.type = type;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double lenght) {
		this.length = lenght;
	}

	public Node getToNode() {
		return toNode;
	}

	public void setToNode(Node to) {
		this.toNode = to;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
