<div class="pagination">
    <c:if test="${listInfo.currentPage > 1}">
        <a href="${api}?key=${key}&page=${listInfo.currentPage - 1}">Previous</a>
    </c:if>

    <c:forEach var="i" begin="1" end="${listInfo.totalPage}">
        <c:choose>
            <c:when test="${i == listInfo.currentPage}">
                <span><b>${i}</b></span>
            </c:when>
            <c:otherwise>
                <a href="${api}?key=${key}&page=${i}">${i}</a>
            </c:otherwise>
        </c:choose>
    </c:forEach>

    <c:if test="${listInfo.currentPage < listInfo.totalPage}">
        <a href="${api}?key=${key}&page=${listInfo.currentPage + 1}">Next</a>
    </c:if>
</div>