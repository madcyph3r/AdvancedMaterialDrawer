package de.madcyph3r.materialnavigationdrawer.menu;

import java.util.ArrayList;
import java.util.List;

import de.madcyph3r.materialnavigationdrawer.menu.item.MaterialMenuItem;
import de.madcyph3r.materialnavigationdrawer.menu.item.section.MaterialItemSection;
import de.madcyph3r.materialnavigationdrawer.menu.item.section.MaterialItemSectionFragment;
import de.madcyph3r.materialnavigationdrawer.menu.item.section.MaterialItemSectionOnClick;

public class MaterialMenu {

    public static int SECTION = 0;
    public static int BOTTOM_SECTION = 1;
    public static int DIVISOR = 2;
    private List<MaterialMenuItem> items;

    private int startIndex;

    public MaterialMenu() {
        startIndex = -1;
        items = new ArrayList<MaterialMenuItem>();
    }

    public MaterialItemSectionFragment getFirstFragmentItem() {
        for (int i = 0; i < getItems().size(); i++) {
            if (getItems().get(i) instanceof MaterialItemSectionFragment) {
                return ((MaterialItemSectionFragment)getItems().get(i));
            }
        }

        return null;
    }

    public List<MaterialMenuItem> getItems() {
        return items;
    }

    /*
    public void resetItems(List<Object> items) {
        this.items = items;
    }*/


    public void add(MaterialMenuItem materialMenuItem) {
        items.add(materialMenuItem);
    }

    public void add(int position, MaterialMenuItem materialMenuItem) {
        items.add(position, materialMenuItem);
    }

    public void remove(MaterialMenuItem materialMenuItem) {
        getItems().remove(materialMenuItem);
    }

    public int getPosition(MaterialMenuItem materialMenuItem) {
        return getItems().indexOf(materialMenuItem);
    }

    public MaterialMenuItem getItem(int position) {
        return getItems().get(position);
    }

    // return the real pos int the list, considered devisor and label
    public int getRealSectionPosition(MaterialItemSection section) {
        return getItems().indexOf(section);
    }

    // return position, does not considered devisor and label
    public int getSectionPosition(MaterialItemSection section) {
        //if (section instanceof MaterialItemSectionOnClick) {
        int pos = 0;
        for (int i = 0; i < getItems().size(); i++) {
            if (getItems().get(i) instanceof MaterialItemSection) {
                if (getItems().get(i) == section) {
                    return pos;
                }
                pos++;
            }
        }
       /* } else {
            throw new RuntimeException("Object is not a MaterialItemSectionOnClick");
        }*/
        return -1;
    }

    public MaterialItemSection getSection(int position) {
        int pos = 0;
        for (int i = 0; i < getItems().size(); i++) {
            if (getItems().get(i) instanceof MaterialItemSection) {
                if (pos == position) {
                    return (MaterialItemSection) getItems().get(i);
                }
                pos++;
            }
        }
        return null;
    }

    public int getSectionSize() {
        int size = 0;
        for (int i = 0; i < getItems().size(); i++) {
            if (getItems().get(i) instanceof MaterialItemSection) {
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

    public MaterialItemSectionOnClick getSectionFromRealPosition(int position) {
        if (getItems().get(position) instanceof MaterialItemSectionOnClick) {
            return (MaterialItemSectionOnClick) getItems().get(position);
        } else {
            return null;
        }
    }
}
