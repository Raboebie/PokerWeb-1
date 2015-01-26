var numberOfCeptions = 0;

$(document).ready(function(){
    getUserList();
    $("#deal").click(function(){
        numberOfCeptions = $(".list-group-item").length;
        ajaxCeption(0);
    });
});

function getUserList(){
    $.ajax({
        type:"GET",
        url:"/play/" + $("#gameid").val() + "/getplayers",
        success:function(data){
            $("#userlist").html(data);
            getUserList();
        }
    });
}

function ajaxCeption(count){
    if(count < numberOfCeptions){
        $.ajax({
            type:"POST",
            url:"/cards",
            data:{username:$.trim($("#user" + count).text()), gamename:$("#game_name").val(), gameid:$("#gameid").val()},
            success:function(data){
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
                window.location = "/play/" + $("#gameid").val()  + "/history";
            }
        });
    }
}