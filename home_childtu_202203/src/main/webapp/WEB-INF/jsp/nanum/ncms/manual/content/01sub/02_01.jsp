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
	<h2 class="cont_title">1-2 게시판 신규생성</h2>
	<div id="cont_wrap">
		<!-- -->
		<div class="section">
			<h3 class="tit">1-2-1 게시판 신규생성 및 게시판 기본정보 입력</h3>
			<p class="img"><img src="/nanum/ncms/manual/img/contents/manual0102_img01.jpg" alt="" /></p>
			<ul class="num_list">
				<li class="num01">[게시판신규생성] 메뉴를 클릭합니다.</li>
				<li class="num02">기존게시판 설정 중 복사하고 싶은 게시판이 있으시면 셀렉트박스에서 해당 게시판을 고르신 후 복사버튼을 눌러주세요.</li>
				<li class="num03">게시판 기본정보를 입력해주세요.</li>
			</ul>
		</div>
		<!-- -->
		<div class="section">
			<h3 class="tit">1-2-2 게시판 필드정보 선택 및 입력</h3>
			<p class="img"><img src="/nanum/ncms/manual/img/contents/manual0102_img02.jpg" alt="" /></p>
			<h4 class="tit">게시판에서 [글쓰기] 버튼 클릭시 나오는 글쓰기 페이지의 필드정보를 선택 및 입력해주세요.</h4>
			<ul class="num_list">
				<li class="num01">게시판 목록페이지에 관리자버튼을 노출하시고 싶으시면 사용을 체크해주세요.</li>
				<li class="num02">게시판 글의 분류를 추가하여 나누고 싶으시면 사용을 체크해주세요. </li>
				<li class="num03">원하시는 필드를 필수로 하고 싶으시면 필수를 체크해주세요.</li>
				<li class="num04">기본적으로 제공되는 필드(이메일, 전화번호, 주소, 본문) 외 다른 필드를 추가 하고싶으시면 사용자 필드추가를 사용으로 선택시 생기는 폼에 원하시는 필드명, 필드타입, 선택항목을 입력해주세요.</li>
			</ul>
		</div>
		<!-- -->
		<div class="section">
			<h3 class="tit">1-2-3 기능정보 선택 및 입력</h3>
			<p class="img"><img src="/nanum/ncms/manual/img/contents/manual0102_img03.jpg" alt="" /></p>
			<h4 class="tit">게시판에서 필요한 기능을 선택 및 입력해주세요.</h4>
			<ul class="num_list">
				<li class="num01">게시판 목록페이지에서 최신게시글 표시인 new아이콘의 노출 일 수를 입력할 수 있습니다. </li>
				<li class="num02">파일을 업로드할 수 있는 기능으로 업로드파일갯수, 파일당 제한용량, 업로드허용파일을 입력할 수 있습니다. (※ 포토게시판 사용시 사용여부 필수)</li>
				<li class="num03"><strong>답변</strong> : 게시글에 답변을 달 수 있는 기능입니다.<br />
				<strong>한줄답글(댓글)</strong> : 게시글 하단의 한줄답글(댓글)을 달 수 있는기능입니다. <br />
				<strong>공개/비공개</strong> : 게시글의 공개/비공개를 선택할 수 있는 기능입니다.<br />
				<strong>비공개게시판</strong> : 게시판을 비공개게시판으로 사용하는 기능입니다.<br />
				<strong>에디터</strong> : 게시글 작성시 글자색상, 크기, 링크, 이미지첨부 등을 편하게 하기 위해 에디터 기능입니다.<br />
				<strong>실명인증</strong> : 공공아이핀인증, 핸드폰번호인증의 인증을 걸쳐 게시글을 작성하는 기능입니다.<br />
				<strong>RSS제공</strong> : RSS마크를 제공합니다.<br />
				<strong>SNS보내기</strong> : 게시글 내용을 SNS로 보낼수 있는 기능입니다.<br />
				<strong>첨부사진 바로보이기</strong> : 파일첨부로 이미지를 업로드 하였을 경우 이미지가 게시글에 바로 보이게 할 수 있는 기능입니다.<br />
				<strong>사용기간</strong> : 게시판의 사용기간을 설정할 수 있습니다.<br />
				<strong>금지단어</strong> : 금지단어(예:욕설, 비방, 광고)를 설정하여 게시글 작성 시 금지단어를 차단할 수 있습니다.<br />
				<strong>휴지통</strong> : 삭제한 게시물을 휴지통에 임시보관하는 기능입니다.<br />
				<strong>게시기간 설정</strong> : 게시물 작성시 게시기간을 설정해 일정기간만 게시물이 보이게 하는 기능입니다.<br />
				<strong>등록일자 수정</strong> : 관리권한이 있는경우에 원하시는 게시글의 등록일자 수정이 가능한 기능입니다.</li>
			</ul>
		</div>
		<!-- -->
		<div class="section">
			<h3 class="tit">1-2-4 디자인정보 선택 및 입력 후 게시판 등록하기</h3>
			<p class="img"><img src="/nanum/ncms/manual/img/contents/manual0102_img04.jpg" alt="" /></p>
			<h4 class="tit">게시판 목록페이지와 글쓰기페이지에서의 디자인정보를 선택 및 입력해주세요.</h4>
			<ul class="num_list">
				<li class="num01">게시판 목록에서 한 목록당 노출되는 게시물의 수를 입력하세요.</li>
				<li class="num02">게시판 목록에서 보여지는 필드를 선택합니다.</li>
				<li class="num03">게시판 글쓰기시에 기본적으로 쓰여지는 내용을 입력합니다(없을 시에 비워두시면됩니다.)</li>
				<li class="num04">게시판의 설정이 완료되면 하단의 [등록]버튼을 눌러서 게시판을 신규등록합니다.</li>
			</ul>
		</div>
		<!-- -->
	</div>
</div>
</body>
</html>
