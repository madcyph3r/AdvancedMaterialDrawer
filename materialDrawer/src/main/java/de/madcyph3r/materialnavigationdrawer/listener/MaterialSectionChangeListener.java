package de.madcyph3r.materialnavigationdrawer.listener;

import de.madcyph3r.materialnavigationdrawer.head.MaterialHeadItem;
import de.madcyph3r.materialnavigationdrawer.menu.item.MaterialSection;

/**
 * Created by marc on 08.03.2015.
 */
public interface MaterialSectionChangeListener {
    public void onBeforeChangedSection(MaterialSection newSection);

    public void onAfterChangedSection(MaterialSection newSection);
}
