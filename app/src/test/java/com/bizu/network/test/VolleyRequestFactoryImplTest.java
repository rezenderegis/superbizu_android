package com.bizu.network.test;

import com.bizu.network.ResponseListener;
import com.bizu.network.VolleyRequestFactoryException;
import com.bizu.network.VolleyRequestFactoryImpl;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

/**
 * Created by andre.lmello on 12/6/15.
 */
public class VolleyRequestFactoryImplTest extends VolleyRequestFactoryTest {

    @Test
    public void testNewInstance_HappyPath() throws VolleyRequestFactoryException {
        mFactory = new VolleyRequestFactoryImpl();
        assertNotNull("newInstance must return instantiated objects."
                , mFactory.newInstance(String.class
                    , new ResponseListenerImpl()
                )
        );
    }

    private static class ResponseListenerImpl implements ResponseListener {
        @Override
        public void onResponse(Object response) {

        }
    }
}
