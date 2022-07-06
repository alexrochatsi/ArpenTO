/*************************************************************************************************
/******************************************YAHOO.js***********************************************
/*************************************************************************************************

/*
Copyright (c) 2006 Yahoo! Inc. All rights reserved.
version 0.9.0
*/

/**
 * @class The Yahoo global namespace
 */
var YAHOO = function() {
    return {
        /**
         * Yahoo presentation platform utils namespace
         */
        util: {},
        /**
         * Yahoo presentation platform widgets namespace
         */
        widget: {},
        /**
         * Yahoo presentation platform examples namespace
         */
        example: {},
        /**
         * Returns the namespace specified and creates it if it doesn't exist
         *
         * YAHOO.namespace("property.package");
         * YAHOO.namespace("YAHOO.property.package");
         *
         * Either of the above would create YAHOO.property, then
         * YAHOO.property.package
         *
         * @param  {String} sNameSpace String representation of the desired
         *                             namespace
         * @return {Object}            A reference to the namespace object
         */
        namespace: function( sNameSpace ) {

            if (!sNameSpace || !sNameSpace.length) {
                return null;
            }

            var levels = sNameSpace.split(".");

            var currentNS = YAHOO;

            // YAHOO is implied, so it is ignored if it is included
            for (var i=(levels[0] == "YAHOO") ? 1 : 0; i<levels.length; ++i) {
                currentNS[levels[i]] = currentNS[levels[i]] || {};
                currentNS = currentNS[levels[i]];
            }

            return currentNS;

        }
    };

} ();

// Yahoo presentation platform packages.  Hard-coding them into the object
// uses fewer chars than the namespace function does
// YAHOO.namespace("util");
// YAHOO.namespace("widget");
// YAHOO.namespace("example");

/*************************************************************************************************
/*******************************************dom.js************************************************
/*************************************************************************************************

/*
Copyright (c) 2006 Yahoo! Inc. All rights reserved.
version 0.9.0
*/

/**
 * @class Provides helper methods for DOM elements.
 */
YAHOO.util.Dom = new function() {

   /**
    * Returns an HTMLElement reference
    * @param {String | HTMLElement} Accepts either a string to use as an ID for getting a DOM reference, or an actual DOM reference.
    * @return {HTMLElement} A DOM reference to an HTML element.
    */
   this.get = function(el) {
      if (typeof el == 'string') { // accept object or id
         el = document.getElementById(el);
      }

      return el;
   };

   /**
    * Normalizes currentStyle and ComputedStyle.
    * @param {String | HTMLElement} Accepts either a string to use as an ID for getting a DOM reference, or an actual DOM reference.
    * @param {String} property The style property whose value is returned.
    * @return {String} The current value of the style property.
    */
   this.getStyle = function(el, property) {
      var value = null;
      var dv = document.defaultView;

      el = this.get(el);

      if (property == 'opacity' && el.filters) {// IE opacity
         value = 1;
         try {
            value = el.filters.item('DXImageTransform.Microsoft.Alpha').opacity / 100;
         } catch(e) {
            try {
               value = el.filters.item('alpha').opacity / 100;
            } catch(e) {}
         }
      }
      else if (el.style[property]) {
         value = el.style[property];
      }
      else if (el.currentStyle && el.currentStyle[property]) {
         value = el.currentStyle[property];
      }
      else if ( dv && dv.getComputedStyle )
      {  // convert camelCase to hyphen-case

         var converted = '';
         for(i = 0, len = property.length;i < len; ++i) {
            if (property.charAt(i) == property.charAt(i).toUpperCase()) {
               converted = converted + '-' + property.charAt(i).toLowerCase();
            } else {
               converted = converted + property.charAt(i);
            }
         }

         if (dv.getComputedStyle(el, '').getPropertyValue(converted)) {
            value = dv.getComputedStyle(el, '').getPropertyValue(converted);
         }
      }

      return value;
   };

   /**
    * Wrapper for setting style properties of HTMLElements.  Normalizes "opacity" across modern browsers.
    * @param {String | HTMLElement} Accepts either a string to use as an ID for getting a DOM reference, or an actual DOM reference.
    * @param {String} property The style property to be set.
    * @param {String} val The value to apply to the given property.
    */
   this.setStyle = function(el, property, val) {
      el = this.get(el);
      switch(property) {
         case 'opacity' :
            if (el.filters) {
               el.style.filter = 'alpha(opacity=' + val * 100 + ')';

               if (!el.currentStyle.hasLayout) {
                  el.style.zoom = 1;
               }
            } else {
               el.style.opacity = val;
               el.style['-moz-opacity'] = val;
               el.style['-khtml-opacity'] = val;
            }
            break;
         default :
            el.style[property] = val;
      }
   };

   /**
    * Gets the current position of an element based on page coordinates.  Element must be part of the DOM tree to have page coordinates (display:none or elements not appended return false).
    * @param {String | HTMLElement} Accepts either a string to use as an ID for getting a DOM reference, or an actual DOM reference.
    */
   this.getXY = function(el) {
      el = this.get(el);

      // has to be part of document to have pageXY
      if (el.parentNode === null || this.getStyle(el, 'display') == 'none') {
         return false;
      }

      /**
       * Position of the html element (x, y)
       * @private
       * @type Array
       */
      var parent = null;
      var pos = [];
      var box;

      if (el.getBoundingClientRect) { // IE
         box = el.getBoundingClientRect();
         var scrollTop = document.documentElement.scrollTop || document.body.scrollTop;
         var scrollLeft = document.documentElement.scrollLeft || document.body.scrollLeft;

         return [box.left + scrollLeft, box.top + scrollTop];
      }
      else if (document.getBoxObjectFor) { // gecko
         box = document.getBoxObjectFor(el);
         pos = [box.x, box.y];
      }
      else { // safari/opera
         pos = [el.offsetLeft, el.offsetTop];
         parent = el.offsetParent;
         if (parent != el) {
            while (parent) {
               pos[0] += parent.offsetLeft;
               pos[1] += parent.offsetTop;
               parent = parent.offsetParent;
            }
         }

         // opera & (safari absolute) incorrectly account for body offsetTop
         var ua = navigator.userAgent.toLowerCase();
         if (
            ua.indexOf('opera') != -1
            || ( ua.indexOf('safari') != -1 && this.getStyle(el, 'position') == 'absolute' )
         ) {
            pos[1] -= document.body.offsetTop;
         }
      }

      if (el.parentNode) { parent = el.parentNode; }
      else { parent = null; }

      while (parent && parent.tagName != 'BODY' && parent.tagName != 'HTML') {
         pos[0] -= parent.scrollLeft;
         pos[1] -= parent.scrollTop;

         if (parent.parentNode) { parent = parent.parentNode; }
         else { parent = null; }
      }

      return pos;
   };

   /**
    * Gets the current X position of an element based on page coordinates.  The element must be part of the DOM tree to have page coordinates (display:none or elements not appended return false).
    * @param {String | HTMLElement} Accepts either a string to use as an ID for getting a DOM reference, or an actual DOM reference.
    */
   this.getX = function(el) {
      return this.getXY(el)[0];
   };

   /**
    * Gets the current Y position of an element based on page coordinates.  Element must be part of the DOM tree to have page coordinates (display:none or elements not appended return false).
    * @param {String | HTMLElement} Accepts either a string to use as an ID for getting a DOM reference, or an actual DOM reference.
    */
   this.getY = function(el) {
      return this.getXY(el)[1];
   };

   /**
    * Set the position of an html element in page coordinates, regardless of how the element is positioned.
    * The element must be part of the DOM tree to have page coordinates (display:none or elements not appended return false).
    * @param {String | HTMLElement} Accepts either a string to use as an ID for getting a DOM reference, or an actual DOM reference.
    * @param {array} pos Contains X & Y values for new position (coordinates are page-based)
    */
   this.setXY = function(el, pos, noRetry) {
      el = this.get(el);
      var pageXY = YAHOO.util.Dom.getXY(el);
      if (pageXY === false) { return false; } // has to be part of doc to have pageXY

      if (this.getStyle(el, 'position') == 'static') { // default to relative
         this.setStyle(el, 'position', 'relative');
      }

      var delta = [
         parseInt( YAHOO.util.Dom.getStyle(el, 'left'), 10 ),
         parseInt( YAHOO.util.Dom.getStyle(el, 'top'), 10 )
      ];

      if ( isNaN(delta[0]) ) { delta[0] = 0; } // defalts to 'auto'
      if ( isNaN(delta[1]) ) { delta[1] = 0; }

      if (pos[0] !== null) { el.style.left = pos[0] - pageXY[0] + delta[0] + 'px'; }
      if (pos[1] !== null) { el.style.top = pos[1] - pageXY[1] + delta[1] + 'px'; }

      var newXY = this.getXY(el);

      // if retry is true, try one more time if we miss
      if (!noRetry && (newXY[0] != pos[0] || newXY[1] != pos[1]) ) {
         this.setXY(el, pos, true);
      }

      return true;
   };

   /**
    * Set the X position of an html element in page coordinates, regardless of how the element is positioned.
    * The element must be part of the DOM tree to have page coordinates (display:none or elements not appended return false).
    * @param {String | HTMLElement} Accepts either a string to use as an ID for getting a DOM reference, or an actual DOM reference.
    * @param {Int} x to use as the X coordinate.
    */
   this.setX = function(el, x) {
      return this.setXY(el, [x, null]);
   };

   /**
    * Set the Y position of an html element in page coordinates, regardless of how the element is positioned.
    * The element must be part of the DOM tree to have page coordinates (display:none or elements not appended return false).
    * @param {String | HTMLElement} Accepts either a string to use as an ID for getting a DOM reference, or an actual DOM reference.
    * @param {Int} Value to use as the Y coordinate.
    */
   this.setY = function(el, y) {
      return this.setXY(el, [null, y]);
   };

   /**
    * Returns the region position of the given element.
    * The element must be part of the DOM tree to have a region (display:none or elements not appended return false).
    * @param {String | HTMLElement} Accepts either a string to use as an ID for getting a DOM reference, or an actual DOM reference.
    * @return {Region} A Region instance containing "top, left, bottom, right" member data.
    */
   this.getRegion = function(el) {
      el = this.get(el);
      return new YAHOO.util.Region.getRegion(el);
   };

   /**
    * Returns the width of the client (viewport).
    * @return {Int} The width of the viewable area of the page.
    */
   this.getClientWidth = function() {
      return (
         document.documentElement.offsetWidth
         || document.body.offsetWidth
      );
   };

   /**
    * Returns the height of the client (viewport).
    * @return {Int} The height of the viewable area of the page.
    */
   this.getClientHeight = function() {
      return (
         self.innerHeight
         || document.documentElement.clientHeight
         || document.body.clientHeight
      );
   };
};

/**
 * @class A region is a representation of an object on a grid.  It is defined
 * by the top, right, bottom, left extents, so is rectangular by default.  If
 * other shapes are required, this class could be extended to support it.
 *
 * @param {int} t the top extent
 * @param {int} r the right extent
 * @param {int} b the bottom extent
 * @param {int} l the left extent
 * @constructor
 */
YAHOO.util.Region = function(t, r, b, l) {

    /**
     * The region's top extent
     * @type int
     */
    this.top = t;

    /**
     * The region's right extent
     * @type int
     */
    this.right = r;

    /**
     * The region's bottom extent
     * @type int
     */
    this.bottom = b;

    /**
     * The region's left extent
     * @type int
     */
    this.left = l;
};

/**
 * Returns true if this region contains the region passed in
 *
 * @param  {Region}  region The region to evaluate
 * @return {boolean}        True if the region is contained with this region,
 *                          else false
 */
YAHOO.util.Region.prototype.contains = function(region) {
    return ( region.left   >= this.left   &&
             region.right  <= this.right  &&
             region.top    >= this.top    &&
             region.bottom <= this.bottom    );
};

/**
 * Returns the area of the region
 *
 * @return {int} the region's area
 */
YAHOO.util.Region.prototype.getArea = function() {
    return ( (this.bottom - this.top) * (this.right - this.left) );
};

/**
 * Returns the region where the passed in region overlaps with this one
 *
 * @param  {Region} region The region that intersects
 * @return {Region}        The overlap region, or null if there is no overlap
 */
YAHOO.util.Region.prototype.intersect = function(region) {
    var t = Math.max( this.top,    region.top    );
    var r = Math.min( this.right,  region.right  );
    var b = Math.min( this.bottom, region.bottom );
    var l = Math.max( this.left,   region.left   );

    if (b >= t && r >= l) {
        return new YAHOO.util.Region(t, r, b, l);
    } else {
        return null;
    }
};

/**
 * Returns the region representing the smallest region that can contain both
 * the passed in region and this region.
 *
 * @param  {Region} region The region that to create the union with
 * @return {Region}        The union region
 */
YAHOO.util.Region.prototype.union = function(region) {
    var t = Math.min( this.top,    region.top    );
    var r = Math.max( this.right,  region.right  );
    var b = Math.max( this.bottom, region.bottom );
    var l = Math.min( this.left,   region.left   );

    return new YAHOO.util.Region(t, r, b, l);
};

/**
 * toString
 * @return string the region properties
 */
YAHOO.util.Region.prototype.toString = function() {
    return ( "Region {" +
             "  t: "    + this.top    +
             ", r: "    + this.right  +
             ", b: "    + this.bottom +
             ", l: "    + this.left   +
             "}" );
}

/**
 * Returns a region that is occupied by the DOM element
 *
 * @param  {HTMLElement} el The element
 * @return {Region}         The region that the element occupies
 * @static
 */
YAHOO.util.Region.getRegion = function(el) {
    var p = YAHOO.util.Dom.getXY(el);

    var t = p[1];
    var r = p[0] + el.offsetWidth;
    var b = p[1] + el.offsetHeight;
    var l = p[0];

    return new YAHOO.util.Region(t, r, b, l);
};

/////////////////////////////////////////////////////////////////////////////


/**
 * @class
 *
 * A point is a region that is special in that it represents a single point on
 * the grid.
 *
 * @param {int} x The X position of the point
 * @param {int} y The Y position of the point
 * @constructor
 * @extends Region
 */
YAHOO.util.Point = function(x, y) {
    /**
     * The X position of the point
     * @type int
     */
    this.x      = x;

    /**
     * The Y position of the point
     * @type int
     */
    this.y      = y;

    this.top    = y;
    this.right  = x;
    this.bottom = y;
    this.left   = x;
};

YAHOO.util.Point.prototype = new YAHOO.util.Region();

/*************************************************************************************************
/*****************************************event.js************************************************
/*************************************************************************************************

/*
Copyright (c) 2006 Yahoo! Inc. All rights reserved.
version 0.9.0
*/

/**
 * @class The CustomEvent class lets you define events for your application
 * that can be subscribed to by one or more independent component.
 * @param {String} type The type of event, which is passed to the callback
 *                 when the event fires
 * @param {Object} oScope The context the event will fire from.  "this" will
 *                 refer to this object in the callback.  Default value:
 *                 the window object.  The listener can override this.
 * @constructor
 */
YAHOO.util.CustomEvent = function(type, oScope) {
    /**
     * The type of event, returned to subscribers when the event fires
     * @type string
     */
    this.type = type;

    /**
     * The scope the the event will fire from.  Defaults to the window obj
     * @type object
     */
    this.scope = oScope || window;

    /**
     * The subscribers to this event
     * @type array
     */
    this.subscribers = [];

    // Register with the event utility for automatic cleanup.  Made optional
    // so that CustomEvent can be used independently of pe.event
    if (YAHOO.util["Event"]) {
        YAHOO.util.Event.regCE(this);
    }
};

YAHOO.util.CustomEvent.prototype = {
    /**
     * Subscribes the caller to this event
     * @param {Function} fn       The function to execute
     * @param {Object}   obj      An object to be passed along when the event fires
     * @param {boolean}  bOverride If true, the obj passed in becomes the execution
     *                            scope of the listener
     */
    subscribe: function(fn, obj, bOverride) {
        this.subscribers.push( new YAHOO.util.Subscriber(fn, obj, bOverride) );
    },

    /**
     * Unsubscribes the caller from this event
     * @param {Function} fn  The function to execute
     * @param {Object}   obj An object to be passed along when the event fires
     * @return {boolean} True if the subscriber was found and detached.
     */
    unsubscribe: function(fn, obj) {
        var found = false;
        for (var i=0; i<this.subscribers.length; ++i) {
            var s = this.subscribers[i];
            if (s && s.contains(fn, obj)) {
                this._delete(i);
                found = true;
            }
        }

        return found;
    },

    /**
     * Notifies the subscribers.  The callback functions will be executed
     * from the scope specified when the event was created, and with the following
     * parameters:
     *   <pre>
     *   - The type of event
     *   - All of the arguments fire() was executed with as an array
     *   - The custom object (if any) that was passed into the subscribe() method
     *   </pre>
     *
     * @param {Array} an arbitrary set of parameters to pass to the handler
     */
    fire: function() {
        for (var i=0; i<this.subscribers.length; ++i) {
            var s = this.subscribers[i];
            if (s) {
                var scope = (s.override) ? s.obj : this.scope;
                s.fn.call(scope, this.type, arguments, s.obj);
            }
        }
    },

    /**
     * Removes all listeners
     */
    unsubscribeAll: function() {
        for (var i=0; i<this.subscribers.length; ++i) {
            this._delete(i);
        }
    },

    /**
     * @private
     */
    _delete: function(index) {
        var s = this.subscribers[index];
        if (s) {
            delete s.fn;
            delete s.obj;
        }

        delete this.subscribers[index];
    }
};

/////////////////////////////////////////////////////////////////////

/**
 * @class
 * @param {Function} fn       The function to execute
 * @param {Object}   obj      An object to be passed along when the event fires
 * @param {boolean}  bOverride If true, the obj passed in becomes the execution
 *                            scope of the listener
 * @constructor
 */
YAHOO.util.Subscriber = function(fn, obj, bOverride) {
    /**
     * The callback that will be execute when the event fires
     * @type function
     */
    this.fn = fn;

    /**
     * An optional custom object that will passed to the callback when
     * the event fires
     * @type object
     */
    this.obj = obj || null;

    /**
     * The default execution scope for the event listener is defined when the
     * event is created (usually the object which contains the event).
     * By setting override to true, the execution scope becomes the custom
     * object passed in by the subscriber
     * @type boolean
     */
    this.override = (bOverride);
};

/**
 * Returns true if the fn and obj match this objects properties.
 * Used by the unsubscribe method to match the right subscriber.
 *
 * @param {Function} fn the function to execute
 * @param {Object} obj an object to be passed along when the event fires
 * @return {boolean} true if the supplied arguments match this
 *                   subscriber's signature.
 */
YAHOO.util.Subscriber.prototype.contains = function(fn, obj) {
    return (this.fn == fn && this.obj == obj);
};

/* Copyright (c) 2006 Yahoo! Inc. All rights reserved. */

// Only load this library once.  If it is loaded a second time, existing
// events cannot be detached.
if (!YAHOO.util.Event) {

/**
 * The event utility provides functions to add and remove event listeners,
 * event cleansing.  It also tries to automatically remove listeners it
 * registers during the unload event.
 * @class
 * @constructor
 */
    YAHOO.util.Event = function() {

        /**
         * True after the onload event has fired
         * @type boolean
         * @private
         */
        var loadComplete =  false;

        /**
         * Cache of wrapped listeners
         * @type array
         * @private
         */
        var listeners = [];

        /**
         * Listeners that will be attached during the onload event
         * @type array
         * @private
         */
        var delayedListeners = [];

        /**
         * User-defined unload function that will be fired before all events
         * are detached
         * @type array
         * @private
         */
        var unloadListeners = [];

        /**
         * Cache of the custom events that have been defined.  Used for
         * automatic cleanup
         * @type array
         * @private
         */
        var customEvents = [];

        /**
         * Cache of DOM0 event handlers to work around issues with DOM2 events
         * in Safari
         * @private
         */
        var legacyEvents = [];

        /**
         * Listener stack for DOM0 events
         * @private
         */
        var legacyHandlers = [];

        return { // PREPROCESS

            /**
             * Element to bind, int constant
             * @type int
             */
            EL: 0,

            /**
             * Type of event, int constant
             * @type int
             */
            TYPE: 1,

            /**
             * Function to execute, int constant
             * @type int
             */
            FN: 2,

            /**
             * Function wrapped for scope correction and cleanup, int constant
             * @type int
             */
            WFN: 3,

            /**
             * Object passed in by the user that will be returned as a
             * parameter to the callback, int constant
             * @type int
             */
            SCOPE: 3,

            /**
             * Adjusted scope, either the element we are registering the event
             * on or the custom object passed in by the listener, int constant
             * @type int
             */
            ADJ_SCOPE: 4,

            /**
             * Safari detection is necessary to work around the preventDefault
             * bug that makes it so you can't cancel a href click from the
             * handler.  There is not a capabilities check we can use here.
             * @private
             */
            isSafari: (navigator.userAgent.match(/safari/gi)),

            /**
             * @private
             * IE detection needed to properly calculate pageX and pageY.
             * capabilities checking didn't seem to work because another
             * browser that does not provide the properties have the values
             * calculated in a different manner than IE.
             */
            isIE: (!this.isSafari && navigator.userAgent.match(/msie/gi)),

            /**
             * Appends an event handler
             *
             * @param {Object}   el        The html element to assign the
             *                             event to
             * @param {String}   sType     The type of event to append
             * @param {Function} fn        The method the event invokes
             * @param {Object}   oScope    An arbitrary object that will be
             *                             passed as a parameter to the handler
             * @param {boolean}  bOverride If true, the obj passed in becomes
             *                             the execution scope of the listener
             * @return {boolean} True if the action was successful or defered,
             *                        false if one or more of the elements
             *                        could not have the event bound to it.
             */
            addListener: function(el, sType, fn, oScope, bOverride) {

                // The el argument can be an array of elements or element ids.
                if ( this._isValidCollection(el)) {
                    var ok = true;
                    for (var i=0; i< el.length; ++i) {
                        ok = ( this.on(el[i],
                                       sType,
                                       fn,
                                       oScope,
                                       bOverride) && ok );
                    }
                    return ok;

                } else if (typeof el == "string") {
                    // If the el argument is a string, we assume it is
                    // actually the id of the element.  If the page is loaded
                    // we convert el to the actual element, otherwise we
                    // defer attaching the event until onload event fires

                    // check to see if we need to delay hooking up the event
                    // until after the page loads.
                    if (loadComplete) {
                        el = this.getEl(el);
                    } else {
                        // defer adding the event until onload fires
                        delayedListeners[delayedListeners.length] =
                            [el, sType, fn, oScope, bOverride];

                        return true;
                    }
                }

                // Element should be an html element or an array if we get
                // here.
                if (!el) {
                    return false;
                }

                // we need to make sure we fire registered unload events
                // prior to automatically unhooking them.  So we hang on to
                // these instead of attaching them to the window and fire the
                // handles explicitly during our one unload event.
                if ("unload" == sType && oScope !== this) {
                    unloadListeners[unloadListeners.length] =
                            [el, sType, fn, oScope, bOverride];
                    return true;
                }


                // if the user chooses to override the scope, we use the custom
                // object passed in, otherwise the executing scope will be the
                // HTML element that the event is registered on
                var scope = (bOverride) ? oScope : el;

                // wrap the function so we can return the oScope object when
                // the event fires;
                var wrappedFn = function(e) {
                        return fn.call(scope, YAHOO.util.Event.getEvent(e),
                                oScope);
                    };

                var li = [el, sType, fn, wrappedFn, scope];
                var index = listeners.length;
                // cache the listener so we can try to automatically unload
                listeners[index] = li;

                if (this.useLegacyEvent(el, sType)) {
                    var legacyIndex = this.getLegacyIndex(el, sType);
                    if (legacyIndex == -1) {

                        legacyIndex = legacyEvents.length;
                        // cache the signature for the DOM0 event, and
                        // include the existing handler for the event, if any
                        legacyEvents[legacyIndex] =
                            [el, sType, el["on" + sType]];
                        legacyHandlers[legacyIndex] = [];

                        el["on" + sType] =
                            function(e) {
                                YAHOO.util.Event.fireLegacyEvent(
                                    YAHOO.util.Event.getEvent(e), legacyIndex);
                            };
                    }

                    // add a reference to the wrapped listener to our custom
                    // stack of events
                    legacyHandlers[legacyIndex].push(index);

                // DOM2 Event model
                } else if (el.addEventListener) {
                    el.addEventListener(sType, wrappedFn, false);
                // Internet Explorer abstraction
                } else if (el.attachEvent) {
                    el.attachEvent("on" + sType, wrappedFn);
                }

                return true;

            },

            /**
             * Shorthand for YAHOO.util.Event.addListener
             * @type function
             */
            // on: this.addListener,

            /**
             * When using legacy events, the handler is routed to this object
             * so we can fire our custom listener stack.
             * @private
             */
            fireLegacyEvent: function(e, legacyIndex) {
                // alert("fireLegacyEvent " + legacyIndex);
                var ok = true;

                // var el = legacyEvents[YAHOO.util.Event.EL];

                /* this is not working because the property may get populated
                // fire the event we replaced, if it exists
                var origHandler = legacyEvents[2];
                alert(origHandler);
                if (origHandler && origHandler.call) {
                    var ret = origHandler.call(el, e);
                    ok = (ret);
                }
                */

                var le = legacyHandlers[legacyIndex];
                for (i=0; i < le.length; ++i) {
                    var index = le[i];
                    // alert(index);
                    if (index) {
                        var li = listeners[index];
                        var scope = li[this.ADJ_SCOPE];
                        var ret = li[this.WFN].call(scope, e);
                        ok = (ok && ret);
                        // alert(ok);
                    }
                }

                return ok;
            },

            /**
             * Returns the legacy event index that matches the supplied
             * signature
             * @private
             */
            getLegacyIndex: function(el, sType) {
                for (var i=0; i < legacyEvents.length; ++i) {
                    var le = legacyEvents[i];
                    if (le && le[0] == el && le[1] == sType) {
                        return i;
                    }
                }

                return -1;
            },

            /**
             * Logic that determines when we should automatically use legacy
             * events instead of DOM2 events.
             * @private
             */
            useLegacyEvent: function(el, sType) {

                return ( (!el.addEventListener && !el.attachEvent) ||
                                (sType == "click" && this.isSafari) );
            },

            /**
             * Removes an event handler
             *
             * @param {Object} el the html element or the id of the element to
             * assign the event to.
             * @param {String} sType the type of event to remove
             * @param {Function} fn the method the event invokes
             * @return {boolean} true if the unbind was successful, false
             * otherwise
             */
            removeListener: function(el, sType, fn) {

                // The el argument can be a string
                if (typeof el == "string") {
                    el = this.getEl(el);
                // The el argument can be an array of elements or element ids.
                } else if ( this._isValidCollection(el)) {
                    var ok = true;
                    for (var i=0; i< el.length; ++i) {
                        ok = ( this.removeListener(el[i], sType, fn) && ok );
                    }
                    return ok;
                }

                var cacheItem = null;
                var index = this._getCacheIndex(el, sType, fn);

                if (index >= 0) {
                    cacheItem = listeners[index];
                }

                if (!el || !cacheItem) {
                    return false;
                }


                if (el.removeEventListener) {
                    el.removeEventListener(sType, cacheItem[this.WFN], false);
                    // alert("adsf");
                } else if (el.detachEvent) {
                    el.detachEvent("on" + sType, cacheItem[this.WFN]);
                }

                // removed the wrapped handler
                delete listeners[index][this.WFN];
                delete listeners[index][this.FN];
                delete listeners[index];

                return true;

            },

            /**
             * Returns the event's target element
             * @param {Event} ev the event
             * @param {boolean} resolveTextNode when set to true the target's
             *                  parent will be returned if the target is a
             *                  text node
             * @return {HTMLElement} the event's target
             */
            getTarget: function(ev, resolveTextNode) {
                var t = ev.target || ev.srcElement;

                if (resolveTextNode && t && "#text" == t.nodeName) {
                    return t.parentNode;
                } else {
                    return t;
                }
            },

            /**
             * Returns the event's pageX
             * @param {Event} ev the event
             * @return {int} the event's pageX
             */
            getPageX: function(ev) {
                var x = ev.pageX;
                if (!x && 0 !== x) {
                    x = ev.clientX || 0;

                    if ( this.isIE ) {
                        x += this._getScrollLeft();
                    }
                }

                return x;
            },

            /**
             * Returns the event's pageY
             * @param {Event} ev the event
             * @return {int} the event's pageY
             */
            getPageY: function(ev) {
                var y = ev.pageY;
                if (!y && 0 !== y) {
                    y = ev.clientY || 0;

                    if ( this.isIE ) {
                        y += this._getScrollTop();
                    }
                }

                return y;
            },

            /**
             * Returns the event's related target
             * @param {Event} ev the event
             * @return {HTMLElement} the event's relatedTarget
             */
            getRelatedTarget: function(ev) {
                var t = ev.relatedTarget;
                if (!t) {
                    if (ev.type == "mouseout") {
                        t = ev.toElement;
                    } else if (ev.type == "mouseover") {
                        t = ev.fromElement;
                    }
                }

                return t;
            },

            /**
             * Returns the time of the event.  If the time is not included, the
             * event is modified using the current time.
             * @param {Event} ev the event
             * @return {Date} the time of the event
             */
            getTime: function(ev) {
                if (!ev.time) {
                    var t = new Date().getTime();
                    try {
                        ev.time = t;
                    } catch(e) {
                        // can't set the time property
                        return t;
                    }
                }

                return ev.time;
            },

            /**
             * Convenience method for stopPropagation + preventDefault
             * @param {Event} ev the event
             */
            stopEvent: function(ev) {
                this.stopPropagation(ev);
                this.preventDefault(ev);
            },

            /**
             * Stops event propagation
             * @param {Event} ev the event
             */
            stopPropagation: function(ev) {
                if (ev.stopPropagation) {
                    ev.stopPropagation();
                } else {
                    ev.cancelBubble = true;
                }
            },

            /**
             * Prevents the default behavior of the event
             * @param {Event} ev the event
             */
            preventDefault: function(ev) {
                if (ev.preventDefault) {
                    ev.preventDefault();
                } else {
                    ev.returnValue = false;
                }
            },

            /**
             * Returns the event, should not be necessary for user to call
             * @param {Event} the event parameter from the handler
             * @return {Event} the event
             */
            getEvent: function(e) {
                var ev = e || window.event;

                if (!ev) {
                    var c = this.getEvent.caller;
                    while (c) {
                        ev = c.arguments[0];
                        if (ev && Event == ev.constructor) {
                            break;
                        }
                        c = c.caller;
                    }
                }

                return ev;
            },

            /**
             * Returns the charcode for an event
             * @param {Event} ev the event
             * @return {int} the event's charCode
             */
            getCharCode: function(ev) {
                return ev.charCode || (ev.type == "keypress") ? ev.keyCode : 0;
            },

            /**
             * @private
             * Locating the saved event handler data by function ref
             */
            _getCacheIndex: function(el, sType, fn) {
                for (var i=0; i< listeners.length; ++i) {
                    var li = listeners[i];
                    if ( li                 &&
                         li[this.FN] == fn  &&
                         li[this.EL] == el  &&
                         li[this.TYPE] == sType ) {
                        return i;
                    }
                }

                return -1;
            },

            /**
             * We want to be able to use getElementsByTagName as a collection
             * to attach a group of events to.  Unfortunately, different
             * browsers return different types of collections.  This function
             * tests to determine if the object is array-like.  It will also
             * fail if the object is an array, but is empty.
             * @param o the object to test
             * @return {boolean} true if the object is array-like and populated
             */
            _isValidCollection: function(o) {
                // alert(o.constructor.toString())
                // alert(typeof o)

                return ( o                    && // o is something
                         o.length             && // o is indexed
                         typeof o != "string" && // o is not a string
                         !o.tagName           && // o is not an HTML element
                         !o.alert             && // o is not a window
                         typeof o[0] != "undefined" );

            },

            /**
             * @private
             * DOM element cache
             */
            elCache: {},

            /**
             * We cache elements bound by id because when the unload event
             * fires, we can no longer use document.getElementById
             * @private
             */
            getEl: function(id) {
                /*
                // this is a problem when replaced via document.getElementById
                if (! this.elCache[id]) {
                    try {
                        var el = document.getElementById(id);
                        if (el) {
                            this.elCache[id] = el;
                        }
                    } catch (er) {
                    }
                }
                return this.elCache[id];
                */

                return document.getElementById(id);
            },

            /**
             * Clears the element cache
             */
            clearCache: function() {
                for (i in this.elCache) {
                    delete this.elCache[i];
                }
            },

            /**
             * Called by CustomEvent instances to provide a handle to the
             * event * that can be removed later on.  Should be package
             * protected.
             * @private
             */
            regCE: function(ce) {
                customEvents.push(ce);
            },

            /**
             * @private
             * hook up any deferred listeners
             */
            _load: function(e) {
                loadComplete = true;
            },

            /**
             * Polling function that runs before the onload event fires,
             * attempting * to attach to DOM Nodes as soon as they are
             * available
             * @private
             */
            _tryPreloadAttach: function() {

                // keep trying until after the page is loaded.  We need to
                // check the page load state prior to trying to bind the
                // elements so that we can be certain all elements have been
                // tested appropriately
                var tryAgain = !loadComplete;

                for (var i=0; i < delayedListeners.length; ++i) {
                    var d = delayedListeners[i];
                    // There may be a race condition here, so we need to
                    // verify the array element is usable.
                    if (d) {

                        // el will be null if document.getElementById did not
                        // work
                        var el = this.getEl(d[this.EL]);

                        if (el) {
                            this.on(el, d[this.TYPE], d[this.FN],
                                    d[this.SCOPE], d[this.ADJ_SCOPE]);
                            delete delayedListeners[i];
                        }
                    }
                }

                if (tryAgain) {
                    setTimeout("YAHOO.util.Event._tryPreloadAttach()", 50);
                }
            },

            /**
             * Removes all listeners registered by pe.event.  Called
             * automatically during the unload event.
             */
            _unload: function(e, me) {
                for (var i=0; i < unloadListeners.length; ++i) {
                    var l = unloadListeners[i];
                    if (l) {
                        var scope = (l[this.ADJ_SCOPE]) ? l[this.SCOPE]: window;
                        l[this.FN].call(scope, this.getEvent(e), l[this.SCOPE] );
                    }
                }

                if (listeners && listeners.length > 0) {
                    for (i = 0; i < listeners.length; ++i) {
                        l = listeners[i];
                        if (l) {
                            this.removeListener(l[this.EL], l[this.TYPE],
                                    l[this.FN]);
                        }
                    }

                    this.clearCache();
                }

                for (i = 0; i < customEvents.length; ++i) {
                    customEvents[i].unsubscribeAll();
                    delete customEvents[i];
                }

                for (i = 0; i < legacyEvents.length; ++i) {
                    // dereference the element
                    delete legacyEvents[i][0];
                    // delete the array item
                    delete legacyEvents[i];
                }
            },

            /**
             * Returns scrollLeft
             * @private
             */
            _getScrollLeft: function() {
                return this._getScroll()[1];
            },

            /**
             * Returns scrollTop
             * @private
             */
            _getScrollTop: function() {
                return this._getScroll()[0];
            },

            /**
             * Returns the scrollTop and scrollLeft.  Used to calculate the
             * pageX and pageY in Internet Explorer
             * @private
             */
            _getScroll: function() {
                var dd = document.documentElement; db = document.body;
                if (dd && dd.scrollTop) {
                    return [dd.scrollTop, dd.scrollLeft];
                } else if (db) {
                    return [db.scrollTop, db.scrollLeft];
                } else {
                    return [0, 0];
                }
            }
        };
    } ();

    YAHOO.util.Event.on = YAHOO.util.Event.addListener;

    if (document && document.body) {
        YAHOO.util.Event._load();
    } else {
        YAHOO.util.Event.on(window, "load", YAHOO.util.Event._load,
                YAHOO.util.Event, true);
    }

    YAHOO.util.Event.on(window, "unload", YAHOO.util.Event._unload,
                YAHOO.util.Event, true);

    YAHOO.util.Event._tryPreloadAttach();

}

/*************************************************************************************************
/****************************************calendar.js**********************************************
/*************************************************************************************************

/*
Copyright (c) 2006 Yahoo! Inc. All rights reserved.
version 0.9.0
*/

YAHOO.namespace("YAHOO.widget");

/**
* @class
* <p>YAHOO.widget.DateMath is used for simple date manipulation. The class is a static utility
* used for adding, subtracting, and comparing dates.</p>
*/
YAHOO.widget.DateMath = new function() {

	/**
	* Constant field representing Day
	* @type String
	*/
	this.DAY = "D";

	/**
	* Constant field representing Week
	* @type String
	*/
	this.WEEK = "W";

	/**
	* Constant field representing Year
	* @type String
	*/
	this.YEAR = "Y";

	/**
	* Constant field representing Month
	* @type String
	*/
	this.MONTH = "M";

	/**
	* Constant field representing one day, in milliseconds
	* @type Integer
	*/
	this.ONE_DAY_MS = 1000*60*60*24;

	/**
	* Adds the specified amount of time to the this instance.
	* @param {Date} date	The JavaScript Date object to perform addition on
	* @param {string} field	The this field constant to be used for performing addition.
	* @param {Integer} amount	The number of units (measured in the field constant) to add to the date.
	*/
	this.add = function(date, field, amount) {
		var d = new Date(date.getTime());
		switch (field)
		{
			case this.MONTH:
				var newMonth = date.getMonth() + amount;
				var years = 0;


				if (newMonth < 0) {
					while (newMonth < 0)
					{
						newMonth += 12;
						years -= 1;
					}
				} else if (newMonth > 11) {
					while (newMonth > 11)
					{
						newMonth -= 12;
						years += 1;
					}
				}

				d.setMonth(newMonth);
				d.setFullYear(date.getFullYear() + years);
				break;
			case this.DAY:
				d.setDate(date.getDate() + amount);
				break;
			case this.YEAR:
				d.setFullYear(date.getFullYear() + amount);
				break;
			case this.WEEK:
				d.setDate(date.getDate() + 7);
				break;
		}
		return d;
	};

	/**
	* Subtracts the specified amount of time from the this instance.
	* @param {Date} date	The JavaScript Date object to perform subtraction on
	* @param {Integer} field	The this field constant to be used for performing subtraction.
	* @param {Integer} amount	The number of units (measured in the field constant) to subtract from the date.
	*/
	this.subtract = function(date, field, amount) {
		return this.add(date, field, (amount*-1));
	};

	/**
	* Determines whether a given date is before another date on the calendar.
	* @param {Date} date		The Date object to compare with the compare argument
	* @param {Date} compareTo	The Date object to use for the comparison
	* @return {Boolean} true if the date occurs before the compared date; false if not.
	*/
	this.before = function(date, compareTo) {
		var ms = compareTo.getTime();
		if (date.getTime() < ms) {
			return true;
		} else {
			return false;
		}
	};

	/**
	* Determines whether a given date is after another date on the calendar.
	* @param {Date} date		The Date object to compare with the compare argument
	* @param {Date} compareTo	The Date object to use for the comparison
	* @return {Boolean} true if the date occurs after the compared date; false if not.
	*/
	this.after = function(date, compareTo) {
		var ms = compareTo.getTime();
		if (date.getTime() > ms) {
			return true;
		} else {
			return false;
		}
	};

	/**
	* Retrieves a JavaScript Date object representing January 1 of any given year.
	* @param {Integer} calendarYear		The calendar year for which to retrieve January 1
	* @return {Date}	January 1 of the calendar year specified.
	*/
	this.getJan1 = function(calendarYear) {
		return new Date(calendarYear,0,1);
	};

	/**
	* Calculates the number of days the specified date is from January 1 of the specified calendar year.
	* Passing January 1 to this function would return an offset value of zero.
	* @param {Date}	date	The JavaScript date for which to find the offset
	* @param {Integer} calendarYear	The calendar year to use for determining the offset
	* @return {Integer}	The number of days since January 1 of the given year
	*/
	this.getDayOffset = function(date, calendarYear) {
		var beginYear = this.getJan1(calendarYear); // Find the start of the year. This will be in week 1.

		// Find the number of days the passed in date is away from the calendar year start
		var dayOffset = Math.ceil((date.getTime()-beginYear.getTime()) / this.ONE_DAY_MS);
		return dayOffset;
	};

	/**
	* Calculates the week number for the given date. This function assumes that week 1 is the
	* week in which January 1 appears, regardless of whether the week consists of a full 7 days.
	* The calendar year can be specified to help find what a the week number would be for a given
	* date if the date overlaps years. For instance, a week may be considered week 1 of 2005, or
	* week 53 of 2004. Specifying the optional calendarYear allows one to make this distinction
	* easily.
	* @param {Date}	date	The JavaScript date for which to find the week number
	* @param {Integer} calendarYear	OPTIONAL - The calendar year to use for determining the week number. Default is
	*											the calendar year of parameter "date".
	* @param {Integer} weekStartsOn	OPTIONAL - The integer (0-6) representing which day a week begins on. Default is 0 (for Sunday).
	* @return {Integer}	The week number of the given date.
	*/
	this.getWeekNumber = function(date, calendarYear, weekStartsOn) {
		if (! weekStartsOn) {
			weekStartsOn = 0;
		}
		if (! calendarYear) {
			calendarYear = date.getFullYear();
		}
		var weekNum = -1;

		var jan1 = this.getJan1(calendarYear);
		var jan1DayOfWeek = jan1.getDay();

		var month = date.getMonth();
		var day = date.getDate();
		var year = date.getFullYear();

		var dayOffset = this.getDayOffset(date, calendarYear); // Days since Jan 1, Calendar Year

		if (dayOffset < 0 && dayOffset >= (-1 * jan1DayOfWeek)) {
			weekNum = 1;
		} else {
			weekNum = 1;
			var testDate = this.getJan1(calendarYear);

			while (testDate.getTime() < date.getTime() && testDate.getFullYear() == calendarYear) {
				weekNum += 1;
				testDate = this.add(testDate, this.WEEK, 1);
			}
		}

		return weekNum;
	};

	/**
	* Determines if a given week overlaps two different years.
	* @param {Date}	weekBeginDate	The JavaScript Date representing the first day of the week.
	* @return {Boolean}	true if the date overlaps two different years.
	*/
	this.isYearOverlapWeek = function(weekBeginDate) {
		var overlaps = false;
		var nextWeek = this.add(weekBeginDate, this.DAY, 6);
		if (nextWeek.getFullYear() != weekBeginDate.getFullYear()) {
			overlaps = true;
		}
		return overlaps;
	};

	/**
	* Determines if a given week overlaps two different months.
	* @param {Date}	weekBeginDate	The JavaScript Date representing the first day of the week.
	* @return {Boolean}	true if the date overlaps two different months.
	*/
	this.isMonthOverlapWeek = function(weekBeginDate) {
		var overlaps = false;
		var nextWeek = this.add(weekBeginDate, this.DAY, 6);
		if (nextWeek.getMonth() != weekBeginDate.getMonth()) {
			overlaps = true;
		}
		return overlaps;
	};

	/**
	* Gets the first day of a month containing a given date.
	* @param {Date}	date	The JavaScript Date used to calculate the month start
	* @return {Date}		The JavaScript Date representing the first day of the month
	*/
	this.findMonthStart = function(date) {
		var start = new Date(date.getFullYear(), date.getMonth(), 1);
		return start;
	};

	/**
	* Gets the last day of a month containing a given date.
	* @param {Date}	date	The JavaScript Date used to calculate the month end
	* @return {Date}		The JavaScript Date representing the last day of the month
	*/
	this.findMonthEnd = function(date) {
		var start = this.findMonthStart(date);
		var nextMonth = this.add(start, this.MONTH, 1);
		var end = this.subtract(nextMonth, this.DAY, 1);
		return end;
	};

	/**
	* Clears the time fields from a given date, effectively setting the time to midnight.
	* @param {Date}	date	The JavaScript Date for which the time fields will be cleared
	* @return {Date}		The JavaScript Date cleared of all time fields
	*/
	this.clearTime = function(date) {
		date.setHours(0,0,0,0);
		return date;
	};
}

YAHOO.namespace("YAHOO.widget");

/**
* @class
* <p>Calendar_Core is the base class for the Calendar widget. In its most basic
* implementation, it has the ability to render a calendar widget on the page
* that can be manipulated to select a single date, move back and forth between
* months and years.</p>
* <p>To construct the placeholder for the calendar widget, the code is as
* follows:
*	<xmp>
*		<div id="cal1Container"></div>
*	</xmp>
* Note that the table can be replaced with any kind of element.
* </p>
* @constructor
* @param {String}	id			The id of the table element that will represent the calendar widget
* @param {String}	containerId	The id of the container element that will contain the calendar table
* @param {String}	monthyear	The month/year string used to set the current calendar page
* @param {String}	selected	A string of date values formatted using the date parser. The built-in
								default date format is MM/DD/YYYY. Ranges are defined using
								MM/DD/YYYY-MM/DD/YYYY. Month/day combinations are defined using MM/DD.
								Any combination of these can be combined by delimiting the string with
								commas. Example: "12/24/2005,12/25,1/18/2006-1/21/2006"
*/
YAHOO.widget.Calendar_Core = function(id, containerId, monthyear, selected) {
	if (arguments.length > 0)
	{
		this.init(id, containerId, monthyear, selected);
	}
}

/**
* Type constant used for renderers to represent an individual date (M/D/Y)
* @final
* @type String
*/
YAHOO.widget.Calendar_Core.DATE = "D";

/**
* Type constant used for renderers to represent an individual date across any year (M/D)
* @final
* @type String
*/
YAHOO.widget.Calendar_Core.MONTH_DAY = "MD";

/**
* Type constant used for renderers to represent a weekday
* @final
* @type String
*/
YAHOO.widget.Calendar_Core.WEEKDAY = "WD";

/**
* Type constant used for renderers to represent a range of individual dates (M/D/Y-M/D/Y)
* @final
* @type String
*/
YAHOO.widget.Calendar_Core.RANGE = "R";

/**
* Type constant used for renderers to represent a month across any year
* @final
* @type String
*/
YAHOO.widget.Calendar_Core.MONTH = "M";

/**
* Constant that represents the total number of date cells that are displayed in a given month
* including
* @final
* @type Integer
*/
YAHOO.widget.Calendar_Core.DISPLAY_DAYS = 42;

/**
* Constant used for halting the execution of the remainder of the render stack
* @final
* @type String
*/
YAHOO.widget.Calendar_Core.STOP_RENDER = "S";

YAHOO.widget.Calendar_Core.prototype = {

	/**
	* The configuration object used to set up the calendars various locale and style options.
	* @type Object
	*/
	Config : null,

	/**
	* The parent CalendarGroup, only to be set explicitly by the parent group
	* @type CalendarGroup
	*/
	parent : null,

	/**
	* The index of this item in the parent group
	* @type Integer
	*/
	index : -1,

	/**
	* The collection of calendar table cells
	* @type HTMLTableCellElement[]
	*/
	cells : null,

	/**
	* The collection of calendar week header cells
	* @type HTMLTableCellElement[]
	*/
	weekHeaderCells : null,

	/**
	* The collection of calendar week footer cells
	* @type HTMLTableCellElement[]
	*/
	weekFooterCells : null,

	/**
	* The collection of calendar cell dates that is parallel to the cells collection. The array contains dates field arrays in the format of [YYYY, M, D].
	* @type Array[](Integer[])
	*/
	cellDates : null,

	/**
	* The id that uniquely identifies this calendar. This id should match the id of the placeholder element on the page.
	* @type String
	*/
	id : null,

	/**
	* The DOM element reference that points to this calendar's container element. The calendar will be inserted into this element when the shell is rendered.
	* @type HTMLElement
	*/
	oDomContainer : null,

	/**
	* A Date object representing today's date.
	* @type Date
	*/
	today : null,

	/**
	* The list of render functions, along with required parameters, used to render cells.
	* @type Array[]
	*/
	renderStack : null,

	/**
	* A copy of the initial render functions created before rendering.
	* @type Array
	* @private
	*/
	_renderStack : null,

	/**
	* A Date object representing the month/year that the calendar is currently set to
	* @type Date
	*/
	pageDate : null,

	/**
	* A Date object representing the month/year that the calendar is initially set to
	* @type Date
	* @private
	*/
	_pageDate : null,

	/**
	* A Date object representing the minimum selectable date
	* @type Date
	*/
	minDate : null,

	/**
	* A Date object representing the maximum selectable date
	* @type Date
	*/
	maxDate : null,

	/**
	* The list of currently selected dates. The data format for this local collection is
	* an array of date field arrays, e.g:
	* [
	*	[2004,5,25],
	*	[2004,5,26]
	* ]
	* @type Array[](Integer[])
	*/
	selectedDates : null,

	/**
	* The private list of initially selected dates.
	* @type Array
	* @private
	*/
	_selectedDates : null,

	/**
	* A boolean indicating whether the shell of the calendar has already been rendered to the page
	* @type Boolean
	*/
	shellRendered : false,

	/**
	* The HTML table element that represents this calendar
	* @type HTMLTableElement
	*/
	table : null,

	/**
	* The HTML cell element that represents the main header cell TH used in the calendar table
	* @type HTMLTableCellElement
	*/
	headerCell : null


};



/**
* Initializes the calendar widget. This method must be called by all subclass constructors.
* @param {String}	id			The id of the table element that will represent the calendar widget
* @param {String}	containerId	The id of the container element that will contain the calendar table
* @param {String}	monthyear	The month/year string used to set the current calendar page
* @param {String}	selected	A string of date values formatted using the date parser. The built-in
								default date format is MM/DD/YYYY. Ranges are defined using
								MM/DD/YYYY-MM/DD/YYYY. Month/day combinations are defined using MM/DD.
								Any combination of these can be combined by delimiting the string with
								commas. Example: "12/24/2005,12/25,1/18/2006-1/21/2006"
*/
YAHOO.widget.Calendar_Core.prototype.init = function(id, containerId, monthyear, selected) {
	this.setupConfig();

	this.id = id;

	this.cellDates = new Array();

	this.cells = new Array();

	this.renderStack = new Array();
	this._renderStack = new Array();

	this.oDomContainer = document.getElementById(containerId);

	this.today = new Date();
	YAHOO.widget.DateMath.clearTime(this.today);

	var month;
	var year;

	if (monthyear)
	{
		var aMonthYear = monthyear.split(this.Locale.DATE_FIELD_DELIMITER);
		month = aMonthYear[0];
		year = aMonthYear[1];
		//month = parseInt(aMonthYear[this.Locale.MY_MONTH_POSITION-1]);
		//year = parseInt(aMonthYear[this.Locale.MY_YEAR_POSITION-1]);
	} else {
		month = this.today.getMonth()+1;
		year = this.today.getFullYear();
	}

	this.pageDate = new Date(year, month-1, 1);
	this._pageDate = new Date(this.pageDate.getTime());

	if (selected)
	{
		this.selectedDates = this._parseDates(selected);
		this._selectedDates = this.selectedDates.concat();
	} else {
		this.selectedDates = new Array();
		this._selectedDates = new Array();
	}

	this.wireDefaultEvents();
	this.wireCustomEvents();
};


/**
* Wires the local DOM events for the Calendar, including cell selection, hover, and
* default navigation that is used for moving back and forth between calendar pages.
*/
YAHOO.widget.Calendar_Core.prototype.wireDefaultEvents = function() {

	/**
	* The default event function that is attached to a date link within a calendar cell
	* when the calendar is rendered.
	* @param	e		The event
	* @param	cal		A reference to the calendar passed by the Event utility
	*/
	this.doSelectCell = function(e, cal) {
		var cell = this;
		var index = cell.index;

		if (cal.Options.MULTI_SELECT) {
			var link = cell.getElementsByTagName("A")[0];
			link.blur();

			var cellDate = cal.cellDates[index];
			var cellDateIndex = cal._indexOfSelectedFieldArray(cellDate);

			if (cellDateIndex > -1)
			{
				cal.deselectCell(index);
			} else {
				cal.selectCell(index);
			}

		} else {
			var link = cell.getElementsByTagName("A")[0];
			link.blur();
			cal.selectCell(index);
		}
	}

	/**
	* The event that is executed when the user hovers over a cell
	* @param	e		The event
	* @param	cal		A reference to the calendar passed by the Event utility
	* @private
	*/
	this.doCellMouseOver = function(e, cal) {
		YAHOO.widget.Calendar_Core.prependCssClass(this, cal.Style.CSS_CELL_HOVER);
	}

	/**
	* The event that is executed when the user moves the mouse out of a cell
	* @param	e		The event
	* @param	cal		A reference to the calendar passed by the Event utility
	* @private
	*/
	this.doCellMouseOut = function(e, cal) {
		YAHOO.widget.Calendar_Core.removeCssClass(this, cal.Style.CSS_CELL_HOVER);
	}

	/**
	* A wrapper event that executes the nextMonth method through a DOM event
	* @param	e		The event
	* @param	cal		A reference to the calendar passed by the Event utility
	* @private
	*/
	this.doNextMonth = function(e, cal) {
		cal.nextMonth();
	}

	/**
	* A wrapper event that executes the previousMonth method through a DOM event
	* @param	e		The event
	* @param	cal		A reference to the calendar passed by the Event utility
	* @private
	*/
	this.doPreviousMonth = function(e, cal) {
		cal.previousMonth();
	}
}

/**
* This function can be extended by subclasses to attach additional DOM events to
* the calendar. By default, this method is unimplemented.
*/
YAHOO.widget.Calendar_Core.prototype.wireCustomEvents = function() { }

/**
This method is called to initialize the widget configuration variables, including
style, localization, and other display and behavioral options.
<p>Config: Container for the CSS style configuration variables.</p>
<p><strong>Config.Style</strong> - Defines the CSS classes used for different calendar elements</p>
<blockquote>
	<div><em>CSS_CALENDAR</em> : Container table</div>
	<div><em>CSS_HEADER</em> : </div>
	<div><em>CSS_HEADER_TEXT</em> : Calendar header</div>
	<div><em>CSS_FOOTER</em> : Calendar footer</div>
	<div><em>CSS_CELL</em> : Calendar day cell</div>
	<div><em>CSS_CELL_OOM</em> : Calendar OOM (out of month) cell</div>
	<div><em>CSS_CELL_SELECTED</em> : Calendar selected cell</div>
	<div><em>CSS_CELL_RESTRICTED</em> : Calendar restricted cell</div>
	<div><em>CSS_CELL_TODAY</em> : Calendar cell for today's date</div>
	<div><em>CSS_ROW_HEADER</em> : The cell preceding a row (used for week number by default)</div>
	<div><em>CSS_ROW_FOOTER</em> : The cell following a row (not implemented by default)</div>
	<div><em>CSS_WEEKDAY_CELL</em> : The cells used for labeling weekdays</div>
	<div><em>CSS_WEEKDAY_ROW</em> : The row containing the weekday label cells</div>
	<div><em>CSS_BORDER</em> : The border style used for the default UED rendering</div>
	<div><em>CSS_CONTAINER</em> : Special container class used to properly adjust the sizing and float</div>
	<div><em>CSS_NAV_LEFT</em> : Left navigation arrow</div>
	<div><em>CSS_NAV_RIGHT</em> : Right navigation arrow</div>
	<div><em>CSS_CELL_TOP</em> : Outlying cell along the top row</div>
	<div><em>CSS_CELL_LEFT</em> : Outlying cell along the left row</div>
	<div><em>CSS_CELL_RIGHT</em> : Outlying cell along the right row</div>
	<div><em>CSS_CELL_BOTTOM</em> : Outlying cell along the bottom row</div>
	<div><em>CSS_CELL_HOVER</em> : Cell hover style</div>
	<div><em>CSS_CELL_HIGHLIGHT1</em> : Highlight color 1 for styling cells</div>
	<div><em>CSS_CELL_HIGHLIGHT2</em> : Highlight color 2 for styling cells</div>
	<div><em>CSS_CELL_HIGHLIGHT3</em> : Highlight color 3 for styling cells</div>
	<div><em>CSS_CELL_HIGHLIGHT4</em> : Highlight color 4 for styling cells</div>

</blockquote>
<p><strong>Config.Locale</strong> - Defines the locale string arrays used for localization</p>
<blockquote>
	<div><em>MONTHS_SHORT</em> : Array of 12 months in short format ("Jan", "Feb", etc.)</div>
	<div><em>MONTHS_LONG</em> : Array of 12 months in short format ("Jan", "Feb", etc.)</div>
	<div><em>WEEKDAYS_1CHAR</em> : Array of 7 days in 1-character format ("S", "M", etc.)</div>
	<div><em>WEEKDAYS_SHORT</em> : Array of 7 days in short format ("Su", "Mo", etc.)</div>
	<div><em>WEEKDAYS_MEDIUM</em> : Array of 7 days in medium format ("Sun", "Mon", etc.)</div>
	<div><em>WEEKDAYS_LONG</em> : Array of 7 days in long format ("Sunday", "Monday", etc.)</div>
	<div><em>DATE_DELIMITER</em> : The value used to delimit series of multiple dates (Default: ",")</div>
	<div><em>DATE_FIELD_DELIMITER</em> : The value used to delimit date fields (Default: "/")</div>
	<div><em>DATE_RANGE_DELIMITER</em> : The value used to delimit date fields (Default: "-")</div>
	<div><em>MY_MONTH_POSITION</em> : The value used to determine the position of the month in a month/year combo (e.g. 12/2005) (Default: 1)</div>
	<div><em>MY_YEAR_POSITION</em> : The value used to determine the position of the year in a month/year combo (e.g. 12/2005) (Default: 2)</div>
	<div><em>MD_MONTH_POSITION</em> : The value used to determine the position of the month in a month/day combo (e.g. 12/25) (Default: 1)</div>
	<div><em>MD_DAY_POSITION</em> : The value used to determine the position of the day in a month/day combo (e.g. 12/25) (Default: 2)</div>
	<div><em>MDY_MONTH_POSITION</em> : The value used to determine the position of the month in a month/day/year combo (e.g. 12/25/2005) (Default: 1)</div>
	<div><em>MDY_DAY_POSITION</em> : The value used to determine the position of the day in a month/day/year combo (e.g. 12/25/2005) (Default: 2)</div>
	<div><em>MDY_YEAR_POSITION</em> : The value used to determine the position of the year in a month/day/year combo (e.g. 12/25/2005) (Default: 3)</div>
</blockquote>
<p><strong>Config.Options</strong> - Defines other configurable calendar widget options</p>
<blockquote>
	<div><em>SHOW_WEEKDAYS</em> : Boolean, determines whether to display the weekday headers (defaults to true)</div>
	<div><em>LOCALE_MONTHS</em> : Array, points to the desired Config.Locale array (defaults to Config.Locale.MONTHS_LONG)</div>
	<div><em>LOCALE_WEEKDAYS</em> : Array, points to the desired Config.Locale array (defaults to Config.Locale.WEEKDAYS_SHORT)</div>
	<div><em>START_WEEKDAY</em> : Integer, 0-6, representing the day that a week begins on</div>
	<div><em>SHOW_WEEK_HEADER</em> : Boolean, determines whether to display row headers</div>
	<div><em>SHOW_WEEK_FOOTER</em> : Boolean, determines whether to display row footers</div>
	<div><em>HIDE_BLANK_WEEKS</em> : Boolean, determines whether to hide extra weeks that are completely OOM</div>
	<div><em>NAV_ARROW_LEFT</em> : String, the image path used for the left navigation arrow</div>
	<div><em>NAV_ARROW_RIGHT</em> : String, the image path used for the right navigation arrow</div>
</blockquote>
*/
YAHOO.widget.Calendar_Core.prototype.setupConfig = function() {
	/**
	* Container for the CSS style configuration variables.
	*/
	this.Config = new Object();

	this.Config.Style = {
		// Style variables
		CSS_ROW_HEADER: "calrowhead",
		CSS_ROW_FOOTER: "calrowfoot",
		CSS_CELL : "calcell",
		CSS_CELL_SELECTED : "selected",
		CSS_CELL_RESTRICTED : "restricted",
		CSS_CELL_TODAY : "today",
		CSS_CELL_OOM : "oom",
		CSS_HEADER : "calheader",
		CSS_HEADER_TEXT : "calhead",
		CSS_WEEKDAY_CELL : "calweekdaycell",
		CSS_WEEKDAY_ROW : "calweekdayrow",
		CSS_FOOTER : "calfoot",
		CSS_CALENDAR : "calendar",
		CSS_BORDER : "calbordered",
		CSS_CONTAINER : "calcontainer",
		CSS_NAV_LEFT : "calnavleft",
		CSS_NAV_RIGHT : "calnavright",
		CSS_CELL_TOP : "calcelltop",
		CSS_CELL_LEFT : "calcellleft",
		CSS_CELL_RIGHT : "calcellright",
		CSS_CELL_BOTTOM : "calcellbottom",
		CSS_CELL_HOVER : "calcellhover",
		CSS_CELL_HIGHLIGHT1 : "highlight1",
		CSS_CELL_HIGHLIGHT2 : "highlight2",
		CSS_CELL_HIGHLIGHT3 : "highlight3",
		CSS_CELL_HIGHLIGHT4 : "highlight4"
	};

	this.Style = this.Config.Style;

	this.Config.Locale = {
		// Locale definition
		MONTHS_SHORT : ["Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"],
		MONTHS_LONG : ["Janeiro", "Fevereiro", "Marco", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"],
		WEEKDAYS_1CHAR : ["D", "S", "T", "Q", "Q", "S", "S"],
		WEEKDAYS_SHORT : ["Do", "Se", "Te", "Qu", "Qu", "Se", "Sa"],
		WEEKDAYS_MEDIUM : ["Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sab"],
		WEEKDAYS_LONG : ["Domingo", "Segunda", "Terca", "Quarta", "Quinta", "Sexta", "Sabado"],
		DATE_DELIMITER : ",",
		DATE_FIELD_DELIMITER : "/",
		DATE_RANGE_DELIMITER : "-",
		MY_MONTH_POSITION : 1,
		MY_YEAR_POSITION : 2,
		MD_MONTH_POSITION : 1,
		MD_DAY_POSITION : 2,
		MDY_MONTH_POSITION : 2,
		MDY_DAY_POSITION : 1,
		MDY_YEAR_POSITION : 3
	};

	this.Locale = this.Config.Locale;

	this.Config.Options = {
		// Configuration variables
		MULTI_SELECT : false,
		SHOW_WEEKDAYS : true,
		START_WEEKDAY : 0,
		SHOW_WEEK_HEADER : true,
		SHOW_WEEK_FOOTER : false,
		HIDE_BLANK_WEEKS : false,
		NAV_ARROW_LEFT : "../resources/images/calendario_esq.gif",
		NAV_ARROW_RIGHT : "../resources/images/calendario_dir.gif"
	};

	this.Options = this.Config.Options;

	this.customConfig();

	if (! this.Options.LOCALE_MONTHS) {
		this.Options.LOCALE_MONTHS=this.Locale.MONTHS_LONG;
	}
	if (! this.Options.LOCALE_WEEKDAYS) {
		this.Options.LOCALE_WEEKDAYS=this.Locale.WEEKDAYS_1CHAR;
	}

	// If true, reconfigure weekday arrays to place Mondays first
	if (this.Options.START_WEEKDAY > 0)
	{
		for (var w=0;w<this.Options.START_WEEKDAY;++w) {
			this.Locale.WEEKDAYS_SHORT.push(this.Locale.WEEKDAYS_SHORT.shift());
			this.Locale.WEEKDAYS_MEDIUM.push(this.Locale.WEEKDAYS_MEDIUM.shift());
			this.Locale.WEEKDAYS_LONG.push(this.Locale.WEEKDAYS_LONG.shift());
		}
	}
};

/**
* This method is called when subclasses need to override configuration variables
* or create new ones. Values can be explicitly set as follows:
* <blockquote><code>
*	this.Config.Style.CSS_CELL = "newcalcell";
*	this.Config.Locale.MONTHS_SHORT = ["Jan", "F?v", "Mars", "Avr", "Mai", "Juin", "Juil", "Ao?t", "Sept", "Oct", "Nov", "D?c"];
* </code></blockquote>
*/
YAHOO.widget.Calendar_Core.prototype.customConfig = function() { };

/**
* Builds the date label that will be displayed in the calendar header or
* footer, depending on configuration.
* @return	The formatted calendar month label
* @type String
*/
YAHOO.widget.Calendar_Core.prototype.buildMonthLabel = function() {
	var text = this.Options.LOCALE_MONTHS[this.pageDate.getMonth()] + " " + this.pageDate.getFullYear();
	return text;
};

/**
* Builds the date digit that will be displayed in calendar cells
* @return	The formatted day label
* @type	String
*/
YAHOO.widget.Calendar_Core.prototype.buildDayLabel = function(workingDate) {
	var day = workingDate.getDate();
	return day;
};



/**
* Builds the calendar table shell that will be filled in with dates and formatting.
* This method calls buildShellHeader, buildShellBody, and buildShellFooter (in that order)
* to construct the pieces of the calendar table. The construction of the shell should
* only happen one time when the calendar is initialized.
*/
YAHOO.widget.Calendar_Core.prototype.buildShell = function() {

	this.table = document.createElement("TABLE");
	this.table.cellSpacing = 0;
	YAHOO.widget.Calendar_Core.setCssClasses(this.table, [this.Style.CSS_CALENDAR]);

	this.table.id = this.id;

	this.buildShellHeader();
	this.buildShellBody();
	this.buildShellFooter();

	YAHOO.util.Event.addListener(window, "unload", this._unload, this);
};

/**
* Builds the calendar shell header by inserting a THEAD into the local calendar table.
*/
YAHOO.widget.Calendar_Core.prototype.buildShellHeader = function() {
	var head = document.createElement("THEAD");
	var headRow = document.createElement("TR");
	var headerCell = document.createElement("TH");
	var colSpan = 7;

	if (this.Config.Options.SHOW_WEEK_HEADER) {
		this.weekHeaderCells = new Array();
		colSpan += 1;
	}
	if (this.Config.Options.SHOW_WEEK_FOOTER) {
		this.weekFooterCells = new Array();
		colSpan += 1;
	}

	headerCell.colSpan = colSpan;

	YAHOO.widget.Calendar_Core.setCssClasses(headerCell,[this.Style.CSS_HEADER_TEXT]);

	this.headerCell = headerCell;

	headRow.appendChild(headerCell);
	head.appendChild(headRow);

	// Append day labels, if needed
	if (this.Options.SHOW_WEEKDAYS)
	{
		var row = document.createElement("TR");
		var fillerCell;

		YAHOO.widget.Calendar_Core.setCssClasses(row,[this.Style.CSS_WEEKDAY_ROW]);

		if (this.Config.Options.SHOW_WEEK_HEADER) {
			fillerCell = document.createElement("TH");
			YAHOO.widget.Calendar_Core.setCssClasses(fillerCell,[this.Style.CSS_WEEKDAY_CELL]);
			row.appendChild(fillerCell);
		}

		for(var i=0;i<this.Options.LOCALE_WEEKDAYS.length;++i)
		{
			var cell = document.createElement("TH");
			YAHOO.widget.Calendar_Core.setCssClasses(cell,[this.Style.CSS_WEEKDAY_CELL]);
			cell.innerHTML=this.Options.LOCALE_WEEKDAYS[i];
			row.appendChild(cell);
		}

		if (this.Config.Options.SHOW_WEEK_FOOTER) {
			fillerCell = document.createElement("TH");
			YAHOO.widget.Calendar_Core.setCssClasses(fillerCell,[this.Style.CSS_WEEKDAY_CELL]);
			row.appendChild(fillerCell);
		}

		head.appendChild(row);
	}

	this.table.appendChild(head);
};

/**
* Builds the calendar shell body (6 weeks by 7 days)
*/
YAHOO.widget.Calendar_Core.prototype.buildShellBody = function() {
	// This should only get executed once
	this.tbody = document.createElement("TBODY");

	for (var r=0;r<6;++r)
	{
		var row = document.createElement("TR");

		for (var c=0;c<this.headerCell.colSpan;++c)
		{
			var cell;
			if (this.Config.Options.SHOW_WEEK_HEADER && c===0) { // Row header
				cell = document.createElement("TH");
				this.weekHeaderCells[this.weekHeaderCells.length] = cell;
			} else if (this.Config.Options.SHOW_WEEK_FOOTER && c==(this.headerCell.colSpan-1)){ // Row footer
				cell = document.createElement("TH");
				this.weekFooterCells[this.weekFooterCells.length] = cell;
			} else {
				cell = document.createElement("TD");
				this.cells[this.cells.length] = cell;
				YAHOO.widget.Calendar_Core.setCssClasses(cell, [this.Style.CSS_CELL]);
			}

			row.appendChild(cell);
		}
		this.tbody.appendChild(row);
	}

	this.table.appendChild(this.tbody);
};

/**
* Builds the calendar shell footer. In the default implementation, there is
* no footer.
*/
YAHOO.widget.Calendar_Core.prototype.buildShellFooter = function() { };

/**
* Outputs the calendar shell to the DOM, inserting it into the placeholder element.
*/
YAHOO.widget.Calendar_Core.prototype.renderShell = function() {
	this.oDomContainer.appendChild(this.table);
	this.shellRendered = true;
};

/**
* Renders the calendar after it has been configured. The render() method has a specific call chain that will execute
* when the method is called: renderHeader, renderBody, renderFooter.
* Refer to the documentation for those methods for information on
* individual render tasks.
*/
YAHOO.widget.Calendar_Core.prototype.render = function() {
	if (! this.shellRendered)
	{
		this.buildShell();
		this.renderShell();
	}

	this.resetRenderers();
	this.cellDates.length = 0;

	// Find starting day of the current month
	var workingDate = YAHOO.widget.DateMath.findMonthStart(this.pageDate);

	this.renderHeader();
	this.renderBody(workingDate);
	this.renderFooter();
	this.onRender();
};



/**
* Appends the header contents into the widget header.
*/
YAHOO.widget.Calendar_Core.prototype.renderHeader = function() {
	this.headerCell.innerHTML = "";

	var headerContainer = document.createElement("DIV");
	headerContainer.className = this.Style.CSS_HEADER;

	headerContainer.appendChild(document.createTextNode(this.buildMonthLabel()));

	this.headerCell.appendChild(headerContainer);
};

/**
* Appends the calendar body. The default implementation calculates the number of
* OOM (out of month) cells that need to be rendered at the start of the month, renders those,
* and renders all the day cells using the built-in cell rendering methods.
*
* While iterating through all of the cells, the calendar checks for renderers in the
* local render stack that match the date of the current cell, and then applies styles
* as necessary.
*
* @param {Date}	workingDate	The current working Date object being used to generate the calendar
*/
YAHOO.widget.Calendar_Core.prototype.renderBody = function(workingDate) {

	this.preMonthDays = workingDate.getDay();
	if (this.Options.START_WEEKDAY > 0) {
		this.preMonthDays -= this.Options.START_WEEKDAY;
	}
	if (this.preMonthDays < 0) {
		this.preMonthDays += 7;
	}

	this.monthDays = YAHOO.widget.DateMath.findMonthEnd(workingDate).getDate();
	this.postMonthDays = YAHOO.widget.Calendar_Core.DISPLAY_DAYS-this.preMonthDays-this.monthDays;

	workingDate = YAHOO.widget.DateMath.subtract(workingDate, YAHOO.widget.DateMath.DAY, this.preMonthDays);

	this.table.style.visibility = "hidden"; // Hide while we render

	var weekRowIndex = 0;

	for (var c=0;c<this.cells.length;++c)
	{
		var cellRenderers = new Array();

		var cell = this.cells[c];

		this.clearElement(cell);

		YAHOO.util.Event.removeListener(cell, "click", this.doSelectCell);
		if (YAHOO.widget.Calendar_Core._getBrowser() == "ie") {
			YAHOO.util.Event.removeListener(cell, "mouseover", this.doCellMouseOver);
			YAHOO.util.Event.removeListener(cell, "mouseout", this.doCellMouseOut);
		}

		cell.index = c;
		cell.id = this.id + "_cell" + c;

		this.cellDates[this.cellDates.length]=[workingDate.getFullYear(),workingDate.getMonth()+1,workingDate.getDate()]; // Add this date to cellDates

		if (workingDate.getDay() == this.Options.START_WEEKDAY) {
			var rowHeaderCell = null;
			var rowFooterCell = null;

			if (this.Options.SHOW_WEEK_HEADER) {
				rowHeaderCell = this.weekHeaderCells[weekRowIndex];
				this.clearElement(rowHeaderCell);
			}

			if (this.Options.SHOW_WEEK_FOOTER) {
				rowFooterCell = this.weekFooterCells[weekRowIndex];
				this.clearElement(rowFooterCell);
			}

			if (this.Options.HIDE_BLANK_WEEKS && this.isDateOOM(workingDate) && ! YAHOO.widget.DateMath.isMonthOverlapWeek(workingDate)) {
				// The first day of the week is not in this month, and it's not an overlap week
				continue;
			} else {
				if (rowHeaderCell) {
					this.renderRowHeader(workingDate, rowHeaderCell);
				}
				if (rowFooterCell) {
					this.renderRowFooter(workingDate, rowFooterCell);
				}
			}
		}



		var renderer = null;

		if (workingDate.getFullYear()	== this.today.getFullYear() &&
			workingDate.getMonth()		== this.today.getMonth() &&
			workingDate.getDate()		== this.today.getDate())
		{
			cellRenderers[cellRenderers.length]=this.renderCellStyleToday;
		}

		if (this.isDateOOM(workingDate))
		{
			cellRenderers[cellRenderers.length]=this.renderCellNotThisMonth;
		} else {
			for (var r=0;r<this.renderStack.length;++r)
			{
				var rArray = this.renderStack[r];
				var type = rArray[0];

				var month;
				var day;
				var year;

				switch (type) {
					case YAHOO.widget.Calendar_Core.DATE:
						month = rArray[1][1];
						day = rArray[1][2];
						year = rArray[1][0];

						if (workingDate.getMonth()+1 == month && workingDate.getDate() == day && workingDate.getFullYear() == year)
						{
							renderer = rArray[2];
							this.renderStack.splice(r,1);
						}
						break;
					case YAHOO.widget.Calendar_Core.MONTH_DAY:
						month = rArray[1][0];
						day = rArray[1][1];

						if (workingDate.getMonth()+1 == month && workingDate.getDate() == day)
						{
							renderer = rArray[2];
							this.renderStack.splice(r,1);
						}
						break;
					case YAHOO.widget.Calendar_Core.RANGE:
						var date1 = rArray[1][0];
						var date2 = rArray[1][1];

						var d1month = date1[1];
						var d1day = date1[2];
						var d1year = date1[0];

						var d1 = new Date(d1year, d1month-1, d1day);

						var d2month = date2[1];
						var d2day = date2[2];
						var d2year = date2[0];

						var d2 = new Date(d2year, d2month-1, d2day);

						if (workingDate.getTime() >= d1.getTime() && workingDate.getTime() <= d2.getTime())
						{
							renderer = rArray[2];

							if (workingDate.getTime()==d2.getTime()) {
								this.renderStack.splice(r,1);
							}
						}
						break;
					case YAHOO.widget.Calendar_Core.WEEKDAY:

						var weekday = rArray[1][0];
						if (workingDate.getDay()+1 == weekday)
						{
							renderer = rArray[2];
						}
						break;
					case YAHOO.widget.Calendar_Core.MONTH:

						month = rArray[1][0];
						if (workingDate.getMonth()+1 == month)
						{
							renderer = rArray[2];
						}
						break;
				}

				if (renderer) {
					cellRenderers[cellRenderers.length]=renderer;
				}
			}

		}

		if (this._indexOfSelectedFieldArray([workingDate.getFullYear(),workingDate.getMonth()+1,workingDate.getDate()]) > -1)
		{
			cellRenderers[cellRenderers.length]=this.renderCellStyleSelected;
		}

		if (this.minDate)
		{
			this.minDate = YAHOO.widget.DateMath.clearTime(this.minDate);
		}
		if (this.maxDate)
		{
			this.maxDate = YAHOO.widget.DateMath.clearTime(this.maxDate);
		}

		if (
			(this.minDate && (workingDate.getTime() < this.minDate.getTime())) ||
			(this.maxDate && (workingDate.getTime() > this.maxDate.getTime()))
		) {
			cellRenderers[cellRenderers.length]=this.renderOutOfBoundsDate;
		} else {
			cellRenderers[cellRenderers.length]=this.renderCellDefault;
		}

		for (var x=0;x<cellRenderers.length;++x)
		{
			var ren = cellRenderers[x];
			if (ren.call(this,workingDate,cell) == YAHOO.widget.Calendar_Core.STOP_RENDER) {
				break;
			}
		}

		workingDate = YAHOO.widget.DateMath.add(workingDate, YAHOO.widget.DateMath.DAY, 1); // Go to the next day
		if (workingDate.getDay() == this.Options.START_WEEKDAY) {
			weekRowIndex += 1;
		}

		YAHOO.widget.Calendar_Core.addCssClass(cell, this.Style.CSS_CELL);
		if (c >= 0 && c <= 6) {
			YAHOO.widget.Calendar_Core.addCssClass(cell, this.Style.CSS_CELL_TOP);
		}
		if ((c % 7) == 0) {
			YAHOO.widget.Calendar_Core.addCssClass(cell, this.Style.CSS_CELL_LEFT);
		}
		if (((c+1) % 7) == 0) {
			YAHOO.widget.Calendar_Core.addCssClass(cell, this.Style.CSS_CELL_RIGHT);
		}

		var postDays = this.postMonthDays;
		if (postDays >= 7 && this.Options.HIDE_BLANK_WEEKS) {
			var blankWeeks = Math.floor(postDays/7);
			for (var p=0;p<blankWeeks;++p) {
				postDays -= 7;
			}
		}

		if (c >= ((this.preMonthDays+postDays+this.monthDays)-7)) {
			YAHOO.widget.Calendar_Core.addCssClass(cell, this.Style.CSS_CELL_BOTTOM);
		}
	}

	this.table.style.visibility = "visible"; // Show table, now that it's rendered

};

/**
* Appends the contents of the calendar widget footer into the shell. By default,
* the calendar does not contain a footer, and this method must be implemented by
* subclassing the widget.
*/
YAHOO.widget.Calendar_Core.prototype.renderFooter = function() { };

/**
* @private
*/
YAHOO.widget.Calendar_Core.prototype._unload = function(e, cal) {
	for (var c in cal.cells) {
		c = null;
	}

	cal.cells = null;

	cal.tbody = null;
	cal.oDomContainer = null;
	cal.table = null;
	cal.headerCell = null;

	cal = null;
};


/****************** BEGIN BUILT-IN TABLE CELL RENDERERS ************************************/

YAHOO.widget.Calendar_Core.prototype.renderOutOfBoundsDate = function(workingDate, cell) {
	YAHOO.widget.Calendar_Core.addCssClass(cell, "previous");
	cell.innerHTML = workingDate.getDate();
	return YAHOO.widget.Calendar_Core.STOP_RENDER;
}

/**
* Renders the row header for a week. The date passed in should be the first date of the given week.
* @param {Date}			workingDate		The current working Date object (beginning of the week) being used to generate the calendar
* @param {HTMLTableCellElement}	cell	The current working cell in the calendar
*/
YAHOO.widget.Calendar_Core.prototype.renderRowHeader = function(workingDate, cell) {
	YAHOO.widget.Calendar_Core.addCssClass(cell, this.Style.CSS_ROW_HEADER);

	var useYear = this.pageDate.getFullYear();

	if (! YAHOO.widget.DateMath.isYearOverlapWeek(workingDate)) {
		useYear = workingDate.getFullYear();
	}

	var weekNum = YAHOO.widget.DateMath.getWeekNumber(workingDate, useYear, this.Options.START_WEEKDAY);
	cell.innerHTML = weekNum;

	if (this.isDateOOM(workingDate) && ! YAHOO.widget.DateMath.isMonthOverlapWeek(workingDate)) {
		YAHOO.widget.Calendar_Core.addCssClass(cell, this.Style.CSS_CELL_OOM);
	}
};

/**
* Renders the row footer for a week. The date passed in should be the first date of the given week.
* @param {Date}			workingDate		The current working Date object (beginning of the week) being used to generate the calendar
* @param {HTMLTableCellElement}	cell	The current working cell in the calendar
*/
YAHOO.widget.Calendar_Core.prototype.renderRowFooter = function(workingDate, cell) {
	YAHOO.widget.Calendar_Core.addCssClass(cell, this.Style.CSS_ROW_FOOTER);

	if (this.isDateOOM(workingDate) && ! YAHOO.widget.DateMath.isMonthOverlapWeek(workingDate)) {
		YAHOO.widget.Calendar_Core.addCssClass(cell, this.Style.CSS_CELL_OOM);
	}
};

/**
* Renders a single standard calendar cell in the calendar widget table.
* All logic for determining how a standard default cell will be rendered is encapsulated in this method,
* and must be accounted for when extending the widget class.
* @param {Date}					workingDate		The current working Date object being used to generate the calendar
* @param {HTMLTableCellElement}	cell			The current working cell in the calendar
* @return YAHOO.widget.Calendar_Core.STOP_RENDER if rendering should stop with this style, null or nothing if rendering
*	should not be terminated
* @type String
*/
YAHOO.widget.Calendar_Core.prototype.renderCellDefault = function(workingDate, cell) {
	cell.innerHTML = "";
	var link = document.createElement("a");

	//link.href="javascript:void(null);";
	link.name=this.id+"__"+workingDate.getFullYear()+"_"+(workingDate.getMonth()+1)+"_"+workingDate.getDate();

	//link.onclick = this._selectEventLink;
	//cell.onclick = this.doSelectCell;

	YAHOO.util.Event.addListener(cell, "click", this.doSelectCell, this);
	if (YAHOO.widget.Calendar_Core._getBrowser() == "ie") {
		YAHOO.util.Event.addListener(cell, "mouseover", this.doCellMouseOver, this);
		YAHOO.util.Event.addListener(cell, "mouseout", this.doCellMouseOut, this);
	}
	link.appendChild(document.createTextNode(this.buildDayLabel(workingDate)));
	cell.appendChild(link);

	//cell.onmouseover = this.doCellMouseOver;
	//cell.onmouseout = this.doCellMouseOut;
};

YAHOO.widget.Calendar_Core.prototype.renderCellStyleHighlight1 = function(workingDate, cell) {
	YAHOO.widget.Calendar_Core.addCssClass(cell, this.Style.CSS_CELL_HIGHLIGHT1);
};
YAHOO.widget.Calendar_Core.prototype.renderCellStyleHighlight2 = function(workingDate, cell) {
	YAHOO.widget.Calendar_Core.addCssClass(cell, this.Style.CSS_CELL_HIGHLIGHT2);
};
YAHOO.widget.Calendar_Core.prototype.renderCellStyleHighlight3 = function(workingDate, cell) {
	YAHOO.widget.Calendar_Core.addCssClass(cell, this.Style.CSS_CELL_HIGHLIGHT3);
};
YAHOO.widget.Calendar_Core.prototype.renderCellStyleHighlight4 = function(workingDate, cell) {
	YAHOO.widget.Calendar_Core.addCssClass(cell, this.Style.CSS_CELL_HIGHLIGHT4);
};

/**
* Applies the default style used for rendering today's date to the current calendar cell
* @param {Date}					workingDate		The current working Date object being used to generate the calendar
* @param {HTMLTableCellElement}	cell			The current working cell in the calendar
* @return	YAHOO.widget.Calendar_Core.STOP_RENDER if rendering should stop with this style, null or nothing if rendering
*			should not be terminated
* @type String
*/
YAHOO.widget.Calendar_Core.prototype.renderCellStyleToday = function(workingDate, cell) {
	YAHOO.widget.Calendar_Core.addCssClass(cell, this.Style.CSS_CELL_TODAY);
};

/**
* Applies the default style used for rendering selected dates to the current calendar cell
* @param {Date}					workingDate		The current working Date object being used to generate the calendar
* @param {HTMLTableCellElement}	cell			The current working cell in the calendar
* @return	YAHOO.widget.Calendar_Core.STOP_RENDER if rendering should stop with this style, null or nothing if rendering
*			should not be terminated
* @type String
*/
YAHOO.widget.Calendar_Core.prototype.renderCellStyleSelected = function(workingDate, cell) {
	YAHOO.widget.Calendar_Core.addCssClass(cell, this.Style.CSS_CELL_SELECTED);
};

/**
* Applies the default style used for rendering dates that are not a part of the current
* month (preceding or trailing the cells for the current month)
* @param {Date}					workingDate		The current working Date object being used to generate the calendar
* @param {HTMLTableCellElement}	cell			The current working cell in the calendar
* @return	YAHOO.widget.Calendar_Core.STOP_RENDER if rendering should stop with this style, null or nothing if rendering
*			should not be terminated
* @type String
*/
YAHOO.widget.Calendar_Core.prototype.renderCellNotThisMonth = function(workingDate, cell) {
	YAHOO.widget.Calendar_Core.addCssClass(cell, this.Style.CSS_CELL_OOM);
	cell.innerHTML=workingDate.getDate();
	return YAHOO.widget.Calendar_Core.STOP_RENDER;
};

/**
* Renders the current calendar cell as a non-selectable "black-out" date using the default
* restricted style.
* @param {Date}					workingDate		The current working Date object being used to generate the calendar
* @param {HTMLTableCellElement}	cell			The current working cell in the calendar
* @return	YAHOO.widget.Calendar_Core.STOP_RENDER if rendering should stop with this style, null or nothing if rendering
*			should not be terminated
* @type String
*/
YAHOO.widget.Calendar_Core.prototype.renderBodyCellRestricted = function(workingDate, cell) {
	YAHOO.widget.Calendar_Core.setCssClasses(cell, [this.Style.CSS_CELL,this.Style.CSS_CELL_RESTRICTED]);
	cell.innerHTML=workingDate.getDate();
	return YAHOO.widget.Calendar_Core.STOP_RENDER;
};
/******************** END BUILT-IN TABLE CELL RENDERERS ************************************/

/******************** BEGIN MONTH NAVIGATION METHODS ************************************/
/**
* Adds the designated number of months to the current calendar month, and sets the current
* calendar page date to the new month.
* @param {Integer}	count	The number of months to add to the current calendar
*/
YAHOO.widget.Calendar_Core.prototype.addMonths = function(count) {
	this.pageDate = YAHOO.widget.DateMath.add(this.pageDate, YAHOO.widget.DateMath.MONTH, count);
	this.resetRenderers();
	this.onChangePage();
};

/**
* Subtracts the designated number of months from the current calendar month, and sets the current
* calendar page date to the new month.
* @param {Integer}	count	The number of months to subtract from the current calendar
*/
YAHOO.widget.Calendar_Core.prototype.subtractMonths = function(count) {
	this.pageDate = YAHOO.widget.DateMath.subtract(this.pageDate, YAHOO.widget.DateMath.MONTH, count);
	this.resetRenderers();
	this.onChangePage();
};

/**
* Adds the designated number of years to the current calendar, and sets the current
* calendar page date to the new month.
* @param {Integer}	count	The number of years to add to the current calendar
*/
YAHOO.widget.Calendar_Core.prototype.addYears = function(count) {
	this.pageDate = YAHOO.widget.DateMath.add(this.pageDate, YAHOO.widget.DateMath.YEAR, count);
	this.resetRenderers();
	this.onChangePage();
};

/**
* Subtcats the designated number of years from the current calendar, and sets the current
* calendar page date to the new month.
* @param {Integer}	count	The number of years to subtract from the current calendar
*/
YAHOO.widget.Calendar_Core.prototype.subtractYears = function(count) {
	this.pageDate = YAHOO.widget.DateMath.subtract(this.pageDate, YAHOO.widget.DateMath.YEAR, count);
	this.resetRenderers();
	this.onChangePage();
};

/**
* Navigates to the next month page in the calendar widget.
*/
YAHOO.widget.Calendar_Core.prototype.nextMonth = function() {
	this.addMonths(1);
};

/**
* Navigates to the previous month page in the calendar widget.
*/
YAHOO.widget.Calendar_Core.prototype.previousMonth = function() {
	this.subtractMonths(1);
};

/**
* Navigates to the next year in the currently selected month in the calendar widget.
*/
YAHOO.widget.Calendar_Core.prototype.nextYear = function() {
	this.addYears(1);
};

/**
* Navigates to the previous year in the currently selected month in the calendar widget.
*/
YAHOO.widget.Calendar_Core.prototype.previousYear = function() {
	this.subtractYears(1);
};

/****************** END MONTH NAVIGATION METHODS ************************************/

/************* BEGIN SELECTION METHODS *************************************************************/

/**
* Resets the calendar widget to the originally selected month and year, and
* sets the calendar to the initial selection(s).
*/
YAHOO.widget.Calendar_Core.prototype.reset = function() {
	this.selectedDates.length = 0;
	this.selectedDates = this._selectedDates.concat();

	this.pageDate = new Date(this._pageDate.getTime());
	this.onReset();
};

/**
* Clears the selected dates in the current calendar widget and sets the calendar
* to the current month and year.
*/
YAHOO.widget.Calendar_Core.prototype.clear = function() {
	this.selectedDates.length = 0;
	this.pageDate = new Date(this.today.getTime());
	this.onClear();
};

/**
* Selects a date or a collection of dates on the current calendar. This method, by default,
* does not call the render method explicitly. Once selection has completed, render must be
* called for the changes to be reflected visually.
* @param	{String/Date/Date[]}	date	The date string of dates to select in the current calendar. Valid formats are
*								individual date(s) (12/24/2005,12/26/2005) or date range(s) (12/24/2005-1/1/2006).
*								Multiple comma-delimited dates can also be passed to this method (12/24/2005,12/11/2005-12/13/2005).
*								This method can also take a JavaScript Date object or an array of Date objects.
* @return						Array of JavaScript Date objects representing all individual dates that are currently selected.
* @type Date[]
*/
YAHOO.widget.Calendar_Core.prototype.select = function(date) {
	this.onBeforeSelect();

	var aToBeSelected = this._toFieldArray(date);

	for (var a=0;a<aToBeSelected.length;++a)
	{
		var toSelect = aToBeSelected[a]; // For each date item in the list of dates we're trying to select
		if (this._indexOfSelectedFieldArray(toSelect) == -1) // not already selected?
		{
			this.selectedDates[this.selectedDates.length]=toSelect;
		}
	}

	if (this.parent) {
		this.parent.sync(this);
	}

	this.onSelect();

	return this.getSelectedDates();
};

/**
* Selects a date on the current calendar by referencing the index of the cell that should be selected.
* This method is used to easily select a single cell (usually with a mouse click) without having to do
* a full render. The selected style is applied to the cell directly.
* @param	{Integer}	cellIndex	The index of the cell to select in the current calendar.
* @return							Array of JavaScript Date objects representing all individual dates that are currently selected.
* @type Date[]
*/
YAHOO.widget.Calendar_Core.prototype.selectCell = function(cellIndex) {
	this.onBeforeSelect();

	this.cells = this.tbody.getElementsByTagName("TD");

	var cell = this.cells[cellIndex];
	var cellDate = this.cellDates[cellIndex];

	var dCellDate = this._toDate(cellDate);

	var selectDate = cellDate.concat();

	this.selectedDates.push(selectDate);

	if (this.parent) {
		this.parent.sync(this);
	}

	this.renderCellStyleSelected(dCellDate,cell);

	this.onSelect();
	this.doCellMouseOut.call(cell, null, this);

	return this.getSelectedDates();
};

/**
* Deselects a date or a collection of dates on the current calendar. This method, by default,
* does not call the render method explicitly. Once deselection has completed, render must be
* called for the changes to be reflected visually.
* @param	{String/Date/Date[]}	date	The date string of dates to deselect in the current calendar. Valid formats are
*								individual date(s) (12/24/2005,12/26/2005) or date range(s) (12/24/2005-1/1/2006).
*								Multiple comma-delimited dates can also be passed to this method (12/24/2005,12/11/2005-12/13/2005).
*								This method can also take a JavaScript Date object or an array of Date objects.
* @return						Array of JavaScript Date objects representing all individual dates that are currently selected.
* @type Date[]
*/
YAHOO.widget.Calendar_Core.prototype.deselect = function(date) {
	this.onBeforeDeselect();

	var aToBeSelected = this._toFieldArray(date);

	for (var a=0;a<aToBeSelected.length;++a)
	{
		var toSelect = aToBeSelected[a]; // For each date item in the list of dates we're trying to select
		var index = this._indexOfSelectedFieldArray(toSelect);

		if (index != -1)
		{
			this.selectedDates.splice(index,1);
		}
	}

	if (this.parent) {
		this.parent.sync(this);
	}

	this.onDeselect();
	return this.getSelectedDates();
};

/**
* Deselects a date on the current calendar by referencing the index of the cell that should be deselected.
* This method is used to easily deselect a single cell (usually with a mouse click) without having to do
* a full render. The selected style is removed from the cell directly.
* @param	{Integer}	cellIndex	The index of the cell to deselect in the current calendar.
* @return							Array of JavaScript Date objects representing all individual dates that are currently selected.
* @type Date[]
*/
YAHOO.widget.Calendar_Core.prototype.deselectCell = function(i) {
	this.onBeforeDeselect();
	this.cells = this.tbody.getElementsByTagName("TD");

	var cell = this.cells[i];
	var cellDate = this.cellDates[i];
	var cellDateIndex = this._indexOfSelectedFieldArray(cellDate);

	var dCellDate = this._toDate(cellDate);

	var selectDate = cellDate.concat();

	if (cellDateIndex > -1)
	{
		if (this.pageDate.getMonth() == dCellDate.getMonth() &&
			this.pageDate.getFullYear() == dCellDate.getFullYear())
		{
			YAHOO.widget.Calendar_Core.removeCssClass(cell, this.Style.CSS_CELL_SELECTED);
		}

		this.selectedDates.splice(cellDateIndex, 1);
	}


	if (this.parent) {
		this.parent.sync(this);
	}

	this.onDeselect();
	return this.getSelectedDates();
};

/**
* Deselects all dates on the current calendar.
* @return				Array of JavaScript Date objects representing all individual dates that are currently selected.
*						Assuming that this function executes properly, the return value should be an empty array.
*						However, the empty array is returned for the sake of being able to check the selection status
*						of the calendar.
* @type Date[]
*/
YAHOO.widget.Calendar_Core.prototype.deselectAll = function() {
	this.onBeforeDeselect();
	var count = this.selectedDates.length;
	this.selectedDates.length = 0;

	if (this.parent) {
		this.parent.sync(this);
	}

	if (count > 0) {
		this.onDeselect();
	}

	return this.getSelectedDates();
};
/************* END SELECTION METHODS *************************************************************/


/************* BEGIN TYPE CONVERSION METHODS ****************************************************/

/**
* Converts a date (either a JavaScript Date object, or a date string) to the internal data structure
* used to represent dates: [[yyyy,mm,dd],[yyyy,mm,dd]].
* @private
* @param	{String/Date/Date[]}	date	The date string of dates to deselect in the current calendar. Valid formats are
*								individual date(s) (12/24/2005,12/26/2005) or date range(s) (12/24/2005-1/1/2006).
*								Multiple comma-delimited dates can also be passed to this method (12/24/2005,12/11/2005-12/13/2005).
*								This method can also take a JavaScript Date object or an array of Date objects.
* @return						Array of date field arrays
* @type Array[](Integer[])
*/
YAHOO.widget.Calendar_Core.prototype._toFieldArray = function(date) {
	var returnDate = new Array();

	if (date instanceof Date)
	{
		returnDate = [[date.getFullYear(), date.getMonth()+1, date.getDate()]];
	}
	else if (typeof date == 'string')
	{
		returnDate = this._parseDates(date);
	}
	else if (date instanceof Array)
	{
		for (var i=0;i<date.length;++i)
		{
			var d = date[i];
			returnDate[returnDate.length] = [d.getFullYear(),d.getMonth()+1,d.getDate()];
		}
	}

	return returnDate;
};

/**
* Converts a date field array [yyyy,mm,dd] to a JavaScript Date object.
* @private
* @param	{Integer[]}		dateFieldArray	The date field array to convert to a JavaScript Date.
* @return					JavaScript Date object representing the date field array
* @type Date
*/
YAHOO.widget.Calendar_Core.prototype._toDate = function(dateFieldArray) {
	if (dateFieldArray instanceof Date)
	{
		return dateFieldArray;
	} else
	{
		return new Date(dateFieldArray[0],dateFieldArray[1]-1,dateFieldArray[2]);
	}
};
/************* END TYPE CONVERSION METHODS ******************************************************/


/************* BEGIN UTILITY METHODS ****************************************************/
/**
* Converts a date field array [yyyy,mm,dd] to a JavaScript Date object.
* @private
* @param	{Integer[]}	array1	The first date field array to compare
* @param	{Integer[]}	array2	The first date field array to compare
* @return						The boolean that represents the equality of the two arrays
* @type Boolean
*/
YAHOO.widget.Calendar_Core.prototype._fieldArraysAreEqual = function(array1, array2) {
	var match = false;

	if (array1[0]==array2[0]&&array1[1]==array2[1]&&array1[2]==array2[2])
	{
		match=true;
	}

	return match;
};

/**
* Gets the index of a date field array [yyyy,mm,dd] in the current list of selected dates.
* @private
* @param	{Integer[]}		find	The date field array to search for
* @return					The index of the date field array within the collection of selected dates.
*								-1 will be returned if the date is not found.
* @type Integer
*/
YAHOO.widget.Calendar_Core.prototype._indexOfSelectedFieldArray = function(find) {
	var selected = -1;

	for (var s=0;s<this.selectedDates.length;++s)
	{
		var sArray = this.selectedDates[s];
		if (find[0]==sArray[0]&&find[1]==sArray[1]&&find[2]==sArray[2])
		{
			selected = s;
			break;
		}
	}

	return selected;
};

/**
* Determines whether a given date is OOM (out of month).
* @param	{Date}	date	The JavaScript Date object for which to check the OOM status
* @return	{Boolean}	true if the date is OOM
*/
YAHOO.widget.Calendar_Core.prototype.isDateOOM = function(date) {
	var isOOM = false;
	if (date.getMonth() != this.pageDate.getMonth()) {
		isOOM = true;
	}
	return isOOM;
};

/************* END UTILITY METHODS *******************************************************/

/************* BEGIN EVENT HANDLERS ******************************************************/

/**
* Event executed before a date is selected in the calendar widget.
*/
YAHOO.widget.Calendar_Core.prototype.onBeforeSelect = function() {
	if (! this.Options.MULTI_SELECT) {
		this.clearAllBodyCellStyles(this.Style.CSS_CELL_SELECTED);
		this.deselectAll();
	}
};

/**
* Event executed when a date is selected in the calendar widget.
*/
YAHOO.widget.Calendar_Core.prototype.onSelect = function() {
	selData('form1:calendario_data'); //Criada para o sistema de Saude > Agenda
};

/**
* Event executed before a date is deselected in the calendar widget.
*/
YAHOO.widget.Calendar_Core.prototype.onBeforeDeselect = function() { };

/**
* Event executed when a date is deselected in the calendar widget.
*/
YAHOO.widget.Calendar_Core.prototype.onDeselect = function() { };

/**
* Event executed when the user navigates to a different calendar page.
*/
YAHOO.widget.Calendar_Core.prototype.onChangePage = function() { this.render(); };

/**
* Event executed when the calendar widget is rendered.
*/
YAHOO.widget.Calendar_Core.prototype.onRender = function() { };

/**
* Event executed when the calendar widget is reset to its original state.
*/
YAHOO.widget.Calendar_Core.prototype.onReset = function() { this.render(); };

/**
* Event executed when the calendar widget is completely cleared to the current month with no selections.
*/
YAHOO.widget.Calendar_Core.prototype.onClear = function() { this.render(); };

/**
* Validates the calendar widget. This method has no default implementation
* and must be extended by subclassing the widget.
* @return	Should return true if the widget validates, and false if
* it doesn't.
* @type Boolean
*/
YAHOO.widget.Calendar_Core.prototype.validate = function() { return true; };

/************* END EVENT HANDLERS *********************************************************/


/************* BEGIN DATE PARSE METHODS ***************************************************/


/**
* Converts a date string to a date field array
* @private
* @param	{String}	sDate			Date string. Valid formats are mm/dd and mm/dd/yyyy.
* @return				A date field array representing the string passed to the method
* @type Array[](Integer[])
*/
YAHOO.widget.Calendar_Core.prototype._parseDate = function(sDate) {
	var aDate = sDate.split(this.Locale.DATE_FIELD_DELIMITER);
	var rArray;

	if (aDate.length == 2)
	{
		rArray = [aDate[this.Locale.MD_MONTH_POSITION-1],aDate[this.Locale.MD_DAY_POSITION-1]];
		rArray.type = YAHOO.widget.Calendar_Core.MONTH_DAY;
	} else {
		rArray = [aDate[this.Locale.MDY_YEAR_POSITION-1],aDate[this.Locale.MDY_MONTH_POSITION-1],aDate[this.Locale.MDY_DAY_POSITION-1]];
		rArray.type = YAHOO.widget.Calendar_Core.DATE;
	}
	return rArray;
};

/**
* Converts a multi or single-date string to an array of date field arrays
* @private
* @param	{String}	sDates		Date string with one or more comma-delimited dates. Valid formats are mm/dd, mm/dd/yyyy, mm/dd/yyyy-mm/dd/yyyy
* @return							An array of date field arrays
* @type Array[](Integer[])
*/
YAHOO.widget.Calendar_Core.prototype._parseDates = function(sDates) {
	var aReturn = new Array();

	var aDates = sDates.split(this.Locale.DATE_DELIMITER);

	for (var d=0;d<aDates.length;++d)
	{
		var sDate = aDates[d];

		if (sDate.indexOf(this.Locale.DATE_RANGE_DELIMITER) != -1) {
			// This is a range
			var aRange = sDate.split(this.Locale.DATE_RANGE_DELIMITER);

			var dateStart = this._parseDate(aRange[0]);
			var dateEnd = this._parseDate(aRange[1]);

			var fullRange = this._parseRange(dateStart, dateEnd);
			aReturn = aReturn.concat(fullRange);
		} else {
			// This is not a range
			var aDate = this._parseDate(sDate);
			aReturn.push(aDate);
		}
	}
	return aReturn;
};

/**
* Converts a date range to the full list of included dates
* @private
* @param	{Integer[]}	startDate	Date field array representing the first date in the range
* @param	{Integer[]}	endDate		Date field array representing the last date in the range
* @return							An array of date field arrays
* @type Array[](Integer[])
*/
YAHOO.widget.Calendar_Core.prototype._parseRange = function(startDate, endDate) {
	var dStart   = new Date(startDate[0],startDate[1]-1,startDate[2]);
	var dCurrent = YAHOO.widget.DateMath.add(new Date(startDate[0],startDate[1]-1,startDate[2]),YAHOO.widget.DateMath.DAY,1);
	var dEnd     = new Date(endDate[0],  endDate[1]-1,  endDate[2]);

	var results = new Array();
	results.push(startDate);
	while (dCurrent.getTime() <= dEnd.getTime())
	{
		results.push([dCurrent.getFullYear(),dCurrent.getMonth()+1,dCurrent.getDate()]);
		dCurrent = YAHOO.widget.DateMath.add(dCurrent,YAHOO.widget.DateMath.DAY,1);
	}
	return results;
};

/************* END DATE PARSE METHODS *****************************************************/

/************* BEGIN RENDERER METHODS *****************************************************/

/**
* Resets the render stack of the current calendar to its original pre-render value.
*/
YAHOO.widget.Calendar_Core.prototype.resetRenderers = function() {
	this.renderStack = this._renderStack.concat();
};

/**
* Clears the inner HTML, CSS class and style information from the specified cell.
* @param	{HTMLTableCellElement}	The cell to clear
*/
YAHOO.widget.Calendar_Core.prototype.clearElement = function(cell) {
	cell.innerHTML = "&nbsp;";
	cell.className="";
};

/**
* Adds a renderer to the render stack. The function reference passed to this method will be executed
* when a date cell matches the conditions specified in the date string for this renderer.
* @param	{String}	sDates		A date string to associate with the specified renderer. Valid formats
*									include date (12/24/2005), month/day (12/24), and range (12/1/2004-1/1/2005)
* @param	{Function}	fnRender	The function executed to render cells that match the render rules for this renderer.
*/
YAHOO.widget.Calendar_Core.prototype.addRenderer = function(sDates, fnRender) {
	var aDates = this._parseDates(sDates);
	for (var i=0;i<aDates.length;++i)
	{
		var aDate = aDates[i];

		if (aDate.length == 2) // this is either a range or a month/day combo
		{
			if (aDate[0] instanceof Array) // this is a range
			{
				this._addRenderer(YAHOO.widget.Calendar_Core.RANGE,aDate,fnRender);
			} else { // this is a month/day combo
				this._addRenderer(YAHOO.widget.Calendar_Core.MONTH_DAY,aDate,fnRender);
			}
		} else if (aDate.length == 3)
		{
			this._addRenderer(YAHOO.widget.Calendar_Core.DATE,aDate,fnRender);
		}
	}
};

/**
* The private method used for adding cell renderers to the local render stack.
* This method is called by other methods that set the renderer type prior to the method call.
* @private
* @param	{String}	type		The type string that indicates the type of date renderer being added.
*									Values are YAHOO.widget.Calendar_Core.DATE, YAHOO.widget.Calendar_Core.MONTH_DAY, YAHOO.widget.Calendar_Core.WEEKDAY,
*									YAHOO.widget.Calendar_Core.RANGE, YAHOO.widget.Calendar_Core.MONTH
* @param	{Array}		aDates		An array of dates used to construct the renderer. The format varies based
*									on the renderer type
* @param	{Function}	fnRender	The function executed to render cells that match the render rules for this renderer.
*/
YAHOO.widget.Calendar_Core.prototype._addRenderer = function(type, aDates, fnRender) {
	var add = [type,aDates,fnRender];
	this.renderStack.unshift(add);

	this._renderStack = this.renderStack.concat();
};

/**
* Adds a month to the render stack. The function reference passed to this method will be executed
* when a date cell matches the month passed to this method.
* @param	{Integer}	month		The month (1-12) to associate with this renderer
* @param	{Function}	fnRender	The function executed to render cells that match the render rules for this renderer.
*/
YAHOO.widget.Calendar_Core.prototype.addMonthRenderer = function(month, fnRender) {
	this._addRenderer(YAHOO.widget.Calendar_Core.MONTH,[month],fnRender);
};

/**
* Adds a weekday to the render stack. The function reference passed to this method will be executed
* when a date cell matches the weekday passed to this method.
* @param	{Integer}	weekay		The weekday (1-7) to associate with this renderer
* @param	{Function}	fnRender	The function executed to render cells that match the render rules for this renderer.
*/
YAHOO.widget.Calendar_Core.prototype.addWeekdayRenderer = function(weekday, fnRender) {
	this._addRenderer(YAHOO.widget.Calendar_Core.WEEKDAY,[weekday],fnRender);
};
/************* END RENDERER METHODS *******************************************************/

/***************** BEGIN CSS METHODS *******************************************/
/**
* Adds the specified CSS class to the element passed to this method.
* @param	{HTMLElement}	element		The element to which the CSS class should be applied
* @param	{String}	style		The CSS class name to add to the referenced element
*/
YAHOO.widget.Calendar_Core.addCssClass = function(element, style) {
	if (element.className.length === 0)
	{
		element.className += style;
	} else {
		element.className += " " + style;
	}
};

YAHOO.widget.Calendar_Core.prependCssClass = function(element, style) {
	element.className = style + " " + element.className;
}

/**
* Removed the specified CSS class from the element passed to this method.
* @param	{HTMLElement}	element		The element from which the CSS class should be removed
* @param	{String}	style		The CSS class name to remove from the referenced element
*/
YAHOO.widget.Calendar_Core.removeCssClass = function(element, style) {
	var aStyles = element.className.split(" ");
	for (var s=0;s<aStyles.length;++s)
	{
		if (aStyles[s] == style)
		{
			aStyles.splice(s,1);
			break;
		}
	}
	YAHOO.widget.Calendar_Core.setCssClasses(element, aStyles);
};

/**
* Sets the specified array of CSS classes into the referenced element
* @param	{HTMLElement}	element		The element to set the CSS classes into
* @param	{String[]}		aStyles		An array of CSS class names
*/
YAHOO.widget.Calendar_Core.setCssClasses = function(element, aStyles) {
	element.className = "";
	var className = aStyles.join(" ");
	element.className = className;
};

/**
* Removes all styles from all body cells in the current calendar table.
* @param	{style}		The CSS class name to remove from all calendar body cells
*/
YAHOO.widget.Calendar_Core.prototype.clearAllBodyCellStyles = function(style) {
	for (var c=0;c<this.cells.length;++c)
	{
		YAHOO.widget.Calendar_Core.removeCssClass(this.cells[c],style);
	}
};

/***************** END CSS METHODS *********************************************/

/***************** BEGIN GETTER/SETTER METHODS *********************************/
/**
* Sets the calendar's month explicitly.
* @param {Integer}	month		The numeric month, from 1 (January) to 12 (December)
*/
YAHOO.widget.Calendar_Core.prototype.setMonth = function(month) {
	this.pageDate.setMonth(month);
};

/**
* Sets the calendar's year explicitly.
* @param {Integer}	year		The numeric 4-digit year
*/
YAHOO.widget.Calendar_Core.prototype.setYear = function(year) {
	this.pageDate.setFullYear(year);
};

/**
* Gets the list of currently selected dates from the calendar.
* @return	An array of currently selected JavaScript Date objects.
* @type Date[]
*/
YAHOO.widget.Calendar_Core.prototype.getSelectedDates = function() {
	var returnDates = new Array();

	for (var d=0;d<this.selectedDates.length;++d)
	{
		var dateArray = this.selectedDates[d];

		var date = new Date(dateArray[0],dateArray[1]-1,dateArray[2]);
		returnDates.push(date);
	}

	returnDates.sort();
	return returnDates;
};

/***************** END GETTER/SETTER METHODS *********************************/

YAHOO.widget.Calendar_Core._getBrowser = function()
{
  /**
   * UserAgent
   * @private
   * @type String
   */
  var ua = navigator.userAgent.toLowerCase();

  if (ua.indexOf('opera')!=-1) // Opera (check first in case of spoof)
	 return 'opera';
  else if (ua.indexOf('msie')!=-1) // IE
	 return 'ie';
  else if (ua.indexOf('safari')!=-1) // Safari (check before Gecko because it includes "like Gecko")
	 return 'safari';
  else if (ua.indexOf('gecko') != -1) // Gecko
	 return 'gecko';
 else
  return false;
}


YAHOO.widget.Cal_Core = YAHOO.widget.Calendar_Core;

YAHOO.namespace("YAHOO.widget");

/**
* @class
* <p>YAHOO.widget.CalendarGroup is a special container class for YAHOO.widget.Calendar_Core. This class facilitates
* the ability to have multi-page calendar views that share a single dataset and are
* dependent on each other.</p>
*
* <p>The calendar group instance will refer to each of its elements using a 0-based index.
* For example, to construct the placeholder for a calendar group widget with id "cal1" and
* containerId of "cal1Container", the markup would be as follows:
*	<xmp>
*		<div id="cal1Container_0"></div>
*		<div id="cal1Container_1"></div>
*	</xmp>
* The tables for the calendars ("cal1_0" and "cal1_1") will be inserted into those containers.
* </p>
* @constructor
* @param {Integer}		pageCount	The number of pages that this calendar should display.
* @param {String}		id			The id of the element that will be inserted into the DOM.
* @param {String}	containerId	The id of the container element that the calendar will be inserted into.
* @param {String}		monthyear	The month/year string used to set the current calendar page
* @param {String}		selected	A string of date values formatted using the date parser. The built-in
									default date format is MM/DD/YYYY. Ranges are defined using
									MM/DD/YYYY-MM/DD/YYYY. Month/day combinations are defined using MM/DD.
									Any combination of these can be combined by delimiting the string with
									commas. Example: "12/24/2005,12/25,1/18/2006-1/21/2006"
*/
YAHOO.widget.CalendarGroup = function(pageCount, id, containerId, monthyear, selected) {
	if (arguments.length > 0)
	{
		this.init(pageCount, id, containerId, monthyear, selected);
	}
}

/**
* Initializes the calendar group. All subclasses must call this method in order for the
* group to be initialized properly.
* @param {Integer}		pageCount	The number of pages that this calendar should display.
* @param {String}		id			The id of the element that will be inserted into the DOM.
* @param {String}		containerId	The id of the container element that the calendar will be inserted into.
* @param {String}		monthyear	The month/year string used to set the current calendar page
* @param {String}		selected	A string of date values formatted using the date parser. The built-in
									default date format is MM/DD/YYYY. Ranges are defined using
									MM/DD/YYYY-MM/DD/YYYY. Month/day combinations are defined using MM/DD.
									Any combination of these can be combined by delimiting the string with
									commas. Example: "12/24/2005,12/25,1/18/2006-1/21/2006"
*/
YAHOO.widget.CalendarGroup.prototype.init = function(pageCount, id, containerId, monthyear, selected) {
	//var self=this;

	this.id = id;
	this.selectedDates = new Array();
	this.containerId = containerId;

	this.pageCount = pageCount;

	this.pages = new Array();

	for (var p=0;p<pageCount;++p)
	{
		var cal = this.constructChild(id + "_" + p, this.containerId + "_" + p , monthyear, selected);

		cal.parent = this;

		cal.index = p;

		cal.pageDate.setMonth(cal.pageDate.getMonth()+p);
		cal._pageDateOrig = new Date(cal.pageDate.getFullYear(),cal.pageDate.getMonth(),cal.pageDate.getDate());
		this.pages.push(cal);
	}

	this.doNextMonth = function(e, calGroup) {
		calGroup.nextMonth();
	}

	this.doPreviousMonth = function(e, calGroup) {
		calGroup.previousMonth();
	}
};

YAHOO.widget.CalendarGroup.prototype.setChildFunction = function(fnName, fn) {
	for (var p=0;p<this.pageCount;++p) {
		this.pages[p][fnName] = fn;
	}
}

YAHOO.widget.CalendarGroup.prototype.callChildFunction = function(fnName, args) {
	for (var p=0;p<this.pageCount;++p) {
		var page = this.pages[p];
		if (page[fnName]) {
			var fn = page[fnName];
			fn.call(page, args);
		}
	}
}

/**
* Constructs a child calendar. This method can be overridden if a subclassed version of the default
* calendar is to be used.
* @param {String}		id			The id of the element that will be inserted into the DOM.
* @param {String}		containerId	The id of the container element that the calendar will be inserted into.
* @param {String}		monthyear	The month/year string used to set the current calendar page
* @param {String}		selected	A string of date values formatted using the date parser. The built-in
									default date format is MM/DD/YYYY. Ranges are defined using
									MM/DD/YYYY-MM/DD/YYYY. Month/day combinations are defined using MM/DD.
									Any combination of these can be combined by delimiting the string with
									commas. Example: "12/24/2005,12/25,1/18/2006-1/21/2006"
* @return							The YAHOO.widget.Calendar_Core instance that is constructed
* @type YAHOO.widget.Calendar_Core
*/
YAHOO.widget.CalendarGroup.prototype.constructChild = function(id,containerId,monthyear,selected) {
	return new YAHOO.widget.Calendar_Core(id,containerId,monthyear,selected);
};


/**
* Sets the calendar group's month explicitly. This month will be set into the first
* page of the multi-page calendar, and all other months will be iterated appropriately.
* @param {Integer}	month		The numeric month, from 1 (January) to 12 (December)
*/
YAHOO.widget.CalendarGroup.prototype.setMonth = function(month) {
	for (var p=0;p<this.pages.length;++p)
	{
		var cal = this.pages[p];
		cal.setMonth(month+p);
	}
};

/**
* Sets the calendar group's year explicitly. This year will be set into the first
* page of the multi-page calendar, and all other months will be iterated appropriately.
* @param {Integer}	year		The numeric 4-digit year
*/
YAHOO.widget.CalendarGroup.prototype.setYear = function(year) {
	for (var p=0;p<this.pages.length;++p)
	{
		var cal = this.pages[p];
		if ((cal.pageDate.getMonth()+1) == 1 && p>0)
		{
			year+=1;
		}
		cal.setYear(year);
	}
};

/**
* Calls the render function of all child calendars within the group.
*/
YAHOO.widget.CalendarGroup.prototype.render = function() {
	for (var p=0;p<this.pages.length;++p)
	{
		var cal = this.pages[p];
		cal.render();
	}
};

/**
* Calls the select function of all child calendars within the group.
*/
YAHOO.widget.CalendarGroup.prototype.select = function(date) {
	var ret;
	for (var p=0;p<this.pages.length;++p)
	{
		var cal = this.pages[p];
		ret = cal.select(date);
	}
	return ret;
};

/**
* Calls the selectCell function of all child calendars within the group.
*/
YAHOO.widget.CalendarGroup.prototype.selectCell = function(cellIndex) {
	var ret;
	for (var p=0;p<this.pages.length;++p)
	{
		var cal = this.pages[p];
		ret = cal.selectCell(cellIndex);
	}
	return ret;
};

/**
* Calls the deselect function of all child calendars within the group.
*/
YAHOO.widget.CalendarGroup.prototype.deselect = function(date) {
	var ret;
	for (var p=0;p<this.pages.length;++p)
	{
		var cal = this.pages[p];
		ret = cal.deselect(date);
	}
	return ret;
};

/**
* Calls the deselectAll function of all child calendars within the group.
*/
YAHOO.widget.CalendarGroup.prototype.deselectAll = function() {
	var ret;
	for (var p=0;p<this.pages.length;++p)
	{
		var cal = this.pages[p];
		ret = cal.deselectAll();
	}
	return ret;
};

/**
* Calls the deselectAll function of all child calendars within the group.
*/
YAHOO.widget.CalendarGroup.prototype.deselectCell = function(cellIndex) {
	for (var p=0;p<this.pages.length;++p)
	{
		var cal = this.pages[p];
		cal.deselectCell(cellIndex);
	}
	return this.getSelectedDates();
};

/**
* Calls the reset function of all child calendars within the group.
*/
YAHOO.widget.CalendarGroup.prototype.reset = function() {
	for (var p=0;p<this.pages.length;++p)
	{
		var cal = this.pages[p];
		cal.reset();
	}
};

/**
* Calls the clear function of all child calendars within the group.
*/
YAHOO.widget.CalendarGroup.prototype.clear = function() {
	for (var p=0;p<this.pages.length;++p)
	{
		var cal = this.pages[p];
		cal.clear();
	}
};

/**
* Calls the nextMonth function of all child calendars within the group.
*/
YAHOO.widget.CalendarGroup.prototype.nextMonth = function() {
	for (var p=0;p<this.pages.length;++p)
	{
		var cal = this.pages[p];
		cal.nextMonth();
	}
};

/**
* Calls the previousMonth function of all child calendars within the group.
*/
YAHOO.widget.CalendarGroup.prototype.previousMonth = function() {
	for (var p=this.pages.length-1;p>=0;--p)
	{
		var cal = this.pages[p];
		cal.previousMonth();
	}
};

/**
* Calls the nextYear function of all child calendars within the group.
*/
YAHOO.widget.CalendarGroup.prototype.nextYear = function() {
	for (var p=0;p<this.pages.length;++p)
	{
		var cal = this.pages[p];
		cal.nextYear();
	}
};

/**
* Calls the previousYear function of all child calendars within the group.
*/
YAHOO.widget.CalendarGroup.prototype.previousYear = function() {
	for (var p=0;p<this.pages.length;++p)
	{
		var cal = this.pages[p];
		cal.previousYear();
	}
};

/**
* Synchronizes the data values for all child calendars within the group. If the sync
* method is called passing in the caller object, the values of all children will be set
* to the values of the caller. If the argument is ommitted, the values from all children
* will be combined into one distinct list and set into each child.
* @param	{YAHOO.widget.Calendar_Core}	caller		The YAHOO.widget.Calendar_Core that is initiating the call to sync().
* @return								Array of selected dates, in JavaScript Date object form.
* @type Date[]
*/
YAHOO.widget.CalendarGroup.prototype.sync = function(caller) {
	var calendar;

	if (caller)
	{
		this.selectedDates = caller.selectedDates.concat();
	} else {
		var hash = new Object();
		var combinedDates = new Array();

		for (var p=0;p<this.pages.length;++p)
		{
			calendar = this.pages[p];

			var values = calendar.selectedDates;

			for (var v=0;v<values.length;++v)
			{
				var valueArray = values[v];
				hash[valueArray.toString()] = valueArray;
			}
		}

		for (var val in hash)
		{
			combinedDates[combinedDates.length]=hash[val];
		}

		this.selectedDates = combinedDates.concat();
	}

	// Set all the values into the children
	for (p=0;p<this.pages.length;++p)
	{
		calendar = this.pages[p];
		if (! calendar.Options.MULTI_SELECT) {
			calendar.clearAllBodyCellStyles(calendar.Config.Style.CSS_CELL_SELECTED);
		}
		calendar.selectedDates = this.selectedDates.concat();

	}

	return this.getSelectedDates();
};

/**
* Gets the list of currently selected dates from the calendar.
* @return			An array of currently selected JavaScript Date objects.
* @type Date[]
*/
YAHOO.widget.CalendarGroup.prototype.getSelectedDates = function() {
	var returnDates = new Array();

	for (var d=0;d<this.selectedDates.length;++d)
	{
		var dateArray = this.selectedDates[d];

		var date = new Date(dateArray[0],dateArray[1]-1,dateArray[2]);
		returnDates.push(date);
	}

	returnDates.sort();
	return returnDates;
};

/**
* Calls the addRenderer function of all child calendars within the group.
*/
YAHOO.widget.CalendarGroup.prototype.addRenderer = function(sDates, fnRender) {
	for (var p=0;p<this.pages.length;++p)
	{
		var cal = this.pages[p];
		cal.addRenderer(sDates, fnRender);
	}
};

/**
* Calls the addMonthRenderer function of all child calendars within the group.
*/
YAHOO.widget.CalendarGroup.prototype.addMonthRenderer = function(month, fnRender) {
	for (var p=0;p<this.pages.length;++p)
	{
		var cal = this.pages[p];
		cal.addMonthRenderer(month, fnRender);
	}
};

/**
* Calls the addWeekdayRenderer function of all child calendars within the group.
*/
YAHOO.widget.CalendarGroup.prototype.addWeekdayRenderer = function(weekday, fnRender) {
	for (var p=0;p<this.pages.length;++p)
	{
		var cal = this.pages[p];
		cal.addWeekdayRenderer(weekday, fnRender);
	}
};

/**
* Sets an event handler universally across all child calendars within the group. For instance,
* to set the onSelect handler for all child calendars to a function called fnSelect, the call would be:
* <code>
* calGroup.wireEvent("onSelect", fnSelect);
* </code>
* @param	{String}	eventName	The name of the event to handler to set within all child calendars.
* @param	{Function}	fn			The function to set into the specified event handler.
*/
YAHOO.widget.CalendarGroup.prototype.wireEvent = function(eventName, fn) {
	for (var p=0;p<this.pages.length;++p)
	{
		var cal = this.pages[p];
		cal[eventName] = fn;
	}
};

YAHOO.widget.CalGrp = YAHOO.widget.CalendarGroup;

YAHOO.namespace("YAHOO.widget");

/**
* @class
* Calendar is the default implementation of the YAHOO.widget.Calendar_Core base class.
* This class is the UED-approved version of the calendar selector widget. For all documentation
* on the implemented methods listed here, see the documentation for YAHOO.widget.Calendar_Core.
* @constructor
* @param {String}	id			The id of the table element that will represent the calendar widget
* @param {String}	containerId	The id of the container element that will contain the calendar table
* @param {String}	monthyear	The month/year string used to set the current calendar page
* @param {String}	selected	A string of date values formatted using the date parser. The built-in
								default date format is MM/DD/YYYY. Ranges are defined using
								MM/DD/YYYY-MM/DD/YYYY. Month/day combinations are defined using MM/DD.
								Any combination of these can be combined by delimiting the string with
								commas. Example: "12/24/2005,12/25,1/18/2006-1/21/2006"
*/
YAHOO.widget.Calendar = function(id, containerId, monthyear, selected) {
	if (arguments.length > 0)
	{
		this.init(id, containerId, monthyear, selected);
	}
}

YAHOO.widget.Calendar.prototype = new YAHOO.widget.Calendar_Core();

YAHOO.widget.Calendar.prototype.buildShell = function() {
	this.border = document.createElement("DIV");
	this.border.className =  this.Style.CSS_BORDER;

	this.table = document.createElement("TABLE");
	this.table.cellSpacing = 0;

	YAHOO.widget.Calendar_Core.setCssClasses(this.table, [this.Style.CSS_CALENDAR]);

	this.border.id = this.id;

	this.buildShellHeader();
	this.buildShellBody();
	this.buildShellFooter();
};

YAHOO.widget.Calendar.prototype.renderShell = function() {
	this.border.appendChild(this.table);
	this.oDomContainer.appendChild(this.border);
	this.shellRendered = true;
};

YAHOO.widget.Calendar.prototype.renderHeader = function() {
	this.headerCell.innerHTML = "";

	var headerContainer = document.createElement("DIV");
	headerContainer.className = this.Style.CSS_HEADER;

	var linkLeft = document.createElement("A");
	linkLeft.href = "javascript:" + this.id + ".previousMonth()";
	var imgLeft = document.createElement("IMG");
	imgLeft.src = this.Options.NAV_ARROW_LEFT;
	imgLeft.className = this.Style.CSS_NAV_LEFT;
	linkLeft.appendChild(imgLeft);

	var linkRight = document.createElement("A");
	linkRight.href = "javascript:" + this.id + ".nextMonth()";
	var imgRight = document.createElement("IMG");
	imgRight.src = this.Options.NAV_ARROW_RIGHT;
	imgRight.className = this.Style.CSS_NAV_RIGHT;
	linkRight.appendChild(imgRight);

	headerContainer.appendChild(linkLeft);
	headerContainer.appendChild(document.createTextNode(this.buildMonthLabel()));
	headerContainer.appendChild(linkRight);

	this.headerCell.appendChild(headerContainer);
};

YAHOO.widget.Cal = YAHOO.widget.Calendar;

YAHOO.namespace("YAHOO.widget");

/**
* @class
* Calendar2up_Cal is the default implementation of the Calendar_Core base class, when used
* in a 2-up view. This class is the UED-approved version of the calendar selector widget. For all documentation
* on the implemented methods listed here, see the documentation for Calendar_Core. This class
* has some special attributes that only apply to calendars rendered within the calendar group implementation.
* There should be no reason to instantiate this class directly.
* @constructor
* @param {String}	id			The id of the table element that will represent the calendar widget
* @param {String}	containerId	The id of the container element that will contain the calendar table
* @param {String}	monthyear	The month/year string used to set the current calendar page
* @param {String}	selected	A string of date values formatted using the date parser. The built-in
								default date format is MM/DD/YYYY. Ranges are defined using
								MM/DD/YYYY-MM/DD/YYYY. Month/day combinations are defined using MM/DD.
								Any combination of these can be combined by delimiting the string with
								commas. Example: "12/24/2005,12/25,1/18/2006-1/21/2006"
*/
YAHOO.widget.Calendar2up_Cal = function(id, containerId, monthyear, selected) {
	if (arguments.length > 0)
	{
		this.init(id, containerId, monthyear, selected);
	}
}

YAHOO.widget.Calendar2up_Cal.prototype = new YAHOO.widget.Calendar_Core();

/**
* Renders the header for each individual calendar in the 2-up view. More
* specifically, this method handles the placement of left and right arrows for
* navigating between calendar pages.
*/
YAHOO.widget.Calendar2up_Cal.prototype.renderHeader = function() {
	this.headerCell.innerHTML = "";

	var headerContainer = document.createElement("DIV");
	headerContainer.className = this.Style.CSS_HEADER;

	if (this.index == 0) {
		var linkLeft = document.createElement("A");
		linkLeft.href = "javascript:void(null)";
		YAHOO.util.Event.addListener(linkLeft, "click", this.parent.doPreviousMonth, this.parent);
		var imgLeft = document.createElement("IMG");
		imgLeft.src = this.Options.NAV_ARROW_LEFT;
		imgLeft.className = this.Style.CSS_NAV_LEFT;
		linkLeft.appendChild(imgLeft);
		headerContainer.appendChild(linkLeft);
	}

	headerContainer.appendChild(document.createTextNode(this.buildMonthLabel()));

	if (this.index == 1) {
		var linkRight = document.createElement("A");
		linkRight.href = "javascript:void(null)";
		YAHOO.util.Event.addListener(linkRight, "click", this.parent.doNextMonth, this.parent);
		var imgRight = document.createElement("IMG");
		imgRight.src = this.Options.NAV_ARROW_RIGHT;
		imgRight.className = this.Style.CSS_NAV_RIGHT;
		linkRight.appendChild(imgRight);
		headerContainer.appendChild(linkRight);
	}

	this.headerCell.appendChild(headerContainer);
};




/**
* @class
* Calendar2up is the default implementation of the Calendar2up_Core base class, when used
* in a 2-up view. This class is the UED-approved version of the 2-up calendar selector widget. For all documentation
* on the implemented methods listed here, see the documentation for Calendar2up_Core.
* @constructor
* @param {String}	id			The id of the table element that will represent the calendar widget
* @param {String}	containerId	The id of the container element that will contain the calendar table
* @param {String}	monthyear	The month/year string used to set the current calendar page
* @param {String}	selected	A string of date values formatted using the date parser. The built-in
								default date format is MM/DD/YYYY. Ranges are defined using
								MM/DD/YYYY-MM/DD/YYYY. Month/day combinations are defined using MM/DD.
								Any combination of these can be combined by delimiting the string with
								commas. Example: "12/24/2005,12/25,1/18/2006-1/21/2006"
*/
YAHOO.widget.Calendar2up = function(id, containerId, monthyear, selected) {
	if (arguments.length > 0)
	{
		this.buildWrapper(containerId);
		this.init(2, id, containerId, monthyear, selected);
	}
}

YAHOO.widget.Calendar2up.prototype = new YAHOO.widget.CalendarGroup();

/**
* Implementation of CalendarGroup.constructChild that ensures that child calendars of
* Calendar2up will be of type Calendar2up_Cal.
*/
YAHOO.widget.Calendar2up.prototype.constructChild = function(id,containerId,monthyear,selected) {
	var cal = new YAHOO.widget.Calendar2up_Cal(id,containerId,monthyear,selected);
	return cal;
};

/**
* Builds the wrapper container for the 2-up calendar.
* @param {String} containerId	The id of the outer container element.
*/
YAHOO.widget.Calendar2up.prototype.buildWrapper = function(containerId) {
	var outerContainer = document.getElementById(containerId);

	outerContainer.className = "calcontainer";

	var innerContainer = document.createElement("DIV");
	innerContainer.className = "calbordered";
	innerContainer.id = containerId + "_inner";

	var cal1Container = document.createElement("DIV");
	cal1Container.id = containerId + "_0";
	cal1Container.className = "cal2up";
	cal1Container.style.marginRight = "10px";

	var cal2Container = document.createElement("DIV");
	cal2Container.id = containerId + "_1";
	cal2Container.className = "cal2up";

	outerContainer.appendChild(innerContainer);
	innerContainer.appendChild(cal1Container);
	innerContainer.appendChild(cal2Container);

	this.innerContainer = innerContainer;
	this.outerContainer = outerContainer;
}

/**
* Renders the 2-up calendar.
*/
YAHOO.widget.Calendar2up.prototype.render = function() {
	this.renderHeader();
	YAHOO.widget.CalendarGroup.prototype.render.call(this);
	this.renderFooter();
};

/**
* Renders the header located at the top of the container for the 2-up calendar.
*/
YAHOO.widget.Calendar2up.prototype.renderHeader = function() {
	if (! this.title) {
		this.title = "";
	}
	if (! this.titleDiv)
	{
		this.titleDiv = document.createElement("DIV");
		if (this.title == "")
		{
			this.titleDiv.style.display="none";
		}
	}

	this.titleDiv.className = "title";
	this.titleDiv.innerHTML = this.title;

	if (this.outerContainer.style.position == "absolute")
	{
		var linkClose = document.createElement("A");
		linkClose.href = "javascript:void(null)";
		YAHOO.util.Event.addListener(linkClose, "click", this.hide, this);

		var imgClose = document.createElement("IMG");
		imgClose.src = "..../resources/images/calendario_x.gif";
		imgClose.className = "close-icon";

		linkClose.appendChild(imgClose);

		this.linkClose = linkClose;

		this.titleDiv.appendChild(linkClose);
	}

	this.innerContainer.insertBefore(this.titleDiv, this.innerContainer.firstChild);
}

/**
* Hides the 2-up calendar's outer container from view.
*/
YAHOO.widget.Calendar2up.prototype.hide = function(e, cal) {
	if (! cal)
	{
		cal = this;
	}
	cal.outerContainer.style.display = "none";
}

/**
* Renders a footer for the 2-up calendar container. By default, this method is
* unimplemented.
*/
YAHOO.widget.Calendar2up.prototype.renderFooter = function() {}

YAHOO.widget.Cal2up = YAHOO.widget.Calendar2up;
