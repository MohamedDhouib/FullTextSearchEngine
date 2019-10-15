<?php include('server.php') ?>

<?php


$m= date("m"); 
$de= date("d"); 
$y= date("Y"); 

$thisWeekFirstDay=date('y-m-d', mktime(0,0,0,$m,($de-7),$y));
$thisMonthFirstDay=date('y-m-d', mktime(0,0,0,$m,($de-30),$y));
$thisYearFirstDay=date('y-m-d', mktime(0,0,0,$m,($de-365),$y));

$today=date('y-m-d');

$thisWeek="BETWEEN"." '$thisWeekFirstDay' "."and"." '$today'"; 
$thisMonth="BETWEEN"." '$thisMonthFirstDay'  "."and"." '$today' "; 
$thisYear="BETWEEN"." '$thisYearFirstDay' "."and"." '$today' "; 

 ?>