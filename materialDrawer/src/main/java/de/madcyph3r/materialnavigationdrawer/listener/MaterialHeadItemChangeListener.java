package de.madcyph3r.materialnavigationdrawer.listener;

import de.madcyph3r.materialnavigationdrawer.head.MaterialHeadItem;

public interface MaterialHeadItemChangeListener {

    public void onBeforeChangedHeadItem(MaterialHeadItem newHeadItem);

    public void onAfterChangedHeadItem(MaterialHeadItem newHeadItem);
}
