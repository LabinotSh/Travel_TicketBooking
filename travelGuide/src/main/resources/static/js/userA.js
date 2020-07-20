  <!--Activate the login tab automatically-->
  function activaTab(tab){
    $('.nav-tabs a[href="#' + tab + '"]').tab('show');
   };
activaTab('tab-2');

function switchColors(element)
{
links=document.getElementsByTagName("a") ;
for (var i = 0 ; i < links.length ; i ++)
links.item(i).style.color = 'black' ;
element.style.color='orange' ;
}