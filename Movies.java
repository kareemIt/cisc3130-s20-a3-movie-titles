import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class MovieTree {

    class MovieNode implements Comparable{

        // left and right child of this node
        MovieNode left, right;
        // data in the node
        String title, year;

        // Constructor
        public MovieNode(String title, String year) {
            this.title = title;
            this.year = year;
            left = null;
            right = null;
        }

        @Override
        public String toString() {
            return title + " " + year;
        }

        @Override
        public int compareTo(Object o) {
            return this.title.compareTo(((MovieNode)o).title);
        }

        @Override
        public boolean equals(Object o) {
            return this.title.equals(((MovieNode)o).title);
        }

    }

    // head of the tree
    private MovieNode root;

    public MovieTree() {
        // initial tree has no node
        root = null;
    }

    public void add(String name, String year) {

        // Create a new node with data
        MovieNode node = new MovieNode(name, year);

        // if root is null then it is an empty tree
        // so new node will be the root
        if(root == null) {
            root = node;
        } else {
            addToChild(root, node);
        }

    }

    // Recursion
    private void addToChild(MovieNode localRoot, MovieNode node) {
        // if node is less than the localRoot then node will be on the left of the localRoot
        if(node.compareTo(localRoot) < 0) {
            if(localRoot.left == null) {
                localRoot.left = node;
            } else {
                // if localRoot already has a left child then recurse on that child
                addToChild(localRoot.left, node);
            }
        // if node is greater than the localRoot then node will be on the right of the localRoot
        } else if(node.compareTo(localRoot) > 0) {
            if(localRoot.right == null) {
                localRoot.right = node;
            } else {
                // if localRoot already has a right child then recurse on that child
                addToChild(localRoot.right, node);
            }
        } else {
            return;
        }
    }

    public MovieNode getRoot() {
        return root;
    }

    public LinkedList<MovieNode> subset(String start, String end) {
        LinkedList<MovieNode> list = new LinkedList<>();
        MovieNode startNode = new MovieNode(start, "");
        MovieNode endNode = new MovieNode(end, "");
        addToSubset(list, startNode, endNode, root);
        return list;
    }

    // Recursion Method
    public LinkedList<MovieNode> addToSubset(LinkedList<MovieNode> list, MovieNode startNode, MovieNode endNode, MovieNode currentNode) {
        // If currentNode is null then do nothing
        // Base case
        if(currentNode == null) {

        // if currentNode is within the boundary of start and end node
        } else if(currentNode.compareTo(startNode) >= 0 && currentNode.compareTo(endNode) <= 0) {
            // then recurse on the left child
            addToSubset(list, startNode, endNode, currentNode.left);

            // add itself to the list
            list.add(currentNode);

            // then recurse on the right child
            addToSubset(list, startNode, endNode, currentNode.right);
        }

        return list;
    }

    // Recursion Method
    public void printTree(MovieNode node) {
        // base case
        if(node == null) {
            return;
        }
        // recurse left side
        printTree(node.left);

        // print itself
        System.out.println(node);

        // recurse right side
        printTree(node.right);
    }

    public static void main(String[] args) throws IOException {
        MovieTree tree = new MovieTree();
        File file = new File("movies.csv");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        reader.readLine();
        String line = reader.readLine();
        int counter = 0;
        while(counter != 100) {
        // while(line != null) {
            String name = line.substring(line.indexOf(",") + 1, line.lastIndexOf("(") - 1).replace("\"", "");
            String date = line.substring(line.lastIndexOf("("), line.lastIndexOf(")") + 1);
            tree.add(name, date);
            line = reader.readLine();
            counter++;
        }
        reader.close();
        // tree.printTree(tree.getRoot());
        LinkedList<MovieNode> list = tree.subset("Toy Story", "White Squall");
        for(MovieNode e : list) {
            System.out.println(e);
        }
    }


}
