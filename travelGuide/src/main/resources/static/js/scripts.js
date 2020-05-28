
function checkBillingAddress(){
   if($("#theSameAsShippingAddress").is(":checked")){
      $(".billingAddress").prop("disabled",true);
   }else{
      $(".billingAddress").prop("disabled",false);
   }
}

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
    $(".carItemQty").on('change',function(){
    var id = this.id;

    $('#update-item-'+id).css('display', 'inline-block');

    });

   $("#theSameAsShippingAddress").on('click',checkBillingAddress);
   $("#txtConfirmPassword").on('keyup',checkPasswordMatch);
   $("#txtNewPassword").on('keyup',checkPasswordMatch);
});