/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
var app = {
    // Application Constructor
    initialize: function() {
        this.bindEvents();
    },
    // Bind Event Listeners
    //
    // Bind any events that are required on startup. Common events are:
    // 'load', 'deviceready', 'offline', and 'online'.
    bindEvents: function() {
        document.addEventListener('deviceready', this.onDeviceReady, false);
    },
    // deviceready Event Handler
    //
    // The scope of 'this' is the event. In order to call the 'receivedEvent'
    // function, we must explicity call 'app.receivedEvent(...);'
    onDeviceReady: function() {
        app.receivedEvent('deviceready');
        app.setupMenu();
    },
    // Update DOM on a Received Event
    receivedEvent: function(id) {
        var parentElement = document.getElementById(id);
        var listeningElement = parentElement.querySelector('.listening');
        var receivedElement = parentElement.querySelector('.received');

        listeningElement.setAttribute('style', 'display:none;');
        receivedElement.setAttribute('style', 'display:block;');

        console.log('Received Event: ' + id);
    },
    setupMenu: function() {
        var self = this;
        menu.initialize([
            { id: 1, order: 1, name: "Home" },
            { id: 2, order: 1, name: "Page2" },
            { id: 3, order: 1, name: "Page3" },
            { id: 4, order: 1, name: "Page4" },
            { id: 5, order: 1, name: "Page5" },
            { id: 6, order: 1, name: "Page6" },
            { id: 7, order: 1, name: "Page7" },
            { id: 8, order: 1, name: "Page8" },
            { id: 9, order: 1, name: "Page9" },
        ]);
        menu.optionItemClick = function (itemId) {
        	if (itemId == 1) {
        		alert('Home page clicked');
        	}
        	if (itemId == 2) {
                alert('Page 2 clicked');
        	}
        	if (itemId == 3) {
        		alert('Page 3 clicked');
        	}
        	if (itemId == 4) {
                alert('Page 4 clicked');
            }
            if (itemId == 5) {
                alert('Page 5 clicked');
            }
            if (itemId == 6) {
                alert('Page 6 clicked');
        	}
            if (itemId == 7) {
                alert('Page 7 clicked');
            }
            if (itemId == 8) {
                alert('Page 8 clicked');
            }
            if (itemId == 9) {
                alert('Page 9 clicked');
        	}
        	console.log(itemId);
        };
    }
};
