<?php
include 'dataBase.php';

function getAttendance($student_id, $course_id) {
    global $conn;

    // Prepare the SQL statement for attendance
    $stmt = mysqli_prepare($conn, "SELECT YearID, CourseID, TeacherID, AttendanceDate, status FROM attendance WHERE students_id = ? and CourseID = ?");
    if (!$stmt) {
        return json_encode(array("status" => "error", "message" => "Database error: Unable to prepare statement"));
    }

    // Bind the parameters
    mysqli_stmt_bind_param($stmt, "ii", $student_id, $course_id);

    // Execute the statement
    if (!mysqli_stmt_execute($stmt)) {
        return json_encode(array("status" => "error", "message" => "Database error: Unable to execute statement"));
    }

    $result = mysqli_stmt_get_result($stmt);

    // Fetch the results into an array
    $attendance = array();
    while ($row = mysqli_fetch_assoc($result)) {
        // Fetch course title
        $stmt1 = mysqli_prepare($conn, "SELECT name FROM course WHERE CourseID = ?");
        mysqli_stmt_bind_param($stmt1, "s", $row['CourseID']);
        mysqli_stmt_execute($stmt1);
        $result1 = mysqli_stmt_get_result($stmt1);
        $course = mysqli_fetch_assoc($result1);

        // Fetch teacher name
        $stmt2 = mysqli_prepare($conn, "SELECT name FROM teachers WHERE TeacherID = ?");
        mysqli_stmt_bind_param($stmt2, "s", $row['TeacherID']);
        mysqli_stmt_execute($stmt2);
        $result2 = mysqli_stmt_get_result($stmt2);
        $teacher = mysqli_fetch_assoc($result2);

        $stmt3 = mysqli_prepare($conn, "SELECT enddate FROM academicyear WHERE YearID = ?");
        mysqli_stmt_bind_param($stmt3, "s", $row['YearID']);
        mysqli_stmt_execute($stmt3);
        $result3 = mysqli_stmt_get_result($stmt3);
        $year = mysqli_fetch_assoc($result3);

        $academicYear = ((int)substr($year['enddate'], 0, 4) - 1) . "-" . substr($year['enddate'], 0, 4);
        $row['YearID'] = $academicYear;

        // Add course title and teacher name to the row
        $row['CourseID'] = $course['name'];
        $row['TeacherID'] = $teacher['name'];

        $attendance[] = $row;

        // Close the statements for course and teacher
        mysqli_stmt_close($stmt1);
        mysqli_stmt_close($stmt2);
    }

    // Close the statement for attendance
    mysqli_stmt_close($stmt);

    // Return the attendance records or a message if none are found
    if (empty($attendance)) {
        return json_encode(array("status" => "error", "message" => "No attendance found"));
    }

    return json_encode(array("status" => "success", "attendance" => $attendance));
}

if (isset($_POST['students_id']) && isset($_POST['CourseID'])) {
    $student_id = $_POST['students_id'];
    $course_id = $_POST['CourseID'];
    echo getAttendance($student_id, $course_id);
} else {
    echo json_encode(array("status" => "error", "message" => "Invalid input."));
}
?>

