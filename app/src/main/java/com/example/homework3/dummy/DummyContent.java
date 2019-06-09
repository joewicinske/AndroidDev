package com.example.homework3.dummy;

import com.example.homework3.GlucoseData;
import com.example.homework3.MainActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<GlucoseData> ITEMS = new ArrayList<>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, GlucoseData> ITEM_MAP = new HashMap<String, GlucoseData>();

    private static final int COUNT = 3;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(GlucoseData item) {
        ITEMS.add(item);
        ITEM_MAP.put(String.valueOf(item.getId()), item);
    }

    private static GlucoseData createDummyItem(int position) {
        GlucoseData  glucoseDataItem = new GlucoseData();
        glucoseDataItem.setId(position);
        glucoseDataItem.setFasting(75);
        glucoseDataItem.setDinner(80);
        glucoseDataItem.setLunch(100);
        glucoseDataItem.setBreakfast(90);
        Date entryDate = new Date();
        int monthNum = (position+2) % 12;
        Calendar cal = Calendar.getInstance();
        cal.setTime(entryDate); // your date (java.util.Date)
        cal.add(Calendar.MONTH, monthNum); // You can -/+ x months here to go back in history or move forward.
        glucoseDataItem.setEntryDate(GlucoseData.getDateLabelTxt(cal.getTime()));
        return glucoseDataItem;
    }

}
