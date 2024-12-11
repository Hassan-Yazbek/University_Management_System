<?php
include 'dataBase.php'; // Assuming this file contains the $conn connection

function getSchedule($student_id){
    global $conn;

    // Prepare the SQL statement
    $query = mysqli_prepare($conn, "
        SELECT 
            S.start_time, 
            S.CourseID, 
            S.class_id, 
            S.teacherID,
            S.YearID,
            S.day_of_week
        FROM 
            schedule S 
        JOIN 
            Course C ON S.CourseID = C.CourseID 
        JOIN 
            Enrollment E ON E.courseID = C.courseID 
        WHERE 
            E.students_id = ?
    ");

    // Check if the statement was prepared correctly
    if (!$query) {
        die(json_encode(['error' => 'Error preparing the statement: ' . htmlspecialchars(mysqli_error($conn))]));
    }

    // Bind the student_id parameter to the statement
    mysqli_stmt_bind_param($query, "s", $student_id);

    // Execute the statement
    if (!mysqli_stmt_execute($query)) {
        die(json_encode(['error' => 'Error executing the statement: ' . htmlspecialchars(mysqli_stmt_error($query))]));
    }

    // Get the result set
    $result = mysqli_stmt_get_result($query);

    // Fetch data into an associative array
    $schedule = [];
    while ($row = mysqli_fetch_assoc($result)) {
        
        $stmt1=mysqli_prepare($conn,"SELECT name from course where CourseID=?");
        mysqli_stmt_bind_param($stmt1,'s',$row['CourseID']);
        mysqli_stmt_execute($stmt1);
        $result1=mysqli_stmt_get_result($stmt1);
        $courseName=mysqli_fetch_assoc($result1);
        $row['CourseID']=$courseName['name'];

        $stmt2 = mysqli_prepare($conn, "SELECT enddate FROM academicyear WHERE YearID = ?");
        mysqli_stmt_bind_param($stmt2, "s", $row['YearID']);
        mysqli_stmt_execute($stmt2);
        $result2 = mysqli_stmt_get_result($stmt2);
        $year = mysqli_fetch_assoc($result2);
        $academicyear = ((int)substr($year['enddate'], 0, 4) - 1) . "-" .substr($year['enddate'], 0, 4);
        $row['YearID']=$academicyear;

        $stmt3=mysqli_prepare($conn,"SELECT name from teachers where teacherID=?");
        mysqli_stmt_bind_param($stmt3,'s',$row['teacherID']);
        mysqli_stmt_execute($stmt3);
        $result3=mysqli_stmt_get_result($stmt3);
        $teacherName=mysqli_fetch_assoc($result3);
        $row['teacherID']=$teacherName['name'];

        $stmt4=mysqli_prepare($conn,"SELECT class_name from classes where class_id=?");
        mysqli_stmt_bind_param($stmt4,'s',$row['class_id']);
        mysqli_stmt_execute($stmt4);
        $result4=mysqli_stmt_get_result($stmt4);
        $className=mysqli_fetch_assoc($result4);
        $row['class_id']=$className['class_name'];

        $schedule[] = $row;
    }

    // Close the statement
    mysqli_stmt_close($query);

    // Return JSON encoded response
    return json_encode(["status" => "success", "schedule" => $schedule]);
}

// Handle the request
if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_POST['students_id'])) {
    $student_id = $_POST['students_id'];
    $schedule = getSchedule($student_id);
    header('Content-Type: application/json');
    echo $schedule;
} else {
    echo json_encode(['error' => 'Student ID not provided']);
}
?>
