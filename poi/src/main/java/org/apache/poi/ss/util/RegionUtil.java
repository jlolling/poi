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

package org.apache.poi.ss.util;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellPropertyType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.util.Removal;

/**
 * Various utility functions that make working with a region of cells easier.
 */
public final class RegionUtil {

    private RegionUtil() {
        // no instances of this class
    }

    /**
     * For setting the same property on many cells to the same value
     */
    private static final class CellPropertySetter {

        private final CellPropertyType property;
        private final Object _propertyValue;

        @Deprecated
        @Removal(version = "7.0.0")
        public CellPropertySetter(String propertyName, int value) {
            this(CellUtil.namePropertyMap.get(propertyName), value);
        }

        @Deprecated
        @Removal(version = "7.0.0")
        public CellPropertySetter(String propertyName, BorderStyle value) {
            this(CellUtil.namePropertyMap.get(propertyName), value);
        }

        /**
         * @param property The property to set
         * @param value The value to set the property to
         * @since POI 5.4.0
         */
        public CellPropertySetter(CellPropertyType property, int value) {
            this.property = property;
            _propertyValue = Integer.valueOf(value);
        }

        /**
         * @param property The property to set
         * @param value The value to set the property to
         * @since POI 5.4.0
         */
        public CellPropertySetter(CellPropertyType property, BorderStyle value) {
            this.property = property;
            _propertyValue = value;
        }

        public void setProperty(Row row, int column) {
            // create cell if it does not exist
            Cell cell = CellUtil.getCell(row, column);
            CellUtil.setCellStyleProperty(cell, property, _propertyValue);
        }
    }

    /**
     * Sets the left border style for a region of cells by manipulating the cell style of the individual
     * cells on the left
     *
     * @param border The new border
     * @param region The region that should have the border
     * @param sheet The sheet that the region is on.
     * @since POI 3.16 beta 1
     */
    public static void setBorderLeft(BorderStyle border, CellRangeAddress region, Sheet sheet) {
        int rowStart = region.getFirstRow();
        int rowEnd = region.getLastRow();
        int column = region.getFirstColumn();

        CellPropertySetter cps = new CellPropertySetter(CellPropertyType.BORDER_LEFT, border);
        for (int i = rowStart; i <= rowEnd; i++) {
            cps.setProperty(CellUtil.getRow(i, sheet), column);
        }
    }

    /**
     * Sets the left border color for a region of cells by manipulating the cell style of the individual
     * cells on the left
     *
     * @param color The color of the border
     * @param region The region that should have the border
     * @param sheet The sheet that the region is on.
     * @since POI 3.15 beta 2
     */
    public static void setLeftBorderColor(int color, CellRangeAddress region, Sheet sheet) {
        int rowStart = region.getFirstRow();
        int rowEnd = region.getLastRow();
        int column = region.getFirstColumn();

        CellPropertySetter cps = new CellPropertySetter(CellPropertyType.LEFT_BORDER_COLOR, color);
        for (int i = rowStart; i <= rowEnd; i++) {
            cps.setProperty(CellUtil.getRow(i, sheet), column);
        }
    }

    /**
     * Sets the right border style for a region of cells by manipulating the cell style of the individual
     * cells on the right
     *
     * @param border The new border
     * @param region The region that should have the border
     * @param sheet The sheet that the region is on.
     * @since POI 3.16 beta 1
     */
    public static void setBorderRight(BorderStyle border, CellRangeAddress region, Sheet sheet) {
        int rowStart = region.getFirstRow();
        int rowEnd = region.getLastRow();
        int column = region.getLastColumn();

        CellPropertySetter cps = new CellPropertySetter(CellPropertyType.BORDER_RIGHT, border);
        for (int i = rowStart; i <= rowEnd; i++) {
            cps.setProperty(CellUtil.getRow(i, sheet), column);
        }
    }

    /**
     * Sets the right border color for a region of cells by manipulating the cell style of the individual
     * cells on the right
     *
     * @param color The color of the border
     * @param region The region that should have the border
     * @param sheet The sheet that the region is on.
     * @since POI 3.15 beta 2
     */
    public static void setRightBorderColor(int color, CellRangeAddress region, Sheet sheet) {
        int rowStart = region.getFirstRow();
        int rowEnd = region.getLastRow();
        int column = region.getLastColumn();

        CellPropertySetter cps = new CellPropertySetter(CellPropertyType.RIGHT_BORDER_COLOR, color);
        for (int i = rowStart; i <= rowEnd; i++) {
            cps.setProperty(CellUtil.getRow(i, sheet), column);
        }
    }

    /**
     * Sets the bottom border style for a region of cells by manipulating the cell style of the individual
     * cells on the bottom
     *
     * @param border The new border
     * @param region The region that should have the border
     * @param sheet The sheet that the region is on.
     * @since POI 3.16 beta 1
     */
    public static void setBorderBottom(BorderStyle border, CellRangeAddress region, Sheet sheet) {
        int colStart = region.getFirstColumn();
        int colEnd = region.getLastColumn();
        int rowIndex = region.getLastRow();
        CellPropertySetter cps = new CellPropertySetter(CellPropertyType.BORDER_BOTTOM, border);
        Row row = CellUtil.getRow(rowIndex, sheet);
        for (int i = colStart; i <= colEnd; i++) {
            cps.setProperty(row, i);
        }
    }

    /**
     * Sets the bottom border color for a region of cells by manipulating the cell style of the individual
     * cells on the bottom
     *
     * @param color The color of the border
     * @param region The region that should have the border
     * @param sheet The sheet that the region is on.
     * @since POI 3.15 beta 2
     */
    public static void setBottomBorderColor(int color, CellRangeAddress region, Sheet sheet) {
        int colStart = region.getFirstColumn();
        int colEnd = region.getLastColumn();
        int rowIndex = region.getLastRow();
        CellPropertySetter cps = new CellPropertySetter(CellPropertyType.BOTTOM_BORDER_COLOR, color);
        Row row = CellUtil.getRow(rowIndex, sheet);
        for (int i = colStart; i <= colEnd; i++) {
            cps.setProperty(row, i);
        }
    }

    /**
     * Sets the top border style for a region of cells by manipulating the cell style of the individual
     * cells on the top
     *
     * @param border The new border
     * @param region The region that should have the border
     * @param sheet The sheet that the region is on.
     * @since POI 3.16 beta 1
     */
    public static void setBorderTop(BorderStyle border, CellRangeAddress region, Sheet sheet) {
        int colStart = region.getFirstColumn();
        int colEnd = region.getLastColumn();
        int rowIndex = region.getFirstRow();
        CellPropertySetter cps = new CellPropertySetter(CellPropertyType.BORDER_TOP, border);
        Row row = CellUtil.getRow(rowIndex, sheet);
        for (int i = colStart; i <= colEnd; i++) {
            cps.setProperty(row, i);
        }
    }

    /**
     * Sets the top border color for a region of cells by manipulating the cell style of the individual
     * cells on the top
     *
     * @param color The color of the border
     * @param region The region that should have the border
     * @param sheet The sheet that the region is on.
     * @since POI 3.15 beta 2
     */
    public static void setTopBorderColor(int color, CellRangeAddress region, Sheet sheet) {
        int colStart = region.getFirstColumn();
        int colEnd = region.getLastColumn();
        int rowIndex = region.getFirstRow();
        CellPropertySetter cps = new CellPropertySetter(CellPropertyType.TOP_BORDER_COLOR, color);
        Row row = CellUtil.getRow(rowIndex, sheet);
        for (int i = colStart; i <= colEnd; i++) {
            cps.setProperty(row, i);
        }
    }
}
