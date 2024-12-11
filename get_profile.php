<?php
include 'dataBase.php';

function getProfile($student_id) {
    global $conn;

    // Prepare the SQL statement
    $stmt = mysqli_prepare($conn, "SELECT name, students_id, major, email, YearID, photo_url FROM student WHERE students_id = ?");
    
    if (!$stmt) {
        return json_encode(array("status" => "error", "message" => "SQL error: " . mysqli_error($conn)));
    }

    // Bind the parameter
    mysqli_stmt_bind_param($stmt, 's', $student_id);

    // Execute the statement
    if (!mysqli_stmt_execute($stmt)) {
        mysqli_stmt_close($stmt);
        return json_encode(array("status" => "error", "message" => "Execution failed: " . mysqli_stmt_error($stmt)));
    }

    // Get the result
    $result = mysqli_stmt_get_result($stmt);

    // Fetch the data
    $profile = mysqli_fetch_assoc($result);

    // Close the statement
    mysqli_stmt_close($stmt);

    // Check if profile is found
    if (!$profile) {
        return json_encode(array("status" => "error", "message" => "Profile not found"));
    }

    $file_path = $profile['photo_url'];
    $full_image_path = $_SERVER['DOCUMENT_ROOT'] . "/USM_CODE/dist" ."/".$file_path;
    
    if (file_exists($full_image_path)) {
        $file_data = file_get_contents($full_image_path);
        $encoded_file = base64_encode($file_data);
        // Add image data to course array
        $profile['photo_url'] = $encoded_file;
       }

    $stmt1=mysqli_prepare($conn,"SELECT dept_name FROM departments where dept_id=?");
    mysqli_stmt_bind_param($stmt1,'s',$profile['major']);
    mysqli_stmt_execute($stmt1);
    $result=mysqli_stmt_get_result($stmt1);
    $dep=mysqli_fetch_assoc($result);
    $profile['major']=$dep['dept_name'];

    $stmt2 = mysqli_prepare($conn, "SELECT enddate FROM academicyear WHERE YearID = ?");
    mysqli_stmt_bind_param($stmt2, "s", $profile['YearID']);
    
    // Execute statement 2
    if (!mysqli_stmt_execute($stmt2)) {
        die(json_encode(array('error' => 'Error executing statement 2: ' . htmlspecialchars(mysqli_stmt_error($stmt2)))));
    }
    
    // Get result of statement 2
    $result2 = mysqli_stmt_get_result($stmt2);
    $year = mysqli_fetch_assoc($result2);
    $academicyear = ((int)substr($year['enddate'], 0, 4) - 1) . "-" .substr($year['enddate'], 0, 4);
    $profile['YearID']=$academicyear;
    // Return the JSON encoded profile
    return json_encode(array("status" => "success", "profile" => $profile));
}

// Check if the students_id is set in the POST request
if (isset($_POST['students_id'])) {
    $student_id = $_POST['students_id'];

    // Sanitize the input
    $student_id = filter_var($student_id, FILTER_SANITIZE_STRING);

    header('Content-Type: application/json');
    echo getProfile($student_id);
} else {
    // Handle the case where students_id is not set
    echo json_encode(array("status" => "error", "message" => "students_id not provided"));
}
?>
