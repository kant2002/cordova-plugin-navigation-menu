/*
 * Copyright 2013-2014 Andrey Kurduymov
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
*/

var argscheck = require('cordova/argscheck'),
    utils = require('cordova/utils'),
    exec = require('cordova/exec');

/**
 * An interface representing a menu manager.
 *
 */
var NavigationMenu = function () {
};

/**
 * Initialize menu with given menu items .
 *
 * @param items
 *            {Array} of MenuItem objects.
 */
NavigationMenu.prototype.initialize = function (items) {
    argscheck.checkArgs('A', 'MenuManager.initialize', arguments);
    console.log("Menu initialized");
    exec(null, null, "NavigationMenu", "initialize", [items]);
}

/**
 * Show popup menu with given menu items .
 *
 * @param items
 *            {Array} of MenuItem objects.
 */
NavigationMenu.prototype.showPopup = function (items) {
    argscheck.checkArgs('A', 'MenuManager.showPopup', arguments);
    exec(null, null, "NavigationMenu", "showPopup", [items]);
}

/**
 * Set menu items.
 *
 * @param successCallback
 *            {Function} is called when orientation is changed successfully.
 * @param errorCallback
 *            {Function} is called with a orientation change was done with error.
 */
NavigationMenu.prototype.setMenuItem = function (item) {
    argscheck.checkArgs('O', 'MenuManager.setMenuItem', arguments);
    var success = successCallback && function (lastModified) {
        console.log("Sucess call to setOrientation");
        //var metadata = new Metadata(lastModified);
        //successCallback(metadata);
    };
    var fail = errorCallback && function(code) {
        console.log("Error during call to setOrientation");
        //errorCallback(new FileError(code));
    };

    exec(success, fail, "NavigationMenu", "setMenuItem", [item]);
};

module.exports = new NavigationMenu();
