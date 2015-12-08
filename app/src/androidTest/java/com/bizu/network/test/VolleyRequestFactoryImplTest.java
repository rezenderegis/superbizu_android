package com.bizu.network.test;

import com.bizu.network.ResponseListener;
import com.bizu.network.VolleyRequestFactoryException;
import com.bizu.network.VolleyRequestFactoryImpl;

/**
 * Created by andre.lmello on 12/6/15.
 */
public class VolleyRequestFactoryImplTest extends VolleyRequestFactoryTest {

    public void setUp() {
        super.setUp();
    }

    public void beforeEach() {
        mFactory = new VolleyRequestFactoryImpl();
    }

    public void testNewInstance_HappyPath() throws VolleyRequestFactoryException {
        beforeEach();
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
