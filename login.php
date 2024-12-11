<?php

include 'dataBase.php';

/**
 * Check if a student exists in the database with the given email and password.
 *
 * @param string $email The student's email to check.
 * @param string $student_password The student's password to check.
 * @return array|null An associative array containing the student's name and photo URL if the student exists, null otherwise.
 */
function check_student_credentials($email, $student_password) {
    global $conn;

    $stmt = mysqli_prepare($conn, "SELECT students_id,name, photo_url FROM student WHERE email = ? AND password = ?");
    
    if (!$stmt) {
        die(json_encode(array("status" => "error", "message" => "SQL error: " . mysqli_error($conn))));
    }
    
    mysqli_stmt_bind_param($stmt, "ss", $email, $student_password);
    mysqli_stmt_execute($stmt);

    $result = mysqli_stmt_get_result($stmt);
    $student = mysqli_fetch_assoc($result);

    $file_path = $student['photo_url'];
    $full_image_path = $_SERVER['DOCUMENT_ROOT'] . "/USM_CODE/dist" ."/".$file_path;
    
    if (file_exists($full_image_path)) {
        $file_data = file_get_contents($full_image_path);
        $encoded_file = base64_encode($file_data);
        // Add image data to course array
        $student['photo_url'] = $encoded_file;
       }
    
    mysqli_stmt_close($stmt);

    return $student;
}

// Set content type to JSON
header('Content-Type: application/json');

// Check if POST variables are set
if (isset($_POST['email']) && isset($_POST['password'])) {
    $email = $_POST['email'];
    $student_password = $_POST['password'];

    $student = check_student_credentials($email, $student_password);

    if ($student) {
        echo json_encode(array("status" => "success", "student" => $student));
    } else {
        echo json_encode(array("status" => "error", "message" => "Student does not exist."));
    }
} else {
    echo json_encode(array("status" => "error", "message" => "Invalid input."));
}
?>


