<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>  
<c:set var="loginout" value="${sessionScope.id == null ? 'logout' : 'login'}" />
<c:set var="loginoutlink" value="${sessionScope.id==null ? '/login' : '/mypage'}" />
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>공지사항</title>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha2/dist/js/bootstrap.bundle.min.js" integrity="sha384-qKXV1j0HvMUeCBQ+QVp7JcfGl760yU08IQ+GpUo5hlbpg51QRiuqHAJz8+BrxE/N" crossorigin="anonymous"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-aFq/bzH65dt+w6FI2ooMVUpc+21e0SRygnTpmBvdBgSdnuTN7QbdgL+OapgHtvPp" crossorigin="anonymous">
    <link rel="stylesheet" href="${path}/resources/css/board/boardPost.css" >
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <style type="text/css">
    	input{
    		border: none;
    		outline: none;
    		background-color: #202020;
    		color: #fff;
    		width: 100%;
    		height: 100%;
    	}
    	
    	input[name='article_title']{
    		font-size: 26px;
    	}

/* 모달 */
.modal-content{
    background-color: #202020;
}

.modal-body{
    font-size: 26px;
    text-align: center;
    border: 1px solid #fff;
}

.modal-header{
    border: 1px solid #fff;
}

.modal-footer{
    border: 1px solid #fff;
    display: flex;
    justify-content: flex-end;
}
 
.modi-del{
	display: flex;
    justify-content: flex-end;
}


.qa-main p{

    font-weight: bold;
}

.btn{
    color: #fff;
    background-color: transparent;
    border-style: none;
    border-color: #fff; 
    font-size: 23px; 
    text-decoration: none;
}


.btn:hover{
    border-color: #33FF33;
    background-color: transparent;
    border-style: solid;
    color: #33FF33; 
}

/* 글목록 배너 */
.title-region{
    position: relative;
    margin: 20px 0;
}

.title-mainline{
    width: 1100px;
    height: 50px;
    border-bottom: 2.5px solid #fff;
    margin: 0 auto;
}

.title-mainline span{
    font-size: 23px;
    padding: 10px;
}

.title-line{
    width: 1100px;
    height: 500px;
    font-size: 20px;
    padding: 40px 0;
    border-bottom: 1.5px solid #fff;
    margin: 0 auto;
}

.qa-main{
    width: 1200px;
    height: 100%;
    margin: 0 auto;
    /* border: 1px solid red; */
}
    </style>
  </head>
 
<!--  body  --------------------->
  <body style="background-color: #202020;">
     
    <div class="wrap">
    	<%@ include file="../fix/header.jsp" %>

        <div id="line-1" >
          <%@ include file="../fix/nav.jsp" %>
        </div>
  


	<script type="text/javascript">
		$(document).ready(function() {
			let article_no = $("input[name=article_no]").val()
			
			$("#modifyBtn").on("click", function() {
				let form = $("#form")
				let isReadonly = $("input[name=article_title]").attr('readonly')
				
				if(isReadonly=='readonly'){
					$("input[name=article_title]").attr('readonly', false)
					$("textarea").attr('readonly', false)
					$("#modi").html("등록")
					$("#del").html("취소")
					
					// FileDTO가 없는 경우에 업로드 입력 필드를 생성
				        if (!$("input[name=FileDTO]").length) {
				            form.append('<input type="file" id="upFIle1" name="upfiles">업로드 1');
				            form.append('<input type="file" id="upFile2" name="upfiles">업로드 2');
        }
					return
				}
				
				form.attr("action", "<c:url value='/board/board/update${searchItem.queryString}' />")
				form.attr("method", "post")
				
				if(formCheck()){
					form.submit()
				}
			})
			
			$("#del").on("click", function() {
				if(!$("input[name=article_title]").attr('readonly')){
					let form = $("#form")
					form.attr("action", "<c:url value='/board/board/read${searchItem.queryString}' />")
					form.attr("method", "get")
					form.submit()
					return
				}
			})
			
			$("#removeBtn").on("click", function() {
				let form = $("#form")
				form.attr("action", "<c:url value='/board/board/delete${searchItem.queryString}' />")
				form.attr("method", "post")
				form.submit()
			})
			
			$("#listBtn").on("click", function() {
				location.href="<c:url value='/board/board${searchItem.queryString}' />"
			})
			
			$("#writeBtn").on("click", function() {
				let form = $("#form")
				form.attr("action", "<c:url value='/board/board/write' />")
				form.attr("method", "post")
				if(formCheck()){form.submit()}
			})
			

			
			let formCheck = function() {
				let form = document.getElementById("form")
				
				if(form.article_title.value==""){
					$(".body").html("제목을 입력해 주세요.")
		   	    	$('#Modal').modal('show');
					form.article_title.focus()
					return false
				}
				
				if(form.article_content.value==""){
					$(".body").html("내용을 입력해 주세요.")
		   	    	$('#Modal').modal('show');
					form.article_content.focus()
					return false
				}
				return true
			}
			
		
		})
	</script>
	
   	<script type="text/javascript">
	   	$(document).ready(function() {
	   	    let msg = "${msg}";
	   	    if(msg == 'MOD_OK') {
	   	        $(".body").html("수정이 완료되었습니다.");
	   	    	$('#Modal').modal('show');
	   	    } 
	   	    if(msg == 'MOD_ERR') {
	   	        $(".body").html("수정 실패했습니다. 다시 시도해주세요.");
	   	     	$('#Modal').modal('show');
	   	    }
	   	 	if(msg == 'WRT_ERR') {
	   	        $(".body").html("글이 등록되지 않았습니다. 다시 시도해주세요.");
	   	     	$('#Modal').modal('show');
	   	    }
	   	});
   	</script>
       	
      <!--글작성 관련 시작-->
      <div class="qa-main">
      
      <form action="" id="form" class="frm" method="post" enctype="multipart/form-data" >
      <div class="modi-del">
		<c:if test="${userDTO.admin.toString() == 'Y'}">
	        <button type="button" class="btn btn-secondary" id="modi" data-bs-toggle="modal" data-bs-target="#exampleModal">
	          수정
	        </button>
	        <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	          <div class="modal-dialog modal-dialog-centered">
	            <div class="modal-content">
	              <div class="modal-header">
	                <h1 class="modal-title fs-5" id="exampleModalLabel">알림</h1>
	                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	              </div>
	              <div class="modal-body" id="modal-bady1">
	                수정하시겠습니까?
	              </div>
	              <div class="modal-footer">
	                <button type="button" class="btn" data-bs-dismiss="modal" id="modifyBtn">Yes</button>
	                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">No</button>
	              </div>
	            </div>
	          </div>
	        </div>
	        <button type="button" class="btn btn-secondary" id="del" data-bs-toggle="modal" data-bs-target="#exampleModa2">
	          삭제
	        </button>
	        <div class="modal fade" id="exampleModa2" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	          <div class="modal-dialog modal-dialog-centered">
	            <div class="modal-content">
	              <div class="modal-header">
	                <h1 class="modal-title fs-5" id="exampleModalLabe2">알림</h1>
	                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	              </div>
	              <div class="modal-body">
	                삭제하시겠습니까?
	              </div>
	              <div class="modal-footer">
	                <button type="button" class="btn btn-primary"  data-toggle="modal" id="removeBtn">Yes</button>
	                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">No</button>
	              </div>
	            </div>
	          </div>
	        </div> 
	   	</c:if>     
       <c:if test="${mode == 'new'}">
	          <!-- Button trigger modal -->
	        <button type="button" class="btn btn-secondary" id="modi" data-bs-toggle="modal" data-bs-target="#exampleModal">
	          등록
	        </button>
	 
	        <!-- Modal -->
	        <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	          <div class="modal-dialog modal-dialog-centered">
	            <div class="modal-content">
	              <div class="modal-header">
	                <h1 class="modal-title fs-5" id="exampleModalLabel">알림</h1>
	                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	              </div>
	              <div class="modal-body" id="modal-bady1">
	                등록하시겠습니까?
	              </div>
	              <div class="modal-footer">
	                <button type="button" class="btn" data-bs-dismiss="modal" id="writeBtn">Yes</button>
	                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">No</button>
	              </div>
	            </div>
	          </div>
	        </div>
       	</c:if>
      
      	<div>
	       <button type="button" class="btn btn-outline-success" id="listBtn"><i class="fa-solid fa-list" style="background-color: #202020; margin: 3px;"></i>목록</button> 
	    </div>
	    </div>
        <!-- 글목록 배너-->
        <div class="title-region">
          <div class="title-mainline">
            <input type="hidden" name="article_no" value="${articleDTO.article_no}"/>
            <div style="display: flex; justify-content: space-between;">
            	<div>
            		<input type="text" name="article_title" value="${articleDTO.article_title}" ${mode=="new" ? "" : "readonly='readonly'" } placeholder="제목을 입력해주세요." style="width:900px;" />
            	</div>
            	<div style="font-size: 20px;"><fmt:formatDate value="${articleDTO.article_create_dt}" pattern="yyyy-MM-dd" type="date"/></div>
            </div>
          </div>
			<div class="title-mainline">
				<span>성별</span>&nbsp;&nbsp;
				<input class="form-check-input" type="radio" name="sex"  value="M"  id="rdo_m"  ${articleDTO.sex == 'M' ? 'checked' : ''}>
				<label class="" for="rdo_m">남</label>&nbsp;&nbsp;&nbsp;&nbsp;
				<input class="form-check-input" type="radio" name="sex"  value="F"	 id="rdo_f"  ${articleDTO.sex == 'F' ? 'checked' : ''}>
				<label class="" for="rdo_f">여</label>		 
			</div>
			<div class="title-mainline">
				<span>카테고리</span>&nbsp;&nbsp;
				<select class="" name="category" id="category" >
					<option value=""	>전체</option>
					<option value="news"		${articleDTO.category == 'news' ? 'selected' : ''}>뉴스</option>
					<option value="isu"	${articleDTO.category == 'isu' ? 'selected' : ''} >시사</option>
					<option value="sports"	${articleDTO.category == 'sports' ? 'selected' : ''}>스포츠</option>
				</select>
			</div>
			<div class="title-mainline" style="border: none;">
				<label class="btn" for="chk_1">
					<input type="checkbox"  name="baseballArray" value="A"	id="chk_1"><span>기아</span>
				</label>	
				<label class="btn" for="chk_2">
					<input type="checkbox" name="baseballArray" 	value="B"	id="chk_2">SSG
				</label>
				<label class="btn" for="chk_3">
					<input type="checkbox" name="baseballArray" 	value="C"	id="chk_3">삼성
				</label>
				<label class="btn" for="chk_4">
					<input type="checkbox"  name="baseballArray" 	value="D"	id="chk_4">한화
				</label>	
				<label class="btn" for="chk_5">
					<input type="checkbox" name="baseballArray" 	 value="E"	id="chk_5">LG
				</label>	
			 	<br/><br/><br/>
			</div>
		<div class="title-line">
       		<textarea name="article_content" ${mode=="new" ? "" : "readonly='readonly'" } placeholder="내용을 입력해주세요." style="background-color: #202020; width: 100%; height: 100%; color: #fff; border: none; outline: none;">${articleDTO.article_content}</textarea>
  		</div>
  			<span>
         		조회수: ${articleDTO.hit_count}
         	</span>
         	
         		<c:choose >
         			<c:when test="${mode =='new'}">
							<input 	type="file" 			id="upFIle1" 		name="upfiles">업로드 1
							<input 	type="file" 			id="upFile2" 		name="upfiles">업로드 2
         			</c:when>
         			<c:otherwise>
					        <c:forEach var="file" items="${fileList}">
					                <span>${file.file_name}</span>
									<c:url var="downloadUrl" value="/board/board/download">
				    						<c:param name="file_no" value="${file.file_no}" />
									</c:url>
					                <a href="${downloadUrl}" style="width: 70px">[다운로드]</a>
					                
									<c:url var="deletefile" value="/board/board/deletefile">
				    						<c:param name="file_no" value="${file.file_no}" />
									</c:url>
					                <a href="${deletefile}" style="width: 70px">[삭제]</a>
					                <div>
								        <p>${message}</p>
								    </div>
					        </c:forEach>
         			</c:otherwise>
         		</c:choose>
  				<c:if test="${mode !='new'}">
					<c:if test="${not empty articleDTO.prev_article_no}">
							<a href="/ottt/board/board/read?page=1&pageSize=10&option=&keyword=&article_no=${articleDTO.prev_article_no}">이전글 | <span>${articleDTO.prev_title}</span></a>
					</c:if>
					<c:if test="${not empty articleDTO.next_article_no}">
  							<a href="/ottt/board/board/read?page=1&pageSize=10&option=&keyword=&article_no=${articleDTO.next_article_no}">다음글 | <span>${articleDTO.next_title}</span></a>
  					</c:if>
  				</c:if>
        </div>
        </form>
        
      </div>
    </div>
    
    	<!-- Modal -->
	        <div class="modal fade" id="Modal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	          <div class="modal-dialog modal-dialog-centered">
	            <div class="modal-content">
	              <div class="modal-header">
	                <h1 class="modal-title fs-5" id="exampleModalLabel">알림</h1>
	                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	              </div>
	              <div class="modal-body body">
	              </div>
	              <div class="modal-footer">
	                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">확인</button>
	              </div>
	            </div>
	          </div>
	        </div>
	        
	        <script>
			    // 데이터베이스에서 가져온 값
			    var databaseValue = '${articleDTO.baseball}';
			
			    // 데이터베이스 값 분리
			    var checkedValues = databaseValue.split(",");
			
			    // 체크박스 선택
			    checkedValues.forEach(function(value) {
			        var checkbox = document.querySelector('input[name="baseballArray"][value="' + value + '"]');
			        if (checkbox) {
			            checkbox.checked = true;
			        }
			    });
			</script>
  </body>
</html> 

