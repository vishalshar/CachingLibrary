
package CacheLibrary;

import java.text.*;
import java.util.*;
import java.util.concurrent.*;

/**
 *
 * @author Vishal
 *
 * This cache Library will maintain the record for the users who logged in the
 * system within last 24 hours.
 *
 * Assumptions : User logging in the system is very frequent.
 *
 */
public class CacheLibrary {

    private static Map<String, Date> cache = new ConcurrentHashMap();

    /**
     * Constructor
     */
    public CacheLibrary() {
        cache = new ConcurrentHashMap();
    }

    /**
     *
     * @return current size of the cache
     */
    public static int getSize() {
        return cache.size();
    }

    /**
     * return if the @uId (user id) has last logged in within 24 hours
     */
    public static boolean get(String uId) {
        if (cache.containsKey(uId)) {
            return true;
        }
        return false;
    }

    /**
     * Method to add cache and remove uId with last login time more than 24
     * hours.
     *
     * @param uId : User Id
     * @param date : Time of login
     */
    public static void add(String uId, Date date) {

        if (cache.containsKey(uId)) {
            /**
             * Updating the time of the userId since its already in the cache
             */
            cache.remove(uId);
            cache.put(uId, date);
        } else {
            /**
             * Adding new entry to the cache and queue
             */
            cache.put(uId, date);
        }

        /**
         * removing uId from cache who's last login time is more than 24 hours.
         *
         */
        Date currentDate = new Date();
        Boolean working = true;
        Set<String> key = cache.keySet();

        for (String id : key) {
            Date idDate = cache.get(id);
            /**
             * benchmark is current time in milliseconds subtracted by
             * milliseconds in a day (86400000). The last login time of the uId
             * has to be greater than benchmark. If not then remove that entry
             * Since its been more than 24 hours that user logged in.
             */
            Long benchmark = currentDate.getTime() - 86400000;
            if (!(idDate.getTime() > benchmark)) {
                cache.remove(id);
            }
        }
    }
}
