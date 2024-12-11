<?php
// Database configuration
$host = "localhost"; // MySQL server host
$username = 'root'; // MySQL username
$password = ''; // MySQL password
$database = 'university system project'; // MySQL database name

// Create connection
$conn = mysqli_connect($host, $username, $password, $database);

// Check connection
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
} 

?>