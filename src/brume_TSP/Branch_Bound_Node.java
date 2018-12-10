/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brume_TSP;

/**
 *
 * @author brume
 */

public class Branch_Bound_Node {
	/**
	 * A node of the state space tree
	 */

	public Branch_Bound_Node parent;
	private double costMatrix;
	private double[][] distances;
	private int[] active_set;
	public int index;

	/**
	 * Constructs a new Branch_Bound_Node
	 *
	 * 
	 * @param parent     This node's parent
	 * @param costMatrix The cost between these nodes
	 * @param distances  The 2D array of distance between locations
	 * @param active_set The set of all points (including this node) that are being
	 *                   calculated
	 * @param index      The location index of this node
	 */
	public Branch_Bound_Node(Branch_Bound_Node parent, double costMatrix, double[][] distances, int[] active_set,
			int index) {
		this.parent = parent;
		this.costMatrix = costMatrix;
		this.distances = distances;
		this.active_set = active_set;
		this.index = index;
	}

	/**
	 * Check if this node is terminal
	 *
	 * @return Whether or not the node is terminal
	 */
	public boolean isTerminal() {
		return active_set.length == 1;
	}

	/**
	 * Generate and return this node's children
	 *
	 * @precondition Branch_Bound_Node is not terminal
	 * @return Array of children
	 */
	public Branch_Bound_Node[] generateChildren() {
		Branch_Bound_Node[] children = new Branch_Bound_Node[active_set.length - 1];
		/**
		 * -1 indicates no edge from row to column allowed, 1 indicates that edge from
		 * row to column required, 0 indicates that edge from row to column allowed
		 */
		int[] new_set = new int[active_set.length - 1];
		int i = 0;
		for (int location : active_set) {
			if (location == index)
				continue;

			new_set[i] = location;
			i++;
		}

		for (int j = 0; j < children.length; j++)
			children[j] = new Branch_Bound_Node(this, distances[index][new_set[j]], distances, new_set, new_set[j]);

		return children;

	}

	/**
	 * Get the path array up to this point
	 *
	 * @return The path
	 */
	public int[] getPath() {
		int depth = distances.length - active_set.length + 1;
		int[] path = new int[depth];
		getPathIndex(path, depth - 1);
		return path;

	}

	/**
	 * Recursive method to fill in a path array from this point
	 *
	 * @param path The path array
	 * @param i    The index of this node
	 */
	public void getPathIndex(int[] path, int i) {
		path[i] = index;
		if (parent != null)
			parent.getPathIndex(path, i - 1);
	}

	/**
	 * Get the lower bound cost of this node (minimum possible solution)
	 *
	 * @return Lower bound cost
	 */
	public double getLowerBound() {
		double value = 0;
		if (active_set.length == 2)
			return getPathCost() + distances[active_set[0]][active_set[1]];
		for (int location : active_set) {
			double low1 = Double.MAX_VALUE;
			double low2 = Double.MAX_VALUE;

			for (int other : active_set) {
				if (other == location)
					continue;
				double cost = distances[location][other];
				if (cost < low1) {
					low2 = low1;
					low1 = cost;
				} else if (cost < low2) {
					low2 = cost;
				}
			}

			value += low1 + low2;
		}

		return getParentCost() + value / 2;

	}

	/**
	 * Get the cost of the entire path up to this point
	 *
	 * @return Cost of path including return
	 */
	public double getPathCost() {
		return distances[0][index] + getParentCost();
	}

	/**
	 * Get the cost up to the parent at this point
	 *
	 * @return Cost of path
	 */
	public double getParentCost() {
		if (parent == null)
			return 0;

		return costMatrix + parent.getParentCost();
	}
}
