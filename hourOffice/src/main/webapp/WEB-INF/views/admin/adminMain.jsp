<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>H:our Office</title>
		
			<!-- 폰트 어썸 CDN -->
			<script src="https://use.fontawesome.com/releases/v5.8.2/js/all.js"></script>
			<!-- 제이쿼리 CDN -->
			<script src="https://code.jquery.com/jquery-3.5.1.js"integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc="crossorigin="anonymous"></script>
		
			<!-- 관리자 페이지 공통 css -->
			<link rel="stylesheet" type="text/css" href="/resources/css/admin/style.css" />
			
			<!-- 관리자 페이지 메인 css -->
			<link rel="stylesheet" type="text/css" href="/resources/css/admin/main.css" />

	</head>
	<body>
		<!--JSTL core Tag 사용 선언  -->
		<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
		
		<!-- JSTL format Tag 사용 선언 -->
		<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

	<div id="wrap">
		<%@ include file="./adminForm/header.jsp"%>

		<div id="contentsBox">
			<%@ include file="./adminForm/sideNaviBar.jsp"%>

			<div id="contents">
				<div class="contentsArray">
					<div id="presentTer" class="content clearfix">
						<table class="terTblStyle">
							<tr>
								<th>오늘의 근태 현황</th>
							</tr>
							<tr>
								<td><div id="donutchart"></div></td>
							</tr>
						</table>
					</div>

					<div id="toDoTer" class="content clearfix">
						<table class="terTblStyle">
							<tr><th>TODOLIST</th></tr>
							<tr><td>
								<table class="toDoList">
									<tr><td>　</td></tr>
									<tr><td><span>삭제 기간 경과 문서</span></td></tr>
									<tr><td><p>10건</p></td></tr>
									<tr><td>　</td></tr>
									<tr><td><span>보존 기간 경과 문서</span></td></tr>
									<tr><td><p>${expireMemberCount}건</p></td></tr>
									<tr><td>　</td></tr>
									<tr><td><span>미경과 문서</span></td></tr>
									<tr><td><p>${expireNotMemberCount}건</p></td></tr>
									<tr><td>　</td></tr>
								</table>
							</td></tr>
						</table>
					</div>
				</div>

				<div class="contentsArray">
					<div id="statsTer" class="content clearfix">
						<table class="terTblStyle">
							<tr>
								<th colspan=2;>프로젝트 통계</th>
							</tr>
							<tr>
								<td><div id="columnchart_material" class="projectTbl"></div></td>
								<td class="projectTbl">
									<table class="toDoList">
									<tr><td>　</td></tr>
									<tr><td><span style="background-color: rgba(67, 131, 254, 0.2)">진행중인 프로젝트 갯수</span></td></tr>
									<tr><td><p>10건</p></td></tr>
									<tr><td><span style="background-color: rgba(218, 70, 52, 0.2)">끝낸 프로젝트 갯수</span></td></tr>
									<tr><td><p>30건</p></td></tr>
									<tr><td><span style="background-color: rgba(247, 179, 0, 0.2)">새로 만들어진 프로젝트 갯수</span></td></tr>
									<tr><td><p>5건</p></td></tr>
									<tr><td>　</td></tr>
									</table>								
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script type='text/javascript'>	    
		$(document).ready(function() {
			var $teamName = $('#teamName');
			$teamName.removeClass('hoverColor');
		});
	</script>
	
		<!-- 구글 차트  -->
		<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
		<script type="text/javascript">
		      google.charts.load("current", {packages:["corechart"]});
		      google.charts.setOnLoadCallback(drawChart);
		      function drawChart() {
		        var data = google.visualization.arrayToDataTable([
		        
		          ['상태', '현황'],
		          ['',     	  0],
		          ['',        0],
		          ['출근', 	 85],
		          ['퇴근',    13],
		          ['결근',     2],
		        ]);
		
		        var options = {
		          title: '사원 수 : 100명',
		          legend: {position:'bottom'},
		          pieHole: 0.3
		        };
		
		        var chart = new google.visualization.PieChart(document.getElementById('donutchart'));
		        chart.draw(data, options);
		      }
		</script>
		<script type="text/javascript">
		      google.charts.load('current', {'packages':['bar']});
		      google.charts.setOnLoadCallback(drawChart);
		
		      function drawChart() {
		        var data = google.visualization.arrayToDataTable([
		          ['', '진행중인 프로젝트', '끝낸 프로젝트', '신규 프로젝트'],
		          ['10월', 20, 25, 5],
		          ['11월', 20, 26, 10],
		          ['12월', 15, 15, 20],
		          ['1월', 14, 22, 23],
		          ['2월', 10, 12, 1]
		        ]);
		
		        var options = {
		        	legend: {position: 'none'}
				};
		
		        var chart = new google.charts.Bar(document.getElementById('columnchart_material'));
		
		        chart.draw(data, google.charts.Bar.convertOptions(options));
		      }
		</script>
	</body>
</html>