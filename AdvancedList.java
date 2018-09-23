import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class AdvancedList<T> implements Iterable<T>, Cloneable {

	private final ArrayList<T> innerList = new ArrayList<T>();

	public AdvancedList() {
	}

	public AdvancedList(Collection<T> items) {
		innerList.addAll(items);
	}

	public AdvancedList(AdvancedList<T> items) {
		innerList.addAll(items.getInnerList());
	}

	// ---

	public boolean add(T item) {
		return innerList.add(item);
	}

	public boolean addRange(Collection<T> items) {
		return innerList.addAll(items);
	}

	public boolean addRange(AdvancedList<T> items) {
		return innerList.addAll(items.getInnerList());
	}

	public void insert(int index, T item) {
		innerList.add(index, item);
	}

	public boolean insertRange(int index, Collection<T> items) {
		return innerList.addAll(index, items);
	}

	public boolean remove(T item) {
		return innerList.remove(item);
	}

	public T remove(int index) {
		return innerList.remove(index);
	}

	public boolean remove(Predicate<T> predicate) {
		return innerList.removeIf(predicate);
	}

	public void removeRange(int index, int count) {
		innerList.subList(index, count + 1).clear();
	}

	public void clear() {
		innerList.clear();
	}

	public T get(int index) {
		return innerList.get(index);
	}

	public T set(int index, T item) {
		return innerList.set(index, item);
	}

	// ---

	@Override
	public AdvancedList<T> clone() {
		AdvancedList<T> result = new AdvancedList<T>();
		innerList.forEach(item -> result.add(item));
		return result;
	}

	public AdvancedList<T> combine(Collection<T> items) {
		AdvancedList<T> result = clone();
		result.addRange(items);
		return result;
	}

	public AdvancedList<T> combine(AdvancedList<T> items) {
		AdvancedList<T> result = clone();
		result.addRange(items);
		return result;
	}

	public boolean contains(T item) {
		return innerList.contains(item);
	}

	public void copyTo(T[] items) {
		int length = innerList.size();
		for (int i = 0; i < length; i++) {
			items[i] = innerList.get(i);
		}
	}

	public int count() {
		return innerList.size();
	}

	public boolean exists(Predicate<T> predicate) {
		int length = innerList.size();
		for (int i = 0; i < length; i++) {
			if (predicate.test(innerList.get(i))) {
				return true;
			}
		}
		return false;
	}

	public T find(Predicate<T> predicate) {
		int length = innerList.size();
		for (int i = 0; i < length; i++) {
			T item = innerList.get(i);
			if (predicate.test(item)) {
				return item;
			}
		}
		return null;
	}

	public int findIndex(Predicate<T> predicate) {
		int length = innerList.size();
		for (int i = 0; i < length; i++) {
			if (predicate.test(innerList.get(i))) {
				return i;
			}
		}
		return -1;
	}

	public int findIndex(int index, Predicate<T> predicate) {
		int length = innerList.size();
		for (int i = index; i < length; i++) {
			if (predicate.test(innerList.get(i))) {
				return i;
			}
		}
		return -1;
	}

	public int findIndex(int index, int count, Predicate<T> predicate) {
		for (int i = index; i < index + count; i++) {
			if (predicate.test(innerList.get(i))) {
				return i;
			}
		}
		return -1;
	}

	public T findLast(Predicate<T> predicate) {
		int length = innerList.size();
		for (int i = length; i > 0; i--) {
			T item = innerList.get(i);
			if (predicate.test(item)) {
				return item;
			}
		}
		return null;
	}

	public int findLastIndex(Predicate<T> predicate) {
		int length = innerList.size();
		for (int i = length; i > 0; i--) {
			if (predicate.test(innerList.get(i))) {
				return i;
			}
		}
		return -1;
	}

	public int findLastIndex(int index, Predicate<T> predicate) {
		for (int i = index; i > 0; i--) {
			if (predicate.test(innerList.get(i))) {
				return i;
			}
		}
		return -1;
	}

	public int findLastIndex(int index, int count, Predicate<T> predicate) {
		for (int i = index; i > index - count; i--) {
			if (predicate.test(innerList.get(i))) {
				return i;
			}
		}
		return -1;
	}

	public AdvancedList<T> runForEach(Consumer<T> consumer) {
		innerList.forEach(consumer);
		return this;
	}

	public ArrayList<T> getInnerList() {
		return innerList;
	}

	public AdvancedList<T> getRange(int index, int count) {
		AdvancedList<T> result = new AdvancedList<T>();
		for (int i = index; i < index + count; i++) {
			result.add(innerList.get(i));
		}
		return result;
	}

	public int indexOf(T item) {
		int length = innerList.size();
		for (int i = 0; i < length; i++) {
			if (innerList.get(i).equals(item)) {
				return i;
			}
		}
		return -1;
	}

	public int indexOf(int index, T item) {
		int length = innerList.size();
		for (int i = index; i < length; i++) {
			if (innerList.get(i).equals(item)) {
				return i;
			}
		}
		return -1;
	}

	public int indexOf(int index, int count, T item) {
		for (int i = index; i < index + count; i++) {
			if (innerList.get(i).equals(item)) {
				return i;
			}
		}
		return -1;
	}

	public int lastIndexOf(T item) {
		int length = innerList.size();
		for (int i = length; i > 0; i--) {
			if (innerList.get(i).equals(item)) {
				return i;
			}
		}
		return -1;
	}

	public int lastIndexOf(int index, T item) {
		for (int i = index; i > 0; i--) {
			if (innerList.get(i).equals(item)) {
				return i;
			}
		}
		return -1;
	}

	public int lastIndexOf(int index, int count, T item) {
		for (int i = index; i > index - count; i--) {
			if (innerList.get(i).equals(item)) {
				return i;
			}
		}
		return -1;
	}

	public AdvancedList<T> reverse() {
		AdvancedList<T> result = new AdvancedList<T>();
		int length = innerList.size();
		for (int i = length - 1; i > -1; i--) {
			result.add(innerList.get(i));
		}
		return result;
	}

	public AdvancedList<T> reverse(int index, int count) {
		AdvancedList<T> result = new AdvancedList<T>();
		int length = innerList.size();
		for (int i = 0; i < index; i++) {
			result.add(innerList.get(i));
		}
		for (int i = index; i < index + count; i++) {
			result.add(innerList.get(index + count - i));
		}
		for (int i = index + count; i < length; i++) {
			result.add(innerList.get(i));
		}
		return result;
	}

	public AdvancedList<T> sort(Comparator<T> comparator) {
		AdvancedList<T> result = clone();
		Collections.sort(result.getInnerList(), comparator);
		return result;
	}

	public AdvancedList<T> sort(int index, int count, Comparator<T> comparator) {
		AdvancedList<T> result = new AdvancedList<T>();
		int length = innerList.size();
		for (int i = 0; i < index; i++) {
			result.add(innerList.get(i));
		}
		result.addRange(getRange(index, count).sort(comparator));
		for (int i = index + count; i < length; i++) {
			result.add(innerList.get(i));
		}
		return result;
	}

	public ArrayList<T> toArrayList() {
		ArrayList<T> result = new ArrayList<T>();
		innerList.forEach(item -> result.add(item));
		return result;
	}

	public boolean trueForAll(Predicate<T> predicate) {
		int length = innerList.size();
		for (int i = 0; i < length; i++) {
			if (!predicate.test(innerList.get(i))) {
				return false;
			}
		}
		return true;
	}

	public AdvancedList<T> where(Predicate<T> predicate) {
		AdvancedList<T> result = new AdvancedList<T>();
		int length = innerList.size();
		for (int i = 0; i < length; i++) {
			T item = innerList.get(i);
			if (predicate.test(item)) {
				result.add(item);
			}
		}
		return result;
	}

	@Override
	public Iterator<T> iterator() {
		return innerList.iterator();
	}
}