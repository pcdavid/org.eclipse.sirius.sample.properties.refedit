/*******************************************************************************
 * Copyright (c) 2016 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.sample.properties.refedit;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.sirius.common.ui.tools.api.selection.EObjectSelectionWizard;
import org.eclipse.sirius.ext.emf.edit.EditingDomainServices;
import org.eclipse.swt.widgets.Display;

public class ReferenceServices {
    private final EditingDomainServices editServices = new EditingDomainServices();
    /**
     * Asks the user to select interactively an EObject among a list of
     * candidates.
     * 
     * @param self
     *            the context.
     * @param title
     *            the title to use for the dialog.
     * @param message
     *            the message to use for the dialog.
     * @param candidates
     *            the candidates among which to select.
     * @param defaultValue
     *            the default value to return if the user selected nothing or
     *            cancelled the dialog.
     * @return the element selected by the user, or <code>null</code> if he
     *         selected none/canceled the dialog.
     */
    public EObject selectEObject(EObject self, String title, String message, Collection<EObject> candidates, EObject defaultValue) {
        AdapterFactory factory = editServices.getAdapterFactory(self);
        EObjectSelectionWizard wizard = new EObjectSelectionWizard(title, message, null, candidates, factory);
        wizard.setMany(Boolean.FALSE);
        final WizardDialog dlg = new WizardDialog(Display.getDefault().getActiveShell(), wizard);
        if (dlg.open() == Window.OK) {
            return wizard.getSelectedEObject();
        } else {
            return defaultValue;
        }
    }

    /**
     * Moves an EObject up in a list (one index closer to the head).
     * 
     * @param self
     *            the context.
     * @param elements
     *            the collection to modify.
     * @param value
     *            the value to move.
     * @return the context.
     */
    public EObject moveUp(EObject self, List<EObject> elements, EObject value) {
        int fromIndex = elements.indexOf(value);
        if (elements instanceof EList<?>) {
            ((EList<EObject>) elements).move(Math.max(0, fromIndex - 1), value);
        }
        return self;
    }

    /**
     * Moves an EObject down in a list (one index farther from the head).
     * 
     * @param self
     *            the context.
     * @param elements
     *            the collection to modify.
     * @param value
     *            the value to move.
     * @return the context.
     */
    public EObject moveDown(EObject self, List<EObject> elements, EObject value) {
        int fromIndex = elements.indexOf(value);
        if (elements instanceof EList<?>) {
            ((EList<EObject>) elements).move(Math.min(elements.size() - 1, fromIndex + 1), value);
        }
        return self;
    }
}
