package com.pospecstudio.todolist;

import com.pospecstudio.todolist.csv.CSVParser;
import com.pospecstudio.todolist.data.Item;
import com.pospecstudio.todolist.data.ItemHolder;
import com.pospecstudio.todolist.data.ItemListsHolder;
import com.pospecstudio.todolist.data.SaveAndLoad;
import org.junit.Test;

import java.io.*;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void load_lists() {
        ClassLoader classLoader = getClass().getClassLoader();
        assert classLoader != null;
        InputStream inputStream = classLoader.getResourceAsStream("lists.txt");
        assert inputStream != null;
        try (ObjectInputStream oos = new ObjectInputStream(inputStream)) {
            ItemListsHolder val = (ItemListsHolder) oos.readObject();
            assertNotNull(val);
        } catch (IOException | ClassNotFoundException ignored) {
            assert(false);
        }
    }

    @Test
    public void load_list_holder() {
        ClassLoader classLoader = getClass().getClassLoader();
        assert classLoader != null;
        InputStream inputStream = classLoader.getResourceAsStream("general.txt");
        assert inputStream != null;
        try (ObjectInputStream oos = new ObjectInputStream(inputStream)) {
            ItemHolder val = (ItemHolder) oos.readObject();
            assertNotNull(val);
        } catch (IOException | ClassNotFoundException e) {
            assert(false);
        }
    }

    @Test
    public void load_csv() {
        ClassLoader classLoader = getClass().getClassLoader();
        assert classLoader != null;
        InputStream inputStream = classLoader.getResourceAsStream("data.csv");
        try (Reader reader = new InputStreamReader(inputStream)) {
            List<Item> items = CSVParser.readCSV(reader, Item.class);
            assertNotNull(items);
        } catch (IOException e) {
            assert(false);
        }
    }
}