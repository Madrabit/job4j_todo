package ru.job4j.todo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.servlets.TodoServlet;
import ru.job4j.todo.store.HiberStore;
import ru.job4j.todo.store.Store;
import ru.job4j.todo.store.StoreStub;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author madrabit on 03.05.2020
 * @version 1$
 * @since 0.1
 */

@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "org.w3c.dom.*", "javax.management.*"})
@RunWith(PowerMockRunner.class)
@PrepareForTest({HiberStore.class})
public class HiberStoreTest {
    @Test
    public void whenAddTaskThenStoreIt() throws IOException {
        Store store = new StoreStub();
        PowerMockito.mockStatic(HiberStore.class);
        Mockito.when(HiberStore.instOf()).thenReturn(store);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(req.getParameter("id")).thenReturn("0");
        when(req.getParameter("name")).thenReturn("Do Some Thing");
        new TodoServlet().doPost(req, resp);
        List<Task> postList = new ArrayList<>(HiberStore.instOf().findAll());
        assertThat(postList.get(0).getName(), is("Do Some Thing"));
        assertThat(HiberStore.instOf().findById(0).getName(), is("Do Some Thing"));
    }

}
