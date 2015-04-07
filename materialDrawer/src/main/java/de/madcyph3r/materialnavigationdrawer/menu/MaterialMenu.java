package de.madcyph3r.materialnavigationdrawer.menu;

import java.util.ArrayList;
import java.util.List;

import de.madcyph3r.materialnavigationdrawer.menu.item.MaterialSection;

public class MaterialMenu {

    public static int SECTION = 0;
    public static int BOTTOM_SECTION = 1;
    public static int DIVISOR = 2;

    private int startIndex;

    public MaterialMenu() {
        startIndex = -1;
    }

    private List<Object> items = new ArrayList<Object>();

    public List<Object> getItems() {
        return items;
    }

    public void sesetItems(List<Object> items) {
        this.items = items;
    }

    public void addItem(Object item) {
        getItems().add(item);
    }

    public void addItem(Object item, int position) {
        getItems().add(position, item);
    }

    public void removeItem(Object item) {
        getItems().remove(item);
    }

    public void removeItem(int position) {
        getItems().remove(position);
    }

    public int getItemPosition(Object item) {
        return getItems().indexOf(item);
    }

    public Object getItem(int position) {
        return getItems().get(position);
    }

    // return the real pos int the list, considered devisor and label
    public int getRealSectionPosition(MaterialSection section) {
        return getItems().indexOf(section);
    }

    // return position, does not considered devisor and label
    public int getSectionPosition(MaterialSection section) {
        //if (section instanceof MaterialSection) {
        int pos = 0;
        for (int i = 0; i < getItems().size(); i++) {
            if (getItems().get(i) instanceof MaterialSection) {
                if (getItems().get(i) == section) {
                    return pos;
                }
                pos++;
            }
        }
       /* } else {
            throw new RuntimeException("Object is not a MaterialSection");
        }*/
        return -1;
    }

    public MaterialSection getSection(int position) {
        int pos = 0;
        for (int i = 0; i < getItems().size(); i++) {
            if (getItems().get(i) instanceof MaterialSection) {
                if (pos == position) {
                    return (MaterialSection) getItems().get(i);
                }
                pos++;
            }
        }
        return null;
    }

    public int getSectionSize() {
        int size = 0;
        for (int i = 0; i < getItems().size(); i++) {
            if (getItems().get(i) instanceof MaterialSection) {
                size++;
            }
        }
        return size;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public MaterialSection getSectionFromRealPosition(int position) {
        if (getItems().get(position) instanceof MaterialSection) {
            return (MaterialSection) getItems().get(position);
        } else {
            return null;
        }
    }
}
