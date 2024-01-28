package jpabook.jpashop.domain.item;

import jpabook.jpashop.exception.NotEnoughStockException;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;


@SpringBootTest
@Transactional
public class ItemTest {

    @Test
    public void 수량증가() throws Exception {
        //given
        Item item = new Book();
        item.setStockQuantity(10);

        //when
        item.addStock(10);

        //then
        Assertions.assertThat(item.getStockQuantity()).isEqualTo(20);
    }

    @Test
    public void 수량감소() throws Exception {
        //given
        Item item = new Book();
        item.setStockQuantity(10);

        //when
        try {
            item.removeStock(10);
        } catch (NotEnoughStockException e) {
            return;
        }

        //then
        Assertions.assertThat(item.getStockQuantity()).isEqualTo(0);
    }

    @Test(expected = NotEnoughStockException.class)
    public void 수량감소_예외() throws Exception {
        //given
        Item item = new Book();
        item.setStockQuantity(10);

        //when
        item.removeStock(11);


        //then
        fail("예외 발생");
    }
}
