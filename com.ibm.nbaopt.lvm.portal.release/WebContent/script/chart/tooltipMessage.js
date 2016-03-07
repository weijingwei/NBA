function showBarTooltip(x, y, color, contents,target) {
	//console.log(x,y);
    $('<div id="tooltip">' + contents + '</div>').css({
        position: 'absolute',
        display: 'block',
        border: '2px solid ' + color,
        padding: '3px',
        'font-size': '9px',
        'border-radius': '5px',
        'background-color': '#fff',
        'font-family': 'Verdana, Arial, Helvetica, Tahoma, sans-serif',
        left:x-230,
        top:y-100,
        opacity: 0.9
    }).appendTo("#"+target).fadeIn(200);
}

function showScatterTooltip(x, y, color, contents) {
    $('<div id="tooltip">' + contents + '</div>').css({
        position: 'absolute',
        display: 'block',
        top: y - 40,
        left: x - 120,
        border: '2px solid ' + color,
        padding: '3px',
        'font-size': '9px',
        'border-radius': '5px',
        'background-color': '#fff',
        'font-family': 'Verdana, Arial, Helvetica, Tahoma, sans-serif',
        opacity: 0.9
    }).appendTo("body").fadeIn(200);
}