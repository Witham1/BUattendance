
<?php
    
    require_once 'user.php';
    
    $username = "";
    
    $password = "";
    
    $email = "";
	     
    $sNum = "";
    
    $lecture = "";
	
	$wk = "";
    
    
    if(isset($_POST['username'])){
        
        $username = $_POST['username'];
        
    }
    
    if(isset($_POST['password'])){
        
        $password = $_POST['password'];
        
    }
    
    if(isset($_POST['email'])){
        
        $email = $_POST['email'];
        
    }
	
	 if(isset($_POST['sNum'])){
        
        $sNum = $_POST['sNum'];
        
    }
    
    if(isset($_POST['lecture'])){
        
        $lecture = $_POST['lecture'];
        
    }
	 if(isset($_POST['wk'])){
        
        $wk = $_POST['wk'];
        
    }
    
    $userObject = new User();
    
    // Registration
    
    if(!empty($username) && !empty($password) && !empty($email)){
        
        $hashed_password = md5($password);
        
        $json_registration = $userObject->createNewRegisterUser($username, $hashed_password, $email);
        
        echo json_encode($json_registration);
        
    }
    
    // Login
    
    if(!empty($username) && !empty($password) && empty($email) && empty($sNum) && empty($lecture) && empty($wk)){
        
        $hashed_password = md5($password);
        
        $json_array = $userObject->loginUsers($username, $hashed_password);
        
        echo json_encode($json_array);
    }
	if(empty($username) && empty($password) && empty($email) && !empty($sNum) && !empty($lecture) && !empty($wk)){
        
        
        $json_logA = $userObject->logAttendance($lecture,$wk,$sNum);
        
        echo json_encode($json_logA);
    }
    
    ?>
