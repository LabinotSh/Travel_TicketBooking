$(document).ready(function() {
  $('location-item').after('<div id="nav" class="text-center"></div>');
  var rowsShown = 5;
  var rowsTotal = $('.t1 .card').length;
  console.log('lenghhhhhh',rowsTotal);
  var numPages = rowsTotal / rowsShown;
  for (i = 0; i < numPages; i++) {
    var pageNum = i + 1;
    $('#nav').append('<a href="#" class="btn-outline-info" rel="' + i + '">&emsp;' + pageNum + '&emsp;</a> ');
  }
  $('.t1 .card').hide();
  $('.t1 .card').slice(0, rowsShown).show();
  $('#nav a:first').addClass('active');
  $('#nav a').bind('click', function(e) {
  	e.preventDefault();
    $('#nav a').removeClass('active');
    $(this).addClass('active');
    var currPage = $(this).attr('rel');
    var startItem = currPage * rowsShown;
    var endItem = startItem + rowsShown;
    $('.t1 .row').css('opacity', '0').hide().slice(startItem, endItem).
//    css('display', 'flex').animate({
//      opacity: 1
//    }, 300);
  });
});



$(document).ready(function() {
    $datatable = $('#locationList').DataTable({
        dom: 'Blfrtip',
        "ordering":false,
        stateSave:true
    });

    $('#locationList').on('page.dt', function(){
      $('html,body').animate({
         scrollTop: $('#locationList').offset().top
      }, 200);
    })
} );

$(document).ready(function() {
    $('#locationList').DataTable();
} );
