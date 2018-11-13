import java.awt.GridLayout;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.function.Consumer;
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

	public ArrayList<E> toArrayList() {
		ArrayList<E> result = new ArrayList<E>();
		for (int i = 0; i < defaultListModel.size(); i++) {
			result.add(defaultListModel.getElementAt(i));
		}
		return result;
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

	public E get(int index) {
		return defaultListModel.getElementAt(index);
	}

	public void set(int index, E item) {
		defaultListModel.setElementAt(item, index);
	}

	public Object[] getItems() {
		return toArrayList().toArray();
	}

	public Object[] getItems(Predicate<E> predicate) {
		return toArrayList(predicate).toArray();
	}

	public void addItem(E item) {
		defaultListModel.addElement(item);
	}

	public boolean removeItem(E item) {
		return defaultListModel.removeElement(item);
	}

	public void removeItem(int index) {
		defaultListModel.removeElementAt(index);
	}

	public void removeItem(Predicate<E> predicate) {
		for (int i = 0; i < defaultListModel.size(); i++) {
			if (predicate.test(defaultListModel.getElementAt(i))) {
				defaultListModel.removeElementAt(i);
			}
		}
	}

	public void clear() {
		defaultListModel.clear();
	}

	public E first(Predicate<E> predicate) {
		for (int i = 0; i < defaultListModel.size(); i++) {
			E element = defaultListModel.getElementAt(i);
			if (predicate.test(element)) {
				return element;
			}
		}
		return null;
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

	public boolean save(String path) {
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(path);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(toArrayList());
			objectOutputStream.close();
			fileOutputStream.close();
		} catch (Exception exception) {
			return false;
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

	public ListSelectionListener addSelectionListener(Consumer<ListSelectionEvent> consumer) {
		ListSelectionListener result = new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				consumer.accept(e);
			}
		};
		list.addListSelectionListener(result);
		return result;
	}
}
