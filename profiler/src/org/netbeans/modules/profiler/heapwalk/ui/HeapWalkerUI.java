/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2010 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s):
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2006 Sun
 * Microsystems, Inc. All Rights Reserved.
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 */

package org.netbeans.modules.profiler.heapwalk.ui;

import org.netbeans.modules.profiler.heapwalk.HeapWalker;
import org.netbeans.modules.profiler.heapwalk.HeapWalkerManager;
import org.openide.util.ImageUtilities;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.KeyboardFocusManager;


/**
 *
 * @author Jiri Sedlacek
 */
public class HeapWalkerUI extends TopComponent {
    //~ Static fields/initializers -----------------------------------------------------------------------------------------------

    // -----
    // I18N String constants
    private static final String COMPONENT_DESCR = NbBundle.getMessage(HeapWalkerUI.class, "HeapWalkerUI_ComponentDescr"); // NOI18N
                                                                                                                          // -----

    //~ Instance fields ----------------------------------------------------------------------------------------------------------

    private HeapWalker heapWalker;
    private Component lastFocusOwner;

    //~ Constructors -------------------------------------------------------------------------------------------------------------

    // --- Constructors ----------------------------------------------------------
    public HeapWalkerUI(HeapWalker heapWalker) {
        this.heapWalker = heapWalker;

        initDefaults();
        initComponents();
    }

    //~ Methods ------------------------------------------------------------------------------------------------------------------
    
    public void componentActivated() {
        if (lastFocusOwner != null) {
            lastFocusOwner.requestFocus();
        } else {
            heapWalker.getMainHeapWalker().getPanel().requestFocus();
        }
    }

    public void componentDeactivated() {
        lastFocusOwner = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
    }

    // --- TopComponent support --------------------------------------------------
    public int getPersistenceType() {
        return TopComponent.PERSISTENCE_NEVER;
    }

    protected void componentClosed() {
        HeapWalkerManager.getDefault().heapWalkerClosed(heapWalker);
    }

    protected String preferredID() {
        return this.getClass().getName();
    }

    // --- UI definition ---------------------------------------------------------
    private void initComponents() {
        setLayout(new BorderLayout());
        add(heapWalker.getMainHeapWalker().getPanel(), BorderLayout.CENTER);
    }

    private void initDefaults() {
        setName(heapWalker.getName());
        setIcon(ImageUtilities.loadImage("org/netbeans/modules/profiler/resources/memory.png")); // NOI18N);
        getAccessibleContext().setAccessibleDescription(COMPONENT_DESCR);
    }
}
