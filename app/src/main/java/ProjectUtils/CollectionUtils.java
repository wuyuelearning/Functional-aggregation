package ProjectUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


public class CollectionUtils {
    private CollectionUtils() {
    }

    /**
     * 删除List中的null元素
     */
    public static void trimList(List l) {
        if (l == null || l.isEmpty()) {
            return;
        }
        for (int i = l.size() - 1; i > -1; --i) {
            if (l.get(i) == null) {
                l.remove(i);
            }
        }
    }

    /**
     * Checks if the collection is null or empty.
     *
     * @param collection the collection to be checked.
     * @param <T>        the type of the elements.
     * @return true if null or empty, false for otherwise.
     */
    public static <T> boolean nullOrEmpty(Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }

    public static <T> boolean notNullOrEmpty(Collection<T> collection) {
        return !nullOrEmpty(collection);
    }

    /**
     * Calculates the union of two collections.
     *
     * @param c1         the first collection.
     * @param c2         the second collection.
     * @param comparator the comparator to compare the elements.
     * @param <T>        the type of the element.
     * @return the union collection.
     */
    public static <T> List<T> union(Collection<T> c1, Collection<T> c2, Comparator<T> comparator) {

        Set<T> set = new TreeSet<T>(comparator);
        set.addAll(checkedCollection(c1));
        set.addAll(checkedCollection(c2));
        return new ArrayList<T>(set);
    }

    /**
     * Calculates the union of two collections.
     *
     * @param c1  the first collection.
     * @param c2  the second collection.
     * @param <T> the type of the element.
     * @return the union collection.
     */
    public static <T> List<T> union(Collection<T> c1, Collection<T> c2) {
        Set<T> set = new HashSet<T>();
        set.addAll(checkedCollection(c1));
        set.addAll(checkedCollection(c2));
        return new ArrayList<T>(set);
    }

    /**
     * Calculates the union of two collections with order kept.
     *
     * @param c1         the first collection.
     * @param c2         the second collection.
     * @param comparator the comparator to compare the elements.
     * @param <T>        the type of the element.
     * @return the union collection.
     */
    public static <T> List<T> unionOrderly(Collection<T> c1, Collection<T> c2, Comparator<T> comparator) {
        Collection<T> checkedCollection1 = checkedCollection(c1);
        Collection<T> checkedCollection2 = checkedCollection(c2);

        List<T> result = new ArrayList<T>(checkedCollection1);
        Set<T> set = new TreeSet<T>(comparator);
        set.addAll(checkedCollection1);
        for (T t : checkedCollection2) {
            if (set.add(t)) {
                result.add(t);
            }
        }
        return result;
    }

    private static <T> Collection<T> checkedCollection(Collection<T> collection) {
        return nullOrEmpty(collection) ? Collections.<T>emptyList() : collection;
    }
}
