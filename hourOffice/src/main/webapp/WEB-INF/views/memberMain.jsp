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
	
	/* 폰트 */
	@font-face {
    font-family: 'GongGothicMedium';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-10@1.0/GongGothicMedium.woff') format('woff');
    font-weight: normal;
    font-style: normal;
	}
	@font-face {
	    font-family: 'GongGothicLight';
	    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-10@1.0/GongGothicLight.woff') format('woff');
	    font-weight: normal;
	    font-style: normal;
	}
</style>
<body>
<c:if test="${empty sessionScope.member }">
	<script>
		alert('로그인이 필요합니다.');
		location.replace('/login.ho');
	</script>
</c:if>
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
                    	<c:choose>
                    		<c:when test="${empty noticeList }">
                    			<span>등록된 공지가 없습니다...</span>
                    		</c:when>
                    		<c:otherwise>
                    			<a href="#">
                    				<span>${noticeList[0].notTitle }</span>
		                            <span>${noticeList[0].notContent }</span>
		                        </a>
		                        
		                        <div>
		                            <span><fmt:formatDate value="${noticeList[0].notDate }" pattern="yyyy년 M월 d일 HH:mm:ss"/></span>
		                            <span>${noticeList[0].notHits }</span>
		                        </div>
                    		</c:otherwise>
                    	</c:choose>
                        
                    </div>
                </div>
                
                <div class="public-pro">
                    <a href="/projectAllList.ho">최근 공개 프로젝트</a>
                    <div class="public-list">
                        
                        <c:forEach items="${publicList }" var="pl" varStatus="status" begin="0" end="3">
                        	<form action="/projectDetail.ho" method="post">
                         
	                    		<div id="pro${status.count }" class="proStyle count-${pl.memCount }">
	                           		
	                           		<c:choose>
		                           		<c:when test="${pl.memCount eq 0 }">
			                           		<a class="pro-title">
			                           			<span>빈 프로젝트</span>
			                           		</a>
		                           		</c:when>
		                           		<c:otherwise>
		                           			<a class="pro-title">
		                            			<span class="pro-subject">${pl.proSubject }</span>
		                           			</a>
		                           			<div class="pro-info">
				                                <a>${pl.memCount }명 참여중</a>
				                                <div class="projectLike">
					                                <c:set var="like" value="false"></c:set>
					                                <c:forEach items="${favorList }" var="fl">
					                                	<c:if test="${fl.proNo eq pl.proNo }">
					                                		<c:set var="like" value="true"></c:set>		
					                                	</c:if>
					                                </c:forEach>
					                                <c:choose>
					                                	<c:when test="${like eq true }">
					                                		<i class="fas fa-star favor"></i>
					                                	</c:when>
					                                	<c:otherwise>
					                                		<i class="far fa-star"></i>
					                                	</c:otherwise>
					                                </c:choose>
					                                
					                                <input type="hidden" name="proNo" value="${pl.proNo }"/>
													<input type="hidden" name="memNo" value="${sessionScope.member.memNo }"/>
													<input type="hidden" name="proSubject" value="${pl.proSubject }"/>
													<input type="hidden" name="boardType" value="post"/>
												</div>
				                            </div>
		                           		</c:otherwise>
	                           		</c:choose>
		                            
	                       		</div>
                       		</form>
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
								<form action="/projectDetail.ho" method="post">
								<div class="in-pro-top">
									<a class="detailBtn">${myProject[0].proSubject }</a>
									
									<div class="inProLike">
		                                <c:set var="like" value="false"></c:set>
		                                <c:forEach items="${favorList }" var="fl">
		                                	<c:if test="${fl.proNo eq myProject[0].proNo }">
		                                		<c:set var="like" value="true"></c:set>		
		                                	</c:if>
		                                </c:forEach>
		                                <c:choose>
		                                	<c:when test="${like eq true }">
		                                		<i class="fas fa-star favor"></i>
		                                	</c:when>
		                                	<c:otherwise>
		                                		<i class="far fa-star"></i>
		                                	</c:otherwise>
		                                </c:choose>
										<input type="hidden" name="proNo" value="${myProject[0].proNo }"/>
										<input type="hidden" name="memNo" value="${sessionScope.member.memNo }"/>
										<input type="hidden" name="proSubject" value="${myProject[0].proSubject }"/>
										<input type="hidden" name="boardType" value="post"/>
									</div>
									<br>
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
												<img src="/resources/images/profile/${boardWirter.memProfile }">
												<div>
												    <span>${boardWirter.memName }</span><br>
													<span><fmt:formatDate value="${boardList[0].boardDate }" pattern="yyyy-MM-dd HH:mm:ss"/></span>
											    </div>
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
									
										<a class="shortcutsBtn"><i class="fas fa-location-arrow"></i> 프로젝트 바로가기</a>
										
									
								</div>
								</form>
							</c:otherwise>
						</c:choose>
					</div>
				    
				    <div id="myMail">
				        <div class="myMail-title">메일함</div>
				        <div id="mailNavi">
				            <div id="reMail">받은메일</div>
				            <div id="mailBtn">
				                <button id="preMail"><i class="fas fa-chevron-left"></i></button>
				                <button id="nextMail"><i class="fas fa-chevron-right"></i></button>
				            </div>
				            <input type="hidden" name="mailPage" id="mailPage" value="1">
				        </div>
				        <div id="mailBOX">
				        	<c:choose>
				        		<c:when test="${empty mailList }">
				        			메일이 없습니다.
				        		</c:when>
				        		<c:otherwise>
				        			<table id="mailStyle">
				        				<tbody id="mailBody">
				        				<c:forEach items="${mailList }" var="ml">
				        				<tr>
					        				<td>${ml.sendDateFormat }</td>
					        				<td><a href="/mailView.ho?mailNo=${ml.mailNo }&other=${ml.recMemNo}&listType=R&readYN=${ml.readYN}">${ml.title }</a></td>
				        				</tr>
				        				</c:forEach>
				        				</tbody>
				        			</table>
				        			<script>
                        			var mailPage = $('#mailPage').val();
                        			
                        			$('#nextMail').click(function(){
                        				mailPage++;
                        				
                        				$.ajax({
                        	            	url : "/moveMailPage.ho",
                        	            	data : {"mailPage":mailPage},
                                        	type : "get",
                                        	success : function(data){
                                        		if(data.length !=0){
                                        			var mailData;
                                        			
                                        			for(var i=0; i<data.length; i++){
                                        				
                                        				mailData += "<tr>";
                                        				
                                        				mailData += "<td>"+data[i].sendDateFormat+"</td>";
                                        				mailData += "<td><a href='/mailView.ho?mailNo="+data[i].mailNo+"&other="+data[i].recMemNo+"&listType=R&readYN="+data[i].readYN+"'>"+data[i].title+"</a></td>";
                                        				
                                        				mailData += "</tr>";
                                        			}
                                        			
													for(var i=data.length; i<3; i++){
                                        				
														mailData += "<tr>";
                                        				
														mailData += "<td></td>";
														mailData += "<td></td>";
                                        				
														mailData += "</tr>";
                                        			}
                                        			
                                        			$('#mailBody').html(mailData);
                                        			
                                        		}else{
                                        			alert("마지막 페이지입니다.");
                                        			mailData--;
                                        		}
                                        	},
                                        	error : function(){
                                        		alert("서버 통신 오류");
                                        	}
                        	            });
                        			});
                        			
                        			$('#preMail').click(function(){
                        				mailPage--;
                        				
                        				$.ajax({
                        	            	url : "/moveMailPage.ho",
                        	            	data : {"mailPage":mailPage},
                                        	type : "get",
                                        	success : function(data){
                                        		if(data.length !=0){
                                        			var mailData;
                                        			
														for(var i=0; i<data.length; i++){
                                        				
                                        				mailData += "<tr>";
                                        				
                                        				mailData += "<td>"+data[i].sendDateFormat+"</td>";
                                        				mailData += "<td><a href='/mailView.ho?mailNo="+data[i].mailNo+"&other="+data[i].recMemNo+"&listType=R&readYN="+data[i].readYN+"'>"+data[i].title+"</a></td>";
                                        				
                                        				mailData += "</tr>";
                                        			}
                                        			
													for(var i=data.length; i<3; i++){
                                        				
														mailData += "<tr>";
                                        				
														mailData += "<td></td>";
														mailData += "<td></td>";
                                        				
														mailData += "</tr>";
                                        			}
                                        			
                                        			$('#mailBody').html(mailData);
                                        			
                                        		}else{
                                        			alert("첫 번째 페이지입니다.");
                                        			mailPage++;
                                        		}
                                        	},
                                        	error : function(){
                                        		alert("서버 통신 오류");
                                        	}
                        	            });
                        			});
                        			</script>
				        		</c:otherwise>
				        	</c:choose>
				        </div>
				    </div>
				    
				</div>
                
                <div class="pro-graph">
                    <div id="columnchart_values"></div>
                    <div class="pro-graph-count" style="width:25%;">
                        <span id="cTitle">이번달 프로젝트 수</span>
                        <span id="cValue">${monthlyList[0].monthlyCount}</span><!--4자리까지-->
                    </div>
                    
                    
                </div>
                
                <div class="role">
                    <div class="role-top">
                        <a href="#">사내규정</a>
                        <div id="roleBtn">
                            <button id="preRule"><i class="fas fa-chevron-left"></i></button>
                            <button id="nextRule"><i class="fas fa-chevron-right"></i></button>
                        </div>
                        <input type="hidden" name="rulePage" id="rulePage" value="1">
                    </div>
                    <div class="role-bottom">
                        <table id="role-data">
                        <thead id="ruleHead">
                            <tr>
                                <th>번호</th>
                                <th>제목</th>
                                <th>작성일</th>
                            </tr>
						</thead>
                            <c:choose>
                            
                            	<c:when test="${empty ruleList }">
                            		<tr>
                            			<td colspan="3">사내규정이 없습니다.</td>
                            		</tr>
                            	</c:when>
                            	<c:otherwise>
                            		<tbody id="ruleBody">
                            		<c:forEach items="${ruleList }" var="rl">
                            			<tr>
			                                <td>${rl.ruleNo }</td>
			                                <td><a href="#">${rl.ruleTitle }</a></td>
			                                <td><fmt:formatDate value="${rl.ruleDate }" pattern="yyyy-MM-dd"/></td>
			                            </tr>
                            		</c:forEach>
                            		</tbody>
                            		<script>
                        			var rulePage = $('#rulePage').val();
                        			
                        			$('#nextRule').click(function(){
                        				rulePage++;
                        				
                        				$.ajax({
                        	            	url : "/moveRulePage.ho",
                        	            	data : {"rulePage":rulePage},
                                        	type : "get",
                                        	success : function(data){
                                        		if(data.length !=0){
                                        			var ruleData;
                                        			
                                        			for(var i=0; i<data.length; i++){
                                        				
                                        				ruleData += "<tr>";
                                        				
                                        				ruleData += "<td>"+data[i].ruleNo+"</td>";
                                        				ruleData += "<td><a href='#'>"+data[i].ruleTitle+"</a></td>";
                                        				ruleData += "<td>"+data[i].ruleDateFormat+"</td>";
                                        				
                                        				ruleData += "</tr>";
                                        			}
                                        			
													for(var i=data.length; i<4; i++){
                                        				
                                        				ruleData += "<tr>";
                                        				
                                        				ruleData += "<td></td>";
                                        				ruleData += "<td></td>";
                                        				ruleData += "<td></td>";
                                        				
                                        				ruleData += "</tr>";
                                        			}
                                        			
                                        			$('#ruleBody').html(ruleData);
                                        			
                                        		}else{
                                        			alert("마지막 페이지입니다.");
                                        			rulePage--;
                                        		}
                                        	},
                                        	error : function(){
                                        		alert("서버 통신 오류");
                                        	}
                        	            });
                        			});
                        			
                        			$('#preRule').click(function(){
                        				rulePage--;
                        				
                        				$.ajax({
                        	            	url : "/moveRulePage.ho",
                        	            	data : {"rulePage":rulePage},
                                        	type : "get",
                                        	success : function(data){
                                        		if(data.length !=0){
                                        			var ruleData;
                                        			
                                        			for(var i=0; i<data.length; i++){
                                        				
                                        				ruleData += "<tr>";
                                        				
                                        				ruleData += "<td>"+data[i].ruleNo+"</td>";
                                        				ruleData += "<td><a href='#'>"+data[i].ruleTitle+"</a></td>";
                                        				ruleData += "<td>"+data[i].ruleDateFormat+"</td>";
                                        				
                                        				ruleData += "</tr>";
                                        			}
                                        			
													for(var i=data.length; i<4; i++){
                                        				
                                        				ruleData += "<tr>";
                                        				
                                        				ruleData += "<td></td>";
                                        				ruleData += "<td></td>";
                                        				ruleData += "<td></td>";
                                        				
                                        				ruleData += "</tr>";
                                        			}
                                        			
                                        			$('#ruleBody').html(ruleData);
                                        			
                                        		}else{
                                        			alert("첫 번째 페이지입니다.");
                                        			rulePage++;
                                        		}
                                        	},
                                        	error : function(){
                                        		alert("서버 통신 오류");
                                        	}
                        	            });
                        			});
                        			</script>
                            	</c:otherwise>
                            	
                            </c:choose>
                            
                        </table>
                    </div>
                </div>
                
                <div class="footer">
                </div>
                
            </div>
            
            <div class="content-r">
               
                <div class="nameCard">
                	<c:choose>
                    	<c:when test="${empty sessionScope.member.memProfile }">
                    		<i class="fas fa-user-circle nc-icon"></i>
                    	</c:when>
                    	<c:otherwise>
                    		<img class="nc-img" src="/resources/images/profile/${sessionScope.member.memProfile }">
                    		<script>
                    			$('.nc-img').click(function(){
                    				location.replace('/information.ho');
                    			});
                    		</script>
                    	</c:otherwise>
                    </c:choose>
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
                        <a href="/approvalList.ho?listType=W&aprStatus=W" class="nc-state">결재할 문서</a>
                        <a href="/approvalList.ho?listType=W&aprStatus=W" class="nc-cont">${alCount }</a>
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

<script type="text/javascript" src="/resources/js/header&sideNavi.js"></script>
<script src="/resources/js/memberMain/memberMain_cal.js"></script>
<script src="/resources/js/memberMain/memberMain_work.js"></script>
<script src="/resources/js/memberMain/memberMain_publicPro.js"></script>
<script src="/resources/js/memberMain/memberMain_mail.js"></script>
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
            [${monthlyList[4].monthly}+"월", ${monthlyList[4].monthlyCount}, "#595959"],
            [${monthlyList[3].monthly}+"월", ${monthlyList[3].monthlyCount}, "#595959"],
            [${monthlyList[2].monthly}+"월", ${monthlyList[2].monthlyCount}, "#595959"],
            [${monthlyList[1].monthly}+"월", ${monthlyList[1].monthlyCount}, "color: #595959"],
            [${monthlyList[0].monthly}+"월", ${monthlyList[0].monthlyCount}, "#595959"]
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