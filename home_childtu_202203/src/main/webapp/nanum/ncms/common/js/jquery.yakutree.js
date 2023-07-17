/*
* 트리 메뉴 의 기능을 수행합니다.
*
* @version 1.0
* @date 2012년 5월 31일 목요일
*
* Dual licensed under the MIT and GPL licenses.
* http://www.opensource.org/licenses/mit-license.php
* http://www.opensource.org/licenses/gpl-license.php
*
* http://yaku.kr
*/
;(function($){

    $.fn.yakutree = function( opts ){
        return $tree.getObject( this, opts  );
    };

    $.yakutree = function (el, opts) {
        return $tree.getObject( el, opts );
    };

    $.yakutree.defaults = {
        animate : false,
        unique : true,
        closed : false,
        aTagClick : true,
        dragndrop : true,
        duration : 300,
        activeClassName : 'active',
        toggleClassName : 'toggle',
        openClassName : 'open',
        plusClassName : 'plus',
        minusClassName : 'minus',
        lastClassName : 'tree_last',
        aSelected : 'selected',
        aCallback : false ,
        btnAllExpand : false,
        btnAllColspand : false ,
        btnFirst : false,
        btnLast : false,
        btnUp : false,
        btnDown : false,
        btnAdd : false,
        btnDel : false,
        btnCallBack : false,
        btnCallack : false
    };

    $.yakutree.impl = function( el, opts ) {
        var s=this;
        s.root = $(el);
        s.opts = $.extend( {}, $.yakutree.defaults, opts );
        s.togglePlus  = '<button type="button" class="' + s.opts.toggleClassName +' ' + s.opts.plusClassName + '" style="display:none;" >+</button>';
        s.toggleMinus = '<button type="button" class="' + s.opts.toggleClassName +' ' + s.opts.minusClassName + '" style="display:none;" >-</button>';
        //s.togglePlus  = '<img src="/nanum/ncms/img/common/side_icon05.png" class="group ' + s.opts.toggleClassName +' ' + s.opts.plusClassName + '" />';
        //s.toggleMinus = '<img src="/nanum/ncms/img/common/side_icon06.png" class="group ' + s.opts.toggleClassName +' ' + s.opts.minusClassName + '" />';
        s.init();
        s.actived();
        s.bindEvents();
        s.aSelected = false;
        if ( s.opts.dragndrop ) {
            s.useDragndrop();
        }
    };

    var $tree = $.yakutree.impl; // $tree 생성자 지정. $.yakutree.impl

    $tree.fn = $tree.prototype = {
        version : '1.0'
    };

    $tree.fn.extend = $tree.extend = $.extend;

    $tree.fn.extend({
        openedUl : null,
        played : false,
        init : function() {
            var s = this, root = s.root;
            root.find("li>ul").css('display','none');
            root.find("li:last-child").addClass(s.opts.lastClassName);
            root.find('li>ul:hidden').parent('li').prepend(s.togglePlus);
            root.find('li>ul:visible').parent('li').prepend(s.toggleMinus);
        },
        actived : function() {
            var s = this, root = this.root;
            root.find("li."+s.opts.activeClassName ).parents("li").addClass(s.opts.openClassName);
            root.find("li."+s.opts.openClassName+">."+s.opts.toggleClassName).text('-').removeClass(s.opts.plusClassName).addClass(s.opts.minusClassName);
            s.open( root.find("li."+s.opts.openClassName+">ul") );
        },
        useDragndrop : function() {
            var s = this;
            var
                dragging = false,
                $holder  = $('<li class="placeholder">');

                var getOffset = function(elem, offsetParent) {
                    var top = 0, left = 0;

                    while(elem && elem != offsetParent) {
                        top  += elem.offsetTop;
                        left += elem.offsetLeft;

                        elem = elem.offsetParent;
                    }

                    return {top:top, left:left};
                }


                var setHolder = function(info, yPos) {
                    if(Math.abs(info.top-yPos) <= 3) {
                        $holder.css({top:info.top-3,height:'5px'});
                        return 'before';
                    } else if(Math.abs(info.bottom-yPos) <= 3) {
                        $holder.css({top:info.bottom-3,height:'5px'});
                        return 'after';
                    } else {
                        $holder.css({top:info.top+3,height:'27px'});
                        return 'prepend';
                    }
                }


            if ( $.ui ) {

                s.root.find('li').draggable({
                    'evert' : 'invalid',
                    'helper' : 'clone',
                    'cursor' : 'move'
                }).droppable({
                    'greedy': true,
                    'accept' : function(){
                        return $(this).parent().children();
                    },
                    'over' : function (event, ui ) {
                        var $li = $(this);
                        s.root.find("li.moveSpace").remove();
                        $li.before('<li class="moveSpace" style="border: 1px dotted #CC0000; height:2px;width:200px;"></li>');
                    },
                    'drop' : function( event, ui ) {
                        var moveSpace = s.root.find("li.moveSpace");
                        var $target = moveSpace.next();
                        var $source = $(ui.draggable);
                        if(dragMoveStatus){
                        	alert("메뉴이동 처리중입니다.\n\n잠시후 다시 시도하시기 바랍니다.");
                        }else{
                        	if(confirm("["+$target.find(">a:eq(0)").text()+"]메뉴 상위로 이동하시겠습니까?")){
                                s.moveAnimation( $source, $target, 'before' );
                                var parent_idx = moveSpace.parent().parent().data("idx");
                                if(parent_idx === undefined || parent_idx === null){
                                	parent_idx = "0";
                                }
                                var this_idx = $source.data("idx");
                                var target_idx = $target.data("idx");
                                dragMoveOk(parent_idx, target_idx, this_idx);
    						}
                        }
						
                        moveSpace.remove();
                    },
                    'deactivate' : function( event, ui) {
                        //$("li.moveSpace").remove();
                    }
                });
            }
        },
        bindEvents : function() {
            var s = this;
            s.buttonClick();
            s.aTagClick();
            s.allExpand();
            s.allColspand();
            s.moveFirst();
            s.moveLast();
            s.moveUp();
            s.moveDown();
            s.addEl();
            s.delEl();
        },
        addEventBind : function ( el ) {
            var s = this;
            s.buttonClick( el.find("button") );
            s.aTagClick( el.find("a.category")  );
        },
        tagLi : function() {
            return  $("<li><a href='#'> Add </a></li>");
        },
        addEl : function(){
            var s = this;
            if ( s.opts.btnAdd ) {
                $(s.opts.btnAdd).on( "click", function(){
                    var $source = s.getSelected(), $target;

                    if ( !$source.length ) {
                        return;
                    }

                    if ( $source.has("ul").length  ) {
                        $target =  $source.find(">ul");
                        $target.find(">li").removeClass( s.opts.lastClassName );
                        $target.append( s.tagLi().addClass( s.opts.lastClassName ) );
                        s.addEventBind( $target.find(">li").last() );
                    } else {
                        var $child = $("<ul />");
                        $child.append( s.tagLi().addClass( s.opts.lastClassName ) );
                        $source.prepend( s.toggleMinus );
                        $source.append( $child );
                        s.buttonClick( $source.find("button") );
                        s.aTagClick( $child.find("a.category")  );
                    }
                });
            }
        },
        delEl : function() {
            var s = this;

            if ( s.opts.btnDel ) {
                $(s.opts.btnDel ).on("click" , function(){
                    var $source = s.getSelected(), $target;

                    if ( !$source.length ) {
                        return;
                    }

                    if ( $source.has("ul").length  ) {
                        alert( ' Has Children is Delete Not .' );
                    } else {
                        var $p = $source.parent();
                        if ( $source.hasClass(s.opts.lastClassName) ) {
                            $source.siblings(":visible").last().addClass(s.opts.lastClassName);
                        }

                        $source.remove();

                        if( $p.find(">li:visible").length <= 0 ) {
                            $p.parent().find(">button").remove();
                            $p.remove();
                        }

                    }
                });
            }

        },
        buttonClick : function( el ) {
            var s = this, $tag = el || s.root.find("button."+s.opts.toggleClassName);
            $tag.on("click", function(e){
                s.toggler( this );
                e.preventDefault();
            });
        },
        aTagClick : function( el ) {
            //var s = this , $tag =  el || s.root.find("li>a");
            var s = this , $tag =  el || s.root.find("li>a.category");
            if ( s.opts.aTagClick ) {
                $tag.on( "click", function(e) {
                    e.preventDefault();

                    if ( s.aSelected ) {
                        $(s.aSelected).removeClass( s.opts.aSelected);
                    }

                    $(this).addClass( s.opts.aSelected );

                    if ( $(this).prev('button').length ) {
                        s.toggler( $(this).prev('button')[0] );
                    }

                    if ( $.isFunction(s.opts.aCallback) ) {
                        s.opts.aCallback.apply( this, [ s ] ); //this aLink , args tree
                    }

                    s.aSelected = this;
                    return false;
                });
            }
        },
        allExpand : function() {
            var s = this;
            if ( s.opts.btnAllExpand ) {
                $(s.opts.btnAllExpand).on('click', function(){
                    s.root.find("button." + s.opts.plusClassName ).each(function(){
                         s.toggler( this );
                    });
                });
            }
        },
        allColspand : function() {
            var s = this;
            if ( s.opts.btnAllColspand ) {
                $(s.opts.btnAllColspand).on('click', function(){
                    s.root.find("button." + s.opts.minusClassName ).each(function(){
                         s.toggler( this );
                    });
                });
            }
        },
        moveFirst : function() {
            var s = this;
            if ( s.opts.btnFirst ) {
                $( s.opts.btnFirst ).on('click', function(){
                    var $source = s.getSelected(), $target;

                    if  ( !$source.length )  {
                        return;
                    }

                    if ( $source.prevAll(':visible').length > 0 ) { // 맨처음이 아닐 경우
                        $target = $source.siblings(':visible').eq(0); // first
                        s.moveAnimation( $source, $target, 'before' ).done(function(){
                            if ( $.isFunction( s.opts.btnCallback ) ) {
                                s.opts.btnCallback.apply( s, [ 'moveFirst', $source, $target  ] );
                            }
                        });
                    }

                });
            }
        },
        moveLast : function() {
            var s = this;
            if ( s.opts.btnLast ) {
                $( s.opts.btnLast ).on( 'click', function() {
                    var $source = s.getSelected(), $target;

                    if ( !$source.length ) {
                        return;
                    }

                    if ( $source.nextAll(':visible').length > 0 ) { // 맨 마지막이 아닐 경우
                        $target = $source.siblings(':visible').eq(-1);
                        s.moveAnimation( $source, $target, 'after' ).done(function(){
                            if ( $.isFunction( s.opts.btnCallback ) ) {
                                s.opts.btnCallback.apply( s, [ 'moveLast', $source, $target ] );
                            }
                        });
                    }
                });
            }
        },
        moveUp : function() {
            var s =this;
            if ( s.opts.btnUp ) {
                $( s.opts.btnUp ).on( 'click', function() {
                     var $source = s.getSelected(), $target;
                     if ( !$source.length ) {
                         return;
                     }
                     if ( $source.prevAll(':visible').length > 0 ) {
                         $target = ( $source.prev().attr("id") == undefined ) ? $source.prev() : $source.prev().prev();
                         s.moveAnimation( $source, $target, 'before' ).done(function(){
                            if ( $.isFunction( s.opts.btnCallback ) ) {
                                s.opts.btnCallback.apply( s, [ 'moveUp', $source, $target ] );
                            }
                         });
                     }
                });
            }
        },
        moveDown : function() {
            var s = this;

            if ( s.opts.btnDown ) {
                $( s.opts.btnDown ).on( 'click', function() {
                    var $source = s.getSelected(), $target;

                    if ( !$source.length ) {
                        return;
                    };

                    if ( $source.nextAll(':visible').length > 0 ) {
                        $target = ( $source.next().attr("id") == undefined ) ? $source.next() : $source.next().next();
                        s.moveAnimation( $source, $target, 'after' ).done(function(){
                            if ( $.isFunction( s.opts.btnCallback ) ) {
                                s.opts.btnCallback.apply( s, [ 'moveDown', $source, $target ] );
                            }
                         });
                    }

                });
            }
        },
        moveAnimation : function( $source, $target, type ) {
            var s = this, sourceHeight = $source.height(), oPos = "oPos", speed = 'fast', last_class = 'tree_last', def = $.Deferred();

            if ( type != 'revert' ) {
                if ( $( "#" +oPos ).length ) {
                    $("#" + oPos).remove();
                }
                $source.before( $("<li id='" + oPos + "'></li>").css('display', 'none') );
            }

            if( $source.css('opacity') === "1" ) {
                $source.css('overflow', 'hidden');

                $source.stop().animate({
                    'height' : '0px',
                    'opacity' : 0
                }, speed, function() {

                    switch ( type ) {
                        case 'prepend':
                            $target.prepend( $source );
                            break;
                        case 'append':
                            $target.append( $source );
                            break;
                        case 'before':
                            $target.before( $source );
                            $target.siblings( '.' + s.opts.lastClassName).removeClass(s.opts.lastClassName);
                            $source.siblings(':visible').last().addClass( s.opts.lastClassName );
                            break;
                        case 'after':
                            $source.parent().children(':visible').removeClass( s.opts.lastClassName );
                            $target.after( $source );
                            $source.parent().children(':visible').last().addClass( s.opts.lastClassName );
                            break;
                        case 'revert':
                            $source.parent().children(':visible').removeClass( s.opts.lastClassName );
                            $('#' + oPos ).after( $source );
                            $source.parent().children(':visible').last().addClass( s.opts.lastClassName );
                            break;
                    }

                    $source.animate( {'height' : sourceHeight, 'opacity' : 1 },  speed, function(){
                        $(this).css({ 'overflow': '', 'height': '' });
                        def.resolve();
                    });
                });
            }

            return def.promise();
        },
        getSelected : function() {
            return $('.'+ this.opts.aSelected).parent('li');
        },
        toggler : function ( el ) {
            var toggleButton = $(el), s = this, opts = s.opts;
            if ( !s.played )  {
                s.played = true;
                if ( toggleButton.hasClass(opts.minusClassName) ) {
                    $tree.swapClass( toggleButton, opts.minusClassName, opts.plusClassName);
                    s.close( toggleButton.parent("li").find(">ul") );
                } else {
                    if ( s.opts.unique && s.openedUl &&  s.openedUl.length === 1) {
                        var opendUl = s.openedUl.parent("li"),  bli = toggleButton.parent("li").get(0);
                        opendUl.siblings("li").each(function(){
                            if ( bli == this ) {
                                $tree.swapClass( opendUl.find("." + opts.toggleClassName), opts.minusClassName, opts.plusClassName);
                                s.close( s.openedUl );
                            }
                        });
                    };
                    $tree.swapClass( toggleButton, opts.plusClassName, opts.minusClassName);
                    s.open( toggleButton.parent("li").find(">ul") );
                }
            }
        },
        close : function( ul ) {
            if ( this.opts.animate ) {
                ul.slideUp(this.opts.duration);
            } else {
                ul.css('display', 'none');
            }
            
            //아이콘변경 추가
            try{
            	var img = ul.parent().find(">a.category>img.group").eq(0);
                img.attr("src", img.attr("src").replace("side_icon03.png", "side_icon02.png"));
                img.attr("src", img.attr("src").replace("side_icon06.png", "side_icon05.png"));
            }catch(e){}
            //아이콘변경 추가 끝
            
            
            this.played  = false;
        },
        open : function ( ul ) {
            if ( this.opts.animate ) {
                ul.slideDown(this.opts.duration);
            } else {
                ul.css('display', 'block');
            }
            
            //아이콘변경 추가
            try{
            	var img = ul.parent().find(">a.category>img.group").eq(0);
                img.attr("src", img.attr("src").replace("side_icon02.png", "side_icon03.png"));
                img.attr("src", img.attr("src").replace("side_icon05.png", "side_icon06.png"));
            }catch(e){}
            //아이콘변경 추가 끝
            
            this.openedUl = ul;
            this.played  = false;
        }
    });

    $tree.extend({
        getObject : function( el, opts){
            if ( !$.data( el, 'tree') ) {
                $.data(el, 'tree', new $tree( el, opts) );
            }
            return $.data(el, 'tree');
        },
        swapClass : function ( el, removeCls, addCls ) {
            $(el).removeClass(removeCls).addClass(addCls);
        }
    });
})(jQuery);


var dragMoveStatus = false;
function dragMoveOk(parent_idx, target_idx, this_idx){
	if(dragMoveStatus){
		alert("메뉴이동 처리중입니다.\n\n잠시후 다시 시도하시기 바랍니다.");
		return;
	}
	var rand = parseInt(Math.random() * 999);
	dragMoveStatus = true;
	$.ajax({
		type: "GET",
		url: "./dragMoveOk.do",
		//data: ({ b_l_num:num }),
		data: "v="+rand+"&parent_idx="+parent_idx+"&target_idx="+target_idx+"&this_idx="+this_idx,
		dataType:"html",
		//timeout:"5000",
		error: function(xhr,status, error){
			alert("에러발생:"+status+","+error);
			dragMoveStatus = false;
		},
		success: function(msg){
			switch(msg){
				case "0" : break;	//alert("메뉴이동 완료.");break;
				case "1" : alert("필요한 파라이터가 누락되었습니다."); break;
				case "2" : alert("데이타가 없습니다."); break;
				default : alert("메뉴이동실패.\n메뉴 동기화 하시기 바랍니다.\n"+msg);
			
			}
			dragMoveStatus = false;
		}
	});
}