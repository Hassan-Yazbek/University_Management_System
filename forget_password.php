<?php
include 'dataBase.php';

function addRequest($student_id, $major, $phoneNb, $email, $description) {
    global $conn;

    try {
        // Prepare the SQL statement
        $stmt = mysqli_prepare($conn, "INSERT INTO request (student_id, major, phone_number, email, description) VALUES (?, ?, ?, ?, ?)");
        if ($stmt === false) {
            throw new Exception('MySQL prepare error: ' . mysqli_error($conn));
        }

        // Bind the parameters
        mysqli_stmt_bind_param($stmt, "issss", $student_id, $major, $phoneNb, $email, $description);

        // Execute the statement
        $result = mysqli_stmt_execute($stmt);
        if ($result === false) {
            throw new Exception('MySQL execute error: ' . mysqli_stmt_error($stmt));
        }

        // Close the statement
        mysqli_stmt_close($stmt);

        return true; // Return true on success
    } catch (Exception $e) {
        // Log the error message
        error_log($e->getMessage());

        // Return the error message for debugging
        return $e->getMessage();
    }
}

// Check if request method is POST
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $student_id = $_POST['students_id'];
    $major = $_POST['major'];
    $phoneNb = $_POST['phone_number'];
    $email = $_POST['email'];
    $description = $_POST['description'];

    $result = addRequest($student_id, $major, $phoneNb, $email, $description);

    if ($result === true) {
        echo json_encode(['success' => true, 'message' => 'Request recorded successfully']);
    } else {
        echo json_encode(['success' => false, 'message' => 'Failed to record request: ' . $result]);
    }
}
?>

