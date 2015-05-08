/*
 * Copyright (C) 2015 jsonwebtoken.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.jsonwebtoken.impl

import org.testng.annotations.Test
import static org.testng.Assert.*

class JwtMapTest {

    @Test
    void testToDateFromDate() {

        def d = new Date()

        Date date = JwtMap.toDate(d, 'foo')

        assertSame date, d

    }

    @Test
    void testToDateFromString() {

        Date d = new Date(2015, 1, 1, 12, 0, 0)

        String s = (d.getTime() / 1000) + '' //JWT timestamps are in seconds - need to strip millis

        Date date = JwtMap.toDate(s, 'foo')

        assertEquals date, d

    }

    @Test
    void testToDateFromNonDateObject() {
        try {
            JwtMap.toDate(new Object() { @Override public String toString() {return 'hi'} }, 'foo')
            fail()
        } catch (IllegalStateException iae) {
            assertEquals iae.message, "Cannot convert 'foo' value [hi] to Date instance."
        }
    }

    @Test
    void testContainsKey() {
        def m = new JwtMap()
        m.put('foo', 'bar')
        assertTrue m.containsKey('foo')
    }

    @Test
    void testContainsValue() {
        def m = new JwtMap()
        m.put('foo', 'bar')
        assertTrue m.containsValue('bar')
    }

    @Test
    void testRemoveByPuttingNull() {
        def m = new JwtMap()
        m.put('foo', 'bar')
        assertTrue m.containsKey('foo')
        assertTrue m.containsValue('bar')
        m.put('foo', null)
        assertFalse m.containsKey('foo')
        assertFalse m.containsValue('bar')
    }

    @Test
    void testPutAll() {
        def m = new JwtMap();
        m.putAll([a: 'b', c: 'd'])
        assertEquals m.size(), 2
        assertEquals m.a, 'b'
        assertEquals m.c, 'd'
    }

    @Test
    void testPutAllWithNullArgument() {
        def m = new JwtMap();
        m.putAll((Map)null)
        assertEquals m.size(), 0
    }

    @Test
    void testClear() {
        def m = new JwtMap();
        m.put('foo', 'bar')
        assertEquals m.size(), 1
        m.clear()
        assertEquals m.size(), 0
    }

    @Test
    void testKeySet() {
        def m = new JwtMap()
        m.putAll([a: 'b', c: 'd'])
        assertEquals( m.keySet(), ['a', 'c'] as Set)
    }

    @Test
    void testValues() {
        def m = new JwtMap()
        m.putAll([a: 'b', c: 'd'])
        assertEquals( m.values(), ['b', 'd'] as Set)
    }
}
