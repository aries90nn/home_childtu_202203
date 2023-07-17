<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

		
		</div>
		<!--// 컨텐츠영역 -->
	</div>

</div>
<!--// 중간영역 -->

<div id="quick">
	<div class="cont"><img src="/nanum/site/img/common/top_up.gif" alt="" /></div>
</div>

<script type="text/javascript">
	$(function(){
	$("#quick").hide(); // 탑 버튼 숨김- 이걸 빼면 항상 보인다.
		$(window).scroll(function () {
			if ($(this).scrollTop() > 100) { // 스크롤 내릴 표시
			$('#quick').fadeIn();
			} else {
				$('#quick').fadeOut();
			}
		});
		$('#quick').click(function () {
			$('body,html').animate({
				scrollTop: 0 //탑 설정 클수록 덜올라간다
			}, 1000); // 탑 이동 스크롤 속도를 조절할 수 있다.
			return false;
		});
	});
</script>

	<jsp:include page="../file/foot.jsp" />