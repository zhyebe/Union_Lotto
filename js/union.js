$(function() {
	layer.load(1,1);
	var date=new Date();
	//获取当前星期
	var dayofweek=date.getDay();
	if(dayofweek==1||dayofweek==3||dayofweek==5){
		getWebData();
	}
	//获取数据
	$("#get_new_data").click(function(){
		getWebData();
	});
	randomBall();
	findAllball();
	// 添加
	$("#add_btn").click(function() {
		var union_id=$("#union_lotto_id").val();
		var redBall = $("#red").val();
		var blueBall = $("#blue").val();
		if (redBall == "" || blueBall == "") {
			layer.alert("数据不能为空！");
		} else {
			$.ajax({
				url : getRootPath() + "/union/add.do",
				type : "post",
				data : {
					"id":union_id,
					"redball" : redBall,
					"blueball" : blueBall
				},
				dataType : "json",
				success : function(result) {
					findAllball();
					randomBall();
					layer.msg(result.msg);
				}
			});
		}
	});

	// 随机选号
	$("#auto_btn").click(function() {
		randomBall();
	});

	// 查询往期开奖号
	$("#all_btn").click(function() {
		findAllball();
	});
});

//随机选号
function randomBall(){
	$("#redball").empty();
	$("#blueball").empty();
	$.ajax({
		url : getRootPath() + "/union/randomUnionLotto.do",
		type : "post",
		dataType : "json",
		success : function(result) {
			var datas = result.data;
			var redballs=datas[0].split(",");
			for(var i=0;i<redballs.length;i++){
				$li=$("<li style='style:none;display:inline-block;margin:7px;'>"+redballs[i]+"</li>");
				$("#redball").append($li);
			}
			$b_li=$("<li style='style:none;display:inline-block;'>"+datas[1]+"</li>");
			$("#blueball").append($b_li);
		}
	});
}

//获取最新数据
function getWebData(){
	$.ajax({
		url : getRootPath() + "/data/getWebData.do",
		type : "post",
		dataType : "json",
		success : function(result) {
			var union_id=result.id;
			var redBalls=result.redBalls;
			var blueBall=result.blueBall;
			var redballs="";
			for(var i=0;i<redBalls.length;i++){
				redballs+=redBalls[i];
				if(i<redBalls.length-1){
					redballs+=",";
				}
			}
			$("#union_lotto_id").val("20"+union_id);
			$("#red").css("color","#ff3399");
			$("#blue").css("color","#009fcc");
			$("#red").val(redballs);
			$("#blue").val(blueBall);
			
		}
	});
}

function getStart(pageSize,pageNum){
	return (pageNum-1)*pageSize+1;
}

function getEnd(pageSize,pageNum){
	return pageSize*pageNum;
}
//获取列表
function findAllball() {
	$("#all_td_union").empty();
	$.ajax({
		url : getRootPath() + "/union/findAll.do",
		type : "post",
		dataType : "json",
		success : function(result) {
			layer.close(layer.load(1,1));
			var pageSize=15;
			var datas = result.data;
			var pageTotal=Math.ceil(datas.length/pageSize);
			var pageNum=1;
			$("#page_msg").css("display","block");
			$("#page_num").html(pageNum);
			$("#page_total").html(pageTotal);
			for (var i = getStart(pageSize, pageNum)-1; i < getEnd(pageSize, pageNum); i++) {
				var ball = datas[i];
				$tb = $("<tr>" + "<td style='color:#000;'>" + ball.id + "</td>"
						+ "<td style='color:#ff3399;'>" + ball.redBalls + "</td>"
						+ "<td>" + ball.blueBall + "</td>"
						+ "</tr>");
				$("#all_td_union").append($tb);
			}
			$("#before_page").click(function(){
				if(pageNum-1>0){
					pageNum-=1;
					$("#all_td_union").empty();
					$("#page_num").html(pageNum);
					for (var j = getStart(pageSize, pageNum)-1; j < getEnd(pageSize, pageNum); j++) {
						var ball = datas[j];
						$tb = $("<tr>" + "<td style='color:#000;'>" + ball.id + "</td>"
								+ "<td style='color:#ff3399;'>" + ball.redBalls + "</td>"
								+ "<td>" + ball.blueBall + "</td>"
								+ "</tr>");
						$("#all_td_union").append($tb);
					}
				}
			});
			$("#next_page").click(function(){
				if(pageNum<pageTotal){
					pageNum+=1;
					$("#all_td_union").empty();
					$("#page_num").html(pageNum);
					for (var n = getStart(pageSize, pageNum)-1; n < getEnd(pageSize, pageNum); n++) {
						var ball1 = datas[n];
						$tb = $("<tr>" + "<td style='color:#000;'>" + ball1.id + "</td>"
								+ "<td style='color:#ff3399;'>" + ball1.redBalls + "</td>"
								+ "<td>" + ball1.blueBall + "</td>"
								+ "</tr>");
						$("#all_td_union").append($tb);
					}
				}
			});
			
		}
	});
}

/* start 获取rootPath */
function getRootPath() {
	// 获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
	var curWwwPath = window.document.location.href;
	// 获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
	// 获取主机地址，如： http://localhost:8083
	var localhostPaht = curWwwPath.substring(0, pos);
	// 获取带"/"的项目名，如：/uimcardprj
	var projectName = pathName
			.substring(0, pathName.substr(1).indexOf('/') + 1);
	return (localhostPaht + projectName);
}
/* end 获取rootPath */