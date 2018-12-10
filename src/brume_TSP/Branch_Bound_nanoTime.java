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

/**********************************************************************/

/**
 * General class to time tasks
 */
public class Branch_Bound_nanoTime {
    long startTime;
    long estimatedTime;
    public final static String[] units = { "μs", "ms", "s", "ks", "Ms" };

    /**
     * Start the branch_bounce_time
     */
    public void start() {
        startTime = System.nanoTime();

    }

    /**
     * Stop the branch_bounce_time
     */
    public void stop() {
        estimatedTime = System.nanoTime();
    }

    /**
     * Get the time elapsed in nanoseconds
     *
     * @return Time in nanoseconds
     */
    public long getTime() {
        return estimatedTime - startTime;
    }

    /**
     * Get a formatted time string that adjusts the number and includes units
     *
     * @return Formatted time
     */
    public String getFormattedTime() {

        long time = getTime();
        /**
         * returns the base 10 log of the elapsed time the number 9 represent
         * nanoseconds(100000000)
         */
        int unit = (int) ((Math.log10(time) - 9 - 2) / 3);
        if (unit > 2)
            unit = 2;
        else if (unit < -2)
            unit = -2;
        return (time / Math.pow(10, unit * 3 + 9)) + units[unit + 1];
    }
}
