import java.util.ArrayList;
import java.util.List;

public class Node {

	public static void main(String[] args) {

		Node n5 = new Node(5, null, null);
		Node n6 = new Node(6, null, null);
		Node n3 = new Node(3, n5, n6);
		Node n4 = new Node(4, null, null);
		Node n2 = new Node(2, null, null);
		Node n1 = new Node(1, n3, n4);
		Node n = new Node(0, n1, n2);

		List<List<Node>> paths = pathBetweenNodes(n, 5, 6);
		print(paths.get(0));
		print(paths.get(1));

		paths = pathBetweenNodes(n, 3, 6);
		print(paths.get(0));
		print(paths.get(1));

		paths = pathBetweenNodes(n, 3, 4);
		print(paths.get(0));
		print(paths.get(1));

//		listPath(n);
////		Node[] headTail = treeInOrderLinkedList(n, new Node[2]);
//		System.out.println();
////		traverseLinkedList(headTail[0]);
//		System.out.println();
//
//		n5 = new Node(5, null, null);
//		n6 = new Node(6, null, null);
//		n3 = new Node(3, n5, n6);
//		n2 = new Node(2, null, null);
//		n1 = new Node(1, n3, null);
//		n = new Node(0, n1, n2);
//
//		listPath(n);
////		headTail = treeInOrderLinkedList(n, new Node[2]);
//		System.out.println();
////		traverseLinkedList(headTail[0]);
//		System.out.println();
//
//		n5 = new Node(5, null, null);
//		n6 = new Node(6, null, null);
//		n3 = new Node(3, n5, n6);
//		n4 = new Node(4, null, null);
//		n1 = new Node(1, n3, n4);
//
//		listPath(n1);
////		headTail = treeInOrderLinkedList(n1, new Node[2]);
//		System.out.println();
////		traverseLinkedList(headTail[0]);
//		System.out.println();
//
//		n5 = new Node(5, null, null);
//		n6 = new Node(6, null, null);
//		n3 = new Node(3, n5, n6);
//
//		listPath(n3);
////		headTail = treeInOrderLinkedList(n3, new Node[2]);
//		System.out.println();
////		traverseLinkedList(headTail[0]);
//		System.out.println();
//
//		n5 = new Node(5, null, null);
//
//		listPath(n5);
////		headTail = treeInOrderLinkedList(n5, new Node[2]);
//		System.out.println();
////		traverseLinkedList(headTail[0]);
////		System.out.println();
	}

	Node left;
	Node right;
	int val;

	public Node(int v, Node l, Node r) {
		this.left = l;
		this.right = r;
		val = v;
	}

	static Node[] treeInOrderLinkedList(Node node, Node[] headTail) {
		if (headTail[0] == null && node.left == null)
			headTail[0] = node;

		if (node.left != null) {
			headTail = treeInOrderLinkedList(node.left, headTail);
			if (node.left.right == null)
				node.left.right = node;
		}
		System.out.print(String.format("-%d-", node.val));
		if (node.right != null) {
			headTail = treeInOrderLinkedList(node.right, headTail);
			if (node.right.left == null)
				node.right.left = node;

			if (headTail[1] != null) {
				headTail[1].right = node;
				node.left = headTail[1];
				headTail[1] = null;
			}

			if (node.right.right == null)
				headTail[1] = node.right;
		}

		return headTail;
	}

	static void traverseLinkedList(Node node) {
		Node tail = null;
		while (node != null) {
			System.out.print(String.format("-%d-", node.val));
			if (node.right == null)
				tail = node;
			node = node.right;
		}
		System.out.println();

		while (tail != null) {
			System.out.print(String.format("-%d-", tail.val));
			tail = tail.left;
		}

	}

	static void listPath(Node n) {
		if (n == null)
			return;
		List<Node> path = new ArrayList<Node>();
		listPath(n, path);
	}

	static void listPath(Node n, List<Node> path) {
		path.add(n);
		if (n.left == null && n.right == null) {
			print(path);
		}
		if (n.left != null)
			listPath(n.left, path);
		if (n.right != null)
			listPath(n.right, path);
		path.remove(path.size() - 1);
	}

	private static void print(List<Node> path) {
		for (Node n : path) {
			System.out.print(String.format("%d-", n.val));
		}
		System.out.println();
	}

	static List<List<Node>> pathBetweenNodes(Node root, int v, int v2) {
		List<Node> path = new ArrayList<>();
		List<Node> path2 = new ArrayList<>();
		List<Node> curPath = new ArrayList<>();
		pathsFromRootToNodes(root, v, v2, path, path2, curPath);

		return pathsFromLcaToNodes(path, path2);
	}

	static void pathsFromRootToNodes(Node root, int v, int v2, List<Node> path, List<Node> path2, List<Node> curPath) {

		if (root == null)
			return;
		curPath.add(root);
		if (root.val == v) {
			path.addAll(curPath);
		}
		if (root.val == v2) {
			path2.addAll(curPath);
		}
		pathsFromRootToNodes(root.left, v, v2, path, path2, curPath);
		pathsFromRootToNodes(root.right, v, v2, path, path2, curPath);
		if (curPath.size() > 0)
			curPath.remove(curPath.size() - 1);
	}

	static List<List<Node>> pathsFromLcaToNodes(List<Node> path, List<Node> path2) {
		int i = 0;

		for (; i < Math.min(path.size(), path2.size()); i++) {
			if (path.get(i) != path2.get(i)) {
				break;
			}
		}
		i--;
		List<List<Node>> ret = new ArrayList<List<Node>>();
		ret.add(path.subList(i, path.size()));
		ret.add(path2.subList(i, path2.size()));
		return ret;
	}

}
