<?php
include 'dataBase.php';

function getExams($student_id){
    global $conn;

    // Prepare the SQL statement to fetch exams
    $stmt = mysqli_prepare($conn, 
        "SELECT 
            X.date, 
            X.CourseID,
            X.YearID,
            X.room,
            X.ExamID
        FROM 
            exam X 
        JOIN 
            Course C ON X.CourseID = C.CourseID 
        JOIN 
            Enrollment E ON E.courseID = C.courseID 
        WHERE 
            E.students_id = ?");

    if ($stmt === false) {
        // Handle errors with preparing the statement
        die(json_encode(array('error' => 'Error preparing the statement: ' . htmlspecialchars(mysqli_error($conn)))));
    }

    // Bind the parameter
    mysqli_stmt_bind_param($stmt, "s", $student_id);

    // Execute the statement
    if (!mysqli_stmt_execute($stmt)) {
        // Handle errors with execution
        die(json_encode(array('error' => 'Error executing the statement: ' . htmlspecialchars(mysqli_stmt_error($stmt)))));
    }

    $result = mysqli_stmt_get_result($stmt);

    // Fetch values and store them in an array
    $exams = array();
    while ($row= mysqli_fetch_assoc($result)) {
        // Prepare statement to fetch course details
        $stmt1 = mysqli_prepare($conn, "SELECT name, Code FROM course WHERE CourseID = ?");
        mysqli_stmt_bind_param($stmt1, "i", $row['CourseID']);
        
        // Execute statement 1
        if (!mysqli_stmt_execute($stmt1)) {
            die(json_encode(array('error' => 'Error executing statement 1: ' . htmlspecialchars(mysqli_stmt_error($stmt1)))));
        }
        
        // Get result of statement 1
        $result1 = mysqli_stmt_get_result($stmt1);
        $course = mysqli_fetch_assoc($result1);
        $row['CourseID'] = $course['Code'] ?? $row['CourseID']; // Check if 'Code' exists
        $row['name'] = $course['name'] ?? ''; // Check if 'name' exists

        // Prepare statement to fetch academic year details
        $stmt2 = mysqli_prepare($conn, "SELECT enddate FROM academicyear WHERE YearID = ?");
        mysqli_stmt_bind_param($stmt2, "s", $row['YearID']);
        
        if (!mysqli_stmt_execute($stmt2)) {
            die(json_encode(array('error' => 'Error executing statement 2: ' . htmlspecialchars(mysqli_stmt_error($stmt2)))));
        }
        $result2 = mysqli_stmt_get_result($stmt2);
        $year = mysqli_fetch_assoc($result2);
        $academicyear = isset($year['enddate']) ? ((int)substr($year['enddate'], 0, 4) - 1) . "-" . substr($year['enddate'], 0, 4) : '';
        $row['YearID'] = $academicyear;

        // Fetch room details
        $stmt3 = mysqli_prepare($conn, "SELECT class_name FROM classes WHERE class_id = ?");
        mysqli_stmt_bind_param($stmt3, 'i', $row['room']);
        mysqli_stmt_execute($stmt3);
        $result3 = mysqli_stmt_get_result($stmt3);
        $room = mysqli_fetch_assoc($result3);
        $row['room'] = $room['class_name'] ?? $row['room']; // Check if 'class_name' exists

        // Fetch grade details
        $stmt4 = mysqli_prepare($conn, "SELECT grade FROM grades WHERE ExamID = ? AND students_id = ?");
        mysqli_stmt_bind_param($stmt4, 'ss', $row['ExamID'], $student_id);
        mysqli_stmt_execute($stmt4);
        $result4 = mysqli_stmt_get_result($stmt4);
        $grade = mysqli_fetch_assoc($result4);
        $row['grade'] = $grade['grade'] ?? null; // Check if 'grade' exists

        $exams[] = $row;

        // Close statements
        mysqli_stmt_close($stmt1);
        mysqli_stmt_close($stmt2);
        mysqli_stmt_close($stmt3);
        mysqli_stmt_close($stmt4);
    }

    // Close the main statement
    mysqli_stmt_close($stmt);

    // Return the exams array as JSON
    return json_encode(array('exams' => $exams));
}

// Check if the request method is POST
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // Get the POST data
    $student_id = isset($_POST['students_id']) ? $_POST['students_id'] : '';

    // Check if student_id is provided
    if (!empty($student_id)) {
        // Call the function and echo the result
        echo getExams($student_id);
    } else {
        // Handle the case where no student_id is provided
        echo json_encode(array('error' => 'No student ID provided'));
    }
} else {
    // Handle the case where the request method is not POST
    echo json_encode(array('error' => 'Invalid request method'));
}
?>
