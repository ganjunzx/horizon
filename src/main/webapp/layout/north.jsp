
<div style="margin: 0 0 0 50px;">
	<img alt="" src="img/logo.png" width="120" height="60"></img>
</div>
<div id="sessionInfoDiv" style="position: absolute; right: 0px; top: 0px;" class="alert alert-info"></div>
<div style="position: absolute; right: 3px; bottom: 3px;color:green;font-size:14px;">欢迎您，张涛&nbsp;&nbsp;&nbsp;&nbsp;
<a class="btn btn-primary btn-info btn-small" href="#">退出系统</a></div>

<script>

function tick() {
	var minutes, seconds;
	var intHours, intMinutes, intSeconds;
	var today, theday;
	today = new Date();
	
	function initArray() {
		this.length = initArray.arguments.length
		for ( var i = 0; i < this.length; i++)
			this[i + 1] = initArray.arguments[i]
	}
	
	var d = new initArray("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六");
	theday = today.getFullYear() + "年" + [ today.getMonth() + 1 ] + "月"
			+ today.getDate()+ "日	" + d[today.getDay() + 1];
	intHours = today.getHours();
	intMinutes = today.getMinutes();
	intSeconds = today.getSeconds();
	if (intMinutes < 10) {
		minutes = "0" + intMinutes + ":";
	} else {
		minutes = intMinutes + ":";
	}
	if (intSeconds < 10) {
		seconds = "0" + intSeconds + " ";
	} else {
		seconds = intSeconds + " ";
	}
	timeString = theday + intHours + ":" + minutes + seconds;
	document.getElementById('sessionInfoDiv').innerHTML = timeString;
// 	sessionInfoDiv.innerHTML = timeString;
	window.setTimeout("tick();", 100);
}
window.load=tick();
</script>
