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
	<h2 class="cont_title">1-1 게시판 리스트</h2>
	<div id="cont_wrap">
		<!-- -->
		<div class="section">
			<h3 class="tit">1-1-1 게시판 리스트 기능 안내</h3>
			<p class="img"><img src="/nanum/ncms/manual/img/contents/manual0101_img01.jpg" alt="" /></p>
			<ul class="num_list">
				<li class="num01">[게시판 리스트] 메뉴를 클릭합니다.</li>
				<li class="num02">찾고자하는 게시판이 있으시면 검색을 해주세요. 게시판 검색은 게시판명, 테이블명(예:board_8), 일련번호(예:24874869)으로 검색하실 수 있습니다. </li>
				<li class="num03">등록하신 게시판의 목록입니다. <br />
				<strong>순서</strong> : 화살표를 이용하여 순서수정이 가능합니다.<br />
				<strong>게시판명</strong> : 해당게시판의 게시판명입니다.<br />
				<strong>테이블명</strong> : 해당게시판의 테이블명과, 일련번호입니다.<br />
				<strong>파일</strong> : 클립모양의 아이콘이 있는 경우 파일첨부가 가능한 게시판입니다.<br />
				<strong>전체/오늘</strong> : 해당게시판의 전체 글 수 / 오늘  작성된 글 수에 대한 안내입니다.<br />
				<strong>수정</strong> : 버튼을 누르시면 해당게시판의 속성을 변경하실 수 있습니다. <br />
				<strong>분류</strong> : 게시판의 속성중 분류를 사용으로 체크하시면 분류를 추가 및 수정하실 수 있는 아이콘이 생성됩니다.</li>
			</ul>
		</div>
		<!-- -->
		<div class="section">
			<h3 class="tit">1-1-2 게시판 리스트 순서일괄수정</h3>
			<p class="img"><img src="/nanum/ncms/manual/img/contents/manual0101_img02.jpg" alt="" /></p>
			<ul class="num_list">
				<li class="num01">[순서일괄수정] 버튼을 클릭합니다.</li>
				<li class="num02">버튼 클릭시 게시판 순서 일괄수정이라는 새창화면이 뜹니다.</li>
				<li class="num03">위 이미지와 같이 위치 수정을 원하는 게시판을 클릭하면서 드래그하여 위치를 조정하셔도 되고 원하는 게시판에 마우스를 오버하면 표시되는 화살표 버튼 4개로 위치를 조정하시면 됩니다.</li>
				<li class="num04">순서수정이 완료 되시면 [등록] 버튼을 클릭 해주세요.</li>
			</ul>
		</div>
		<!-- -->
		<div class="section">
			<h3 class="tit">1-1-3 게시판 분류 관리</h3>
			<p class="img"><img src="/nanum/ncms/manual/img/contents/manual0101_img03.jpg" alt="" /></p>
			<h4 class="tit">게시판 리스트에서 해당게시판의 <img src="/nanum/ncms/manual/img/contents/manual0101_icon.gif" alt="" /> 버튼 클릭하시면 게시판 분류 관리페이지로 이동됩니다.</h4>
			<ul class="num_list">
				<li class="num01">분류명 입력창에 원하시는 분류명을 적고 사용여부를 체크하여 [등록] 버튼을 클릭 해주세요.</li>
				<li class="num02">이미 등록된 분류의 순서 및 분류명, 이미지, 사용여부를 수정하거나 분류명을 삭제할 수 있습니다.</li>
				<li class="num03">여러가지의 분류를 한꺼번에 삭제 혹은 사용여부를 변경할 수 있습니다.<br />
				<strong>전체 선택/해제</strong> : [전체 선택/해제] 버튼을 클릭하면 선택 체크박스가 모두 선택됩니다. 재클릭 시 선택 체크박스가 모두 해제됩니다. <br /> 
				<strong>선택 분류삭제</strong> : 분류를 선택하시고 [선택 분류삭제]를 누르시면 해당 분류가 일괄 삭제됩니다.<br />
				<strong>선택한 분류 사용여부 변경</strong> : 분류를 선택하시고 사용여부의 셀렉트 박스를 사용 혹은 중지로 변경버튼을 누르시면 해당 분류가 일괄 사용 혹은 중지로 변경됩니다.</li>
			</ul>
		</div>
		<!-- -->
	</div>
</div>
</body>
</html>
