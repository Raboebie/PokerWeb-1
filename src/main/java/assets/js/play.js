var numberOfCeptions = 0;

$(document).ready(function(){
    $("#cardtable").hide();

    var count = $(".btn-select").length - 1;
    var siblings = [];
    $("#userselection0").siblings(".dropdown-menu").children("li").siblings().each( function(){
        siblings[siblings.length] = $(this).children("a").html();
    });

    for(var i = 0; i < count; i++){
        $("#userselection" + i).html(siblings[i] + " <span class=\"caret\"></span>");
    }

    $(".dropdown-menu li a").click(function(e){
        var selText = $(this).text();
        var old = $(this).parents('.btn-group').find('.dropdown-toggle').html();
        $(this).parents('.btn-group').find('.dropdown-toggle').html(selText+' <span class="caret"></span>');

        var dup = checkDuplicates();
        if(dup){
            $(this).parents('.btn-group').find('.dropdown-toggle').html(old);
            alert("No duplicate players allowed");
        }

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

function checkDuplicates(){
    var count = $(".btn-select").length - 1;
    var siblings = [];
    $("#userselection0").siblings(".dropdown-menu").children("li").siblings().each( function(){
        siblings[siblings.length] = $.trim($(this).children("a").text());
    });

    var temp = 0;
    for(var i = 0; i < siblings.length; i++){
        for(var j = 0; j < count; j++){
            if($.trim($("#userselection" + j).text()) == siblings[i]){
                temp++;
            }
        }
        if(temp > 1)
            return true;
        else
            temp = 0;
    }
    return false;
}

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