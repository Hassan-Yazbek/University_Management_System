<?php
include 'dataBase.php'; // Ensure this file securely handles your database connection

function getEnrollment($student_id) {
    global $conn; // Ensure $conn is your mysqli connection object

    // Prepare the SQL statement with a parameter placeholder
    $stmt = mysqli_prepare($conn, "SELECT C.name, C.Code, C.picture_url FROM course C JOIN Enrollment E ON C.CourseID = E.CourseID WHERE E.students_id = ?");
    
    if ($stmt === false) {
        return json_encode(array("status" => "Error", "message" => "Failed to prepare the SQL statement."));
    }

    // Bind the parameter to the statement
    mysqli_stmt_bind_param($stmt, "s", $student_id);
    
    // Execute the statement
    if (mysqli_stmt_execute($stmt) === false) {
        return json_encode(array("status" => "Error", "message" => "Failed to execute the SQL statement."));
    }

    // Get the result set from the prepared statement
    $result = mysqli_stmt_get_result($stmt);
    
    if ($result === false) {
        return json_encode(array("status" => "Error", "message" => "Failed to get the result set."));
    }

    // Fetch the courses into an array
    $courses = array();
    while ($course = mysqli_fetch_assoc($result)) {
        // Fetch image data as binary
        $image_path = $course['picture_url'];
        $full_image_path = $_SERVER['DOCUMENT_ROOT'] . "/USM_CODE/dist" ."/".$image_path;
        
        if (file_exists($full_image_path)) {
            $image_data = file_get_contents($full_image_path);
            $encoded_image = base64_encode($image_data);
            // Add image data to course array
            $course['picture_url'] = $encoded_image;
        } else {
            $course['picture_url'] = null; // Handle missing images
        }

        // Append course details to courses array
        $courses[] = $course;
    }

    // Close the statement
    mysqli_stmt_close($stmt);

    // Return the result as a JSON-encoded array
    return json_encode(array("status" => "Success", "courses" => $courses));
}

// Check if students_id is provided via POST
if (isset($_POST['students_id'])) {
    $student_id = $_POST['students_id'];
    
    // Validate student_id (Example: basic numeric validation)
    if (!is_numeric($student_id)) {
        echo json_encode(array("status" => "Error", "message" => "Invalid student ID."));
        exit;
    }
    
    // Call function to fetch enrollment
    echo getEnrollment($student_id);
} else {
    echo json_encode(array("status" => "Error", "message" => "Missing student ID."));
}
?>

