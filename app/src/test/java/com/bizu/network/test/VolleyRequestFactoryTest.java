package com.bizu.network.test;

import com.android.volley.Request;
import com.bizu.network.ResponseListener;
import com.bizu.network.VolleyRequestFactory;
import com.bizu.network.VolleyRequestFactoryException;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by andre.lmello on 12/2/15.
 */
public class VolleyRequestFactoryTest {

    @Before
    public void setUp() {
        mFactory = mock(VolleyRequestFactory.class);
        mListener = mock(ResponseListener.class);
    }

    @Test
    public void testFactory_HappyPath() throws VolleyRequestFactoryException {
        when(mFactory.newInstance(Object.class, mListener)).thenReturn(mock(Request.class));
        assertNotNull("Request cannot be null", mFactory.newInstance(Object.class, mListener));
    }

    @Test(expected = VolleyRequestFactoryException.class)
    public void testFactory_ExceptionPath() throws VolleyRequestFactoryException {
        when(mFactory.newInstance(Object.class, mListener)).thenThrow(VolleyRequestFactoryException.class);
        mFactory.newInstance(Object.class, mListener);
        fail("an exception must be thrown.");
    }

    protected VolleyRequestFactory mFactory;
    protected ResponseListener<?> mListener;
}
