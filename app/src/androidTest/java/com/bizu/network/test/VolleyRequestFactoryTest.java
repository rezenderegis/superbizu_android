package com.bizu.network.test;

import android.test.AndroidTestCase;

import com.android.volley.Request;
import com.bizu.network.ResponseListener;
import com.bizu.network.VolleyRequestFactory;
import com.bizu.network.VolleyRequestFactoryException;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.mock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

/**
 * Created by andre.lmello on 12/2/15.
 */
public class VolleyRequestFactoryTest extends AndroidTestCase {

    public void setUp() {
        mFactory = mock(VolleyRequestFactory.class);
        mListener = mock(ResponseListener.class);
    }

    public void testFactory_HappyPath() throws VolleyRequestFactoryException {
        expect(mFactory.newInstance(Object.class, mListener)).andReturn(mock(Request.class));
        replay(mFactory);
        assertNotNull("Request cannot be null", mFactory.newInstance(Object.class, mListener));
        verify(mFactory);
    }

    public void testFactory_ExceptionPath() {
        try {
            expect(mFactory.newInstance(Object.class, mListener)).andThrow(new VolleyRequestFactoryException(""));
            replay(mFactory);
            mFactory.newInstance(Object.class, mListener);
            fail("an exception must be thrown.");
        } catch (VolleyRequestFactoryException e) {
            e.printStackTrace();
            verify(mFactory);
        }
    }

    protected VolleyRequestFactory mFactory;
    protected ResponseListener<?> mListener;
}
