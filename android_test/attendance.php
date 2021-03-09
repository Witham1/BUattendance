
<?php
    
    require_once 'user.php';

    $db_table2 = "attendance";

      
    $sNum = "";
    
    $lecture = "";
    
    
    if(isset($_POST['sNum'])){
        
        $sNum = $_POST['sNum'];
        
    }
    
    if(isset($_POST['lecture'])){
        
        $sNum = $_POST['lecture'];
        
    }
      $userObject = new User();

 //logatten
	if(!empty($sNum) && !empty($lecture)){
        
        
        $json_logA = $userObject->logAttendance($sNum, $lecture);
        
        echo json_encode($json_logA);
    }
    
  
  
    ?>
