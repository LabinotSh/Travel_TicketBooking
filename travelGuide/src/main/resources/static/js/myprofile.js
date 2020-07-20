
function checkPasswordMatch(){
 var password = $("#txtNewPassword").val();
 var confirmPassword = $("#txtConfirmPassword").val();

 if(password == "" && confirmPassword == ""){
    $("#checkPasswordMatch").html("");
    $("#updateUserInfoButton").prop('disabled',false);
 }else{
    if(password != confirmPassword){
      $("#checkPasswordMatch").html("Passwords do not match!");
      $("#updateUerInfoButton").prop('disabled',true);
    }else{
     $("#checkPasswordMatch").html("Passwords match!");
     $("#updateUerInfoButton").prop('disabled',false);
    }
 }
 }

$(document).ready(function(){
    $('a[data-toggle="pill"]').on('show.bs.tab', function(e) {
        localStorage.setItem('activeTab', $(e.target).attr('href'));
    });
    var activeTab = localStorage.getItem('activeTab');
    if(activeTab){
        $('#myTab a[href="' + activeTab + '"]').tab('show');
    }

    $("#txtConfirmPassword").on('keyup',checkPasswordMatch);
    $("#txtNewPassword").on('keyup',checkPasswordMatch);
});


