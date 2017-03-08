package com.github.privacystreams.accessibility;

import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.github.privacystreams.core.Item;
import com.github.privacystreams.core.providers.MultiItemStreamProvider;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author toby
 * @date 2/28/17
 * @time 10:53 AM
 */
public class BaseAccessibilityEvent extends Item {

    public static final String TIMESTAMP = "timestamp";
    public static final String EVENT_TYPE = "event_type";
    public static final String PACKAGE_NAME = "package_name";
    public static final String UI_NODE_LIST = "ui_node_list";
    public static final String ROOT_VIEW = "root_view";
    public static final String ITEM_COUNT = "item_count";

    BaseAccessibilityEvent(AccessibilityEvent accessibilityEvent, AccessibilityNodeInfo rootNode, Date timeStamp){
        this.setFieldValue(EVENT_TYPE, accessibilityEvent.getEventType());
        this.setFieldValue(TIMESTAMP, timeStamp);
        this.setFieldValue(PACKAGE_NAME, accessibilityEvent.getPackageName() != null ? accessibilityEvent.getPackageName() : "NULL");
        this.setFieldValue(UI_NODE_LIST, getUINodeList(rootNode));
        this.setFieldValue(ROOT_VIEW,rootNode);
        this.setFieldValue(ITEM_COUNT,accessibilityEvent.getItemCount());
    }

    private List<AccessibilityNodeInfo> getUINodeList(AccessibilityNodeInfo rootNode){
        List<AccessibilityNodeInfo> list = new ArrayList<>();
        if(rootNode == null)
            return list;
        list.add(rootNode);
        int childCount = rootNode.getChildCount();
        for(int i = 0; i < childCount; i ++){
            AccessibilityNodeInfo node = rootNode.getChild(i);
            if(node != null)
                list.addAll(getUINodeList(node));
        }
        return list;
    }

    //TODO: FOR TESTING PURPOSE ONLY
//    @Override
//    public String toString(){
//        String eventType = "";
//        int eventTypeInt = getValueByField(EVENT_TYPE);
//        switch (eventTypeInt){
//            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
//                eventType = "TYPE_WINDOW_CONTENT_CHANGED";
//                break;
//            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
//                eventType = "TYPE_WINDOW_STATE_CHANGED";
//                break;
//            case AccessibilityEvent.TYPE_WINDOWS_CHANGED:
//                eventType = "TYPE_WINDOWS_CHANGED";
//                break;
//            case AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED:
//                eventType = "VIEW_TEXT_SELECTION_CHANGED";
//                break;
//        }
//
//
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS");
//        String timeStamp = format.format(getValueByField(TIMESTAMP));
//
//        String packageName = getValueByField(PACKAGE_NAME).toString();
//
//        String listSize = String.valueOf(((List<AccessibilityNodeInfo>)getValueByField(UI_NODE_LIST)).size());
//
//        return timeStamp + " " + eventType + " " + packageName + " " + "NODE_COUNT: " + listSize;
//
//    }

    public static MultiItemStreamProvider asUpdates() {
        return new BaseAccessibilityEventProvider();
    }
}