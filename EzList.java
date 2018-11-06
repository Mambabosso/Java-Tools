import java.awt.GridLayout;
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

public class EzList<E> extends JComponent implements Iterable<E> {
	
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
		return new ArrayList<E>(Collections.list(defaultListModel.elements()));
	}
	
	public Object[] getItems() {
		return toArrayList().toArray();
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
	
	public E getSelected() {
		return list.getSelectedValue();
	}
	
	public Object[] getSelectedItems() {
		return list.getSelectedValuesList().toArray();
	}
	
	public int getSelectedIndex() {
		return list.getSelectedIndex();
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
