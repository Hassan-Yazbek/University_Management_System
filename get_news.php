<?php
include 'dataBase.php';

function getNews() {
    global $conn;

    $sql = "SELECT title, date, article,photo_url FROM articles WHERE status = '1' and category='students' ";
    $stmt = mysqli_prepare($conn, $sql);

    if (!$stmt) {
        die(json_encode(array("status" => "error", "message" => "SQL error: " . mysqli_error($conn))));
    }

    mysqli_stmt_execute($stmt);
    $result = mysqli_stmt_get_result($stmt);
    
    $news = array();
    while ($row = mysqli_fetch_assoc($result)) {

        $file_path = $row['photo_url'];
         $full_image_path = $_SERVER['DOCUMENT_ROOT'] . "/USM_CODE/dist" ."/".$file_path;
         
         if (file_exists($full_image_path)) {
             $file_data = file_get_contents($full_image_path);
             $encoded_file = base64_encode($file_data);
             $row['photo_url'] = $encoded_file;
            }

        $news[] = $row;
    }

    mysqli_stmt_close($stmt);

    if (empty($news)) {
        return json_encode(array("message" => "No news found"));
    }

    return json_encode($news);

}

header('Content-Type: application/json');
echo getNews();
?>
