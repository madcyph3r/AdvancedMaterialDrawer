package de.madcyph3r.materialnavigationdrawer.listener;

import de.madcyph3r.materialnavigationdrawer.head.MaterialHeadItem;

public interface MaterialHeadItemChangeListener {

    public void onBeforeChangeHeadItem(MaterialHeadItem newHeadItem);

    public void onAfterChangeHeadItem(MaterialHeadItem newHeadItem);
}
