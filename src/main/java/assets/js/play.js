var numberOfCeptions = 0;

$(document).ready(function(){
    $(".dropdown-menu li a").click(function(e){
        var selText = $(this).text();
        $(this).parents('.btn-group').find('.dropdown-toggle').html(selText+' <span class="caret"></span>');

       /* $.each($(this).parents('.btn-group'), function(){
            alert("ASD");
        });*/

        if($(this).parent().parent().parent().attr("id") != "noplayers")
            e.preventDefault();
    });

    $("#btnPlay").click(function(){
        $("#cardcontainer").html("");
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
                $("#cardcontainer").prepend(data);
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
                 $("#cardcontainer").prepend(data);
            }
        });
    }
}