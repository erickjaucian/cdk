/* $RCSfile$    
 * $Author$    
 * $Date$    
 * $Revision$
 * 
 * Copyright (C) 1997-2006  The Chemistry Development Kit (CDK) project
 * 
 * Contact: cdk-devel@lists.sourceforge.net
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA. 
 */
package org.openscience.cdk;

import org.openscience.cdk.interfaces.IChemObjectChangeEvent;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.interfaces.ISetOfMolecules;

/** 
 * A set of molecules, for example those taking part in a reaction.
 *
 * To retrieve the molecules from the set, there are two options:
 *
 * <pre>
 * Molecule[] mols = setOfMolecules.getMolecules();
 * for (int i=0; i < mols.length; i++) {
 *     Molecule mol = mols[i];
 * }
 * </pre>
 *
 * and
 *
 * <pre>
 * for (int i=0; i < setOfMolecules.getMoleculeCount(); i++) {
 *    Molecule mol = setOfMolecules.getMolecule(i);
 * }
 * </pre>
 *
 * @cdk.module data
 *
 * @cdk.keyword reaction
 * @cdk.keyword molecule
 */
public class SetOfMolecules extends SetOfAtomContainers implements ISetOfMolecules, Cloneable {

	/**
     * Determines if a de-serialized object is compatible with this class.
     *
     * This value must only be changed if and only if the new version
     * of this class is imcompatible with the old version. See Sun docs
     * for <a href=http://java.sun.com/products/jdk/1.1/docs/guide
     * /serialization/spec/version.doc.html>details</a>.
	 * 
	 */
	private static final long serialVersionUID = -861287315770869699L;

	public SetOfMolecules() {}
	
    /**
     *  Adds an molecule to this container.
     *
     * @param  molecule  The molecule to be added to this container 
     */
    public void addMolecule(org.openscience.cdk.interfaces.IMolecule molecule) {
        super.addAtomContainer(molecule);
	/* notifyChanged() called in super.addAtomContainer() */
    }
    
    /**
     *  Adds all molecules in the SetOfMolecules to this container.
     *
     * @param  moleculeSet  The SetOfMolecules 
     */
    public void add(org.openscience.cdk.interfaces.ISetOfMolecules moleculeSet) {
    	org.openscience.cdk.interfaces.IMolecule[] mols = moleculeSet.getMolecules();
        for (int i=0; i< mols.length; i++) {
            addMolecule(mols[i]);
        }
	/* notifyChanged() called in super.addAtomContainer() */
    }
    
    public void setMolecules(org.openscience.cdk.interfaces.IMolecule[] molecules)
    {
	    if (atomContainerCount > 0) removeAllAtomContainers();
	    for (int f = 0; f < molecules.length; f++)
	    {
		    addMolecule(molecules[f]);
	    }
    }
    
    /**
     *  Returns the array of Molecules of this container.
     *
     * @return    The array of Molecules of this container 
     * @see #setMolecules
     */
    public org.openscience.cdk.interfaces.IMolecule[] getMolecules() {
        Molecule[] result = new Molecule[super.getAtomContainerCount()];
        org.openscience.cdk.interfaces.IAtomContainer[] containers = super.getAtomContainers();
        for (int i=0; i<containers.length; i++) {
            result[i] = (Molecule)containers[i];
        }
        return result;
    }
    
    
    /**
     *  
     * Returns the Molecule at position <code>number</code> in the
     * container.
     *
     * @param  number  The position of the Molecule to be returned. 
     * @return         The Molecule at position <code>number</code> . 
     */
    public org.openscience.cdk.interfaces.IMolecule getMolecule(int number)
    {
        return (Molecule)super.getAtomContainer(number);
    }
    
    
    /**
     * Returns the number of Molecules in this Container.
     *
     * @return     The number of Molecules in this Container
     */
    public int getMoleculeCount() {
        return super.getAtomContainerCount();
    }
	
	
	/**
	 *  Clones this SetOfMolecules and its content.
	 *
	 *@return    the cloned object
	 */
	public Object clone() throws CloneNotSupportedException {
		SetOfMolecules clone = (SetOfMolecules)super.clone();
		IMolecule[] result = getMolecules();
		for (int i = 0; i < result.length; i++) {
			clone.addMolecule((Molecule) result[i].clone());
		}
		return (Object) clone;
	}
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("SetOfMolecules(");
        buffer.append(super.toString());
        buffer.append(')');
        return buffer.toString();
    }
    
    	/**
	 *  Called by objects to which this object has
	 *  registered as a listener.
	 *
	 *@param  event  A change event pointing to the source of the change
	 */
	public void stateChanged(IChemObjectChangeEvent event)
	{
		notifyChanged(event);
	}    
}
