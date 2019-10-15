<?php include('server.php') ?>
<?php
$pressed=$_SESSION['pressed'];

ini_set('max_execution_time', 0);
ini_set('memory_limit', '1G');
error_reporting(E_ERROR);
// HELPER METHODS
/*function initCurlRequest($reqType, $reqURL, $reqBody = '', $headers = array()) {
    if (!in_array($reqType, array('GET', 'POST', 'PUT', 'DELETE'))) {
        throw new Exception('Curl first parameter must be "GET", "POST", "PUT" or "DELETE"');
    }
    $ch = curl_init();
    
    curl_setopt($ch, CURLOPT_URL, $reqURL);
    curl_setopt($ch, CURLOPT_FRESH_CONNECT, true);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
    curl_setopt($ch, CURLOPT_CUSTOMREQUEST, $reqType);
    curl_setopt($ch, CURLOPT_POSTFIELDS, $reqBody);
    curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
    curl_setopt($ch, CURLOPT_HEADER, true);
    
   	$body = curl_exec($ch);
   	// extract header
   	$headerSize = curl_getinfo($ch, CURLINFO_HEADER_SIZE);
	$header = substr($body, 0, $headerSize);
	$header = getHeaders($header);
	// extract body
	$body = substr($body, $headerSize);
    curl_close($ch);
    
    return [$header, $body];
}
function getHeaders($respHeaders) {
    $headers = array();
    $headerText = substr($respHeaders, 0, strpos($respHeaders, "\r\n\r\n"));
    foreach (explode("\r\n", $headerText) as $i => $line) {
        if ($i === 0) {
            $headers['http_code'] = $line;
        } else {
            list ($key, $value) = explode(': ', $line);
            $headers[$key] = $value;
        }
    }
    return $headers;
}
// MAIN
$reqBody = '';
$headers = array();
list($header, $body) = initCurlRequest('GET', '', $reqBody, $headers);


*/
//
//echo $body;




if( strlen($pressed)!=0){
if($ch = curl_init('http://localhost:8080/slt')){
curl_setopt($ch, CURLOPT_RETURNTRANSFER, TRUE);
curl_setopt($ch, CURLOPT_FOLLOWLOCATION, TRUE);
curl_setopt($ch, CURLOPT_AUTOREFERER, TRUE);
$result = curl_exec($ch);
//echo $result;
}
$answers=explode("|", $result);
foreach($answers as $answer) {
    echo "<br>". $answer . "</br>";
}
}


//$answers=explode("|", $result);

//foreach($answers as $answer) {
//	$answer2=explode("/", $answer);
//	$pathOnServer='/www';

//	for ($i=7; $i <count($answer2) ; $i++) { 
//		$pathOnServer=$pathOnServer .'/'.$answer2[i];


//	}
	
//}







// <?php 
// $url=$_POST['txturl'];
// $ch = curl_init($url);
// curl_setopt($ch, CURLOPT_URL,$url);
// curl_setopt($ch, CURLOPT_RETURNTRANSFER,1);
// $output = curl_exec($ch);
// echo $output;
// curl_close($ch); 
// ?>