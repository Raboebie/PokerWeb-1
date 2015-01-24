var numberOfCeptions = 0;

$(document).ready(function(){
    $("#cardtable").hide();

    $("#deal").click(function(){
    alert($("#gameid").val());
        $("#cardtable").show();
        $("#cards").html("");
        $("#conclusion").html("");
        numberOfCeptions = $(".list-group-item").length;
        ajaxCeption(0);
    });
});

function ajaxCeption(count){
    if(count < numberOfCeptions){
        $.ajax({
            type:"POST",
            url:"/cards",
            data:{username:$.trim($("#user" + count).text()), gamename:$("#game_name").val(), gameid:$("#gameid").val()},
            success:function(data){
                //$("#cards").append(data);
                ajaxCeption(count + 1);
            }
        });
    }
    else if(count == numberOfCeptions){
        numberOfCeptions = 0;
        $.ajax({
            type:"POST",
            url:"/resetDeck",
            data:{gameid:$("#gameid").val()},
            success:function(data){
                 //$("#conclusion").prepend(data);
                 window.location = "/play/1/history";
            }
        });
    }
}