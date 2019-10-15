<?php include('server.php') ?>
<?php
	
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
	$len = mysqli_num_rows($lenQuery) ;
	$a=$len-1;

	

	$query=$query . " result1.word ";
	
	
	

	if($a>1){
		for ($x = 2; $x <= $a; $x++) {
    		$query=$query . " and result" . strval($x-1) . ".word = result". strval($x) . ".word";
    		//mysqli_query($db,$dropQuery . "result" . strval($x));
    		
		}
		echo $query;
		echo $a;
		echo $len;
		//mysqli_query($db,$query);	
	}
	

?>

