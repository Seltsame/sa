<div id="header">
    <h2>${groupName}</h2>
</div>
<div id="content">
    <table class="datatable"
           border="1">
        <tr>
            <th>Дата принятия</th>
            <th>Фио</th>
            <th>Действия</th>
        </tr>
        <#list studentList as student>
            <tr>
                <td>${student.creationDate}</td>
                <td>${student.name}</td>
                <td>
                    <a href="URL">Edit</a>
                </td>
            </tr>
        </#list>
    </table>
    <fieldset>
        <legend>Добавить студента</legend>
        <form name="student" action="${'/student/add/'+groupId+'/'}" method="post">
            Имя группы : <input type="text" name="studentName"/><br/>
            <input type="submit" value="Добавить студента"/>
        </form>
    </fieldset>
    <br/>
</div>

