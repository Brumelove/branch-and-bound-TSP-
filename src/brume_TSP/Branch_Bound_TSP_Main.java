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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFileChooser;

/**
 * Solve Traveling Salesman problem using branch and bound
 *
 * https://www.techiedelight.com/travelling-salesman-problem-using-branch-and-bound/
 *
 * Further reading: - [AN ALGORITHM FOR THE TRAVELING SALESMAN
 * PROBLEM](http://dspace.mit.edu/bitstream/handle/1721.1/46828/algorithmfortrav00litt.pdf)
 * - [SOLVING THE TRAVELLING SALESMAN PROBLEM USING THE BRANCH AND BOUND
 * METHOD](https://hrcak.srce.hr/file/236378)
 *
 */

public class Branch_Bound_TSP_Main {

    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) {
        // Declare the file to be read
        File file;
        if (args.length > 0) {
            file = new File(args[0]);
        }

        else {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("./src/brume_TrainingSets"));
            int response = chooser.showOpenDialog(null);
            if (response != JFileChooser.APPROVE_OPTION)
                return;
            file = chooser.getSelectedFile();
        }
        // Reading the selected file

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (IOException e) {
            alert("Error loading file " + e);
            System.exit(1);
        }
        int dimension = 0;

        /**
         * setting the header of each file in the TSP format
         **/

        try {
            String line;

            while (!(line = reader.readLine()).equals("NODE_COORD_SECTION")) {
                String[] entry = line.split(":", 1);
                switch (entry[0].trim()) {
                case "TYPE":
                    if (!entry[1].trim().equals("TSP"))
                        throw new Exception("File not in TSP format");
                    break;
                case "DIMENSION":
                    dimension = Integer.parseInt(entry[1]);
                    break;
                }
            }
        } catch (Exception e) {
            alert("Error parsing header " + e);
            System.exit(1);
        }

        // setting the footer of each file in the TSP format
        ArrayList<Branch_Bound_City> cities = new ArrayList<Branch_Bound_City>(dimension);
        try {
            String line;
            while ((line = reader.readLine()) != null && !line.equals("EOF")) {
                String[] entry = line.split(" ");
                cities.add(new Branch_Bound_City(entry[0], Double.parseDouble(entry[1]), Double.parseDouble(entry[2])));
            }

            reader.close();

        } catch (Exception e) {
            alert("Error parsing data " + e);
            System.exit(1);
        }

        Branch_Bound_nanoTime branch_bounce_time = new Branch_Bound_nanoTime();
        Branch_Bound_PathFinder bbp = new Branch_Bound_PathFinder(cities);
        branch_bounce_time.start();
        int[] path = bbp.calculate();
        branch_bounce_time.stop();

        String message = cities.get(path[0]).getName();
        for (int i = 1; i < path.length; i++) {
            message += " -> " + cities.get(path[i]).getName();
        }

        message += " -> " + cities.get(path[0]).getName();
        message += "\nLength of the Path is" + " -> " + bbp.getPathLength() + "m";
        message += "\nTime: " + branch_bounce_time.getFormattedTime();
        alert(message);
    }

    private static void alert(String message) {
        System.out.printf(message);

    }

}
