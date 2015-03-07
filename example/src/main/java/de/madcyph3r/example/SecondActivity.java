package de.madcyph3r.example;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.madcyph3r.example.tools.AllMenuTypes;
import de.madcyph3r.example.tools.BackPatternCustom;
import de.madcyph3r.example.tools.BackPatternDefault;
import de.madcyph3r.example.tools.BackPatternStartIndex;
import de.madcyph3r.example.tools.CustomHeaderActivity;
import de.madcyph3r.example.tools.FiveHeadItem;
import de.madcyph3r.example.tools.FiveHeadItemExtraMenu;
import de.madcyph3r.example.tools.HeadItemAvaterBackgroundClick;
import de.madcyph3r.example.tools.HeadItemStartSecSection;
import de.madcyph3r.example.tools.ImageHeaderActivity;
import de.madcyph3r.example.tools.NoHeaderActivity;
import de.madcyph3r.example.tools.OneHeadItem;
import de.madcyph3r.example.tools.OneHeadItemDark;
import de.madcyph3r.example.tools.OneHeadItemLight;
import de.madcyph3r.example.tools.OneHeadItemMyTheme;
import de.madcyph3r.example.tools.OneHeadItemOwnDrawerWidth;
import de.madcyph3r.example.tools.OneHeadItemOwnFragment;
import de.madcyph3r.example.tools.ThreeHeadItem;
import de.madcyph3r.example.tools.ThreeHeadItemNoCloseDrawer;
import de.madcyph3r.example.tools.TwoHeadItemChangeListenerDrawerListener;
import de.madcyph3r.example.tools.TwoHeadItemExtraMenu;
import de.madcyph3r.example.tools.TwoHeadItemNoFragementLoadOnChange;
import de.madcyph3r.example.tools.TwoHeadItemOneMenu;
import de.madcyph3r.example.tools.TwoHeadItemRemoveAddNewHeadItem;

public class SecondActivity extends ActionBarActivity {

    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.secondactivity);

    }
}