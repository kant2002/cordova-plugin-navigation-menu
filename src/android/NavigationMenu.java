/*
   Copyright 2013-2014 Andrey Kurduymov

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package org.apache.cordova.navigationmenu;

import java.util.Collection;
import java.util.HashMap;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

public class NavigationMenu extends CordovaPlugin  {
	HashMap<Integer, JSONObject> items = new HashMap<Integer, JSONObject>();
    // JSONArray items;
    boolean menuinitialized = false;
    /**
     * Executes the request and returns whether the action was valid.
     *
     * @param action                 The action to execute.
     * @param args                 JSONArray of arguments for the plugin.
     * @param callbackContext        The callback context used when calling back into JavaScript.
     * @return                         True if the action was valid, false otherwise.
     */
    public boolean execute(String action, final JSONArray args, final CallbackContext callbackContext) throws JSONException {
        if (action.equals("initialize")) {
            if (!this.menuinitialized) {
                JSONArray menuItems = args.getJSONArray(0);
                for (int i = 0; i < menuItems.length(); i++) {
					JSONObject menuItem = menuItems.getJSONObject(i);
					Integer id = menuItem.getInt("id");
					items.put(id, menuItem);
				}
                
                if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
                    invalidateOptionsMenu();
                }
                
                callbackContext.success();
            } else {
                callbackContext.error("Menu should be initialized before user pressed on the menu button.");
            }
            
            return true;
        } else if (action.equals("showPopup")) {
            if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            	callbackContext.error("Popup menus requires API at least 11 level");
            	return true;
            } 
            
            JSONArray items = args.getJSONArray(0);
            this.showPopup(items);
            callbackContext.success();
            
            return true;
        } else {
            return false;
        }
    }
    
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void invalidateOptionsMenu() {
    	this.cordova.getActivity().invalidateOptionsMenu();
    }
    /**
     * Displays popup menu.
     *
     * @param items   The array of objects which describes menu items.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    protected void showPopup(JSONArray items) {
    	final PopupMenu popup = new PopupMenu(this.webView.getContext(), this.webView);
    	
    	PopupMenu.OnMenuItemClickListener handler = new PopupMenu.OnMenuItemClickListener(){
    		public boolean onMenuItemClick(MenuItem item) {
    			int itemId = item.getItemId();
    			webView.sendJavascript("menu.popupItemClick && menu.popupItemClick(" + itemId + ");");
    	    	return false;
    	    }
    	};
    	popup.setOnMenuItemClickListener(handler);
        Menu popupMenu = popup.getMenu();
        for (int i = 0; i < items.length(); i++) {
        	JSONObject menuItem = items.optJSONObject(i);
        	if (menuItem != null) {
        		appendItem(popupMenu, menuItem);
        	}
		}
        
        this.cordova.getActivity().runOnUiThread(new Runnable() { 
        	public void run() {
        		popup.show();
        	}
        });
        
    }

    /**
     * Called when a message is sent to plugin.
     *
     * @param id            The message id
     * @param data          The message data
     * @return              Object to stop propagation or null
     */
    public Object onMessage(String id, Object data) {
        if (id.equals("onCreateOptionsMenu")){
            this.menuinitialized = true;
            Menu menu = (Menu)data;
            Object[] items = this.items.values().toArray();
            for (Object jsonObject : items) {
        		JSONObject menuItem = (JSONObject)jsonObject;
            	appendItem(menu, menuItem);
    		}

            this.webView.sendJavascript("console.log('onCreateOptionsMenu');");
            return null;
        } else if (id.equals("onPrepareOptionsMenu")){
        	Menu menu = (Menu)data;
        	for (int i = 0; i< menu.size(); i++){
        		MenuItem menuItem = menu.getItem(i);
        		int itemId = menuItem.getItemId();
        		JSONObject itemDescription = this.items.get(itemId);
        		boolean enabled = itemDescription.optBoolean("enabled", true);
				enableMenuItem(menuItem.setEnabled(enabled), enabled);
        	}
        } else if (id.equals("onOptionsItemSelected")){
        	MenuItem menuItem = (MenuItem)data;
    		int itemId = menuItem.getItemId();
            this.webView.sendJavascript("menu.optionItemClick && menu.optionItemClick(" + itemId + ");");
        }
        
        return null;
    }
    
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void enableMenuItem(MenuItem menuItem, boolean enable){
    	View actionView = menuItem.getActionView();
    	if (actionView != null) {
    		if (enable) {
    			actionView.setAlpha(255);
    		}else {
    			actionView.setAlpha(130);
    		}
    	}
    }

	private void appendItem(Menu menu, JSONObject menuItem) {
		try {
			Integer menuId = menuItem.getInt("id");
			Integer order = menuItem.optInt("order", 1);
			String name = menuItem.getString("name");
			menu.add(Menu.NONE, menuId, order, name);	
		} catch (JSONException e) {
			this.webView.sendJavascript("console.log('Invalid menu option');");
		}
	}
}
