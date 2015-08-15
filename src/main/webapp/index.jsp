<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<script src="static/jquery-2.1.4.min.js"></script>
</head>
<body>
	<script>
		
		var JSONData = {
			name: "TestName",
			type: "GRADEDTEST",
			dateCreated: "10.10.15 12:30", 
			creator: 1,
			start: "10.10.15 12:30", 
			end: "10.10.15 12:30", 
			duration: 999,
			password: "password123",
			questions: []
		};
		
		
		
         $.ajax({
                type: "POST",
                url: 'http://localhost:8080/ask/admin/tests',
                dataType: 'json',
                data:  JSONData,
                success: function(data){
                    alert(data);
                }
			}); 

        alert("POST JSON ------------> "+JSONData);
	</script>
</body>
</html>