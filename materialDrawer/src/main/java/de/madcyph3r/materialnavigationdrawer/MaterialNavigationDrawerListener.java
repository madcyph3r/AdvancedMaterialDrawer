package de.madcyph3r.materialnavigationdrawer;

import de.madcyph3r.materialnavigationdrawer.item.MaterialHeadItem;

public interface MaterialNavigationDrawerListener {

    public void onBeforeChangedHeadItem(MaterialHeadItem newHeadItem);

    public void onAfterChangedHeadItem(MaterialHeadItem newHeadItem);
}
