$(document).ready(function(){
    $("#usernameinuse").hide();
    $(".passwordStrength").hide();
    $("#submit").attr("disabled", "disabled");

    $("#username").keyup(function(){
        var password = $("#password").val();
        var username = $("#username").val();

        $.ajax({
            type:"POST",
            url:"/userExists",
            data:{username:$("#username").val()},
            success:function(data){
                if(data == "true"){
                    $(".passwordStrength").hide();
                    $("#usernameinuse").show();
                    $("#submit").attr("disabled", "disabled");
                    $("#password").attr("disabled", "disabled");
                }
                else{
                    $("#usernameinuse").hide();
                    if(username.length > 0 && password.length > 0) $("#submit").removeAttr("disabled");
                    $("#password").removeAttr("disabled");
                }
            }
        });
    });

    $("#password").keyup(function(){
        $(".passwordStrength").show();
        var password = $("#password").val();
        var username = $("#username").val();
        var passwordStrength = 0;

        if(password.length > 0 && username.length > 0) $("#submit").removeAttr("disabled");
        else $("#submit").attr("disabled", "disabled");

        if(password.length > 10) passwordStrength += 25;
        if(hasLower(password) && hasUpper(password)) passwordStrength += 25;
        if(hasNumeric(password)) passwordStrength += 25;
        if(specialCharacters(password)) passwordStrength += 25;

        $(".progress-bar").removeClass("progress-bar-danger");
        $(".progress-bar").removeClass("progress-bar-warning");
        $(".progress-bar").removeClass("progress-bar-info");
        $(".progress-bar").removeClass("progress-bar-success");

        $("#passwordStrengthLabel").removeClass("label-danger");
        $("#passwordStrengthLabel").removeClass("label-warning");
        $("#passwordStrengthLabel").removeClass("label-info");
        $("#passwordStrengthLabel").removeClass("label-success");

        if(passwordStrength < 26){
            $(".progress-bar").addClass("progress-bar-danger");
            $("#passwordStrengthLabel").addClass("label-danger");
            $("#passwordStrengthLabel").html("Password Strength: Bad");
        }
        else if(passwordStrength < 51){
            $(".progress-bar").addClass("progress-bar-warning");
            $("#passwordStrengthLabel").addClass("label-warning");
            $("#passwordStrengthLabel").html("Password Strength: OK");
        }
        else if(passwordStrength < 76){
            $(".progress-bar").addClass("progress-bar-info");
            $("#passwordStrengthLabel").addClass("label-info");
            $("#passwordStrengthLabel").html("Password Strength: Good");
        }
        else{
            $(".progress-bar").addClass("progress-bar-success");
            $("#passwordStrengthLabel").addClass("label-success");
            $("#passwordStrengthLabel").html("Password Strength: Great");
        }

        $(".progress-bar").attr("style", "width:" + passwordStrength + "%")
    });
});

function hasNumeric(string){
    return (/[0-9]/.test(string));
}

function hasLower(string){
    return (/[a-z]/.test(string));
}

function hasUpper(string){
    return (/[A-Z]/.test(string));
}

var specialChars = "<>@!#$%^&*()_+[]{}?:;|'\"\\,./~`-="
function specialCharacters(string){
 for(i = 0; i < specialChars.length;i++){
   if(string.indexOf(specialChars[i]) > -1){
       return true
    }
 }
 return false;
}