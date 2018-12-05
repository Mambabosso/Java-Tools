import java.awt.GridLayout;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class EzList<E> extends JComponent implements Iterable<E>, Serializable {

	private static final long serialVersionUID = 1L;

	private DefaultListModel<E> defaultListModel;
	private JList<E> list;
	private JScrollPane scrollPane;

	public EzList() {
		setLayout(new GridLayout(0, 1));
		defaultListModel = new DefaultListModel<E>();
		list = new JList<E>(defaultListModel);
		scrollPane = new JScrollPane(list);
		add(scrollPane);
	}

	public EzList(EzList<E> list) {
		this();
		for (E item : list) {
			addItem(item);
		}
	}

	public EzList(E[] items) {
		this();
		for (E item : items) {
			addItem(item);
		}
	}

	public EzList(ArrayList<E> items) {
		this();
		for (E item : items) {
			addItem(item);
		}
	}

	public DefaultListModel<E> getModel() {
		return defaultListModel;
	}

	public JList<E> getList() {
		return list;
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public Iterator<E> iterator() {
		return Collections.list(defaultListModel.elements()).iterator();
	}

	public int count() {
		return defaultListModel.size();
	}

	public ArrayList<E> toArrayList(Predicate<E> predicate) {
		ArrayList<E> result = new ArrayList<E>();
		for (int i = 0; i < defaultListModel.size(); i++) {
			E element = defaultListModel.getElementAt(i);
			if (predicate.test(element)) {
				result.add(element);
			}
		}
		return result;
	}

	public ArrayList<E> toArrayList() {
		return toArrayList(e -> true);
	}

	public E get(int index) {
		return defaultListModel.getElementAt(index);
	}

	public Object[] getItems() {
		return toArrayList().toArray();
	}

	public Object[] getItems(Predicate<E> predicate) {
		return toArrayList(predicate).toArray();
	}

	public boolean contains(E item) {
		return defaultListModel.contains(item);
	}

	public int indexOf(E item) {
		return defaultListModel.indexOf(item);
	}

	public void set(int index, E item) {
		defaultListModel.setElementAt(item, index);
	}

	public void addItem(E item) {
		defaultListModel.addElement(item);
	}

	public void addItems(E[] items) {
		for (E item : items) {
			defaultListModel.addElement(item);
		}
	}

	public void addItems(ArrayList<E> items) {
		for (E item : items) {
			defaultListModel.addElement(item);
		}
	}

	public boolean removeItem(E item) {
		return defaultListModel.removeElement(item);
	}

	public void removeItem(int index) {
		defaultListModel.removeElementAt(index);
	}

	public void removeItem(Predicate<E> predicate) {
		for (int i = 0; i < defaultListModel.size(); i++) {
			E element = defaultListModel.getElementAt(i);
			if (predicate.test(element)) {
				defaultListModel.removeElement(element);
			}
		}
	}

	public void clear() {
		defaultListModel.clear();
	}

	public boolean any() {
		return count() > 0;
	}

	public E firstItem(Predicate<E> predicate) {
		for (int i = 0; i < defaultListModel.size(); i++) {
			E element = defaultListModel.getElementAt(i);
			if (predicate.test(element)) {
				return element;
			}
		}
		return null;
	}

	public E firstItem() {
		return firstItem(e -> true);
	}

	public E lastItem(Predicate<E> predicate) {
		for (int i = defaultListModel.size() - 1; i > -1; i--) {
			E element = defaultListModel.getElementAt(i);
			if (predicate.test(element)) {
				return element;
			}
		}
		return null;
	}

	public E lastItem() {
		return lastItem(e -> true);
	}

	public E getSelectedItem() {
		return list.getSelectedValue();
	}

	public Object[] getSelectedItems() {
		return list.getSelectedValuesList().toArray();
	}

	public int getSelectedIndex() {
		return list.getSelectedIndex();
	}

	public int[] getSelectedIndices() {
		return list.getSelectedIndices();
	}

	public <EConverted> ArrayList<EConverted> convertAll(Function<E, EConverted> function) {
		ArrayList<EConverted> result = new ArrayList<EConverted>();
		for (int i = 0; i < defaultListModel.size(); i++) {
			E element = defaultListModel.getElementAt(i);
			result.add(function.apply(element));
		}
		return result;
	}

	public boolean trueForAll(Predicate<E> predicate) {
		for (int i = 0; i < defaultListModel.size(); i++) {
			E element = defaultListModel.getElementAt(i);
			if (!predicate.test(element)) {
				return false;
			}
		}
		return true;
	}

	public boolean save(String path, Predicate<E> predicate) {
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(path);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(toArrayList(predicate));
			objectOutputStream.close();
			fileOutputStream.close();
		} catch (Exception exception) {
			return false;
		}
		return true;
	}

	public boolean save(String path) {
		return save(path, e -> true);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<E> load(String path) {
		ArrayList<E> result = null;
		try {
			FileInputStream fileInputStream = new FileInputStream(path);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			result = (ArrayList<E>) objectInputStream.readObject();
			objectInputStream.close();
			fileInputStream.close();
		} catch (Exception exception) {
			return null;
		}
		return result;
	}

	public ArrayList<E> load(String path, boolean addToList) {
		ArrayList<E> result = load(path);
		if (result != null && addToList) {
			addItems(result);
		}
		return result;
	}

	public ListSelectionListener addListSelectionListener(ListSelectionListener listener) {
		list.addListSelectionListener(listener);
		return listener;
	}

	@SuppressWarnings("unchecked")
	public ListSelectionListener addListSelectionListener(BiConsumer<ListSelectionEvent, List<E>> consumer) {
		ListSelectionListener result = new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					List<E> items = ((JList<E>) e.getSource()).getSelectedValuesList();
					consumer.accept(e, Collections.unmodifiableList(items));
				}
			}
		};
		list.addListSelectionListener(result);
		return result;
	}

	public void removeListSelectionListener(ListSelectionListener listener) {
		list.removeListSelectionListener(listener);
	}
}
