package com.example.anti_social;

import com.example.anti_social.comparators.trivialFunction;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class trivialTest {

    @Mock
    trivialFunction f;

    @Test
    public void trivialFuncTest(){
       when(f.add3Intes(1, 2, 3)).thenReturn(6);
       assertEquals(6, f.add3Intes(1,2,3));
    }
}



