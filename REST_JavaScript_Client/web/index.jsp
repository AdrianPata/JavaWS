<%-- 
    Document   : index
    Created on : Aug 8, 2017, 1:06:44 PM
    Author     : 10051644
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>REST_JavaScript_Client</title>
        <script type = "text/javascript" src = "http://code.jquery.com/jquery-latest.min.js"></script>
        <script type = 'text/javascript'>
            var url = "http://localhost:8080/REST_JavaScript_Client/getAgenda.jsp";
            
            function displayPreds(preds) {
                $('#container').empty(); // clear the old list, if any
                $.each(preds, function(ind, val) { 
                     $('#container').append('<li>' + preds[ind].name + ': ' + 
                                                     preds[ind].phone + '</li>'); } );
             }
             
             function fetch() {
                $.ajax({
                         url:         url,
                         method:      'GET',
                         dataType:    'json',
                         contentType: "application/json; charset=utf-8",
                         cache:       false,
                         success:     function(res) { displayPreds(res.persons.agenda.person); },
                         error:       function(res) { console.log(res); },
                         complete:    function () {
                                         setTimeout(function() { fetch() }, 5000)}});
            }

            $(document).ready(fetch); // invoked after DOM is built and loaded
        </script>
    </head>
    <body>
        <h1>REST_JavaScript_Client</h1>
        <ul id = 'container'></ul>
    </body>
</html>
