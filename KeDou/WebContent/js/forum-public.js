$(document).ready(function(){
    //鼠标悬停变换图片 <img data-replace-img="替换图片地址" src="" />
    $('img').mouseover(function(){
        var datatext=$(this).data("replace-img");
        if (datatext!=''){
            var srctext=$(this).attr('src');
            $(this).data("replaceold-img", srctext);
            $(this).attr('src',datatext)
        }
    });
    $('img').mouseout(function(){
        var datatext=$(this).data("replaceold-img");
        if (datatext!=''){
            var srctext=$(this).attr('src');
            $(this).data("replace-img", srctext);
            $(this).attr('src',datatext)
        }
    });
    //分页
    var prevnum=2;
    var nextnum=2;
    var dqnum=parseInt($('.am-pagination .am-active a').text());

    $('.am-pagination-next').before('<li id="wos-pagination-next-more" style="display: none"><a class="am-hide-sm">...</a></li>')
    $('.am-pagination-prev').after('<li id="wos-pagination-prev-more"  style="display: none"><a class="am-hide-sm">...</a></li>')
    $(".am-pagination li a").each(function(){
        var dq=$(this).text()
        if (dq>dqnum+nextnum){
            $(this).parent("li").hide();
            $('#wos-pagination-next-more').show();
        }
        if (dq<dqnum-prevnum){
            $(this).parent("li").hide();
            $('#wos-pagination-prev-more').show();
        }
    })
    $('#wos-pagination-next-more').click(function(){
        $(".am-pagination li a").each(function(){
            var dq=$(this).text()
            if (dq>dqnum+nextnum){
                $(this).parent("li").show();
            }
        });
        $('#wos-pagination-next-more').hide();
    });
    $('#wos-pagination-prev-more').click(function(){
        $(".am-pagination li a").each(function(){
            var dq=$(this).text()
            if (dq<dqnum-prevnum){
                $(this).parent("li").show();
            }
        });
        $('#wos-pagination-prev-more').hide();
    });
});

