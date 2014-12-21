package de.madcyph3r.materialnavigationdrawer;

import de.madcyph3r.materialnavigationdrawer.item.MaterialHeadItem;

/**
 * Created by neokree on 11/12/14.
 */
public interface MaterialNavigationDrawerListener {

    public void onBeforeChangedHeadItem(MaterialHeadItem newHeadItem);

    public void onAfterChangedHeadItem(MaterialHeadItem newHeadItem);
}
