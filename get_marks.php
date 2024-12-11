<?php
include 'database.php';

function getMark($student_id) {
    global $conn;

    $stmt = mysqli_prepare($conn,
    "SELECT CourseID, YearID, submitted_at, final_exams_score 
    FROM mark 
    WHERE students_id = ?");
   
   mysqli_stmt_bind_param($stmt, 's', $student_id);
    mysqli_stmt_execute($stmt);
    $result = mysqli_stmt_get_result($stmt);

    $marks = array();

    while ($row = mysqli_fetch_assoc($result)) {
        // Fetch course details
        $stmt1 = mysqli_prepare($conn, "SELECT Code, name FROM course WHERE CourseId = ?");
        mysqli_stmt_bind_param($stmt1, 's', $row['CourseID']);
        mysqli_stmt_execute($stmt1);
        $result1 = mysqli_stmt_get_result($stmt1);
        $courseName = mysqli_fetch_assoc($result1);

        $row['name'] = $courseName['name'];
        $row['CourseID'] = $courseName['Code'];

        // Fetch academic year details
        $stmt2 = mysqli_prepare($conn, "SELECT enddate FROM academicyear WHERE YearID = ?");
        mysqli_stmt_bind_param($stmt2, "s", $row['YearID']);
        mysqli_stmt_execute($stmt2);
        $result2 = mysqli_stmt_get_result($stmt2);
        $year = mysqli_fetch_assoc($result2);

        $academicYear = ((int)substr($year['enddate'], 0, 4) - 1) . "-" . substr($year['enddate'], 0, 4);
        $row['YearID'] = $academicYear;

        // Append row to marks array
        $marks[] = $row;
    }

    // Free result sets
    mysqli_free_result($result);
    mysqli_free_result($result1);
    mysqli_free_result($result2);

    // Close statements
    mysqli_stmt_close($stmt);
    mysqli_stmt_close($stmt1);
    mysqli_stmt_close($stmt2);


    return json_encode(array("status" => "success", "marks" => $marks));
}

if (isset($_POST['students_id'])) {

    $student_id = $_POST['students_id'];
    echo getMark($student_id);

} else {
    // Handle the case where students_id is not set
    echo json_encode(array("status" => "error", "message" => "students_id not provided"));
}

?>
