<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
   String ctxPath = request.getContextPath();
%>

<jsp:include page="../covengers_header.jsp"></jsp:include>
<style type="text/css">

   div#container {
      /* border: solid 1px yellow;  */
      width: 1200px;
      margin: 30px auto;
      text-align: center; 
    }
   
   div#miniContainer {
      /* border: solid 1px red; */ 
      width: 800px;
      margin: 0 auto;
      text-align: center;
      display: inline-block;
    }

   button.mybtn:hover {
           font-weight: bold;
    }
   
    button.mybtn {
         border: none;
           margin: 10px 10px;
           padding: 7px 7px;
           background-color: #eee;
           color: #444;
           border-radius: 5%;
           float: right;
      }
      
      div.board {
         background-color: #f2f2f2;
         width: 600px;
         height: 30px;
         cursor: pointer;
         margin: 0 auto;
         padding: 5px;
      }
      
      div.board:hover {
         background-color: #ccc;
      }
      
</style>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>

   $(document).ready(function() {
      
      
      // 문의 작성하기 버튼을 누르면 작성action으로 넘어감
      $("button#insertQuestion").click(function() {
         
         location.href="<%= ctxPath%>/board/qnaRegister.com";
         
      });// end of $("button#insertQuestion").click(function() {});----------------
      
      // 특정 게시글을 누르면 비밀번호를 입력하게 하여 해당 게시글의 비밀번호와 일치하면 detail 창으로 넘어가게 함
      $("div.board").click(function() {
         
         var selectNo = $(this).children("input[name=qnaNo]").val();
         // alert(selectNo);
         
         // 선택한 게시글의 게시글번호를 selectQnaNo이름의 input태그값에 넣는다. 
          location.href="<%= request.getContextPath()%>/board/qnaDetail.com?selectNo="+selectNo;
         
      });// end of $("div.board").click(function() {});----------------------------
      
      
   });// end of $(document).ready(function() {});-----------------------------------

</script>
<body>

   <div id="container">
      <div id="miniContainer">
      
         <h1>1 : 1 문의</h1>
            <hr style="border: solid 1px gray;">   
         
         <c:if test="${questionList.size() eq 0}">
            <h2>등록된 문의글이 없습니다.</h2>
         </c:if>
         <c:if test="${questionList.size() ne 0}">
            <c:forEach var="qna" items="${questionList}">
               <div class="board well" style="border:solid 1px gray; margin-bottom:10px;">

                  <c:forEach var="answer" items="${answerList}">
                     <c:if test="${answer eq qna.qnaNo}">
                        <span style="color: #e600ac;">♥답변완료♥</span>
                     </c:if>
                  </c:forEach>
               
                  <input name="qnaNo" type="hidden" value="${qna.qnaNo}" />
                  <span style="color: #cc66ff;">${qna.qUserName}</span>님의 문의입니다. &nbsp;&nbsp;&nbsp;
                  <span>작성일 : ${qna.qDateString}</span>
               </div>
            </c:forEach>
         </c:if>
   
         <div>${pageBar}</div>
         <button id="insertQuestion" type="button" class="mybtn">문의 작성하기</button>
      
      </div>
   </div>

</body>
</html>