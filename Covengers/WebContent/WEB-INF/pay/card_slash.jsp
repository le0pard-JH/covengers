<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
  String ctxPath = request.getContextPath();
%>

<style type="text/css">
    .payment-form {
        padding-bottom: 50px;
        font-family: 'Montserrat', sans-serif;
    }

    .payment-form.dark {
        background-color: #f6f6f6;
    }

    .payment-form .content {
        box-shadow: 0px 2px 10px rgba(0, 0, 0, 0.075);
        background-color: white;
    }

    .payment-form .block-heading {
        padding-top: 50px;
        margin-bottom: 40px;
        text-align: center;
    }

    .payment-form .block-heading p {
        text-align: center;
        max-width: 420px;
        margin: auto;
        opacity: 0.7;
    }

    .payment-form.dark .block-heading p {
        opacity: 0.8;
    }

    .payment-form .block-heading h1,
    .payment-form .block-heading h2,
    .payment-form .block-heading h3 {
        margin-bottom: 1.2rem;
        color: #3b99e0;
    }

    .payment-form form {
        border-top: 2px solid #5ea4f3;
        box-shadow: 0px 2px 10px rgba(0, 0, 0, 0.075);
        background-color: #ffffff;
        padding: 0;
        max-width: 600px;
        margin: auto;
    }

    .payment-form .title {
        font-size: 1em;
        border-bottom: 1px solid rgba(0, 0, 0, 0.1);
        margin-bottom: 0.8em;
        font-weight: 600;
        padding-bottom: 8px;
    }

    .payment-form .products {
        background-color: #f7fbff;
        padding: 25px;
    }

    .payment-form .products .item {
        margin-bottom: 1em;
    }

    .payment-form .products .item-name {
        font-weight: 600;
        font-size: 0.9em;
    }

    .payment-form .products .item-description {
        font-size: 0.8em;
        opacity: 0.6;
    }

    .payment-form .products .item p {
        margin-bottom: 0.2em;
    }

    .payment-form .products .price {
        float: right;
        font-weight: 600;
        font-size: 0.9em;
    }

    .payment-form .products .total {
        border-top: 1px solid rgba(0, 0, 0, 0.1);
        margin-top: 10px;
        padding-top: 19px;
        font-weight: 600;
        line-height: 1;
    }

    .payment-form .card-details {
        padding: 25px 25px 15px;
    }

    .payment-form .card-details label {
        font-size: 12px;
        font-weight: 600;
        margin-bottom: 15px;
        color: #79818a;
        text-transform: uppercase;
    }

    .payment-form .card-details button {
        margin-top: 0.6em;
        padding: 12px 0;
        font-weight: 600;
    }

    .payment-form .date-separator {
        margin-left: 10px;
        margin-right: 10px;
        margin-top: 5px;
    }

    @media (min-width: 576px) {
        .payment-form .title {
            font-size: 1.2em;
        }

        .payment-form .products {
            padding: 40px;
        }

        .payment-form .products .item-name {
            font-size: 1em;
        }

        .payment-form .products .price {
            font-size: 1em;
        }

        .payment-form .card-details {
            padding: 40px 40px 30px;
        }

        .payment-form .card-details button {
            margin-top: 2em;
        }
    }

    .border-none {
        border: none;
        width: 100px;
    }

</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>


<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
<link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">

<script type="text/javascript">
    $(document).ready(function() {

        console.log("START");
        
        console.log( "spCartNo : " + "${ spCartNo }" );
        console.log( "spOptionNo : " + "${ spOptionNo }" );
        console.log( "sumPrice : " + "${ sumPrice }" );
        console.log( "deliveryCharge : " + "${ deliveryCharge }" );
        console.log( "totalCost : " + "${ totalCost }" );
        
        console.log("END");

        var scartno = "${ spCartNo }";
        var arrcartno = [];
        var total_amount = 0;
        var arrproduct_code = [];
        var sproduct_code = "";
        var arrpprice = [];
        var spprice = "";
        var arrpoqty = [];
        var spoqty = null;
        var arroption_code = [];
        var soption_code = "";
        var arrfcartno = [];
        var sfcartno = "";

        /////////////////////////////////////////////////
        // cartno

        arrcartno = scartno.split(',');

        $("input.price").each(function() {
            if ($(this).val() != '') {
                total_amount += Number($.trim($(this).val()));
            }
        });

        /////////////////////////////////////////////////
        // product_code
		$("input[name=product_code]").each(function() {
			console.log($(this).val());
            if ($(this).val() != '') {
                arrproduct_code.push($.trim($(this).val()));
            }
        });

        sproduct_code = arrproduct_code.join(',');
        console.log(sproduct_code);

        /////////////////////////////////////////////////
        // pprice

        $('.price').each(function() {
			var num_price = $(this).text().replace(/[^0-9]/g,'');
            if ($(this).text() != '') {
                arrpprice.push($.trim(num_price));
            }
        });

        spprice = arrpprice.join(',');
        console.log(spprice);

        /////////////////////////////////////////////////
        // poqty

        $('.item-description').each(function() {
        	var num_qty = $(this).text().replace(/[^0-9]/g,'');
            if (num_qty != null) {
                arrpoqty.push($.trim(num_qty));
                //console.log(num_qty);
            }
        });

        spoqty = arrpoqty.join(',');
        
        //console.log(spoqty);

        /////////////////////////////////////////////////
        // option_code

        $('input[name=option_code]').each(function() {
            if ($(this).val() != '') {
                arroption_code.push($.trim($(this).val()));
            }
        });

        soption_code = arroption_code.join(',');

        /////////////////////////////////////////////////
        // fcartno

        $('input[name=cartno]').each(function() {
            if ($(this).val() != '') {
                arrfcartno.push($.trim($(this).val()));
            }
        });

        sfcartno = arrfcartno.join(',');

        /////////////////////////////////////////////////

        $("button#cardSlash").click(function() {
        	alert(${ totalCost });
            goCardSlashEnd('${sessionScope.loginuser.userno}', ${ totalCost });
        });

        /////////////////////////////////////////////////

        $('input[name=fuserno]').val(${ sessionScope.loginuser.userno });
        $('input[name=ftotal_amount]').val("${ totalCost }");
        $('input[name=fpprice]').val(spprice);
        $('input[name=fproduct_code]').val(sproduct_code);
        $('input[name=fpoqty]').val(spoqty);
        $('input[name=foptioncode]').val("${ spOptionNo }");
        $('input[name=fcartno]').val("${ spCartNo }");
        
        /* console.log(${ sessionScope.loginuser.userno });
        console.log(${ total_amount });
        console.log(${ spprice });
        console.log(${ sproduct_code });
        console.log(${ spoqty });
        console.log(${ soption_code });
        console.log(${ sfcartno }); */

        /////////////////////////////////////////////////
        
        var cart = "${ spOptionNo }";
        var orderno = "${ pre_order_no }";
        var option = "${ spOptionNo }";
        var qty = spoqty;
		var point = (Number(${ totalCost }) * 0.05);
		console.log("AAAA");
		console.log(orderno);
		console.log(option);
        
        $('.preceed:button').click(function() {
        	//alert(" preceed click 찡긋 ");
        	console.log(" preceed click 찡긋 ");
            goCardSlashEnd('${sessionScope.loginuser.userno}', ${ totalCost });
            goPreLog('${sessionScope.loginuser.userno}', orderno, ${ totalCost });
        	goDeleteCart(cart, option);
        	goPoint(point);
        	goDetailLog(orderno, option, qty);
        	goStockUpdate(option, qty);
        	
        });
        
        $('.item-description').click(function() {
		console.log("asd" + $(this).text());
        });

    });
    
    function check() {
    	alert("check");
    }

    // === 아임포트 결제를 해주는 함수 === //
    function goCardSlashEnd(userno, totalCost) {
        var url = "<%=request.getContextPath()%>/payment/cardSlashEnd.com?userno=" + userno + "&totalCost=" + ${ totalCost };
        window.open(url, "cardSlashEnd.com", "left=350px, top=100px, width=820px, height=600px");
    }
    
  

function goPreLog(userno, total_amount, orderno) {
	
//	alert("card_slash jsp - 결제 성공, goPayLogInsert");   

    	var queryString = $("form[name=LogPreDetail]").serialize();
        
         $.ajax({
           url:"/Covengers/payment/cardSlashUpdate.com",
           type:"POST",
           data:queryString,
           success:function(){
			//	alert(" pre ajax 성공");
				location.href = "/Covengers/main.com";
           },
           error: function(request, status, error){
              alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
           }
        }); 
        
    }    
    
function goDetailLog(orderno, option, qty) {

	$.ajax({
        url: "<%= ctxPath%>/payment/cardDetail.com",
        data: { "orderno": orderno, "option": option, "qty": qty },
        type: "post",
        success:function(){
        //	alert("detail ajax 성공");
        }
     });
}

function goDeleteCart(soption, orderno) {
	$.ajax({
        url: "<%= ctxPath%>/payment/cartDelete.com",
        data: { "scartno": soption, "orderno": orderno },
        type: "post",
        success:function(){
        //	alert("delete ajax 성공");
        }
     });
}

function goPoint(point) {
	$.ajax({
        url: "<%= ctxPath%>/payment/cardPoint.com",
        data: { "point": point },
        type: "post",
        success:function(){
        //	alert("point ajax 성공");
        }
     });
}

function goStockUpdate(option, qty) {
	$.ajax({
        url: "<%= ctxPath%>/payment/cardStockUpdate.com",
        data: { "option": option, "qty": qty },
        type: "post",
        success:function(){
        //	alert("stock ajax 성공");
        }
     });
}

</script>

<jsp:include page="../headNavigation.jsp"></jsp:include>

<main class="page payment-page">
    <section class="payment-form dark">
        <div class="container">
            <div class="block-heading">
                <h2>COVENGERS</h2>
                <p>CARDSLASH</p>
            </div>

            <form>
                <div class="products">
                    <h3 class="title">Checkout</h3>
                    <div class="item">

                        <!-- 주문 목록 -->
                        <c:forEach var="cardSlash" items="${ CardSlashList }">
                        
                        <input type="hidden" class="border-none" name="product_code" value="${ cardSlash.product_code }" />
                        <input type="text" class="border-none" name="price" value="${ cardSlash.pprice }" />

                            <span class="price">&#8361;
                                <fmt:formatNumber value="${ cardSlash.total_amount }" pattern="#,###" />
                            </span>
                            <p class="item-name">${ cardSlash.product_name }</p>
                            <p class="item-description">갯수 : ${ cardSlash.poqty }</p>
                            <br>
                        </c:forEach>

                    </div>

                    <div class="total">Total<span class="price">&#8361;
                            <fmt:formatNumber value="${ totalCost }" pattern="#,###" />
                        </span></div>
                </div>
                <!-- 주소록 -->
                <div class="card-details">
                    <h3 class="title">Shipping</h3>
                    <div class="">
                        <c:forEach var="svo" items="${ addressList }">

                            <div>${ svo.siteName }</div>
                            <div>${ svo.receiverName }</div>
                            <div>( ${ svo.postcode } ) ${ svo.address }</div>
                            <div>${ svo.detailAddress } ${ svo.extraAddress }</div>
                            <div>${ svo.mobile }</div>

                            <br>
                            <div class="form-group col-sm-12">
                                <button type="button" name="preceed" class="btn btn-primary btn-block preceed">Proceed</button>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </form>

        </div>
    </section>
</main>

<form name="LogPreDetail">
	<input type="text" class="border-none" name="pre_order_no" value="${ pre_order_no }" /><br>
	<input type="text" class="border-none" name="pre_userno" value="${ pre_userno }" /><br>
	<input type="text" class="border-none" name="pre_checkout" value="${ pre_checkout }" /><br>
	<input type="text" class="border-none" name="pre_clientip_pre" value="${ pre_clientip }" /><br>

	<input type="text" class="border-none" name="detail_fk_order_no" value="${ detail_fk_order_no }" /><br>
	<input type="text" class="border-none" name="detail_fk_userno" value="${ detail_fk_userno }" /><br>
	<input type="text" class="border-none" name="detail_option_code" value="${ spOptionNo }" /><br>
	<input type="text" class="border-none" name="detail_qty" value="${ detail_qty }" /><br>
	<input type="text" class="border-none" name="detail_clientip" value="${ detail_clientip }" /><br>
</form>

<form name="payLognIsert">
	<br>
    userno : <input type="text" class="border-none" name="fuserno" value="" />
    <br>
    fcartno : <input type="text" class="border-none" style="width: 1000px" name="fcartno" value="" />
    <br>
    fproduct_code : <input type="text" class="border-none" style="width: 1000px" name="fproduct_code" value="" />
    <br>
    foptioncode : <input type="text" class="border-none" style="width: 1000px" name="foptioncode" value=""/>
    <br>
    fpoqty : <input type="text" class="border-none" name="fpoqty" value=""/>
    <br>
    fpprice : <input type="text" class="border-none" style="width: 1000px" name="fpprice" value="" />
    <br>
    ftotal_amount : <input type="text" class="border-none" name="ftotal_amount" value="" />
</form>

</body>
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
</body>

</html>
