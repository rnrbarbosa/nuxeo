/*
 * (C) Copyright 2006-2011 Nuxeo SA (http://nuxeo.com/) and others.
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
 *
 * Contributors:
 *     bstefanescu
 */
package org.nuxeo.connect.update.standalone.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

import org.junit.Before;
import org.nuxeo.common.Environment;
import org.nuxeo.common.utils.FileUtils;
import org.nuxeo.connect.update.LocalPackage;
import org.nuxeo.connect.update.task.Task;
import org.nuxeo.connect.update.util.IOUtils;
import org.nuxeo.connect.update.util.PackageBuilder;
import org.nuxeo.connect.update.xml.XmlWriter;

/**
 * @author <a href="mailto:bs@nuxeo.com">Bogdan Stefanescu</a>
 */
public class TestCopy extends AbstractCommandTest {

    private File goldStandardFile;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        goldStandardFile = new File(Environment.getDefault().getConfig(), "goldstandard.properties");
        FileUtils.writeFile(goldStandardFile, "param1=value1");
    }

    @Override
    protected File createPackage() throws IOException, URISyntaxException {
        return getTestPackageZip("test-copy");
    }

    @Override
    protected void installDone(Task task, Throwable error) throws Exception {
        super.installDone(task, error);
        LocalPackage pkg = task.getPackage();
        File src = pkg.getData().getEntry("test.properties");
        File dst = getTargetFile();
        assertTrue(dst.isFile());
        assertEquals(IOUtils.createMd5(src), IOUtils.createMd5(dst));
        Properties goldstandard = new Properties();
        goldstandard.load(new FileInputStream(goldStandardFile));
        assertEquals("Original property is missing", "value1", goldstandard.getProperty("param1"));
        assertEquals("Appended property is missing", "value2", goldstandard.getProperty("param2"));
        assertEquals("Appended property is missing", "value3", goldstandard.getProperty("param3"));
    }

    @Override
    protected void uninstallDone(Task task, Throwable error) throws Exception {
        super.uninstallDone(task, error);
        assertFalse(getTargetFile().exists());
        Properties goldstandard = new Properties();
        goldstandard.load(new FileInputStream(goldStandardFile));
        assertEquals("Original property is missing", "value1", goldstandard.getProperty("param1"));
        assertNull("Appended property must be removed", goldstandard.getProperty("param2"));
        assertNull("Appended property must be removed", goldstandard.getProperty("param3"));
    }

    protected File getTargetFile() {
        return new File(Environment.getDefault().getConfig(), "test.properties");
    }

}
