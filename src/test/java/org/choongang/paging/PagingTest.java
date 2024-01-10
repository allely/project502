package org.choongang.paging;

import jakarta.servlet.http.HttpServletRequest;
import org.choongang.commons.Pagination;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class PagingTest {

    @Mock   // 내부에서 모의 객체 생성 (웹서버 없이 사용할 수 있도록)
    private HttpServletRequest request;

    @Test
    @DisplayName("페이징")
    void pagingTest() {
        Pagination pagination = new Pagination(23, 12345, 5, 20);
        List<String[]> data = pagination.getPages();
        data.forEach(s -> System.out.println(Arrays.toString(s)));
        System.out.println(pagination);

        int totalPages = (int)Math.ceil(pagination.getTotal() / (double)pagination.getLimit());

        assertEquals(totalPages, pagination.getTotalPages());
    }

    @Test
    @DisplayName("페이징 쿼리스트링이 유지되는지 테스트")
    void pagingWithRequestTest() {
        given(request.getQueryString())
                .willReturn("?orderStatus=CASH&userNm=name&page=3");

        Pagination pagination = new Pagination(23, 12345, 5, 20, request);
        List<String[]> data = pagination.getPages();
        data.forEach(s -> System.out.println(Arrays.toString(s)));

    }

}
