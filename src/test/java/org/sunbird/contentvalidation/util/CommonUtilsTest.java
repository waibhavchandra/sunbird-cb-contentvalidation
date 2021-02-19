package org.sunbird.contentvalidation.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CommonUtilsTest {

    final String nonOffensiveConst = "Not Offensive";

    final String offensiveConst = "Offensive";

    @InjectMocks
    private CommonUtils commonUtils;

    final String filePath = "https://igot.blob.core.windows.net/content/content/do_113155331519225856127/artifact/sample.pdf";

    final String filePathForNullByte = "0/sample.pdf";

    @Test
    void getFileName(){
        assertEquals(-1, commonUtils.indexOfLastSeparator(null));
        assertNull(commonUtils.getFileName(null));
        assertEquals("sample.pdf", commonUtils.getFileName(filePath));
    }

    @Test
    void emptyCheck(){
        assertTrue(commonUtils.emptyCheck(""));
        assertFalse(commonUtils.emptyCheck("Some Random String"));
    }

    @Test
    void getProfanityClassification(){
        assertEquals(offensiveConst, commonUtils.getProfanityClassification(nonOffensiveConst, offensiveConst));
        assertEquals(offensiveConst, commonUtils.getProfanityClassification(offensiveConst, nonOffensiveConst));
    }

    @Test
    void failIfNullBytePresent(){
        assertEquals(offensiveConst, commonUtils.getProfanityClassification(nonOffensiveConst, offensiveConst));
        assertEquals(offensiveConst, commonUtils.getProfanityClassification(offensiveConst, nonOffensiveConst));
    }

    @Test
    void checkNullByte(){
        assertEquals("sample.pdf", commonUtils.getFileName(filePathForNullByte));
    }
}
