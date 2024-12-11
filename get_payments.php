<?php
include 'dataBase.php';  // Ensure this file contains your $conn connection variable

function getPayment($student_id, $method) {
    global $conn;

    // Validate method to prevent SQL injection
    $allowed_methods = ["DETAILS", "OTHER_METHOD"];  // Define allowed methods
    if (!in_array($method, $allowed_methods)) {
        return json_encode(array("status" => "error", "message" => "Invalid method."));
    }

    // Prepare SQL statement based on payment method
    if ($method == "DETAILS") {
        $stmt = mysqli_prepare($conn, "SELECT Method,semester_id, paid_amount, PaymentDate, YearID, RemainingAmount FROM lb_payment WHERE students_id = ?");
    } else {
        $stmt = mysqli_prepare($conn, "SELECT annual_amount, annual_remaining, YearID 
                                        FROM lb_payment 
                                        WHERE students_id = ? 
                                        ORDER BY PaymentID DESC 
                                        LIMIT 1;
                                        ");}

    if (!$stmt) {
        return json_encode(array("status" => "error", "message" => "SQL error: " . mysqli_error($conn)));
    }

    // Bind parameters and execute query
    mysqli_stmt_bind_param($stmt, "s", $student_id);
    mysqli_stmt_execute($stmt);
    
    // Get result set
    $result = mysqli_stmt_get_result($stmt);

    // Fetch student name
    $stmt1 = mysqli_prepare($conn, "SELECT name FROM student WHERE students_id = ?");
    mysqli_stmt_bind_param($stmt1, "s", $student_id);
    mysqli_stmt_execute($stmt1);
    $result1 = mysqli_stmt_get_result($stmt1);
    $name = mysqli_fetch_assoc($result1)['name'];

    // Fetch results into an array
    $payment = array();
    while ($row = mysqli_fetch_assoc($result)) {
        $row['students_id'] = $name;  // Assign the fetched name to each row
        $stmt2 = mysqli_prepare($conn, "SELECT enddate FROM academicyear WHERE YearID = ?");
        mysqli_stmt_bind_param($stmt2, "s", $row['YearID']);
        
        // Execute statement 2
        if (!mysqli_stmt_execute($stmt2)) {
            die(json_encode(array('error' => 'Error executing statement 2: ' . htmlspecialchars(mysqli_stmt_error($stmt2)))));
        }
        
        // Get result of statement 2
        $result2 = mysqli_stmt_get_result($stmt2);
        $year = mysqli_fetch_assoc($result2);
        $academicyear = ((int)substr($year['enddate'], 0, 4) - 1) . "-" . substr($year['enddate'], 0, 4);
        $row['YearID'] = $academicyear;
        $payment[] = $row;
    }

    // Close statements
    mysqli_stmt_close($stmt);
    mysqli_stmt_close($stmt1);

    // Check if payment array is empty
    if (empty($payment)) {
        return json_encode(array("status" => "error", "message" => "No payment found"));
    }

    // Return JSON-encoded payment data
    return json_encode(array("status" => "success", "payment" => $payment));
}

// Check if POST parameters are set
if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_POST['students_id']) && isset($_POST['method'])) {
    $student_id = $_POST['students_id'];
    $method = $_POST['method'];
    echo getPayment($student_id, $method);
} else {
    echo json_encode(array("status" => "error", "message" => "Invalid input."));
}
?>


