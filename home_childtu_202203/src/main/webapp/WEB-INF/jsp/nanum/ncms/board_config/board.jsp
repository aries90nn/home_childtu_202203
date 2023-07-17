<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="language" content="ko" />
<meta name="format-detection" content="telephone=no" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />

<title>게시판 테스트</title>

<link rel="shortcut icon" href="/nanum/site/img/common/favicon.ico" />
<!-- Android icon -->
<link rel="shortcut icon" href="/nanum/site/img/common/android-128x128.png" />
<!-- iPhone icon -->
<link rel="apple-touch-icon" sizes="57x57" href="/nanum/site/img/common/apple-57x57.png" />
<!-- iPad icon -->
<link rel="apple-touch-icon" sizes="72x72" href="/nanum/site/img/common/apple-72x72.png" />
<!-- iPhone icon(Retina) -->
<link rel="apple-touch-icon" sizes="114x114" href="/nanum/site/img/common/apple-114x114.png" />
<!-- iPad icon(Retina) -->
<link rel="apple-touch-icon" sizes="144x144" href="/nanum/site/img/common/apple-144x144.png" />

<script type="text/javascript" src="/nanum/site/common/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="/nanum/site/common/js/jquery.easing.1.3.min.js"></script>
<script type="text/javascript" src="/nanum/site/common/js/common_design.js"></script>
<script type="text/javascript" src="/nanum/site/common/js/common_dev.js"></script>

<link type="text/css" rel="stylesheet" href="/nanum/site/common/css/all.css" />
<link type="text/css" rel="stylesheet" href="/nanum/site/common/css/head_layout.css" />
<link type="text/css" rel="stylesheet" href="/nanum/site/common/css/foot_layout.css" />

<link rel="stylesheet" href="/nanum/site/common/css/owl.carousel.min.css">
<script src="/nanum/site/common/js/jquery.min.js"></script>
<script src="/nanum/site/common/js/owl.carousel.js"></script>

<link type="text/css" rel="stylesheet" href="/nanum/site/common/css/sub_layout.css" />
<link type="text/css" rel="stylesheet" href="/nanum/site/common/css/sub_design.css" />
<!-- 메뉴마다 필요CSS -->


<link href="https://fonts.googleapis.com/css?family=Roboto|Open+Sans" rel="stylesheet" />
<!--[if lt IE 9]>
	<script src="/nanum/site/common/js/html5shiv.js"></script>
	<script src="/nanum/site/common/js/respond.min.js"></script>
<![endif]-->

</head>
<body>


<jsp:include flush='false' page='/board/board.do'><jsp:param name='a_num' value='${param.a_num }' /></jsp:include>


</body>
</html>