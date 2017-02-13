<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${requestScope.jspSiguiente==null}">
	<jsp:forward page="navegacionInvalida" />
</c:if>
