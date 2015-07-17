package de.madcyph3r.materialnavigationdrawer.listener;

import de.madcyph3r.materialnavigationdrawer.menu.item.section.MaterialItemSection;

public interface MaterialSectionChangeListener {
    public void onBeforeChangeSection(MaterialItemSection newSection);

    public void onAfterChangeSection(MaterialItemSection newSection);
}
