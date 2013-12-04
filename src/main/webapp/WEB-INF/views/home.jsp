<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="pageTitle" value="TTT - Table Tennis Tracker" scope="request"/>

<jsp:include page="./header.jsp"/>
  <form:form commandName="command">

    <div class="container">
		<h2>Leaderboard</h2>
		<div class="table-responsive">
			<table class="table table-striped">
				<thead>
					<tr>
						<th></th>
						<th>Name</th>
						<th>W</th>
						<th>L</th>
					</tr>
				</thead>
				<tbody>
                <c:forEach var="item" items="${command}" varStatus="loop">
                    <tr>
                        <td>${loop.index+1}</td>
                        <td>${item.player.name}</td>
                        <td>${item.matchWins}</td>
                        <td>${item.matchLosses}</td>
                    </tr>
                </c:forEach>
				</tbody>
			</table>
		</div>

    </div> <!-- /container -->
  </form:form>

<jsp:include page="./footer.jsp"/>
