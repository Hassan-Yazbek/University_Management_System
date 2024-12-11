-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 23, 2024 at 08:23 PM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.0.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `university system project`
--

-- --------------------------------------------------------

--
-- Table structure for table `academicyear`
--

CREATE TABLE `academicyear` (
  `YearID` int(10) NOT NULL,
  `startdate` date DEFAULT NULL,
  `enddate` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `academicyear`
--

INSERT INTO `academicyear` (`YearID`, `startdate`, `enddate`) VALUES
(1, '2024-01-01', '2025-01-01'),
(18, '2025-10-23', '2026-10-23');

-- --------------------------------------------------------

--
-- Table structure for table `additional_fees`
--

CREATE TABLE `additional_fees` (
  `PaymentID` int(11) NOT NULL,
  `students_id` bigint(20) DEFAULT NULL,
  `Method` varchar(255) DEFAULT NULL,
  `paid_amount` decimal(10,2) DEFAULT 300.00,
  `Total_amount` decimal(10,2) NOT NULL,
  `annual_amount` decimal(10,2) NOT NULL,
  `PaymentDate` date DEFAULT NULL,
  `YearID` int(11) DEFAULT NULL,
  `RemainingAmount` decimal(10,2) DEFAULT NULL,
  `Total_remaining` decimal(10,2) NOT NULL,
  `annual_remaining` decimal(10,2) NOT NULL,
  `semester_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `additional_fees`
--

INSERT INTO `additional_fees` (`PaymentID`, `students_id`, `Method`, `paid_amount`, `Total_amount`, `annual_amount`, `PaymentDate`, `YearID`, `RemainingAmount`, `Total_remaining`, `annual_remaining`, `semester_id`) VALUES
(52, 30, 'cash', '100.00', '100.00', '100.00', '2024-07-07', 1, '200.00', '200.00', '500.00', 51),
(53, 30, 'cash', '100.00', '200.00', '200.00', '2024-07-07', 1, '100.00', '100.00', '400.00', 51),
(54, 30, 'cash', '100.00', '300.00', '300.00', '2024-07-07', 1, '0.00', '0.00', '300.00', 51),
(59, 7, 'cash', '100.00', '100.00', '100.00', '2024-07-17', 1, '200.00', '200.00', '500.00', 51);

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `admin_id` int(11) NOT NULL,
  `name` varchar(30) NOT NULL,
  `email` varchar(30) NOT NULL,
  `password` varchar(255) NOT NULL,
  `photo_name` varchar(255) DEFAULT NULL,
  `photo_url` varchar(255) NOT NULL DEFAULT 'uploads/admins',
  `YearID` int(11) DEFAULT NULL,
  `staffID` int(11) DEFAULT NULL,
  `dept_id` int(11) DEFAULT NULL,
  `phone` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`admin_id`, `name`, `email`, `password`, `photo_name`, `photo_url`, `YearID`, `staffID`, `dept_id`, `phone`) VALUES
(10, 'Khaled Zein', 'khaledRW2@Iul.edu.lb Zein ', 'Khaled#123', 'stack-of-three-black-hot-stones-spa-salon-vector-16122479.jpg', 'uploads/admins/stack-of-three-black-hot-stones-spa-salon-vector-16122479.jpg', 1, 10, 1, '71155179'),
(20, 'Rabih Wazneh', 'RW2@Iul.edu.lb', 'RW22024.Iul', '436316675_10161672938065295_3669042275330485765_n', 'uploads/admins/user.jpg', 1, 27, 1, '78981155'),
(21, 'Admin3', 'Boss@mail.com', 'BossAdmin', 'R (1)', 'uploads/admins/boy.png', 1, 66, 2, '81155149'),
(22, 'admin4', 'user@mail.com', 'passwORD123', 'R (1)', 'uploads/admins/boy.png', 1, 69, 2, '76743716');

-- --------------------------------------------------------

--
-- Table structure for table `articles`
--

CREATE TABLE `articles` (
  `id` int(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  `date` date NOT NULL,
  `source` varchar(200) NOT NULL,
  `author` varchar(200) NOT NULL,
  `article` text NOT NULL,
  `photo_url` varchar(255) NOT NULL DEFAULT 'uploads/lectures',
  `photo_name` varchar(255) NOT NULL,
  `category` varchar(255) NOT NULL,
  `operator` int(255) NOT NULL DEFAULT 1,
  `status` bit(1) DEFAULT NULL,
  `action` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `articles`
--

INSERT INTO `articles` (`id`, `title`, `date`, `source`, `author`, `article`, `photo_url`, `photo_name`, `category`, `operator`, `status`, `action`) VALUES
(15, 'Academic year start', '2024-06-26', 'IUL', 'Admin ', '               You are welcome to our family, The academic year start \r\n\r\n\r\n\r\n\r\n\r\n', 'uploads/lectures/OIP.jpeg', 'OIP', 'Academic', 20, b'1', ''),
(16, 'Colleages meeting', '2024-06-26', 'IUL', 'Head of computer science department', 'We will make a meeting for teacher before the holiday ', 'uploads/lectures/colleagues.jpg', 'colleagues', 'Teachers ', 20, b'1', ''),
(17, 'Grades time', '2024-06-26', 'IUL', 'Head of computer science department', 'The grades will distribute next week', 'uploads/lectures/Grades1.jpeg', 'Grades1', 'students', 20, b'1', ''),
(18, 'Registration time', '2024-09-26', 'IUL', 'Admin ', 'The registration for the fall semester starts', 'uploads/lectures/grades.jpeg', 'grades', 'students', 20, b'1', ''),
(19, 'Registration time', '2024-08-28', 'IUL', 'Admin ', 'Our doors are open to start build your dreams, fall semester registration starting 28 August ', 'uploads/lectures/grades.jpeg', 'grades', 'Academic', 20, b'1', '');

-- --------------------------------------------------------

--
-- Table structure for table `assignments`
--

CREATE TABLE `assignments` (
  `assignment_id` int(10) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `assignment_status` varchar(255) NOT NULL DEFAULT '0',
  `solution_url` varchar(255) NOT NULL DEFAULT 'uploads/Solution/student-solution ',
  `assignment_url` varchar(255) DEFAULT NULL,
  `students_id` bigint(11) DEFAULT NULL,
  `CourseID` int(11) DEFAULT NULL,
  `YearID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `assignments`
--

INSERT INTO `assignments` (`assignment_id`, `title`, `description`, `assignment_status`, `solution_url`, `assignment_url`, `students_id`, `CourseID`, `YearID`) VALUES
(11, 'Assinment for lecture 1', 'solve this will be graded', '1', 'uploads/Solution/students_solution/software engineering/11/7/Solution of ERD Project 4 Physician.pdf', 'uploads/assignment/DFD Project 1 - Automatic Ticket Machine with French.pdf', NULL, 1, 1),
(12, 'Assinment for lecture 1', ' new assignment', '1', 'uploads/Solution/students_solution/operating system/12/7/Solution of ERD Project 4 Physician.pdf', 'uploads/assignment/New Text Document.txt', NULL, 2, 1),
(13, 'Assinment for lecture 1', 'solve this assignment ', '1', 'uploads/Solution/students_solution/Artificial Inelligence/13/7/Solution of ERD Project 4 Physician.pdf', 'uploads/assignment/IMG-20231118-WA0012.jpg', NULL, 18, 1),
(14, 'Assinment for lecture 1', ' try to understand and solve this assignment ', '1', 'uploads/Solution/students_solution/Internet of Things/14/7/DFD Project 1 - Automatic Ticket Machine with French_1_1_9.pdf', 'uploads/assignment/IMG-20231202-WA0035.jpg', NULL, 35, 1),
(15, 'Assinment for lecture 1', 'solve this before the end of the lecture time', '1', 'uploads/Solution/students_solution/software engineering/30/DFD Project 1 - Automatic Ticket Machine with French_1_1_1.pdf', 'uploads/assignment/L1.jpg', NULL, 21, 1),
(16, 'Assinment  1-b', 'this is the assignment two for the first lecture ', '1', 'uploads/Solution/students_solution/software engineering/30/DFD Project 1 - Automatic Ticket Machine with French_1_1_9.pdf', 'uploads/assignment/Assignment2.jpg', NULL, 21, 1),
(17, 'Assinment for lecture 1', 'solve the assignment ', '0', 'uploads/Solution/student-solution ', 'uploads/assignment/L1.jpg', NULL, 16, 1),
(18, 'Assinment for lecture 1', 'hello this is an Assignment for you related for the lecture 1', '0', 'uploads/Solution/student-solution ', 'uploads/assignment/Assignment2.jpg', NULL, 53, 1),
(19, 'Assignment for lecture 1', 'graded assignment for lecture one', '0', 'uploads/Solution/student-solution ', 'uploads/assignment/session 1.pdf', NULL, 50, 1);

-- --------------------------------------------------------

--
-- Table structure for table `attendance`
--

CREATE TABLE `attendance` (
  `Attendance_id` int(11) NOT NULL,
  `students_id` bigint(20) DEFAULT NULL,
  `AttendanceDate` datetime DEFAULT NULL,
  `TeacherID` bigint(20) DEFAULT NULL,
  `CourseID` int(11) DEFAULT NULL,
  `status` varchar(10) NOT NULL,
  `YearID` int(11) NOT NULL,
  `lecture_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `attendance`
--

INSERT INTO `attendance` (`Attendance_id`, `students_id`, `AttendanceDate`, `TeacherID`, `CourseID`, `status`, `YearID`, `lecture_id`) VALUES
(23, 30, '2024-07-13 19:35:47', 30, 21, 'present', 1, 17),
(24, 30, '2024-07-13 19:36:24', 30, 21, 'present', 1, 18),
(25, 30, '2024-07-10 14:21:01', 26, 21, 'absent ', 1, 15),
(26, 28, '2024-07-06 14:22:48', 30, 34, 'absent ', 1, 5),
(27, 28, '2024-11-21 14:24:52', 31, 4, 'present ', 1, 17),
(28, 25, '2024-07-17 12:03:00', 28, 1, 'present', 1, 13),
(29, 28, '2024-07-17 12:03:00', 28, 1, 'present', 1, 13),
(30, 30, '2024-07-17 12:03:00', 28, 1, 'present', 1, 13),
(31, 25, '2024-07-17 20:26:10', 28, 1, 'present', 1, 13),
(32, 28, '2024-07-17 20:26:10', 28, 1, 'present', 1, 13),
(33, 30, '2024-07-17 20:26:10', 28, 1, 'present', 1, 13),
(34, 7, '2024-07-17 20:26:10', 28, 1, 'present', 1, 13),
(35, 27, '2024-07-17 20:27:35', 28, 44, 'absent', 1, 21),
(36, 7, '2024-07-17 20:27:35', 28, 44, 'absent', 1, 21),
(37, 25, '2024-07-19 02:04:52', 32, 50, 'present', 1, 24),
(38, 30, '2024-07-19 02:13:24', 31, 36, 'present', 1, 25),
(40, 25, '2024-07-21 23:51:16', 30, 54, 'present', 1, 27);

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

CREATE TABLE `categories` (
  `id` int(11) NOT NULL,
  `category_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`id`, `category_name`) VALUES
(2, 'students'),
(3, 'Teachers '),
(4, 'Academic');

-- --------------------------------------------------------

--
-- Table structure for table `classes`
--

CREATE TABLE `classes` (
  `class_id` int(11) NOT NULL,
  `class_name` varchar(255) DEFAULT NULL,
  `YearID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `classes`
--

INSERT INTO `classes` (`class_id`, `class_name`, `YearID`) VALUES
(1, 'S_201', 1),
(2, 'S_202', 1),
(3, 'S_203', 1),
(4, 'S_204', 1),
(5, 'GH123', 1),
(7, 'Online', 1);

-- --------------------------------------------------------

--
-- Table structure for table `course`
--

CREATE TABLE `course` (
  `CourseID` int(255) NOT NULL,
  `curriculum_year_id` int(11) DEFAULT NULL,
  `name` varchar(30) NOT NULL,
  `Code` varchar(255) NOT NULL,
  `credits` int(255) NOT NULL,
  `credit_price` varchar(255) NOT NULL,
  `picture_url` varchar(255) NOT NULL DEFAULT 'uploads/Courses',
  `YearID` int(11) DEFAULT NULL,
  `status` varchar(2) NOT NULL,
  `dept_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `course`
--

INSERT INTO `course` (`CourseID`, `curriculum_year_id`, `name`, `Code`, `credits`, `credit_price`, `picture_url`, `YearID`, `status`, `dept_id`) VALUES
(1, 3, 'software engineering', 'C1101', 4, '600000', 'uploads/Courses/SE.jpg', 1, '1', 1),
(2, 3, 'operating system ', 'C3102', 5, '600000', 'uploads/Courses/OS.jpg', 1, '1', 1),
(4, NULL, 'object oriented programming ', 'C1103', 5, '600000', 'uploads/Courses/c3.jpeg', 1, '1', 1),
(13, 1, 'General English 1', 'C1104', 3, '600000', 'uploads/Courses/C1.jpeg', 1, '1', 1),
(16, 1, 'introduction to informatic', 'C1105', 3, '600000', 'uploads/Courses/c4.png', 1, '1', 1),
(17, 1, 'Discret math', 'C1106', 5, '600000', 'uploads/Courses/c7.jpeg', 1, '1', 1),
(18, 3, 'Artificial Inelligence ', 'C1107', 3, '600000', 'uploads/Courses/AI.jpeg', 1, '1', 1),
(19, NULL, 'Network Essentials', 'C1108', 3, '600000', 'uploads/Courses/C1.jpeg', 1, '1', 1),
(20, NULL, 'Training(10 weeks)', 'C1109', 6, '600000', 'uploads/Courses/c4.png', 1, '1', 1),
(21, 3, 'Visual programming', 'C1110', 4, '600000', 'uploads/Courses/c6.jpg', 1, '1', 1),
(22, NULL, 'Linux basics', 'C1111', 3, '600000', 'uploads/Courses/c8.jpeg', 1, '1', 1),
(23, NULL, 'Logic design', 'C1112', 5, '600000', 'uploads/Courses/C2.jpeg', 1, '1', 1),
(24, 1, 'Software applications', 'C1113', 3, '600000', 'uploads/Courses/c5.jpeg', 1, '1', 1),
(25, 1, 'Introductory calculas', 'C1114', 5, '600000', 'uploads/Courses/c7.jpeg', 1, '1', 1),
(31, 1, 'Basic Accounting', 'C1115', 3, '600000', 'uploads/Courses/Accounting.jpeg', 1, '1', 1),
(32, 1, 'General Physics ', 'C1116', 4, '600000', 'uploads/Courses/Physics.jpeg', 1, '1', 1),
(33, 1, 'Culture and religions', 'C1117', 2, '600000', 'uploads/Courses/Culture.jpg', 1, '1', 1),
(34, 3, 'Project Managment', 'C1118', 5, '600000', 'uploads/Courses/PM.jpg', 1, '1', 1),
(35, 3, 'Internet of Things', 'C1119', 5, '600000', 'uploads/Courses/IOT.jpeg', 1, '1', 1),
(36, 3, 'Mobile Application', 'C1120', 4, '600000', 'uploads/Courses/mobile_application.jpg', 1, '1', 1),
(42, 1, 'Linear Algerbra ', 'C1121', 5, '600000', 'uploads/Courses/c7.jpeg', 1, '1', 1),
(43, 1, 'General English 2', 'c1122', 3, '600000', 'uploads/Courses/C1.jpeg', 1, '1', 1),
(44, 1, 'Introduction to Programming', 'c1123', 4, '600000', 'uploads/Courses/c8.jpeg', 1, '1', 1),
(45, 1, 'Calculus 2', 'c1124', 5, '600000', 'uploads/Courses/c7.jpeg', 1, '1', 1),
(46, 1, 'Database fundamentals', 'C1125', 5, '600000', 'uploads/Courses/database.jpg', 1, '1', 1),
(47, 1, 'marketing ', 'C1126', 3, '600000', 'uploads/Courses/marketing.jpeg', 1, '1', 1),
(48, 2, 'Database Applications', 'c1127', 5, '600000', 'uploads/Courses/database.jpg', 1, '1', 1),
(49, 2, 'Advanced Programming', 'c1128', 5, '600000', 'uploads/Courses/c++.jpeg', 1, '1', 1),
(50, 2, 'operation research', 'c1129', 4, '600000', 'uploads/Courses/c3.jpeg', 1, '1', 1),
(51, 2, 'Statistics and Probability', 'SLMT108', 5, '600000', 'uploads/Courses/c7.jpeg', 1, '1', 1),
(52, 2, 'Data Communications', 'CSC334', 5, '600000', 'uploads/Courses/IOT.jpeg', 1, '1', 1),
(53, 2, 'Oral English', 'ENG222', 4, '600000', 'uploads/Courses/English.jpeg', 1, '1', 1),
(54, 2, 'Data Structures', 'CSC340', 5, '600000', 'uploads/Courses/c8.jpeg', 1, '1', 1),
(55, 2, 'Web Applications ', 'SCSC342', 3, '600000', 'uploads/Courses/c6.jpg', 1, '1', 1),
(56, 2, 'Numerical Analysis', 'MAT343', 5, '600000', 'uploads/Courses/c7.jpeg', 1, '1', 1),
(57, 2, 'Network Essentials', 'CSC341', 3, '600000', 'uploads/Courses/SE.jpg', 1, '1', 1),
(58, 3, 'Asp.Net', 'CSC452', 5, '600000', 'uploads/Courses/c5.jpeg', 1, '1', 1),
(59, 3, 'Entrepreneur', 'CSC490', 5, '600000', 'uploads/Courses/PM.jpg', 1, '0', 1),
(60, 3, 'Final Project ', 'MGT225', 5, '600000', 'uploads/Courses/FP.jpeg', 1, '1', 1);

-- --------------------------------------------------------

--
-- Table structure for table `curriculum_year`
--

CREATE TABLE `curriculum_year` (
  `curriculum_year_id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `curriculum_year`
--

INSERT INTO `curriculum_year` (`curriculum_year_id`, `name`, `created_at`) VALUES
(1, 'First Year', '2024-07-06 11:34:08'),
(2, 'Second Year', '2024-07-06 11:34:08'),
(3, 'Third Year', '2024-07-06 11:34:08'),
(4, 'Fourth Year', '2024-07-06 11:34:08'),
(5, 'Fifth Year', '2024-07-06 11:34:08');

-- --------------------------------------------------------

--
-- Table structure for table `departments`
--

CREATE TABLE `departments` (
  `dept_id` int(11) NOT NULL,
  `dept_name` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `departments`
--

INSERT INTO `departments` (`dept_id`, `dept_name`) VALUES
(1, 'Computer Science'),
(2, 'Graphic Design');

-- --------------------------------------------------------

--
-- Table structure for table `enrollment`
--

CREATE TABLE `enrollment` (
  `students_id` bigint(20) NOT NULL,
  `CourseID` int(11) NOT NULL,
  `YearID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `enrollment`
--

INSERT INTO `enrollment` (`students_id`, `CourseID`, `YearID`) VALUES
(7, 1, 1),
(7, 2, 1),
(7, 17, 1),
(7, 18, 1),
(7, 21, 1),
(7, 23, 1),
(7, 34, 1),
(7, 35, 1),
(7, 36, 1),
(7, 43, 1),
(7, 44, 1),
(7, 45, 1),
(7, 46, 1),
(7, 47, 1),
(25, 1, 1),
(25, 2, 1),
(25, 4, 1),
(25, 20, 1),
(25, 48, 1),
(25, 49, 1),
(25, 50, 1),
(25, 51, 1),
(25, 52, 1),
(25, 53, 1),
(25, 54, 1),
(25, 56, 1),
(27, 17, 1),
(27, 23, 1),
(27, 43, 1),
(27, 44, 1),
(27, 45, 1),
(27, 46, 1),
(27, 47, 1),
(28, 1, 1),
(28, 2, 1),
(28, 58, 1),
(28, 59, 1),
(28, 60, 1),
(30, 1, 1),
(30, 2, 1),
(30, 18, 1),
(30, 21, 1),
(30, 34, 1),
(30, 35, 1),
(30, 36, 1);

-- --------------------------------------------------------

--
-- Table structure for table `exam`
--

CREATE TABLE `exam` (
  `ExamID` int(10) NOT NULL,
  `exam_type` varchar(255) NOT NULL,
  `mark` decimal(10,2) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `File_url` varchar(255) NOT NULL,
  `YearID` int(11) DEFAULT NULL,
  `room` int(11) DEFAULT NULL,
  `CourseID` int(11) DEFAULT NULL,
  `exam_type_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `exam`
--

INSERT INTO `exam` (`ExamID`, `exam_type`, `mark`, `date`, `File_url`, `YearID`, `room`, `CourseID`, `exam_type_id`) VALUES
(37, 'Final Exam', '60.00', '2024-07-04', 'uploads/exams/lab 5.pdf', 1, 5, 1, 1),
(38, 'Partial Exam', '40.00', '2024-12-18', 'uploads/exams/OS exam.pdf', 1, 5, 1, 2),
(55, 'Partial Exam', '40.00', '2024-07-23', 'uploads/exams/OS exam.pdf', 1, 1, 2, 2);

-- --------------------------------------------------------

--
-- Table structure for table `exam_type`
--

CREATE TABLE `exam_type` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `exam_type`
--

INSERT INTO `exam_type` (`id`, `name`) VALUES
(1, 'Final Exam'),
(2, 'Partial Exam');

-- --------------------------------------------------------

--
-- Table structure for table `grades`
--

CREATE TABLE `grades` (
  `grade_id` int(11) NOT NULL,
  `students_id` bigint(20) DEFAULT NULL,
  `ExamID` int(11) DEFAULT NULL,
  `grade` decimal(5,2) DEFAULT NULL,
  `CourseID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `grades`
--

INSERT INTO `grades` (`grade_id`, `students_id`, `ExamID`, `grade`, `CourseID`) VALUES
(1, 7, 37, '50.00', 1),
(2, 7, 38, '35.00', 1),
(3, 30, 37, '45.00', 1),
(4, 30, 38, '30.00', 1),
(5, 27, 37, '30.00', 1),
(6, 27, 38, '30.00', 1),
(11, 28, 37, '30.00', 1),
(12, 25, 38, '30.00', 1),
(13, 30, 55, '32.00', 2);

-- --------------------------------------------------------

--
-- Table structure for table `lb_payment`
--

CREATE TABLE `lb_payment` (
  `PaymentID` int(11) NOT NULL,
  `students_id` bigint(20) DEFAULT NULL,
  `Method` varchar(50) DEFAULT 'Cash',
  `paid_amount` decimal(10,2) DEFAULT NULL,
  `Total_amount` decimal(10,2) NOT NULL,
  `annual_amount` decimal(10,2) NOT NULL,
  `PaymentDate` date DEFAULT NULL,
  `YearID` int(11) DEFAULT NULL,
  `RemainingAmount` decimal(10,2) DEFAULT NULL,
  `Total_remaining` decimal(10,2) NOT NULL,
  `annual_remaining` decimal(10,2) NOT NULL,
  `semester_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `lb_payment`
--

INSERT INTO `lb_payment` (`PaymentID`, `students_id`, `Method`, `paid_amount`, `Total_amount`, `annual_amount`, `PaymentDate`, `YearID`, `RemainingAmount`, `Total_remaining`, `annual_remaining`, `semester_id`) VALUES
(157, 30, 'cash', '3000000.00', '3000000.00', '3000000.00', '2024-07-07', 1, '18000000.00', '15000000.00', '21000000.00', 51),
(158, 30, 'cash', '3000000.00', '6000000.00', '6000000.00', '2024-07-07', 1, '18000000.00', '6000000.00', '30000000.00', 51),
(159, 30, 'cash', '3000000.00', '9000000.00', '9000000.00', '2024-07-07', 1, '18000000.00', '9000000.00', '27000000.00', 51),
(160, 30, 'cash', '4000000.00', '13000000.00', '13000000.00', '2024-07-07', 1, '18000000.00', '13000000.00', '23000000.00', 51),
(161, 30, 'cash', '5000000.00', '18000000.00', '18000000.00', '2024-07-07', 1, '18000000.00', '18000000.00', '18000000.00', 51),
(172, 28, 'cash', '8000000.00', '8000000.00', '8000000.00', '2024-07-17', 1, '18000000.00', '8000000.00', '28000000.00', 51),
(174, 27, 'cash', '8000000.00', '8000000.00', '8000000.00', '2024-07-17', 1, '18000000.00', '8000000.00', '28000000.00', 53),
(177, 30, 'cash', '8000000.00', '8000000.00', '26000000.00', '2024-07-17', 1, '18000000.00', '10000000.00', '10000000.00', 51),
(178, 30, 'cash', '10000000.00', '10000000.00', '36000000.00', '2024-07-17', 1, '18000000.00', '0.00', '0.00', 51),
(179, 7, 'cash', '10000000.00', '10000000.00', '10000000.00', '2024-07-17', 1, '8000000.00', '10000000.00', '26000000.00', 51);

-- --------------------------------------------------------

--
-- Table structure for table `lectures`
--

CREATE TABLE `lectures` (
  `id` int(11) NOT NULL,
  `startTime` datetime NOT NULL,
  `endTime` datetime NOT NULL,
  `CourseID` int(11) DEFAULT NULL,
  `Lecture_name` varchar(255) NOT NULL,
  `File_url` varchar(255) NOT NULL,
  `YearID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `lectures`
--

INSERT INTO `lectures` (`id`, `startTime`, `endTime`, `CourseID`, `Lecture_name`, `File_url`, `YearID`) VALUES
(5, '2024-07-01 19:14:00', '2024-07-31 19:14:00', 24, 'lecture 3', 'uploads/lectures/3.pptx', 1),
(7, '2024-08-07 19:15:00', '2024-08-14 19:15:00', 22, 'Lecture 4', 'uploads/lectures/4.pptx', 1),
(13, '2024-10-16 12:32:00', '2024-10-16 12:32:00', 1, 'Lecture 1', 'uploads/lectures/session 1.pdf', 1),
(14, '2024-10-16 12:38:00', '2024-10-16 12:38:00', 2, 'Lecture 2', 'uploads/lectures/2.pptx', 1),
(15, '2024-10-15 12:40:00', '2024-07-23 12:40:00', 18, 'Lecture 2', 'uploads/lectures/CH1.pptx', 1),
(16, '2024-08-01 12:44:00', '2024-08-08 12:44:00', 35, 'Lecture 1', 'uploads/lectures/Project- IoT.pdf', 1),
(17, '2024-10-15 15:51:00', '2024-10-22 15:51:00', 21, 'Lecture 1', 'uploads/lectures/chapter1_Updated.pptx', 1),
(18, '2024-10-22 22:44:00', '2024-10-29 22:44:00', 21, 'lecture 2', 'uploads/lectures/2.pptx', 1),
(19, '2024-07-17 20:10:00', '2024-07-31 20:10:00', 1, '', 'uploads/lectures/4.pptx', 1),
(20, '2024-07-17 20:10:00', '2024-07-24 20:11:00', 16, '', 'uploads/lectures/chapter1_Updated.pptx', 1),
(21, '2024-07-23 20:11:00', '2024-07-30 20:11:00', 44, '', 'uploads/lectures/2.pptx', 1),
(22, '2024-07-17 20:22:00', '2024-07-31 20:22:00', 44, '', 'uploads/lectures/4.pptx', 1),
(23, '2024-07-18 23:41:00', '2024-07-25 23:41:00', 53, '', 'uploads/lectures/01. Practise English on your own author Minitoba .pdf', 1),
(24, '2024-10-15 01:42:00', '2024-10-28 01:42:00', 50, '', 'uploads/lectures/Introduction to Optimization Modeling.pdf', 1),
(25, '2024-10-15 02:12:00', '2024-10-29 02:12:00', 36, '', 'uploads/lectures/Week3-Android-Intent.pptx', 1),
(27, '2024-10-21 23:50:00', '2024-10-28 23:50:00', 54, '', 'uploads/lectures/sqlconnection.pptx', 1);

-- --------------------------------------------------------

--
-- Table structure for table `mark`
--

CREATE TABLE `mark` (
  `MarkID` int(10) NOT NULL,
  `final_exams_score` varchar(255) NOT NULL DEFAULT '0',
  `students_id` bigint(255) DEFAULT NULL,
  `ExamID` int(10) DEFAULT NULL,
  `CourseID` int(11) NOT NULL,
  `YearID` int(11) DEFAULT NULL,
  `submitted_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `mark`
--

INSERT INTO `mark` (`MarkID`, `final_exams_score`, `students_id`, `ExamID`, `CourseID`, `YearID`, `submitted_at`) VALUES
(3, '75.00', 7, 38, 1, 1, '2024-07-08 11:18:42'),
(5, '70.00', 27, 38, 1, 1, '2024-07-08 14:19:05'),
(6, '125.00', 7, 37, 1, 1, '2024-07-16 11:51:10'),
(7, '155.00', 7, 38, 1, 1, '2024-07-16 11:51:21'),
(8, '100.00', 27, 37, 1, 1, '2024-07-16 11:51:29'),
(9, '85.00', 7, 37, 1, 1, '2024-07-21 17:43:04'),
(10, '27.00', 30, 55, 2, 1, '2024-07-23 17:55:24'),
(16, '75.00', 30, 37, 1, 1, '2024-07-23 18:22:02');

-- --------------------------------------------------------

--
-- Table structure for table `registration`
--

CREATE TABLE `registration` (
  `register_id` int(11) NOT NULL,
  `name` varchar(30) NOT NULL,
  `email` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL,
  `department_id` int(11) DEFAULT NULL,
  `YearID` int(11) DEFAULT NULL,
  `staffID` int(11) DEFAULT NULL,
  `phone` varchar(255) NOT NULL,
  `admin_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `registration`
--

INSERT INTO `registration` (`register_id`, `name`, `email`, `password`, `department_id`, `YearID`, `staffID`, `phone`, `admin_id`) VALUES
(10, 'ahmad ', 'AhmadAlkadri2002@mail.com', 'Ahmad%54321', 1, 1, 94, '76743718', 20);

-- --------------------------------------------------------

--
-- Table structure for table `request`
--

CREATE TABLE `request` (
  `ReqID` int(11) NOT NULL,
  `student_id` int(255) NOT NULL,
  `major` varchar(100) DEFAULT NULL,
  `phone_number` varchar(20) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `request`
--

INSERT INTO `request` (`ReqID`, `student_id`, `major`, `phone_number`, `email`, `description`) VALUES
(6, 30, 'computer Science ', '71879101', 'HY@Iul.edu.lb', 'I forget my password');

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE `role` (
  `roleID` int(11) NOT NULL,
  `description` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`roleID`, `description`) VALUES
(1, 'admin'),
(2, 'teacher'),
(3, 'registration');

-- --------------------------------------------------------

--
-- Table structure for table `schedule`
--

CREATE TABLE `schedule` (
  `schedule_id` int(11) NOT NULL,
  `CourseID` int(11) DEFAULT NULL,
  `teacherID` bigint(20) DEFAULT NULL,
  `section_id` int(11) DEFAULT NULL,
  `start_time` time(6) NOT NULL,
  `end_time` time(6) NOT NULL,
  `day_of_week` varchar(255) NOT NULL,
  `YearID` int(11) DEFAULT NULL,
  `class_id` int(11) DEFAULT NULL,
  `department_id` int(11) DEFAULT NULL,
  `curriculum_year_id` int(11) DEFAULT NULL,
  `semester_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `schedule`
--

INSERT INTO `schedule` (`schedule_id`, `CourseID`, `teacherID`, `section_id`, `start_time`, `end_time`, `day_of_week`, `YearID`, `class_id`, `department_id`, `curriculum_year_id`, `semester_name`) VALUES
(22, 1, 22, 20, '08:00:00.000000', '10:30:00.000000', 'monday', 1, 1, 1, 3, 'Fall Semester'),
(23, 21, 30, 20, '11:00:00.000000', '01:30:00.000000', 'monday', 1, 1, 1, 3, 'Fall Semester'),
(24, 2, 27, 22, '14:00:00.000000', '16:00:00.000000', 'monday', 1, 2, 1, 3, 'Fall Semester'),
(25, 35, 25, 26, '08:00:00.000000', '10:30:00.000000', 'tuesday', 1, 4, 1, 3, 'Fall Semester'),
(26, 18, 26, 28, '11:00:00.000000', '01:30:00.000000', 'tuesday', 1, 5, 1, 3, 'Fall Semester'),
(27, 2, 27, 26, '02:00:00.000000', '15:30:00.000000', 'tuesday', 1, 4, 1, 3, 'Fall Semester'),
(28, 34, 41, 29, '08:00:00.000000', '10:30:00.000000', 'thursday', 1, 7, 1, 1, 'Fall Semester'),
(30, 45, 32, 22, '08:00:00.000000', '10:30:00.199000', 'monday', 1, 1, 1, 1, 'Spring Semester'),
(31, 46, 28, 24, '11:00:00.000000', '01:30:00.000000', 'monday', 1, 3, 1, 1, 'Spring Semester'),
(32, 17, 32, 28, '14:00:00.000000', '16:00:00.000000', 'monday', 1, 5, 1, 1, 'Spring Semester'),
(33, 43, 36, 29, '08:00:00.000000', '10:30:00.000000', 'friday', 1, 7, 1, 1, 'Spring Semester'),
(34, 44, 28, 26, '08:00:00.000000', '10:30:00.000000', 'tuesday', 1, 4, 1, 1, 'Spring Semester'),
(35, 47, 35, 20, '11:00:00.000000', '01:30:00.000000', 'tuesday', 1, 1, 1, 1, 'Spring Semester');

-- --------------------------------------------------------

--
-- Table structure for table `sections`
--

CREATE TABLE `sections` (
  `section_id` int(11) NOT NULL,
  `section_name` varchar(255) NOT NULL,
  `YearID` int(11) DEFAULT NULL,
  `class_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `sections`
--

INSERT INTO `sections` (`section_id`, `section_name`, `YearID`, `class_id`) VALUES
(20, 'S_201 English	', 1, 1),
(21, 'S_201 Francie\'s', 1, 1),
(22, 'S_202 English	', 1, 2),
(23, 'S_202 Francie\'s', 1, 2),
(24, 'S_203 English	', 1, 3),
(25, 'S_203 Francie\'s', 1, 3),
(26, 'S_204 English	', 1, 4),
(27, 'S_204 Francie\'s', 1, 4),
(28, 'GH123 Both', 1, 5),
(29, 'Online Both', 1, 7);

-- --------------------------------------------------------

--
-- Table structure for table `semister`
--

CREATE TABLE `semister` (
  `SemesterID` int(11) NOT NULL,
  `SemesterName` varchar(255) NOT NULL,
  `Startdate` date DEFAULT NULL,
  `ENDdate` date DEFAULT NULL,
  `DepartmentID` int(11) DEFAULT NULL,
  `curriculum_year_id` int(11) DEFAULT NULL,
  `YearID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `semister`
--

INSERT INTO `semister` (`SemesterID`, `SemesterName`, `Startdate`, `ENDdate`, `DepartmentID`, `curriculum_year_id`, `YearID`) VALUES
(51, 'fall semester', '2024-10-15', '2025-02-13', 1, 3, 1),
(52, 'fall semester', '2024-10-16', '2025-03-16', 1, 1, 1),
(53, 'spring semester ', '2025-03-20', '2025-06-16', 1, 1, 1),
(54, 'fall semester ', '2024-10-16', '2025-03-16', 1, 2, 1),
(55, 'spring semester ', '2025-03-16', '2025-06-16', 1, 2, 1),
(56, 'spring semester ', '2025-03-16', '2025-06-16', 1, 3, 1),
(64, 'fall semester ', '2024-07-23', '2024-07-17', 1, 1, 1),
(65, 'spring', '2025-03-17', '2025-06-17', 1, 3, 1);

-- --------------------------------------------------------

--
-- Table structure for table `semister_courses`
--

CREATE TABLE `semister_courses` (
  `semister_course_id` int(11) NOT NULL,
  `SemesterID` int(11) DEFAULT NULL,
  `CourseID` int(11) DEFAULT NULL,
  `date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `semister_courses`
--

INSERT INTO `semister_courses` (`semister_course_id`, `SemesterID`, `CourseID`, `date`) VALUES
(59, 51, 1, '2024-07-05'),
(60, 51, 2, '2024-07-05'),
(61, 51, 18, '2024-07-05'),
(62, 51, 21, '2024-07-05'),
(63, 51, 34, '2024-07-05'),
(64, 51, 35, '2024-07-05'),
(65, 51, 36, '2024-07-05'),
(73, 53, 17, '2024-07-16'),
(74, 53, 23, '2024-07-16'),
(75, 53, 43, '2024-07-16'),
(76, 53, 44, '2024-07-16'),
(77, 53, 45, '2024-07-16'),
(78, 53, 46, '2024-07-16'),
(79, 53, 47, '2024-07-16'),
(121, 64, 16, '2024-07-17'),
(124, 65, 58, '2024-07-17'),
(125, 65, 59, '2024-07-17'),
(126, 65, 60, '2024-07-17');

-- --------------------------------------------------------

--
-- Table structure for table `staff`
--

CREATE TABLE `staff` (
  `staff_id` int(100) NOT NULL,
  `role_id` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `description` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `staff`
--

INSERT INTO `staff` (`staff_id`, `role_id`, `name`, `email`, `password`, `description`) VALUES
(7, 1, 'khaled zein', 'khaled1@mail.com', 'Khaled123', 'admin'),
(9, 1, 'Rabi waznih', 'RabihIUL@mail.com', 'Rabih2024.IUL', 'admin'),
(10, 1, 'Rabi waznih', 'Rabi12@mail.com', 'Rabih#123', 'admin'),
(11, 1, 'ahmad alkadri', 'ahmad@mail.com', 'Ahmadsss12', 'admin'),
(12, 1, 'ahmad alkadri', 'ahmad@mail.com', 'Ahmadsss12', 'admin'),
(13, 1, 'ahmad alkadri', 'ahmad@mail.com', 'Ahmadsss12', 'admin'),
(14, 1, 'ahmad alkadri', 'ahmad@mail.com', 'Ahmadsss12', 'admin'),
(15, 1, 'layal', 'layal@mail.com', 'Layal4321', 'admin'),
(16, 2, 'DR.Ali Rammal', 'AliRammal12@mail.com', 'AR2024@iul.edu.lb', 'Teacher'),
(17, 2, 'Bassel dhayni', 'Bassel@Iul.edu.lb', 'Bassel1223', 'Teacher'),
(18, 2, 'Ali raad', 'AR!@Iul.edu.lb', 'Ali!2024.Iul', 'Teacher'),
(19, 2, 'Layal Hajjar', 'LayalHajjar@Iul.edu.lb', 'LH22024.Iul', 'Teacher'),
(25, 1, 'soaad', 'soaad21@mail.com', 'g123123', 'admin'),
(27, 1, 'Rabih Wazneh', 'RW2@Iul.edu.lb', '$2y$10$XSlVBduxP/Q7WZucaWJ65u0xhfmtpwcfZbcr/tcNB7ZDen8fbq.R.', 'admin'),
(28, 2, 'Kassm danash', 'KD2@uil.edu.lb', 'KD22024.Iul', 'Teacher'),
(29, 2, 'Kassm danash', 'KD2@uil.edu.lb', 'KD22024.Iul', 'Teacher'),
(30, 2, 'Kassm danash', 'KD2@uil.edu.lb', 'KD22024.Iul', 'Teacher'),
(31, 2, 'Kassm danash', 'KD2@uil.edu.lb', 'KD22024.Iul', 'Teacher'),
(32, 2, 'Kassm danash', 'KD2@uil.edu.lb', 'KD22024.Iul', 'Teacher'),
(33, 2, 'Kassm danash', 'KD2@uil.edu.lb', 'KD22024.Iul', 'Teacher'),
(34, 2, 'Ali Haj Hassan', 'AHH@iul.edu.lb', 'AHH22024.Iul', 'Teacher'),
(35, 3, 'register', 'register@ul.edu.lb', 'R321', 'computer science '),
(36, 3, 'register', 'register@iul.edu.lb', 'r123', 'computer Science '),
(37, 3, 'register', 'register@ul.edu.lb', 'r1234', 'computer Science '),
(38, 3, 'register', 'Register@iul.edu.lb', 're321', 'registration'),
(39, 3, 'register', 'reg@iul.edu.lb', 'ret321', 'registration'),
(40, 3, 'register', 'register@ul.edu.lb', 'reft321', 'registration'),
(41, 2, 'teacher', 'user@mail.com', 'passwORD123', 'Teacher'),
(42, 2, 'memo', 'memodemo@mail.com', 'demoMemo12', 'Teacher'),
(43, 2, 'memo', 'memodemo@mail.com', 'demoMemo', 'Teacher'),
(44, 2, 'memo', 'user@mail.com', 'passwORD123', 'Teacher'),
(45, 3, 'emp1', 'emp1@iul.com', 'Emp1#2', 'registration'),
(46, 3, 'emp1', 'emp1@iul.com', 'Emp1#2', 'registration'),
(47, 3, 'emp1', 'emp1@iul.com', 'Emp1#2', 'registration'),
(48, 3, 'emp1', 'user@mail.com', 'passwORD123', 'registration'),
(49, 3, 'emp1', 'user@Iul.com', 'passwORD123', 'registration'),
(50, 3, 'emp1', 'user@Iul.com', 'passwORD123', 'registration'),
(51, 3, 'emp1', 'user@Iul.com', 'passwORD123', 'registration'),
(52, 3, 'emp1', 'user@Iul.com', 'passwORD123', 'registration'),
(53, 3, 'emp1', 'user@Iul.com', 'passwORD123', 'registration'),
(54, 3, 'emp1', 'user@Iul.com', 'passwORD123', 'registration'),
(55, 3, 'emp1', 'user@Iul.com', 'passwORD123', 'registration'),
(56, 3, 'emp1', 'user@Iul.com', 'passwORD123', 'registration'),
(57, 3, 'emp1', 'user@Iul.com', 'passwORD123', 'registration'),
(58, 3, 'emp1', 'user@Iul.com', 'passwORD123', 'registration'),
(59, 3, 'emp1', 'user@Iul.com', 'passwORD123', 'registration'),
(60, 3, 'user1', 'user@Iul.com', 'passwORD123', 'registration'),
(61, 3, 'new register', 'New@iul.edu.lb', 'NewIul2024', 'registration'),
(62, 3, 'new register', 'user@mail.com', 'passwORD123', 'registration'),
(63, 3, 'new register', 'user@mail.com', 'passwORD123', 'registration'),
(64, 3, 'guess', 'guess!!@email.com', 'guess@!#', 'registration'),
(65, 3, 'zed', 'user@iul.com', 'passwORD123', 'registration'),
(66, 1, 'ahmadBoss', 'Boss@mail.com', 'BossAdmin', 'admin'),
(67, 3, 'ahmad', 'Ahmad@mail.com', 'Ahmad#21', 'registration'),
(68, 3, 'Ahmad', 'Ahmad@mail.com', 'Ahmad321', 'registration'),
(69, 1, 'ahmadBoss', 'user@mail.com', 'passwORD123', 'admin'),
(70, 3, 'ahmad', 'ahmad@mail.com', 'Ahmad#21', 'registration'),
(71, 2, 't1', 't1@mail.com', 'T1qwerty32', 'Teacher'),
(72, 3, 'R1', 'user.@mail.com', 'R1Qwerty', 'registration'),
(73, 2, 'Ali bu melhem', 'Ali@mail.com', 'Ali12024', 'Teacher'),
(74, 2, 'Teacher', 'Teacher@mail.com', 'Teacher#21', 'Teacher'),
(75, 2, 'Teacher', 'Teacher4@mail.com', 'Teacher4312', 'Teacher'),
(76, 2, 'Teacher2', 'usert2@iul.com', 'passwORD123', 'Teacher'),
(77, 2, 'Ali Raad', 'AA@Iul.edu.lb', 'AA@Iul.2024', 'Teacher'),
(78, 2, 'Walid Fahes', 'WF@iul.edu.lb', 'WFIul.2024', 'Teacher'),
(79, 2, 'Walid Fahes', 'WF@iul.edu.lb', 'WFIul.2024', 'Teacher'),
(80, 2, 'Walid Fahes', 'WF@iul.edu.lb', 'wfiul2024', 'Teacher'),
(81, 2, 'Nizar Hmadeh', 'NH@iul.edu.lb', 'NHIul.2024', 'Teacher'),
(82, 2, 'Ali Mokdad', 'AM@iul.edu.lb', 'AMIul;2024', 'Teacher'),
(83, 2, 'Ali Raad', 'AR@iul.edu.lb', 'ARIul.2024', 'Teacher'),
(84, 2, 'Rabih Waznih', 'Rabih@iul.edu.lb', 'Rabih!Iul.2024', 'Teacher'),
(85, 2, 'Rabih Waznih', 'Rabih@iul.edu.lb', 'passwORD123', 'Teacher'),
(86, 2, 'Khaled zein', 'KH@iul.edu.lb', 'KHIul.2024', 'Teacher'),
(87, 2, 'Adnan elhaj', 'AH!@mmail.com', 'AD#123', 'Teacher'),
(88, 2, 'Diana Bader', 'DB#@MAIL.COM', 'dIANA$1234', 'Teacher'),
(89, 2, 'POTEN', 'POTEN@MAIL.COM', 'po%12345', 'Teacher'),
(90, 2, 'NAIM BAAINI', 'NAIM@MAIL.COM', 'nA^123456', 'Teacher'),
(91, 2, 'Jomana Mhaydli', 'Jomana@mail.com', 'Jomana&1234567', 'Teacher'),
(92, 2, 'Layal Hajjar', 'LayalHajjar@Iul.edu.lb', 'Layal*7654', 'Teacher'),
(93, 2, 'Ali rammal', 'AliRamal@mail.com', 'Ali$325165', 'Teacher'),
(94, 3, 'ahmad ', 'AhmadAlkadri2002@mail.com', 'Ahmad%54321', 'registration'),
(95, 3, 'ahmad ', 'alibumelhem@gmail.com', 'ali1990', 'registration'),
(96, 3, 'ahmad ', 'alibumelhem@gmail.com', 'ali1990', 'registration'),
(97, 2, 'Layal Hajjar', 'LayalHajjar@Iul.edu.lb', 'Layal%4321', 'Teacher'),
(98, 3, 'Layal Hajjar', 'LayalHajjar@Iul.edu.lb', 'klyukyukyuk', 'registration'),
(99, 3, 'Layal Hajjar', 'LayalHajjar@Iul.edu.lb', 'i67uj6j5tyj', 'registration'),
(100, 3, 'Layal Hajjar', 'LayalHajjar@Iul.edu.lb', 'i67uj6j5tyj', 'registration'),
(101, 3, 'Layal Hajjar', 'LayalHajjar@Iul.edu.lb', 'yujkujktyjtyjtj', 'registration'),
(102, 3, 'Layal Hajjar', 'LayalHajjar@Iul.edu.lb', 'yjtyjtjtyjtyjty', 'registration'),
(103, 3, 'Layal Hajjar', 'LayalHajjar@Iul.edu.lb', 'ghngnfgnfgnn', 'registration'),
(104, 3, 'Layal Hajjar', 'LayalHajjar@Iul.edu.lb', 'bb$dgdsgdg', 'registration'),
(105, 3, 'Layal Hajjar', 'LayalHajjar@Iul.edu.lb', 'YUTKJYKJTUJKYUKYU', 'registration'),
(106, 3, 'Layal Hajjar', 'LayalHajjar@Iul.edu.lb', 'UKUYKKYUK', 'registration'),
(107, 3, 'Layal Hajjar', 'LayalHajjar@Iul.edu.lb', 'UYKJYUKJYUKJYUKU#', 'registration'),
(108, 3, 'Layal Hajjar', 'LayalHajjar@Iul.edu.lb', 'UYKJYUKJYUKJYUKU#', 'registration'),
(109, 3, 'YUKYUKY', 'UYKTYY@MGH', 'UYKUYKTYUK', 'registration'),
(110, 3, 'ahamd hoha', 'Hmada@mail.com', 'gbnfgnsg', 'registration'),
(111, 3, 'ahamd hoha', 'Hmada@mail.com', 'gbnfgnsg', 'registration'),
(112, 2, 'ahamd hoha', 'Hmada@mail.com', '/ /;. ,;/., ;', 'Teacher'),
(113, 3, 'Layal Hajjar', 'LayalHajjar@Iul.edu.lb', '/ljkjnlkjnklj', 'registration'),
(114, 3, 'Layal Hajjar', 'LayalHajjar@Iul.edu.lb', '7i67i67i', 'registration'),
(115, 2, 'Ali Rammal', 'ALiRammal@mail.com', 'ALI%4123', 'Teacher'),
(116, 3, 'Ali Rammal', 'ahmad@mail.com', 'o;io;', 'registration'),
(117, 3, 'm h h', 'ahmadalkadri@gmail.com', 'mn nm nm m', 'registration'),
(118, 3, 'ahmad alkadri', 'aa92105@net.iul.edu.lb', 'ghjnghjgj', 'registration'),
(119, 2, 'ahmad kurdi', 'ahmadalkadri2002@gmail.com', 'hjfjj', 'Teacher'),
(120, 2, 'ahmad kurdi', 'ahmadalkadri2002@gmail.com', 'hfghh', 'Teacher');

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

CREATE TABLE `student` (
  `students_id` bigint(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `phone_number` int(255) NOT NULL,
  `photo_url` varchar(255) NOT NULL DEFAULT 'uploads/students',
  `photo_name` varchar(255) NOT NULL,
  `password` varchar(100) NOT NULL,
  `YearID` int(11) DEFAULT NULL,
  `major` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `student`
--

INSERT INTO `student` (`students_id`, `name`, `email`, `phone_number`, `photo_url`, `photo_name`, `password`, `YearID`, `major`) VALUES
(7, 'Saja Ahmad Mchayk', 'SAM@Iul.edu.lb', 3472869, 'uploads/students/g2.png', 'R (1)', 'SA202', 1, 1),
(25, 'Ahmad Amer Alkadri', 'ahmadalkadri2002@gmail.com', 78743718, 'uploads/students/b3.png', '', 'Ahmettre123', 1, 1),
(27, 'aya jamol', 'aya@mail.com', 78743716, 'uploads/students/g1.png', 'boy', 'Aya123', 1, 1),
(28, 'Ali hasson', 'aa92102@net.iul.edu.lb', 71155188, 'uploads/students/b1.jpeg', 'boy', 'AliIul.2024', 1, 1),
(30, 'Hassan Yazbek', 'HY@Iul.edu.lb', 1231556, 'uploads/students/b2.png', 'boy', 'HYIul.2024', 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `teachers`
--

CREATE TABLE `teachers` (
  `teacherID` bigint(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `phone_number` int(255) NOT NULL,
  `photo_name` varchar(255) NOT NULL,
  `photo_url` varchar(255) NOT NULL DEFAULT 'uploads/teachers',
  `password` varchar(100) NOT NULL,
  `YearID` int(11) DEFAULT NULL,
  `staffID` int(11) DEFAULT NULL,
  `department` int(11) DEFAULT NULL,
  `admin_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `teachers`
--

INSERT INTO `teachers` (`teacherID`, `name`, `email`, `phone_number`, `photo_name`, `photo_url`, `password`, `YearID`, `staffID`, `department`, `admin_id`) VALUES
(22, 'Ali Raad', 'AA@Iul.edu.lb', 3472860, 'boy', 'uploads/teachers/boy.png', 'AA@Iul.2024', 1, 77, 1, 20),
(25, 'Walid Fahes', 'WF@iul.edu.lb', 71440740, 'boy', 'uploads/teachers/boy.png', 'wfiul2024', 1, 80, 1, 20),
(26, 'Nizar Hmadeh', 'NH@iul.edu.lb', 3472862, 'boy', 'uploads/teachers/boy.png', 'NHIul.2024', 1, 81, 1, 20),
(27, 'Ali Mokdad', 'AM@iul.edu.lb', 70876326, 'Ali mokdad', 'uploads/teachers/Ali mokdad.png', 'AMIul;2024', 1, 82, 1, 20),
(28, 'Ali Raad', 'AR@iul.edu.lb', 3472860, 'Ali raad', 'uploads/teachers/Ali raad.png', 'ARIul.2024', 1, 83, 1, 20),
(30, 'Rabih Waznih', 'Rabih@iul.edu.lb', 3662547, '436316675_10161672938065295_3669042275330485765_n', 'uploads/teachers/436316675_10161672938065295_3669042275330485765_n.jpg', 'passwORD123', 1, 85, 1, 20),
(31, 'Khaled zein', 'KH@iul.edu.lb', 76158589, 'boy', 'uploads/teachers/boy.png', 'KHIul.2024', 1, 86, 1, 20),
(32, 'Adnan elhaj', 'AH!@mmail.com', 3628230, 'boy', 'uploads/teachers/boy.png', 'AD#123', 1, 87, 1, 20),
(33, 'Diana Bader', 'DB#@MAIL.COM', 3951507, 'woman', 'uploads/teachers/woman.png', 'dIANA$1234', 1, 88, 1, 20),
(34, 'POTEN', 'POTEN@MAIL.COM', 78932165, 'boy', 'uploads/teachers/boy.png', 'po%12345', 1, 89, 1, 20),
(35, 'NAIM BAAINI', 'NAIM@MAIL.COM', 9865203, 'boy', 'uploads/teachers/boy.png', 'nA^123456', 1, 90, 1, 20),
(36, 'Jomana Mhaydli', 'Jomana@mail.com', 78985566, 'woman', 'uploads/teachers/woman.png', 'Jomana&1234567', 1, 91, 1, 20),
(39, 'Layal Hajjar', 'LayalHajjar@Iul.edu.lb', 81147148, 'woman', 'uploads/teachers/woman.png', 'Layal%4321', 1, 97, 1, 20),
(41, 'Ali Rammal', 'ALiRammal@mail.com', 78743716, 'boy', 'uploads/teachers/boy.png', 'ALI%4123', 1, 115, 1, 20);

-- --------------------------------------------------------

--
-- Table structure for table `teaching`
--

CREATE TABLE `teaching` (
  `teacherID` bigint(20) NOT NULL,
  `CourseID` int(11) NOT NULL,
  `YearID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `teaching`
--

INSERT INTO `teaching` (`teacherID`, `CourseID`, `YearID`) VALUES
(22, 21, 1),
(22, 22, 1),
(22, 24, 1),
(25, 35, 1),
(25, 52, 1),
(25, 57, 1),
(26, 18, 1),
(26, 46, 1),
(26, 48, 1),
(27, 2, 1),
(28, 1, 1),
(28, 16, 1),
(28, 44, 1),
(30, 54, 1),
(31, 36, 1),
(31, 60, 1),
(32, 17, 1),
(32, 25, 1),
(32, 42, 1),
(32, 45, 1),
(32, 50, 1),
(32, 51, 1),
(33, 13, 1),
(33, 43, 1),
(34, 31, 1),
(35, 32, 1),
(36, 33, 1),
(36, 53, 1),
(39, 58, 1),
(41, 34, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `academicyear`
--
ALTER TABLE `academicyear`
  ADD PRIMARY KEY (`YearID`);

--
-- Indexes for table `additional_fees`
--
ALTER TABLE `additional_fees`
  ADD PRIMARY KEY (`PaymentID`),
  ADD KEY `students_id` (`students_id`),
  ADD KEY `YearID` (`YearID`),
  ADD KEY `fk_semester_id` (`semester_id`);

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`admin_id`),
  ADD KEY `YearID` (`YearID`),
  ADD KEY `staffID` (`staffID`),
  ADD KEY `fk_admin_dept` (`dept_id`);

--
-- Indexes for table `articles`
--
ALTER TABLE `articles`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `assignments`
--
ALTER TABLE `assignments`
  ADD PRIMARY KEY (`assignment_id`),
  ADD KEY `CourseID` (`CourseID`),
  ADD KEY `YearID` (`YearID`),
  ADD KEY `fk_assignments_students` (`students_id`);

--
-- Indexes for table `attendance`
--
ALTER TABLE `attendance`
  ADD PRIMARY KEY (`Attendance_id`),
  ADD KEY `students_id` (`students_id`),
  ADD KEY `TeacherID` (`TeacherID`),
  ADD KEY `CourseID` (`CourseID`),
  ADD KEY `YearID` (`YearID`),
  ADD KEY `fk_lecture` (`lecture_id`);

--
-- Indexes for table `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `classes`
--
ALTER TABLE `classes`
  ADD PRIMARY KEY (`class_id`),
  ADD KEY `YearID` (`YearID`);

--
-- Indexes for table `course`
--
ALTER TABLE `course`
  ADD PRIMARY KEY (`CourseID`),
  ADD KEY `YearID` (`YearID`),
  ADD KEY `fk_dept_id` (`dept_id`),
  ADD KEY `fk_course_curriculum_year` (`curriculum_year_id`);

--
-- Indexes for table `curriculum_year`
--
ALTER TABLE `curriculum_year`
  ADD PRIMARY KEY (`curriculum_year_id`);

--
-- Indexes for table `departments`
--
ALTER TABLE `departments`
  ADD PRIMARY KEY (`dept_id`);

--
-- Indexes for table `enrollment`
--
ALTER TABLE `enrollment`
  ADD PRIMARY KEY (`students_id`,`CourseID`,`YearID`),
  ADD KEY `CourseID` (`CourseID`),
  ADD KEY `YearID` (`YearID`);

--
-- Indexes for table `exam`
--
ALTER TABLE `exam`
  ADD PRIMARY KEY (`ExamID`),
  ADD KEY `YearID` (`YearID`),
  ADD KEY `fk_exam_classes` (`room`),
  ADD KEY `CourseID` (`CourseID`),
  ADD KEY `fk_exam_type` (`exam_type_id`);

--
-- Indexes for table `exam_type`
--
ALTER TABLE `exam_type`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `grades`
--
ALTER TABLE `grades`
  ADD PRIMARY KEY (`grade_id`),
  ADD KEY `students_id` (`students_id`),
  ADD KEY `ExamID` (`ExamID`),
  ADD KEY `CourseID` (`CourseID`);

--
-- Indexes for table `lb_payment`
--
ALTER TABLE `lb_payment`
  ADD PRIMARY KEY (`PaymentID`),
  ADD KEY `students_id` (`students_id`),
  ADD KEY `YearID` (`YearID`),
  ADD KEY `fk_semester` (`semester_id`);

--
-- Indexes for table `lectures`
--
ALTER TABLE `lectures`
  ADD PRIMARY KEY (`id`),
  ADD KEY `CourseID` (`CourseID`),
  ADD KEY `YearID` (`YearID`);

--
-- Indexes for table `mark`
--
ALTER TABLE `mark`
  ADD PRIMARY KEY (`MarkID`),
  ADD KEY `students_id` (`students_id`),
  ADD KEY `ExamID` (`ExamID`),
  ADD KEY `YearID` (`YearID`),
  ADD KEY `CourseID` (`CourseID`);

--
-- Indexes for table `registration`
--
ALTER TABLE `registration`
  ADD PRIMARY KEY (`register_id`),
  ADD KEY `department_id` (`department_id`),
  ADD KEY `YearID` (`YearID`),
  ADD KEY `staffID` (`staffID`),
  ADD KEY `fk_admin` (`admin_id`);

--
-- Indexes for table `request`
--
ALTER TABLE `request`
  ADD PRIMARY KEY (`ReqID`);

--
-- Indexes for table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`roleID`);

--
-- Indexes for table `schedule`
--
ALTER TABLE `schedule`
  ADD PRIMARY KEY (`schedule_id`),
  ADD KEY `teacherID` (`teacherID`),
  ADD KEY `section_id` (`section_id`),
  ADD KEY `YearID` (`YearID`),
  ADD KEY `CourseID` (`CourseID`),
  ADD KEY `fk_class` (`class_id`),
  ADD KEY `fk_schedule_department` (`department_id`),
  ADD KEY `fk_schedule_curriculum_year` (`curriculum_year_id`);

--
-- Indexes for table `sections`
--
ALTER TABLE `sections`
  ADD PRIMARY KEY (`section_id`),
  ADD KEY `YearID` (`YearID`),
  ADD KEY `fk_class_id` (`class_id`);

--
-- Indexes for table `semister`
--
ALTER TABLE `semister`
  ADD PRIMARY KEY (`SemesterID`),
  ADD KEY `DepartmentID` (`DepartmentID`),
  ADD KEY `curriculum_year_id` (`curriculum_year_id`),
  ADD KEY `FK_semester_YearID` (`YearID`);

--
-- Indexes for table `semister_courses`
--
ALTER TABLE `semister_courses`
  ADD PRIMARY KEY (`semister_course_id`),
  ADD KEY `SemesterID` (`SemesterID`),
  ADD KEY `CourseID` (`CourseID`);

--
-- Indexes for table `staff`
--
ALTER TABLE `staff`
  ADD PRIMARY KEY (`staff_id`),
  ADD KEY `role_id` (`role_id`);

--
-- Indexes for table `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`students_id`),
  ADD KEY `YearID` (`YearID`),
  ADD KEY `fk_students_dept` (`major`);

--
-- Indexes for table `teachers`
--
ALTER TABLE `teachers`
  ADD PRIMARY KEY (`teacherID`),
  ADD KEY `YearID` (`YearID`),
  ADD KEY `staffID` (`staffID`),
  ADD KEY `fk_department` (`department`),
  ADD KEY `fk_admin_teachers` (`admin_id`);

--
-- Indexes for table `teaching`
--
ALTER TABLE `teaching`
  ADD PRIMARY KEY (`teacherID`,`CourseID`,`YearID`),
  ADD KEY `CourseID` (`CourseID`),
  ADD KEY `YearID` (`YearID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `academicyear`
--
ALTER TABLE `academicyear`
  MODIFY `YearID` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `additional_fees`
--
ALTER TABLE `additional_fees`
  MODIFY `PaymentID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=62;

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `admin_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT for table `articles`
--
ALTER TABLE `articles`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT for table `assignments`
--
ALTER TABLE `assignments`
  MODIFY `assignment_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT for table `attendance`
--
ALTER TABLE `attendance`
  MODIFY `Attendance_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;

--
-- AUTO_INCREMENT for table `categories`
--
ALTER TABLE `categories`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `classes`
--
ALTER TABLE `classes`
  MODIFY `class_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `course`
--
ALTER TABLE `course`
  MODIFY `CourseID` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=63;

--
-- AUTO_INCREMENT for table `curriculum_year`
--
ALTER TABLE `curriculum_year`
  MODIFY `curriculum_year_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `departments`
--
ALTER TABLE `departments`
  MODIFY `dept_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `exam`
--
ALTER TABLE `exam`
  MODIFY `ExamID` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=56;

--
-- AUTO_INCREMENT for table `exam_type`
--
ALTER TABLE `exam_type`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `grades`
--
ALTER TABLE `grades`
  MODIFY `grade_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `lb_payment`
--
ALTER TABLE `lb_payment`
  MODIFY `PaymentID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=180;

--
-- AUTO_INCREMENT for table `lectures`
--
ALTER TABLE `lectures`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT for table `mark`
--
ALTER TABLE `mark`
  MODIFY `MarkID` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `registration`
--
ALTER TABLE `registration`
  MODIFY `register_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- AUTO_INCREMENT for table `request`
--
ALTER TABLE `request`
  MODIFY `ReqID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `role`
--
ALTER TABLE `role`
  MODIFY `roleID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `schedule`
--
ALTER TABLE `schedule`
  MODIFY `schedule_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;

--
-- AUTO_INCREMENT for table `sections`
--
ALTER TABLE `sections`
  MODIFY `section_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT for table `semister`
--
ALTER TABLE `semister`
  MODIFY `SemesterID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=69;

--
-- AUTO_INCREMENT for table `semister_courses`
--
ALTER TABLE `semister_courses`
  MODIFY `semister_course_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=131;

--
-- AUTO_INCREMENT for table `staff`
--
ALTER TABLE `staff`
  MODIFY `staff_id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=121;

--
-- AUTO_INCREMENT for table `student`
--
ALTER TABLE `student`
  MODIFY `students_id` bigint(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- AUTO_INCREMENT for table `teachers`
--
ALTER TABLE `teachers`
  MODIFY `teacherID` bigint(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=44;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `additional_fees`
--
ALTER TABLE `additional_fees`
  ADD CONSTRAINT `additional_fees_ibfk_1` FOREIGN KEY (`students_id`) REFERENCES `student` (`students_id`),
  ADD CONSTRAINT `additional_fees_ibfk_2` FOREIGN KEY (`YearID`) REFERENCES `academicyear` (`YearID`),
  ADD CONSTRAINT `fk_semester_id` FOREIGN KEY (`semester_id`) REFERENCES `semister` (`SemesterID`);

--
-- Constraints for table `admin`
--
ALTER TABLE `admin`
  ADD CONSTRAINT `admin_ibfk_1` FOREIGN KEY (`YearID`) REFERENCES `academicyear` (`YearID`),
  ADD CONSTRAINT `admin_ibfk_2` FOREIGN KEY (`staffID`) REFERENCES `staff` (`staff_id`),
  ADD CONSTRAINT `fk_admin_dept` FOREIGN KEY (`dept_id`) REFERENCES `departments` (`dept_id`);

--
-- Constraints for table `assignments`
--
ALTER TABLE `assignments`
  ADD CONSTRAINT `assignments_ibfk_1` FOREIGN KEY (`CourseID`) REFERENCES `course` (`CourseID`),
  ADD CONSTRAINT `assignments_ibfk_2` FOREIGN KEY (`YearID`) REFERENCES `academicyear` (`YearID`),
  ADD CONSTRAINT `fk_assignments_students` FOREIGN KEY (`students_id`) REFERENCES `student` (`students_id`);

--
-- Constraints for table `attendance`
--
ALTER TABLE `attendance`
  ADD CONSTRAINT `attendance_ibfk_1` FOREIGN KEY (`students_id`) REFERENCES `student` (`students_id`),
  ADD CONSTRAINT `attendance_ibfk_2` FOREIGN KEY (`TeacherID`) REFERENCES `teachers` (`teacherID`),
  ADD CONSTRAINT `attendance_ibfk_3` FOREIGN KEY (`CourseID`) REFERENCES `course` (`CourseID`),
  ADD CONSTRAINT `attendance_ibfk_4` FOREIGN KEY (`YearID`) REFERENCES `academicyear` (`YearID`),
  ADD CONSTRAINT `fk_lecture` FOREIGN KEY (`lecture_id`) REFERENCES `lectures` (`id`);

--
-- Constraints for table `classes`
--
ALTER TABLE `classes`
  ADD CONSTRAINT `classes_ibfk_1` FOREIGN KEY (`YearID`) REFERENCES `academicyear` (`YearID`);

--
-- Constraints for table `course`
--
ALTER TABLE `course`
  ADD CONSTRAINT `course_ibfk_1` FOREIGN KEY (`YearID`) REFERENCES `academicyear` (`YearID`),
  ADD CONSTRAINT `fk_course_curriculum_year` FOREIGN KEY (`curriculum_year_id`) REFERENCES `curriculum_year` (`curriculum_year_id`),
  ADD CONSTRAINT `fk_dept_id` FOREIGN KEY (`dept_id`) REFERENCES `departments` (`dept_id`);

--
-- Constraints for table `enrollment`
--
ALTER TABLE `enrollment`
  ADD CONSTRAINT `enrollment_ibfk_1` FOREIGN KEY (`students_id`) REFERENCES `student` (`students_id`),
  ADD CONSTRAINT `enrollment_ibfk_2` FOREIGN KEY (`CourseID`) REFERENCES `course` (`CourseID`),
  ADD CONSTRAINT `enrollment_ibfk_3` FOREIGN KEY (`YearID`) REFERENCES `academicyear` (`YearID`);

--
-- Constraints for table `exam`
--
ALTER TABLE `exam`
  ADD CONSTRAINT `exam_ibfk_2` FOREIGN KEY (`YearID`) REFERENCES `academicyear` (`YearID`),
  ADD CONSTRAINT `exam_ibfk_3` FOREIGN KEY (`CourseID`) REFERENCES `course` (`CourseID`),
  ADD CONSTRAINT `fk_exam_classes` FOREIGN KEY (`room`) REFERENCES `classes` (`class_id`),
  ADD CONSTRAINT `fk_exam_type` FOREIGN KEY (`exam_type_id`) REFERENCES `exam_type` (`id`);

--
-- Constraints for table `grades`
--
ALTER TABLE `grades`
  ADD CONSTRAINT `grades_ibfk_1` FOREIGN KEY (`students_id`) REFERENCES `student` (`students_id`),
  ADD CONSTRAINT `grades_ibfk_2` FOREIGN KEY (`ExamID`) REFERENCES `exam` (`ExamID`),
  ADD CONSTRAINT `grades_ibfk_3` FOREIGN KEY (`CourseID`) REFERENCES `course` (`CourseID`);

--
-- Constraints for table `lb_payment`
--
ALTER TABLE `lb_payment`
  ADD CONSTRAINT `fk_semester` FOREIGN KEY (`semester_id`) REFERENCES `semister` (`SemesterID`),
  ADD CONSTRAINT `lb_payment_ibfk_1` FOREIGN KEY (`students_id`) REFERENCES `student` (`students_id`),
  ADD CONSTRAINT `lb_payment_ibfk_2` FOREIGN KEY (`YearID`) REFERENCES `academicyear` (`YearID`);

--
-- Constraints for table `lectures`
--
ALTER TABLE `lectures`
  ADD CONSTRAINT `lectures_ibfk_1` FOREIGN KEY (`CourseID`) REFERENCES `course` (`CourseID`),
  ADD CONSTRAINT `lectures_ibfk_2` FOREIGN KEY (`YearID`) REFERENCES `academicyear` (`YearID`);

--
-- Constraints for table `mark`
--
ALTER TABLE `mark`
  ADD CONSTRAINT `mark_ibfk_1` FOREIGN KEY (`students_id`) REFERENCES `student` (`students_id`),
  ADD CONSTRAINT `mark_ibfk_2` FOREIGN KEY (`ExamID`) REFERENCES `exam` (`ExamID`),
  ADD CONSTRAINT `mark_ibfk_3` FOREIGN KEY (`YearID`) REFERENCES `academicyear` (`YearID`),
  ADD CONSTRAINT `mark_ibfk_4` FOREIGN KEY (`CourseID`) REFERENCES `course` (`CourseID`);

--
-- Constraints for table `registration`
--
ALTER TABLE `registration`
  ADD CONSTRAINT `fk_admin` FOREIGN KEY (`admin_id`) REFERENCES `admin` (`admin_id`),
  ADD CONSTRAINT `registration_ibfk_1` FOREIGN KEY (`department_id`) REFERENCES `departments` (`dept_id`),
  ADD CONSTRAINT `registration_ibfk_2` FOREIGN KEY (`YearID`) REFERENCES `academicyear` (`YearID`),
  ADD CONSTRAINT `registration_ibfk_3` FOREIGN KEY (`staffID`) REFERENCES `staff` (`staff_id`);

--
-- Constraints for table `schedule`
--
ALTER TABLE `schedule`
  ADD CONSTRAINT `fk_class` FOREIGN KEY (`class_id`) REFERENCES `classes` (`class_id`),
  ADD CONSTRAINT `fk_schedule_curriculum_year` FOREIGN KEY (`curriculum_year_id`) REFERENCES `curriculum_year` (`curriculum_year_id`),
  ADD CONSTRAINT `fk_schedule_department` FOREIGN KEY (`department_id`) REFERENCES `departments` (`dept_id`),
  ADD CONSTRAINT `schedule_ibfk_1` FOREIGN KEY (`CourseID`) REFERENCES `course` (`CourseID`),
  ADD CONSTRAINT `schedule_ibfk_2` FOREIGN KEY (`teacherID`) REFERENCES `teachers` (`teacherID`),
  ADD CONSTRAINT `schedule_ibfk_3` FOREIGN KEY (`section_id`) REFERENCES `sections` (`section_id`),
  ADD CONSTRAINT `schedule_ibfk_4` FOREIGN KEY (`YearID`) REFERENCES `academicyear` (`YearID`),
  ADD CONSTRAINT `schedule_ibfk_5` FOREIGN KEY (`CourseID`) REFERENCES `course` (`CourseID`);

--
-- Constraints for table `sections`
--
ALTER TABLE `sections`
  ADD CONSTRAINT `fk_class_id` FOREIGN KEY (`class_id`) REFERENCES `classes` (`class_id`),
  ADD CONSTRAINT `sections_ibfk_1` FOREIGN KEY (`YearID`) REFERENCES `academicyear` (`YearID`);

--
-- Constraints for table `semister`
--
ALTER TABLE `semister`
  ADD CONSTRAINT `FK_semester_YearID` FOREIGN KEY (`YearID`) REFERENCES `academicyear` (`YearID`),
  ADD CONSTRAINT `semister_ibfk_2` FOREIGN KEY (`DepartmentID`) REFERENCES `departments` (`dept_id`),
  ADD CONSTRAINT `semister_ibfk_3` FOREIGN KEY (`curriculum_year_id`) REFERENCES `curriculum_year` (`curriculum_year_id`);

--
-- Constraints for table `semister_courses`
--
ALTER TABLE `semister_courses`
  ADD CONSTRAINT `semister_courses_ibfk_1` FOREIGN KEY (`SemesterID`) REFERENCES `semister` (`SemesterID`),
  ADD CONSTRAINT `semister_courses_ibfk_2` FOREIGN KEY (`CourseID`) REFERENCES `course` (`CourseID`);

--
-- Constraints for table `staff`
--
ALTER TABLE `staff`
  ADD CONSTRAINT `staff_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`roleID`);

--
-- Constraints for table `student`
--
ALTER TABLE `student`
  ADD CONSTRAINT `fk_students_dept` FOREIGN KEY (`major`) REFERENCES `departments` (`dept_id`),
  ADD CONSTRAINT `student_ibfk_1` FOREIGN KEY (`YearID`) REFERENCES `academicyear` (`YearID`);

--
-- Constraints for table `teachers`
--
ALTER TABLE `teachers`
  ADD CONSTRAINT `fk_admin_teachers` FOREIGN KEY (`admin_id`) REFERENCES `admin` (`admin_id`),
  ADD CONSTRAINT `fk_department` FOREIGN KEY (`department`) REFERENCES `departments` (`dept_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `teachers_ibfk_1` FOREIGN KEY (`YearID`) REFERENCES `academicyear` (`YearID`),
  ADD CONSTRAINT `teachers_ibfk_2` FOREIGN KEY (`staffID`) REFERENCES `staff` (`staff_id`);

--
-- Constraints for table `teaching`
--
ALTER TABLE `teaching`
  ADD CONSTRAINT `teaching_ibfk_1` FOREIGN KEY (`teacherID`) REFERENCES `teachers` (`teacherID`),
  ADD CONSTRAINT `teaching_ibfk_2` FOREIGN KEY (`CourseID`) REFERENCES `course` (`CourseID`),
  ADD CONSTRAINT `teaching_ibfk_3` FOREIGN KEY (`YearID`) REFERENCES `academicyear` (`YearID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
