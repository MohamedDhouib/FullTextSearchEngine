<?php include('server.php') ?>
<?php include('filter.php') ?>
<?php

$language=$_SESSION['language'];
$format=$_SESSION['format'];
$publicationDate=$_SESSION['publicationDate'];
$filter="";
if (!(($language=="*") and($format=="*") and($publicationDate=="*"))) {
    $filter="WHERE ";
}
if (!($language=="*")) {
    $filter=$filter." language='{$language}' ";
}
if (!($format=="*")) {
    if (!($language=="*")) {
    $filter=$filter." and ";
    }
    $filter=$filter." format='{$format}' ";
}
if (!($publicationDate=="*")) {
    if (!($language=="*") or !($format=="*")) {
        $filter=$filter." and ";
    }
    if ($publicationDate=="thisWeek") {
        $filter=$filter." publicationDate $thisWeek ";
    }
    if ($publicationDate=="thisMonth") {
        $filter=$filter." publicationDate $thisMonth ";
    }
    if ($publicationDate=="thisYear") {
        $filter=$filter." publicationDate $thisYear ";
    }
    
}
// $sql = "SELECT result FROM results ";
// $sql=$sql.$filter;
// if($result = mysqli_query($db , $sql)){
//     if(mysqli_num_rows($result) > 0){
//         echo "<table>";
//         while($row = mysqli_fetch_array($result)){
//             echo "<tr>";
//                 echo "<td>" ."<div class='row'>". $row['result'] . "</div>"."</td>";

//             echo "</tr>";

//         }
//         echo "</table>";
//         mysqli_free_result($result);
//     } 
// }

mysqli_query($db,"DROP TABLE IF EXISTS output");
    mysqli_query($db,"CREATE TABLE output(result VARCHAR(120),  format VARCHAR(30), publicationDate VARCHAR(30)  )");
    
    $query="INSERT INTO output  SELECT name, type, cerationDate from METADATA ";
    for ($x=1; $x <=3 ; $x++) { 
        $query=$query . ", result" . strval($x) ." ";
    }
    $query=$query . " WHERE METADATA.name = ";
    

    $dropQuery="DROP TABLE ";
    //mysqli_query($db,$dropQuery . "result1");

    $lenQuery = mysqli_query($db,"SELECT * FROM key_words");
    
    $len = mysqli_num_rows($lenQuery) -1;

    

    $query=$query . " result1.word ";
    
    
    

    if($len>1){
        for ($x = 2; $x <= 3; $x++) {
            $query=$query . " and result" . strval($x-1) . ".word = result". strval($x) . ".word";
            //mysqli_query($db,$dropQuery . "result" . strval($x));
            
        }
        //echo $query;
        mysqli_query($db,$query);   
    }
$sql = "SELECT result FROM output ";
$sql=$sql.$filter;
if($result = mysqli_query($db , $sql)){
    if(mysqli_num_rows($result) > 0){
        echo "<table>";
        while($row = mysqli_fetch_array($result)){

                echo $row['result'];


        }
        echo "</table>";
        mysqli_free_result($result);
    } 
}


?>
