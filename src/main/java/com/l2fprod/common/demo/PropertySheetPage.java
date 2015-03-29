/*
 * Copyright 2015 Matthew Aguirre
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
package com.l2fprod.common.demo;

import com.l2fprod.common.beans.BeanBinder;
import static com.l2fprod.common.demo.PropertySheetPage.Seasons.SUMMER;
import com.l2fprod.common.propertysheet.PropertySheet;
import com.l2fprod.common.propertysheet.PropertySheetPanel;
import com.l2fprod.common.swing.LookAndFeelTweaks;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.io.File;
import java.util.ListResourceBundle;

import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * PropertySheetPage. <br>
 *
 */
public class PropertySheetPage extends JPanel {

    public enum Seasons {

        SUMMER,
        FALL,
        WINTER,
        SPRING
    }

    public PropertySheetPage() {
        setLayout(LookAndFeelTweaks.createVerticalPercentLayout());

        JTextArea message = new JTextArea();
        message.setText(PropertySheetMain.RESOURCE.getString("Main.sheet1.message"));
        LookAndFeelTweaks.makeMultilineLabel(message);
        add(message);

        final Bean data = new Bean();
        data.setName("John Smith");
        data.setText("Any text here");
        data.setColor(Color.green);
        data.setPath(new File("."));
        data.setVisible(true);
        data.setTime(System.currentTimeMillis());
        data.setCalendar(java.util.Calendar.getInstance());

        final PropertySheetPanel sheet = new PropertySheetPanel();
        sheet.setMode(PropertySheet.VIEW_AS_CATEGORIES);
        sheet.setDescriptionVisible(true);
        sheet.setSortingCategories(true);
        sheet.setSortingProperties(true);
        sheet.setRestoreToggleStates(true);
        add(sheet, "*");

        // everytime a property change, update the sheet with it
        new BeanBinder(data, sheet);
    }

    public static class Bean {

        private java.util.Calendar calendar;

        public java.util.Calendar getCalendar() {
            return calendar;
        }

        public void setCalendar(java.util.Calendar value) {
            calendar = value;
        }

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        private long time;

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public String getVersion() {
            return "1.0";
        }

        private boolean visible;

        public boolean isVisible() {
            return visible;
        }

        public void setVisible(boolean visible) {
            this.visible = visible;
        }

        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        private File path;

        public File getPath() {
            return path;
        }

        public void setPath(File path) {
            this.path = path;
        }

        private Color color = Color.blue;

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }

        private double doubleValue = 121210.4343543;

        public void setADouble(double d) {
            this.doubleValue = d;
        }

        public double getADouble() {
            return doubleValue;
        }

        private Seasons season = SUMMER;

        public void setSeason(Seasons s) {
            season = s;
        }

        public Seasons getSeason() {
            return season;
        }

        private String constrained;

        public String getConstrained() {
            return constrained;
        }

        public void setConstrained(String constrained) throws PropertyVetoException {
            if ("blah".equals(constrained)) {
                throw new PropertyVetoException("e",
                        new PropertyChangeEvent(this, "constrained", this.constrained,
                                constrained));
            }
            this.constrained = constrained;
        }

        @Override
        public String toString() {
            return "[name=" + getName() + ",text=" + getText() + ",time=" + getTime()
                    + ",version=" + getVersion() + ",visible=" + isVisible() + ",id="
                    + getId() + ",path=" + getPath() + ",aDouble=" + getADouble()
                    + ",season=" + getSeason() + "]";
        }

    }
//
//    public static class BeanBeanInfo extends BaseBeanInfo {
//
//        public BeanBeanInfo() {
//            super(Bean.class);
//            addProperty("id").setCategory("General");
//            addProperty("name").setCategory("General");
//            addProperty("text").setCategory("General");
//            addProperty("visible").setCategory("General");
//            addProperty("calendar").setCategory("General");
//
//            // the File attribute will not be shown if running in Java Web
//            // Start, otherwise it will lead to exception when rendering the
//            // value
//            if (System.getProperty("javawebstart.version") == null) {
//                addProperty("path").setCategory("Details");
//            }
//
//            addProperty("time").setCategory("Details");
//            addProperty("color").setCategory("Details");
//            addProperty("aDouble").setCategory("Numbers");
//            addProperty("season").setCategory("Details").setPropertyEditorClass(
//                    SeasonEditor.class);
//            // a readonly property
//            addProperty("version");
//            // a constrained property
//            addProperty("constrained");
//        }
//    }

//    @EditorRegistry(type = {Seasons.class})
//    public static class SeasonEditor extends ComboBoxPropertyEditor {
//
//        public SeasonEditor() {
//            super();
//            setAvailableValues(new String[]{"Spring", "Summer", "Fall", "Winter",});
//            Icon[] icons = new Icon[4];
//            Arrays.fill(icons, UIManager.getIcon("Tree.openIcon"));
//            setAvailableIcons(icons);
//        }
//    }

    public static class BeanRB extends ListResourceBundle {

        @Override
        protected Object[][] getContents() {
            return new Object[][]{{"name", "Name"},
            {"name.shortDescription", "The name of this object<br>Here I'm using multple lines<br>for the property<br>so scrollbars will get enabled"},
            {"text", "Text"}, {"time", "Time"}, {"color", "Background"},
            {"aDouble", "a double"},
            {"season", "Season"},
            {
                "constrained.shortDescription",
                "This property is constrained. Try using <b>blah</b> as the value, the previous value will be restored"}};
        }
    }

}
