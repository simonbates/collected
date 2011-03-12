function Turtle(canvasId) {
    this.canvasId = canvasId;
    var canvas = document.getElementById(canvasId);
    this.x = canvas.width/2;
    this.y = canvas.height/2;
    this.heading = 0 - Math.PI / 2; // North, in radians
}

Turtle.prototype.setxy = function(x, y) {
    this.x = x;
    this.y = y;
}

Turtle.prototype.forward = function(dist) {
    var canvas = document.getElementById(this.canvasId);
    var context = canvas.getContext("2d");
    var xDelta = dist * Math.cos(this.heading);
    var yDelta = dist * Math.sin(this.heading);
    context.beginPath();
    context.moveTo(this.x, this.y);
    this.x = this.x + xDelta;
    this.y = this.y + yDelta;
    context.lineTo(this.x, this.y);
    context.strokeStyle = "#000";
    context.stroke();
}

Turtle.prototype.back = function(dist) {
    var xDelta = dist * Math.cos(this.heading);
    var yDelta = dist * Math.sin(this.heading);
    this.x = this.x - xDelta;
    this.y = this.y - yDelta;
}

Turtle.prototype.left = function(degrees) {
    this.heading = this.heading - ((Math.PI * degrees) / 180);
}

Turtle.prototype.right = function(degrees) {
    this.heading = this.heading + ((Math.PI * degrees) / 180);
}
