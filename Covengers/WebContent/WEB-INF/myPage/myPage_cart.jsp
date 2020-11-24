<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
   String ctxPath = request.getContextPath();
%>

<style type="text/css">
   div#container {
      /* border: solid 1px yellow; */
      width: 1200px;
      margin: 30px auto;
   }

   div#miniContainer {
/*       border: solid 1px red; */
      width: 700px;
      margin: 0 auto;
      text-align: center;
      display: inline-block;
   }

   span.head {
      margin: 0 60px;
      font-size: 15pt;
   }

   div.item {
      border: solid 2px #d6d6c2;
      width: 680px;
      height: 200px;
      display: inline-table;
   }

   div#allCheckBox, .individualCheck {
      float: left;
     margin-left: 10px; 
   }
   

   img {
      /*       border: solid 1px red; */
      width: 130px;
      height: 170px;
   }

   div.item_product {
      /*       border: solid 1px blue; */
      /*       float: left; */
      margin: 0 35px;
   }

   span.cost {
      /*       border: solid 1px red; */
      /*       float: left; */
      margin: 35px 35px;
      width: 80px;
      text-align: right;
   }

   input.poqty {
      /*       border: solid 1px red; */
      /*       float: left; */
      margin: 35px 55px;
      width: 80px;
      text-align: right;
   }

   .left {
      float: left;
      margin-left: 30px;
      
   }

   .productImg {
      float: left;
      /*    border: solid 1px red; */
   }
   
   div#rightDiv {
     position: fixed;
     top: 300px; 
     left: 1000px; 
        background-color: #f2f2f2;
        border-radius: 2%;
   }
  
   button.mybtn {
      border: none;
        margin: 10px 10px;
        padding: 7px 7px;
        background-color: black;
        color: white;
        border-radius: 5%;
   }
   
   button:hover {
        font-weight: bold;
   }
   
   button.mybtn2 {
      border: none;
        margin: 10px 10px;
        padding: 3px 5px;
        border-radius: 5%;
        background-color: white;
        color: black;
   }
   
   
    .order-con {
       list-style: none;
    }
      .order-wrap .order-con li:last-child {
         border-top: 1px solid #444;
   }
   .order-wrap .order-con li span:nth-child(2), span:last-child {
         float:right;
   }
   
   .order-wrap .order-con {
       padding: 0 10px;
       text-align: left;
   }
   
   .order-wrap .order-con li {
      margin: 10px 0;
   }
   
   li > span:nth-child(2) {
      float:right;
   }
   
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script type="text/javascript">

   $(document).ready(function () {

      calSumPrice();

      // === "이전 페이지로" 버튼을  누르면 직전 페이지로 넘어감.
      $("button#goBack").click(function() {

         history.back();

      });// end of $("button#goBack").click(function() {});----------------------

      // === 전체 선택/해제 체크박스를 클릭했을 때, 전체 선택/해제 체크박스가 체크되었다면 모든 체크박스를 체크하고 전체 선택/해제 체크박스가 해제되었다면 모든 체크박스를 해제함.  
      $("input#allCheck").click(function () {
         
         var bCheckboxState = $("input#allCheck").prop("checked");

         if (bCheckboxState) {
            $("input.individualCheck").prop("checked", true);
         } else {
            $("input.individualCheck").prop("checked", false);
         }
         
         calSumPrice();
         
      });// end of $("input#allCheck").click(function() {});--------------------------------


      // === 하위 체크박스에 체크가 1개라도 체크가 해제되면 체크박스 전체선택/전체해제 체크박스도 체크가 해제되고
      //     하위 체크박스에 체크가 모두 체크가 되어지지면  체크박스 전체선택/전체해제 체크박스도 체크가 되어지도록 함.=
      $("input.individualCheck").click(function () {

         var bCheckboxState = $(this).prop("checked");
         if (!bCheckboxState) {
            // 클릭한 체크박스가 해제된 상태라면 전체 선택/해제 체크박스를 해제 시킴.
            $("input#allCheck").prop("checked", false);
         } else {
            // 클릭한 체크박스가 체크된 상태라면 모든 개별 체크박스를 확인하고 모두 체크된 상태일 때, 전체 선택/해제 체크박스를 체크 시킴.
            var flag = true;
            $("input.individualCheck").each(function () {
               if (!($(this).prop("checked"))) {
                  flag = false;
               }
            });

            if (flag) {
               $("input#allCheck").prop("checked", true);
            }

         }// end of if (!bCheckboxState) {}else{}------------------------------------------

      });// end of $("input.individualCheck").click(function() {});-------------------------


      // === 수량이 적힌 input태그의 화살표를 클릭했을 때, 해당 수량에 맞게 합계 금액이 바뀌도록 함.
      $("input.poqty").click(function () {

        // 각 상품의 개당 가격을 잡아옴.
        var iPrice =  Number($(this).parent().find($("input.ipPrice")).val()); 
        
        if ($(this).val() <= 0) {
            $(this).val(1);
         }

         var pPrice = Number($(this).parent().find($("span.pPrice")).text());
         var poqty = $(this).val();

         console.log("di : " + (iPrice * poqty));
         console.log($(this).siblings("input.itotalPrice").val() );

         // hidden input 태그에 합계 가격을 집어 넣음.
         $(this).siblings("input.itotalPrice").val(iPrice * poqty);
         
         var totalPrice = $(this).siblings("input.itotalPrice").val();
         

         $(this).parent().find($("span.totalPrice")).text("￦"+comma(totalPrice));
         

         calSumPrice();

      });// end of $("input.poqty").click(function() {});-----------------------------------


      // === 수량이 적힌 input태그의 숫자를 바꾸었을 때, 해당 수량에 맞게 합계 금액이 바뀌도록 함.
      $("input.poqty").blur(function () {

       // 각 상품의 개당 가격을 잡아옴.
         var iPrice =  Number($(this).parent().find($("input.ipPrice")).val());
         
        if ($(this).val() <= 0) {
            $(this).val(1);
         }

        var pPrice = Number($(this).parent().find($("span.pPrice")).text());
         var poqty = $(this).val();
        
        // hidden input 태그에 합계 가격을 집어 넣음.
         $(this).siblings("input.itotalPrice").val(iPrice * poqty);
         
         var totalPrice = $(this).siblings("input.itotalPrice").val();
         

         $(this).parent().find($("span.totalPrice")).text("￦"+comma(totalPrice));
         
        
         calSumPrice();

      });// end of $("input.poqty").blur(function() {});-------------------------------------

      // === 수량이 적힌 input태그의 숫자를 바꾸었을 때, 해당 수량에 맞게 합계 금액이 바뀌도록 함.
      $("input.poqty").keyup(function (event) {

        // 각 상품의 개당 가격을 잡아옴.
         var iPrice =  Number($(this).parent().find($("input.ipPrice")).val());
         
        if (event.keyCode == 13) {
            if ($(this).val() <= 0) {
               $(this).val(1);
            }
        
        //  var pPrice = $(this).parent().find($("input.ipPrice")).val();
            var pPrice = Number($(this).parent().find($("span.pPrice")).text());
            var poqty = $(this).val();

         // hidden input 태그에 합계 가격을 집어 넣음.
            $(this).siblings("input.itotalPrice").val(iPrice * poqty);
            
            var totalPrice = $(this).siblings("input.itotalPrice").val();
            

            $(this).parent().find($("span.totalPrice")).text("￦"+comma(totalPrice));
            

            calSumPrice();
         }


      });// end of $("input.poqty").blur(function() {});-------------------------------------

      
      // === 체크박스를 클릭할 시, 체크박스에 체크된 항목들만 계산되어 합계 금액이 바뀌도록 함.
      $("input.individualCheck").bind("click",function() {
         calSumPrice();
     });
      

      // === 저장 버튼을 누르면 현재 상태의 수량과 가격이 장바구니 테이블에 저장되도록 함.
      $("button.save").click(function () {
         // alert("저장버튼을 누름");

         var poqty = $(this).parent().find($("input.poqty")).val();
         var cartno = $(this).parent().find($("input.cartno")).val();

         $.ajax({
            url: "<%= ctxPath%>/product/myCartSave.com",
            data: { "poqty": poqty, "cartno": cartno },
            type: "post"
         });

      });// end of $("button#save").click(function() {});----------------------------------- 

      
      // === "전체삭제" 버튼을 누르면 해당 회원의 모든 장바구니 데이터를 삭제하도록 함.
      $("button#allDelete").click(function () {

         location.href = "<%= ctxPath%>/product/myCartAllDelete.com";

      });// end of $("button#allDelete").click(function() {});------------------------------

      
      // === "선택삭제" 버튼을 누르면 체크박스가 체크된 항목들이 장바구니에서 삭제되도록 함.
      $("button#selectDelete").click(function () {

         var arrcartno = [];

         $("input.individualCheck").each(function (index, item) {

            if ($(this).prop("checked")) {
               //  console.log($(this).parent().find($("input.cartno")).val()); // $("div#container").find(".baseball")
               arrcartno.push($(this).parent().find($("input.cartno")).val());
            }

         });
         var scartno = arrcartno.join(',');
      //   console.log(scartno);

         $.ajax({
            url: "<%= ctxPath%>/product/myCartSelectDelete.com",
            data: { "scartno": scartno },
            type: "post",
         });

         location.reload();

      });

      // === "삭제" 버튼을 누르면 테이블에서 삭제
      $("button.individualDelete").click(function (event) {

         var cartno = $(this).parent().find($("input.cartno")).val();

         $.ajax({
            url: "<%= ctxPath%>/product/myCartDeleteOne.com",
            data: { "cartno": cartno },
            type: "post"
         });

         location.reload();

      });


      // === "선택 상품 결제하기" 버튼을 누르면 결제하기 창으로 form을 보내도록 함. 
      $("button#checkedCardSlash").click(function () {
         
        if ($("input:checkbox[class=individualCheck]:checked").length == 0) {
            alert("구매하실 상품을 체크해주세요!!");
        }else{
         
           var poqty = 0;
           var cartno = 0;
           // 모든 상품의 수량 상태를 DB상으로 update한다. 
            $("div.item").each(function() {

               poqty = $(this).find($("input.poqty")).val();
                 cartno = $(this).find($("input.cartno")).val();

                 $.ajax({
                    url: "<%= ctxPath%>/product/myCartSave.com",
                    data: { "poqty": poqty, "cartno": cartno },
                    type: "post"
                 });

           });
           
           // 배열 선언
           var arrcartno = [];
           var arroption = [];
           
           var cartno = 0;
           var option = 0;
           // 각 상품의 체크박스를 확인
           $("input.individualCheck").each(function () {
            
             // 체크되어진 상품들의 장바구니 번호(cartno)값을 배열에 넣음.
             // 체크되어진 상품들의 옵션코드(optionno)값을 배열에 넣음.
               if ($(this).prop("checked")) {
                  
                  cartno = $(this).parent().find($("input.cartno")).val().trim();
                  
                  if (cartno != "") {
                     arrcartno.push(cartno);
               }
                  
                  option = $(this).parent().find($("input.optionno")).val().trim();
                  
                  if (option != "") {
                     arroption.push(option);
               }
                  
               }
               
            });
            
           // 배열의 모든 요소를 ,구분자로 조인하여 문자열로 만듦
           var scartno = arrcartno.join(',');
           var soption = arroption.join(',');
           
           // 문자열 값을 purchasecartno태그의 값으로 넣음
           $("input[name=purchasecartno]").val(scartno); 
           $("input[name=purchaseoption]").val(soption); 
           
           var frm = document.cartForm;
            frm.action = "<%= ctxPath%>/payment/cardSlash.com";
            frm.method = "post";
             frm.submit();
      
        }
      });
      
      
      // === "전체 상품 결제하기" 버튼을 누르면 결제하기 창으로 form을 보내도록 함. 
      $("button#cardSlash").click(function () {
        
        // 모든 상품의 체크박스를 true로 바꿈. 
        $("input.individualCheck").each(function () {
             $(this).prop("checked", true);
         });
        
        // 전체 품목 합계, 배송비, 총합계를 계산
        calSumPrice();
         
        var poqty = 0;
        var cartno = 0; 
        // 모든 상품의 수량 상태를 DB상으로 update한다. 
        $("div.item").each(function() {

           poqty = $(this).find($("input.poqty")).val();
             cartno = $(this).find($("input.cartno")).val();

             $.ajax({
                url: "<%= ctxPath%>/product/myCartSave.com",
                data: { "poqty": poqty, "cartno": cartno },
                type: "post"
             });

       });
        
        // 배열 선언
       var arrcartno = [];
       var arroption = [];
       
       // 각 상품의 장바구니 번호를 배열에 넣음
       $("input.cartno").each(function() {
          if ( $(this).val().trim() != "" ) {
               arrcartno.push($(this).val().trim());
          }
       });
       
       // 각 상품의 옵션 번호를 배열에 넣음
       $("input.optionno").each(function() {
          if ( $(this).val().trim() != "" ) {
          arroption.push($(this).val().trim());
          }
       });
       
        
       // 배열의 모든 요소를 ,구분자로 조인하여 문자열로 만듦
       var scartno = arrcartno.join(',');
       var soption = arroption.join(',');
       
       // 문자열 값을 purchasecartno태그의 값으로 넣음
       $("input[name=purchasecartno]").val(scartno); 
       $("input[name=purchaseoption]").val(soption); 

       var frm = document.cartForm;
        frm.action = "<%= ctxPath%>/payment/cardSlash.com";
         frm.method = "post";
         frm.submit();
      
      });
     
   });// end of $(document).ready(function() {});--------------------------------------------


   // === 전제품목합계이 있는 span태그 부분에 모든 상품의 합계 금액을 더한 값이 들어가도록 하고
   // === 만일 전체품목합계액이 50,000원 이하일 때는 배송비가 2500원, 전체품목합계액이 50,000원 이상일 때는 배송비가 0원
   // === 총합계는 전체합계금액과 배송비가 더해진 값이 나오도록 함.
   function calSumPrice() {

     // 전체품목합계가 있는 span태그 부분에 체크한 모든 상품의 합계 금액을 더한 값이 들어가고
      var sumPrice = 0;
     
      $("input.individualCheck").each(function () {
         if ( $(this).prop("checked") ) {
         sumPrice += Number($(this).parent().find($("input.itotalPrice")).val());
      }
      });
     
      $("input[name=sumPrice]").val(sumPrice);
      
      $("span[id=sumPrice]").text(comma(sumPrice));
      
      // 만일 전체품목합계액이 50,000원 이하일 때는 배송비가 2500원, 전체품목합계액이 0원 이거나 50,000원 이상일 때는 배송비가 0원
      var deliveryCharge = 2500;

      if (sumPrice >= 300000 || sumPrice == 0) {
         deliveryCharge = 0;
      }
      $("span#deliveryCharge").text(comma(deliveryCharge));
      
      $("input[name=deliveryCharge]").val(deliveryCharge);

     
      // 총합계는 전체합계금액과 배송비가 더해진 값이 나오도록 함.
      $("span#totalCost").text(comma(sumPrice + deliveryCharge));
      
      $("input[name=totalCost]").val(sumPrice + deliveryCharge);

   }// end of function calSumPrice() {}------------------------------------

   // 콤마 찍기
   function comma(str) {
       str = String(str);
       return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
   }// end of function comma(str) {}---------------------------------------
   
   //콤마풀기
   function uncomma(str) {
       str = String(str);
       return str.replace(/[^\d]+/g, '');
   }// end of function uncomma(str) {}-------------------------------------
   
</script>

<jsp:include page="../headNavigation.jsp"></jsp:include>

<div id="container">
   <div id="miniContainer">
      <h1>장 바 구 니</h1>
      <hr style="border: solid 1px gray;">

      <!-- 장바구니에 들어있는 상품이 없으면 -->
      <c:if test="${cartList.size() == 0}">
         <span style="color: red; font-size: 20pt;">장바구니가 비었습니다.</span><br><br>
         <button id="goBack" type="button" class="btn btn-info">이전 페이지로</button>
      </c:if>

      <!--장바구니에 들어있는 상품이 있으면  -->
      <c:if test="${cartList.size() != 0}">

         <span class="head">제품</span><span class="head">가격</span><span class="head">수량</span><span
            class="head">합계</span>
         <hr style="width: 680px; border: solid 1px gray;">

         <div id="allCheckBox">
            <input type="checkbox" id="allCheck" name="allCheck" /> <label for="allCheck">전체 선택/해제</label>
         </div>
         <br>


            <c:forEach var="product" items="${cartList}">
               <div class="item">
                  <input type="checkbox" class="individualCheck"><br>
                  <span class="left">${product.krproductname}</span><br>
                  <div class="productImg">
                     <img class="left" src="../images/${product.productimg1}" />
                  </div>
                  
                  <input class="ipPrice" type="hidden" value="${product.pprice}" /> 
                  <span class="cost left pPrice">￦<fmt:formatNumber value="${product.pprice}" pattern="#,###" /></span>
                  
                  <input class="poqty left" type="number" min="1" value="${product.poqty}" />
                  
              <input class="itotalPrice" type="hidden" value="${product.totalprice}" />
                  <span class="cost left totalPrice">￦<fmt:formatNumber value="${product.totalprice}" pattern="#,###" /></span><br>
                  
                  <button type="button" class="individualDelete mybtn2">삭제</button>
                  <button type="button" class="save mybtn2">저장</button>
                  <input type="hidden" class="cartno" value="${product.cartno}" />
                  <input type="hidden" class="optionno" value="${product.fk_optioncode}" />
               </div>
               <br><br>

            </c:forEach>


         <div id="rightDiv" class="order-wrap">
            <form name="cartForm">
               <input type="hidden" name="purchasecartno" value="" />
               <input type="hidden" name="purchaseoption" value="" />
               <input type="hidden" name="sumPrice" value="" />
               <input type="hidden" name="deliveryCharge" value="" />
               <input type="hidden" name="totalCost" value="" />
            </form>
               <ul class="order-con">
               <li>
                  <span>전체 품목 합계</span>
                    <span id="sumPrice">0</span><span>￦</span>
                  
               </li>
               <li>
                  <span>배송비</span>
                 <span id="deliveryCharge">0</span><span>￦</span>
               </li>
               <li>
                  <span>총합계</span>
                  <span id="totalCost">0</span><span>￦</span>
               </li>
            </ul>
            <div class="order-btn">
               <button id="checkedCardSlash" type="button" class="mybtn">선택 상품 결제하기</button>
               <button id="cardSlash" type="button" class="mybtn">전체 상품 결제하기</button>
            </div>
            </div>
       
         <button id="selectDelete" type="button" class="left mybtn">선택삭제</button>
         <button id="allDelete" type="button" class="left mybtn">전체삭제</button>



      </c:if>
   </div> <!-- end of #miniContainer -->

</div>

<!-- <button type="button" class="btn btn-danger">Danger</button> -->





</body>

</html>