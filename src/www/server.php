<?php
	
	$query="";

	//connection to the dataBase
	//$db = mysqli_connect('localhost','root','password','SearchEngine');

	//if the button search is clicked
	if(isset($_POST['search'])){

		session_start();
		$_SESSION['pressed']=$_POST['query'];
		$_SESSION['key']=$_POST['query'];
		$query=$_POST['query'];
		$query.=" ";
		//if the search field is empty
		if($query==" "){
			header('Location: ' . $_SERVER['HTTP_REFERER']);
		}
		//removing prepositions
		else{
		header('Location: results.php');
		$prepArr=array('of ','with ','at ','from ','the ');
		$query=str_ireplace($prepArr,'',$query);
		$keyWords=explode(" ", $query);
		//$query="";
		array_pop($keyWords);
		//mysqli_query($db,"Delete from key_words");
	/*	foreach ($keyWords as $keyword) {
			$sql="INSERT INTO key_words(word) VALUES('$keyword')";
			mysqli_query($db,$sql);
		}*/

		file_put_contents("file.txt", $query);
		require_once "router.php";

		route('/getData', function () {
    	return "Hello form the about route";
		});
		$action = $_SERVER['REQUEST_URI'];
		dispatch($action);
		
		

		//$a=$_POST['cars1'];
		//$sql="INSERT INTO key_words(key_word) VALUES('$a')";
		//	mysqli_query($db,$sql);
		unset($keyword);
		$_SESSION['language']=$_POST['language'];
		$_SESSION['format']=$_POST['format'];
		$_SESSION['publicationDate']=$_POST['publicationDate'];
		
	}
		

	}
	





?>