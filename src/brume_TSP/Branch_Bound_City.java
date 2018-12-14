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

import java.awt.geom.Point2D;

/**
 * Branch_Bound_City class that represents a city by its x and y coordinates
 */

public class Branch_Bound_City extends Point2D.Double {
	private static final long serialVersionUID = 1L;
	private String name;

	/**
	 * Constructs a city by its x and y coordinates
	 *
	 * @param name The city name
	 * @param x    The x-coordinate
	 * @param y    The y-coordinate
	 */
	public Branch_Bound_City(String name, double x, double y) {
		super(x, y);
		this.name = name;
	}

	/**
	 * Gets the city's name
	 *
	 * @return The city's name
	 */

	public String getName() {
		return name;
	}
}
