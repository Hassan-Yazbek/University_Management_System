<?php
include 'dataBase.php';

function getAssignments($student_id) {
    global $conn;

    if (!$conn) {
        return json_encode(array("status" => "error", "message" => "Database connection failed: " . mysqli_connect_error()));
    }

    $query = "
        SELECT 
            A.CourseId, 
            A.description, 
            A.title, 
            A.YearID,
            A.assignment_url 
        FROM 
            assignments A 
        JOIN 
            Course C ON A.CourseID = C.CourseID 
        JOIN 
            Enrollment E ON E.courseID = C.courseID 
        WHERE 
            E.students_id =? and A.assignment_status=1
    ";

    $stmt = mysqli_prepare($conn, $query);
    if (!$stmt) {
        return json_encode(array("status" => "error", "message" => "SQL error: " . mysqli_error($conn)));
    }

    mysqli_stmt_bind_param($stmt, "s", $student_id);
    if (!mysqli_stmt_execute($stmt)) {
        mysqli_stmt_close($stmt);
        return json_encode(array("status" => "error", "message" => "Execution failed: " . mysqli_stmt_error($stmt)));
    }

    $result = mysqli_stmt_get_result($stmt);
    $assignments = array();

    while ($row = mysqli_fetch_assoc($result)) {
        // Fetch course name
        $stmt1 = mysqli_prepare($conn, "SELECT name FROM course WHERE CourseId = ?");
        mysqli_stmt_bind_param($stmt1, 's', $row['CourseId']);
        mysqli_stmt_execute($stmt1);
        $result1 = mysqli_stmt_get_result($stmt1);
        $courseName = mysqli_fetch_assoc($result1);
        $row['CourseId'] = $courseName['name'];

        // Fetch academic year
        $stmt2 = mysqli_prepare($conn, "SELECT enddate FROM academicyear WHERE YearID = ?");
        mysqli_stmt_bind_param($stmt2, "s", $row['YearID']);
        if (!mysqli_stmt_execute($stmt2)) {
            return json_encode(array('status' => 'error', 'message' => 'Error executing statement 2: ' . htmlspecialchars(mysqli_stmt_error($stmt2))));
        }
        $result2 = mysqli_stmt_get_result($stmt2);
        $year = mysqli_fetch_assoc($result2);
        $academicyear = ((int)substr($year['enddate'], 0, 4) - 1) . "-" . substr($year['enddate'], 0, 4);
        $row['YearID'] = $academicyear;

        // Fetch and encode file data
        $file_path = $row['assignment_url'];
        $extension = pathinfo($file_path, PATHINFO_EXTENSION);
        $row['extension'] = $extension;

        $full_image_path = $_SERVER['DOCUMENT_ROOT'] . "/USM_CODE/dist" . "/" . $file_path;
        if (file_exists($full_image_path)) {
            $file_data = file_get_contents($full_image_path);
            $encoded_file = base64_encode($file_data);
            $row['assignment_url'] = $encoded_file;
        } else {
            $row['assignment_url'] = null;
        }

        $assignments[] = $row;
    }

    mysqli_stmt_close($stmt);

    if (empty($assignments)) {
        return json_encode(array("status" => "success", "assignments" => []));
    }

    return json_encode(array("status" => "success", "assignments" => $assignments));
}

function uploadSolution($student_id, $courseName, $solution, $file_extension) {
    global $conn;

    if (!$conn) {
        return json_encode(array("status" => "error", "message" => "Database connection failed: " . mysqli_connect_error()));
    }

    $unique_filename = $student_id . "." . $file_extension;
    $base_url = $_SERVER['DOCUMENT_ROOT'] . "/USM_CODE/dist/uploads/Solution/";
    $full_path = $base_url . $courseName . '/';

    if (!is_dir($full_path)) {
        mkdir($full_path, 0777, true);
    }

    // Set permissions for the directory (optional, use with caution)
    if (!is_writable($full_path)) {
        chmod($full_path, 0777);
    }

    // Create the full path for the final file
    $final_file_path = $full_path . $unique_filename;

    // Decode the base64-encoded file data
    $file_data = base64_decode($solution);

    if ($file_data === false) {
        error_log("Base64 decoding failed for solution: " . $solution);
        return json_encode(array("status" => "error", "message" => "Base64 decoding failed"));
    }

    // Debugging information for file path and permissions
    if (!is_writable($final_file_path)) {
        error_log("File path is not writable: " . $final_file_path);
    } else {
        error_log("File path is writable: " . $final_file_path);
    }
 
    // Save the decoded data to the final file path
    if (file_put_contents($final_file_path, $file_data) === false) {
        error_log("Failed to save file to destination: " . $final_file_path);
        return json_encode(array("status" => "error", "message" => "Failed to save file to destination"));
    }

    // Update the database with the new file path
    $insert_query = "UPDATE assignments SET solution_url = ? WHERE students_id = ?";
    $stmt = mysqli_prepare($conn, $insert_query);
    mysqli_stmt_bind_param($stmt, "ss", $final_file_path, $student_id);

    if (!mysqli_stmt_execute($stmt)) {
        error_log("Database insertion failed: " . mysqli_stmt_error($stmt));
        return json_encode(array("status" => "error", "message" => "Database insertion failed: " . mysqli_stmt_error($stmt)));
    }

    mysqli_stmt_close($stmt);

    return json_encode(array("status" => "success", "message" => "File uploaded and inserted into database successfully"));
}



if (isset($_POST['students_id']) && !isset($_POST['courseName'])) {
    $student_id = $_POST['students_id'];
    header('Content-Type: application/json');
    echo getAssignments($student_id);
} elseif (isset($_POST['students_id']) && isset($_POST['courseName']) && isset($_POST['solution']) && isset($_POST['file_path'])) {
    $student_id = $_POST['students_id'];
    $courseName = $_POST['courseName'];
    $solution = $_POST['solution'];
    $file_extension = $_POST['file_path'];
    header('Content-Type: application/json');
    echo uploadSolution($student_id, $courseName, $solution, $file_extension);
} else {
    echo json_encode(array("status" => "error", "message" => "Required parameters not provided"));
}
?>
