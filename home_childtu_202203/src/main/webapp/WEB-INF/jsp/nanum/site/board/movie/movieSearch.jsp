<?
$api_key = "1c66a37e6cab7c63a20e33a7a06e0929";
$query = $_GET['query'];
$query = htmlentities( urlencode($query) );
?>
<script type="text/javascript">
//<![CDATA[
$(function(){

	searchMovie('<?=$api_key?>', '<?=$query?>', 'movieSearchResult');

});

function movieSearchResult(obj){

	var html = "";
	for(var i=0;i<=obj.channel.totalCount-1;i++){

		var img_url = obj.channel.item[i].thumbnail[0].content;
		var title = obj.channel.item[i].title[0].content;
		var director = "";
		for(var z=0;z<=obj.channel.item[i].director.length-1;z++){
			if(director != ""){director += ", ";}
			director += obj.channel.item[i].director[z].content;
		}

		if(html != ""){html +="<br />";}
		html += "<img src='"+img_url+"' />"+title+" | 감독 : "+director;

	}
	$("#movie_list").html( html );
}

//]]>
</script>

<div id="movie_list">
검색결과가 없습니다.
</div>