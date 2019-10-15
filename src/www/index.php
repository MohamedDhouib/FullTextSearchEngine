<?php
require_once "router.php";

route('/getData', function () {
    return "Hello form the about route";
});
$action = $_SERVER['REQUEST_URI'];
dispatch($action);

?>