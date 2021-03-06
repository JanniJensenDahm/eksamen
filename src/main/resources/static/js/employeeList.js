$(document).ready(function (){
    getList();

    $("input[name=filterLocations]").click(function () {
        getList();
    });

});

function getList() {
    var filterLocations = [];
    $('input[type=checkbox]').each(function () {
        if($(this).prop("checked") == true){
            filterLocations.push($(this).val());
        }
    });
    //console.log(filterLocations);
    $.ajax({
        type: "POST",
        data: {checkboxesLocation: filterLocations},
        url: "/getEmployees",
        success: function (data) {
            $('#table').empty();

            $.each(data[0], function (key, value) {
                $('#table').append('<th align="left">' + value + '</th>')
            });
          $('#table').append('<th align="left" th:if="${isAdmin}">' + 'Redigér' + '</th>');


          for (var i = 1; i < data.length; i++) {
                var string = '<tr>';
                $.each(data[i], function (key, value) {
                    string += '<td>' + value + '</td>'
                });
                string += "<td><a href='/medarbejder?email="+data[i][2]+"'><i class='fas fa-edit'></i></a></td>";
                string += '</tr>';
                $('#table').append(string)
            }

           console.log(data);
        }
    });
}