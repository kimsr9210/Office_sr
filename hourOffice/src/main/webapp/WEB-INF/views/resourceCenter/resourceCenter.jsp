<%@page import="kr.or.houroffice.common.PageList"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.sql.Date"%>
<%@page import="kr.or.houroffice.member.model.vo.Member"%>
<%@page import="kr.or.houroffice.resource.model.vo.ResourceData"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
	<!-- 폰트어썸 -->
    <script src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>
    
    <!--jQuery CDN-->
    <script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
	
	<!-- CSS -->
	
	<link rel="stylesheet" type="text/css" href="/resources/css/header&sideNavi.css" />
</head>
<body>
<%
	String resourceType = (String)request.getAttribute("resourceType");
	PageList pl = (PageList)request.getAttribute("pl");
	String pageNavi = pl.getPageNavi();
	ArrayList<ResourceData> fileList = pl.getList();
	ArrayList<Member> allMember = (ArrayList<Member>)request.getAttribute("allMember");
	
	Member m = (Member)session.getAttribute("member");
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
%>
<style>
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
#TitleName{
	font-family: GongGothicMedium;
}
	#fileBox{
		width: 1100px;
		height: 600px;
		margin: 0 auto;
	}
	
	#fileDetail{
		width: 100%;
		height: 100%;
		border: 1px solid #cccccc;
		border-radius: 5px;
	}
	#fileNaviBox{
		width: 20%;
		height: 100%;
		float: left;
		border-right: 1px solid #cccccc;
	}
	#fileContentsBox{
		width: 80%;
		height: 100%;
		float: left;
	}
	#naviName{
		width: 100%;
		height : 50px;
		padding: 15px 15px 10px 15px;
		font-size: 1rem;
		color: #999999;
	}
	#fileNavi{
		padding : 10px;
	}
	
	#fileNavi>li{
		padding :5px;
	}
	#fileNavi>li>a{
		text-decoration: none;
		color: black;
	}
	
	#fileNavi>li:hover{
		background-color: #F7F7F7;
	}
	#contentsName{
		width: 100%;
		padding: 20px 0px 29px 0px;
		border-bottom: 1px solid #cccccc;
	}
	#contentsName>li{
		float: left;
		text-align: center;
		font-weight: bolder;
	}
	
	#contentsName>li>a{
		text-decoration: none;
		color: black;
	}
	
	#contentsName>li:nth-child(1){
		width: 5%;
	}
	#contentsName>li:nth-child(2){
		width: 40%;
	}
	#contentsName>li:nth-child(3){
		width: 10%;
	}
	#contentsName>li:nth-child(4){
		width: 10%;
	}
	#contentsName>li:nth-child(5){
		width: 15%;
	}
	#contentsName>li:nth-child(6){
		width: 10%;
	}
	#contentsName>li:nth-child(7){
		width: 10%;
	}
	
input[type="checkbox"]{
  border: 0;
  clip: rect(0 0 0 0);
  height: 1px;
  margin: -1px;
  overflow: hidden;
  padding: 0;
  position: absolute;
  width: 1px;
}
input[type="checkbox"]:hover + label:before{
  border-color: #999;
}
input[type="checkbox"]:active + label:before{
  transition-duration: 0;
  filter: brightness(0.2);
}
input[type="checkbox"] + label{
  position: relative;
  padding-left: 26px;
  font-weight: normal;
}
input[type="checkbox"] + label:before, input[type="checkbox"] + label:after{
  box-sizing: content-box;
  position: absolute;
  content: '';
  display: block;
  left: 0;
}
input[type="checkbox"] + label:before{
  top: 50%;
  width: 16px;
  height: 16px;
  margin-top: -10px;
  border: 2px solid #d9d9d9;
  text-align: center;
}
input[type="checkbox"] + label:after{
  background-color: #00bad2;
  top: 50%;
  left: 6px;
  width: 8px;
  height: 8px;
  margin-top: -4px;
  transform: scale(0);
  transform-origin: 50%;
  transition: transform 200ms ease-out;
}

input[type="checkbox"] + label:after {
  background-color: transparent;
  top: 50%;
  left: 5px;
  width: 7px;
  height: 4px;
  margin-top: -5px;
  border-style: solid;
  border-color: #00bad2;
  border-width: 0 0 3px 3px;
  -moz-transform: rotate(-45deg) scale(0);
  -ms-transform: rotate(-45deg) scale(0);
  -webkit-transform: rotate(-45deg) scale(0);
  transform: rotate(-45deg) scale(0);
  -moz-transition: none;
  -o-transition: none;
  -webkit-transition: none;
  transition: none;
}
input[type="checkbox"]:checked + label:before {
  -moz-animation: borderscale 200ms ease-in;
  -webkit-animation: borderscale 200ms ease-in;
  animation: borderscale 200ms ease-in;
}
input[type="checkbox"]:checked + label:after {
  content: '';
  -moz-transform: rotate(-45deg) scale(1);
  -ms-transform: rotate(-45deg) scale(1);
  -webkit-transform: rotate(-45deg) scale(1);
  transform: rotate(-45deg) scale(1);
  -moz-transition: -moz-transform 200ms ease-out;
  -o-transition: -o-transform 200ms ease-out;
  -webkit-transition: -webkit-transform 200ms ease-out;
  transition: transform 200ms ease-out;
}



/* IE 10/11+ - This hides native dropdown button arrow so it will have the custom appearance, IE 9 and earlier get a native select - targeting media query hack via http://browserhacks.com/#hack-28f493d247a12ab654f6c3637f6978d5 - looking for better ways to achieve this targeting */

@media screen and (-ms-high-contrast: active), (-ms-high-contrast: none) {
  select::-ms-expand {
    display: none;
  }

  select:focus::-ms-value {
    background: transparent;
    color: grey;
  }
}
	
	
	.fileList{
		width: 100%;
		padding: 20px 0px 29px 0px;
		border-bottom: 1px solid #cccccc;
	}
	
	.fileList>li{
		float: left;
		text-align: center;
	}
	
	.fileList>li>a{
		text-decoration: none;
		color: black;
	}
	
	.fileList>li:nth-child(1){
		width: 5%;
	}
	.fileList>li:nth-child(2){
		width: 40%;
		text-align: left;
	}
	.fileList>li:nth-child(3){
		width: 10%;
	}
	.fileList>li:nth-child(4){
		width: 10%;
	}
	.fileList>li:nth-child(5){
		width: 15%;
	}
	.fileList>li:nth-child(6){
		width: 10%;
	}
	.fileList>li:nth-child(7){
		width: 10%;
	}
	#uploadBtn{
		width: 80px;
		height: 30px;
		line-height: 30px;
		background-color: white;
		text-align: center;
		cursor: pointer;
		border: 1px solid #cccccc;
		border-radius: 5px;
		font-size: 0.875rem;
		box-shadow: 3px 3px 5px #cccccc;
		color: #1D9F8E;
		font-weight: 800;
		margin-bottom: 10px;
	}
	
	#uploadBox{
		position: absolute;
		top:100px;
		left:500px;
		width: 80px;
		height: 80px;
		border: 1px solid black;
		background-color: yellow;
		z-index: 1;
		display: none;
	}
	
	.modal_layer{
		width: 100%;
		height: 100%;
        background:rgba(0, 0, 0, 0.5);
        z-index:-1;
	}
	/* 모달 */
        #modal {
          display: none;
          position:relative;
          width:100%;
          height:100%;
          z-index:1;
        }
        #modal .modal-content {
          width:600px;
          margin:230px auto;
          background:#fff;
          border:1px solid #cccccc;
          border-radius: 5px;
          box-shadow: 3px 3px 5px rgba(63,0,153,30%);
        }
        #modal .modal-layer {
          position:fixed;
          top:0;
          left:0;
          width:100%;
          height:100%;
          background:rgba(0, 0, 0, 0.5);
          z-index:-1;
        }   
        
        #uploadHeader{
        	width: 100%;
        	height: 40px;
        	line-height: 40px;
        	text-align: center;
        	background-color: #c8c8c8;
        	border:0;
        	border-bottom: 1px solid #B3B3B3;
        	border-top-left-radius: 5px;
        	border-top-right-radius: 5px;
        	font-size: 1rem;
        	font-weight: bolder;
        }
        
        #modalExit{
        	cursor: pointer;
        	float: right;
        	padding: 15px 20px 0 0;
        }
        
        #selectBox{
        	width: 100%;
        	height: 40px;
        	text-align: right;
        	padding-top: 5px;
        	background-color: #c8c8c8;
        }
        
        #uploadSubmit{
        	width : 70px;
        	height: 30px;
        	background-color: #1D9F8E;
        	color: white;
        	outline: none;
        	border: 1px solid #1D9F8E;
        	border-radius: 3px;
        	cursor: pointer;
        }
        
        #categoryBox{
        	outline: none;
        }
        
        #fileUpload{
        	display: none;
        }
        
        .fileUploadImg{
        	border: 1px solid black;
        	background-color: #737373;
        	color: white;
        	padding: 5px 10px 5px 10px;
        	border-radius: 5px;
        	border:0;
        	cursor: pointer;
        }
        .fileUploadImg:hover{
        	background-color: #8C8C8C;
        }
        
        
        .uploadContents{
			width: 100%;
			min-height: 40px;
			line-height: 40px;
			overflow: hidden;
        }
        .uploadFile{
			width: 100%;
        }
        
        .uploadFile>li{
			text-align: center;
			float: left;
        }
        
        .uploadFile>li:nth-child(1){
        	width: 25%;
        	margin: 0 auto;
        }
        .uploadFile>li:nth-child(2){
        	width: 25%;
        }
        .uploadFile>li:nth-child(3){
        	width: 20%;
        }
        .uploadFile>li:nth-child(4){
        	width: 10%;
        }
        .uploadFile>li:nth-child(5){
        	width: 20%;
        }
        
        #img{
        	max-width: 100%;
        	max-height: 100%;
        	padding: 10px;
        }
        #fileUp{
        	height: 100px;
        	line-height: 100px;
        }
        
        .fileList>li>svg{
	    	color: #47C83E;
	    	font-size: 1.5rem;
   		}
   		
   		#pageNaviBox{
   			padding-top: 10px;
   			text-align: center;
   			margin: 0 auto;
   		}
   		
   		#pageNaviBox>a{
   			color: black;
   			text-decoration: none;
   		}
	
		
		.downloadBtn{
			width: 70%;
			background-color: #1D9F8E;
			margin: 0 auto;
			color: white;
			border-radius: 3px;
			cursor: pointer;
		}
		
		.deleteBtn{
			width: 70%;
			background-color: #FF6363;
			margin: 0 auto;
			color: white;
			border-radius: 3px;
			cursor: pointer;
		}
		
		
		<%if(resourceType.equals("all")){%>
		#fileNavi>li:nth-child(1){
			background-color: #F2F2F2;
		}
	<%} else if(resourceType.equals("pc")){%>
		#fileNavi>li:nth-child(2){
			background-color: #F2F2F2;
		}
	<%} else if(resourceType.equals("img")){%>
		#fileNavi>li:nth-child(3){
			background-color: #F2F2F2;
		}
		<%} else if(resourceType.equals("video")){%>
		#fileNavi>li:nth-child(4){
			background-color: #F2F2F2;
		}
		<%} else if(resourceType.equals("audio")){%>
		#fileNavi>li:nth-child(5){
			background-color: #F2F2F2;
		}
		<%} else if(resourceType.equals("driver")){%>
		#fileNavi>li:nth-child(6){
			background-color: #F2F2F2;
		}
		<%} else if(resourceType.equals("network")){%>
		#fileNavi>li:nth-child(7){
			background-color: #F2F2F2;
		}
		<%} else if(resourceType.equals("software")){%>
		#fileNavi>li:nth-child(8){
			background-color: #F2F2F2;
		}
		<%} else if(resourceType.equals("font")){%>
		#fileNavi>li:nth-child(9){
			background-color: #F2F2F2;
		}
		<%}%>
</style>

	<div id="wrap">
		<%@ include file="/WEB-INF/views/common/header.jsp" %>
		<div id="contentsBox">
			<%@ include file="/WEB-INF/views/common/sideNavi.jsp" %>

			<div id="contents">
				<div id="contentsDetail" class="clearfix">
					<div id="TitleName">
						<!--여기서 각자 id 만드시면 됩니다-->
						자료실
						<!----------------------------------->
					</div>
					<div id="TitleContents">
						<div id="fileBox">
							<div id="uploadBtn">업로드</div>
							<div id="fileDetail">
								<div id="fileNaviBox">
									<div id="naviName">카테고리</div>
									<ul id="fileNavi">
										<li><a href="/resourceCenter.ho?resourceType=all"><i class="far fa-folder"></i>&nbsp;&nbsp;전체</a></li>
										<li><a href="/resourceCenter.ho?resourceType=pc"><i class="far fa-folder"></i>&nbsp;&nbsp;PC/관리/보관</a></li>
										<li><a href="/resourceCenter.ho?resourceType=img"><i class="far fa-folder"></i>&nbsp;&nbsp;이미지/그래픽</a></li>
										<li><a href="/resourceCenter.ho?resourceType=video"><i class="far fa-folder"></i>&nbsp;&nbsp;동영상</a></li>
										<li><a href="/resourceCenter.ho?resourceType=audio"><i class="far fa-folder"></i>&nbsp;&nbsp;오디오</a></li>
										<li><a href="/resourceCenter.ho?resourceType=driver"><i class="far fa-folder"></i>&nbsp;&nbsp;드라이버</a></li>
										<li><a href="/resourceCenter.ho?resourceType=network"><i class="far fa-folder"></i>&nbsp;&nbsp;인터넷/네트워크</a></li>
										<li><a href="/resourceCenter.ho?resourceType=software"><i class="far fa-folder"></i>&nbsp;&nbsp;소프트웨어</a></li>
										<li><a href="/resourceCenter.ho?resourceType=font"><i class="far fa-folder"></i>&nbsp;&nbsp;폰트</a></li>
									</ul>
								</div>
							
							<div id="fileContentsBox">
								<ul id="contentsName">
									<li>&nbsp;
									</li>
									<li>파일명</li>
									<li>크기</li>
									<li>등록자</li>
									<li>업로드 날짜</li>
									<li>다운로드</li>
									<li>파일삭제</li>
								</ul>
								
								<!-- 얘가 여러개 -->
        						<%for(ResourceData rd : fileList){ %>
        						<%
        							String name="알 수 없음";
	        						Date fileDate = new Date(rd.getUploadTime().getTime());
	                            	String date = sdf.format(fileDate);
        							for(Member member : allMember){
        								if(member.getMemNo()==rd.getMemNo()){
        									name = member.getMemName();
        								}
        							}
        						%>
								<ul class="fileList">
									<li>&nbsp;
									</li>
									<li>
									<%if(rd.getOriginalFileName().contains(".png")==true || rd.getOriginalFileName().contains(".jpg")==true){ %>
                            		<i class="far fa-image"></i>
                            		<%} else if(rd.getOriginalFileName().contains(".pdf")==true){%>
                            		<i class="far fa-file-pdf"></i>
                            		<%} else if(rd.getOriginalFileName().contains(".zip")==true || rd.getOriginalFileName().contains(".jar")==true || rd.getOriginalFileName().contains(".7z")==true || rd.getOriginalFileName().contains(".iso")==true || rd.getOriginalFileName().contains(".egg")==true){ %>
                            		<i class="far fa-file-archive"></i>
                            		<%} else{%>
                            		<i class="far fa-file"></i>
                            		<%} %>
									<%=rd.getOriginalFileName() %></li>
									<li><%=rd.getFileSize() %> KB</li>
									<li><%=name %></li>
									<li><%=date %></li>
									<li>
									<form action="/resourceFileDownload.ho" method="post">
									<div class="downloadBtn">다운로드</div>
									<input type="hidden" name="fileNo" value="<%=rd.getFileNo() %>"/>
									</form>
									</li>
									<li>
									<%if(m.getMemNo()==rd.getMemNo()){ %>
									<form action="/deleteResourceFile.ho" method="post">
									<div class="deleteBtn">파일삭제</div>
									<input type="hidden" name="fileNo" value="<%=rd.getFileNo() %>"/>
									<%} %>
									</form>
									</li>
								</ul>
								<%} %>
								<div id="pageNaviBox">
								<%=pageNavi %>
								</div>
							</div>
							
								
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div id="modal">
        <div class="modal-content">
        	<div id="uploadHeader">파일 업로드
	        	<span id="modalExit">
					<i class="fas fa-times"></i>
				</span>
			</div>
        	<form id="fileForm" action="/insertResource.ho" method="post"  enctype="multipart/form-data">
        	<div id="selectBox">
        			<select id="categoryBox" name="category">
        				<option value="pc">PC/관리/보관</option>
        				<option value="img">이미지/그래픽</option>
        				<option value="video">동영상</option>
        				<option value="audio">오디오</option>
        				<option value="driver">드라이버</option>
        				<option value="network">인터넷/네트워크</option>
        				<option value="software">소프트웨어</option>
        				<option value="font">폰트</option>
        			</select>
        			<button id="uploadSubmit" type="submit">업로드</button>
        	</div>
        	
        	<div class="uploadContents" style="border-bottom:1px solid #cccccc;">
        		<ul class="uploadFile">
        			<li>파일 미리보기</li>
        			<li>파일명</li>
        			<li>크기</li>
        			<li>확장자</li>
        			<li></li>
        		</ul>
        	</div>
        	
        	<div class="uploadContents">
        		<ul id="fileUp" class="uploadFile">
        			<li><img id="img"/></li>
        			<li id="name">&nbsp;</li>
        			<li id="size">&nbsp;</li>
        			<li id="extensions">&nbsp;</li>
        			<li>
        				<input type="file" name="file" id="fileUpload"/>
        				<label for="fileUpload" class="fileUploadImg">파일 선택</label>
        			</li>
        		</ul>
        	</div>
        	</form>
        </div>
        <div class="modal-layer"></div>
    </div>
	<!-- 자바 스크립트    -->
	<script>
		$(function(){
			$('#categoryResource').children().last().children().attr('class',
					'fas fa-chevron-left');
			
			$('#uploadBtn').click(function(){
				$('#modal').show();
			});
			
			//이미지 미리보기
			$('#fileUpload').on("change", handleImgFileSelect);
			
			function handleImgFileSelect(e) {
				
				var files = e.target.files;
				var filesArr = Array.prototype.slice.call(files);
				
				filesArr.forEach(function(f) {
					if(!f.type.match("image.*")){
						$('#img').attr("src", "/resources/images/preview.png");
						return;
					}
					
					sel_file = f;
					var reader = new FileReader();
					reader.onload = function(e){
						$('#img').attr("src", e.target.result);
					}
					reader.readAsDataURL(f);
				})
			}
			//$(this)[0].files[0].name
			$('#fileUpload').change(function(){
				var extensions = $(this)[0].files[0].name.split(".");
				$('#name').text($(this)[0].files[0].name);
				$('#size').text($(this)[0].files[0].size+" KB");
				$('#extensions').text(extensions[1]);
			});
			$('#modalExit').click(function(){
				$('#modal').hide();
			});
			
			//파일 다운로드
			$('.downloadBtn').click(function(){
				$(this).parent().submit();
			});
			//파일 삭제
			$('.deleteBtn').click(function(){
				var result = window.confirm("해당 파일을 삭제하시겠습니까?");
				if(result){
					$(this).parent().submit();
				}
			});
		});
	</script>
	<script type="text/javascript" src="/resources/js/header&sideNavi.js"></script>

</body>
</html>