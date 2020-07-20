

$(document).ready(function(){

  $('.delete-location').on('click' ,function(){

         var path = /*[[@{/}]]*/'remove';

         var id=$(this).attr('id');

         bootbox.confirm({
            message: "Are you sure you want to delete this location from the list? It can't be undone!",
            buttons: {
               cancel: {
                 label: '<i class="fas fa-times"></i> Cancel'
               },
               confirm: {
                 label: '<i class="fas fa-check"></i> Confirm'
               }
            },

            callback: function(confirmed){
               if(confirmed){
                 $.post(path, {'id':id}, function(res){
                   location.reload();
                 });
               }
            }
         });
  });

    var locationIdList = [];

    $('.checkboxLocation').click(function(){
       var id=$(this).attr('id');
       if(this.checked){
        locationIdList.push(id);
       }else{
         locationIdList.splice(locationIdList.indexOf(id), 1);
       }
    })

    $('#deleteSelected').click(function(){

       var path = /*[[@{/}]]*/'removeList';
       var id=$(this).attr('id');

                bootbox.confirm({
                  message: "Are you sure you want to delete all selected locations from the list? It can't be undone!",
                  buttons: {
                     cancel: {
                       label: '<i class="fas fa-times"></i> Cancel'
                     },
                     confirm: {
                       label: '<i class="fas fa-check"></i> Confirm'
                     }
                  },

                  callback: function(confirmed){
                     if(confirmed){
                         $.ajax({
                           type: 'POST',
                           url: path,
                           data: JSON.stringify(locationIdList),
                           contentType: "application/json",
                           success: function(res) {console.log(res); location.reload()},
                           error: function(res) {console.log(res); location.reload();}
                         });
                       }
                     }
                  });
               });

             $("#selectAllLocations").click(function(){
                if($(this).prop("checked")==true){
                   $(".checkboxLocation").click();
                }else if($(this).prop("checked")==false){
                   $(".checkboxLocation").click();
                }

             })
});

