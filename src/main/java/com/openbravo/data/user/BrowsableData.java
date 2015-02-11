package com.openbravo.data.user;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.LocalRes;
import java.util.*;
import javax.swing.ListModel;
import javax.swing.event.EventListenerList;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class BrowsableData implements ListModel {

    protected EventListenerList listeners = new EventListenerList();
    private boolean m_bIsAdjusting;

    private ListProvider m_dataprov;
    private SaveProvider m_saveprov;

    private List m_aData; // List<Object>

    private Comparator m_comparer;

    /**
     * Creates a new instance of BrowsableData
     * @param c
     */
    public BrowsableData(ListProvider dataprov, SaveProvider saveprov, Comparator c) {
        m_dataprov = dataprov;
        m_saveprov = saveprov;
        m_comparer = c;
        m_bIsAdjusting = false;

        m_aData = new ArrayList();
    }

    public BrowsableData(ListProvider dataprov, SaveProvider saveprov) {
        this(dataprov, saveprov, null);
    }

    public BrowsableData(ListProvider dataprov) {
        this(dataprov, null, null);
    }

    public final void addListDataListener(ListDataListener l) {
        listeners.add(ListDataListener.class, l);
    }
    public final void removeListDataListener(ListDataListener l) {
        listeners.remove(ListDataListener.class, l);
    }

    // Metodos de acceso
    public final Object getElementAt(int index) {
        return m_aData.get(index);
    }

    public final int getSize() {
        return m_aData.size();
    }

    public final boolean isAdjusting() {
        return m_bIsAdjusting;
    }

    protected void fireDataIntervalAdded(int index0, int index1) {
        m_bIsAdjusting = true;
        EventListener[] l = listeners.getListeners(ListDataListener.class);
        ListDataEvent e = null;
        for (int i = 0; i < l.length; i++) {
            if (e == null) {
                e = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, index0, index1);
            }
            ((ListDataListener) l[i]).intervalAdded(e);
        }
        m_bIsAdjusting = false;
    }

    protected void fireDataContentsChanged(int index0, int index1) {
        m_bIsAdjusting = true;
        EventListener[] l = listeners.getListeners(ListDataListener.class);
        ListDataEvent e = null;
        for (int i = 0; i < l.length; i++) {
            if (e == null) {
                e = new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, index0, index1);
            }
            ((ListDataListener) l[i]).contentsChanged(e);
        }
        m_bIsAdjusting = false;
    }

    protected void fireDataIntervalRemoved(int index0, int index1) {
        m_bIsAdjusting = true;
        EventListener[] l = listeners.getListeners(ListDataListener.class);
        ListDataEvent e = null;
        for (int i = 0; i < l.length; i++) {
            if (e == null) {
                e = new ListDataEvent(this, ListDataEvent.INTERVAL_REMOVED, index0, index1);
            }
            ((ListDataListener) l[i]).intervalRemoved(e);
        }
        m_bIsAdjusting = false;
    }

    /**
     *
     * @throws BasicException
     */
    public void refreshData() throws BasicException {

        putNewData(m_dataprov == null
                ? null
                : m_dataprov.refreshData());
    }

    /**
     *
     * @throws BasicException
     */
    public void loadData() throws BasicException {

        putNewData(m_dataprov == null
                ? null
                : m_dataprov.loadData());
    }

    /**
     *
     * @throws BasicException
     */
    public void unloadData() throws BasicException {
        putNewData(null);
    }

    public void loadList(List l) {
        putNewData(l);
    }

    /**
     *
     * @throws BasicException
     */
    public void sort(Comparator c) throws BasicException {

        Collections.sort(m_aData, c);
        putNewData(m_aData);
    }

    public final boolean canLoadData() {
        return m_dataprov != null;
    }

    public boolean canInsertData() {
        return m_saveprov != null && m_saveprov.canInsert();
    }

    public boolean canDeleteData() {
        return m_saveprov != null && m_saveprov.canDelete();
    }

    public boolean canUpdateData() {
        return m_saveprov != null && m_saveprov.canUpdate();
    }

    /**
     *
     * @throws BasicException
     */
    public final int findNext(int index, Finder f) throws BasicException {
        int i = index + 1;

        // search up to the end of the recordset
        while (i < m_aData.size()) {
            if (f.match(this.getElementAt(i))) {
                return i;
            }
            i++;
        }
        // search from the begining
        i = 0;
        while (i < index) {
            if (f.match(this.getElementAt(i))) {
                return i;
            }
            i++;
        }

        // No se ha encontrado
        return -1;
    }

    /**
     *
     * @throws BasicException
     */
    public final int removeRecord(int index) throws BasicException {
        if (canDeleteData() && index >= 0 && index < m_aData.size()) {
            if (m_saveprov.deleteData(getElementAt(index)) > 0) {
                // borramos el elemento indicado
                m_aData.remove(index);
                // disparamos los eventos
                fireDataIntervalRemoved(index, index);

                int newindex;
                if (index < m_aData.size()) {
                    newindex = index;
                } else {
                    newindex = m_aData.size() - 1;
                }
                return newindex;
            } else {
                throw new BasicException(LocalRes.getIntString("exception.nodelete"));
            }
        } else {
            // indice no valido
            throw new BasicException(LocalRes.getIntString("exception.nodelete"));
        }
    }

    /**
     *
     * @throws BasicException
     */
    public final int updateRecord(int index, Object value) throws BasicException {

        if (canUpdateData() && index >= 0 && index < m_aData.size()) {
            if (m_saveprov.updateData(value) > 0) {
                // Modificamos el elemento indicado
                int newindex;
                if (m_comparer == null) {
                    newindex = index;
                    m_aData.set(newindex, value);
                } else {
                    // lo movemos
                    newindex = insertionPoint(value);
                    if (newindex == index + 1) {
                        newindex = index;
                        m_aData.set(newindex, value);
                    } else if (newindex > index + 1) {
                        m_aData.remove(index);
                        newindex --;
                        m_aData.add(newindex, value);
                    } else {
                        m_aData.remove(index);
                        m_aData.add(newindex, value);
                    }
                }

                if (newindex >= index) {
                    fireDataContentsChanged(index, newindex);
                } else {
                    fireDataContentsChanged(newindex, index);
                }
                return newindex;
            } else {
                // fallo la actualizacion
                throw new BasicException(LocalRes.getIntString("exception.noupdate"));
            }
        } else {
            // registro invalido
            throw new BasicException(LocalRes.getIntString("exception.noupdate"));
        }
    }

    /**
     *
     * @throws BasicException
     */
    public final int insertRecord(Object value) throws BasicException {

        if (canInsertData() && m_saveprov.insertData(value) > 0) {
            int newindex;
            if (m_comparer == null) {
                // Anadimos el elemento indicado al final...
                newindex = m_aData.size();
             } else {
                 // lo insertamos en el lugar adecuado
                newindex = insertionPoint(value);
             }
             m_aData.add(newindex, value);

            // Disparamos la inserccion
            fireDataIntervalAdded(newindex, newindex);
            return newindex;
        } else {
            throw new BasicException(LocalRes.getIntString("exception.noinsert"));
        }
    }

    private void putNewData(List aData) {

        int oldSize = m_aData.size();
        m_aData = (aData == null) ? new ArrayList() : aData;
        int newSize = m_aData.size();

        // Ordeno si es un Browsabledata ordenado
        if (m_comparer != null) {
            Collections.sort(m_aData, m_comparer);
        }

        fireDataContentsChanged(0, newSize - 1);
        if (oldSize > newSize) {
            fireDataIntervalRemoved(newSize, oldSize - 1);
        } else if (oldSize < newSize) {
            fireDataIntervalAdded(oldSize, newSize - 1);
        }
    }

    private final int insertionPoint(Object value) {

	int low = 0;
	int high = m_aData.size() - 1;

	while (low <= high) {
	    int mid = (low + high) >> 1;
	    Object midVal = m_aData.get(mid);
	    int cmp = m_comparer.compare(midVal, value);

	    if (cmp <= 0) {
		low = mid + 1;
            } else {
		high = mid - 1;
            }
	}
	return low;
    }
}
