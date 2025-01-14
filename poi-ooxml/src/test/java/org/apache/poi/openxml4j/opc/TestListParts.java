/* ====================================================================
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
==================================================================== */

package org.apache.poi.openxml4j.opc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.TreeMap;

import org.apache.logging.log4j.Logger;
import org.apache.poi.logging.PoiLogManager;
import org.apache.poi.openxml4j.OpenXML4JTestDataSamples;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public final class TestListParts {
    private static final Logger LOG = PoiLogManager.getLogger(TestListParts.class);

    private TreeMap<PackagePartName, String> expectedValues;

    private TreeMap<PackagePartName, String> values;

    @BeforeEach
    void setUp() throws Exception {
        values = new TreeMap<>();

        // Expected values
        expectedValues = new TreeMap<>();
        expectedValues.put(PackagingURIHelper.createPartName("/_rels/.rels"),
                "application/vnd.openxmlformats-package.relationships+xml");

        expectedValues
                .put(PackagingURIHelper.createPartName("/docProps/app.xml"),
                        "application/vnd.openxmlformats-officedocument.extended-properties+xml");
        expectedValues.put(PackagingURIHelper
                .createPartName("/docProps/core.xml"),
                "application/vnd.openxmlformats-package.core-properties+xml");
        expectedValues.put(PackagingURIHelper
                .createPartName("/word/_rels/document.xml.rels"),
                "application/vnd.openxmlformats-package.relationships+xml");
        expectedValues
                .put(
                        PackagingURIHelper.createPartName("/word/document.xml"),
                        "application/vnd.openxmlformats-officedocument.wordprocessingml.document.main+xml");
        expectedValues
                .put(PackagingURIHelper.createPartName("/word/fontTable.xml"),
                        "application/vnd.openxmlformats-officedocument.wordprocessingml.fontTable+xml");
        expectedValues.put(PackagingURIHelper
                .createPartName("/word/media/image1.gif"), "image/gif");
        expectedValues
                .put(PackagingURIHelper.createPartName("/word/settings.xml"),
                        "application/vnd.openxmlformats-officedocument.wordprocessingml.settings+xml");
        expectedValues
                .put(PackagingURIHelper.createPartName("/word/styles.xml"),
                        "application/vnd.openxmlformats-officedocument.wordprocessingml.styles+xml");
        expectedValues.put(PackagingURIHelper
                .createPartName("/word/theme/theme1.xml"),
                "application/vnd.openxmlformats-officedocument.theme+xml");
        expectedValues
                .put(
                        PackagingURIHelper
                                .createPartName("/word/webSettings.xml"),
                        "application/vnd.openxmlformats-officedocument.wordprocessingml.webSettings+xml");
    }

    /**
     * List all parts of a package.
     */
    @Test
    void testListParts() throws InvalidFormatException, IOException {
        try (InputStream is = OpenXML4JTestDataSamples.openSampleStream("sample.docx");
             OPCPackage p = OPCPackage.open(is)) {

            for (PackagePart part : p.getParts()) {
                values.put(part.getPartName(), part.getContentType());
                LOG.atDebug().log(part.getPartName());
            }

            // Compare expected values with values return by the package
            for (PackagePartName partName : expectedValues.keySet()) {
                assertNotNull(values.get(partName));
                assertEquals(expectedValues.get(partName), values.get(partName));
            }
        }
    }
}
