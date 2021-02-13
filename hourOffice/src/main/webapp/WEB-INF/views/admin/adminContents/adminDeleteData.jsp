<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	
	<!-- 관리자 페이지 데이터 / 문서 관리  css -->
	<link rel="stylesheet" type="text/css" href="/resources/css/admin/deleteData.css" />

	<!-- 구글 차트 api -->
	<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>

</head>
<body>
	<div id="wrap">
		<%@ include file ="../adminForm/header.jsp" %>
		
		<div id="contentsBox">
			<%@ include file ="../adminForm/sideNaviBar.jsp" %>
			
			<div id="contents">
				<div id="contentsDetail" class="clearfix">
					<div id="TitleName">
						데이터/문서 관리
    				</div>
    				<div id="TitleContents">
						
						<div id="record-div">
							<div id="memberRecord" class="float">
								<div>사원 기록 삭제</div>
								<table>
									<tr>
										<td style="height: 110px;">보존기간(1년) 경과 문서</td>
										<td>${deleteMemberCount}건</td>
									</tr>
								</table>
								<div>
									<div id="myChart1" class="graph-div"></div>
									<div>
										[2명/23명]<br>
										<button class="agreeButtonType">삭제</button>
									</div>
								</div>
							</div>
							<div id="boardRecord" class="float">
								<div>게시물 기록 삭제</div>
								<table><tr><td>삭제 대기 기간 경과 문서</td><td>0 건</td></tr></table>
								<table><tr><td>보존기간(5년) 경과 문서</td><td>0 건</td></tr></table>
								<div>
									<div id="myChart2" class="graph-div"></div>
									<div>
										[0건/1223건]<br>
										<button class="agreeButtonType">삭제</button>
									</div>
								</div>
							</div>
							<div id="paymentRecord" class="float">
								<div>결재안 기록 삭제</div>
								<table><tr><td>삭제 대기 기간 경과 문서</td><td>0 건</td></tr></table>
								<table><tr><td>보존기간(3년) 경과 문서</td><td>2 건</td></tr></table>
								<div>
									<div id="myChart3" class="graph-div"></div>
									<div>
										[0건/294871939건]<br>
										<button class="agreeButtonType">삭제</button>
									</div>
								</div>
							</div>
						</div>						
						<script type="text/javascript">						
							// 데이터 연산
							var result = [3.4, 2, 7.9];
							
					        //구글차트 
					        google.charts.load('current', {'packages':['corechart']}); 
					        google.charts.setOnLoadCallback(drawChart);
					        function drawChart() {
					        	
					            for(var i=1; i<4; i++){
					            	var data = new google.visualization.DataTable();
					            	data.addColumn('string','Foods');
					            	data.addColumn('number','비중');
					            	data.addRows([
					     	           ['',0],
					     	           ['기간 경과',result[i-1]],
					     	           ['',0],
					     	           ['',0],
					     	           ['',0],
					     	           ['',0],
					     	           ['',0],
					     	           ['',0],
					     	           ['',0],
					     	           ['미경과',10.0-result[i-1]]
					     	        ]);
					                var opt = {
					                        'title':'',
					                        'width':250,
					                        'height':250,
					                        legend:{ position: 'bottom' }
					                };
					            	var chart = new google.visualization.PieChart(document.getElementById('myChart'+i));
					                chart.draw(data,opt);
					            }					            
					        }				 
				        </script>						
    				</div>
				</div>
			</div> 
		</div>
	</div>
	
	<script type='text/javascript'>	    
		// 네비 화살표 돌리기	
			$dataList = $('#dataList');
	        $dataList.children().eq(2).children().attr('class','iArrow fas fa-angle-left');		
			$dataList.removeClass('hoverColor').addClass('click');
	</script>
</body>
</html>