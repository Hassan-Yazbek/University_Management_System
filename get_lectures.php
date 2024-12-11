<?php 

include 'database.php';

function getLectures($course_id) {
    global $conn;

    $stmt = mysqli_prepare($conn, "SELECT Lecture_name, File_url FROM lectures l join course c on l.CourseID = c.CourseID where c.name=? ");
    mysqli_stmt_bind_param($stmt, 's', $course_id);
    mysqli_stmt_execute($stmt);
    $result = mysqli_stmt_get_result($stmt);

    $lectures = array();
    while ($row = mysqli_fetch_assoc($result)) {
        $file_path = $row['File_url'];
        $extension = pathinfo($file_path, PATHINFO_EXTENSION);
        $row['extension'] = $extension;

        $full_image_path = $_SERVER['DOCUMENT_ROOT'] . "/USM_CODE/dist" . "/" . $file_path;
        if (file_exists($full_image_path)) {
            $file_data = file_get_contents($full_image_path);
            $encoded_file = base64_encode($file_data);
            $row['File_url'] = $encoded_file;
        } else {
            $row['File_url'] = null;
        }

        $lectures[] = $row; // Append the row to the lectures array
    }

    return $lectures; // Return the lectures array
}

function getCourseName($student_id) {
    global $conn;

    $stmt = mysqli_prepare($conn, "SELECT name FROM course c JOIN enrollment e ON c.CourseID = e.CourseID WHERE students_id = ?");
    mysqli_stmt_bind_param($stmt, 's', $student_id);
    mysqli_stmt_execute($stmt);
    $result = mysqli_stmt_get_result($stmt);

    $courses = array();
    while ($row = mysqli_fetch_assoc($result)) {
        $courses[] = $row; // Append the row to the courses array
    }

    return $courses; // Return the courses array
}

header('Content-Type: application/json');

if (isset($_POST['course_id'])) {
    $course_id = $_POST['course_id'];
    $lectures = getLectures($course_id);
    echo json_encode(['lectures' => $lectures]);

} elseif (isset($_POST['students_id'])) {
    $student_id = $_POST['students_id'];
    $courses = getCourseName($student_id);
    echo json_encode(['courses' => $courses]);
} else {
    echo json_encode(['error' => 'No valid parameters provided.']);
}

?>
