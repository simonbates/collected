function Turtle(canvasId) {
    this.canvasId = canvasId;
    var canvas = document.getElementById(canvasId);
    this.x = canvas.width/2;
    this.y = canvas.height/2;
    this.heading = 0 // North
    this.lineWidth = 1;
    this.strokeStyle = "rgb(0,0,0)";
}

Turtle.prototype.setXY = function(x, y) {
    this.x = x;
    this.y = y;
}

Turtle.prototype.setHeading = function(degrees) {
    this.heading = degrees;
}

Turtle.prototype.forward = function(dist) {
    var canvas = document.getElementById(this.canvasId);
    var context = canvas.getContext("2d");
    var xDelta = dist * Math.cos(this.getHeadingInRadians());
    var yDelta = dist * Math.sin(this.getHeadingInRadians());
    context.beginPath();
    context.moveTo(this.x, this.y);
    this.x = this.x + xDelta;
    this.y = this.y + yDelta;
    context.lineTo(this.x, this.y);
    context.lineWidth = this.lineWidth;
    context.strokeStyle = this.strokeStyle;
    context.stroke();
}

Turtle.prototype.back = function(dist) {
    var xDelta = dist * Math.cos(this.getHeadingInRadians());
    var yDelta = dist * Math.sin(this.getHeadingInRadians());
    this.x = this.x - xDelta;
    this.y = this.y - yDelta;
}

Turtle.prototype.left = function(degrees) {
    this.heading = this.heading - degrees;
}

Turtle.prototype.right = function(degrees) {
    this.heading = this.heading + degrees;
}

Turtle.prototype.setLineWidth = function(pixels) {
    this.lineWidth = pixels;
}

Turtle.prototype.setColor = function(r, g, b) {
    this.strokeStyle = "rgb(" + r + "," + g + "," + b + ")";
}

Turtle.prototype.getHeadingInRadians = function() {
    return (Math.PI * (this.heading -90)) / 180;
}
