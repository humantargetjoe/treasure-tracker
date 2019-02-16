<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>
	
	<div class="container">
		<table class="table table-striped">
			<caption>Containers</caption>
			<thead>
				<tr>
					<th>Name</th>
					<th>Maximum Weight</th>
					<th>Maximum Volume</th>
					<th>Extradimensional</th>
					<th></th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${containers}" var="container">
					<tr>
						<td>${container.name}</td>
						<td>${container.maximumWeight}</td>
						<td>${container.maximumVolume}</td>
						<td>${container.isExtraDimensional}</td>
						<td></td>
						<td></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div>
			<a class="button" href="/add-container">Add a Container</a>
		</div>
	</div>
<%@ include file="common/footer.jspf" %>