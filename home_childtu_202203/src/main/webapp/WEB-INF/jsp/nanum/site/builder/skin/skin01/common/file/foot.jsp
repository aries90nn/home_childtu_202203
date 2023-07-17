<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!-- 하단영역 -->
<%--카피라이트 --%>
${BUILDER.bs_copyright }
<!-- //하단영역 -->


		<!-- 퀵영역 -->
		
		<div class="go-top">
			<p class="top_btn"><a href="#head"><img src="/nanum/site/builder/skin/${BUILDER_SKIN }/img/common/quick_top.png" alt="최상단 이동" /></a></p>
		</div>
		<!--// 퀵영역 -->
		<script type="text/javascript">
		/*Add class when scroll down*/
		$(window).scroll(function(event){
			var scroll = $(window).scrollTop();
			if (scroll >= 50) {
				$(".go-top").addClass("show");
				$(".floating").addClass("show");
			} else {
				$(".go-top").removeClass("show");
				$(".floating").removeClass("show");
			}
		});
		/*Animation anchor*/
		$('.top_btn a').click(function(){
			$('html, body').animate({
				scrollTop: $( $(this).attr('href') ).offset().top
			}, 1000);
		});
		</script>


</div>
</body>
</html>