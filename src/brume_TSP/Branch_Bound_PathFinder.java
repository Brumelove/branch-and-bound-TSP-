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
import java.util.*;

public class Branch_Bound_PathFinder {
	double[][] distances;
	double best_cost;
	int[] best_path;

	/**
	 * Constructs a new Branch_Bound_PathFinder and initializes distances array
	 *
	 * @param cities An ArrayList of Branch_Bound_City
	 * @return
	 */
	public Branch_Bound_PathFinder(ArrayList<Branch_Bound_City> cities) {
		distances = new double[cities.size()][cities.size()];
		for (int i = 0; i < cities.size(); i++) {
			for (int j = 0; j < cities.size(); j++)
				distances[i][j] += getEuclideanDistance(cities.get(i), cities.get(j));
		}
		return;
	}

	/**
	 * Calculate the Euclidean distance.
	 *
	 * @param branch_Bound_City  the starting point
	 * @param branch_Bound_City2 the ending point
	 * @return distance the distance between the points
	 */
	private double getEuclideanDistance(Branch_Bound_City branch_Bound_City, Branch_Bound_City branch_Bound_City2) {
		double temp1 = Math.pow((branch_Bound_City2.y - branch_Bound_City.y), 2);
		double temp2 = Math.pow((branch_Bound_City2.x - branch_Bound_City.x), 2);
		double distance = Math.sqrt(temp1 + temp2);
		return distance;

	}

	/**
	 * Calculates the shortest (non-repeating) path between a series of nodes
	 *
	 * @return An array with the locations of the best path
	 */
	public int[] calculate() {
		HashSet<Integer> location_set = new HashSet<Integer>(distances.length);
		for (int i = 0; i < distances.length; i++)
			location_set.add(i);
		best_cost = findGreedyCost(0, location_set, distances);
		int[] active_set = new int[distances.length];

		for (int i = 0; i < active_set.length; i++)
			active_set[i] = i;
		Branch_Bound_Node root = new Branch_Bound_Node(null, 0, distances, active_set, 0);
		traverse(root);

		return best_path;
	}

	/**
	 * Get current path cost
	 *
	 * @return The cost
	 */
	public double getPathLength() {
		return best_cost;
	}

	/**
	 * Find the greedy cost for a set of locations
	 *
	 * @param i            The current location
	 * @param location_set Set of all remaining locations
	 * @param distances    The 2D array containing point distances
	 * @return The greedy cost
	 */
	private double findGreedyCost(int i, HashSet<Integer> location_set, double[][] distances) {
		if (location_set.isEmpty())

			return distances[0][i];
		location_set.remove(i);

		double lowest = Double.MAX_VALUE;
		int closest = 0;
		for (int location : location_set) {
			double cost = distances[i][location];
			if (cost < lowest) {
				lowest = cost;
				closest = location;
			}
		}

		return lowest + findGreedyCost(closest, location_set, distances);
	}

	/**
	 * Recursive method to go through the tree finding and pruning paths
	 *
	 * @param parent The root/parent node
	 */
	private void traverse(Branch_Bound_Node parent) {
		Branch_Bound_Node[] children = parent.generateChildren();

		for (Branch_Bound_Node child : children) {
			if (child.isTerminal()) {
				double cost = child.getPathCost();
				if (cost < best_cost) {
					best_cost = cost;
					best_path = child.getPath();
				}
			} else if (child.getLowerBound() <= best_cost) {
				traverse(child);
			}
		}
	}
}
