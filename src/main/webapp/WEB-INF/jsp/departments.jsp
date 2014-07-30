<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<h3>Department Page</h3>
<div id="createdDepartmentContainer" style="display: none"><h2>Successfully created new department "${createdDept.name}"</h2></div>
<script type="text/javascript">var CREATED_DEPT = "${createdDept.name}";</script>
<div id="addBtn-container" <sec:authorize access="hasRole('ROLE_ADMIN')">style="display:block"</sec:authorize> style="display:none">
	<button type="button" id="addBtn" style="width: 45px;">Add</button>
</div>
<div id="addEntity" style="display: none">
	<fieldset>
		<legend>Add Department</legend>
		<form name="newDept" id="newDepartment" action="newDepart" method="post">
			<div>
				<labeL>Dept Name: *</labeL><input type="text" name="name" id="newDeptName" required />
				<input type="hidden" name="isActive" value="true">
				<labeL>Parent Dept:</label>
				<select name="parentDepartment.id">
				<option value="">...</option>
					<c:forEach items="${depts}" var="pdept">
						<option value="${pdept.id}" class="parentSelect-${pdept.id}">${pdept.name}</option>
					</c:forEach>
				</select>
				<br><br>
				<button type="submit">Save</button>
				<button type="reset" id="cancelBtn" value="reset">Cancel</button>
			</div>
			<br>
			<div>Required Fields indicated with a *</div>
		</form>
	</fieldset>
</div>

<div id="editContainer" style="display: none">
		<fieldset>
			<legend>Edit Department</legend>
			<form name="editDept" class="editFormClass" action="updateDepart" method="post">
				<div>
					<labeL>Dept Name: *</labeL><input type="text" name="name" value="" id="editName" required />
					<input type="hidden" name="id" id="editId" value="" />
					<input type="hidden" name="isActive" id="editIsActive" value="true" />
					<label>Parent Dept:</label>
					<select id="editParent">
						<option value="">...</option>
						<c:forEach items="${depts}" var="pdept">
							<option value="${pdept.id}" class="parentSelect-${pdept.id}">${pdept.name}</option>
						</c:forEach>
					</select>
					<br><br>
					<button type="submit">Save</button>
					<button type="button" onClick="performDelete()">Delete</button>
					<button type="reset" id="cancelEditBtn" value="reset">Cancel</button>
				</div>
				<br>
				<div>Required Fields indicated with a *</div>
			</form>
		</fieldset>
	</div></td>

<table id="t1">
	<tr>
		<!-- <sec:authorize access="hasRole('ROLE_ADMIN')"> -->
		<!-- <th>Task</th></sec:authorize> -->
		<th>Dept Name</th>
		<th>Parent Dept</th>
	</tr>
	<!--
	<c:forEach items="${depts}" var="dept">
		<tr class="parentSelect-${dept.id}">
			<td>${dept.name}</td>
			<td>${dept.parentDepartment.name}</td>
			<td <sec:authorize access="hasRole('ROLE_ADMIN')">style="display:block"</sec:authorize> style="display:none">
			<div class="editBtn-containerClass"
					id="editBtn-container-${dept.id}">
					<button type="button" class="editBtnClass"
						id="editBtn-${dept.id}">Edit</button>
				</div></td>
			<td>
			<div id="editEntity-${dept.id}" style="display: none">
					<fieldset>
						<legend>Edit Department</legend>
						<form name="editDept" class="editFormClass" action="updateDepart" method="post">
							<div>
								<labeL>Dept Name: *</labeL><input type="text" name="name" value="${dept.name}" id="editName-${dept.id}" required />
								<input type="hidden" name="doDelete" value="false" />
								<input type="hidden" name="id" value="${dept.id}" />
								<input type="hidden" id="activeVal-${dept.id}" name="isActive" value="true" />
								<label>Parent Dept:</label>
								<select name="parentDepartment.id">
									<option value="">...</option>
									<c:forEach items="${depts}" var="pdept">
										<c:choose>
										<c:when test="${pdept.id == dept.parentDepartment.id}">
											<option value="${pdept.id}" class="parentSelect-${dept.id}" selected>${pdept.name}</option>
										</c:when>
										<c:otherwise>
											<option value="${pdept.id}" class="parentSelect-${dept.id}">${pdept.name}</option>
										</c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
								<br><br>
								<button type="submit">Save</button>
								<button type="button" class="deleteBtnClass" id="deleteBtn-${dept.id}">Delete</button>
								<button type="reset" class="cancelEditBtnClass" id="cancelEditBtn-${dept.id}" value="reset">Cancel</button>
							</div>
							<br>
							<div>Required Fields indicated with a *</div>
						</form>
					</fieldset>
				</div></td>
		</tr>
	</c:forEach>
	-->
</table>
