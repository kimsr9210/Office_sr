<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>H:our Office</title>
	<!-- 폰트어썸 -->
    <script src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css">
    
    <!--jQuery CDN-->
    <script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
	
	<!-- JSTL:C -->
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<!-- JSTL:fmt -->
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	<!-- JSTL:fn -->
	<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
	
	<!-- CSS -->
	<link rel="stylesheet" type="text/css" href="/resources/css/header&sideNavi.css" />
	<link rel="stylesheet" type="text/css" href="/resources/css/memberMain/memberMain_notice.css">
	<link rel="stylesheet" type="text/css" href="/resources/css/memberMain/memberMain_nameCard.css">
	<link rel="stylesheet" type="text/css" href="/resources/css/memberMain/memberMain_work.css">
	<link rel="stylesheet" type="text/css" href="/resources/css/memberMain/memberMain_cal.css">
	<link rel="stylesheet" type="text/css" href="/resources/css/memberMain/memberMain_publicPro.css">
	<link rel="stylesheet" type="text/css" href="/resources/css/memberMain/memberMain_inPro.css">
	<link rel="stylesheet" type="text/css" href="/resources/css/memberMain/memberMain_mail.css">
	<link rel="stylesheet" type="text/css" href="/resources/css/memberMain/memberMain_graph.css">
	<link rel="stylesheet" type="text/css" href="/resources/css/memberMain/memberMain_group.css">
	<link rel="stylesheet" type="text/css" href="/resources/css/memberMain/memberMain_role.css">
</head>
<style>
.content-l{
        width: calc(100% - 275px);
        padding: 30px 10px 0px 20px;
        float: left;
    }
    .content-r{
        width: 275px;
        padding: 15px 15px 0px 10px;
        float: left;
    }
    .content-l>div{
        margin-bottom: 30px; /*25px*/
    }
    .content-r>div{
        margin-bottom: 20px; /*20px*/
    }
    
    /*참여중&메일함*/
    .proAmail{
        height: 280px;
        display: flex;
    }
    #columnchart_values{
        width: 75%;
    }
    /* 즐겨찾기 색상 */
	.favor{
		color: yellow;
	}
</style>
<body>
    <div id="wrap">
        
        <%@ include file="/WEB-INF/views/common/header.jsp" %>
        
        <div id="contentsBox">
            <%@ include file="/WEB-INF/views/common/sideNavi.jsp" %>
            
            <div id="contents" class="clearfix">
               
                <div class="content-l">
                    
                    <div class="notice">
                        <div class="notice-top">
                            <a href="#">공지</a>
                        </div>
                        <div class="notice-bottom">
                            <a href="#">
                                <span>공지입니다!</span>
                                <span>1월 29일 Front 마무리 점검이 있습니다.</span>
                            </a>
                            
                            <div>
                                <span>작성 날짜</span><span>조회수</span>
                            </div>
                        </div>
                    </div>
                    
                    <div class="public-pro">
                        <a href="/projectAllList.ho">최근 공개 프로젝트</a>
                        <div class="public-list">
                            
                            <c:forEach items="${publicList }" var="pl" varStatus="status" begin="0" end="3">
	                            
	                       		<div id="pro${status.count }" class="proStyle count-${pl.memCount }">
		                             <a class="pro-title">
		                             	<c:choose>
		                             		<c:when test="${pl.memCount eq 0 }">
		                             			<span>빈 프로젝트</span>
		                             		</c:when>
		                             		<c:otherwise>
		                             			<form action="/projectDetail.ho" method="post">
			                             			<span class="pro-subject">${pl.proSubject }</span>
			                             			<input type="hidden" name="proNo" value="${pl.proNo }"/>
													<input type="hidden" name="memNo" value="${sessionScope.member.memNo }"/>
													<input type="hidden" name="proSubject" value="${pl.proSubject }"/>
													<input type="hidden" name="boardType" value="post"/>
		                             			</form>
		                             		</c:otherwise>
		                             	</c:choose>
		                             </a>
		                             <div class="pro-info">
		                                 <a>${pl.memCount }명 참여중</a>
		                                 <i class="fas fa-star favor"></i>
		                             </div>
		                         </div>
                            </c:forEach>
                                
                        </div>
                    </div>
                    
                    <div class="proAmail">
                       
                        <div id="proIn" class="in-pro">
                        	<c:choose>
                            	<c:when test="${empty myProject }">
                            		<div class="in-pro-none">
                            			<button class="attendBtn">프로젝트 참여하기</button>
                            		</div>
                            	</c:when>
                            	
                            	<c:otherwise>
                            	
                            		<div class="in-pro-top">
		                                <a>${myProject[0].proSubject }</a>
		                                <i class="fas fa-star favor"></i><br>
		                                <span>${myProject[0].memCount }명 참여중</span>
		                            </div>
		                            <div class="in-pro-post">
		                            
		                            <c:choose>
		                            	<c:when test="${empty boardList }">
		                            		<div class="post-none">
		                            			등록된 게시물이 없습니다..
		                            		</div>
		                            	</c:when>
		                            	<c:otherwise>
		                            	
		                            	<div class="post-info">
		                                    <img>
		                                    <div>
		                                        <span>${boardWirter.memName }</span><br>
		                                        <span><fmt:formatDate value="${boardList[0].boardDate }" pattern="yyyy-MM-dd HH:mm:ss"/></span>
		                                    </div>
		                                    <c:if test="${boardWirter.memName eq sessionScope.member.memName}">
		                                    	<i id="boardMenu" class="fas fa-ellipsis-v"></i>
		                                    </c:if>
		                                </div>
		                                <div class="post-content">
		                                    ${boardList[0].boardText }
		                                </div>
		                                <div class="post-comment">
		                                    <div class="comment-count">
		                                        <span>댓글 ${fn:length(commentList)} 개</span>
		                                    </div>
		                                </div>
		                                
		                            	</c:otherwise>
		                            </c:choose>
		                                
		                                <hr size="1" color="lightgray">
		                                <form action="/projectDetail.ho" method="post">
	                             			<a class="shortcutsBtn"><i class="fas fa-location-arrow"></i> 프로젝트 바로가기</a>
	                             			<input type="hidden" name="proNo" value="${myProject[0].proNo }"/>
											<input type="hidden" name="memNo" value="${sessionScope.member.memNo }"/>
											<input type="hidden" name="proSubject" value="${myProject[0].proSubject }"/>
											<input type="hidden" name="boardType" value="post"/>
                             			</form>
		                                
		                                
		                            </div>
                            
                            	</c:otherwise>
                            </c:choose>
                            
                        </div>
                        
                        <div id="myMail">
                            <div class="myMail-title">메일함</div>
                            <div id="mailNavi">
                                <div id="reMail">받은메일</div>
                                <div id="mailBtn">
                                    <button><i class="fas fa-chevron-left"></i></button>
                                    <button><i class="fas fa-chevron-right"></i></button>
                                </div>
                            </div>
                            <div id="mailList">
                                메일이 없습니다.
                            </div>
                        </div>
                        
                    </div>
                    
                    <div class="pro-graph">
                        <div id="columnchart_values"></div>
                        <div class="pro-graph-count" style="width:25%;">
                            <span id="cTitle">이번달 프로젝트 수</span>
                            <span id="cValue">9</span><!--4자리까지-->
                        </div>
                        
                        
                    </div>
                    
                    <div class="role">
                        <div class="role-top">
                            <a href="#">사내규정</a>
                            <div id="roleBtn">
                                <button><i class="fas fa-chevron-left"></i></button>
                                <button><i class="fas fa-chevron-right"></i></button>
                            </div>
                        </div>
                        <div class="role-bottom">
                            <table id="role-data">
                                <tr>
                                    <th>번호</th>
                                    <th>제목</th>
                                    <th>작성자</th>
                                    <th>작성일</th>
                                </tr>
                                <tr>
                                    <td>4</td>
                                    <td><a href="#">네 번째 사내규정</a></td>
                                    <td>이진원 차장</td>
                                    <td>21/01/28</td>
                                </tr>
                                <tr>
                                    <td>3</td>
                                    <td><a href="#">세 번째 사내규정</a></td>
                                    <td>이진원 차장</td>
                                    <td>21/01/27</td>
                                </tr>
                                <tr>
                                    <td>2</td>
                                    <td><a href="#">두 번째 사내규정</a></td>
                                    <td>이진원 차장</td>
                                    <td>21/01/26</td>
                                </tr>
                                <tr>
                                    <td>1</td>
                                    <td><a href="#">첫 번째 사내규정</a></td>
                                    <td>이진원 차장</td>
                                    <td>21/01/25</td>
                                </tr>
                            </table>
                        </div>
                    </div>
                    
                    <div class="footer">
                    </div>
                    
                </div>
                
                <div class="content-r">
                   
                    <div class="nameCard">
                        <img class="nc-img" src="/resources/images/profile/${sessionScope.member.memProfile }">
                        <!-- <i class="fas fa-user-circle nc-icon"></i> -->
                        <span class="nc-name">${sessionScope.member.memName } ${sessionScope.member.memPosition }</span>
                        <span class="nc-dept">
	                        <c:choose>
	                        	<c:when test="${empty sessionScope.member.deptName }">
	                        		부서 없음
	                        	</c:when>
	                        	<c:otherwise>
	                        		${sessionScope.member.deptName }
	                        	</c:otherwise>
	                        </c:choose>
                        </span>
                        
                        <div>
                            <a href="#" class="nc-state">결재할 문서</a>
                            <a href="#" class="nc-cont">${alCount }</a>
                        </div>
                        <div>
                            <a href="/projectAllList.ho" class="nc-state">참여중 프로젝트</a>
                            <a href="/projectAllList.ho" class="nc-cont">${fn:length(myProject)}</a>
                        </div>
                        
                    </div>
                    
                    <div class="work">
                        <div class="work-title">근태관리</div>
                        <div id="clock">시간</div>
                        <div class="work-today">오늘 근무시간</div>
                        
                        <div class="work-record">
                            <span id="work-hour">
                            	<c:choose>
                            		<c:when test="${empty atten.todayWork }">
                            			0
                            		</c:when>
                            		<c:otherwise>
                            		<c:set var="workHour" value="${fn:indexOf(atten.todayWork,'h') }"></c:set>
                            			${fn:substring(atten.todayWork,0,workHour) }
                            		</c:otherwise>
                            	</c:choose>
                            </span> 시간
                            <span id="work-minute">
                            	<c:choose>
                            		<c:when test="${empty atten.todayWork }">
                            			0
                            		</c:when>
                            		<c:otherwise>
                            		<c:set var="workMin" value="${fn:indexOf(atten.todayWork,'m') }"></c:set>
                            			${fn:substring(atten.todayWork,(workHour+1),workMin) }
                            		</c:otherwise>
                            	</c:choose>
                            </span> 분
                            <input type="hidden" id="workTime" name="todayWork">
                        </div>
                        
                        <div class="record">
                            <span class="record-name">출근시간</span>
                            <span id="go-clock" class="record-clock">
                            	<c:choose>
                            		<c:when test="${empty atten.startDate }">
                            			미등록
                            		</c:when>
                            		<c:otherwise>
                            			<fmt:formatDate value="${atten.startDate }" pattern="M/d HH:mm:ss"/>
                            		</c:otherwise>
                            	</c:choose>
                            </span>
                            <input type="hidden" id="startTime" name="startDate" value="${atten.startDate }">
                        </div>
                        
                        <div class="record">
                            <span class="record-name">퇴근시간</span>
                            <span id="back-clock" class="record-clock">
                            	<c:choose>
                            		<c:when test="${empty atten.endDate }">
                            			미등록
                            		</c:when>
                            		<c:otherwise>
                            			<fmt:formatDate value="${atten.endDate }" pattern="M/d HH:mm:ss"/>
                            		</c:otherwise>
                            	</c:choose>
                            </span>
                            <input type="hidden" name="endDate" value="${atten.endDate }">
                        </div>
                        
                        <hr size="1" color="lightgray">
                        
                        <button id="go" class="btnStyle">출근하기</button>
                        <button id="back" class="btnStyle">퇴근하기</button>
                        
                    </div>
                    
                    <div class="cal">
                        <div class="calendar">
                            <div class="header">
                                <div class="year-month"></div>
                                <div class="nav">
                                    <button class="nav-btn go-prev" onclick="prevMonth()"><i class="fas fa-chevron-left"></i></button>
                                    <button class="nav-btn go-next" onclick="nextMonth()"><i class="fas fa-chevron-right"></i></button>
                                </div>
                            </div>
                            
                            <div class="main">
                                <div class="days">
                                    <div class="day">일</div>
                                    <div class="day">월</div>
                                    <div class="day">화</div>
                                    <div class="day">수</div>
                                    <div class="day">목</div>
                                    <div class="day">금</div>
                                    <div class="day">토</div>
                                </div>
                                <div class="dates">
                                    
                                </div>
                            </div>
                            
                        </div>
                    </div>
                    
                    <div class="group">
                        <!--조직도-->
                        <ul class="tree">
                            <li>
                                <input type="checkbox" id="root">
                                <label id="root" for="root">칠판그룹</label>
                                
                                <ul id="treeMenu">
                                    <c:forEach items="${companyOC }" var="memberOC">
                                   		<c:if test="${empty memberOC.deptCode }">
                                   			<li><span>${memberOC.memName } ${memberOC.memPosition }</span></li>
                                   		</c:if>
                                   	</c:forEach>
                                    
                                    <c:forEach items="${deptList }" var="dept" varStatus="d">
                                    	<li>
	                                        <input type="checkbox" id="node${d.count }" checked="checked">
	                                        <label class="deptName" for="node${d.count }">${dept.deptName }</label>
	                                        <ul>
	                                        	<c:forEach items="${companyOC }" var="member">
	                                      			<c:choose>
	                                      				<c:when test="${empty member.memName }">
	                                      					
	                                      				</c:when>
	                                      				<c:otherwise>
		                                      				<c:if test="${member.deptCode eq dept.deptCode}">
			                                        			<li><span>${member.memName } ${member.memPosition }</span></li>
			                                        		</c:if>
	                                      				</c:otherwise>
	                                      			</c:choose>
	                                        		
	                                        	</c:forEach>
	                                        </ul>
                                    	</li>
                                    </c:forEach>
                                </ul>
                                
                            </li>
                        </ul>
                    </div>
                    
                </div>
                
            </div>
        </div>
    </div>
<script>
    
</script>
<script type="text/javascript" src="/resources/js/header&sideNavi.js"></script>
<script src="/resources/js/memberMain/memberMain_cal.js"></script>
<script src="/resources/js/memberMain/memberMain_work.js"></script>
<script src="/resources/js/memberMain/memberMain_publicPro.js"></script>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript">
    google.charts.load("current", {packages:['corechart']});
    google.charts.setOnLoadCallback(drawChart);
    
    function drawChart() {
        var tYear = new Date().getFullYear();
        var tMonth = new Date().getMonth();
        
        var monthArr = [];
        for(var i=0; i<5; i++){
            var iMonth = new Date(tYear,tMonth-i);
            monthArr.push(iMonth.getMonth()+1);
        }
            
        var data = google.visualization.arrayToDataTable([
        ["Element", "생성 수", { role: "style" } ],
        [monthArr[4]+"월", 9, "#595959"],
        [monthArr[3]+"월", 10, "#595959"],
        [monthArr[2]+"월", 19, "#595959"],
        [monthArr[1]+"월", 21, "color: #595959"],
        [monthArr[0]+"월", 9, "#595959"]
        ]);

        var view = new google.visualization.DataView(data);
        view.setColumns([0, 1,
                       { calc: "stringify",
                         sourceColumn: 1,
                         type: "string",
                         role: "none" },
                       2]);

        var options = {
                title: { position: "none" },
                bar: {groupWidth: "95%"},
                legend: { position: "none" },
                width: '100%',
                height: 425,
                chartArea:{left:'15%',width:'80%',height:'80%'}
        };
        
        var chart = new google.visualization.ColumnChart(document.getElementById("columnchart_values"));
        chart.draw(view, options);
        window.addEventListener('resize',drawChart,false);
    }
</script>

</body></html>