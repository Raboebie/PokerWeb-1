$(document).ready(function(){
    $(".dropdown-menu li a").click(function(e){
        var selText = $(this).text();
        $(this).parents('.btn-group').find('.dropdown-toggle').html(selText+' <span class="caret"></span>');
        e.preventDefault();
    });

    $("#btnPlay").click(function(){
        $("#cardcontainer").html("");
        //First Hand
        $.ajax({
            type:"POST",
            url:"/cards",
            data:{username:$.trim($('.btn-select').text()), gamename:$("#game_name").val()},
            success:function(data){
                $("#cardcontainer").append(data);
                //Second Hand
                    $.ajax({
                        type:"POST",
                        url:"/cards",
                        data:{username:$.trim($('.btn-select1').text()), gamename:$("#game_name").val()},
                        success:function(data){
                            $("#cardcontainer").append(data);
                            //Third Hand
                                $.ajax({
                                    type:"POST",
                                    url:"/cards",
                                    data:{username:$.trim($('.btn-select2').text()), gamename:$("#game_name").val()},
                                    success:function(data){
                                        $("#cardcontainer").append(data);
                                        //Fourth Hand
                                            $.ajax({
                                                type:"POST",
                                                url:"/cards",
                                                data:{username:$.trim($('.btn-select3').text()), gamename:$("#game_name").val()},
                                                success:function(data){
                                                    $("#cardcontainer").append(data);
                                                    //Reset Deck
                                                        $.ajax({
                                                            type:"POST",
                                                            url:"/resetDeck",
                                                            success:function(data){
                                                                $("#cardcontainer").prepend(data);
                                                            }
                                                        });
                                                }
                                            });
                                    }
                                });
                        }
                    });
            }
        });
    });
});