package Tree;

import State.*;

public class MonteCarloTree {
    private final double cValue;
    private Node root;

    public MonteCarloTree(State state, double cValue, int colour) {
        this.cValue = cValue;
        root = new Node(state, colour);
    }

    public Action search() {
        Node tree = root;
        Timer time = new Timer(29);
        int runCount = 0;
        while (time.timeLeft()) {
            System.out.println(1);
            Node leaf = select(tree);
            System.out.println(2);
            Node child = expand(leaf);
            System.out.println(3);
            int result = Simulate.simulate(child);
            System.out.println(4);
            backPropagate(result, child);
        }
        System.out.println("Made it here");
        return mostVisitedNode().getAction();
    }

    private Node select(Node tree) {
        Node current = tree;

        while (!current.isLeaf())
            current = UCBMove(current);

        return current;
    }

    private Node expand(Node leaf) {
        return ExpansionPolicy.expansionNode(leaf);
    }

    /**
     * We now use the result of the simulation to update all the search tree nodes going up to the root.
     *
     * @param result The player that won. Either State.BLACK or State.WHITE
     * @param child
     */
    public void backPropagate(int result, Node child) {
        do {
            if (child.getColour() == result) {
                child.setTotalPlayouts(child.getTotalPlayouts() + 1);
                child.setTotalWins(child.getTotalWins() + 1);
            } else {
                child.setTotalPlayouts(child.getTotalPlayouts() + 1);
            }
            child = child.getParent();
        }
        while (child.getParent() != null);
    }

    private Node mostVisitedNode() {
        Node bestNode = null;
        int bestCount = -1;

        for (Node child : getRoot().getChildren()) {
            if (child.getTotalPlayouts() > bestCount) {
                bestNode = child;
                bestCount = child.getTotalPlayouts();
            }
        }

        return bestNode;
    }

    private Node UCBMove(Node n) {
        Node bestChild = null;
        double bestValue = -1;
        for (Node child : n.getChildren()) {
            double val = UCBEquation(child);
            if (val > bestValue) {
                bestValue = val;
                bestChild = child;
            }
        }
        return bestChild;
    }

    private double UCBEquation(Node n) {
        return (double) n.getTotalWins() / n.getTotalPlayouts() + cValue * Math.sqrt(Math.log((double) n.getParent().getTotalPlayouts() / n.getTotalPlayouts()));
    }

    public Node getRoot() {
        return this.root;
    }

    public double getCValue() {
        return this.cValue;
    }

    public void setRoot(Node root) {
        this.root = root;
    }
}
