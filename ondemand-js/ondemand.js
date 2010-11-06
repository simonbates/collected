// Copyright (c) 2007, 2008 Simon Bates
// Licensed under the MIT license.

(function(){
	var makeTrigger = function(elem, label, func){
		// create a trigger button and place it after the element
		var p = document.createElement("p");
		var button = document.createElement("button");
		button.innerHTML = label;
		p.appendChild(button);
		dojo.place(p, elem, "after");
		dojo.connect(button, "onclick", func);
	};

	ondemand = function(elem, showLabel, hideLabel){
		elem = dojo.byId(elem);
		// hide the element
		dojo.style(elem, "display", "none");
		dojo.style(elem, "overflow", "hidden");

		var show = function(event){
			dojo.style(elem, "height", "1px");
			dojo.style(elem, "display", "block");
			var anim = dojo.animateProperty({ node: elem, duration: 300,
				properties: {
					height: { start: "1", end: elem.scrollHeight, unit: "px" }
				}
			});
			dojo.connect(anim, "onEnd", function(){
				// after we are finished showing the element
				// switch the functionality to hide
				// by changing the label on the trigger control
				// and directing our event handler to use the
				// hide function
				event.target.innerHTML = hideLabel;
				dispatchTo = hide;
			});
			anim.play();
		};

		var hide = function(event){
			dojo.style(elem, "display", "block");
			dojo.style(elem, "height", elem.scrollHeight + "px");
			var anim = dojo.animateProperty({ node: elem, duration: 300,
				properties: { height: { end: "1", unit: "px" } }
			});
			dojo.connect(anim, "onEnd", function(){
				// after we are finished hiding the element
				// switch the functionality to show
				// by changing the label on the trigger control
				// and directing our event handler to use the
				// show function
				dojo.style(elem, "display", "none");
				event.target.innerHTML = showLabel;
				dispatchTo = show;
			});
			anim.play();
		};

		// our event handler uses a variable called "dispatchTo"
		// to determine whether it will show or hide
		var dispatchTo = show;
		var dispatcher = function(event){
			dispatchTo.apply(null, [event])
		};

		makeTrigger(elem, showLabel, dispatcher);
	}
})();
