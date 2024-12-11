<?php
include 'dataBase.php'; // Assuming this file includes database connection logic

function getAllAttendance($student_id) {
    global $conn;

    // Queries for absence and presence
    $absence_query = "SELECT CourseID, COUNT(*) AS absence
                      FROM attendance
                      WHERE students_id = ? AND status <> 'present'
                      GROUP BY CourseID";

    $presence_query = "SELECT teacherID, YearID, CourseID, COUNT(*) AS presence
                       FROM attendance
                       WHERE students_id = ? AND status = 'present'
                       GROUP BY CourseID";

    // Prepare and execute absence query
    $stmt_absence = mysqli_prepare($conn, $absence_query);
    if (!$stmt_absence) {
        return json_encode(array("status" => "error", "message" => "Database error: Unable to prepare absence statement"));
    }
    mysqli_stmt_bind_param($stmt_absence, "s", $student_id);
    mysqli_stmt_execute($stmt_absence);
    $result_absence = mysqli_stmt_get_result($stmt_absence);

    // Prepare and execute presence query
    $stmt_presence = mysqli_prepare($conn, $presence_query);
    if (!$stmt_presence) {
        return json_encode(array("status" => "error", "message" => "Database error: Unable to prepare presence statement"));
    }
    mysqli_stmt_bind_param($stmt_presence, "s", $student_id);
    mysqli_stmt_execute($stmt_presence);
    $result_presence = mysqli_stmt_get_result($stmt_presence);

    // Fetch and process data
    $attendance = array();
    while ($row = mysqli_fetch_assoc($result_presence)) {
        $course_id = $row['CourseID'];

        // Fetch teacher name
        $stmt_teacher = mysqli_prepare($conn, "SELECT name FROM teachers WHERE TeacherID = ?");
        mysqli_stmt_bind_param($stmt_teacher, "s", $row['teacherID']);
        mysqli_stmt_execute($stmt_teacher);
        $result_teacher = mysqli_stmt_get_result($stmt_teacher);
        $teacher = mysqli_fetch_assoc($result_teacher);
        $row['teacherID'] = $teacher['name'];

        // Fetch course title
        $stmt_course = mysqli_prepare($conn, "SELECT name FROM course WHERE CourseID = ?");
        mysqli_stmt_bind_param($stmt_course, "s", $course_id);
        mysqli_stmt_execute($stmt_course);
        $result_course = mysqli_stmt_get_result($stmt_course);
        $course = mysqli_fetch_assoc($result_course);
        $row['id']=$row['CourseID'];
        $row['CourseID'] = $course['name'];

        // Fetch academic year
        $stmt_year = mysqli_prepare($conn, "SELECT enddate FROM academicyear WHERE YearID = ?");
        mysqli_stmt_bind_param($stmt_year, "s", $row['YearID']);
        mysqli_stmt_execute($stmt_year);
        $result_year = mysqli_stmt_get_result($stmt_year);
        $year = mysqli_fetch_assoc($result_year);
        $academic_year = ((int)substr($year['enddate'], 0, 4) - 1) . "-" . substr($year['enddate'], 0, 4);
        $row['YearID'] = $academic_year;

        // Add presence count
        $attendance[$course_id] = $row;
        $attendance[$course_id]['absence'] = 0; // Default absence to 0
    }

    // Process absence data
    while ($row = mysqli_fetch_assoc($result_absence)) {
        $course_id = $row['CourseID'];

        // Fetch course title
        $stmt_course = mysqli_prepare($conn, "SELECT name FROM course WHERE CourseID = ?");
        mysqli_stmt_bind_param($stmt_course, "s", $course_id);
        mysqli_stmt_execute($stmt_course);
        $result_course = mysqli_stmt_get_result($stmt_course);
        $course = mysqli_fetch_assoc($result_course);
        $row['CourseID'] = $course['name'];

        // Add absence count
        if (isset($attendance[$course_id])) {
            $attendance[$course_id]['absence'] = $row['absence'];
        } else {
            $attendance[$course_id] = array(
                'teacherID' => null,
                'YearID' => null,
                'CourseID' => $row['CourseID'],
                'presence' => 0,
                'absence' => $row['absence']
            );
        }
    }

    // Close statements
    mysqli_stmt_close($stmt_absence);
    mysqli_stmt_close($stmt_presence);

    // Close database connection
    mysqli_close($conn);

    // Return JSON response
    return json_encode(array("status" => "success", "attendance" => array_values($attendance)));
}

// Handle POST request
if (isset($_POST['students_id'])) {
    $student_id = $_POST['students_id'];
    echo getAllAttendance($student_id);
}
?>
