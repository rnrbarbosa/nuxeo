/*
 * (C) Copyright 2007 Nuxeo SA (http://nuxeo.com/) and others.
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
 *     Nuxeo - initial API and implementation
 *
 * $Id$
 */

package org.nuxeo.ecm.platform.ui.web.pathelements;

import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.facet.VersioningDocument;

/**
 * @author <a href="mailto:glefter@nuxeo.com">George Lefter</a>
 */
public class VersionDocumentPathElement extends DocumentPathElement {

    public static final String TYPE = "VersionDocumentPathElement";

    private static final long serialVersionUID = -2151946234154284611L;

    public VersionDocumentPathElement(DocumentModel docModel) {
        super(docModel);
    }

    @Override
    public String getName() {
        VersioningDocument docVer = docModel.getAdapter(VersioningDocument.class);
        String minorVer = docVer.getMinorVersion().toString();
        String majorVer = docVer.getMajorVersion().toString();
        return majorVer + '.' + minorVer;
    }

    @Override
    public String getType() {
        return TYPE;
    }

}
