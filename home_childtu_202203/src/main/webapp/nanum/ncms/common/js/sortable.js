$(function(){
	$(".sortable").find("li").each(function () {
		$(this).mouseover(function () {
			$(this).css("border-color", "#339999").find(".ds-buttons").css("display", "block");
		});
		$(this).mouseout(function () {
			$(this).css("border-color", "#CCC").find(".ds-buttons").css("display", "none");
		});
	});

	//한단계 위로
	$("._moveUp").click(function(){
		var liobj = $(this).parents("li:first");
		//==== 첫번째 팝업존인지 체크 ===========			
		if( liobj.index() == 0){
			alert("첫번째 입니다.");
			return;
		}
		liobj.insertBefore(liobj.prev());

		//화살표 레이어 숨기기
		liobj.find(".ds-buttons").css("display", "none");
		sort_complete();
	});

	//맨위로
	$("._moveTop").click(function(){
		var liobj = $(this).parents("li:first");
		//==== 첫번째 팝업존인지 체크 ===========
		if( liobj.index() == 0){
			alert("첫번째 입니다.");
			return;
		}		
		liobj.insertBefore($("#sortable").children('li:first'));

		//화살표 레이어 숨기기
		liobj.find(".ds-buttons").css("display", "none");
		sort_complete();
		$('html,body').animate({scrollTop: $(this).offset().top}, 800);
	});
	//한단계 밑으로
	$("._moveDown").click(function(){
		var liobj = $(this).parents("li:first");
		//==== 마지막 팝업존인지 체크 ===========
		if( liobj.next("li").length==0){
			alert("마지막 입니다.");
			return;
		}
		liobj.insertAfter(liobj.next());
		
		//화살표 레이어 숨기기
		liobj.find(".ds-buttons").css("display", "none");
		sort_complete();
	});
	//맨 밑으로
	$("._moveBottom").click(function(){
		var liobj = $(this).parents("li:first");
		//==== 마지막 팝업존인지 체크 ===========
		if( liobj.next("li").length==0){
			alert("마지막 입니다.");
			return;
		}
		liobj.insertAfter($("#sortable").children('li:last'));

		//화살표 레이어 숨기기
		liobj.find(".ds-buttons").css("display", "none");
		sort_complete();
	});


	//순서바꾸는 함수
	$( "#sortable" ).sortable({
		placeholder: "sortable-placeholder",
		helper: 'clone',
		opacity: "1",
		cursor: 'move',
		sort: function(e, ui) {
			//$(ui.placeholder).html(Number($("#sortable > li").index(ui.placeholder)) + 1);
		},
		update: function(event, ui){
			sort_complete();
		}
	});

	//화살표 함수 처리 완료
	function sort_complete(){
		$("#sortable").children('li').each(function() {
			$(this).find(".jsNum").html($(this).index() + 1);
		});
	}
});