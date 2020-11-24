<!DOCTYPE html>
<html lang="ko">
<head>
  <title>Bootstrap Example</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  
<style type="text/css">
	
	table{
		margin-left : 200px;
		margin-right : 200px;
		
	}
	
	tr, th, td {
		width : 5px;
	}
	
	form.form-inline {
		margin-left : 200px;
		margin-top : 50px;
		margin-bottom : 100px;
	}
	div#header1 {
		margin-left : 200px;
		margin-top : 50px;
	}
	
	button#first {
		margin-left : 200px;
		size : 50px;
	}
	
	form.form-delivery {
		margin-left : 200px;
		margin-top : 50px;
		margin-bottom : 100px;
	}
	
	form.form-payment {
		margin-left : 200px;
		margin-top : 50px;
		margin-bottom : 100px;
	}
	  		
</style>
  
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>
<div id="header1"><h2>상품확인</h2></div>
<table class="table table-dark">
  <thead>
    <tr>
      <th scope="col"></th>
      <th scope="col">상품명</th>
      <th scope="col">수량</th>
      <th scope="col">가격</th>
    </tr>
  </thead>
  <tbody>

    <tr>
      <th scope="row">
      <input class="form-check-input" type="checkbox" value="" id="defaultCheck1">
	  <label class="form-check-label" for="defaultCheck1">
	  </label>사진</th>
      <td>상품1</td>
      <td>5</td>
      <td>12,000원</td>
    </tr>
    <tr>
      <th scope="row">      <input class="form-check-input" type="checkbox" value="" id="defaultCheck1">
	  <label class="form-check-label" for="defaultCheck1">사진</th>
      <td>상품2</td>
      <td>3</td>
      <td>9,000원</td>
    </tr>
    <tr>
      <th scope="row">      <input class="form-check-input" type="checkbox" value="" id="defaultCheck1">
	  <label class="form-check-label" for="defaultCheck1">사진</th>
      <td>상품3</td>
      <td>12</td>
      <td>2,500원</td>
    </tr>
    <tr>
      <th scope="row">Total</th>
      <td></td>
      <td>20</td>
      <td>23,500원</td>
    </tr>
  </tbody>
</table>

<button type="button" class="btn btn-secondary" id="first">전체선택</button>
<button type="button" class="btn btn-secondary">전체해제</button>
<button type="button" class="btn btn-secondary">선택된 상품 삭제</button>
	
<form class="form-inline">
  <h2>포인트 사용</h2>
  <div class="form-group mb-2">
    <label for="staticEmail2" class="sr-only"></label>
    <input type="text" readonly class="form-control-plaintext" id="staticEmail2" value="사용가능한 포인트 : 2000점">
  </div>
  <div class="form-group mx-sm-3 mb-2">
    <label for="inputPassword2" class="sr-only"></label>
    <input type="text" class="form-control" id="inputPassword2" placeholder="사용할 포인트">
  </div>
  <button type="submit" class="btn btn-primary mb-2">포인트 사용</button>
</form>

<form class="form-delivery">
<h2>배송지 확인</h2>
<button type="button" class="btn btn-secondary" id="first">저장된 배송지</button>
  <div class="form-row">
    <div class="col-md-6 mb-3">
      <label for="validationServer01">이름</label>
      <input type="text" class="form-control is-valid" id="validationServer01" value="Mark" required>
      <div class="valid-feedback">
        Looks good!
      </div>
    </div>
    <div class="col-md-6 mb-3">
      <label for="validationServer02">핸드폰 번호</label>
      <input type="text" class="form-control is-valid" id="validationServer02" value="Otto" required>
      <div class="valid-feedback">
        Looks good!
      </div>
    </div>
  </div>
  <div class="form-row">
    <div class="col-md-6 mb-3">
      <label for="validationServer03">주소</label>
      <input type="text" class="form-control is-invalid" id="validationServer03" aria-describedby="validationServer03Feedback" required>
      <div id="validationServer03Feedback" class="invalid-feedback">
        Please provide a valid city.
      </div>
    </div>

    <div class="col-md-3 mb-3">
      <label for="validationServer05">도로명 주소</label>
      <input type="text" class="form-control is-invalid" id="validationServer05" aria-describedby="validationServer05Feedback" required>
      <div id="validationServer05Feedback" class="invalid-feedback">
        Please provide a valid zip.
      </div>
    </div>
  </div>
  <div class="form-group">
    <div class="form-check">
    </div>
  </div>
  <button class="btn btn-primary" type="submit">주소지 검색</button>
</form>

<form class="form-payment">
<h2>결제 수단 선택</h2>
  <div class="form-group row">
    <label for="inputEmail3" class="col-sm-2 col-form-label">총 상품 금액</label>
    <div class="col-sm-10">
      <input type="text" class="form-control" id="inputEmail3" value="23,500원">
    </div>
  </div>
  <div class="form-group row">
    <label for="inputPassword3" class="col-sm-2 col-form-label">할인금액</label>
    <div class="col-sm-10">
      <input type="text" class="form-control" id="inputPassword3" value="500원"/>
    </div>
  </div>
  <div class="form-group row">
    <label for="inputPassword3" class="col-sm-2 col-form-label">최종 결제 금액</label>
    <div class="col-sm-10">
      <input type="text" class="form-control" id="inputPassword3" value="23,000원">
    </div>
  </div>
  <fieldset class="form-group">
    <div class="row">
      <legend class="col-form-label col-sm-2 pt-0">결제수단</legend>
      <div class="col-sm-10">
        <div class="form-check">
          <input class="form-check-input" type="radio" name="gridRadios" id="gridRadios1" value="option1" checked>
          <label class="form-check-label" for="gridRadios1">
            카카오페이
          </label>
        </div>
        <div class="form-check">
          <input class="form-check-input" type="radio" name="gridRadios" id="gridRadios2" value="option2">
          <label class="form-check-label" for="gridRadios2">
            신용카드
          </label>
        </div>
        <div class="form-check disabled">
          <input class="form-check-input" type="radio" name="gridRadios" id="gridRadios3" value="option3" disabled>
          <label class="form-check-label" for="gridRadios3">
            핸드폰 결제
          </label>
        </div>
      </div>
    </div>
  </fieldset>
  <div class="form-group row">
    <div class="col-sm-2">약관 동의</div>
    <div class="col-sm-10">
      <div class="form-check">
        <input class="form-check-input" type="checkbox" id="gridCheck1">
        <label class="form-check-label" for="gridCheck1">
          약관에 동의하시겠습니까?
        </label>
      </div>
    </div>
  </div>
  <div class="form-group row">
    <div class="col-sm-10">
      <button type="submit" class="btn btn-primary">결제 하기</button>
    </div>
  </div>
</form>

</body>
</html>
