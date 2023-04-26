
package com.companyname.projectname.controller;

import com.companyname.projectname.model.Item;
import com.companyname.projectname.service.ItemService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ItemControllerTest {
    
    @Mock
    private ItemService itemService;
    
    @InjectMocks
    private ItemController itemController;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void testGetAllItems() {
        List<Item> itemList = new ArrayList<>();
        Item item1 = new Item();
        item1.setId(1);
        item1.setName("Item 1");
        item1.setDescription("Description 1");
        item1.setPrice(10.0);
        Item item2 = new Item();
        item2.setId(2);
        item2.setName("Item 2");
        item2.setDescription("Description 2");
        item2.setPrice(20.0);
        itemList.add(item1);
        itemList.add(item2);
        when(itemService.getAllItems()).thenReturn(itemList);
        List<Item> result = itemController.getAllItems(mock(Request.class), mock(Response.class));
        assertEquals(itemList, result);
        verify(itemService, times(1)).getAllItems();
    }
    
    @Test
    public void testGetItemById() {
        Item item = new Item();
        item.setId(1);
        item.setName("Item 1");
        item.setDescription("Description 1");
        item.setPrice(10.0);
        when(itemService.getItemById(1)).thenReturn(item);
        Item result = itemController.getItemById(mock(Request.class), mock(Response.class));
        assertEquals(item, result);
        verify(itemService, times(1)).getItemById(1);
    }
    
    @Test
    public void testCreateItem() {
        Item item = new Item();
        item.setName("Item 1");
        item.setDescription("Description 1");
        item.setPrice(10.0);
        when(itemService.createItem(item)).thenReturn(item);
        Item result = itemController.createItem(mock(Request.class), mock(Response.class));
        assertEquals(item, result);
        verify(itemService, times(1)).createItem(item);
    }
    
    @Test
    public void testUpdateItem() {
        Item item = new Item();
        item.setId(1);
        item.setName("Item 1");
        item.setDescription("Description 1");
        item.setPrice(10.0);
        when(itemService.getItemById(1)).thenReturn(item);
        item.setName("Item 2");
        when(itemService.updateItem(item)).thenReturn(item);
        Item result = itemController.updateItem(mock(Request.class), mock(Response.class));
        assertEquals(item, result);
        verify(itemService, times(1)).getItemById(1);
        verify(itemService, times(1)).updateItem(item);
    }
    
    @Test
    public void testDeleteItem() {
        Item item = new Item();
        item.setId(1);
        item.setName("Item 1");
        item.setDescription("Description 1");
        item.setPrice(10.0);
        when(itemService.getItemById(1)).thenReturn(item);
        itemController.deleteItem(mock(Request.class), mock(Response.class));
        verify(itemService, times(1)).getItemById(1);
        verify(itemService, times(1)).deleteItem(item);
    }
    
}
