var numberOfCeptions = 0;

$(document).ready(function(){
    $("#cardtable").hide();

    var count = $(".btn-select").length - 1;
    var siblings = [];
    var i = 0;
    $("#userselection0").siblings(".dropdown-menu").children("li").siblings().each( function(){
        siblings[i++] = $(this).children("a").html();
    });
    for(i = 0; i < count; i++){
        $("#userselection" + i).html(siblings[i] + "<span class=\"caret\"></span>");
    }

    $(".dropdown-menu li a").click(function(e){
        var selText = $(this).text();
        $(this).parents('.btn-group').find('.dropdown-toggle').html(selText+' <span class="caret"></span>');

        if($(this).parent().parent().parent().attr("id") != "noplayers")
            e.preventDefault();
    });

    $("#btnPlay").click(function(){
        $("#cardtable").show();
        $("#cards").html("");
        $("#conclusion").html("");
        numberOfCeptions = $(".btn-select").length - 1;
        ajaxCeption(0);
    });
});

function ajaxCeption(count){
    if(count < numberOfCeptions){
        $.ajax({
            type:"POST",
            url:"/cards",
            data:{username:$.trim($("#userselection" + count).text()), gamename:$("#game_name").val()},
            success:function(data){
                $("#cards").append(data);
                ajaxCeption(count + 1);
            }
        });
    }
    else if(count == numberOfCeptions){
        numberOfCeptions = 0;
        $.ajax({
            type:"POST",
            url:"/resetDeck",
            success:function(data){
                 $("#conclusion").prepend(data);
            }
        });
    }
}