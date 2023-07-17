<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<!doctype html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="language" content="ko" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>나눔아이앤씨 관리자가이드</title>
<link rel="stylesheet" type="text/css" href="/nanum/ncms/manual/common/css/manual.css" />
<script type="text/javascript" src="/nanum/ncms/manual/common/js/jquery-3.1.1.min.js"></script>
</head>
<body>
<div class="area">
	<h2 class="cont_title">3-4 설문조사관리</h2>
	<div id="cont_wrap">
		<!-- -->
		<div class="section">
			<h3 class="tit">3-4-1 설문조사 리스트 페이지</h3>
			<p class="img"><img src="/nanum/ncms/manual/img/contents/manual0305_img01.jpg" alt="" /></p>
			<ul class="num_list">
				<li class="num01">[설문조사리스트] 메뉴를 클릭합니다.</li>
				<li class="num02">원하시는 설문조사를 제목으로 검색하실 수 있습니다.</li>
				<li class="num03">등록하신 설문조사의 목록입니다. 해당 설문조사의 제목, 설문기간, 사용여부를 확인하실 수 있고 수정, 삭제, 설문문항관리 기능이 있습니다.</li>
				<li class="num04">여러개의 설문조사를 한꺼번에 삭제 혹은 사용여부변경이 가능합니다.<br />
				<strong>전체 선택/해제</strong> : [전체 선택/해제] 버튼을 클릭하면 선택 체크박스가 모두 선택됩니다. 재클릭 시 선택 체크박스가 모두 해제됩니다.<br />
				<strong>선택 설문삭제</strong> : 설문조사를 선택하시고 [선택 설문삭제] 버튼을 클릭하시면 해당 설문조사가 일괄 삭제됩니다.<br />
				<strong>선택한 설문조사 사용여부 변경</strong> : 설문조사를 선택하시고 사용여부 셀렉트 박스를 원하시는 것으로 선택 후 변경버튼을 누르시면 해당 설문조사의 사용여부가 일괄 변경됩니다.</li>
			</ul>
		</div>
		<!-- -->
		<div class="section">
			<h3 class="tit">3-4-2 신규 설문조사 생성</h3>
			<p class="img"><img src="/nanum/ncms/manual/img/contents/manual0305_img02.jpg" alt="" /></p>
			<ul class="num_list">
				<li class="num01">[신규 설문조사 생성] 메뉴를 클릭합니다.</li>
				<li class="num02">신규 설문조사의 정보를 입력해주세요.<br />
				<strong>제목</strong> : 설문조사의 제목을 입력해주세요.<br />
				<strong>설문 대상자 선택</strong> :  설문 대사장자 회원그룹을 선택해 주세요.<br />
				<strong>설문기간</strong> : 설문조사 기간을 선택해 주세요.<br />
				<strong>사용여부</strong> : 설문조사의 사용여부를 선택해 주세요. 사용선택시 등록과 동시에 바로 적용됩니다.</li>
				<li class="num03">신규 설문조사의 정보 입력 후 [등록] 버튼을 클릭하시면 신규 설문조사가 생성됩니다. </li>
			</ul>
		</div>
		<!-- -->
		<div class="section">
			<h3 class="tit">3-4-3 설문조사 문항설정</h3>
			<p class="img"><img src="/nanum/ncms/manual/img/contents/manual0305_img03.jpg" alt="" /></p>
			<h4 class="tit">설문조사 리스트에서 해당 설문조사의 <img src="/nanum/ncms/manual/img/contents/manual0305_icon.gif" alt="" />버튼 클릭하시면 게시판 분류 관리페이지로 이동됩니다.</h4>
			<ul class="num_list">
				<li class="num01">설문조사 문항의 정보를 입력해 주세요.<br />
				<strong>머릿말</strong> : 설문조사 문항 제목 위에 머릿말로 노출 되는 부분입니다. <br />
				<strong>문항 제목</strong> : 설문조사 문항의 제목으로 설문조사 답변을 하기 위한 질문에 해당됩니다.<br />
				<strong>형식</strong> : 설문조사 답변에 대한 형식(주관식 혹은 객관식)을 선택해 주세요. 객관식을 선택하시면 객관식의 답을 번호에 맞게 입력해 주세요. 객관식의 답은 다중선택 설정이 가능합니다.<br />
				<strong>사용여부</strong> : 등록하시는 문항의 사용여부를 선택해 주세요.</li>
				<li class="num02">설문조사 문항의 정보 입력 후 [등록] 버튼을 클릭하시면 신규 문항이 등록됩니다. 등록하신 문항은 아래에서 확인하실 수 있습니다. </li>
			</ul>
		</div>
		<!-- -->
		<div class="section">
			<h3 class="tit">3-4-4 설문조사 문항설정2</h3>
			<p class="img"><img src="/nanum/ncms/manual/img/contents/manual0305_img04.jpg" alt="" /></p>
			<h4 class="tit">위 내용에서 문항을 등록하시면 아래에 등록하신 문항을 확인하실 수 있습니다.</h4>
			<ul class="num_list">
				<li class="num01">등록하신 문항의 리스트입니다. 등록정보와 사용여부에 대해서 알 수 있고, 해당문항의 수정, 삭제가 가능합니다.</li>
				<li class="num02">여러개의 설문조사 문항을 한꺼번에 삭제 혹은 순서일괄수정, 사용여부변경이 가능합니다.<br />
				<strong>전체 선택/해제</strong> : [전체 선택/해제] 버튼을 클릭하면 선택 체크박스가 모두 선택됩니다. 재클릭 시 선택 체크박스가 모두 해제됩니다.<br />
				<strong>선택 문항삭제</strong> : 문항을 선택하시고 [선택 문항삭제] 버튼을 클릭하시면 해당 문항이 일괄 삭제됩니다.<br />
				<strong>순서일괄수정</strong> : 버튼을 클릭하시면 문항 순서를 일괄수정할 수 있습니다.<br />
				<strong>선택한 문항 사용여부 변경</strong> : 문항을 선택하시고 사용여부 셀렉트 박스를 원하시는 것으로 선택 후 변경버튼을 누르시면 해당 문항의 사용여부가 일괄 변경됩니다.</li>
			</ul>
		</div>
		<!-- -->
	</div>
</div>
</body>
</html>
