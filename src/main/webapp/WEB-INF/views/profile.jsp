<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="pageTitle" value="TTT - Table Tennis Tracker" scope="request"/>

<jsp:include page="./header.jsp"/>
<form:form commandName="command">

    <div class="container">
        <h2>${command.player.name}</h2>
        <div class="row">
            <div class="col-md-2 thumbnail">
                <img class="img-responsive" alt="140x140" src="http://placehold.it/250x250">
                <div class="caption">
                    <h3>Thumbnail label</h3>
                    <p>...</p>
                </div>
            </div>
            <div class="col-md-10">

                <div class="table-responsive">
                    <h3>Match History</h3>
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th></th>
                            <th>Opponent</th>
                            <th>Result</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="item" items="${command.matches}" varStatus="loop">
                            <tr>
                                <td>${loop.index+1}</td>
                                <td>${item.p2.name}</td>
                                <td>${item.p1Score} - ${item.p2Score}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div> <!-- /container -->
</form:form>

<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
<script src="js/bootstrap.min.js"></script>
</body>
</html>
