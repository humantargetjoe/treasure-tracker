<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>
	
	<div class="container">
		<table class="table table-striped">
			<caption>Your items are</caption>
			<thead>
				<tr>
					<th>Name</th>
					<th>Item Type</th>
					<th>Item Subtype</th>
					<th>Amount</th>
					<th>Description</th>
					<th>Tags</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${items}" var="item">
					<tr>
						<td>${item.name}</td>
						<td>${item.itemType}</td>
						<td>${item.itemSubType}</td>
						<td>${item.amount}</td>
						<td>${item.description}</td>
						<td>${item.tags}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div>
			<a class="button" href="/add-item">Add a item</a>
		</div>
	</div>
<%@ include file="common/footer.jspf" %>