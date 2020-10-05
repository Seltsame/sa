<div id="header">
    <h2>Группы университета ScaleApps</h2>
</div>
<div id="content">
    <table class="datatable"
           border="1">
        <tr>
            <th>Номер</th>
            <th>Количество студентов</th>
            <th>Действия</th>
        </tr>
        <#list groupList as group>
            <tr>
                <td>${group.groupName}</td>
                <td>${group.studentQuantity}</td>
                <td>
                    <a href="${'../student/getStudentByGroupId/' +group.id+'/'}">Edit</a>
                </td>
            </tr>
        </#list>
    </table>
    <fieldset>
        <legend>Добавить группу</legend>
        <form name="group" action="/group/add" method="post">
            Имя группы : <input type="text" name="groupName"/><br/>
            <input type="submit" value="Добавить группу"/>
        </form>
    </fieldset>
    <br/>
</div>