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
	
	<!-- 헤더 & 네비 CSS -->
	<link rel="stylesheet" type="text/css" href="/resources/css/header&sideNavi.css" />
	<!-- 관리자 탭 공통 CSS -->
	<link rel="stylesheet" type="text/css" href="/resources/css/admin_tap/adminTapCommon.css" />
	<!-- 게시글 쓰기 공통 CSS -->
	<link rel="stylesheet" type="text/css" href="/resources/css/board/postWrite.css" />
	
	<!-- 스마트에디터2 라이브러리 -->
    <script type="text/javascript" src="/resources/api/smarteditor2/js/service/HuskyEZCreator.js" charset="utf-8"></script> 
    <!-- api 이미지 업로드 라이브러리 추가 -->
    <!-- <script type="text/javascript" src="./quick_photo_uploader/plugin/hp_SE2M_AttachQuickPhoto.js" charset="utf-8"> </script> -->
   
    <script>
        $(function(){
            // 취소버튼 클릭 이벤트
            $('.delBtn').click(function(){
                history.go(-1);
            });
        })
    </script>
    
    
	
</head>
<body>
	<div id="wrap">
		<%@ include file="/WEB-INF/views/common/header.jsp" %>
		<div id="contentsBox">
			<%@ include file="/WEB-INF/views/common/sideNavi.jsp" %>

			<div id="contents">
				<div id="contentsDetail" class="clearfix">
					<div id="TitleName">
						<!--여기서 각자 id 만드시면 됩니다-->
						${deptName } 게시판 <span>글쓰기</span>
						<!----------------------------------->
					</div>
					<div id="TitleContents">
						<!--여기서 각자 id 만드시면 됩니다-->
						
						
						<div id="txt-content">
                            <form id="frm" action="/savePostPartBoard.ho" method="post" enctype="multipart/form-data">
                            <div><span>제목</span> <input type="text" name="partTitle"/></div>
                            <div><span>첨부파일</span> <div id="attachedFile"></div><input type="file" name="attachedFile" style="display:none"/></div>
                            
                            <!-- 표시할 textarea 영역 -->
                            <textarea name="partContent" id="txtArea" required></textarea>
                                <div></div>
                            <div><button id="save-btn">저장</button> <button type="button" class="delBtn">취소</button></div>
                            </form>
                        </div>
						
						
	<!-- smartEditor2 api 페이지 로딩시 초기화 -->
	<script>
		$(function(){
		    //전역변수
		    var obj = [];              
		    //스마트에디터 프레임생성
		    nhn.husky.EZCreator.createInIFrame({
		        oAppRef: obj,
		        elPlaceHolder: "txtArea",
		        sSkinURI: "/resources/api/smarteditor2/SmartEditor2Skin.html",
		        fCreator : "createSEditor2", 
		        htParams : {
		            bUseToolbar : true, // 툴바 사용 여부 (true:사용/ false:사용하지 않음)            
		            bUseVerticalResizer : false, // 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
		            bUseModeChanger : true // 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
		        }
		    });
		    //전송버튼
		    $("#save-btn").click(function(){
		        //id가 smarteditor인 textarea에 에디터에서 대입
		        obj.getById["txtArea"].exec("UPDATE_CONTENTS_FIELD", []);
		        //폼 submit
		        $("#frm").submit();
		    });
		    
		    $('#attachedFile').click(function(){
		    	$(this).next().click(); // input 태그
		    });
		    
		    $('input[type=file]').on("change",handlefileSelect);
		    function handlefileSelect(e){
		    	var files = e.target.files;
		    	var filesArr = Array.prototype.slice.call(files);
		    	
		    	filesArr.forEach(function(f){
		    		var reader = new FileReader();
		    		reader.onload = function(e){
		    			$('#attachedFile').html('<div>'+f.name+'<span>X</span></div>');
		    		}
		    		reader.readAsDataURL(f);
		    	});
		    }
		})
	</script>		
						
	
						
						<!----------------------------------->
					</div>
				</div>
			</div>
		</div>

	<!-- 자바 스크립트    -->
	<script type="text/javascript" src="/resources/js/header&sideNavi.js"></script>
	
	
	
	</div>
</body>
</html>

