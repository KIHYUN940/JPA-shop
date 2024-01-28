package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ItemServiceTest {

    @Autowired
    ItemService itemService;
    @Autowired
    ItemRepository itemRepository;

    @Test
    @DirtiesContext
    public void 아이템_저장() throws Exception {
        //given
        Item item = new Book();
        item.setName("jpa");
        item.setPrice(20000);

        //when
        itemService.saveItem(item);

        //then
        Item savedItem = itemService.findOne(item.getId());
        Assertions.assertThat(savedItem).isNotNull();
        Assertions.assertThat(savedItem.getName()).isEqualTo("jpa");
        Assertions.assertThat(savedItem.getPrice()).isEqualTo(20000);
    }

    @Test
    @DirtiesContext
    public void 아이템_조회() throws Exception {
        //given
        Item item1 = new Book();
        item1.setName("book1");
        item1.setPrice(10000);

        Item item2 = new Book();
        item2.setName("book2");
        item2.setPrice(20000);

        //when
        itemService.saveItem(item1);
        itemService.saveItem(item2);
        List<Item> items = itemService.findItems();

        //then
        Assertions.assertThat(items).hasSize(2);
        Assertions.assertThat(items.get(0).getName()).isEqualTo("book1");
        Assertions.assertThat(items.get(1).getPrice()).isEqualTo(20000);
    }

    @Test
    public void 아이템_Id조회() throws Exception {
        //given
        Item item1 = new Book();
        item1.setName("Spring");
        item1.setPrice(30000);

        //when
        itemService.saveItem(item1);
        Item itemId = itemService.findOne(item1.getId());

        //then
        Assertions.assertThat(itemId).isNotNull();
        Assertions.assertThat(itemId.getId()).isEqualTo(1);
    }
}